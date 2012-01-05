package java2plant;

import java2plant.describer.ContextDescriber;
import java.io.File;
import java2plant.builder.FromJavaBuilder;
import java2plant.writer.PlantWriter;
import javax.swing.JFileChooser;

/**
 *
 * @author arthur
 */
public class Java2Plant {

    //TODO: clean up
    private static File fInputDir;
    private static File fOutputDir;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /*
         * File fPlant = new File("/home/arthur/4A/BE COO/svn/trunk/class_diagram/diag.uml");
         * FromPlantBuilder fpb = new FromPlantBuilder();
         * fpb.buildFromFile(fPlant);
         * System.out.println("Fini");
         */
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
        
        FromJavaBuilder fjb = new FromJavaBuilder();
        PlantWriter pw = new PlantWriter(ContextDescriber.getInstance());
        pw.write(fOutputDir);
        
    }

}
