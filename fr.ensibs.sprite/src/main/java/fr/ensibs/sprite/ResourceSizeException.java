package fr.ensibs.sprite;

/**
 * An exception that occurs if a resource doesn't have the required size
 *
 * @author Pascale Launay
 * @version 2
 */
public class ResourceSizeException extends Exception {

    private final int width, height;                 // the size of the resource
    private final int requiredWidth, requiredHeight; // the required size

    //---------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------
    /**
     * Constructor. Initialize the size of the resource
     *
     * @param width the width of the resource
     * @param height the height of the resource
     * @param requiredWidth the required width
     * @param requiredHeight the required height
     */
    public ResourceSizeException(int width, int height, int requiredWidth, int requiredHeight) {
        super("Wrong resource size: " + width + "," + height
                + "(required: " + requiredWidth + "," + requiredHeight + ")");
        this.width = width;
        this.height = height;
        this.requiredWidth = requiredWidth;
        this.requiredHeight = requiredHeight;
    }

    //---------------------------------------------------------------
    // Getters
    //---------------------------------------------------------------
    /**
     * Give the width of the resource
     *
     * @return the width of the resource
     */
    public int getWidth() {
        return width;
    }

    /**
     * Give the height of the resource
     *
     * @return the height of the resource
     */
    public int getHeight() {
        return height;
    }

    /**
     * Give the required width
     *
     * @return the required width
     */
    public int getRequiredWidth() {
        return requiredWidth;
    }

    /**
     * Give the required height
     *
     * @return the required height
     */
    public int getRequiredHeight() {
        return requiredHeight;
    }
}
