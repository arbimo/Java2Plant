/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant;

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

public class ClassDescriptor {

    protected String name;
    protected Visibility visibility;
    protected String pack;
    private boolean isAbstract;

    protected ArrayList<FieldDescriptor> fields = new ArrayList();
    protected ArrayList<MethodDescriptor> methods = new ArrayList();

    public ClassDescriptor() {
        this.visibility = new Visibility("private");
        this.isAbstract = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    
    public void setVisibility(String vis) {
        this.visibility = new Visibility(vis);
    }

    public Visibility getVisibility() {
        return this.visibility;
    }

    public void print() {
        System.out.println("Class : ");
        System.out.println("Name : " + name);
        System.out.println("Visibility : " + visibility);
        for(Iterator it = fields.iterator(); it.hasNext() ;) {
            FieldDescriptor fd = (FieldDescriptor) it.next();
            fd.print();
        }
        for(Iterator it = methods.iterator(); it.hasNext() ;) {
            MethodDescriptor md= (MethodDescriptor) it.next();
            md.print();
        }
        
    }

    public void addField(FieldDescriptor fd) {
        this.fields.add(fd);
    }

    public void addMethod(MethodDescriptor md) {
        this.methods.add(md);
    }

    public String getPackage() {
        return pack;
    }

    public void setPackage(String pack) {
        this.pack = pack;
    }

    void writeUML(BufferedWriter fw) {
        try {
            if(isAbstract()) {
                fw.write("abstract ");
            }
            fw.write("class "+this.name +" {");
            fw.newLine();

            for(Iterator it = fields.iterator(); it.hasNext() ;) {
                FieldDescriptor fd = (FieldDescriptor) it.next();
                fd.writeUML(fw);
            }
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

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

}
