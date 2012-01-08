/*
 * To change c template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java2plant.describer.ArgumentDescriber;
import java2plant.describer.ClassDescriber;
import java2plant.describer.ContextDescriber;
import java2plant.describer.FieldDescriber;
import java2plant.describer.MethodDescriber;
import java2plant.describer.Visibility;
import java2plant.model.ClassCollection;

/**
 *
 * @author arthur
 */
public class PlantWriter extends AbstractWriter {

    private final ClassCollection classes;
    private ArrayList<Relation> relations = new ArrayList();

    public PlantWriter(ClassCollection classes) {
        this.classes= classes;
    }
    
    @Override
    public void write(File fOutputDir) {
        FileWriter commonFW = null;
        try {
            fOutputDir.mkdirs();
            
            commonFW = new FileWriter(fOutputDir.getAbsolutePath()
                    + File.separator + "complete-diag.uml");
            commonFW.write("@startuml img/default.png\n");
            
            for(ClassDescriber c:classes.getClasses()) {
                writeClass(c, fOutputDir);
                commonFW.write("!include " + "classes" + File.separator +
                        c.getName() +".iuml\n");
            }
            
            // Create an empty file for user modifications
            File fRelations = new File(fOutputDir, "relations.iuml");
            if(!fRelations.exists()) {
                fRelations.createNewFile();
            }
            commonFW.write("!include "+ "relations.iuml\n\n");
            writeRelations(commonFW);
            
            commonFW.write("\n@enduml\n");
            
        } catch (IOException ex) {
            Logger.getLogger(ContextDescriber.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                commonFW.close();
            } catch (IOException ex) {
                Logger.getLogger(ContextDescriber.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean relationExists(String class1, String class2) {
        for(Relation r: relations) {
            if(class1.equals(r.getClass1()) && class2.equals(r.getClass2())) {
                return true;
            }
        }
        return false;
    }
    public void addRelation(String class1, String class2) {
        if(class2.contains("<") && class2.indexOf(">") > class2.indexOf("<")) {
            class2 = class2.substring(class2.indexOf("<")+1, class2.indexOf(">"));
        }
        if(classes.classExists(class1) && classes.classExists(class2)) {
            if(!relationExists(class1, class2)) {
                relations.add(new Relation(class1, class2));
            }
        }
        
    }

    public void writeRelations(FileWriter fw) {
        for(Relation r : relations) {
            try {
                fw.write(r.getClass1() + " --> " + r.getClass2() + "\n");
            } catch (IOException ex) {
                Logger.getLogger(PlantWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void writeClass(ClassDescriber c, File fOutputDir) {
        BufferedWriter bw = null;
        try {
            String filename = fOutputDir.getAbsolutePath() + File.separator;
            filename += "classes" + File.separator + c.getName() + ".iuml";
            File f = new File(filename);
            f.getParentFile().mkdirs();
            bw = new BufferedWriter(new FileWriter(filename));
            
            if (!c.getPackage().isEmpty()) {
                bw.write("package " + c.getPackage());
                bw.newLine();
            }
            if (c.isAbstract()) {
                bw.write("abstract ");
            }
            if(c.isInterface()) {
                bw.write("interface " + c.getName());
            } else {
                bw.write("class " + c.getName());
            }
            
            bw.write(" {");
            bw.newLine();
            for (FieldDescriber fd : c.getFields()) {
                writeField(fd, bw);
                addRelation(c.getName(), fd.getType());
            }
            for (MethodDescriber md : c.getMethods()) {
                writeMethod(md, bw);
            }
            bw.write("}");
            bw.newLine();
 
            for(String inh:c.getInheritances()) {
                bw.write(" " + c.getName() + " --|> " + inh);                
                bw.newLine();
            }  

            if (!c.getPackage().isEmpty()) {
                bw.write("end package");
                bw.newLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(ClassDescriber.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(ClassDescriber.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
   public void writeField(FieldDescriber fd, BufferedWriter bw) {
        try {
            writeVisibility(fd.getVisibility(), bw);
            bw.write(" " + fd.getName() + ":" + fd.getType());
            bw.newLine();
        } catch (IOException ex) {
            Logger.getLogger(FieldDescriber.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 

   public void writeMethod(MethodDescriber md, BufferedWriter bw) {
        try {
            writeVisibility(md.getVisibility(), bw);
            bw.write(" "+ md.getName() + "(");
            for(Iterator it = md.getArgs().iterator(); it.hasNext() ;) {
                ArgumentDescriber arg = (ArgumentDescriber) it.next();
                writeArgument(arg, bw);
                if(it.hasNext()) {
                    bw.write(", ");
                }
            }
            if(md.getReturnType().equals("void")) {
                bw.write(")");
            } else {
                bw.write("):" + md.getReturnType());
            }
            bw.newLine();

        } catch (IOException ex) {
            Logger.getLogger(MethodDescriber.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    public void writeArgument(ArgumentDescriber arg, BufferedWriter bw) {
        try {
            bw.write(arg.getName() + ":" + arg.getType());
        } catch (IOException ex) {
            Logger.getLogger(ArgumentDescriber.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   public void writeVisibility(Visibility vis, BufferedWriter bw) {
        String result;
        String visibility = vis.toString();
        if( visibility.equals("public")) {
            result = "+";
        }
        else if( visibility.equals("protected")) {
            result = "#";
        }
        else if( visibility.equals("package")) {
            result = "~";
        }
        else {
            result = "-";
        }
        try {
            bw.write(result);
        } catch (IOException ex) {
            Logger.getLogger(Visibility.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    

}
