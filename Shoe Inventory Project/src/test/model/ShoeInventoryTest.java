package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShoeInventoryTest {
    public ShoeInventory inv;

    @BeforeEach
    public void setup() {
        inv = new ShoeInventory();
    }

    public void setupOneShoe() {
        inv.addShoe(new Shoe("n", "US M", 9.5, "b", "white", "new"));
    }

    //improved setupTenShoes
    public void setupTenShoes() {
        char name = 'a';
        String size;
        double shoeSize = 1;
        String brand;
        String mainColor;
        String condition;
        for (int i = 0; i < 10; i++) {
            //required fields
            if (i % 2 == 0) {
                size = "US M";
            } else {
                size = "US W";
            }
            if (i % 3 == 0) {
                brand = "Adidas";
            } else {
                brand = "Nike";
            }
            if (i % 5 == 0) {
                mainColor = "black";
            } else {
                mainColor = "white";
            }
            if (i >= 5) {
                condition = "new";
            } else {
                condition = "used";
            }

            inv.addShoe(new Shoe(Character.toString(name), size, shoeSize, brand, mainColor, condition));

            //optional fields
            if (i >= 7) {
                inv.getInventory().get(i).setSecondaryColor("green");
            }
            if (i == 1) {
                inv.getInventory().get(i).setColorway("white nylon");
            }
            if (7 <= i && i <= 8 && inv.getInventory().get(i).getMainColor().equals("white")) {
                inv.getInventory().get(i).setLine("Mickeys");
                inv.getInventory().get(i).setVersion(1);
            }
            if (i == 2) {
                inv.getInventory().get(i).setCollab("Offwhite");
            }
            if (i == 0) {
                inv.getInventory().get(i).setYear(1995);
            }
            if (5 <= i && i <= 7) {
                inv.getInventory().get(i).setPrice(100+(10*i));
            }

            name++;
            shoeSize += 0.5;
        }
    }

    public void printSetupTenShoes() {
        setupTenShoes();

        for (int i = 0; i < 10; i ++) {
            System.out.print(inv.get(i).getName() + " ");
            System.out.print(inv.get(i).getSize() + " ");
            System.out.print(inv.get(i).getShoeSize() + " ");
            System.out.print(inv.get(i).getBrand() + " ");
            System.out.print(inv.get(i).getMainColor() + " ");
            System.out.print(inv.get(i).getCondition() + " ");
            System.out.println();
        }
    }

    public void printInv() {
        setupTenShoes();
        for (int i = 0; i < inv.size(); i ++) {
            System.out.print(inv.get(i).getName() + " ");
            System.out.print(inv.get(i).getSize() + " ");
            System.out.print(inv.get(i).getShoeSize() + " ");
            System.out.print(inv.get(i).getBrand() + " ");
            System.out.print(inv.get(i).getMainColor() + " ");
            System.out.print(inv.get(i).getCondition() + " ");
            System.out.println();
        }
    }

    @Test
    public void addShoeTestEmpty() {
        assertEquals(0, inv.size());
        setupOneShoe();
        assertEquals(1, inv.size());
        assertEquals(0, inv.get(0).getId());
    }

    @Test
    public void addShoeTestNotEmpty() {
        assertEquals(0, inv.size());
        setupTenShoes();
        assertEquals(10, inv.size());
        for (int i = 0; i < inv.size(); i++) {
            assertEquals(i, inv.get(i).getId());
        }
    }

    @Test
    public void removeShoeTestShoeDoesNotExistOnlyOne() {
        setupOneShoe();
        assertFalse(inv.removeShoe(1));
        assertEquals(1, inv.size());
    }

    @Test
    public void removeShoeTestShoeDoesNotExistMultiple() {
        setupTenShoes();
        assertFalse(inv.removeShoe(10));
        assertEquals(10, inv.size());
    }

    @Test
    public void removeShoeTestShoeExistsOnlyOne() {
        setupOneShoe();
        assertTrue(inv.removeShoe(0));
        assertEquals(0, inv.size());
    }

    @Test
    public void removeShoeTestShoeExistsMultiple() {
        setupTenShoes();
        assertTrue(inv.removeShoe(9));
        assertEquals(9, inv.size());
    }

    @Test
    public void removeShoeTestRemoveSameShoe() {
        setupTenShoes();
        assertTrue(inv.removeShoe(5));
        assertEquals(9, inv.size());

        assertFalse(inv.removeShoe(5));
        assertEquals(9, inv.size());
    }

    @Test
    public void removeShoeTestRemoveMultipleShoes() {
        setupTenShoes();
        assertTrue(inv.removeShoe(2));
        assertEquals(9, inv.size());

        assertTrue(inv.removeShoe(3));
        assertEquals(8, inv.size());
    }

    @Test
    public void setFilterNoFilter() {
        setupTenShoes();
        assertEquals(10, inv.size());

        assertTrue(inv.get(0).isSame(new Shoe("a", "US M", 1.0, "Adidas", "black", "used")));
        assertTrue(inv.get(1).isSame(new Shoe("b", "US W", 1.5, "Nike", "white", "used")));
        assertTrue(inv.get(2).isSame(new Shoe("c", "US M", 2.0, "Nike", "white", "used")));
        assertTrue(inv.get(3).isSame(new Shoe("d", "US W", 2.5, "Adidas", "white", "used")));
        assertTrue(inv.get(4).isSame(new Shoe("e", "US M", 3.0, "Nike", "white", "used")));
        assertTrue(inv.get(5).isSame(new Shoe("f", "US W", 3.5, "Nike", "black", "new")));
        assertTrue(inv.get(6).isSame(new Shoe("g", "US M", 4.0, "Adidas", "white", "new")));
        assertTrue(inv.get(7).isSame(new Shoe("h", "US W", 4.5, "Nike", "white", "new")));
        assertTrue(inv.get(8).isSame(new Shoe("i", "US M", 5.0, "Nike", "white", "new")));
        assertTrue(inv.get(9).isSame(new Shoe("j", "US W", 5.5, "Adidas", "white", "new")));
    }

    @Test
    public void setFilterOneFilterName() {
        setupTenShoes();
        inv.setFilter("name", "d");

        assertEquals(1, inv.getFilteredInventory().size());

        assertEquals(inv.get(3), inv.getFilteredInventory().get(0));
    }

    @Test
    public void setFilterOneFilterSize() {
        setupTenShoes();
        inv.setFilter("size", "US M");

        assertEquals(5, inv.getFilteredInventory().size());

        assertEquals(inv.get(0), inv.getFilteredInventory().get(0));
        assertEquals(inv.get(2), inv.getFilteredInventory().get(1));
        assertEquals(inv.get(4), inv.getFilteredInventory().get(2));
        assertEquals(inv.get(6), inv.getFilteredInventory().get(3));
        assertEquals(inv.get(8), inv.getFilteredInventory().get(4));
    }

    @Test
    public void setFilterOneFilterShoeSize() {
        setupTenShoes();
        inv.setFilter("shoe size", "4.0");

        assertEquals(1, inv.getFilteredInventory().size());

        assertEquals(inv.get(6), inv.getFilteredInventory().get(0));
    }

    @Test
    public void setFilterOneFilterBrand() {
        setupTenShoes();
        inv.setFilter("brand", "Adidas");

        assertEquals(4, inv.getFilteredInventory().size());

        assertEquals(inv.get(0), inv.getFilteredInventory().get(0));
        assertEquals(inv.get(3), inv.getFilteredInventory().get(1));
        assertEquals(inv.get(6), inv.getFilteredInventory().get(2));
        assertEquals(inv.get(9), inv.getFilteredInventory().get(3));
    }

    @Test
    public void setFilterOneFilterMainColor() {
        setupTenShoes();
        inv.setFilter("main color", "black");

        assertEquals(2, inv.getFilteredInventory().size());

        assertEquals(inv.get(0), inv.getFilteredInventory().get(0));
        assertEquals(inv.get(5), inv.getFilteredInventory().get(1));
    }

    @Test
    public void setFilterOneFilterCondition() {
        setupTenShoes();
        inv.setFilter("condition", "new");

        assertEquals(5, inv.getFilteredInventory().size());

        assertEquals(inv.get(5), inv.getFilteredInventory().get(0));
        assertEquals(inv.get(6), inv.getFilteredInventory().get(1));
        assertEquals(inv.get(7), inv.getFilteredInventory().get(2));
        assertEquals(inv.get(8), inv.getFilteredInventory().get(3));
        assertEquals(inv.get(9), inv.getFilteredInventory().get(4));
    }

    @Test
    public void setFilterOneFilterId() {
        setupTenShoes();
        inv.setFilter("id", "5");

        assertEquals(1, inv.getFilteredInventory().size());

        assertEquals(inv.get(5), inv.getFilteredInventory().get(0));
    }

    @Test
    public void setFilterOneFilterSecondaryColor() {
        setupTenShoes();
        inv.setFilter("secondary color", "green");

        assertEquals(3, inv.getFilteredInventory().size());

        assertEquals(inv.get(7), inv.getFilteredInventory().get(0));
        assertEquals(inv.get(8), inv.getFilteredInventory().get(1));
        assertEquals(inv.get(9), inv.getFilteredInventory().get(2));
    }

    @Test
    public void setFilterOneFilterColorway() {
        setupTenShoes();
        inv.setFilter("colorway", "white nylon");

        assertEquals(1, inv.getFilteredInventory().size());

        assertEquals(inv.get(1), inv.getFilteredInventory().get(0));
    }

    @Test
    public void setFilterOneFilterLine() {
        setupTenShoes();
        inv.setFilter("line", "Mickeys");

        assertEquals(2, inv.getFilteredInventory().size());

        assertEquals(inv.get(7), inv.getFilteredInventory().get(0));
        assertEquals(inv.get(8), inv.getFilteredInventory().get(1));
    }

    @Test
    public void setFilterOneFilterVersion() {
        setupTenShoes();
        inv.setFilter("version", "1");

        assertEquals(2, inv.getFilteredInventory().size());

        assertEquals(inv.get(7), inv.getFilteredInventory().get(0));
        assertEquals(inv.get(8), inv.getFilteredInventory().get(1));
    }

    @Test
    public void setFilterOneFilterCollab() {
        setupTenShoes();
        inv.setFilter("collab", "Offwhite");

        assertEquals(1, inv.getFilteredInventory().size());

        assertEquals(inv.get(2), inv.getFilteredInventory().get(0));
    }

    @Test
    public void setFilterOneFilterYear() {
        setupTenShoes();
        inv.setFilter("year", "1995");

        assertEquals(1, inv.getFilteredInventory().size());

        assertEquals(inv.get(0), inv.getFilteredInventory().get(0));
    }

    @Test
    public void setFilterOneFilterPrice() {
        setupTenShoes();
        inv.setFilter(160, 170);

        assertEquals(2, inv.getFilteredInventory().size());

        assertEquals(inv.get(6), inv.getFilteredInventory().get(0));
        assertEquals(inv.get(7), inv.getFilteredInventory().get(1));
    }

    @Test
    public void setFilterOneFilterLowerPrice() {
        setupTenShoes();
        inv.setFilter(0, 10);

        assertEquals(0, inv.getFilteredInventory().size());
    }

    @Test
    public void setFilterOneFilterHigherPrice() {
        setupTenShoes();
        inv.setFilter(1000, 2000);

        assertEquals(0, inv.getFilteredInventory().size());
    }

    @Test
    public void setFilterTwoFiltersSizeBrand() {
        setupTenShoes();
        inv.setFilter("size", "US M");
        inv.setFilter("brand", "Nike");

        assertEquals(3, inv.getFilteredInventory().size());

        assertEquals(inv.get(2), inv.getFilteredInventory().get(0));
        assertEquals(inv.get(4), inv.getFilteredInventory().get(1));
        assertEquals(inv.get(8), inv.getFilteredInventory().get(2));
    }

    @Test
    public void setFilterThreeFiltersBrandMainColorPrice() {
        setupTenShoes();
        inv.setFilter("brand", "Nike");
        inv.setFilter("main color", "white");
        inv.setFilter(100, 170);

        assertEquals(1, inv.getFilteredInventory().size());

        assertEquals(inv.get(7), inv.getFilteredInventory().get(0));
    }

    @Test
    public void clearFilterTest() {
        setupTenShoes();
        inv.setFilter("brand", "Nike");
        inv.setFilter("main color", "white");
        inv.setFilter(100, 170);

        assertEquals(1, inv.getFilteredInventory().size());

        assertEquals(inv.get(7), inv.getFilteredInventory().get(0));

        inv.clearFilters();

        assertEquals(10, inv.getFilteredInventory().size());

        assertTrue(inv.get(0).isSame(new Shoe("a", "US M", 1.0, "Adidas", "black", "used")));
        assertTrue(inv.get(1).isSame(new Shoe("b", "US W", 1.5, "Nike", "white", "used")));
        assertTrue(inv.get(2).isSame(new Shoe("c", "US M", 2.0, "Nike", "white", "used")));
        assertTrue(inv.get(3).isSame(new Shoe("d", "US W", 2.5, "Adidas", "white", "used")));
        assertTrue(inv.get(4).isSame(new Shoe("e", "US M", 3.0, "Nike", "white", "used")));
        assertTrue(inv.get(5).isSame(new Shoe("f", "US W", 3.5, "Nike", "black", "new")));
        assertTrue(inv.get(6).isSame(new Shoe("g", "US M", 4.0, "Adidas", "white", "new")));
        assertTrue(inv.get(7).isSame(new Shoe("h", "US W", 4.5, "Nike", "white", "new")));
        assertTrue(inv.get(8).isSame(new Shoe("i", "US M", 5.0, "Nike", "white", "new")));
        assertTrue(inv.get(9).isSame(new Shoe("j", "US W", 5.5, "Adidas", "white", "new")));
    }

    @Test
    public void addShoeWhileUsingFiltersTest() {
        setupTenShoes();
        inv.setFilter("size", "US M");
        inv.setFilter("brand", "Nike");

        assertEquals(3, inv.getFilteredInventory().size());

        assertEquals(inv.get(2), inv.getFilteredInventory().get(0));
        assertEquals(inv.get(4), inv.getFilteredInventory().get(1));
        assertEquals(inv.get(8), inv.getFilteredInventory().get(2));

        inv.addShoe(new Shoe("dummy", "US M", 0.5, "Dummy", "rainbow", "beat"));

        assertEquals(3, inv.getFilteredInventory().size());
        assertEquals(11, inv.getInventory().size());

        inv.clearFilters();
        assertEquals(11, inv.getFilteredInventory().size());
        assertEquals(inv.get(10), inv.getFilteredInventory().get(10));
    }

    @Test
    public void getShoeFromIdTestExists() {
        setupTenShoes();

        assertEquals(inv.get(5), inv.getShoeFromId(5));
    }

    @Test
    public void getShoeFromIdTestDoesNotExist() {
        setupTenShoes();

        assertNull(inv.getShoeFromId(10));
    }
}
