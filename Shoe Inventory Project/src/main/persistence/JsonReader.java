// some methods took from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import model.Shoe;
import model.ShoeInventory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads shoeInventory from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads ShoeInventory from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ShoeInventory read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseShoeInventory(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses shoe inventory from JSON object and returns it
    private ShoeInventory parseShoeInventory(JSONObject jsonObject) {
        ShoeInventory si = new ShoeInventory();
        addShoes(si, jsonObject);
        return si;
    }

    // MODIFIES: si
    // EFFECTS : parses thingies from JSON object and adds them to shoe inventory
    private void addShoes(ShoeInventory si, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("inventory");
        for (Object json : jsonArray) {
            JSONObject nextShoe = (JSONObject) json;
            addShoe(si, nextShoe);
        }
    }

    // MODIFIES: si
    // EFFECTS : parses shoe from JSON object and adds it to shoe inventory
    private void addShoe(ShoeInventory si, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String size = jsonObject.getString("size");
        double shoeSize = jsonObject.getDouble("shoe size");
        String brand = jsonObject.getString("brand");
        String mainColor = jsonObject.getString("main color");
        String condition = jsonObject.getString("condition");
        int id = jsonObject.getInt("id");
        String secondaryColor = jsonObject.getString("secondary color");
        String colorway = jsonObject.getString("colorway");
        String line = jsonObject.getString("line");
        int version = jsonObject.getInt("version");
        String collab = jsonObject.getString("collab");
        int year = jsonObject.getInt("year");
        double price = jsonObject.getDouble("price");

        Shoe s = new Shoe(name, size, shoeSize, brand, mainColor, condition);
        setShoeProperties(s, id, secondaryColor, colorway, line, version, collab, year, price);

        si.addShoe(s);
        s.setId(id);
    }

    // helper method for addShoe
    // MODIFIES: s
    // EFFECTS : sets properties for Shoe s
    private void setShoeProperties(Shoe s, int id, String secondaryColor, String colorway,
                                   String line, int version, String collab, int year, double price) {
        s.setId(id);
        s.setSecondaryColor(secondaryColor);
        s.setColorway(colorway);
        s.setLine(line);
        s.setVersion(version);
        s.setCollab(collab);
        s.setYear(year);
        s.setPrice(price);
    }
}
