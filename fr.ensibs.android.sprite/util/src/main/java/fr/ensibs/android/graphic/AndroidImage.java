package fr.ensibs.android.graphic;

import android.graphics.Bitmap;
import fr.ensibs.graphic.Image;

/**
 * An {@link Image} for the Android graphic system
 *
 * @author Pascale Launay
 * @version 3
 */
public class AndroidImage implements Image {

    private final String name;   // the image name
    private final Bitmap bitmap; // {@link Bitmap} delegate instance

    //---------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------
    /**
     * Constructor
     *
     * @param name the image name
     * @param bitmap {@link Bitmap} delegate instance
     */
    public AndroidImage(String name, Bitmap bitmap) {
        this.name = name;
        this.bitmap = bitmap;
    }

    //---------------------------------------------------------------
    // Getter
    //---------------------------------------------------------------
    /**
     * Give the {@link Bitmap} instance that implements the Android image
     *
     * @return {@link Bitmap} instance
     */
    public Bitmap getBitmap() {
        return bitmap;
    }

    //---------------------------------------------------------------
    // Image method implementations
    //---------------------------------------------------------------
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }
}
