package model.Persistence;

import model.Shoe;
import model.ShoeInventory;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ShoeInventory si = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyShoeInventory() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyShoeInventory.json");
        try {
            ShoeInventory si = reader.read();
            assertEquals(0, si.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralShoeInventory() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralShoeInventory.json");
        try {
            ShoeInventory si = reader.read();
            List<Shoe> shoes = si.getInventory();
            assertEquals(2, shoes.size());
            checkShoe("test1", "US M", 9.5, "Nike", "white", "new",
                    4, "white", "test1", "test1", 2, "test1", 2020, 120,
                    shoes.get(0));
            checkShoe("test2", "US W", 11, "Nike", "red", "used",
                    10, "black", "test2", "test2", 1, "test2", 1990, 3000,
                    shoes.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
