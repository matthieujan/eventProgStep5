package fr.ensibs.json;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Objects;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * JUnit test for the {@link JsonReader} class
 *
 * @author Pascale Launay
 * @version 1
 */
public class JsonReaderTest {

    private static final TestConverter FACTORY = new TestConverter();

    private static final TestObject SIMPLE = new TestObject("simple", null);
    private static final TestObject COMPLEX = new TestObject("complex", SIMPLE);
    private static final JSONObject COMPLEX_JSON = new JSONObject();
    private static final JSONObject SIMPLE_JSON = new JSONObject();

    static {
        SIMPLE_JSON.put("name", "simple");
        COMPLEX_JSON.put("name", "complex");
        COMPLEX_JSON.put("ref", SIMPLE_JSON);
    }

    /**
     * Test of readJson method, of class JsonReader.
     */
    @Test
    public void testReadJson() {

        JsonReader<TestObject> instance = new JsonReader<>(FACTORY);

        // simple object
        InputStream in = JsonReaderTest.class.getResourceAsStream("/simple.json");
        try {
            assertEquals(SIMPLE, instance.readJson(in));
        } catch (Exception e) {
            fail("readJson didn't fail with /simple.json");
        }

        // compound object
        in = JsonReaderTest.class.getResourceAsStream("/complex.json");
        try {
            assertEquals(COMPLEX, instance.readJson(in));
        } catch (Exception e) {
            fail("readJson didn't fail with /complex.json");
        }

        // wrong object
        in = JsonReaderTest.class.getResourceAsStream("/err.json");
        try {
            TestObject obj = instance.readJson(in);
            fail("readJson didn't fail with /err.json");
        } catch (Exception e) {
            // success
        }
    }

    /**
     * Test of writeJson method, of class JsonReader.
     */
    @Test
    public void testWriteJson() {

        JsonReader<TestObject> instance = new JsonReader<>(FACTORY);

        // simple object
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            instance.writeJson(out, SIMPLE);
            try (InputStream in = new ByteArrayInputStream(out.toByteArray())) {
                assertEquals(SIMPLE, instance.readJson(in));
            } catch (Exception e) {
                fail("readJson didn't fail with /simple.json");
            }
        } catch (Exception e) {
            fail("writeJson didn't fail with " + SIMPLE);
        }
    }

    static class TestObject {

        private String name;
        private TestObject ref;

        public TestObject(String name, TestObject ref) {
            this.name = name;
            this.ref = ref;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof TestObject) {
                TestObject obj = (TestObject) o;
                return name.equals(name) && Objects.equals(ref, obj.ref);
            }
            return false;
        }

        @Override
        public String toString() {
            return "[" + name + (ref != null ? ", " + ref : "") + "]";
        }
    }

    static class TestConverter implements JsonConverter<TestObject> {

        @Override
        public TestObject fromJson(JSONObject json) throws Exception {
            String name = json.getString("name");
            TestObject ref = null;
            if (json.has("ref")) {
                ref = fromJson(json.getJSONObject("ref"));
            }
            return new TestObject(name, ref);
        }

        @Override
        public JSONObject toJson(TestObject obj) throws Exception {
            JSONObject json = new JSONObject();
            json.put("name", obj.name);
            if (obj.ref != null) {
                json.put("ref", toJson(obj.ref));
            }
            return json;
        }
    }
}
