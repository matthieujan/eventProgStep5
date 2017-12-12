package fr.ensibs.json;

import org.json.JSONObject;

/**
 * Generic class to make objects from JSON objects (and vice versa)
 *
 * @param <T> the type of the objects to be made
 *
 * @author Pascale Launay
 * @version 1
 */
public interface JsonConverter<T> {

    //--------------------------------------------------------------
    // Protected abstract methods to be redefined
    //--------------------------------------------------------------
    /**
     * Give an instance of T from a JSON obejct
     *
     * @param json a JSON obejct
     * @return an instance of T
     * @throws Exception if an error occurs while making the object
     */
    public T fromJson(JSONObject json) throws Exception;

    /**
     * Give a JSON object from an instance of T
     *
     * @param obj an instance of T
     * @return a JSON object
     * @throws Exception if an error occurs while making the object
     */
    public JSONObject toJson(T obj) throws Exception;
}
