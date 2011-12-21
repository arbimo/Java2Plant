/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant.describer;

import java2plant.describer.ClassDescriber;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arthur
 */
public class ContextDescriber {
    String namespace = "";

    ArrayList<ClassDescriber> classes = new ArrayList();
    ArrayList<InterfaceDescriber> interfaces = new ArrayList();

    public void setNamespace(String namespace) {
        this.namespace = namespace;
        System.out.println("Nouveau namespace : "+ namespace);
    }

    public void addClass(ClassDescriber c) {
        c.setPackage(namespace);
        classes.add(c);
    }

    public void writeUML(BufferedWriter fw) {
        try {
            fw.write("package "+ this.namespace);
            fw.newLine();
            for(Iterator it = classes.iterator(); it.hasNext() ;) {
                ClassDescriber cd = (ClassDescriber) it.next();
                cd.writeUML(fw);
            }
            fw.write("end package");
            fw.newLine();
        } catch (IOException ex) {
            Logger.getLogger(ContextDescriber.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
