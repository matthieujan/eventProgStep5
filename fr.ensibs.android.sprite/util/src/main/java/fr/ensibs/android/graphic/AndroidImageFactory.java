package fr.ensibs.android.graphic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import fr.ensibs.graphic.Image;
import fr.ensibs.graphic.ImageFactory;

import java.io.InputStream;

/**
 * A factory to make images for the Android graphic system
 *
 * @author Pascale Launay
 * @version 3
 */
public class AndroidImageFactory implements ImageFactory {

    //---------------------------------------------------------------
    // ImageFactory method implementation
    //---------------------------------------------------------------
    @Override
    public Image makeImage(String name, InputStream in) {
        Bitmap bitmap = BitmapFactory.decodeStream(in);
        return new AndroidImage(name, bitmap);
    }
}
