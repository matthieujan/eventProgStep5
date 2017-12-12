package fr.ensibs.json;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Generic class to read/write objects from/to a JSON input/output stream
 *
 * @param <T> the type of the objects to be read/written
 *
 * @author Pascale Launay
 * @version 1
 */
public class JsonReader<T> {

    private final JsonConverter<T> factory; // to make objects

    //--------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------
    /**
     * Constructor
     *
     * @param factory to make objects
     */
    public JsonReader(JsonConverter<T> factory) {
        this.factory = factory;
    }

    //--------------------------------------------------------------
    // Read/write methods
    //--------------------------------------------------------------
    /**
     * Read an instance of T from a JSON input stream
     *
     * @param in JSON input stream
     * @return an instance of T
     * @throws Exception if an error occurs while reading from the input stream
     * or while making the object
     */
    public T readJson(InputStream in) throws Exception {
        BufferedInputStream bin = new BufferedInputStream(in);
        JSONTokener token = new JSONTokener(toString(bin));
        JSONObject json = new JSONObject(token);
        return factory.fromJson(json);
    }

    /**
     * Write an instance of T to a JSON output stream
     *
     * @param out JSON output stream
     * @param obj an instance of T
     * @throws Exception if an error occurs while writing to the output stream
     * or while making the object
     */
    public void writeJson(OutputStream out, T obj) throws Exception {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        JSONObject json = factory.toJson(obj);
        writer.write(json.toString(4) + "\n");
        writer.flush();
    }

    //--------------------------------------------------------------
    // Private methods
    //--------------------------------------------------------------
    /**
     * Return a string representation of the bytes in the input stream
     *
     * @param in input stream
     * @return tring representation of the bytes in the input stream
     * @throws IOException if an error occurs while reading from the input
     * stream
     */
    private String toString(BufferedInputStream in) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int b = in.read();
        while (b != -1) {
            buffer.write((byte) b);
            b = in.read();
        }
        return buffer.toString();
    }
}
