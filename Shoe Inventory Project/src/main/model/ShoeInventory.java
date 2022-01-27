package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

public class ShoeInventory implements Writable {
    private List<Shoe> inventory;
    private List<Shoe> filteredInventory;
    private int numShoesAdded;
    private boolean isFilterOn;

    public ShoeInventory() {
        this.inventory = new ArrayList<>();
        this.filteredInventory = new ArrayList<>();
        this.numShoesAdded = 0;
        this.isFilterOn = false;
    }

    // MODIFIES: this and shoe
    // EFFECTS : adds shoe to inventory and sets id based on the number of shoes added to the inventory
    public void addShoe(Shoe shoe) {
        shoe.setId(this.numShoesAdded);
        inventory.add(shoe);
        if (!this.isFilterOn) {
            filteredInventory.add(shoe);
        }
        this.numShoesAdded++;

        EventLog.getInstance().logEvent(
                new Event("Added shoe " + shoe.getName() + " to the inventory. ID: " + shoe.getId()));
    }

    // REQUIRES: shoe with id is in inventory
    // MODIFIES: this
    // EFFECTS : remove shoe with matching id from inventory and returns true if done, false otherwise
    public boolean removeShoe(int id) {
        EventLog.getInstance().logEvent(
                new Event("Removed shoe "
                        + getShoeFromId(id).getName()
                        + " from the inventory. ID: " + id));

        return inventory.removeIf(shoe -> (shoe.getId() == id));
    }

    // EFFECTS: returns the size of the current shoeInventory
    public int size() {
        return inventory.size();
    }

    // EFFECTS: returns the shoe at index i
    public Shoe get(int i) {
        return inventory.get(i);
    }

    // REQUIRES: filter exists (refer to filter list in README)
    // MODIFIES: this
    // EFFECTS : filters the shoeInventory with the specified filters
    public void setFilter(String filterType, String setting) {
        this.isFilterOn = true;
        setFilterRequiredFields(filterType, setting);
        setFilterOptionalFields(filterType, setting);
        EventLog.getInstance().logEvent(
                new Event("Set a " + filterType + "filter on the inventory to filter " + setting));
    }

    // MODIFIES: this
    // EFFECTS : filters the shoeInventory with the price range
    public void setFilter(double lower, double upper) {
        this.isFilterOn = true;
        filterPrice(lower, upper);
        EventLog.getInstance().logEvent(
                new Event("Set filter on the inventory.\nMinimum price: "
                        + lower + "\nMaximum price: " + upper));
    }

    // helper for setFilter
    // this is split off from setFilterOptionalFields to satisfy method length requirement
    // MODIFIES: this
    // EFFECTS : filters required fields
    private void setFilterRequiredFields(String filterType, String setting) {
        switch (filterType) {
            case "name":
                filterName(setting);
                break;
            case "size":
                filterSize(setting);
                break;
            case "shoe size":
                filterShoeSize(setting);
                break;
            case "brand":
                filterBrand(setting);
                break;
            case "main color":
                filterMainColor(setting);
                break;
            case "condition":
                filterCondition(setting);
                break;
            case "id":
                filterId(setting);
                break;
        }
    }

    // helper for setFilter
    // this is split off from setFilterRequiredFields to satisfy method length requirement
    // MODIFIES: this
    // EFFECTS : filters optional fields
    private void setFilterOptionalFields(String filterType, String setting) {
        switch (filterType) {
            case "secondary color":
                filterSecondaryColor(setting);
                break;
            case "colorway":
                filterColorway(setting);
                break;
            case "line":
                filterLine(setting);
                break;
            case "version":
                filterVersion(setting);
                break;
            case "collab":
                filterCollab(setting);
                break;
            case "year":
                filterYear(setting);
                break;
        }
    }

    // helper for setFilter
    // MODIFIES: this
    // EFFECTS : removes shoes from filteredInventory that don't match name
    private void filterName(String name) {
        filteredInventory.removeIf(shoe -> (!shoe.getName().equals(name)));
    }

    // helper for setFilter
    // MODIFIES: this
    // EFFECTS : removes shoes from filteredInventory that don't match size
    private void filterSize(String size) {
        filteredInventory.removeIf(shoe -> (!shoe.getSize().equals(size)));
    }

    // helper for setFilter
    // MODIFIES: this
    // EFFECTS : removes shoes from filteredInventory that don't match shoeSize
    private void filterShoeSize(String shoeSize) {
        filteredInventory.removeIf(shoe -> (shoe.getShoeSize() != Double.parseDouble(shoeSize)));
    }

    // helper for setFilter
    // MODIFIES: this
    // EFFECTS : removes shoes from filteredInventory that don't match brand
    private void filterBrand(String brand) {
        filteredInventory.removeIf(shoe -> (!shoe.getBrand().equals(brand)));
    }

    // helper for setFilter
    // MODIFIES: this
    // EFFECTS : removes shoes from filteredInventory that don't match mainColor
    private void filterMainColor(String mainColor) {
        filteredInventory.removeIf(shoe -> (!shoe.getMainColor().equals(mainColor)));
    }

    // helper for setFilter
    // MODIFIES: this
    // EFFECTS : removes shoes from filteredInventory that don't match condition
    private void filterCondition(String condition) {
        filteredInventory.removeIf(shoe -> (!shoe.getCondition().equals(condition)));
    }

    // helper for setFilter
    // MODIFIES: this
    // EFFECTS : removes shoes from filteredInventory that don't match id
    private void filterId(String id) {
        filteredInventory.removeIf(shoe -> (shoe.getId() != Integer.parseInt(id)));
    }

    // helper for setFilter
    // MODIFIES: this
    // EFFECTS : removes shoes from filteredInventory that don't match secondaryColor
    private void filterSecondaryColor(String secondaryColor) {
        filteredInventory.removeIf(shoe -> (!shoe.getSecondaryColor().equals(secondaryColor)));
    }

    // helper for setFilter
    // MODIFIES: this
    // EFFECTS : removes shoes from filteredInventory that don't match colorway
    private void filterColorway(String colorway) {
        filteredInventory.removeIf(shoe -> (!shoe.getColorway().equals(colorway)));
    }

    // helper for setFilter
    // MODIFIES: this
    // EFFECTS : removes shoes from filteredInventory that don't match line
    private void filterLine(String line) {
        filteredInventory.removeIf(shoe -> (!shoe.getLine().equals(line)));
    }

    // helper for setFilter
    // MODIFIES: this
    // EFFECTS : removes shoes from filteredInventory that don't match version
    private void filterVersion(String version) {
        filteredInventory.removeIf(shoe -> (shoe.getVersion() != Integer.parseInt(version)));
    }

    // helper for setFilter
    // MODIFIES: this
    // EFFECTS : removes shoes from filteredInventory that don't match collab
    private void filterCollab(String collab) {
        filteredInventory.removeIf(shoe -> (!shoe.getCollab().equals(collab)));
    }

    // helper for setFilter
    // REQUIRES: year is positive
    // MODIFIES: this
    // EFFECTS : removes shoes from filteredInventory that don't match year
    private void filterYear(String year) {
        filteredInventory.removeIf(shoe -> (shoe.getYear() != Integer.parseInt(year)));
    }

    // helper for setFilter
    // REQUIRES: lower >= 0 and upper > 0
    // MODIFIES: this
    // EFFECTS : removes any shoes in filteredInventory that are not in the range
    private void filterPrice(double lower, double upper) {
        filteredInventory.removeIf(shoe -> (lower > shoe.getPrice() || shoe.getPrice() > upper));
    }

    // MODIFIES: this
    // EFFECTS : resets (clears) filters on filteredInventory
    public void clearFilters() {
        filteredInventory.clear();
        this.isFilterOn = false;
        filteredInventory.addAll(inventory);
    }

    //for test cases only
    // EFFECTS: returns inventory
    public List<Shoe> getInventory() {
        return inventory;
    }

    //for test cases only
    // EFFECTS: returns filteredInventory
    public List<Shoe> getFilteredInventory() {
        return filteredInventory;
    }

    // EFFECTS: returns the shoe with the given id
    public Shoe getShoeFromId(int id) {
        for (Shoe shoe : inventory) {
            if (shoe.getId() == id) {
                return shoe;
            }
        }
        return null;
    }

    /**
     * Data persistence
     */
    public void announceLoadedInventory() {
        EventLog.getInstance().logEvent(new Event("Loaded an inventory."));
    }

    @Override
    // EFFECTS: makes the shoeInventory into a Json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("inventory", inventoryToJson());
        EventLog.getInstance().logEvent(new Event("Saved the inventory."));
        return json;
    }

    // EFFECTS: makes the shoeInventory into a Json array to store it into a json file
    private JSONArray inventoryToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Shoe s : inventory) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }
}
