package fr.ensibs.javaFX.graphic;

import javafx.scene.image.Image;

/**
 * An image for tha JavaX graphic system
 *
 * @author Pascale Launay
 * @version 2
 */
public class FXImage implements fr.ensibs.graphic.Image {

    private final String name;  // the image name
    private final Image image;  // the JavaFX image

    //---------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------
    /**
     * Initialize a javaFX image
     *
     * @param name the image name
     * @param image {@link javafx.scene.image.Image} instance
     */
    public FXImage(String name, Image image) {
        this.name = name;
        this.image = image;
    }

    //---------------------------------------------------------------
    // Getters
    //---------------------------------------------------------------
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getWidth() {
        return (int) Math.round(this.image.getWidth());
    }

    @Override
    public int getHeight() {
        return (int) Math.round(this.image.getHeight());
    }

    /**
     * Give the JavaFX image
     *
     * @return the JavaFX image
     */
    public Image getImage() {
        return this.image;
    }
}
