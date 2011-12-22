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
    protected ContextDescriber contextDescriber;
    
    public abstract ContextDescriber buildFromStream(InputStream inputStream);

}
