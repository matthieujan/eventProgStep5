package fr.ensibs.sprite.json;

import fr.ensibs.graphic.Image;
import fr.ensibs.sprite.Stage;
import fr.ensibs.json.JsonConverter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/**
 * Class to read/write {@link Stage} instance from/to a JSON input/output stream
 *
 * @author Pascale Launay
 * @version 2
 */
public class StageJsonConverter implements JsonConverter<Stage> {

    private final Map<String, Image> images;  // list of images to make sprites 

    //--------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------
    /**
     * Constructor
     *
     * @param images list of images to make sprites
     */
    public StageJsonConverter(Map<String, Image> images) {
        this.images = images;
    }

    /**
     * Constructor
     *
     * @param images list of images to make sprites
     */
    public StageJsonConverter(List<Image> images) {
        this.images = new HashMap<>();
        for (Image image : images) {
            this.images.put(image.getName(), image);
        }
    }

    //--------------------------------------------------------------
    // JsonFactory methods
    //--------------------------------------------------------------
    @Override
    public Stage fromJson(JSONObject json) throws Exception {
        int width = json.getInt("width");
        int height = json.getInt("height");
        Stage stage = new Stage(width, height);
        if (json.has("background")) {
            String imageName = json.getString("background");
            Image background = images.get(imageName);
            if (background != null) {
                stage.setBackground(background);
            } else {
                throw new Exception("No image " + imageName);
            }
        }
        return stage;
    }

    @Override
    public JSONObject toJson(Stage stage) throws Exception {
        Image background = stage.getBackground();
        int width = stage.getWidth();
        int height = stage.getHeight();
        JSONObject json = new JSONObject();
        if (background != null) {
            json.put("background", background);
        }
        json.put("width", width);
        json.put("height", height);
        return json;
    }

}
