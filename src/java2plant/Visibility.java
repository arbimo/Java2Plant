/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arthur
 */
public class Visibility { 

    String visibility = "private";
    
    Visibility(String vis) {
        if( vis.equals("public")) {
            this.visibility = vis;
        }
        else if( vis.equals("protected")) {
            this.visibility = vis;
        }
        else if( vis.equals("package")) {
            this.visibility = vis;
        }
        else {
            this.visibility = "private";
        }
    }

    @Override
    public String toString() {
        return this.visibility;
    }

    public void writeUML(BufferedWriter bw) {
        String result;
        if( visibility.equals("public")) {
            result = "+";
        }
        else if( visibility.equals("protected")) {
            result = "#";
        }
        else if( visibility.equals("package")) {
            result = "~";
        }
        else {
            result = "-";
        }
        try {
            bw.write(result);
        } catch (IOException ex) {
            Logger.getLogger(Visibility.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
}
