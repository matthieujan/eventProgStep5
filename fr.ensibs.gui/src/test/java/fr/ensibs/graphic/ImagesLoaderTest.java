package fr.ensibs.graphic;

import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipInputStream;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A test for the {@link ImagesLoader} class
 *
 * @author Pascale Launay
 * @version 3
 */
public class ImagesLoaderTest {

    private static final String[] IMAGES = {
        "background-0.png", "bird-01.png", "tux.png"
    };

    /**
     * Test of loadImages method, of class ImagesLoader.
     */
    @Test
    public void testLoadImages() throws Exception {
        ZipInputStream in = new ZipInputStream(ImagesLoaderTest.class.getResourceAsStream("/images.zip"));
        TestImageFactory imageFactory = new TestImageFactory();
        ImagesLoader instance = new ImagesLoader(imageFactory);
        Map<String, Image> result = instance.loadImages(in);
        assertEquals("The number of images loaded is wrong", IMAGES.length, result.size());
        for (String name : IMAGES) {
            Image image = result.get(name);
            assertNotNull("Image " + name + " not found", image);
            assertEquals("The image " + name + " has the wrong name", name, image.getName());
        }
    }

    class TestImageFactory implements ImageFactory {

        @Override
        public TestImage makeImage(String name, InputStream in) {
            return new TestImage(name);
        }
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
