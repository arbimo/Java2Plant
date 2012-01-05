/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant.builder;

import java.io.InputStream;
import java2plant.describer.ContextDescriber;

/**
 *
 * @author arthur
 */
abstract public class AbstractBuilder {
    
    protected InputStream is;
    protected ContextDescriber context;
    
    //TODO: should be from File (input dir or file)
    public abstract ContextDescriber buildFromStream(InputStream inputStream);

    /**
     * This method does the same as String.split but also removes the empty strings
     * from the result array.
     * @param str  A String to split
     * @param regex A regular expression corresponding to a separator
     * @return array of splited String
     */
    public static String[] splitString(String str, String regex) {
        //TODO: clean up that mess
        String buffer = null;
        if(str.contains("<") && str.indexOf(">") > str.indexOf("<")) {
            buffer = str.substring(str.indexOf("<"), str.indexOf(">")+1);
            str = str.replaceAll("<[^>]*>", "!!----!!");
        }
        String[] split = str.split(regex);
        int count = 0;
        
        for(String s:split) {
            if(!s.isEmpty()) {
                count++;
            }
        }

        String[] result = new String[count];
        count=0;

        for(String s:split) {
            if(!s.isEmpty()) {
                result[count] = s;
                count++;
            }
        }

        if(buffer != null) {
            for(int i=0 ; i<result.length; i++) {
                result[i] = result[i].replaceAll("!!----!!", buffer);
            }
        }
        
        return result;
    }
    
    public static String[] splitString(String str) {
        return splitString(str, "[ \n\t;{}]");
    }

}
