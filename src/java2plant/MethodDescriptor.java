/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arthur
 */


public class MethodDescriptor {

    private Visibility visibility;
    private String returnType;
    private String name;
    private ArrayList<Argument> args = new ArrayList();

    MethodDescriptor(String str) {
        this.visibility = new Visibility("private");
        this.buildFromString(str);
    }

    private void buildFromString(String str) {
        String[] split = str.split(" ");
        int i=0;
        while(i < split.length ) {
            if(split[i].isEmpty()) {
                i++;
            } else if(split[i].equals("public") || split[i].equals("private") ||
                    split[i].equals("protected") || split[i].equals("package")) {
                this.setVisibility(split[i]);
                i++;
            } else if(split[i].equals("static")) {
                i++;
            } else if(split[i].contains("final")) {
                i++;
            } else if(split[i].contains("abstract")) {
                i++;
            } else if(split[i].startsWith("@")) {
                i++;
            } else {
                if(split[i].contains("(")) {
                    this.setReturnType("");
                } else {
                    this.setReturnType( split[i]);
                    i++;
                }
                this.setName(split[i].substring(0, split[i].indexOf('(')));
                i = split.length; //exit
            }

        }
        /* Construction des arguments */
        int a = str.indexOf("(");
        int b = str.indexOf(")");

        str = str.substring(str.indexOf("(")+1, str.indexOf(")"));
        if(!str.isEmpty()) {
            split = str.split(",");
            for(int j=0; j<split.length; j++) {
                Argument arg = new Argument(split[j]);
                args.add(arg);
            }
            
        }
    }
    
    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(String vis) {
        this.visibility = new Visibility(vis);
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void print() {
        System.out.println("Method : " + visibility +" "+returnType+" "+name);
        System.out.print("-- Args : ");
        for(Iterator it = args.iterator(); it.hasNext() ;) {
            Argument arg = (Argument) it.next();
            System.out.print(arg);
        }
        System.out.println("");
    }

    void writeUML(BufferedWriter bw) {
        try {
            this.visibility.writeUML(bw);
            bw.write(this.name+"(");
            for(Iterator it = args.iterator(); it.hasNext() ;) {
                Argument arg = (Argument) it.next();
                arg.writeUML(bw);
                if(it.hasNext()) {
                    bw.write(", ");
                }
            }
            if(this.returnType.equals("void")) {
                bw.write(")");
            } else {
                bw.write("):"+this.returnType);
            }
            bw.newLine();

        } catch (IOException ex) {
            Logger.getLogger(MethodDescriptor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
