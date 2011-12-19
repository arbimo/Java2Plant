/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arthur
 */
public class FieldDescriptor {

    private Visibility visibility;
    private String name;
    private String type;

    public FieldDescriptor() {
        this.visibility = new Visibility("private");
    }

    public void setVisibility(String vis) {
        this.visibility = new Visibility(vis);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getType() {
        return this.type;
    }

    public void print() {
        System.out.println("Field : " + visibility +" "+type+" "+name);
    }

    void writeUML(BufferedWriter fw) {
        try {
            this.visibility.writeUML(fw);
            fw.write(this.name+":"+this.type);
            fw.newLine();
        } catch (IOException ex) {
            Logger.getLogger(FieldDescriptor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
