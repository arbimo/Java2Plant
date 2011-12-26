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

    protected String name = "";
    protected Visibility visibility = new Visibility("private");
    protected String pack = "";
    private boolean isAbstract = false;

    private ArrayList<FieldDescriber> fields = new ArrayList();
    private ArrayList<MethodDescriber> methods = new ArrayList();
    private ArrayList<String> inheritances = new ArrayList();

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
        for(Iterator it = getFields().iterator(); it.hasNext() ;) {
            FieldDescriber fd = (FieldDescriber) it.next();
            fd.print();
        }
        for(Iterator it = getMethods().iterator(); it.hasNext() ;) {
            MethodDescriber md= (MethodDescriber) it.next();
            md.print();
        }
        
    }

    public void addField(FieldDescriber fd) {
        this.getFields().add(fd);
    }

    public void addMethod(MethodDescriber md) {
        this.getMethods().add(md);
    }

    public String getPackage() {
        return pack;
    }

    public void setPackage(String pack) {
        this.pack = pack;
    }

    void writeUML(BufferedWriter fw) {
        try {
            if(!this.pack.isEmpty()) {
                fw.write(this.pack+"."+this.name +" as "+this.name);
                fw.newLine();
            }
            if(isAbstract()) {
                fw.write("abstract ");
            }
            fw.write("class " + this.name  + " {");
            fw.newLine();

            for(Iterator it = getFields().iterator(); it.hasNext() ;) {
                FieldDescriber fd = (FieldDescriber) it.next();
                fd.writeUML(fw);
            }
            for(Iterator it = getMethods().iterator(); it.hasNext() ;) {
                MethodDescriber md= (MethodDescriber) it.next();
                md.writeUML(fw);
            }

            fw.write("}");
            fw.newLine();
            
            if(!this.pack.isEmpty()) {
                fw.write("end package");
                fw.newLine();
            }

            
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

    public ArrayList<FieldDescriber> getFields() {
        return fields;
    }

    public ArrayList<MethodDescriber> getMethods() {
        return methods;
    }

    public ArrayList<String> getInheritances() {
        return inheritances;
    }

    public void addInheritance(String inheritance) {
        this.inheritances.add(inheritance);
    }

}
