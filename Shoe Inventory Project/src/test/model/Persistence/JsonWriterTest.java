package model.Persistence;

import model.Shoe;
import model.ShoeInventory;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            ShoeInventory si = new ShoeInventory();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyShoeInventory() {
        try {
            ShoeInventory si = new ShoeInventory();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyShoeInventory.json");
            writer.open();
            writer.write(si);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyShoeInventory.json");
            si = reader.read();
            assertEquals(0, si.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralShoeInventory() {
        try {
            ShoeInventory si = new ShoeInventory();
            si.addShoe(new Shoe("test1", "US M", 9.5, "Nike", "white", "new"));
            si.get(0).setId(4);
            si.get(0).setSecondaryColor("white");
            si.get(0).setColorway("test1");
            si.get(0).setLine("test1");
            si.get(0).setVersion(2);
            si.get(0).setCollab("test1");
            si.get(0).setYear(2020);
            si.get(0).setPrice(120);

            si.addShoe(new Shoe("test2", "US W", 11, "Nike", "red", "used"));
            si.get(1).setId(10);
            si.get(1).setSecondaryColor("black");
            si.get(1).setColorway("test2");
            si.get(1).setLine("test2");
            si.get(1).setVersion(1);
            si.get(1).setCollab("test2");
            si.get(1).setYear(1990);
            si.get(1).setPrice(3000);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralShoeInventory.json");
            writer.open();
            writer.write(si);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralShoeInventory.json");
            si = reader.read();
            List<Shoe> shoes = si.getInventory();
            assertEquals(2, shoes.size());
            checkShoe("test1", "US M", 9.5, "Nike", "white", "new",
                    4, "white", "test1", "test1", 2, "test1", 2020, 120,
                    shoes.get(0));
            checkShoe("test2", "US W", 11, "Nike", "red", "used",
                    10, "black", "test2", "test2", 1, "test2", 1990, 3000,
                    shoes.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
