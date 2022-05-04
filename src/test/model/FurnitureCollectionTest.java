package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class FurnitureCollectionTest {
    private FurnitureCollection testFurnitureCollection;
    private FurnitureCollection testFurnitureCollection2;
    private Furniture testFurniture;
    private Furniture testFurniture1;
    private Furniture testFurniture2;
    private Furniture testFurniture3;

    @BeforeEach
    void runBefore() {
        testFurnitureCollection = new FurnitureCollection("test");
        testFurnitureCollection2 = new FurnitureCollection("test2");

        testFurniture1 = new Furniture("Lack55x55", "30449908", "9.99", "000301" );
        testFurniture2 = new Furniture("Kallax77x147", "80275887", "79.99", "000103");

        testFurniture = new Furniture("Lack55x55", "30449908", "9.99", "000301" );
        testFurniture3 = new Furniture("LuroyQueen","00160215","-50.00","040300");
        testFurnitureCollection.addFurniture(testFurniture);
    }

   @Test
    void testConstructorCollection() {
        assertEquals("test",testFurnitureCollection.getCollectionName());
        assertTrue(testFurnitureCollection2.isEmpty());
   }

   @Test
    void testAddFurniture() {
        assertTrue(testFurnitureCollection.contains(testFurniture));
   }

   @Test
    void testPrintFurnitureInList() {
       testFurnitureCollection.printFurnitureInList();
       //Check console print for "Lack55x55"
   }

   @Test
   void testFindFurnitureInList() {
        assertEquals(testFurniture,testFurnitureCollection.findFurnitureInList("Lack55x55"));
        assertNull(testFurnitureCollection.findFurnitureInList("Linnmon100x60"));
   }

   @Test
    void testIsFurnitureInList() {
        assertTrue(testFurnitureCollection.isFurnitureInList("Lack55x55"));
        assertFalse(testFurnitureCollection.isFurnitureInList("Kallax77x147"));
   }

   @Test
    void testGetSize() {
        assertEquals(1, testFurnitureCollection.getSize());
   }

   @Test
    void testRemoveFurniture() {
        testFurnitureCollection.removeFurniture(testFurniture);
        assertFalse(testFurnitureCollection.contains(testFurniture));
   }

    @Test
    void testConstructorFurniture() {
        assertEquals("Lack55x55", testFurniture1.getFurnitureName());
        assertEquals("30449908", testFurniture1.getArticleNumber());
        assertEquals(9.99, testFurniture1.getPrice());
        assertEquals("000301", testFurniture1.getLocation());
        assertEquals(0,testFurniture3.getPrice());
        Furniture f1 = new Furniture("test","999999999", "String","999999");
        assertEquals(0,f1.getPrice());
    }

    @Test
    void testGetFurnitureName() {
        assertEquals("Lack55x55", testFurniture1.getFurnitureName());
    }

    @Test
    void testGetArtNum() {
        assertEquals("30449908", testFurniture1.getArticleNumber());
    }

    @Test
    void testGetPrice() {
        assertEquals(9.99, testFurniture1.getPrice());
    }

    @Test
    void testGetLocation() {
        assertEquals("000301", testFurniture1.getLocation());
    }

    @Test
    void changeLocation() {
        testFurniture1.changeLocation("000302");
        testFurniture2.changeLocation("00010");
        assertEquals("000302", testFurniture1.getLocation());
        assertEquals("000103",testFurniture2.getLocation());
    }

    @Test
    void testCheckSlidIsSixDigit() {
        assertTrue(testFurniture1.checkSlidIsSixDigit(testFurniture1.getLocation()));
        assertFalse(testFurniture1.checkSlidIsSixDigit("00001"));
    }

    @Test
    void testCheckArtNumIsEightDigit() {
        assertTrue(testFurniture1.checkArtNumIsEightDigit("90124534"));
        assertFalse(testFurniture1.checkArtNumIsEightDigit("9012453"));
    }

    @Test
    void testGetFurnitureAtIndex() {
        assertEquals(testFurniture,testFurnitureCollection.getFurnitureAtIndex(0));
    }
}