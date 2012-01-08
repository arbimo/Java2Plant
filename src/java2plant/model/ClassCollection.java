/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant.model;

import java.util.Collection;
import java2plant.describer.ClassDescriber;

/**
 *
 * @author arthur
 */
public interface ClassCollection {

	public Collection<ClassDescriber> getClasses();

	public boolean classExists(String className);
	
}
