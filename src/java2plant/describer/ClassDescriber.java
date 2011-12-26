/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant.describer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
    private boolean isInterface = false;
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
        this.inheritances.add(inheritance);
    }

    void writeUML(File fOutputDir) {
        BufferedWriter bw = null;
        try {
            String filename = fOutputDir.getAbsolutePath() + File.separator;
            filename += "classes" + File.separator + this.getName() + ".iuml";
            File f = new File(filename);
            f.getParentFile().mkdirs();
            bw = new BufferedWriter(new FileWriter(filename));
            
            if (!this.pack.isEmpty()) {
                bw.write("package " + this.pack);
                bw.newLine();
            }
            if (isAbstract()) {
                bw.write("abstract ");
            }
            if(isInterface) {
                bw.write("interface" + this.name + " {");
            } else {
                bw.write("class " + this.name + " {");
            }
            bw.newLine();
            for (Iterator it = getFields().iterator(); it.hasNext();) {
                FieldDescriber fd = (FieldDescriber) it.next();
                fd.writeUML(bw);
            }
            for (Iterator it = getMethods().iterator(); it.hasNext();) {
                MethodDescriber md = (MethodDescriber) it.next();
                md.writeUML(bw);
            }
            bw.write("}");
            bw.newLine();
            if (!this.pack.isEmpty()) {
                bw.write("end package");
                bw.newLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(ClassDescriber.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(ClassDescriber.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean isInterface() {
        return isInterface;
    }

    public void setInterface(boolean isInterface) {
        this.isInterface = isInterface;
    }
}
