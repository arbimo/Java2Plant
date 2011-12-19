/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arthur
 */
class InterfaceDescriptor extends ClassDescriptor {
    @Override
    void writeUML(BufferedWriter fw) {
        try {
            fw.write("interface "+this.name +" {");
            fw.newLine();

            for(Iterator it = methods.iterator(); it.hasNext() ;) {
                MethodDescriptor md= (MethodDescriptor) it.next();
                md.writeUML(fw);
            }

            fw.write("}");
            fw.newLine();
        } catch (IOException ex) {
            Logger.getLogger(ClassDescriptor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
