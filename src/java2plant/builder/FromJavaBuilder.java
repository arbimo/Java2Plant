package java2plant.builder;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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

    public ContextDescriber build(File fInputDir, File fOutputDir) {
        try {
            fOutputDir.mkdirs();
            File fClassDir = new File(fOutputDir, "class");
            fClassDir.mkdirs();

            ArrayList<File> files = new ArrayList();
            ArrayList<File> dirs = new ArrayList();
            
            if(fInputDir.isDirectory()) {
                dirs.add(fInputDir);
            } else {
                files.add(fInputDir);
            }

            for(int i=0; i<dirs.size(); i++) {
                File[] childs = dirs.get(i).listFiles();
                for(File child:childs) {
                    if(child.isDirectory()) {
                        dirs.add(child);
                    } else if(child.getName().endsWith("FromJavaBuilder.java")) {
                        files.add(child);
                    }
                }
            }
            for(File f : files)
                System.out.println(f.getAbsolutePath()+" "+f.getName() );


            FileWriter commonFW = new FileWriter(fOutputDir.getAbsolutePath()
                    + File.separator + "complete-diag.uml");
            commonFW.write("@startuml img/default.png\n");

            for(File f:files) {
                FileInputStream fis = new FileInputStream(f);
                AbstractBuilder builder = new FromJavaBuilder();
                ContextDescriber context = builder.buildFromStream(fis);
                File fOut = new File(fClassDir, f.getName().replace(".java", ".iuml"));
                FileWriter fw = new FileWriter(fOut);
                BufferedWriter bw = new BufferedWriter(fw);
                context.writeUML(bw);
                bw.close();
                
                commonFW.write("!include "+ "class"+File.separator + 
                        f.getName().replace(".java", ".iuml") +"\n");
            }

            // Create an empty file for user modifications
            File fRelations = new File(fOutputDir, "relations.iuml");
            if(!fRelations.exists()) {
                fRelations.createNewFile();
            }
            commonFW.write("!include "+ "relations.iuml\n");
            
            commonFW.write("@enduml\n");
            commonFW.close();
            

        } catch (Exception ex) {
            Logger.getLogger(FromJavaBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }

        return contextDescriber;

    }

    @Override
    public ContextDescriber buildFromStream(InputStream inputStream) {
        this.is = inputStream;
        this.contextDescriber = new ContextDescriber();
        
        String str = getNext(is);
        String decla = extractDeclaration(str);
        while(!str.isEmpty()) {
            
            if( decla.contains("package ")) {
                String[] split = splitString(decla);
                for( int i=0 ; i< split.length ; i++ ) {
                    if( split[i].contentEquals("package") ) {
                        contextDescriber.setNamespace(split[i+1]);
                    }
                }
            } else if (decla.contains(" class ")) {
                ClassDescriber c = buildClassFromString(str);
                contextDescriber.addClass(c);
            } else if (str.contains(" interface ")) {
                //TODO
            }
            str = getNext(is);
            decla = extractDeclaration(str);
        }
        
        return contextDescriber;
    }
                                                                   
    


public String getNext(InputStream is) { 
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
                    cOld = cNew;
                    cNew = is.read();
                    
                } else if(cNew == '\'' && cOld != '\\') {
                    cOld = cNew;
                    cNew = is.read();
                    while(cNew != '\'' || cOld == '\\') {
                        cOld = cNew;
                        cNew = is.read();
                    }
                    cOld = cNew;
                    cNew = is.read();
                } else if( cOld == '/' && cNew == '*') {
                    while( cOld != '*' || cNew != '/') {
                        cOld = cNew;
                        cNew = is.read();
                    }
                    cOld = cNew;
                    cNew = is.read();
                    
                } else if( cOld == '/' && cNew == '/' ) {
                    while( cNew != '\n' ) {
                        cOld = cNew;
                        cNew = is.read();
                    }
                    cOld = cNew;
                    cNew = is.read();
                } else {
                    str += (char) cOld;
                    if(cNew == -1) {
                        parsing=false;
                    } else if(cNew == '{') {
                        openedBraces++;
                        System.out.println("++ " + str + (char) cNew+"\n\n");
                    } else if(cNew == '}') {
                        openedBraces--;
                        System.out.println("-- " + str + (char) cNew + "\n\n");
                        if(openedBraces == 0) {
                            parsing = false;
                        }
                    } else if(cNew == ';' && openedBraces == 0) {
                        parsing = false;
                    }
                }
                
            }
            str += (char) cNew;
        } catch (EOFException ex) {
        } catch (IOException ex) {
            Logger.getLogger(FromJavaBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }

public String getNext(String src) {
        String next = "";
        
        if(src.indexOf(";") != -1 && (src.indexOf(";") < src.indexOf("{") || src.indexOf("{") == -1) ) {
            next = src.substring(0, src.indexOf(";")+1);
        } else if(src.indexOf("{") != -1) {
            int openedBraces = 1;
            int i = src.indexOf("{")+1;
            while(openedBraces > 0) {
                char c = src.charAt(i);
                if(c=='{') {
                    openedBraces++;
                    System.out.println("++" + src.charAt(i-1) + src.charAt(i));
                } else if(c=='}') {
                    openedBraces--;
                    System.out.println("--" + src.charAt(i-1) + src.charAt(i));
                }
                i++;
            }
            next = src.substring(0, i);
        }

        return next;
    }

    public String extractDeclaration(String str) {
        String declaration = "";
        if(str.indexOf(";") != -1 && str.indexOf(";") < str.indexOf("{")) {
            declaration = str.substring(0, str.indexOf(";")+1);
        } else if(str.indexOf("{") != -1) {
            declaration = str.substring(0, str.indexOf("{")+1);
        }
        return declaration;
    }

    public ClassDescriber buildClassFromString(String str) {
        ClassDescriber cd = new ClassDescriber();

        String declaration = extractDeclaration(str);
        String split[] = splitString(extractDeclaration(str), " ");
        
        for( int i=0 ; i< split.length ; i++ ) {
            if(split[i].equals("public") || split[i].equals("private") ||
                    split[i].equals("protected") || split[i].equals("package")) {
                cd.setVisibility(split[i]);
            } else if(split[i].equals("abstract")) {
                cd.setAbstract(true);
            } else if(split[i].equals("class")) {
                i++;
                cd.setName(split[i]);
            }
        }

        if(declaration.endsWith("{")) {
            str = str.substring(declaration.length(), str.lastIndexOf("}"));
        } else {
            str = "";
        }

        while(!str.isEmpty()) {
            String current = getNext(str);
            declaration = extractDeclaration(current);

            if(current.isEmpty()) {
                str = "";
            } else if(current.endsWith(";") && declaration.contains("=")) {
                FieldDescriber field = buildFieldFromString(current);
                cd.addField(field);
            } else if(current.endsWith(";") && !declaration.contains("(")) {
                FieldDescriber field = buildFieldFromString(current);
                cd.addField(field);
            } else {
                MethodDescriber method = buildMethodFromString(declaration);
                cd.addMethod(method);
            }
            str = str.substring(current.length());
        }
        

        return cd;
    }

    public MethodDescriber buildMethodFromString(String str) {
        MethodDescriber md = new MethodDescriber();
        String[] split = splitString(str);
        int i=0;
        while(i < split.length ) {
            if(split[i].equals("public") || split[i].equals("private") ||
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
                if(split[i].contains("(")) {
                    md.setName(split[i].substring(0, split[i].indexOf('(')));
                } else {
                    md.setName(split[i]);
                }
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
                ArgumentDescriber arg = buildArgumentFromString(split[j]);
                md.addArg(arg);
            }
            
        }
        return md;
    }

    private ArgumentDescriber buildArgumentFromString(String str) {
        ArgumentDescriber ad = new ArgumentDescriber();

        String[] split = splitString(str, " ");
        int i=0;
        while(i<split.length && split[i].isEmpty()) {
            i++;
        }
        ad.setType(split[i]);
        i++;
        while(i<split.length && split[i].isEmpty()) {
            i++;
        }
        ad.setName(split[i]);

        return ad;
    }

    public FieldDescriber buildFieldFromString(String str) {
        FieldDescriber fd = new FieldDescriber();

        str = str.replace(";","");
        
        String[] split = splitString(str);
        int i=0;
        while(i < split.length ) {
            if(split[i].equals("public") || split[i].equals("private") ||
                    split[i].equals("protected") || split[i].equals("package")) {
                fd.setVisibility(split[i]);
                i++;
            } else if(split[i].contains("final")) {
                i++;
            } else if(split[i].contains("static")) {
                i++;
            } else {
                fd.setType(split[i]);
                fd.setName(split[i+1]);
                i = split.length; //exit
            }
        }
        return fd;
    }

}
