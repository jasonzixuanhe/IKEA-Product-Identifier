package persistence;

import model.Furniture;
import model.FurnitureCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads FurnitureCollection from file and returns it;
    // throws IOException if an error occurs reading data from file
    public FurnitureCollection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseFurnitureCollection(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses FurnitureCollection from JSON object and returns it
    private FurnitureCollection parseFurnitureCollection(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        FurnitureCollection fc = new FurnitureCollection(name);
        addFurnitures(fc, jsonObject);
        return fc;
    }

    // MODIFIES: fc
    // EFFECTS: parses Furnitures from JSON object and adds them to FurnitureCollection
    private void addFurnitures(FurnitureCollection fc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("furniture");
        for (Object json : jsonArray) {
            JSONObject nextFurniture = (JSONObject) json;
            addFurniture(fc, nextFurniture);
        }
    }

    // MODIFIES: fc
    // EFFECTS: parses thingy from JSON object and adds it to FurnitureCollection
    private void addFurniture(FurnitureCollection fc, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String artNum = jsonObject.getString("article number");
        double priceDouble = jsonObject.getDouble("price");
        String price = Double.toString(priceDouble);
        String location = jsonObject.getString("location");
        Furniture furniture = new Furniture(name, artNum, price, location);
        fc.addFurniture(furniture);
    }
}
