/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java2plant.describer.ClassDescriber;
import javax.swing.AbstractListModel;

/**
 *
 * @author arthur
 */
public class ClassList extends AbstractListModel implements ClassCollection {

	private static ClassList instance = null;
    private ConcurrentHashMap<String, ClassDescriber> classes = new ConcurrentHashMap();

	private ClassList() {
	}

	public static ClassList getInstance() {
		if(instance == null) {
			instance = new ClassList();
		}
		return instance;
	}

	public void addClass(ClassDescriber c) {
		classes.put(c.getPackage()+"."+c.getName(), c);
		fireContentsChanged(this, 0, getSize());
    }

	public ClassDescriber getClass(String id) {
		return classes.get(id);
	}
	
    public ClassDescriber getClass(String pack, String name) {
        ClassDescriber result = null;
		if(classes.containsKey(pack+ "." + name)) {
			result = classes.get(pack + "." + name);	
		} else {
			result = new ClassDescriber();
			result.setName(name);
			result.setPackage(pack);
			addClass(result);
		}
        return result;
    }

	@Override
    public boolean classExists(String className) {
        boolean result = false;
        for(ClassDescriber c:classes.values()) {
            if(className.equals(c.getPackage() + "." + c.getName())) {
                result = true;
            }
        } 

        return result;
    }

	@Override
    public Collection<ClassDescriber> getClasses() {
        return classes.values();
    }
	
	@Override
	public int getSize() {
		return classes.size();
	}

	@Override
	public Object getElementAt(int index) {
		return new ArrayList(classes.keySet()).get(index);
	}

}