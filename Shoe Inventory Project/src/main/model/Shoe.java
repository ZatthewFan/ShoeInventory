package model;

import org.json.JSONObject;
import persistence.Writable;

public class Shoe implements Writable {
    // required fields
    private String name;
    private String size;
    private double shoeSize;
    private String brand;
    private String mainColor;
    private String condition;

    // does not get initialized by constructor
    // every shoe ever added will have a new unique id
    private int id;

    // optional fields
    private String secondaryColor;
    private String colorway;
    private String line;
    private int version;
    private String collab;
    private int year;
    private double price;

    // REQUIRES: shoeSize is a multiple of 0.5 and non-negative number
    // EFFECTS: sets required fields except for id
    public Shoe(String name, String size, double shoeSize, String brand, String mainColor, String condition) {
        this.name = name;
        this.size = size;
        this.shoeSize = shoeSize;
        this.brand = brand;
        this.mainColor = mainColor;
        this.condition = condition;

        this.secondaryColor = "";
        this.colorway = "";
        this.line = "";
        this.version = -1;
        this.collab = "";
        this.year = -1;
        this.price = -1;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public double getShoeSize() {
        return shoeSize;
    }

    public String getBrand() {
        return brand;
    }

    public String getColorway() {
        return colorway;
    }

    public String getMainColor() {
        return mainColor;
    }

    public String getCondition() {
        return condition;
    }

    public int getId() {
        return id;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public String getLine() {
        return line;
    }

    public int getVersion() {
        return version;
    }

    public String getCollab() {
        return collab;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public void setColorway(String colorway) {
        this.colorway = colorway;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setCollab(String collab) {
        this.collab = collab;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    //test purposes
    // EFFECTS: checks if required fields of the shoes are the same
    public boolean isSame(Shoe other) {
        return (this.name.equals(other.getName()))
                && (this.size.equals(other.getSize()))
                && (this.shoeSize == other.getShoeSize()
                && (this.brand.equals(other.brand)))
                && (this.mainColor.equals(other.getMainColor()))
                && (this.condition.equals(other.getCondition()));
    }



    /**
     * Data persistence
     */
    @Override
    // EFFECTS: makes a shoe into a Json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("size", size);
        json.put("shoe size", shoeSize);
        json.put("brand", brand);
        json.put("main color", mainColor);
        json.put("condition", condition);
        json.put("id", id);
        json.put("secondary color", secondaryColor);
        json.put("colorway", colorway);
        json.put("line", line);
        json.put("version", version);
        json.put("collab", collab);
        json.put("year", year);
        json.put("price", price);
        return json;
    }
}
