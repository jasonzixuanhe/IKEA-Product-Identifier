package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a furniture having a name(swedish), article number, price(CAD), and slid(sales location ID)
public class Furniture implements Writable {
    private String name;
    private String artNum;
    private Double cad;
    private String slid;

    //REQUIRES: articleNumber to be 8 digit input, location to be a 6 digit input
    public Furniture(String furnitureName,String articleNumber,String price, String location) {
        name = furnitureName;
        // 8 digit int
        artNum = articleNumber;
        // 6 digit int
        slid = location;

        // Any invalid inputs for price will be defaulted to 0
        try {
            cad = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            cad = 0.0;
        }

        // No negative price for furniture
        if (cad < 0.0) {
            cad = 0.0;
        }

    }

    //EFFECTS: returns the name of the furniture
    public String getFurnitureName() {
        return name;
    }

    //EFFECTS: returns the article number of the furniture
    public String getArticleNumber() {
        return artNum;
    }

    //EFFECTS: returns the price in CAD of the furniture
    public double getPrice() {
        return cad;
    }

    //EFFECTS: returns the slid of the furniture
    public String getLocation() {
        return slid;
    }

    //MODIFIES: this
    //EFFECTS: changes the slid location to new location specified in parameter
    public void changeLocation(String location) {
        if (checkSlidIsSixDigit(location)) {
            slid = location;
        } else {
            System.out.println("Error! Please input a six digit location!");
        }
    }

    //EFFECTS: return false if num is not six, true otherwise
    public boolean checkSlidIsSixDigit(String num) {
        int length = num.length();
        return length == 6;
    }

    //EFFECTS: returns false if num is not eight digits, true otherwise
    public boolean checkArtNumIsEightDigit(String num) {
        int length = num.length();
        return length == 8;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("article number", artNum);
        json.put("price", cad);
        json.put("location", slid);
        return json;
    }
}
