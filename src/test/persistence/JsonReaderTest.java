package persistence;

import model.FurnitureCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
//test
import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            FurnitureCollection fc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expected result
        }
    }

    @Test
    void testReaderEmptyFurnitureCollection() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyFurnitureCollection.json");
        try {
            FurnitureCollection fc = reader.read();
            assertEquals("Beds", fc.getCollectionName());
            assertEquals(0, fc.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
