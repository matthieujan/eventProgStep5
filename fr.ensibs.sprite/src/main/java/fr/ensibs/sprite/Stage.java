package fr.ensibs.sprite;

import fr.ensibs.graphic.Image;
import java.util.Objects;

/**
 * A stage where a sequence takes place
 *
 * @author Pascale Launay
 * @version 2
 */
public class Stage {

    private Image background;            // the background image
    private final int width, height;     // the stage size

    //---------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------
    /**
     * Constructor
     *
     * @param width the stage width
     * @param height the stage height
     */
    public Stage(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Constructor
     *
     * @param background the background image
     * @param width the stage width
     * @param height the stage height
     */
    public Stage(Image background, int width, int height) {
        this(width, height);
        this.background = background;
    }

    //---------------------------------------------------------------
    // Getters
    //---------------------------------------------------------------
    /**
     * Give the background image
     *
     * @return the background image
     */
    public Image getBackground() {
        return background;
    }

    /**
     * Give the stage width
     *
     * @return the stage width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Give the stage height
     *
     * @return the stage height
     */
    public int getHeight() {
        return height;
    }

    //---------------------------------------------------------------
    // Setters
    //---------------------------------------------------------------
    /**
     * Set the background image
     *
     * @param background the background image
     */
    public void setBackground(Image background) {
        this.background = background;
    }

    //---------------------------------------------------------------
    // equals, hashCode
    //---------------------------------------------------------------
    @Override
    public boolean equals(Object o) {
        return (o instanceof Stage) && hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.background);
        hash = 83 * hash + this.width;
        hash = 83 * hash + this.height;
        return hash;
    }
}
