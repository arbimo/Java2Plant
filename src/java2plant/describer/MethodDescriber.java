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


public class MethodDescriber {

    private Visibility visibility;
    private String returnType;
    private String name;
    private boolean isAbstract;
    private ArrayList<ArgumentDescriber> args = new ArrayList();

    public MethodDescriber() {
        this.visibility = new Visibility("private");
        this.isAbstract = false;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(String vis) {
        this.visibility = new Visibility(vis);
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

    public void print() {
        System.out.println("Method : " + visibility +" "+returnType+" "+name);
        System.out.print("-- Args : ");
        for(Iterator it = args.iterator(); it.hasNext() ;) {
            ArgumentDescriber arg = (ArgumentDescriber) it.next();
            System.out.print(arg);
        }
        System.out.println("");
    }

    void writeUML(BufferedWriter bw) {
        try {
            this.visibility.writeUML(bw);
            bw.write(this.name+"(");
            for(Iterator it = args.iterator(); it.hasNext() ;) {
                ArgumentDescriber arg = (ArgumentDescriber) it.next();
                arg.writeUML(bw);
                if(it.hasNext()) {
                    bw.write(", ");
                }
            }
            if(this.returnType.equals("void")) {
                bw.write(")");
            } else {
                bw.write("):"+this.returnType);
            }
            bw.newLine();

        } catch (IOException ex) {
            Logger.getLogger(MethodDescriber.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addArg(ArgumentDescriber arg) {
        this.args.add(arg);
    }

}
