/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant.describer;

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
    
    public Visibility(String vis) {
        if( vis.equals("public")) {
            this.visibility = vis;
        } else if( vis.equals("protected")) {
            this.visibility = vis;
        } else if( vis.equals("package")) {
            this.visibility = vis;
        } else if( vis.equals("-")) {
            this.visibility = "private";
        } else if( vis.equals("#")) {
            this.visibility = "protected";
        } else if( vis.equals("~")) {
            this.visibility = "package";
        } else if( vis.equals("+")) {
            this.visibility = "public";
        } else {
            this.visibility = "private";
        }
    }

    @Override
    public String toString() {
        return this.visibility;
    }

    
        
}
