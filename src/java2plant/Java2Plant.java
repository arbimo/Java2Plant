/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant;

import java2plant.describer.ContextDescriber;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java2plant.builder.AbstractBuilder;
import java2plant.builder.FromJavaBuilder;
import javax.swing.JFileChooser;

/**
 *
 * @author arthur
 */
public class Java2Plant {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            File fInputDir;
            File fOutputDir;
            if(args.length == 2) {
                fInputDir = new File(args[0]);
                fOutputDir = new File(args[1]);
            } else {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.showDialog(null, "Choose a directory containing your sources");
                fInputDir = fc.getSelectedFile();
                fc.showDialog(null, "Choose the output directory");
                fOutputDir= fc.getSelectedFile();
            }

            fOutputDir.mkdirs();
            File fClassDir = new File(fOutputDir, "class");
            fClassDir.mkdirs();

            ArrayList<File> files = new ArrayList();
            ArrayList<File> dirs = new ArrayList();
            
            if(fInputDir.isDirectory()) {
                dirs.add(fInputDir);
            } else {
                files.add(fInputDir);
            }

            int i=0;

            while(i<dirs.size()) {
                File[] childs = dirs.get(i).listFiles();
                for(int j=0; j<childs.length; j++) {
                    if(childs[j].isDirectory()) {
                        dirs.add(childs[j]);
                    } else if(childs[j].getName().endsWith(".java")) {
                        files.add(childs[j]);
                    }
                }
                i++;
            }
            i=0;
            while(i<files.size()) {
                System.out.println(files.get(i).getAbsolutePath()+" "+
                        files.get(i).getName() );
                i++;
            }


            FileWriter commonFW = new FileWriter(fOutputDir.getAbsolutePath()
                    + File.separator + "complete-diag.uml");
            commonFW.write("@startuml img/default.png\n");

            for(i=0; i<files.size(); i++) {
                FileInputStream fis = new FileInputStream(files.get(i));
                AbstractBuilder builder = new FromJavaBuilder();
                ContextDescriber context = builder.buildFromStream(fis);
                String umlFileName = fOutputDir.getAbsolutePath()
                        +File.separator + "class" + File.separator;
                umlFileName += (files.get(i).getName().replace(".java", ".iuml"));
                FileWriter fw = new FileWriter(umlFileName);
                BufferedWriter out = new BufferedWriter(fw);
                context.writeUML(out);
                out.close();

                
                commonFW.write("!include "+ "class"+File.separator + 
                        files.get(i).getName().replace(".java", ".iuml") +"\n");
            }

            // Create an empty file for user modifications
            File fRelations = new File(fOutputDir, "relations.iuml");
            if(!fRelations.exists()) {
                fRelations.createNewFile();
            }
            commonFW.write("!include "+ "relations.iuml\n");
            
            commonFW.write("@enduml\n");
            commonFW.close();
            

        } catch (Exception ex) {
            Logger.getLogger(Java2Plant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
