/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant.control;

import java.io.File;
import java2plant.builder.AbstractBuilder;
import java2plant.builder.FromJavaBuilder;
import java2plant.describer.ContextDescriber;
import java2plant.gui.Gui;
import java2plant.model.AppData;
import java2plant.model.ClassCollection;
import java2plant.model.ClassList;
import java2plant.writer.AbstractWriter;
import java2plant.writer.PlantWriter;

/**
 *
 * @author arthur
 */
public class Controller implements ToCtrl {

	private static Controller ctrl = null;
	private AbstractBuilder parser = null;
	private Gui gui = null;
	private AbstractWriter writer = null;

	private Controller() {
	}

	public static Controller getInstance() {
		if(Controller.ctrl == null) {
			Controller.ctrl = new Controller();
		}
		return Controller.ctrl;
	}

	public void setGui(Gui gui) {
		this.gui = gui;
	}

	@Override
	public void setInputFile(File in) {
		AppData.getInstance().setInputFile(in);
	}

	@Override
	public void setOutputFile(File out) {
		AppData.getInstance().setOutputFile(out);
	}

	@Override
	public void parseJava() {
		this.parser = new FromJavaBuilder();
		parser.buildFromFile(AppData.getInstance().getInputFile());
	}

	@Override
	public void writePlant(ClassCollection classes) {
		this.writer = new PlantWriter(classes);
		writer.write(AppData.getInstance().getOutputFile());
	}
    
}
