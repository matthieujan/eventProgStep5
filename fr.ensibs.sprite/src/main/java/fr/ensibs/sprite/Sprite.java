package fr.ensibs.sprite;

import fr.ensibs.graphic.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A sprite composed of a list of images having all the same size
 *
 * @author Pascale Launay
 * @version 2
 */
public class Sprite {

    // sprite properties
    private final String name;        // the name of the sprite
    private final List<Image> images; // the images that compose the sprite
    private final long duration;      // total duration to display the images (in s)
    private int width, height;        // size of the images

    //State related
    private int x,y;
    private boolean visible;
    private long startTime;
    private long time;
    private Image currentImage;

    //---------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------
    /**
     * Constructor. Initialize a sprite with an empty list of images
     *
     * @param name the name of the sprite
     * @param duration the sprite duration
     */
    public Sprite(String name, long duration) {
        this.name = name;
        this.duration = duration;
        this.images = new ArrayList<>();
        this.x = 0;
        this.y = 0;
        this.visible = false;
        this.startTime = 0;
    }

    /**
     * Constructor. Initialize a sprite and its images
     *
     * @param name the name of the sprite
     * @param images the images that compose the sprite
     * @param duration the sprite duration
     * @throws ResourceSizeException if the size of an image is not compatible
     * with the other images of the sprite
     */
    public Sprite(String name, List<Image> images, long duration) throws ResourceSizeException {
        this(name, duration);
        for (Image image : images) {
            addImage(image);
        }
    }

    //---------------------------------------------------------------
    // Getters
    //---------------------------------------------------------------
    /**
     * Give the images that compose the sprite
     *
     * @return the images that compose the sprite
     */
    public List<Image> getImages() {
        return images;
    }

    /**
     * Give the total duration to display the images (in s)
     *
     * @return total duration to display the images
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Give the name of the sprite
     *
     * @return the name of the sprite
     */
    public String getName() {
        return name;
    }

    /**
     * Give the width of the images of the sprite
     *
     * @return the width of the images of the sprite
     */
    public int getWidth() {
        return width;
    }

    /**
     * Give the height of the images of the sprite
     *
     * @return the height of the images of the sprite
     */
    public int getHeight() {
        return height;
    }

    //---------------------------------------------------------------
    // Setters
    //---------------------------------------------------------------
    /**
     * Add an image to this sprite
     *
     * @param idx the index of the image in the list
     * @param image the image to be added
     * @throws ResourceSizeException if the size of the image is not compatible
     * with the other images of the sprite
     */
    public final void addImage(int idx, Image image) throws ResourceSizeException {
        if (images.isEmpty()) {
            this.width = image.getWidth();
            this.height = image.getHeight();
        } else if (this.width != image.getWidth() || this.height != image.getHeight()) {
            throw new ResourceSizeException(image.getWidth(), image.getHeight(), width, height);
        }
        images.add(idx, image);
    }

    /**
     * Add an image to this sprite at the end of the list
     *
     * @param image the image to be added
     * @throws ResourceSizeException if the size of the image is not compatible
     * with the other images of the sprite
     */
    public final void addImage(Image image) throws ResourceSizeException {
        Sprite.this.addImage(images.size(), image);
    }

    //---------------------------------------------------------------
    // equals, hashCode
    //---------------------------------------------------------------
    @Override
    public boolean equals(Object o) {
        return (o instanceof Sprite) && hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.name);
        hash = 41 * hash + Objects.hashCode(this.images);
        hash = 41 * hash + (int) (this.duration ^ (this.duration >>> 32));
        hash = 41 * hash + this.width;
        hash = 41 * hash + this.height;
        return hash;
    }

    //TODO Documentation ...
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        if(visible && !this.visible){
            startTime = time;
            setCurrentTime(time);
        }
        this.visible = visible;
    }

    public void setCurrentTime(long time) {
        this.time = time;
        if(visible){
            int deltaT = (int) ((time - startTime)%duration);
            int indexImage = deltaT/images.size();
            currentImage = images.get(indexImage);
        }
    }

    public Image getCurrentImage() {
        return currentImage;
    }

    public void reset() {
        x = 0;
        y = 0;
        visible = false;
        time = 0;
        startTime = 0;
        currentImage = null;
    }
}
