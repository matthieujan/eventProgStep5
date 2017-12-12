package fr.ensibs.fs;

/**
 * A factory to make a file system
 *
 * @author Pascale Launay
 * @version 2
 */
public interface FileSystemFactory {

    /**
     * Make a file system
     *
     * @return a file system
     */
    public FileSystem makeFileSystem();
}
