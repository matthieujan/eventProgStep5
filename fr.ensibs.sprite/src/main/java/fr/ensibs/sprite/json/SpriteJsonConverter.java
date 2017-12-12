package fr.ensibs.sprite.json;

import fr.ensibs.graphic.Image;
import fr.ensibs.sprite.Sprite;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import fr.ensibs.json.JsonConverter;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Class to read/write {@link Sprite} instance from/to a JSON input/output
 * stream
 *
 * @author Pascale Launay
 * @version 2
 */
public class SpriteJsonConverter implements JsonConverter<Sprite> {

    private final Map<String, Image> images;  // list of images to make sprites 

    //--------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------
    /**
     * Constructor
     *
     * @param images list of images to make sprites
     */
    public SpriteJsonConverter(Map<String, Image> images) {
        this.images = images;
    }

    /**
     * Constructor
     *
     * @param images list of images to make sprites
     */
    public SpriteJsonConverter(List<Image> images) {
        this.images = new HashMap<>();
        for (Image image : images) {
            this.images.put(image.getName(), image);
        }
    }

    //--------------------------------------------------------------
    // JsonFactory methods
    //--------------------------------------------------------------
    @Override
    public Sprite fromJson(JSONObject json) throws Exception {
        String name = json.getString("name");
        long duration = json.getLong("duration");
        Sprite sprite = new Sprite(name, duration);
        if (json.has("images")) {
            JSONArray array = json.getJSONArray("images");
            for (int i = 0; i < array.length(); i++) {
                String imageName = array.getString(i);
                Image image = images.get(imageName);
                if (image != null) {
                    sprite.addImage(image);
                } else {
                    throw new Exception("No image " + imageName);
                }
            }
        }
        return sprite;
    }

    @Override
    public JSONObject toJson(Sprite sprite) {
        JSONObject json = new JSONObject();
        json.append("name", sprite.getName());
        json.append("duration", sprite.getDuration());
        List<Image> images = sprite.getImages();
        if (!images.isEmpty()) {
            JSONArray array = new JSONArray();
            for (Image image : images) {
                array.put(image.getName());
            }
            json.append("images", array);
        }
        return json;
    }
}
