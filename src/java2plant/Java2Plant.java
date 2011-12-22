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

    private static File fInputDir;
    private static File fOutputDir;
    private static File fClassDir;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
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
            fClassDir = new File(fOutputDir, "class");
            fClassDir.mkdirs();

            ArrayList<File> files = new ArrayList();
            ArrayList<File> dirs = new ArrayList();
            
            if(fInputDir.isDirectory()) {
                dirs.add(fInputDir);
            } else {
                files.add(fInputDir);
            }


            for(int i=0; i<dirs.size(); i++) {
                File[] childs = dirs.get(i).listFiles();
                for(File child:childs) {
                    if(child.isDirectory()) {
                        dirs.add(child);
                    } else if(child.getName().endsWith(".java")) {
                        files.add(child);
                    }
                }
            }
            for(File f : files)
                System.out.println(f.getAbsolutePath()+" "+f.getName() );


            FileWriter commonFW = new FileWriter(fOutputDir.getAbsolutePath()
                    + File.separator + "complete-diag.uml");
            commonFW.write("@startuml img/default.png\n");

            for(File f:files) {
                FileInputStream fis = new FileInputStream(f);
                AbstractBuilder builder = new FromJavaBuilder();
                ContextDescriber context = builder.buildFromStream(fis);
                File fOut = new File(fClassDir, f.getName().replace(".java", ".iuml"));
                FileWriter fw = new FileWriter(fOut);
                BufferedWriter bw = new BufferedWriter(fw);
                context.writeUML(bw);
                bw.close();
                
                commonFW.write("!include "+ "class"+File.separator + 
                        f.getName().replace(".java", ".iuml") +"\n");
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
