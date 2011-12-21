/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant.describer;

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

public class ClassDescriber {

    protected String name;
    protected Visibility visibility;
    protected String pack="";
    private boolean isAbstract;

    protected ArrayList<FieldDescriber> fields = new ArrayList();
    protected ArrayList<MethodDescriber> methods = new ArrayList();

    public ClassDescriber() {
        this.visibility = new Visibility("private");
        this.isAbstract = false;
        this.pack = "";
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
            FieldDescriber fd = (FieldDescriber) it.next();
            fd.print();
        }
        for(Iterator it = methods.iterator(); it.hasNext() ;) {
            MethodDescriber md= (MethodDescriber) it.next();
            md.print();
        }
        
    }

    public void addField(FieldDescriber fd) {
        this.fields.add(fd);
    }

    public void addMethod(MethodDescriber md) {
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
            /*
            if(!this.pack.isEmpty()) {
                fw.write(this.pack+"."+this.name +" as "+this.name);
                fw.newLine();
            }
            */
            if(isAbstract()) {
                fw.write("abstract ");
            }
            fw.write("class " + this.name  + " {");
            fw.newLine();

            for(Iterator it = fields.iterator(); it.hasNext() ;) {
                FieldDescriber fd = (FieldDescriber) it.next();
                fd.writeUML(fw);
            }
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

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

}
