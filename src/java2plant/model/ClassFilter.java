/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant.model;

import java.util.ArrayList;
import java.util.Collection;
import java2plant.describer.ClassDescriber;
import javax.swing.AbstractListModel;

/**
 *
 * @author arthur
 */
public class ClassFilter extends AbstractListModel implements ClassCollection {

	ClassList classes = null;
	ArrayList<String> filters = new ArrayList();

	public ClassFilter(ClassList classes) {
		this.classes = classes;
	}

	@Override
	public Collection<ClassDescriber> getClasses() {
		ArrayList<ClassDescriber> list = new ArrayList();

		for(int i=0 ; i<filters.size() ; i++) {
			if(classes.classExists(filters.get(i))) {
				list.add(classes.getClass(filters.get(i)));
			}
		}

		return list;
	}

	public void add(String filter) {
		filters.add(filter);
		fireContentsChanged(this, 0, getSize());
	}
	
	@Override
	public int getSize() {
		return filters.size();
	}

	@Override
	public Object getElementAt(int index) {
		return filters.get(index);
	}

	@Override
	public boolean classExists(String className) {
		for(String s : filters) {
			if(s.endsWith(className)) {
				return true;
			}
		}
		return false;
	}

}
