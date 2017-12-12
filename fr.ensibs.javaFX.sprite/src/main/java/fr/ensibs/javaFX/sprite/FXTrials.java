package fr.ensibs.javaFX.sprite;

import fr.ensibs.conf.Configuration;
import fr.ensibs.fs.FileSystem;
import fr.ensibs.graphic.Image;
import fr.ensibs.graphic.ImagesLoader;
import fr.ensibs.javaFX.graphic.FXImage;
import fr.ensibs.javaFX.graphic.FXImageFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.zip.ZipInputStream;
import javafx.scene.canvas.Canvas;

/**
 * A class with operations defined for trial purposes
 *
 * @author Pascale Launay
 * @version 4
 */
public class FXTrials {

    private final FileSystem fileSystem;         // the file system
    private final Canvas canvas;                 // graphic object
    private final Configuration configuration;   // the configuration properties

    //---------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------
    /**
     * Constructor
     *
     * @param fileSystem the file system
     * @param canvas graphic object
     * @param configuration the configuration properties
     */
    public FXTrials(FileSystem fileSystem, Canvas canvas, Configuration configuration) {
        this.fileSystem = fileSystem;
        this.canvas = canvas;
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
            Map<String, Image> imagesLibrary = loadImages(fileSystem, images);
            drawImage(imagesLibrary.get(background));
        } catch (IOException e) {
            System.err.println("Unable to load images");
        }
    }

    //---------------------------------------------------------------
    // Private methods
    //---------------------------------------------------------------
    /**
     * Load the image library from the zip file represented by the images
     * property
     *
     * @return image property
     * @throws IOException if an error occurs while loading the images
     */
    private Map<String, Image> loadImages(FileSystem fs, String images) throws IOException {
        ImagesLoader loader = new ImagesLoader(new FXImageFactory());
        try (InputStream in = fs.getInputStream(images)) {
            if (in != null) {
                return loader.loadImages(new ZipInputStream(in));
            }
        }
        return null;
    }

    /**
     * Draw an image in the main {@link Canvas} that fits its dimensions
     *
     * @param image the image to be drawn
     */
    private void drawImage(Image image) {

        // compute the size ratio (canvas/image) as the minimum (width ratio, height ratio)
        double widthRatio = 1.0 * canvas.getWidth() / image.getWidth();
        double heightRatio = 1.0 * canvas.getHeight() / image.getHeight();
        double ratio = Math.min(widthRatio, heightRatio);

        // compute the rectangle where the image will be drawn
        int width = (int) Math.round(image.getWidth() * ratio);
        int height = (int) Math.round(image.getHeight() * ratio);
        int x = (int) Math.round((canvas.getWidth() - width) / 2);
        int y = (int) Math.round((canvas.getHeight() - height) / 2);

        // draw the image
        javafx.scene.image.Image img = ((FXImage) image).getImage();
        canvas.getGraphicsContext2D().drawImage(img, x, y, width, height);
    }
}
