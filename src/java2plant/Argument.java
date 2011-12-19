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
public class Argument {
    private String type;
    private String name;

    Argument(String str) {
        String[] split = str.split(" ");
        int i=0;
        while(i<split.length && split[i].isEmpty()) {
            i++;
        }
        this.type = split[i];
        i++;
        while(i<split.length && split[i].isEmpty()) {
            i++;
        }
        this.name = split[i];
    }

    Argument(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return type+" "+name;
    }

    void writeUML(BufferedWriter bw) {
        try {
            bw.write(this.name + ":" + this.type);
        } catch (IOException ex) {
            Logger.getLogger(Argument.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
