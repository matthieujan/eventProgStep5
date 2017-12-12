package fr.ensibs.javaFX.fs;

import fr.ensibs.fs.FileSystem;
import fr.ensibs.fs.FileSystemFactory;

/**
 * A factory to make {@link FileSystem} instances for JavaFX
 *
 * @author Pascale Launay
 * @version 2
 */
public class FXFileSystemFactory implements FileSystemFactory {

    @Override
    public FileSystem makeFileSystem() {
        return new FXFileSystem();
    }
}
