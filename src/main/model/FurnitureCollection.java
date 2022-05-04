package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FurnitureCollection implements Writable {
    private List<Furniture> data;
    private String collectionName;

    public FurnitureCollection(String name) {
        collectionName = name;
        data = new ArrayList<>();
    }

    //EFFECTS: gets the collection name
    public String  getCollectionName() {
        return collectionName;
    }

    //EFFECTS: adds furniture to FurnitureCollection list
    public void addFurniture(Furniture furniture) {
        data.add(furniture);
    }

    //EFFECTS: prints all furniture in list
    public void printFurnitureInList() {
        for (int i = 0; i < data.size(); i++) {
            System.out.println(data.get(i).getFurnitureName());
        }
    }

    //REQUIRES: input String must have specific furniture name exist
    //EFFECTS: returns the Furniture in list which matches provided String input
    public Furniture findFurnitureInList(String input) {
        for (Furniture furniture : data) {
            if (input.equals(furniture.getFurnitureName())) {
                return furniture;
            }
        }
        return null;
    }

    //EFFECTS: returns true if a furniture name matches with specified input, otherwise produce false
    public boolean isFurnitureInList(String input) {
        for (Furniture furniture : data) {
            if (input.equals(furniture.getFurnitureName())) {
                return true;
            }
        }
        return false;
    }

    //EFFECTS: returns true if list is empty, false otherwise
    public boolean isEmpty() {
        return data.isEmpty();
    }

    //EFFECTS: returns true if list contains specified furniture, false otherwise
    public boolean contains(Furniture furniture) {
        return data.contains(furniture);
    }

    //EFFECTS: returns int size of list
    public int getSize() {
        return data.size();
    }

    //EFFECTS: remove a Furniture from the collection
    public void removeFurniture(Furniture furniture) {
        data.remove(furniture);
    }

    //EFFECTS: gets a Furniture from the collection
    public List<Furniture> getFurniture() {
        return Collections.unmodifiableList(data);
    }

    //EFFECTS: gets furniture at index i of list
    public Furniture getFurnitureAtIndex(int i) {
        return data.get(i);
    }


    //EFFECTS: Creates new JSONObject using collection name
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", collectionName);
        json.put("furniture", furnitureToJson());
        return json;
    }

    // EFFECTS: returns things in this collection as a JSON array
    private JSONArray furnitureToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Furniture furniture : data) {
            jsonArray.put(furniture.toJson());
        }

        return jsonArray;
    }
}
