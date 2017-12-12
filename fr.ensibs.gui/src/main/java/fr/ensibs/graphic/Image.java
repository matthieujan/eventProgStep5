package fr.ensibs.graphic;

/**
 * Image representation for any graphic system
 *
 * @author Pascale Launay
 * @version 1
 */
public interface Image {

    //---------------------------------------------------------------
    // Getters
    //---------------------------------------------------------------
    /**
     * Give the name of the image
     *
     * @return the name of the image
     */
    public String getName();

    /**
     * Give the width of the image in pixels
     *
     * @return the width of the image
     */
    public int getWidth();

    /**
     * Give the height of the image in pixels
     *
     * @return the height of the image
     */
    public int getHeight();
}
