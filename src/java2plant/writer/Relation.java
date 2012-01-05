/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant.writer;

/**
 *
 * @author arthur
 */
public class Relation {

    private String class1;
    private String class2;

    public Relation(String class1, String class2) {
        this.class1 = class1;
        this.class2 = class2;
    }

    public String getClass1() {
        return class1;
    }

    public void setClass1(String class1) {
        this.class1 = class1;
    }

    public String getClass2() {
        return class2;
    }

    public void setClass2(String class2) {
        this.class2 = class2;
    }
    
}
