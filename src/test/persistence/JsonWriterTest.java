package persistence;

import model.Furniture;
import model.FurnitureCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            FurnitureCollection fc = new FurnitureCollection("Beds");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // expected result
        }
    }

    @Test
    void testWriterEmptyFurnitureCollection() {
        try {
            FurnitureCollection fc = new FurnitureCollection("Nightstands");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyFurnitureCollection.json");
            writer.open();
            writer.write(fc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyFurnitureCollection.json");
            fc = reader.read();
            assertEquals("Nightstands", fc.getCollectionName());
            assertEquals(0, fc.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralFurnitureCollection() {
        try {
            FurnitureCollection fc = new FurnitureCollection("Beds");
            fc.addFurniture(new Furniture("Malm", "12345678", "9090", "090102"));
            fc.addFurniture(new Furniture("Brimnes", "13322233", "99.99", "101010"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralFurnitureCollection.json");
            writer.open();
            writer.write(fc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralFurnitureCollection.json");
            fc = reader.read();
            assertEquals("Beds", fc.getCollectionName());
            List<Furniture> furnitures = fc.getFurniture();
            assertEquals(2, furnitures.size());
            checkFurniture("Malm", "12345678", 9090.00, "090102", furnitures.get(0));
            checkFurniture("Brimnes", "13322233",99.99, "101010", furnitures.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
