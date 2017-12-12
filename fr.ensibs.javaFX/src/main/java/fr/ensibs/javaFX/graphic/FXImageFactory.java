package fr.ensibs.javaFX.graphic;

import fr.ensibs.graphic.ImageFactory;
import java.io.InputStream;
import javafx.scene.image.Image;

/**
 * A factory to make images for tha JavaX graphic system
 *
 * @author Pascale Launay
 * @version 2
 */
public class FXImageFactory implements ImageFactory {

    //---------------------------------------------------------------
    // Factory method
    //---------------------------------------------------------------
    @Override
    public FXImage makeImage(String name, InputStream in) {
        Image image = new Image(in);
        return new FXImage(name, image);
    }
}
