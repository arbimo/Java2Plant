/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant.describer;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author arthur
 */
public class ClassDescriber {

    protected String name = "";
    protected Visibility visibility = new Visibility("private");
    protected String pack = "";
    private boolean isInterface = false;
    private boolean isAbstract = false;
    private ArrayList<FieldDescriber> fields = new ArrayList();
    private ArrayList<MethodDescriber> methods = new ArrayList();
    private ArrayList<String> inheritances = new ArrayList();

    public void setName(String name) {
        //TODO: manage a class name and an alias? to deal with that
        if(name.contains("<")) {
            this.name = name.substring(0, name.indexOf("<"));
        } else {
            this.name = name;
        }
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
        for (Iterator it = getFields().iterator(); it.hasNext();) {
            FieldDescriber fd = (FieldDescriber) it.next();
            fd.print();
        }
        for (Iterator it = getMethods().iterator(); it.hasNext();) {
            MethodDescriber md = (MethodDescriber) it.next();
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
        //TODO: not clean
        if(inheritance.contains("<")) {
            this.inheritances.add(inheritance.substring(0, inheritance.indexOf("<")));
        } else {
            this.inheritances.add(inheritance);
        }
    }

    public boolean isInterface() {
        return isInterface;
    }

    public void setInterface(boolean isInterface) {
        this.isInterface = isInterface;
    }
}
