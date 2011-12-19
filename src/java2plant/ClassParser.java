/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arthur
 */
class ClassParser extends Parser {

    private ClassDescriptor cd;
    ClassParser(InputStream is, ClassDescriptor cd) {
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
                    FieldDescriptor fd = new FieldDescriptor();
                    
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
                    MethodDescriptor md = new MethodDescriptor(str);
                    
                    cd.addMethod(md);
                    
                }
                
                if(str.endsWith("{")) {
                    int openedBraces = 1;
                    while(openedBraces != 0) {
                        int c = is.read();
                        if( c=='}') {
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


