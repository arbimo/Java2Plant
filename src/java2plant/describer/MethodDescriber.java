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


public class MethodDescriber {

    private Visibility visibility = new Visibility("private");
    private String returnType = "";
    private String name = "";
    private boolean isAbstract = false;
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

    public ArrayList<ArgumentDescriber> getArgs() {
        return args;
    }

    public void print() {
        System.out.println("Method : " + visibility +" "+returnType+" "+name);
        System.out.print("-- Args : ");
        for(Iterator it = getArgs().iterator(); it.hasNext() ;) {
            ArgumentDescriber arg = (ArgumentDescriber) it.next();
            System.out.print(arg);
        }
        System.out.println("");
    }

    public void addArg(ArgumentDescriber arg) {
        this.getArgs().add(arg);
    }

}
