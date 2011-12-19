/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author arthur
 */
public class Context {
    String namespace;

    ArrayList<ClassDescriptor> classes = new ArrayList();
    ArrayList<InterfaceDescriptor> interfaces = new ArrayList();

    public void setNamespace(String namespace) {
        this.namespace = namespace;
        System.out.println("Nouveau namespace : "+ namespace);
    }

    public void addClass(ClassDescriptor c) {
        c.setPackage(namespace);
        classes.add(c);
    }

    void writeUML(BufferedWriter fw) {
        for(Iterator it = classes.iterator(); it.hasNext() ;) {
            ClassDescriptor cd = (ClassDescriptor) it.next();
            cd.writeUML(fw);
        }
    }

    
}
