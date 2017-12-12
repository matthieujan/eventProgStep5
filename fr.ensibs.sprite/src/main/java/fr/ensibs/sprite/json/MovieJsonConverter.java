package fr.ensibs.sprite.json;

import fr.ensibs.graphic.Image;
import fr.ensibs.sprite.Movie;
import fr.ensibs.sprite.Sequence;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import fr.ensibs.json.JsonConverter;
import fr.ensibs.sprite.Sprite;
import java.util.HashMap;

/**
 * Class to read/write {@link Movie} instance from/to a JSON input/output stream
 *
 * @author Pascale Launay
 * @version 2
 */
public class MovieJsonConverter implements JsonConverter<Movie> {

    private final Map<String, Image> images;
    private final SpriteJsonConverter spriteJsonConverter;

    //--------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------
    /**
     * Constructor
     *
     * @param images list of images to make sprites
     */
    public MovieJsonConverter(Map<String, Image> images) {
        this.images = images;
        this.spriteJsonConverter = new SpriteJsonConverter(images);
    }

    /**
     * Constructor
     *
     * @param images list of images to make sprites
     */
    public MovieJsonConverter(List<Image> images) {
        this.images = new HashMap<>();
        for (Image image : images) {
            this.images.put(image.getName(), image);
        }
        this.spriteJsonConverter = new SpriteJsonConverter(images);
    }

    //--------------------------------------------------------------
    // JsonFactory methods
    //--------------------------------------------------------------
    @Override
    public Movie fromJson(JSONObject json) throws Exception {
        Movie movie = new Movie();
        Map<String, Sprite> sprites = new HashMap<>();
        if (json.has("sprites")) {
            JSONArray array = json.getJSONArray("sprites");
            for (int i = 0; i < array.length(); i++) {
                JSONObject spriteObject = array.getJSONObject(i);
                Sprite sprite = spriteJsonConverter.fromJson(spriteObject);
                sprites.put(sprite.getName(), sprite);
            }
        }
        SequenceJsonConverter sequenceJsonConverter = new SequenceJsonConverter(images, sprites);
        if (json.has("sequences")) {
            JSONArray array = json.getJSONArray("sequences");
            for (int i = 0; i < array.length(); i++) {
                JSONObject sequenceObject = array.getJSONObject(i);
                Sequence sequence = sequenceJsonConverter.fromJson(sequenceObject);
                movie.addSequence(sequence);
            }
        }
        return movie;
    }

    @Override
    public JSONObject toJson(Movie movie) throws Exception {
        JSONObject json = new JSONObject();
        List<Sequence> sequences = movie.getSequences();
        if (!sequences.isEmpty()) {
            Map<String, Sprite> sprites = new HashMap<>();
            JSONArray spritesArray = new JSONArray();
            for (Sequence sequence : sequences) {
                for (Sprite sprite : sequence.getSprites()) {
                    if (!sprites.containsKey(sprite.getName())) {
                        JSONObject spriteObject = spriteJsonConverter.toJson(sprite);
                        spritesArray.put(spriteObject);
                        sprites.put(sprite.getName(), sprite);
                    }
                }
            }
            json.put("sprites", spritesArray);
            SequenceJsonConverter sequenceJsonConverter = new SequenceJsonConverter(images, sprites);
            JSONArray sequencesArray = new JSONArray();
            for (Sequence sequence : sequences) {
                JSONObject sequenceObject = sequenceJsonConverter.toJson(sequence);
                sequencesArray.put(sequenceObject);
            }
            json.put("sequences", sequencesArray);
        }
        return json;
    }

}
