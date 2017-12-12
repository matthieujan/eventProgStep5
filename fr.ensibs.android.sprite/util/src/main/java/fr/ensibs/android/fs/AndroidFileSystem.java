package fr.ensibs.android.fs;

import android.content.Context;
import android.content.res.Resources;
import fr.ensibs.fs.FileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A file system implemetation for Android platforms
 *
 * @author Pascale Launay
 * @version 3
 */
public class AndroidFileSystem implements FileSystem {

    private final Context context;  // the android application context

    //---------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------
    /**
     * Constructor
     *
     * @param context the android application context
     */
    public AndroidFileSystem(Context context) {
        this.context = context;
    }

    //---------------------------------------------------------------
    // FileSystem methods implementation
    //---------------------------------------------------------------
    @Override
    public String getBasePath() {
        return context.getExternalFilesDir(null).getPath();
    }

    @Override
    public InputStream getInputStream(String path) throws IOException {
        // look for the resource in the file system
        File file = new File(path);
        if (file.exists()) {
            return new FileInputStream(file);
        }
        Resources resources = context.getResources();

        // look for the file in the raw folder
        int rawId = resources.getIdentifier(path, "raw", context.getPackageName());
        if (rawId > 0) {
            return resources.openRawResource(rawId);
        }

        return null;
    }
}
