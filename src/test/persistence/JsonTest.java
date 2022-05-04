package persistence;

import model.Furniture;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkFurniture(String name, String artNum, Double price, String location, Furniture furniture) {
        assertEquals(name, furniture.getFurnitureName());
        assertEquals(artNum, furniture.getArticleNumber());
        assertEquals(price, furniture.getPrice());
        assertEquals(location, furniture.getLocation());
    }
}
