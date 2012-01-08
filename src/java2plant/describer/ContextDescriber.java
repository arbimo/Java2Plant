/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant.describer;

import java.util.Collection;
import java2plant.model.ClassList;

/**
 *
 * @author arthur
 */
public class ContextDescriber {
    private String namespace = "";

	ClassList classes = null;

    private static ContextDescriber instance = null;

    private ContextDescriber() {
		classes = ClassList.getInstance();
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
		classes.addClass(c);	
    }

    public ClassDescriber getClass(String pack, String name) {
		return classes.getClass(pack, name);
    }

    public boolean classExists(String className) {
        return classes.classExists(className);
    }

    public Collection<ClassDescriber> getClasses() {
        return classes.getClasses();
    }

    public String getNamespace() {
        return this.namespace;
    }
    
}
