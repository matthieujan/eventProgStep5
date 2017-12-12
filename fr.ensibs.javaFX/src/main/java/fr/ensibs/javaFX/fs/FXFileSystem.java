package fr.ensibs.javaFX.fs;

import fr.ensibs.fs.FileSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A file system for a JavaFX application. Look for resources first in the file
 * system and then in the application context (jar file). The base path is the
 * user home directory.
 *
 * @author Pascale Launay
 * @version 2
 */
public class FXFileSystem implements FileSystem {

    @Override
    public InputStream getInputStream(String path) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            return new FileInputStream(file);
        }
        return FXFileSystem.class.getResourceAsStream("/" + path);
    }

    @Override
    public String getBasePath() {
        return System.getProperty("user.home");
    }
}
