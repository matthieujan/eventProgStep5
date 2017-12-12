package fr.ensibs.sprite.json;

import fr.ensibs.sprite.Movie;
import fr.ensibs.sprite.ResourceSizeException;
import fr.ensibs.sprite.Sequence;
import fr.ensibs.sprite.Sprite;
import fr.ensibs.sprite.Stage;
import fr.ensibs.sprite.action.MotionAction;
import fr.ensibs.sprite.action.VisibilityAction;
import fr.ensibs.graphic.Image;
import fr.ensibs.json.JsonReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Class to test the fromJson method of the {@link MovieJsonConverter} class
 *
 * @author Pascale Launay
 * @version 2
 */
public class MovieJsonConverterTest {

    private static final String[] IMAGES = {
        "background-0.png", "background-1.png", "bird-01.png", "bird-02.png",
        "bird-03.png", "bird-04.png", "bird-05.png", "bird-06.png",
        "bird-07.png", "bird-08.png", "bird-09.png", "bird-10.png",
        "bird-11.png", "bird-12.png", "bird-13.png", "tux.png",
        "deer-01.png", "deer-02.png", "deer-03.png", "deer-04.png",
        "deer-05.png", "deer-06.png", "deer-07.png"
    };

    /**
     * Test of fromJson method, of class MovieJsonConverter.
     *
     * @throws ResourceSizeException exception while making the movie
     */
    @Test
    public void testFromJson() throws ResourceSizeException {
        List<Image> images = new ArrayList<>();
        for (String name : IMAGES) {
            images.add(new TestImage(name));
        }
        MovieJsonConverter instance = new MovieJsonConverter(images);
        JsonReader<Movie> reader = new JsonReader<>(instance);

        // make the expected movie object
        Movie expected = makeMovie();

        // read a correct JSON file
        try (InputStream in = MovieJsonConverterTest.class.getResourceAsStream("/movie-test.json")) {
            Movie movie = reader.readJson(in);
            assertNotNull("Movie is null", movie);
            assertEquals(expected, movie);
        } catch (Exception ex) {
            fail("Exception while reading the movie movie.json: " + ex.getClass().getName()
                    + " (" + ex.getMessage() + ")");
        }

        // read a erroneous JSON file
        try (InputStream in = MovieJsonConverterTest.class.getResourceAsStream("/movie-err.json")) {
            reader.readJson(in);
            fail("No exception while reading the movie movie-err.json");
        } catch (Exception ex) {
            // DO NOTHING
        }
    }

    private Movie makeMovie() throws ResourceSizeException {
        List<Image> birdImages = new ArrayList<>();
        List<Image> deerImages = new ArrayList<>();
        List<Image> tuxImages = new ArrayList<>();
        for (String name : IMAGES) {
            if (name.startsWith("bird")) {
                birdImages.add(new TestImage(name));
            } else if (name.startsWith("deer")) {
                deerImages.add(new TestImage(name));
            } else if (name.startsWith("tux")) {
                tuxImages.add(new TestImage(name));
            }
        }

        Sprite bird = new Sprite("bird", birdImages, 1500);
        Sprite tux = new Sprite("tux", tuxImages, 1);
        Sprite deer = new Sprite("deer", deerImages, 2000);

        Movie movie = new Movie();
        Stage stage = new Stage(new TestImage("background-0.png"), 1024, 768);
        Sequence sequence = new Sequence(15000, stage);
        sequence.addSprite(bird);
        sequence.addAction(new VisibilityAction(bird, 0, true, 0, 120));
        sequence.addAction(new MotionAction(bird, 500, 7500, 0, 120, 420, 120));
        movie.addSequence(sequence);
        stage = new Stage(new TestImage("background-1.png"), 1024, 768);
        sequence = new Sequence(15000, stage);
        sequence.addSprite(tux);
        sequence.addSprite(deer);
        sequence.addAction(new VisibilityAction(tux, 0, true, 443, 476));
        movie.addSequence(sequence);
        return movie;
    }

    class TestImage implements Image {

        private String name;

        public TestImage(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public int getWidth() {
            return 0;
        }

        @Override
        public int getHeight() {
            return 0;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 53 * hash + Objects.hashCode(this.name);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof TestImage && hashCode() == obj.hashCode();
        }
    }
}
