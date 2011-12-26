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
    }

    public void addClass(ClassDescriber c) {
        c.setPackage(namespace);
        classes.add(c);
    }

    public ClassDescriber findClass(String name) {
        for(ClassDescriber c:classes) {
            if(name.equals(c.getName())) {
                return c;
            }
        }
        for(ClassDescriber c:interfaces) {
            if(name.equals(c.getName())) {
                return c;
            }
        }
        return null;
    }

    public void writeUML(BufferedWriter fw) {
        for(Iterator it = classes.iterator(); it.hasNext() ;) {
            ClassDescriber cd = (ClassDescriber) it.next();
            cd.writeUML(fw);
        }
    }

    
}
