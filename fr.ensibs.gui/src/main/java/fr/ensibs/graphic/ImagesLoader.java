package fr.ensibs.graphic;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Class used to load images from zip files.
 *
 * @author Pascale Launay
 * @version 3
 */
public class ImagesLoader {

    private final ImageFactory imageFactory;   // to make images from the ZIP entries

    /**
     * Constructor
     *
     * @param imageFactory to make images from the ZIP entries
     */
    public ImagesLoader(ImageFactory imageFactory) {
        this.imageFactory = imageFactory;
    }

    /**
     * Load the images from a zip input stream
     *
     * @param in zip input stream from which images are loaded
     * @return the images (keys: images names)
     * @throws IOException error while accessing the input stream
     */
    public Map<String, Image> loadImages(ZipInputStream in) throws IOException {
        Map<String, Image> images = new HashMap<>();
        ZipEntry entry = in.getNextEntry();
        while (entry != null) {
            String name = entry.getName();
            Image image = imageFactory.makeImage(name, in);
            if (image != null) {
                images.put(name, image);
            }
            entry = in.getNextEntry();
        }
        return images;
    }
}
