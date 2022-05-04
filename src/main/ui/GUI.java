package ui;

import model.Furniture;
import model.FurnitureCollection;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;

public class GUI extends JFrame implements ActionListener, ListSelectionListener {
    private JButton addButton;
    private JButton removeButton;
    private JButton infoButton;

    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem save;
    private JMenuItem load;
    private JMenu sound;
    private JMenuItem soundOn;
    private JMenuItem soundOff;
    private boolean soundMute = false;

    private JPanel fcPanel;
    private ImageIcon ikeaIcon;

    private JFrame addFurnitureWindow;
    private static final String JSON_STORE_BEDS = "./data/inventoryBeds.json";
    private static final String JSON_STORE_NIGHTSTANDS = "./data/inventoryNightstands.json";
    private static final String JSON_STORE_SHELFUNITS = "./data/inventoryShelfUnits.json";
    private static final String JSON_STORE_SIDETABLES = "./data/inventorySideTables.json";
    private static final String JSON_STORE_OFFICEDESKS = "./data/inventoryOfficeDesks.json";
    private static final String[] COLLECTIONS = {"Beds","Nightstands","Shelf Units","Side Tables","Office Desks"};
    private FurnitureCollection beds;
    private FurnitureCollection nightstands;
    private FurnitureCollection shelfUnits;
    private FurnitureCollection sideTables;
    private FurnitureCollection officeDesks;
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

    private JScrollPane listScrollPane;
    private JList list;
    private DefaultListModel listModel;
    private JComboBox collectionSelector;
    private int index;

    public GUI() {

        init();

        //Menu bar
        initializeMenuBar();

        initializeJScrollPane();

        //FurnitureCollection selector panel
        initializeFurnitureCollectionPanel();

        //List panel
        JPanel listPanel = new JPanel();
        listPanel.setBackground(Color.WHITE);
        listPanel.setBounds(0,50,500,350);
        listPanel.setLayout(new BorderLayout());
        this.add(listPanel,BorderLayout.CENTER);

        //List display
        listPanel.add(listScrollPane,BorderLayout.CENTER);

        //Button panel
        initializeButtonPanel();

        //sets up JFrame
        initializeJFrame();

        //program icon
        programIconSetter();

        this.setVisible(true);  //make frame visible, should be last call
    }

    //EFFECTS: initializes button panel along with add, remove, info buttons
    private void initializeButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.YELLOW);
        buttonPanel.setBounds(0,400,500,50);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,5,10));
        this.add(buttonPanel, BorderLayout.SOUTH);

        addButton = new JButton("Add Furniture");
        addButton.setActionCommand("Add Furniture");
        addButton.addActionListener(this);
        buttonPanel.add(addButton);

        //remove button
        removeButton = new JButton();
        removeButton.addActionListener(this);
        removeButton.setText("Remove Furniture");
        removeButton.setFocusable(false);
        //removeButton.setIcon(addIcon);
        removeButton.setHorizontalTextPosition(JButton.CENTER);
        removeButton.setVerticalTextPosition(JButton.BOTTOM);
        buttonPanel.add(removeButton);

        //info button
        infoButton = new JButton();
        infoButton.addActionListener(this);
        infoButton.setText("Info on Furniture");
        infoButton.setFocusable(false);
        //infoButton.setIcon(addIcon);
        infoButton.setHorizontalTextPosition(JButton.CENTER);
        infoButton.setVerticalTextPosition(JButton.BOTTOM);
        buttonPanel.add(infoButton);
    }

    //MODIFIES: this
    //EFFECTS: initializes main JFrame
    private void initializeJFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //exit out of application
        this.setTitle("IKEA Inventory");  //sets title of the frame
        this.setSize(500,500);  //sets the x-dimension, and y-dimension of frame
        this.setLayout(new BorderLayout());
        this.setResizable(false);  //prevents frame from being resized
    }

    //MODIFIES: this
    //EFFECTS: sets JFrame icon
    private void programIconSetter() {
        ikeaIcon = new ImageIcon("ikea_logo.jpg");  //create an ImageIcon
        this.setIconImage(ikeaIcon.getImage());  //change icon of frame
        this.getContentPane().setBackground(new Color(255,255,255));  //change color of background
    }

    //MODIFIES: this
    //EFFECTS: initializes FurnitureCollection panel with JComboBox
    private void initializeFurnitureCollectionPanel() {
        fcPanel = new JPanel();
        fcPanel.setBackground(Color.YELLOW);
        fcPanel.setBounds(0,0,500,50);
        fcPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,10));
        this.add(fcPanel,BorderLayout.NORTH);

        //FurnitureCollection combo box
        initializeFurnitureCollectionComboBox();
    }

    //MODIFIES: fcPanel
    //EFFECTS: initializes FurnitureCollection ComboBox
    private void initializeFurnitureCollectionComboBox() {
        collectionSelector = new JComboBox(COLLECTIONS);
        collectionSelector.addActionListener(this);
        fcPanel.add(collectionSelector);
    }

    //cite: https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
    //EFFECTS: initializes JScrollPane, used for displaying Furniture in FurnitureCollection
    //         depending on ComboBox selection
    private void initializeJScrollPane() {
        listModel = new DefaultListModel();
        listModel.addElement("Here is where Furniture in selected Furniture Collection will show up!");
        //Create the list and put it in a scroll pane.
        listScrollPane = initializeJList();
    }

    //MODIFIES: this
    //EFFECTS: initializes MenuBar with options File -> Save, load
    private void initializeMenuBar() {
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        menu = new JMenu("File");
        menuBar.add(menu);
        sound = new JMenu("Sound");
        menuBar.add(sound);

        save = new JMenuItem("Save", imageIconResizer("save.png"));
        save.addActionListener(this);
        menu.add(save);
        load = new JMenuItem("Load", imageIconResizer("file.png"));
        load.addActionListener(this);
        menu.add(load);

        soundOn = new JMenuItem("On");
        soundOn.addActionListener(this);
        sound.add(soundOn);
        soundOff = new JMenuItem("Off");
        soundOff.addActionListener(this);
        sound.add(soundOff);
    }

    //EFFECTS: resizes ImageIcon to 40x40 size
    private ImageIcon imageIconResizer(String fileName) {
        ImageIcon img = new ImageIcon(fileName);
        Image i = img.getImage();
        Image resizedImg = i.getScaledInstance(40,40,Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    //EFFECTS: initializes JScrollPane to display available furniture in collection
    private JScrollPane initializeJList() {
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(17);
        JScrollPane listScrollPane = new JScrollPane(list);
        return listScrollPane;
    }

    //EFFECTS: Using inputted int index, returns selected furniture collection
    public FurnitureCollection collectionInFocus(int index) {
        if (index == 0) {
            return beds;
        } else if (index == 1) {
            return nightstands;
        } else if (index == 2) {
            return shelfUnits;
        } else if (index == 3) {
            return sideTables;
        } else {
            return officeDesks;
        }
    }

    //EFFECTS: initializes FurnitureCollections, JSONReaders, JSONWriters
    public void init() {
        beds = new FurnitureCollection("Beds");
        nightstands = new FurnitureCollection("Nightstands");
        shelfUnits = new FurnitureCollection("Shelf Units");
        sideTables = new FurnitureCollection("Side Tables");
        officeDesks = new FurnitureCollection("Office Desks");
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
    }

    //EFFECTS: will refresh listModel depending on currently selected furnitureCollection
    public void scanChange() {
        listModel.clear();
        FurnitureCollection currentCollection = collectionInFocus(index);
        for (int i = 0; i < currentCollection.getSize(); i++) {
            String listName = currentCollection.getFurnitureAtIndex(i).getFurnitureName();
            listModel.addElement(listName);
        }
        listScrollPane = initializeJList();
    }

    //cite: http://suavesnippets.blogspot.com/2011/06/add-sound-on-jbutton-click-in-java.html
    //EFFECTS: plays sound using given sound file
    public void playSound(String soundName) {
        if (!soundMute) {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                        new File(soundName).getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (Exception e) {
                System.out.println("Error with playing sound" + soundName);
                e.printStackTrace();
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: Depending on which action is performed, addButton, removeButton, collectionSelector
    //         ,infoButton. Calls appropriate methods to action performed.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            playSound("mouseClick.au");
            initializeAddFurnitureWindow();

        } else if (e.getSource() == removeButton) {
            playSound("mouseClick.au");
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            removeButtonMethod();
            
        } else if (e.getSource() == collectionSelector) {
            playSound("mouseClick.au");
            initializeCollectionSelector();

        } else if (e.getSource() == infoButton) {
            playSound("mouseClick.au");
            produceInfoOnFurniture();

        } else if (e.getSource() == save) {
            playSound("mouseClick.au");
            saveFurnitureCollection();

        } else if (e.getSource() == load) {
            playSound("mouseClick.au");
            loadFurnitureCollection();
        } else if (e.getSource() == soundOn) {
            soundMute = false;
        } else if (e.getSource() == soundOff) {
            soundMute = true;
        }
    }

    //EFFECTS: initializes collection selector combobox
    private void initializeCollectionSelector() {
        index = collectionSelector.getSelectedIndex();
        scanChange();
    }

    //EFFECTS: initializes add furniture JFrame which takes user inputs to create a new Furniture
    //         and add it to desired Furniture Collection
    private void initializeAddFurnitureWindow() {
        addFurnitureWindow = new JFrame("Add furniture");
        addFurnitureWindow.getContentPane().add(new AddFurnitureWindow().getUI());
        addFurnitureWindow.setLocationRelativeTo(null);
        addFurnitureWindow.setIconImage(ikeaIcon.getImage());  //change icon of frame
        addFurnitureWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addFurnitureWindow.pack();
        addFurnitureWindow.setVisible(true);
    }

    //EFFECTS: finds info on Furniture which is currently selected depending on index
    private void produceInfoOnFurniture() {
        int index = list.getSelectedIndex();
        Furniture furniture = getSelectedFurnitureAtIndex(index);
        furnitureInfoDialogBox(furniture);
        int size = listModel.getSize();

        if (size == 0) { //Nobody's left, disable firing.
            infoButton.setEnabled(false);
        }
    }

    //EFFECTS: load FurnitureCollection depending on user selection in JOptionPane
    private void loadFurnitureCollection() {
        JComboBox fcBox = new JComboBox(COLLECTIONS);
        JOptionPane.showMessageDialog(null,
                fcBox,
                "Select a collection",
                JOptionPane.QUESTION_MESSAGE);
        int index = fcBox.getSelectedIndex();
        FurnitureCollection fc = collectionInFocus(index);
        JsonReader currentReader = getJsonRead(fc);
        loadFurnitureCollection(currentReader);
        scanChange();
    }

    //EFFECTS: load correct FurnitureCollection based on which JSON Reader it is operating on
    private void loadFurnitureCollection(JsonReader currentReader) {
        if (currentReader == jsonReaderBeds) {
            loadBedsCollection(currentReader);
        } else if (currentReader == jsonReaderNightstands) {
            loadNightstandsCollection(currentReader);
        } else if (currentReader == jsonReaderShelfUnits) {
            loadShelfUnitsCollection(currentReader);
        } else if (currentReader == jsonReaderSideTables) {
            loadSideTablesCollection(currentReader);
        } else if (currentReader == jsonReaderOfficeDesks) {
            loadOfficeDesks(currentReader);
        }
    }

    //EFFECTS: load saved OfficeDesks
    private void loadOfficeDesks(JsonReader currentReader) {
        try {
            officeDesks = currentReader.read();
            JOptionPane.showMessageDialog(null,
                    "Loaded "
                            + officeDesks.getCollectionName()
                            + " from " + JSON_STORE_OFFICEDESKS);
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(null,
                    "Unable to read from file: "
                            + JSON_STORE_OFFICEDESKS);
        }
    }

    //EFFECTS: load saved SideTables
    private void loadSideTablesCollection(JsonReader currentReader) {
        try {
            sideTables = currentReader.read();
            JOptionPane.showMessageDialog(null,
                    "Loaded "
                            + sideTables.getCollectionName()
                            + " from " + JSON_STORE_SIDETABLES);
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(null,
                    "Unable to read from file: "
                            + JSON_STORE_SIDETABLES);
        }
    }

    //EFFECTS: load saved ShelfUnits
    private void loadShelfUnitsCollection(JsonReader currentReader) {
        try {
            shelfUnits = currentReader.read();
            JOptionPane.showMessageDialog(null,
                    "Loaded "
                            + shelfUnits.getCollectionName()
                            + " from " + JSON_STORE_SHELFUNITS);
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(null,
                    "Unable to read from file: "
                            + JSON_STORE_SHELFUNITS);
        }
    }

    //EFFECTS: load saved NightStands
    private void loadNightstandsCollection(JsonReader currentReader) {
        try {
            nightstands = currentReader.read();
            JOptionPane.showMessageDialog(null,
                    "Loaded "
                            + nightstands.getCollectionName()
                            + " from " + JSON_STORE_NIGHTSTANDS);
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(null,
                    "Unable to read from file: "
                            + JSON_STORE_NIGHTSTANDS);
        }
    }

    //EFFECTS: load saved Beds
    private void loadBedsCollection(JsonReader currentReader) {
        try {
            beds = currentReader.read();
            JOptionPane.showMessageDialog(null,
                    "Loaded "
                            + beds.getCollectionName()
                            + " from " + JSON_STORE_BEDS);
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(null,
                    "Unable to read from file: "
                            + JSON_STORE_BEDS);
        }
    }

    //EFFECTS: saves FurnitureCollection depending on user selection in JOptionePane
    private void saveFurnitureCollection() {
        JComboBox fcBox = new JComboBox(COLLECTIONS);
        JOptionPane.showMessageDialog(null,
                fcBox,
                "Select a collection",
                JOptionPane.QUESTION_MESSAGE);
        int index = fcBox.getSelectedIndex();
        FurnitureCollection fc = collectionInFocus(index);
        String currentPath = getJsonStore(fc);
        JsonWriter currentWriter = getJsonWrite(fc);
        try {
            currentWriter.open();
            currentWriter.write(fc);
            currentWriter.close();
            JOptionPane.showMessageDialog(null,
                    "Saved "
                            + fc.getCollectionName()
                            + " to " + currentPath);
        } catch (FileNotFoundException e1) {
            JOptionPane.showMessageDialog(null,
                    "Unable to write to file: "
                            + currentPath);
        }
        scanChange();
    }

    //EFFECTS: returns JSON file name based on FurnitureCollection
    private String getJsonStore(FurnitureCollection fc) {
        if (fc == beds) {
            return JSON_STORE_BEDS;
        } else if (fc == nightstands) {
            return JSON_STORE_NIGHTSTANDS;
        } else if (fc == shelfUnits) {
            return JSON_STORE_OFFICEDESKS;
        } else if (fc == sideTables) {
            return JSON_STORE_SIDETABLES;
        } else {
            return JSON_STORE_OFFICEDESKS;
        }
    }

    //EFFECTS: returns correct JSONWriter based on FurnitureCollection
    private JsonWriter getJsonWrite(FurnitureCollection fc) {
        if (fc == beds) {
            return jsonWriterBeds;
        } else if (fc == nightstands) {
            return jsonWriterNightstands;
        } else if (fc == shelfUnits) {
            return jsonWriterShelfUnits;
        } else if (fc == sideTables) {
            return jsonWriterSideTables;
        } else {
            return jsonWriterOfficeDesks;
        }
    }

    //EFFECTS: returns correct JSONReader based on FurnitureCollection
    private JsonReader getJsonRead(FurnitureCollection fc) {
        if (fc == beds) {
            return jsonReaderBeds;
        } else if (fc == nightstands) {
            return jsonReaderNightstands;
        } else if (fc == shelfUnits) {
            return jsonReaderShelfUnits;
        } else if (fc == sideTables) {
            return jsonReaderSideTables;
        } else {
            return jsonReaderOfficeDesks;
        }
    }

    //EFFECTS: JOptionPane shows all info available on furniture
    private void furnitureInfoDialogBox(Furniture furniture) {
        JOptionPane.showMessageDialog(
                null,
                "Furniture Name = " + furniture.getFurnitureName() + "\n"
                        + "Article Number = " + furniture.getArticleNumber()
                        + "\n" + "Price (CAD) = " + furniture.getPrice() + "\n"
                        + "Sales location = " + furniture.getLocation());
    }

    //EFFECTS: returns Furniture currently selected at index
    private Furniture getSelectedFurnitureAtIndex(int index) {
        String furnitureName = listModel.getElementAt(index).toString();
        FurnitureCollection fc = collectionInFocus(this.index);
        Furniture furniture = fc.findFurnitureInList(furnitureName);
        return furniture;
    }

    //EFFECTS: removeButton, removes Furniture at selected index or if no index selected
    //         removes last Furniture. Disables button when empty
    private void removeButtonMethod() {
        int index = list.getSelectedIndex();
        removeSelectedFurniture(index);
        listModel.remove(index);

        int size = listModel.getSize();

        if (size == 0) { //Nobody's left, disable firing.
            removeButton.setEnabled(false);

        } else { //Select an index.
            if (index == listModel.getSize()) {
                //removed item in last position
                index--;
            }

            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }
    }

    //EFFECTS: removes Furniture at selected index
    private void removeSelectedFurniture(int index) {
        String furnitureName = listModel.getElementAt(index).toString();
        FurnitureCollection fc = collectionInFocus(this.index);
        Furniture furniture = fc.findFurnitureInList(furnitureName);
        fc.removeFurniture(furniture);
    }

    //EFFECTS: enables or disables remove,info button depending on if item is selected
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
            if (list.getSelectedIndex() == -1) {
                //No selection, disable remove button.
                removeButton.setEnabled(false);
                infoButton.setEnabled(false);

            } else {
                //Selection, enable the remove button.
                removeButton.setEnabled(true);
                infoButton.setEnabled(true);
            }
        }
    }

    class AddFurnitureWindow extends JFrame implements ActionListener {
        private JComboBox collectionSelector;
        private JPanel pnlMain;
        private JButton btnAdd;
        private JButton btnCancel;
        private JTextField txtName;
        private JTextField txtArtNum;
        private JTextField txtPrice;
        private JTextField txtSlid;
        private String name;
        private String artNum;
        private String cad;
        private String slid;
        private int index;

        public AddFurnitureWindow() {
            pnlMain = new JPanel();
            pnlMain.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            //collection label
            collectionLabel(gbc);

            //collection combo selector
            collectionComboSelector();

            //product name label
            productNameLabel();

            //product name input
            productNameInput();

            //article number label
            articleNumberLabel();

            //article number input
            articleNumberInput();

            //product price label
            productPriceLabel();

            //product price input
            productPriceInput();

            //sales location label
            salesLocationLabel();

            //sales location input
            salesLocationInput();

            //add button to add desired fields to become furniture in a furniture collection
            buttonAdd();

//            btnCancel = new JButton("Cancel");
//            btnCancel.addActionListener(this);
//            gbc = new GridBagConstraints();
//            gbc.anchor = GridBagConstraints.EAST;
//            gbc.gridwidth = 1;
//            gbc.gridx = 3;
//            gbc.gridy = 5;
//            gbc.insets = new Insets(10, 0, 10, 10);
//            gbc.weightx = 1;
//            pnlMain.add(btnCancel, gbc);
        }

        //EFFECTS: initializes and positions addButton
        private void buttonAdd() {
            GridBagConstraints gbc;
            btnAdd = new JButton("Add");
            btnAdd.addActionListener(this);
            gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.EAST;
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.insets = new Insets(10, 10, 10, 0);
            gbc.weightx = 1;
            pnlMain.add(btnAdd, gbc);
        }

        //EFFECTS: initializes and positions sales location input
        private void salesLocationInput() {
            GridBagConstraints gbc;
            txtSlid = new JTextField();
            gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridwidth = 3;
            gbc.gridx = 1;
            gbc.gridy = 4;
            gbc.weightx = 1;
            gbc.insets = new Insets(5, 0, 0, 10);
            pnlMain.add(txtSlid, gbc);
        }

        //EFFECTS: initializes and positions price input
        private void productPriceInput() {
            GridBagConstraints gbc;
            txtPrice = new JTextField();
            gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridwidth = 3;
            gbc.gridx = 1;
            gbc.gridy = 3;
            gbc.weightx = 1;
            gbc.insets = new Insets(5, 0, 0, 10);
            pnlMain.add(txtPrice, gbc);
        }

        //EFFECTS: initializes and positions article number input
        private void articleNumberInput() {
            GridBagConstraints gbc;
            txtArtNum = new JTextField();
            gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridwidth = 3;
            gbc.gridx = 1;
            gbc.gridy = 2;
            gbc.weightx = 1;
            gbc.insets = new Insets(5, 0, 0, 10);
            pnlMain.add(txtArtNum, gbc);
        }

        //EFFECTS: initializes and positions product name input
        private void productNameInput() {
            GridBagConstraints gbc;
            txtName = new JTextField();
            gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridwidth = 3;
            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.insets = new Insets(5, 0, 0, 10);
            gbc.weightx = 1;
            pnlMain.add(txtName, gbc);
        }

        //EFFECTS: initializes and poistions sales location label
        private void salesLocationLabel() {
            GridBagConstraints gbc;
            JLabel lblSlid   = new JLabel("Sales Location ID (6 digits)");
            gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.insets = new Insets(0, 10, 0, 0);
            gbc.weightx = 1;
            pnlMain.add(lblSlid, gbc);
        }

        //EFFECTS: initializes and positions price label
        private void productPriceLabel() {
            GridBagConstraints gbc;
            JLabel lblPrice = new JLabel("Price (xx.xx)");
            gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.insets = new Insets(0, 10, 0, 0);
            gbc.weightx = 1;
            pnlMain.add(lblPrice, gbc);
        }

        //EFFECTS: initializes and position article number label
        private void articleNumberLabel() {
            GridBagConstraints gbc;
            JLabel lblArtNum = new JLabel("Article Number (8 digits)");
            gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.insets = new Insets(0, 10, 0, 0);
            gbc.weightx = 1;
            pnlMain.add(lblArtNum, gbc);
        }

        //EFFECTS: initializes and positions name label
        private void productNameLabel() {
            GridBagConstraints gbc;
            JLabel lblName = new JLabel("Product Name");
            gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.insets = new Insets(0, 10, 0, 0);
            gbc.weightx = 1;
            pnlMain.add(lblName, gbc);
        }

        //EFFECTS: initializes and positions Furniture Collection combobox
        private void collectionComboSelector() {
            GridBagConstraints gbc;
            String[] collections = {"Beds", "Nightstands", "Shelf Units", "Side Tables", "Office Desks"};
            collectionSelector = new JComboBox(collections);
            collectionSelector.addActionListener(this);
            gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridwidth = 3;
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.insets = new Insets(5, 0, 0, 10);
            gbc.weightx = 1;
            pnlMain.add(collectionSelector, gbc);
        }

        //EFFECTS: initializes and positions Furniture Collection label
        private void collectionLabel(GridBagConstraints gbc) {
            JLabel lblCollection = new JLabel("Collection");
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 10, 0, 0);
            gbc.weightx = 1;
            pnlMain.add(lblCollection, gbc);
        }

        //EFFECTS: returns JPanel for JScrollPane
        public JPanel getUI() {
            return pnlMain;
        }

        //EFFECTS: checks for user interaction on collectionSelector comboBox and
        //         add button. Calls method call based on input.
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == collectionSelector) {
                index = collectionSelector.getSelectedIndex();
            }
            if (e.getSource() == btnAdd) {
                cad = txtPrice.getText();
                name = txtName.getText();
                artNum = txtArtNum.getText();
                slid = txtSlid.getText();
                FurnitureCollection fc = collectionInFocus(index);
                addFurnitureToCollection(fc,name,artNum,cad,slid);
                JOptionPane.showMessageDialog(null,
                        "Success! " + txtName.getText() + " is added to "
                                + fc.getCollectionName() + "!");

                addFurnitureToDisplay();
                scanChange();

                //closes add furniture window
                addFurnitureWindow.dispose();
            }
        }

        //EFFECTS: Adds desired furniture to JScrollList so that it can be view on the display
        private void addFurnitureToDisplay() {
            //gets selected index
            int position = list.getAnchorSelectionIndex();
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }
            //adds new furniture name to current furniture collection menu
            listModel.insertElementAt(name,0);

            //Select the new item and make it visible.
            list.setSelectedIndex(position);
            list.ensureIndexIsVisible(position);
        }

        //EFFECTS: returns product name
        public String getProductName() {
            return name;
        }

        //EFFECTS: Creates a new furniture using signature inputs and adds that
        //         Furniture to given FurnitureCollection
        public void addFurnitureToCollection(FurnitureCollection fc,
                                             String name,
                                             String artNum,
                                             String cad,
                                             String slid) {
            Furniture furniture = new Furniture(name,artNum,cad,slid);
            fc.addFurniture(furniture);
        }
    }
}

