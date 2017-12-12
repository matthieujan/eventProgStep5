package fr.ensibs.conf;

import fr.ensibs.fs.FileSystem;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The configuration parameters of an application. Loads properties from a file
 * containing lines in the form key=value.
 *
 * @author Pascale Launay
 * @version 3
 */
public class Configuration {

    private final Properties properties;   // configuration properties

    //---------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------
    /**
     * Constructor
     *
     * @param fs a file system to retrieve the configuration properties
     * @param defaultPath the path to the default configuration properties
     */
    public Configuration(FileSystem fs, String defaultPath) {
        this.properties = new Properties();
        InputStream in;
        try {
            in = fs.getInputStream(defaultPath);
            this.properties.load(in);
        } catch (IOException e) {
            System.err.println("Cannot load default properties: " + e.getClass().getName());
        }
    }

    //---------------------------------------------------------------
    // Getters
    //---------------------------------------------------------------
    /**
     * Give a string property
     *
     * @param name the name of the property
     * @return the string value of the property
     */
    public String get(String name) {
        return properties.getProperty(name);
    }

    /**
     * Give an integer property
     *
     * @param name the name of the property
     * @return the integer value of the property
     */
    public int getInt(String name) {
        return Integer.parseInt(get(name));
    }

    /**
     * Give a long integer property
     *
     * @param name the name of the property
     * @return the long integer value of the property
     */
    public long getLong(String name) {
        return Integer.parseInt(get(name));
    }
}
