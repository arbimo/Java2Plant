/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant.builder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java2plant.describer.ArgumentDescriber;
import java2plant.describer.ClassDescriber;
import java2plant.describer.ContextDescriber;
import java2plant.describer.FieldDescriber;
import java2plant.describer.MethodDescriber;
import java2plant.describer.Visibility;

/**
 *
 * @author arthur
 */
public class FromPlantBuilder extends AbstractBuilder {

    public FromPlantBuilder() {
        context = ContextDescriber.getInstance();
    }

    @Override
    public ContextDescriber buildFromStream(InputStream inputStream) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ContextDescriber buildFromFile(File inputFile) {
        try {
            BufferedReader bf = new BufferedReader(new FileReader(inputFile));
            String line;
            ClassDescriber cd = null;
            while((line = bf.readLine()) != null) {
                System.out.println(line);
                if(line.startsWith("\'") || line.contains("...")) {
                } else if(line.startsWith("package")) {
                    System.out.println(line);
                    String[] split = splitString(line);
                    context.setNamespace(split[1]);
                } else if(line.contains("end") && line.contains("package")) {
                    context.setNamespace("");
                } else if(line.startsWith("!include")) {
                    String[] split = splitString(line);
                    buildFromFile(new File(inputFile.getParentFile(), split[1]));
                } else if(line.contains("{")) {
                    if(line.contains("class") || line.contains("interface")) {
                        boolean isAbstract = false;
                        String[] split = splitString(line);
                        for(int i=0; i<split.length; i++) {
                            if("abstract".equals(split[i])) {
                                isAbstract = true;
                            } else if("class".equals(split[i])) {
                                i++;
                                cd = context.getClass(split[i]);
                            } else if("interface".equals(split[i])) {
                                i++;
                                cd = context.getClass(split[i]);
                                cd.setInterface(true);
                            } else {
                                Logger.getLogger(FromPlantBuilder.class.getName()).log(Level.SEVERE, null);
                            }
                        }
                        cd.setPackage(context.getNamespace());
                        cd.setAbstract(isAbstract);
                    } else {
                        Logger.getLogger(FromPlantBuilder.class.getName()).log(Level.SEVERE, null);
                    }
                } else if(line.contains("}")) {
                    cd = null;
                } else if(line.startsWith("+") || line.startsWith("#") 
                        || line.startsWith("~") || line.startsWith("-")) {
                    Visibility vis = new Visibility("" + line.charAt(0));
                    if(line.contains("(")) {
                        MethodDescriber md = new MethodDescriber();
                        String name = line.substring(line.indexOf(" ")+1, line.indexOf("("));
                        String args = line.substring(line.indexOf("(")+1, line.indexOf(")"));

                        md.setName(name);
                        if(line.lastIndexOf("):") != -1) {
                            String returnType = line.substring(line.lastIndexOf(":")+1);
                            md.setReturnType(returnType);
                        }
                        String[] splitArgs = splitString(args, ", "); 
                        for(String s:splitArgs) {
                            String[] arg = splitString(s, ":");
                            ArgumentDescriber a = new ArgumentDescriber();
                            if(s.startsWith(":")) {
                                a.setType(arg[0]);
                            } else if(s.endsWith(":")) {
                                a.setName(arg[0]);
                            } else {
                                a.setName(arg[0]);
                                a.setType(arg[1]);
                            }
                            md.addArg(a);
                        }
                        cd.addMethod(md);
                    } else {
                        String name = line.substring(line.indexOf(" ")+1,line.indexOf(":"));
                        String type = line.substring(line.indexOf(":")+1);
                        
                        FieldDescriber field = new FieldDescriber();
                        field.setName(name);
                        field.setType(type);
                        cd.addField(field);
                    }
                } else {
                    parseRelations(line);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FromPlantBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }


        return context;
    }

    private void parseRelations(String line) {
        if(line.contains("--|>")) {
            String[] split = splitString(line);
        }
    }
    
}
