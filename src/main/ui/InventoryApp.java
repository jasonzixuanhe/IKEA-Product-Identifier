package ui;

import model.Furniture;
import model.FurnitureCollection;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class InventoryApp {
    private static final String JSON_STORE_BEDS = "./data/inventoryBeds.json";
    private static final String JSON_STORE_NIGHTSTANDS = "./data/inventoryNightstands.json";
    private static final String JSON_STORE_SHELFUNITS = "./data/inventoryShelfUnits.json";
    private static final String JSON_STORE_SIDETABLES = "./data/inventorySideTables.json";
    private static final String JSON_STORE_OFFICEDESKS = "./data/inventoryOfficeDesks.json";
    private FurnitureCollection beds;
    private FurnitureCollection nightstands;
    private FurnitureCollection shelfUnits;
    private FurnitureCollection sideTables;
    private FurnitureCollection officeDesks;
    private Furniture checkValid;
    private Scanner input;
    private String errorMessage = "Selection not valid...";
    private JsonWriter jsonWriterBeds;
    private JsonReader jsonReaderBeds;
    private JsonWriter jsonWriterNightstands;
    private JsonReader jsonReaderNightstands;
    private JsonWriter jsonWriterShelfUnits;
    private JsonReader jsonReaderShelfUnits;
    private JsonWriter jsonWriterSideTables;
    private JsonReader jsonReaderSideTables;
    private JsonWriter jsonWriterOfficeDesks;
    private JsonReader jsonReaderOfficeDesks;


    //EFFECTS
    public InventoryApp() {
        jsonWriterBeds = new JsonWriter(JSON_STORE_BEDS);
        jsonReaderBeds = new JsonReader(JSON_STORE_BEDS);
        jsonWriterNightstands = new JsonWriter(JSON_STORE_NIGHTSTANDS);
        jsonReaderNightstands = new JsonReader(JSON_STORE_NIGHTSTANDS);
        jsonWriterShelfUnits = new JsonWriter(JSON_STORE_SHELFUNITS);
        jsonReaderShelfUnits = new JsonReader(JSON_STORE_SHELFUNITS);
        jsonWriterSideTables = new JsonWriter(JSON_STORE_SIDETABLES);
        jsonReaderSideTables = new JsonReader(JSON_STORE_SIDETABLES);
        jsonWriterOfficeDesks = new JsonWriter(JSON_STORE_OFFICEDESKS);
        jsonReaderOfficeDesks = new JsonReader(JSON_STORE_OFFICEDESKS);
        runInventoryApp();
    }

    //MODIFIES: this
    //EFFECTS: starts initializing inventory app
    private void runInventoryApp() {
        boolean keepGoing = true;
        String command;
        input = new Scanner(System.in);

        init();

        while (keepGoing) {
            displayMenu();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nEnjoy your shopping!");
    }

    //MODIFIES: this
    //EFFECTS: initializes accounts
    private void init() {
        checkValid = new Furniture("Skrova","90124534","20.00","040100");
        beds = new FurnitureCollection("Beds");
        nightstands = new FurnitureCollection("Nightstands");
        shelfUnits = new FurnitureCollection("Shelf Units");
        sideTables = new FurnitureCollection("Side Tables");
        officeDesks = new FurnitureCollection("Office Desks");
    }

    //EFFECTS: displays possible menu options for the user to select
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add new Furniture");
        System.out.println("\tr -> remove Furniture");
        System.out.println("\tp -> print available Furniture in a collection");
        System.out.println("\ti -> info on furniture in collection");
        System.out.println("\ts -> save collection to file");
        System.out.println("\tl -> load collection from file");
        System.out.println("\tq -> quit application");
    }

    //MODIFIES: this
    //EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            editAddFurniture();
        } else if (command.equals("r")) {
            editRemoveFurniture();
        } else if (command.equals("p")) {
            editPrintFurnitureInList();
        } else if (command.equals("i")) {
            editInfoOnFurnitureInCollection();
        } else if (command.equals("s")) {
            editSaveInventory();
        } else if (command.equals("l")) {
            editLoadInventory();
        } else if (command.equals("q")) {
            System.out.println("Good luck!");
        } else {
            System.out.println(errorMessage);
        }
    }

    //MODIFIES: this
    //EFFECTS: starts add furniture menu and processing commands from user
    private void editSaveInventory() {
        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            saveInventoryMenu();
            command = input.nextLine();
            command = command.toLowerCase();
            processCommandSave(command);
            keepGoing = false;
        }
    }

    //MODIFIES: this
    //EFFECTS: starts add furniture menu and processing commands from user
    private void editLoadInventory() {
        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            loadInventoryMenu();
            command = input.nextLine();
            command = command.toLowerCase();
            processCommandLoad(command);
            keepGoing = false;
        }
    }

    //MODIFIES: this
    //EFFECTS: starts add furniture menu and processing commands from user
    private void editAddFurniture() {
        boolean keepGoing = true;
        String command;
        
        while (keepGoing) {
            addFurnitureMenu();
            command = input.nextLine();
            command = command.toLowerCase();
            processCommandAdd(command);
            keepGoing = false;
        }
    }

    //MODIFIES: this
    //EFFECTS: starts remove furniture menu and processing commands from user
    private void editRemoveFurniture() {
        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            removeFurnitureMenu();
            command = input.nextLine();
            command = command.toLowerCase();
            processCommandRemove(command);
            keepGoing = false;
        }
    }

    //MODIFIES: this
    //EFFECTS: starts remove furniture menu and processing commands from user
    private void editPrintFurnitureInList() {
        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            printFurnitureInCollectionMenu();
            command = input.nextLine();
            command = command.toLowerCase();
            processCommandPrint(command);
            keepGoing = false;
        }
    }

    //MODIFIES: this
    //EFFECTS: starts add furniture menu and processing commands from user
    private void editInfoOnFurnitureInCollection() {
        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            furnitureCollectionMenu();
            command = input.nextLine();
            command = command.toLowerCase();
            processCommandInfo(command);
            keepGoing = false;
        }
    }

    //MODIFIES: this
    //EFFECTS: adds new Furniture to a FurnitureCollection
    private void addFurnitureMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tb -> " + beds.getCollectionName());
        System.out.println("\tn -> " + nightstands.getCollectionName());
        System.out.println("\tsu -> " + shelfUnits.getCollectionName());
        System.out.println("\tst -> " + sideTables.getCollectionName());
        System.out.println("\tod -> " + officeDesks.getCollectionName());
    }

    //MODIFIES: this
    //EFFECTS: processes user command for add menu
    private void processCommandAdd(String command) {
        if (command.equals("b")) {
            doAddFurnitureBeds();
        } else if (command.equals("n")) {
            doAddFurnitureNightstands();
        } else if (command.equals("su")) {
            doAddFurnitureShelfUnits();
        } else if (command.equals("st")) {
            doAddFurnitureSideTables();
        } else if (command.equals("od")) {
            doAddFurnitureOfficeDesks();
        } else {
            System.out.println(errorMessage);
        }
    }

    //MODIFIES: this
    //EFFECTS: create new Furniture in FurnitureCollection beds
    private void doAddFurnitureBeds() {
        System.out.println("\nPlease enter furniture name!");
        String name = input.nextLine();
        System.out.println("\nPlease input a 8 digit article number!");
        String artNum = input.nextLine();
        while (!checkValid.checkArtNumIsEightDigit(artNum)) {
            System.out.println("Please change your input to a eight digit input");
            artNum = input.nextLine();
        }
        System.out.println("\nPlease input price in the form xx.xx!");
        String cad = input.nextLine();
        System.out.println("\nPlease input a 6 digit location!");
        input.nextLine();
        String slid = input.nextLine();
        while (!checkValid.checkSlidIsSixDigit(slid)) {
            System.out.println("Please change your input to a six digit number!");
            slid = input.nextLine();
        }
        Furniture furniture = new Furniture(name,artNum,cad,slid);
        beds.addFurniture(furniture);
        System.out.println("Success!");
    }

    //MODIFIES: this
    //EFFECTS: create new Furniture in FurnitureCollection nightstands
    private void doAddFurnitureNightstands() {
        System.out.println("\nPlease enter furniture name!");
        String name = input.nextLine();
        System.out.println("\nPlease input a 8 digit article number!");
        String artNum = input.nextLine();
        while (!checkValid.checkArtNumIsEightDigit(artNum)) {
            System.out.println("Please change your input to a eight digit input");
            artNum = input.nextLine();
        }
        System.out.println("\nPlease input price in the form xx.xx!");
        String cad = input.nextLine();
        System.out.println("\nPlease input a 6 digit location!");
        input.nextLine();
        String slid = input.nextLine();
        while (!checkValid.checkSlidIsSixDigit(slid)) {
            System.out.println("Please change your input to a six digit number!");
            slid = input.nextLine();
        }
        Furniture furniture = new Furniture(name,artNum,cad,slid);
        nightstands.addFurniture(furniture);
        System.out.println("Success!");
    }

    //MODIFIES: this
    //EFFECTS: create new Furniture in FurnitureCollection shelfUnits
    private void doAddFurnitureShelfUnits() {
        System.out.println("\nPlease enter furniture name!");
        String name = input.nextLine();
        System.out.println("\nPlease input a 8 digit article number!");
        String artNum = input.nextLine();
        while (!checkValid.checkArtNumIsEightDigit(artNum)) {
            System.out.println("Please change your input to a eight digit input");
            artNum = input.nextLine();
        }
        System.out.println("\nPlease input price in the form xx.xx!");
        String cad = input.nextLine();
        System.out.println("\nPlease input a 6 digit location!");
        input.nextLine();
        String slid = input.nextLine();
        while (!checkValid.checkSlidIsSixDigit(slid)) {
            System.out.println("Please change your input to a six digit number!");
            slid = input.nextLine();
        }
        Furniture furniture = new Furniture(name,artNum,cad,slid);
        shelfUnits.addFurniture(furniture);
        System.out.println("Success!");
    }

    //MODIFIES: this
    //EFFECTS: create new Furniture in FurnitureCollection sideTables
    private void doAddFurnitureSideTables() {
        System.out.println("\nPlease enter furniture name!");
        String name = input.nextLine();
        System.out.println("\nPlease input a 8 digit article number!");
        String artNum = input.nextLine();
        while (!checkValid.checkArtNumIsEightDigit(artNum)) {
            System.out.println("Please change your input to a eight digit input");
            artNum = input.nextLine();
        }
        System.out.println("\nPlease input price in the form xx.xx!");
        String cad = input.nextLine();
        System.out.println("\nPlease input a 6 digit location!");
        input.nextLine();
        String slid = input.nextLine();
        while (!checkValid.checkSlidIsSixDigit(slid)) {
            System.out.println("Please change your input to a six digit number!");
            slid = input.nextLine();
        }
        Furniture furniture = new Furniture(name,artNum,cad,slid);
        sideTables.addFurniture(furniture);
        System.out.println("Success!");
    }

    //MODIFIES: this
    //EFFECTS: create new Furniture in FurnitureCollection officeDesks
    private void doAddFurnitureOfficeDesks() {
        System.out.println("\nPlease enter furniture name!");
        String name = input.nextLine();
        System.out.println("\nPlease input a 8 digit article number!");
        String artNum = input.nextLine();
        while (!checkValid.checkArtNumIsEightDigit(artNum)) {
            System.out.println("Please change your input to a eight digit input");
            artNum = input.nextLine();
        }
        System.out.println("\nPlease input price in the form xx.xx!");
        String cad = input.nextLine();
        System.out.println("\nPlease input a 6 digit location!");
        input.nextLine();
        String slid = input.nextLine();
        while (!checkValid.checkSlidIsSixDigit(slid)) {
            System.out.println("Please change your input to a six digit number!");
            slid = input.nextLine();
        }
        Furniture furniture = new Furniture(name,artNum,cad,slid);
        officeDesks.addFurniture(furniture);
        System.out.println("Success!");
    }

    //MODIFIES: this
    //EFFECTS: remove a Furniture from a FurnitureCollection
    private void removeFurnitureMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tb -> " + beds.getCollectionName());
        System.out.println("\tn -> " + nightstands.getCollectionName());
        System.out.println("\tsu -> " + shelfUnits.getCollectionName());
        System.out.println("\tst -> " + sideTables.getCollectionName());
        System.out.println("\tod -> " + officeDesks.getCollectionName());
    }
    
    //MODIFIES: this
    //EFFECTS: 
    private void processCommandRemove(String command) {
        if (command.equals("b")) {
            doRemoveFurnitureBeds();
        } else if (command.equals("n")) {
            doRemoveFurnitureNightstands();
        } else if (command.equals("su")) {
            doRemoveFurnitureShelfUnits();
        } else if (command.equals("st")) {
            doRemoveFurnitureSideTables();
        } else if (command.equals("od")) {
            doRemoveFurnitureOfficeDesks();
        } else {
            System.out.println(errorMessage);
        }
    }

    //MODIFIES: this
    //EFFECTS: remove a Furniture from FurnitureCollection beds
    private void doRemoveFurnitureBeds() {
        beds.printFurnitureInList();
        System.out.println("Please type the name of the furniture you wish to remove! ");
        String name = input.nextLine();
        while (!beds.isFurnitureInList(name)) {
            System.out.print("Please enter the name of a furniture which is in the collection!");
            name = input.nextLine();
        }
        beds.removeFurniture(beds.findFurnitureInList(name));
        System.out.println("Success!");
    }

    //MODIFIES: this
    //EFFECTS: remove a Furniture from FurnitureCollection nightstands
    private void doRemoveFurnitureNightstands() {
        nightstands.printFurnitureInList();
        System.out.println("Please type the name of the furniture you wish to remove! ");
        while (!nightstands.isFurnitureInList(input.nextLine())) {
            System.out.print("Please enter the name of a furniture which is in the collection!");
        }
        nightstands.removeFurniture(nightstands.findFurnitureInList(input.nextLine()));
        System.out.println("Success!");
    }

    //MODIFIES: this
    //EFFECTS: remove a Furniture from FurnitureCollection shelfUnits
    private void doRemoveFurnitureShelfUnits() {
        shelfUnits.printFurnitureInList();
        System.out.println("Please type the name of the furniture you wish to remove! ");
        while (!shelfUnits.isFurnitureInList(input.nextLine())) {
            System.out.print("Please enter the name of a furniture which is in the collection!");
        }
        shelfUnits.removeFurniture(shelfUnits.findFurnitureInList(input.nextLine()));
        System.out.println("Success!");
    }

    //MODIFIES: this
    //EFFECTS: remove a Furniture from FurnitureCollection sideTables
    private void doRemoveFurnitureSideTables() {
        sideTables.printFurnitureInList();
        System.out.println("Please type the name of the furniture you wish to remove! ");
        while (!sideTables.isFurnitureInList(input.nextLine())) {
            System.out.print("Please enter the name of a furniture which is in the collection!");
        }
        sideTables.removeFurniture(sideTables.findFurnitureInList(input.nextLine()));
        System.out.println("Success!");
    }

    //MODIFIES: this
    //EFFECTS: remove a Furniture from FurnitureCollection officeDesks
    private void doRemoveFurnitureOfficeDesks() {
        officeDesks.printFurnitureInList();
        System.out.println("Please type the name of the furniture you wish to remove! ");
        while (!officeDesks.isFurnitureInList(input.nextLine())) {
            System.out.print("Please enter the name of a furniture which is in the collection!");
        }
        officeDesks.removeFurniture(officeDesks.findFurnitureInList(input.nextLine()));
        System.out.println("Success!");
    }

    //EFFECTS: prints available options for the user to select to print FurnitureCollection
    private void printFurnitureInCollectionMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tb -> " + beds.getCollectionName());
        System.out.println("\tn -> " + nightstands.getCollectionName());
        System.out.println("\tsu -> " + shelfUnits.getCollectionName());
        System.out.println("\tst -> " + sideTables.getCollectionName());
        System.out.println("\tod -> " + officeDesks.getCollectionName());
    }

    //MODIFIES: this
    //EFFECTS: processes commands from user input for printFurnitureInCollection
    private void processCommandPrint(String command) {
        if (command.equals("b")) {
            beds.printFurnitureInList();
        } else if (command.equals("n")) {
            nightstands.printFurnitureInList();
        } else if (command.equals("su")) {
            shelfUnits.printFurnitureInList();
        } else if (command.equals("st")) {
            sideTables.printFurnitureInList();
        } else if (command.equals("od")) {
            officeDesks.printFurnitureInList();
        } else {
            System.out.println(errorMessage);
        }
    }

    private void furnitureCollectionMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tb -> " + beds.getCollectionName());
        System.out.println("\tn -> " + nightstands.getCollectionName());
        System.out.println("\tsu -> " + shelfUnits.getCollectionName());
        System.out.println("\tst -> " + sideTables.getCollectionName());
        System.out.println("\tod -> " + officeDesks.getCollectionName());
    }

    //MODIFIES: this
    //EFFECTS: processes commands from user input for InfoFurniture
    private void processCommandInfo(String command) {
        if (command.equals("b")) {
            doInfoFurnitureBeds(command);
        } else if (command.equals("n")) {
            doInfoFurnitureNightstands(command);
        } else if (command.equals("su")) {
            doInfoFurnitureShelfUnits(command);
        } else if (command.equals("st")) {
            doInfoFurnitureSideTables(command);
        } else if (command.equals("od")) {
            doInfoFurnitureOfficeDesks(command);
        } else {
            System.out.println(errorMessage);
        }
    }

    //MODIFIES: this
    //EFFECTS: prints info on the specified Furniture
    private void doInfoFurnitureBeds(String command) {
        beds.printFurnitureInList();
        System.out.println("Please input the furniture if you want ");
        String temp = input.nextLine();
        while (!beds.isFurnitureInList(temp)) {
            System.out.print("Please enter the name of a furniture which is in the collection!");
        }
        System.out.println("Furniture name is " + beds.findFurnitureInList(temp).getFurnitureName());
        System.out.println("Article number is " + beds.findFurnitureInList(temp).getArticleNumber());
        System.out.println("Price in CAD is " + beds.findFurnitureInList(temp).getPrice());
        System.out.println("Location is " + beds.findFurnitureInList(temp).getLocation());
    }

    //MODIFIES: this
    //EFFECTS: prints info on the specified Furniture
    private void doInfoFurnitureNightstands(String command) {
        nightstands.printFurnitureInList();
        System.out.println("Please input the furniture if you want ");
        String temp = input.nextLine();
        while (!nightstands.isFurnitureInList(temp)) {
            System.out.print("Please enter the name of a furniture which is in the collection!");
        }
        System.out.println("Furniture name is " + nightstands.findFurnitureInList(temp).getFurnitureName());
        System.out.println("Article number is " + nightstands.findFurnitureInList(temp).getArticleNumber());
        System.out.println("Price in CAD is " + nightstands.findFurnitureInList(temp).getPrice());
        System.out.println("Location is " + nightstands.findFurnitureInList(temp).getLocation());
    }

    //MODIFIES: this
    //EFFECTS: prints info on the specified Furniture
    private void doInfoFurnitureShelfUnits(String command) {
        shelfUnits.printFurnitureInList();
        System.out.println("Please input the furniture if you want ");
        String temp = input.nextLine();
        while (!shelfUnits.isFurnitureInList(temp)) {
            System.out.print("Please enter the name of a furniture which is in the collection!");
        }
        System.out.println("Furniture name is " + shelfUnits.findFurnitureInList(temp).getFurnitureName());
        System.out.println("Article number is " + shelfUnits.findFurnitureInList(temp).getArticleNumber());
        System.out.println("Price in CAD is " + shelfUnits.findFurnitureInList(temp).getPrice());
        System.out.println("Location is " + shelfUnits.findFurnitureInList(temp).getLocation());
    }

    //MODIFIES: this
    //EFFECTS: prints info on the specified Furniture
    private void doInfoFurnitureSideTables(String command) {
        sideTables.printFurnitureInList();
        System.out.println("Please input the furniture if you want ");
        String temp = input.nextLine();
        while (!sideTables.isFurnitureInList(temp)) {
            System.out.print("Please enter the name of a furniture which is in the collection!");
        }
        System.out.println("Furniture name is " + sideTables.findFurnitureInList(temp).getFurnitureName());
        System.out.println("Article number is " + sideTables.findFurnitureInList(temp).getArticleNumber());
        System.out.println("Price in CAD is " + sideTables.findFurnitureInList(temp).getPrice());
        System.out.println("Location is " + sideTables.findFurnitureInList(temp).getLocation());
    }

    //MODIFIES: this
    //EFFECTS: prints info on the specified Furniture
    private void doInfoFurnitureOfficeDesks(String command) {
        officeDesks.printFurnitureInList();
        System.out.println("Please input the furniture if you want ");
        String temp = input.nextLine();
        while (!officeDesks.isFurnitureInList(temp)) {
            System.out.print("Please enter the name of a furniture which is in the collection!");
        }
        System.out.println("Furniture name is " + officeDesks.findFurnitureInList(temp).getFurnitureName());
        System.out.println("Article number is " + officeDesks.findFurnitureInList(temp).getArticleNumber());
        System.out.println("Price in CAD is " + officeDesks.findFurnitureInList(temp).getPrice());
        System.out.println("Location is " + officeDesks.findFurnitureInList(temp).getLocation());
    }

    private void saveInventoryMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tb -> " + beds.getCollectionName());
        System.out.println("\tn -> " + nightstands.getCollectionName());
        System.out.println("\tsu -> " + shelfUnits.getCollectionName());
        System.out.println("\tst -> " + sideTables.getCollectionName());
        System.out.println("\tod -> " + officeDesks.getCollectionName());
    }

    private void processCommandSave(String command) {
        if (command.equals("b")) {
            saveInventoryBeds();
        } else if (command.equals("n")) {
            saveInventoryNightstands();
        } else if (command.equals("su")) {
            saveInventoryShelfUnits();
        } else if (command.equals("st")) {
            saveInventorySideTables();
        } else if (command.equals("od")) {
            saveInventoryOfficeDesks();
        } else {
            System.out.println(errorMessage);
        }
    }

    // EFFECTS: saves the beds collection to file
    private void saveInventoryBeds() {
        try {
            jsonWriterBeds.open();
            jsonWriterBeds.write(beds);
            jsonWriterBeds.close();
            System.out.println("Saved " + beds.getCollectionName() + " to " + JSON_STORE_BEDS);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_BEDS);
        }
    }

    // EFFECTS: saves the nightstands collection to file
    private void saveInventoryNightstands() {
        try {
            jsonWriterNightstands.open();
            jsonWriterNightstands.write(nightstands);
            jsonWriterNightstands.close();
            System.out.println("Saved " + nightstands.getCollectionName() + " to " + JSON_STORE_NIGHTSTANDS);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_NIGHTSTANDS);
        }
    }

    // EFFECTS: saves the shelfUnits collection to file
    private void saveInventoryShelfUnits() {
        try {
            jsonWriterShelfUnits.open();
            jsonWriterShelfUnits.write(shelfUnits);
            jsonWriterShelfUnits.close();
            System.out.println("Saved " + shelfUnits.getCollectionName() + " to " + JSON_STORE_SHELFUNITS);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_SHELFUNITS);
        }
    }

    // EFFECTS: saves the side tables collection to file
    private void saveInventorySideTables() {
        try {
            jsonWriterSideTables.open();
            jsonWriterSideTables.write(sideTables);
            jsonWriterSideTables.close();
            System.out.println("Saved " + sideTables.getCollectionName() + " to " + JSON_STORE_SIDETABLES);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_SIDETABLES);
        }
    }

    // EFFECTS: saves the office desks collection to file
    private void saveInventoryOfficeDesks() {
        try {
            jsonWriterOfficeDesks.open();
            jsonWriterOfficeDesks.write(officeDesks);
            jsonWriterOfficeDesks.close();
            System.out.println("Saved " + officeDesks.getCollectionName() + " to " + JSON_STORE_OFFICEDESKS);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_OFFICEDESKS);
        }
    }

    private void loadInventoryMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tb -> " + beds.getCollectionName());
        System.out.println("\tn -> " + nightstands.getCollectionName());
        System.out.println("\tsu -> " + shelfUnits.getCollectionName());
        System.out.println("\tst -> " + sideTables.getCollectionName());
        System.out.println("\tod -> " + officeDesks.getCollectionName());
    }

    private void processCommandLoad(String command) {
        if (command.equals("b")) {
            loadInventoryBeds();
        } else if (command.equals("n")) {
            loadInventoryNightstands();
        } else if (command.equals("su")) {
            loadInventoryShelfUnits();
        } else if (command.equals("st")) {
            loadInventorySideTables();
        } else if (command.equals("od")) {
            loadInventoryOfficeDesks();
        } else {
            System.out.println(errorMessage);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads beds from file
    private void loadInventoryBeds() {
        try {
            beds = jsonReaderBeds.read();
            System.out.println("Loaded " + beds.getCollectionName() + " from " + JSON_STORE_BEDS);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_BEDS);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads nightstands from file
    private void loadInventoryNightstands() {
        try {
            nightstands = jsonReaderNightstands.read();
            System.out.println("Loaded " + nightstands.getCollectionName() + " from " + JSON_STORE_NIGHTSTANDS);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_NIGHTSTANDS);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads shelf units from file
    private void loadInventoryShelfUnits() {
        try {
            shelfUnits = jsonReaderShelfUnits.read();
            System.out.println("Loaded " + shelfUnits.getCollectionName() + " from " + JSON_STORE_SHELFUNITS);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_SHELFUNITS);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads side tables from file
    private void loadInventorySideTables() {
        try {
            sideTables = jsonReaderSideTables.read();
            System.out.println("Loaded " + sideTables.getCollectionName() + " from " + JSON_STORE_SIDETABLES);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_SIDETABLES);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads office desks from file
    private void loadInventoryOfficeDesks() {
        try {
            officeDesks = jsonReaderOfficeDesks.read();
            System.out.println("Loaded " + officeDesks.getCollectionName() + " from " + JSON_STORE_OFFICEDESKS);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_OFFICEDESKS);
        }
    }




}
