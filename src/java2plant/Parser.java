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
abstract public class Parser {
    
    protected InputStream is;
    
    public Parser(InputStream is) {
        this.is = is;    
    }
    public abstract void parse();

    public boolean isSeparator( int c) {
        return c == ';' || c == '}' || c == '{';
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

}
