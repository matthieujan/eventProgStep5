package fr.ensibs.sprite.json;

import fr.ensibs.sprite.Sprite;
import fr.ensibs.sprite.action.MotionAction;
import fr.ensibs.sprite.action.SpriteAction;
import fr.ensibs.sprite.action.VisibilityAction;
import fr.ensibs.json.JsonConverter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/**
 * Class to read/write {@link SpriteAction} instance from/to a JSON input/output
 * stream
 *
 * @author Pascale Launay
 * @version 2
 */
public class SpriteActionJsonConverter implements JsonConverter<SpriteAction> {

    private final Map<String, Sprite> sprites;  // list of sprites to make actions 

    //--------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------
    /**
     * Constructor
     *
     * @param sprites list of sprites to make actions
     */
    public SpriteActionJsonConverter(List<Sprite> sprites) {
        this.sprites = new HashMap<>();
        for (Sprite sprite : sprites) {
            this.sprites.put(sprite.getName(), sprite);
        }
    }

    /**
     * Constructor
     *
     * @param sprites list of sprites to make actions
     */
    public SpriteActionJsonConverter(Map<String, Sprite> sprites) {
        this.sprites = sprites;
    }

    //--------------------------------------------------------------
    // JsonFactory methods
    //--------------------------------------------------------------
    @Override
    public SpriteAction fromJson(JSONObject json) throws Exception {
        String spriteName = json.getString("sprite");
        Sprite sprite = sprites.get(spriteName);
        if (sprite == null) {
            throw new Exception("No sprite " + spriteName);
        }
        long time = json.getLong("time");

        if (json.has("visible")) {
            boolean visible = json.getBoolean("visible");
            int x = json.getInt("x");
            int y = json.getInt("y");
            return new VisibilityAction(sprite, time, visible, x, y);

        } else {
            long endTime = json.getInt("endTime");
            int startX = json.getInt("startX");
            int startY = json.getInt("startY");
            int endX = json.getInt("endX");
            int endY = json.getInt("endY");
            return new MotionAction(sprite, time, endTime, startX, startY, endX, endY);
        }
    }

    @Override
    public JSONObject toJson(SpriteAction action) throws Exception {
        JSONObject json = new JSONObject();
        Sprite sprite = action.getSprite();
        json.put("sprite", sprite.getName());
        json.put("time", action.getTime());

        if (action instanceof VisibilityAction) {
            VisibilityAction visibilityAction = (VisibilityAction) action;
            json.put("visible", visibilityAction.isVisible());
            json.put("x", visibilityAction.getX());
            json.put("y", visibilityAction.getY());

        } else if (action instanceof MotionAction) {
            MotionAction motionAction = (MotionAction) action;
            json.put("endTime", motionAction.getEndTime());
            json.put("startX", motionAction.getStartX());
            json.put("startY", motionAction.getStartY());
            json.put("endX", motionAction.getEndX());
            json.put("endY", motionAction.getEndY());
        }
        return json;
    }

}
