/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant.builder;

import java2plant.builder.AbstractBuilder;
import java2plant.describer.InterfaceDescriber;
import java2plant.describer.ContextDescriber;
import java2plant.describer.ClassDescriber;
import java.io.EOFException;
import java.io.InputStream;

/**
 *
 * @author arthur
 */
public class ContextParser extends AbstractBuilder {

    ContextDescriber context;

    ContextParser(InputStream is, ContextDescriber context) {
        super(is);
        this.context = context;
    }

    @Override
    public void parse() {
        try {
            while(true) {
                String str = getNext();
                System.out.println( str );
                if( str.contains("package ")) {
                    String[] split = str.split(" ");
                    for( int i=0 ; i< split.length ; i++ ) {
                        System.out.println( split[i] );
                        if( split[i].contentEquals("package") ) {
                            context.setNamespace( split[i+1]);
                        }
                    }
                } else if (str.contains(" class ")) {
                    ClassDescriber c = new ClassDescriber();
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
                    InterfaceDescriber c = new InterfaceDescriber();
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
