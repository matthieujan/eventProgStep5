package fr.ensibs.fs;

import java.io.IOException;
import java.io.InputStream;

/**
 * Represents a file system to acces input stream
 *
 * @author Pascale Launay
 * @version 2
 */
public interface FileSystem {

    /**
     * Give the base path of the file system
     *
     * @return the base path of the file system
     */
    public String getBasePath();

    /**
     * Give an input stream from its path name
     *
     * @param path the path name
     * @return the input stream
     * @throws IOException the input stream cannot be found
     */
    public InputStream getInputStream(String path) throws IOException;
}
