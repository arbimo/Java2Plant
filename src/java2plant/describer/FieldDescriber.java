/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant.describer;

/**
 *
 * @author arthur
 */
public class FieldDescriber {

    private Visibility visibility = new Visibility("private");
    private String name = "";
    private String type = "";

    public void setVisibility(String vis) {
        this.visibility = new Visibility(vis);
    }

    public Visibility getVisibility() {
        return visibility;
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

}
