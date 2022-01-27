package ui;

import model.Shoe;
import model.ShoeInventory;
import persistence.JsonWriter;
import persistence.JsonReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class InventoryManager {
    private static final String JSON_STORE = "./data/shoeInventory.json";
    private Scanner input;
    private ShoeInventory inventory;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private boolean madeChanges = false;

    // EFFECTS: runs the inventory manager application
    public InventoryManager() throws FileNotFoundException {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runInventoryManager();
    }

    // MODIFIES: this
    // EFFECTS : processes user input
    private void runInventoryManager() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();

            if (command.equals("0")) {
                doSaveInventory(true);
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye");
    }

    @SuppressWarnings("methodlength")
    // MODIFIES: this
    // EFFECTS : processes user command
    private void processCommand(String command) {
        switch (command) {
            case "1":
                doAddShoe();
                break;
            case "2":
                doRemoveShoe();
                break;
            case "3":
                doSetProperty();
                break;
            case "4":
                doPrintInventory();
                break;
            case "5":
                doSetFilters();
                break;
            case "6":
                doClearFilters();
                break;
            case "7":
                doSaveInventory(false);
                break;
            case "8":
                doLoadInventory();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS : initializes shoe inventory
    private void init() {
        inventory = new ShoeInventory();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nWhat would you like to do?:");
        System.out.println("\t1 -> add a shoe");
        System.out.println("\t2 -> remove a shoe");
        System.out.println("\t3 -> add a property");
        System.out.println("\t4 -> print your inventory");
        System.out.println("\t5 -> set filters");
        System.out.println("\t6 -> clear filters");
        System.out.println("\t7 -> save inventory");
        System.out.println("\t8 -> load inventory");
        System.out.println("\t0 -> quit");
    }

    // MODIFIES: this
    // EFFECTS : adds a shoe to the user's inventory
    private void doAddShoe() {
        System.out.println("Adding a shoe to your inventory... \n (enter nothing to not add shoe)");

        System.out.println("Shoe name: ");
        String name = input.next();

        System.out.println("Size (US M, US W, US GS, etc.): ");
        String size = input.next();

        System.out.println("Shoe size: ");
        String shoeSize = input.next();

        System.out.println("Brand: ");
        String brand = input.next();

        System.out.println("Main color: ");
        String mainColor = input.next();

        System.out.println("Condition (new, used, beat): ");
        String condition = input.next();

        addShoeToInventory(name, size, shoeSize, brand, mainColor, condition);
    }

    // MODIFIES: this
    // EFFECTS : checks if everything is entered properly and adds shoe to inventory
    private void addShoeToInventory(String name,
                                    String size,
                                    String shoeSize,
                                    String brand,
                                    String mainColor,
                                    String condition) {
        try {
            if (name.equals("") || size.equals("") || shoeSize.equals("") || brand.equals("")
                    || mainColor.equals("") || condition.equals("")) {
                System.out.println("Detected one or more empty input. Shoe not added.");
            } else {
                Shoe temp = new Shoe(name, size, Double.parseDouble(shoeSize), brand, mainColor, condition);
                inventory.addShoe(temp);
                System.out.println("Added shoe to inventory! Shoe id: " + temp.getId());
                madeChanges = true;
            }
        } catch (NumberFormatException e) {
            System.out.println("Shoe size must be a number!");
        }
    }

    // MODIFIES: this
    // EFFECTS : removes a shoe from the user's inventory
    private void doRemoveShoe() {
        System.out.println("Removing a shoe from your inventory...");

        System.out.println("Please give me the shoe's id: ");
        System.out.println("(enter nothing to exit)\n");
        String removeId = input.next();

        if (removeId.equals("")) {
            System.out.println("Going back to menu...");
        } else if (inventory.removeShoe(Integer.parseInt(removeId))) {
            System.out.println("Removed the shoe from your inventory!");
            madeChanges = true;
        } else {
            System.out.println("Shoe does not exist.");
        }
    }

    // MODIFIES: this
    // EFFECTS : sets properties for optional properties
    private void doSetProperty() {
        System.out.println("Which shoe would you like to add a property to? (enter id, enter nothing to return): ");
        try {
            String setPropertyId = input.next();
            searchFindSet(setPropertyId);
            madeChanges = true;
        } catch (NullPointerException e) {
            System.out.println("Inventory is empty. Returning to menu...");
        } catch (NumberFormatException e) {
            System.out.println("Must be a id! (number)");
        }
    }

    // helper for doSetProperty
    // MODIFIES: this
    // EFFECTS : finds a shoe with matching id and sets the property
    private void searchFindSet(String id) {
        Shoe gettingPropertySet;
        if (id.equals("")) {
            System.out.println("Returning to menu...");
        } else {
            printShoe(inventory.getShoeFromId(Integer.parseInt(id)));
            ArrayList<Integer> listOfId = new ArrayList<>();

            for (Shoe shoe : inventory.getInventory()) {
                listOfId.add(shoe.getId());
            }

            if (listOfId.contains(Integer.parseInt(id))) {
                //TODO: maybe change to binary search?
                for (Shoe shoe : inventory.getInventory()) {
                    if (shoe.getId() == Integer.parseInt(id)) {
                        gettingPropertySet = shoe;
                        printListOfPropertiesToSet();

                        int choice = input.nextInt();
                        changeProperty(gettingPropertySet, choice);
                    }
                }
            } else {
                System.out.println("Sorry, shoe does not exist.");
            }
        }
    }

    // EFFECTS: prints out a list of properties that the user can set
    private void printListOfPropertiesToSet() {
        System.out.println("\t1 -> Secondary color");
        System.out.println("\t2 -> Colorway");
        System.out.println("\t3 -> Line");
        System.out.println("\t4 -> Version");
        System.out.println("\t5 -> Collab");
        System.out.println("\t6 -> Year");
        System.out.println("\t7 -> Price");
        System.out.println("\t0 -> Exit");
    }

    // helper for doSetProperty
    // MODIFIES: this
    // EFFECTS : changes a user specified property
    private void changeProperty(Shoe gettingPropertySet, int choice) {
        if (choice == 0) {
            System.out.println("Returning to menu...");
        } else if (choice == 1) {
            runChangePropertyCommand1(gettingPropertySet);
        } else if (choice == 2) {
            runChangePropertyCommand2(gettingPropertySet);
        } else if (choice == 3) {
            runChangePropertyCommand3(gettingPropertySet);
        } else if (choice == 4) {
            runChangePropertyCommand4(gettingPropertySet);
        } else if (choice == 5) {
            runChangePropertyCommand5(gettingPropertySet);
        } else if (choice == 6) {
            runChangePropertyCommand6(gettingPropertySet);
        } else if (choice == 7) {
            runChangePropertyCommand7(gettingPropertySet);
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // helper for changeProperty
    // MODIFIES: this
    // EFFECTS : sets secondary color for a selected shoe
    private void runChangePropertyCommand1(Shoe gettingPropertySet) {
        System.out.println("What would you like to set the secondary color to?: ");
        String secondaryColor = input.next();
        gettingPropertySet.setSecondaryColor(secondaryColor);
    }

    // helper for changeProperty
    // MODIFIES: this
    // EFFECTS : sets colorway for a selected shoe
    private void runChangePropertyCommand2(Shoe gettingPropertySet) {
        System.out.println("What would you like to set the colorway to?: ");
        String colorway = input.next();
        gettingPropertySet.setColorway(colorway);
    }

    // helper for changeProperty
    // MODIFIES: this
    // EFFECTS : sets line for a selected shoe
    private void runChangePropertyCommand3(Shoe gettingPropertySet) {
        System.out.println("What would you like to set the line to?: ");
        String line = input.next();
        gettingPropertySet.setLine(line);
    }

    // helper for changeProperty
    // MODIFIES: this
    // EFFECTS : sets version for a selected shoe
    private void runChangePropertyCommand4(Shoe gettingPropertySet) {
        System.out.println("What would you like to set the version to?: ");
        int version = input.nextInt();
        gettingPropertySet.setVersion(version);
    }

    // helper for changeProperty
    // MODIFIES: this
    // EFFECTS : sets collab for a selected shoe
    private void runChangePropertyCommand5(Shoe gettingPropertySet) {
        System.out.println("What would you like to set the collab to?: ");
        String collab = input.next();
        gettingPropertySet.setCollab(collab);
    }

    // helper for changeProperty
    // MODIFIES: this
    // EFFECTS : sets year for a selected shoe
    private void runChangePropertyCommand6(Shoe gettingPropertySet) {
        System.out.println("What would you like to set the release year to?: ");
        int year = input.nextInt();
        gettingPropertySet.setYear(year);
    }

    // helper for changeProperty
    // MODIFIES: this
    // EFFECTS : sets price for a selected shoe
    private void runChangePropertyCommand7(Shoe gettingPropertySet) {
        System.out.println("What would you like to change the price to?: ");
        double price = input.nextDouble();
        gettingPropertySet.setPrice(price);
    }


    // EFFECTS: prints out either the regular inventory or filtered inventory by the user's choice
    public void doPrintInventory() {
        if (inventory.getInventory().size() > 0) {
            System.out.println("Which inventory would you like to print?: ");
            System.out.println("\t1 -> Regular inventory");
            System.out.println("\t2 -> Filtered inventory");
            System.out.println("\t0 -> exit");
            String choice = input.next();

            switch (choice) {
                case "0":
                    System.out.println("Returning to menu...");
                    break;
                case "1":
                    printFilteredInventory(false);
                    break;
                case "2":
                    printFilteredInventory(true);
                    break;
                default:
                    System.out.println("Selection not valid...");
                    break;
            }
        } else {
            System.out.println("Inventory is empty.");
        }
    }

    // EFFECTS: prints filtered inventory with filters if true
    //          prints unfiltered inventory if false
    private void printFilteredInventory(boolean filter) {
        if (filter) {
            printFilteredList(inventory);
        } else {
            printUnfilteredList(inventory);
        }
    }

    // EFFECTS: prints out unfiltered list of shoeInventory
    private void printUnfilteredList(ShoeInventory inv) {
        for (Shoe shoe : inv.getInventory()) {
            printShoe(shoe);
        }
    }

    // EFFECTS: prints out a filtered list of shoeInventory
    private void printFilteredList(ShoeInventory inv) {
        for (Shoe shoe : inv.getFilteredInventory()) {
            printShoe(shoe);
        }
    }

    // EFFECTS: prints out the information of a shoe
    private void printShoe(Shoe shoe) {
        System.out.println("-------------------------------------------------------------");
        printRequiredProperties(shoe);
        printOptionalProperties(shoe);
        System.out.println("id: " + shoe.getId());
        System.out.println("-------------------------------------------------------------");
    }

    // helper for printFilteredList and printUnfilteredList
    // EFFECTS: prints out the required properties of a shoe
    private void printRequiredProperties(Shoe shoe) {
        System.out.println("Name: " + shoe.getName());
        System.out.println("Size: " + shoe.getSize() + " " + shoe.getShoeSize());
        System.out.println("Brand: " + shoe.getBrand());
        System.out.println("Colorway: " + shoe.getColorway());
        System.out.println("Condition: " + shoe.getCondition());
    }

    // helper for printFilteredList and printUnfilteredList
    // EFFECTS: prints out the optional properties of a shoe
    private void printOptionalProperties(Shoe shoe) {
        if (shoe.getSecondaryColor().equals("")) {
            System.out.println("Color: " + shoe.getMainColor());
        } else {
            System.out.println("Color: " + shoe.getMainColor() + " and " + shoe.getSecondaryColor());
        }
        if (!shoe.getLine().equals("") && shoe.getVersion() >= 1) {
            System.out.println("Line: " + shoe.getLine() + " " + shoe.getVersion());
        }
        if (!shoe.getCollab().equals("")) {
            System.out.println("Collab: " + shoe.getCollab());
        }
        if (shoe.getYear() != -1) {
            System.out.println("Release Year: " + shoe.getYear());
        }
        if (shoe.getPrice() != -1) {
            System.out.println("Price: " + shoe.getPrice());
        }
    }

    // MODIFIES: this
    // EFFECTS : sets filters
    public void doSetFilters() {
        System.out.println("What filter would you like to set?");

        System.out.println("\t1 -> Name");
        System.out.println("\t2 -> Size");
        System.out.println("\t3 -> Shoe size");
        System.out.println("\t4 -> Brand");
        System.out.println("\t5 -> Main color");
        System.out.println("\t6 -> Condition");
        System.out.println("\t7 -> Secondary color");
        System.out.println("\t8 -> Colorway");
        System.out.println("\t9 -> Line");
        System.out.println("\ta -> Version");
        System.out.println("\tb -> Collab");
        System.out.println("\tc -> Year");
        System.out.println("\td -> Price");
        System.out.println("\te -> id");
        System.out.println("\t0 -> exit");
        String choice = input.next();
        choice = choice.toLowerCase();
        makeFilterChoice(choice);
    }

    // helper for doSetFilters
    // MODIFIES: this
    // EFFECTS : makes the choice to set filters
    @SuppressWarnings("methodlength")
    private void makeFilterChoice(String choice) {
        switch (choice) {
            case "0":
                System.out.println("Returning to menu...");
                break;
            case "1":
                setThisFilter("name");
                break;
            case "2":
                setThisFilter("size");
                break;
            case "3":
                setThisFilter("shoe size");
                break;
            case "4":
                setThisFilter("brand");
                break;
            case "5":
                setThisFilter("main color");
                break;
            case "6":
                setThisFilter("condition");
                break;
            case "7":
                setThisFilter("secondary color");
                break;
            case "8":
                setThisFilter("colorway");
                break;
            case "9":
                setThisFilter("line");
                break;
            case "a":
                setThisFilter("version");
                break;
            case "b":
                setThisFilter("collab");
                break;
            case "c":
                setThisFilter("year");
                break;
            case "d":
                setThisFilter("price");
                break;
            case "e":
                setThisFilter("id");
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    // helper for make filter choice
    // MODIFIES: this
    // EFFECTS : sets a filter f
    private void setThisFilter(String f) {
        System.out.println("Enter " + f + " to filter (enter nothing to return to menu; no filter is set): ");
        if (!f.equals("price")) {
            String toFilter = input.next();
            if (toFilter.equals("")) {
                System.out.println("Returning to menu...");
            }
            inventory.setFilter(f, toFilter);
            madeChanges = true;
        } else {
            System.out.println("Enter minimum price: ");
            String min = input.next();
            System.out.println("Enter maximum price: ");
            String max = input.next();
            if (isDouble(min) && isDouble(max)) {
                inventory.setFilter(Double.parseDouble(min), Double.parseDouble(max));
                madeChanges = true;
            } else {
                System.out.println("Price must be a number!");
            }
        }
    }

    // from: https://stackoverflow.com/questions/3133770/how-to-find-out-if-the-value-contained-in-a-string-is-double-or-not/3133797
    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS : clears filters from filtered list
    public void doClearFilters() {
        System.out.println("Are you sure?");
        System.out.println("\t1 -> Yes");
        System.out.println("\t2 -> No");

        String choice = input.next();

        switch (choice) {
            case "1":
                madeChanges = true;
                inventory.clearFilters();
                System.out.println("Cleared all filters");
                break;
            case "2":
                System.out.println("Returning to menu...");
                break;
            default:
                System.out.println("Not a valid option");
                doClearFilters();
        }
    }



    // EFFECTS: prompts the user to choose an option
    //          between setting a name and saving the inventory or quitting without saving
    private void doSaveInventory(boolean isExiting) {
        if (madeChanges) {
            if (isExiting) {
                System.out.println("Would you like to save before exiting?");
            } else {
                System.out.println("Are you sure you want to save?");
            }
            System.out.println("\t1 -> Yes");
            System.out.println("\t2 -> No");

            String choice = input.next();

            switch (choice) {
                case "1":
                    saveShoeInventory();
                    break;
                case "2":
                    if (!isExiting) {
                        System.out.println("Returning to menu...");
                    }
                    break;
                default:
                    System.out.println("Not a valid option");
                    doSaveInventory(isExiting);
            }
        }
    }

    // EFFECTS: saves the inventory to file
    private void saveShoeInventory() {
        try {
            jsonWriter.open();
            jsonWriter.write(inventory);
            jsonWriter.close();
            System.out.println("Saved inventory to " + JSON_STORE);
            madeChanges = false;
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: prompts the user to choose an option
    //          between loading an inventory with a given name or go back to menu
    public void doLoadInventory() {
        System.out.println("Are you sure you want to load a saved inventory?");
        System.out.println("\t1 -> Yes");
        System.out.println("\t2 -> No");

        String choice = input.next();

        switch (choice) {
            case "1":
                loadShoeInventory();
                break;
            case "2":
                System.out.println("Returning to menu...");
                break;
            default:
                System.out.println("Not a valid option");
                doLoadInventory();
        }
    }

    // EFFECTS: loads an inventory with the given name
    private void loadShoeInventory() {
        try {
            inventory.announceLoadedInventory();
            inventory = jsonReader.read();
            System.out.println("Loaded inventory from " + JSON_STORE);
            madeChanges = false;
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    //TODO: find a way to save and load different inventories
}
