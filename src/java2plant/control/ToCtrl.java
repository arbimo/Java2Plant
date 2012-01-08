/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant.control;

import java.io.File;
import java2plant.model.ClassCollection;

/**
 *
 * @author arthur
 */
public interface ToCtrl {
    
    public void setInputFile(File in);
    
    public void setOutputFile(File out);

	public void parseJava();

	public void writePlant(ClassCollection classes);
    
}
