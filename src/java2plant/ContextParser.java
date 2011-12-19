/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant;

import java.io.EOFException;
import java.io.InputStream;

/**
 *
 * @author arthur
 */
public class ContextParser extends Parser {

    Context context;

    ContextParser(InputStream is, Context context) {
        super(is);
        this.context = context;
    }

    @Override
    public void parse() {
        try {
            while(true) {
                String str = getNext();
                System.out.println( str );
                if( str.contains(" package ")) {
                    String[] split = str.split(" ");
                    for( int i=0 ; i< split.length ; i++ ) {
                        System.out.println( split[i] );
                        if( split[i].contentEquals("package") ) {
                            context.setNamespace( split[i+1]);
                        }
                    }
                } else if (str.contains(" class ")) {
                    ClassDescriptor c = new ClassDescriptor();
                    context.addClass(c);
                    String[] split = str.split(" ");
                    
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
                    
                    ClassParser classParser = new ClassParser(is, c);
                    classParser.parse();
                    
                    c.print();
                   } else if (str.contains(" interface ")) {
                    InterfaceDescriptor c = new InterfaceDescriptor();
                    context.addClass(c);
                    String[] split = str.split(" ");
                    
                    for( int i=0 ; i< split.length ; i++ ) {
                        if(split[i].equals("public") || split[i].equals("private") ||
                                split[i].equals("protected") || split[i].equals("package")) {
                            c.setVisibility(split[i]);
                        } else if(split[i].equals("interface")) {
                            i++;
                            c.setName(split[i]);
                        }
                    }
                    
                    ClassParser classParser = new ClassParser(is, c);
                    classParser.parse();
                    
                    c.print(); 
                } else {

                }
            }
        } catch (EOFException ex) {
        }
    }

}
