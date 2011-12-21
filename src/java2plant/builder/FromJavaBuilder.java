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
public class FromJavaBuilder extends AbstractBuilder {

    @Override
    public ContextDescriber buildFromStream(InputStream inputStream) {
        this.is = inputStream;
        cd = new ContextDescriber();
    }
    
}
