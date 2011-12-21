/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant.describer;

import java2plant.describer.ClassDescriber;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arthur
 */
class InterfaceDescriber extends ClassDescriber {

    @Override
    void writeUML(BufferedWriter fw) {
        try {
            
            fw.write("interface " + this.name  + " {");
            fw.newLine();

            for(Iterator it = methods.iterator(); it.hasNext() ;) {
                MethodDescriber md= (MethodDescriber) it.next();
                md.writeUML(fw);
            }

            fw.write("}");
            fw.newLine();
        } catch (IOException ex) {
            Logger.getLogger(ClassDescriber.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
