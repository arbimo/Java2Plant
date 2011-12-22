package java2plant.builder;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java2plant.describer.ArgumentDescriber;
import java2plant.describer.ClassDescriber;
import java2plant.describer.ContextDescriber;
import java2plant.describer.FieldDescriber;
import java2plant.describer.InterfaceDescriber;
import java2plant.describer.MethodDescriber;

/**
 *
 * @author arthur
 */
public class FromJavaBuilder extends AbstractBuilder {

    @Override
    public ContextDescriber buildFromStream(InputStream inputStream) {
        this.is = inputStream;
        this.contextDescriber = new ContextDescriber();
        String str = "";
        while(str != "jfdudsjlkfhdsjf") {
            str = getNextDecla(is);
            System.out.println(str);
        } 

        parseFile(this.contextDescriber);

        return contextDescriber;
    }

    /**
     * This method does the same as String.split but also removes the empty strings
     * from the result array.
     * @param str  A String to split
     * @param regex A regular expression corresponding to a separator
     * @return array of splited String
     */
    public static String[] splitString(String str, String regex) {
        String[] split = str.split(regex);
        int count = 0;
        
        for(String s:split) {
            if(!s.isEmpty()) {
                count++;
            }
        }

        String[] result = new String[count];
        count=0;

        for(String s:split) {
            if(!s.isEmpty()) {
                result[count] = s;
                count++;
            }
        }

        return result;
    }

    public boolean isSeparator( int c) {
        return c == ';' || c == '}' || c == '{';
    }

    public String getNextDecla(InputStream is) {
        String str = "";
        try {
            int cOld = 0;
            int cNew = 0;
            boolean parsing = true;
            int openedBraces = 0;

            cNew =  is.read();
            if(cNew == -1) {
                throw new EOFException();
            }

            while(parsing) {
                cOld = cNew;
                cNew = is.read();
                if(cNew == '\"' && cOld != '\\') {
                    cOld = cNew;
                    cNew = is.read();
                    while(cNew != '\"' || cOld == '\\') {
                        cOld = cNew;
                        cNew = is.read();
                    }

                } else if(cNew == '\'' && cOld != '\\') {
                    cOld = cNew;
                    cNew = is.read();
                    while(cNew != '\'' || cOld == '\\') {
                        cOld = cNew;
                        cNew = is.read();
                    }
                } else if( cOld == '/' && cNew == '*') {
                    while( cOld != '*' || cNew != '/') {
                        cOld = cNew;
                        cNew = is.read();
                    }
                    
                } else if( cOld == '/' && cNew == '/' ) {
                    while( cNew != '\n' ) {
                        cOld = cNew;
                        cNew = is.read();
                    }
                } else {
                    str += (char) cOld;
                    if(cNew == '{') {
                        openedBraces++;
                    } else if(cNew == '}') {
                        openedBraces--;
                        if(openedBraces == 0) {
                            parsing = false;
                        }
                    } else if(cNew == ';' && openedBraces == 0) {
                        parsing = false;
                    }
                }
                    
            }
            str += (char) cNew;
            

        } catch (IOException ex) {
            Logger.getLogger(FromJavaBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }

    
    public String getNext() throws EOFException {
        String str = new String();
        int cOld = 0;
        int cNew = 0;
        try {
            cOld =  is.read();
            if(cOld == -1) {
                throw new EOFException();
            }
            cNew = is.read();
            while( !isSeparator( cNew ) ) {
                if( cNew == -1 ) {
                    throw new IOException();
                } if( cOld == '/' && cNew == '*') {
                    while( cOld != '*' || cNew != '/') {
                        cOld = cNew;
                        cNew = is.read();
                    }
                    cOld = cNew;
                    cNew = is.read();
                    
                } else if( cOld == '/' && cNew == '/' ) {
                    while( cNew != '\n' ) {
                        cNew = is.read();
                    }
                    cNew = is.read();
                } else {
                    str += (char) cOld;
                }
                cOld = cNew;
                cNew = is.read();
            }
            str += (char) cOld;
            str += (char) cNew;
            
        } catch (IOException ex) {
            if(ex instanceof EOFException ) {
                throw (EOFException) ex;
            }
        }
        str = str.replace("\n", " ");
        str = str.replace("\t", " ");
        str = str.replace("{", " {");
        str = str.replace("}", " }");
        str = str.replace(";", " ;");
        return str;
    }
    
    public void parseFile(ContextDescriber cd) {
        try {
            while(true) {
                String str = getNext();
                if( str.contains("package ")) {
                    String[] split = splitString(str, " ");
                    for( int i=0 ; i< split.length ; i++ ) {
                        if( split[i].contentEquals("package") ) {
                            cd.setNamespace( split[i+1]);
                        }
                    }
                } else if (str.contains(" class ")) {
                    ClassDescriber c = new ClassDescriber();
                    cd.addClass(c);
                    String[] split = splitString(str, " ");
                    
                    for( int i=0 ; i< split.length ; i++ ) {
                        if(split[i].equals("public") || split[i].equals("private") ||
                                split[i].equals("protected") || split[i].equals("package")) {
                            c.setVisibility(split[i]);
                        } else if(split[i].equals("abstract")) {
                            c.setAbstract(true);
                        } else if(split[i].equals("class")) {
                            i++;
                            c.setName(split[i]);
                        }
                    }
                    
                    parseClass(c);
                    
                   } else if (str.contains(" interface ")) {
                    InterfaceDescriber c = new InterfaceDescriber();
                    cd.addClass(c);
                    String[] split = splitString(str, " ");
                    
                    for( int i=0 ; i< split.length ; i++ ) {
                        if(split[i].equals("public") || split[i].equals("private") ||
                                split[i].equals("protected") || split[i].equals("package")) {
                            c.setVisibility(split[i]);
                        } else if(split[i].equals("interface")) {
                            i++;
                            c.setName(split[i]);
                        }
                    }
                    
                    parseClass(c);
                } else {

                }
            }
        } catch (EOFException ex) {
        }
    }

    public ClassDescriber buildClassFromString(String str) {
        ClassDescriber cd = new ClassDescriber();

        return cd;
    }

    public void parseClass(ClassDescriber classDescriber) {
        boolean parsingClass = true;
        try {
            while(parsingClass) {
                String str = getNext();
                
                if(str.endsWith("}")) {
                    parsingClass = false;
                } else if(str.contains("=") || (!str.contains("(") && !str.contains(")")) && str.endsWith(";")) {
                    //TODO: Move to FieldDescriptor
                    FieldDescriber fd = new FieldDescriber();
                    
                    String[] split = splitString(str, " ");
                    int i=0;
                    while(i < split.length ) {
                        if(split[i].isEmpty()) {
                            i++;
                        } else if(split[i].equals("public") || split[i].equals("private") ||
                                split[i].equals("protected") || split[i].equals("package")) {
                            fd.setVisibility(split[i]);
                            i++;
                        } else if(split[i].contains("final")) {
                            i++;
                        } else if(split[i].contains("static")) {
                            i++;
                        } else {
                            fd.setType( split[i]);
                            fd.setName(split[i+1]);
                            i = split.length; //exit
                        }
                    }
                    classDescriber.addField(fd);
                } else if(str.contains("(") && str.contains(")")) {
                    MethodDescriber md = buildMethodFromString(str);
                    classDescriber.addMethod(md);
                }
                

                //TODO: clean up that mess
                if(str.endsWith("{")) {
                    int openedBraces = 1;
                    boolean openedDoubleQuotes = false;
                    boolean openedQuotes = false;
                    int cOld = ' ';
                    int c = ' ';
                    while(openedBraces != 0) {
                        cOld = c;
                        c = is.read();
                            
                        if( c=='\'' && cOld != '\\' && !openedDoubleQuotes) {
                            openedQuotes = !openedQuotes;
                        } else if( c=='\"' && cOld != '\\') {
                            openedDoubleQuotes = !openedDoubleQuotes;
                        } else if(openedQuotes || openedDoubleQuotes) {
                        } else if( c=='*' && cOld=='/') {
                            cOld = c;
                            c = is.read();
                            while(c!='/' || cOld!='*') {
                                cOld = c;
                                c = is.read();
                            }
                        } else if( c=='/' && cOld=='/') {
                            cOld = c;
                            c = is.read();
                            while(c!='\n') {
                                cOld = c;
                                c = is.read();
                            }
                        } else if(c=='}') {
                            openedBraces--;
                        } else if(c=='{') {
                            openedBraces++;
                        }
                        
                    }
                }
            }
        } catch (IOException ex) {
        }
    }

    public MethodDescriber buildMethodFromString(String str) {
        MethodDescriber md = new MethodDescriber();
        String[] split = str.split(" ");
        int i=0;
        while(i < split.length ) {
            if(split[i].isEmpty()) {
                i++;
            } else if(split[i].equals("public") || split[i].equals("private") ||
                    split[i].equals("protected") || split[i].equals("package")) {
                md.setVisibility(split[i]);
                i++;
            } else if(split[i].equals("static")) {
                i++;
            } else if(split[i].contains("final")) {
                i++;
            } else if(split[i].contains("abstract")) {
                md.setAbstract(true);
                i++;
            } else if(split[i].startsWith("@")) {
                i++;
            } else {
                if(split[i].contains("(")) {
                    md.setReturnType("");
                } else {
                    md.setReturnType( split[i]);
                    i++;
                }
                md.setName(split[i].substring(0, split[i].indexOf('(')));
                i = split.length; //exit
            }

        }
        /* Construction des arguments */
        int a = str.indexOf("(");
        int b = str.indexOf(")");

        str = str.substring(str.indexOf("(")+1, str.indexOf(")"));
        if(!str.isEmpty()) {
            split = splitString(str, ",");
            for(int j=0; j<split.length; j++) {
                ArgumentDescriber arg = new ArgumentDescriber(split[j]);
                md.addArg(arg);
            }
            
        }
        return md;
    }
    
}
