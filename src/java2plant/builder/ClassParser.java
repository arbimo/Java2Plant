/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant.builder;

import java2plant.builder.AbstractBuilder;
import java2plant.describer.MethodDescriber;
import java2plant.describer.FieldDescriber;
import java2plant.describer.ClassDescriber;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author arthur
 */
class ClassParser extends AbstractBuilder {

    private ClassDescriber cd;
    ClassParser(InputStream is, ClassDescriber cd) {
        super(is);
        this.cd = cd;
    }

    @Override
    public void parse() {
        boolean parsingClass = true;
        try {
            while(parsingClass) {
                String str = getNext();
                System.out.println("ClassParser : "+str);
                
                
                if(str.endsWith("}")) {
                    parsingClass = false;
                } else if(str.contains("=") || (!str.contains("(") && !str.contains(")")) && str.endsWith(";")) {
                    //TODO: Move to FieldDescriptor
                    FieldDescriber fd = new FieldDescriber();
                    
                    String[] split = str.split(" ");
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
                    cd.addField(fd);
                } else if(str.contains("(") && str.contains(")")) {
                    MethodDescriber md = new MethodDescriber(str);
                    
                    cd.addMethod(md);
                    
                }
                
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
}


