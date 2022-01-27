package model.Persistence;

import model.Shoe;
import model.ShoeInventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkShoe(String name, String size, double shoeSize, String brand,
                             String mainColor, String condition, int id, String secondaryColor,
                             String colorway, String line, int version,
                             String collab, int year, double price,
                             Shoe shoe) {
        assertEquals(name, shoe.getName());
        assertEquals(size, shoe.getSize());
        assertEquals(shoeSize, shoe.getShoeSize());
        assertEquals(brand, shoe.getBrand());
        assertEquals(mainColor, shoe.getMainColor());
        assertEquals(condition, shoe.getCondition());
        assertEquals(id, shoe.getId());
        assertEquals(secondaryColor, shoe.getSecondaryColor());
        assertEquals(colorway, shoe.getColorway());
        assertEquals(line, shoe.getLine());
        assertEquals(version, shoe.getVersion());
        assertEquals(collab, shoe.getCollab());
        assertEquals(year, shoe.getYear());
        assertEquals(price, shoe.getPrice());
    }
}
