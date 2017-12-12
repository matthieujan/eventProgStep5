package fr.ensibs.android.sprite;

import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;
import fr.ensibs.android.graphic.AndroidImage;
import fr.ensibs.android.graphic.AndroidImageFactory;
import fr.ensibs.conf.Configuration;
import fr.ensibs.fs.FileSystem;
import fr.ensibs.graphic.Image;
import fr.ensibs.graphic.ImagesLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * A class with operations defined for trial purposes
 *
 * @author Pascale Launay
 * @version 3
 */
public class AndroidTrials {

    private final Activity activity;           // the parent activity
    private final ImageView imageView;         // the view to display the image
    private final FileSystem fs;               // the file system
    private final Configuration configuration; // the application configuration properties

    //---------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------
    /**
     * Constructor
     *
     * @param activity the parent activity
     * @param imageView the view to display the image
     * @param fs the file system
     * @param configuration the application configuration properties
     */
    public AndroidTrials(Activity activity, ImageView imageView, FileSystem fs, Configuration configuration) {
        this.activity = activity;
        this.imageView = imageView;
        this.fs = fs;
        this.configuration = configuration;
    }

    //---------------------------------------------------------------
    // Trial
    //---------------------------------------------------------------
    /**
     * Display the background image and the sprite and start the sprite motion
     */
    public void startTrial() {
        String images = configuration.get("images");
        String background = configuration.get("background");
        try {
            drawBackground(loadBackground(fs, images, background));
        } catch (IOException e) {
            Log.e(getClass().getName(), "Unable to load background image");
        }
    }

    //---------------------------------------------------------------
    // Private method
    //---------------------------------------------------------------
    /**
     * Load an image from a zip input stream
     *
     * @param fs the file system
     * @param images the name of the zip file that contains images
     * @param background the name of the background image file
     * @return the image
     * @throws IOException if an error occurs while reading the zip input
     * streamm
     */
    private Image loadBackground(FileSystem fs, String images, String background) throws IOException {
        ImagesLoader loader = new ImagesLoader(new AndroidImageFactory());
        try (InputStream in = fs.getInputStream(images)) {
            if (in != null) {
                Map<String, Image> imageLibrary = loader.loadImages(new ZipInputStream(in));
                return imageLibrary.get(background);
            }
        }
        return null;
    }

    /**
     * Draw the image in the imageView.
     *
     * @param image the image
     */
    public void drawBackground(Image image) {
        if (image instanceof AndroidImage) {
            imageView.setImageBitmap(((AndroidImage) image).getBitmap());
        }
    }

}
