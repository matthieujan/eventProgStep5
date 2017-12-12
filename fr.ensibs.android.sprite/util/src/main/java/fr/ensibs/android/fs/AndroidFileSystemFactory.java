package fr.ensibs.android.fs;

import android.content.Context;
import fr.ensibs.fs.FileSystem;
import fr.ensibs.fs.FileSystemFactory;

/**
 * A factory to create file systems for Android platforms
 *
 * @author Pascale Launay
 * @version 3
 */
public class AndroidFileSystemFactory implements FileSystemFactory {

    private final Context context; // the application context

    //---------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------
    /**
     * Constructor
     *
     * @param context the application context
     */
    public AndroidFileSystemFactory(Context context) {
        this.context = context;
    }

    //---------------------------------------------------------------
    // FileSystemFactory method implementation
    //---------------------------------------------------------------
    @Override
    public FileSystem makeFileSystem() {
        return new AndroidFileSystem(context);
    }
}
