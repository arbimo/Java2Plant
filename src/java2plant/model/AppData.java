/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant.model;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author arthur
 */
public class AppData {

	static AppData instance = null;

	private File inputFile;
	private File outputFile;
	private ArrayList<ClassFilter> filters = new ArrayList();
	private int currentFilter = -1;

	private AppData() {
	}

	public static AppData getInstance() {
		if(instance == null) {
			instance = new AppData();
		}
		return instance;
	}

	public ClassFilter getCurrentFilter() {
		ClassFilter filter = null;
		if(currentFilter == -1) {
			filter = new ClassFilter(ClassList.getInstance());
			filters.add(filter);
			this.currentFilter = filters.size() - 1;
		} else {
			filter = filters.get(currentFilter);
		}
		return filter;
	}

	public File getInputFile() {
		return inputFile;
	}

	public void setInputFile(File inputFile) {
		this.inputFile = inputFile;
	}

	public File getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}
	
}
