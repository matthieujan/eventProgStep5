package fr.ensibs.graphic;

import java.io.InputStream;

/**
 * A factory to make an image from an input stream
 *
 * @author Pascale Launay
 * @version 2
 */
public interface ImageFactory {

    //---------------------------------------------------------------
    // Factory method
    //---------------------------------------------------------------
    /**
     * Make an {@link Image} instance by reading an input stream
     *
     * @param name the name of the image
     * @param in the input stream
     * @return an image
     */
    public Image makeImage(String name, InputStream in);
}
