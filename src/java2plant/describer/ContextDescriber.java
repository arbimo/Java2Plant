/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant.describer;

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
public class ContextDescriber {
    private String namespace = "";

    private ArrayList<ClassDescriber> classes = new ArrayList();

    private static ContextDescriber instance = null;

    private ContextDescriber() {
    }

    public static ContextDescriber getInstance() {
        if(instance == null) {
            instance = new ContextDescriber();
        }
        return instance;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void addClass(ClassDescriber c) {
        classes.add(c);
    }

    public ClassDescriber getClass(String name) {
        ClassDescriber result = null;
        for(ClassDescriber c:classes) {
            if(name.equals(c.getName())) {
                result = c;
            }
        }
        if(result == null) {
            result = new ClassDescriber();
            result.setName(name);
            addClass(result);
        }
        return result;
    }

    public ArrayList<ClassDescriber> getClasses() {
        return classes;
    }

    public String getNamespace() {
        return this.namespace;
    }

    
}
