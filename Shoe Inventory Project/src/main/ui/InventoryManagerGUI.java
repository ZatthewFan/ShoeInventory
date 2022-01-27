package ui;

import model.Event;
import model.EventLog;
import model.Shoe;
import model.ShoeInventory;
import persistence.JsonReader;
import persistence.JsonWriter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class InventoryManagerGUI extends JFrame implements ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String ADD_COMMAND = "add";
    private static final String REMOVE_COMMAND = "remove";
    private static final String SAVE_COMMAND = "save";
    private static final String LOAD_COMMAND = "load";
    private static final String QUIT_COMMAND = "quit";

    private JFrame frame;
    private JPanel leftPanel;
    private JPanel centerPanel;

    private InventoryTree treePanel;

    private JLabel nameLabel;
    private JLabel sizeLabel;
    private JLabel shoeSizeLabel;
    private JLabel brandLabel;
    private JLabel mainColorLabel;
    private JLabel conditionLabel;

    private static String nameString = "Name: ";
    private static String sizeString = "Size: ";
    private static String shoeSizeString = "Shoe size: ";
    private static String brandString = "Brand: ";
    private static String mainColorString = "Main color: ";
    private static String conditionString = "Condition: ";

    private JFormattedTextField nameField;
    private JFormattedTextField sizeField;
    private JFormattedTextField shoeSizeField;
    private JFormattedTextField brandField;
    private JFormattedTextField mainColorField;
    private JFormattedTextField conditionField;

    private ShoeInventory inventory;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/shoeInventoryGUI.json";

    // MODIFIES: this
    // EFFECTS : sets up the gui window
    public InventoryManagerGUI() throws FileNotFoundException {
        // initialize fields
        frame = new JFrame();
        leftPanel = new JPanel();
        treePanel = new InventoryTree();
        centerPanel = new JPanel();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        inventory = new ShoeInventory();

        // sets up frame
        setupFrame();

        // sets up buttons
        setupButtons();

        // sets up the left panel
        leftPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        leftPanel.setLayout(new GridLayout(0, 1));
        frame.add(leftPanel, BorderLayout.WEST);

        // sets up the center panel
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        frame.add(centerPanel, BorderLayout.CENTER);
        setupAddShoePanel();

        // sets up the right panel
        treePanel.setPreferredSize(new Dimension(300, 150));
        frame.add(treePanel, BorderLayout.EAST);
    }

    // MODIFIES: this
    // EFFECTS : sets up the frame for the gui
    private void setupFrame() {
        frame.setSize(WIDTH, HEIGHT);
        frame.add(leftPanel, BorderLayout.WEST);
        frame.setTitle("Inventory Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 600);
        frame.setVisible(true);
    }


// -------------------------------------- LEFT PANEL --------------------------------------

    // MODIFIES: this
    // EFFECTS : makes and sets up buttons on the left panel
    public void setupButtons() {
        leftPanel.add(makeAddShoeButton());
        leftPanel.add(makeRemoveShoeButton());
        leftPanel.add(makeSaveInventoryBtn());
        leftPanel.add(makeLoadInventoryBtn());
        leftPanel.add(makeQuitBtn());
    }

    // MODIFIES: this
    // EFFECTS : makes a button to add shoe
    private JButton makeAddShoeButton() {
        JButton addShoeBtn = new JButton("Add Shoe");
        addShoeBtn.setActionCommand(ADD_COMMAND);
        addShoeBtn.addActionListener(this);
        return addShoeBtn;
    }

    // MODIFIES: this
    // EFFECTS : makes a button to remove shoe
    private JButton makeRemoveShoeButton() {
        JButton removeShoeBtn = new JButton("Remove Shoe");
        removeShoeBtn.setActionCommand(REMOVE_COMMAND);
        removeShoeBtn.addActionListener(this);
        return removeShoeBtn;
    }

/*


    // MODIFIES: this
    // EFFECTS : makes a button to add a property to a shoe
    private JButton makeAddPropertyButton() {
        JButton addPropertyBtn = new JButton("Add Property");
        addPropertyBtn.setActionCommand("addProperty");
        addPropertyBtn.addActionListener(this);
        return addPropertyBtn;
    }

    // MODIFIES: this
    // EFFECTS : makes a button to set a filter to the inventory
    private JButton makeSetFilterBtn() {
        JButton setFilterBtn = new JButton("Set Filter");
        setFilterBtn.setActionCommand("setFilter");
        setFilterBtn.addActionListener(this);
        return setFilterBtn;
    }

    // MODIFIES: this
    // EFFECTS : makes a button to clear filters on the inventory
    private JButton makeClearFilterBtn() {
        JButton clearFiltersBtn = new JButton("Clear Filters");
        clearFiltersBtn.setActionCommand("clearFilters");
        clearFiltersBtn.addActionListener(this);
        return clearFiltersBtn;
    }


 */

    // MODIFIES: this
    // EFFECTS : makes a button to save the inventory
    private JButton makeSaveInventoryBtn() {
        JButton saveInventoryBtn = new JButton("Save Inventory");
        saveInventoryBtn.setActionCommand(SAVE_COMMAND);
        saveInventoryBtn.addActionListener(this);
        return saveInventoryBtn;
    }

    // MODIFIES: this
    // EFFECTS : makes a button to load an inventory
    private JButton makeLoadInventoryBtn() {
        JButton loadInventoryBtn = new JButton("Load Inventory");
        loadInventoryBtn.setActionCommand(LOAD_COMMAND);
        loadInventoryBtn.addActionListener(this);
        return loadInventoryBtn;
    }

    // MODIFIES: this
    // EFFECTS : makes a button to quit the application
    private JButton makeQuitBtn() {
        JButton quitBtn = new JButton("Quit");
        quitBtn.setActionCommand(QUIT_COMMAND);
        quitBtn.addActionListener(this);
        return quitBtn;
    }

// ------------------------------------- CENTER PANEL ---------------------------------------

    // MODIFIES: this
    // EFFECTS : sets up middle panel for the text fields
    private void setupAddShoePanel() {
        setupLabels();

        setupFields();

        setLabelsForFields();

        JPanel labelPane = new JPanel(new GridLayout(0, 1));
        labelPane.add(nameLabel);
        labelPane.add(sizeLabel);
        labelPane.add(shoeSizeLabel);
        labelPane.add(brandLabel);
        labelPane.add(mainColorLabel);
        labelPane.add(conditionLabel);

        JPanel fieldPane = new JPanel(new GridLayout(0, 1));
        fieldPane.add(nameField);
        fieldPane.add(sizeField);
        fieldPane.add(shoeSizeField);
        fieldPane.add(brandField);
        fieldPane.add(mainColorField);
        fieldPane.add(conditionField);

        centerPanel.add(labelPane, BorderLayout.CENTER);
        centerPanel.add(fieldPane, BorderLayout.LINE_END);
    }

    // MODIFIES: this
    // EFFECTS : sets up the labels for the text fields
    private void setLabelsForFields() {
        nameLabel.setLabelFor(nameField);
        sizeLabel.setLabelFor(sizeField);
        shoeSizeLabel.setLabelFor(shoeSizeField);
        brandLabel.setLabelFor(brandField);
        mainColorLabel.setLabelFor(mainColorField);
        conditionLabel.setLabelFor(conditionField);
    }

    // helper for setLabelsForFields()
    // MODIFIES: this
    // EFFECTS : makes labels
    private void setupLabels() {
        nameLabel = new JLabel(nameString);
        sizeLabel = new JLabel(sizeString);
        shoeSizeLabel = new JLabel(shoeSizeString);
        brandLabel = new JLabel(brandString);
        mainColorLabel = new JLabel(mainColorString);
        conditionLabel = new JLabel(conditionString);
    }


    // MODIFIES: this
    // EFFECTS : sets up the text fields
    private void setupFields() {
        setupNameField();
        setupSizeField();
        setupShoeSizeField();
        setupBrandField();
        setupMainColorField();
        setupConditionField();
    }

    // helper for setupFields()
    // MODIFIES: this
    // EFFECTS : sets up the name text field
    private void setupNameField() {
        nameField = new JFormattedTextField();
        nameField.setValue("");
        nameField.setColumns(10);
//        nameField.addPropertyChangeListener("name", this);
    }

    // helper for setupFields()
    // MODIFIES: this
    // EFFECTS : sets up the size text field
    private void setupSizeField() {
        sizeField = new JFormattedTextField();
        sizeField.setValue("");
        sizeField.setColumns(10);
    }

    // helper for setupFields()
    // MODIFIES: this
    // EFFECTS : sets up the shoe size text field
    private void setupShoeSizeField() {
        shoeSizeField = new JFormattedTextField();
        shoeSizeField.setValue("");
        shoeSizeField.setColumns(10);
    }

    // helper for setupFields()
    // MODIFIES: this
    // EFFECTS : sets up the brand text field
    private void setupBrandField() {
        brandField = new JFormattedTextField();
        brandField.setValue("");
        brandField.setColumns(10);
    }

    // helper for setupFields()
    // MODIFIES: this
    // EFFECTS : sets up the main color text field
    private void setupMainColorField() {
        mainColorField = new JFormattedTextField();
        mainColorField.setValue("");
        mainColorField.setColumns(10);
    }

    // helper for setupFields()
    // MODIFIES: this
    // EFFECTS : sets up the condition text field
    private void setupConditionField() {
        conditionField = new JFormattedTextField();
        conditionField.setValue("");
        conditionField.setColumns(10);
    }

// ------------------------------------------------------------------------------------------



    @Override
    // MODIFIES: this
    // EFFECTS : does the command of whatever button is pressed
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (ADD_COMMAND.equals(command)) {
            doAddCommand();
        } else if (REMOVE_COMMAND.equals(command)) {
            doRemoveCommand();
        } else if (SAVE_COMMAND.equals(command)) {
            doSaveCommand();
        } else if (LOAD_COMMAND.equals(command)) {
            doLoadCommand();
        } else if (QUIT_COMMAND.equals(command)) {
            doQuitCommand();
        }
    }

    // helper for actionPerformed()
    // REQUIRES: shoe size field must contain a number
    // MODIFIES: this
    // EFFECTS : adds a shoe to the inventory and tree
    private void doAddCommand() {
        Shoe temp = new Shoe((String)nameField.getValue(),
                (String)sizeField.getValue(),
                Double.parseDouble((String)shoeSizeField.getValue()),
                (String)brandField.getValue(),
                (String)mainColorField.getValue(),
                (String)conditionField.getValue());
        inventory.addShoe(temp);
        treePanel.parseShoe(temp);
    }

    // helper for actionPerformed()
    // MODIFIES: this
    // EFFECTS : removes selected shoe from the inventory and tree
    private void doRemoveCommand() {
        treePanel.removeCurrentNode(inventory);
    }

    // helper for actionPerformed()
    // MODIFIES: this
    // EFFECTS : saves a shoe to the directory
    private void doSaveCommand() {
        try {
            jsonWriter.open();
            jsonWriter.write(inventory);
            jsonWriter.close();
        } catch (FileNotFoundException except) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // helper for actionPerformed()
    // MODIFIES: this
    // EFFECTS : loads a shoe from a directory
    private void doLoadCommand() {
        try {
            inventory.announceLoadedInventory();
            inventory = jsonReader.read();
            for (int i = 0; i < inventory.size(); i++) {
                treePanel.parseShoe(inventory.get(i));
            }
        } catch (IOException except) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // helper for actionPerformed()
    // MODIFIES: this
    // EFFECTS : exits the application
    private void doQuitCommand() {
        printEventLog();
        System.exit(0);
    }

    private void printEventLog() {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event);
        }
    }

    public static void main(String[] args) {
        try {
            new InventoryManagerGUI();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
}
