package fr.ensibs.sprite.json;

import fr.ensibs.graphic.Image;
import fr.ensibs.sprite.Sequence;
import fr.ensibs.sprite.Sprite;
import fr.ensibs.sprite.Stage;
import fr.ensibs.sprite.action.SpriteAction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import fr.ensibs.json.JsonConverter;

/**
 * Class to read/write {@link Sequence} instance from/to a JSON input/output
 * stream
 *
 * @author Pascale Launay
 * @version 2
 */
public class SequenceJsonConverter implements JsonConverter<Sequence> {

    private final Map<String, Image> images;    // list of images where the background image should be found
    private final Map<String, Sprite> sprites;  // list of sprites 
    private final StageJsonConverter stageJsonConverter;

    //--------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------
    /**
     * Constructor
     *
     * @param images images to make the background
     * @param sprites sprites
     */
    public SequenceJsonConverter(Map<String, Image> images, Map<String, Sprite> sprites) {
        this.images = images;
        this.sprites = sprites;
        this.stageJsonConverter = new StageJsonConverter(images);
    }

    /**
     * Constructor
     *
     * @param images list of images to make the background
     * @param sprites list of sprites
     */
    public SequenceJsonConverter(List<Image> images, List<Sprite> sprites) {
        this.images = new HashMap<>();
        for (Image image : images) {
            this.images.put(image.getName(), image);
        }
        this.sprites = new HashMap<>();
        for (Sprite sprite : sprites) {
            this.sprites.put(sprite.getName(), sprite);
        }
        this.stageJsonConverter = new StageJsonConverter(this.images);
    }

    //--------------------------------------------------------------
    // JsonFactory methods
    //--------------------------------------------------------------
    @Override
    public Sequence fromJson(JSONObject json) throws Exception {

        long duration = json.getLong("duration");
        JSONObject stageObject = json.getJSONObject("stage");
        Stage stage = stageJsonConverter.fromJson(stageObject);
        Sequence sequence = new Sequence(duration, stage);

        if (json.has("sprites")) {
            JSONArray spritesArray = json.getJSONArray("sprites");
            for (int i = 0; i < spritesArray.length(); i++) {
                String spriteName = spritesArray.getString(i);
                Sprite sprite = sprites.get(spriteName);
                if (sprite != null) {
                    sequence.addSprite(sprite);
                } else {
                    throw new Exception("No sprite " + spriteName);
                }
            }
            if (json.has("actions")) {
                List<Sprite> sprites = sequence.getSprites();
                SpriteActionJsonConverter actionJsonConverter = new SpriteActionJsonConverter(sprites);
                JSONArray actionsArray = json.getJSONArray("actions");
                for (int i = 0; i < actionsArray.length(); i++) {
                    JSONObject actionObject = actionsArray.getJSONObject(i);
                    SpriteAction action = actionJsonConverter.fromJson(actionObject);
                    sequence.addAction(action);
                }
            }
        }

        return sequence;
    }

    @Override
    public JSONObject toJson(Sequence sequence) throws Exception {
        JSONObject json = new JSONObject();

        json.append("duration", sequence.getDuration());
        json.append("width", sequence.getWidth());
        json.append("height", sequence.getHeight());

        Stage stage = sequence.getStage();
        json.append("stage", stageJsonConverter.toJson(stage));

        List<Sprite> sprites = sequence.getSprites();
        if (!sprites.isEmpty()) {
            JSONArray spritesArray = new JSONArray();
            for (Sprite sprite : sprites) {
                spritesArray.put(sprite.getName());
            }
            json.put("sprites", sprites);

            List<SpriteAction> actions = sequence.getActions();
            if (!actions.isEmpty()) {
                SpriteActionJsonConverter actionFactory = new SpriteActionJsonConverter(sprites);
                JSONArray actionsArray = new JSONArray();
                for (SpriteAction action : actions) {
                    actionsArray.put(actionFactory.toJson(action));
                }
                json.put("actions", sprites);
            }
        }

        return json;
    }
}
