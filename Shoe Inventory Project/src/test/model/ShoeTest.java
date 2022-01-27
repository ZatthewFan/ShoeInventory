package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShoeTest {
    Shoe shoe;

    @BeforeEach
    public void setup() {
        shoe = new Shoe("dummy", "US M", 0.5, "Dummy", "rainbow", "beat");
    }

    @Test
    public void isSameTestYes() {
        Shoe test = new Shoe("dummy", "US M", 0.5, "Dummy", "rainbow", "beat");

        assertTrue(shoe.isSame(test));
    }

    @Test
    public void isSameTestNoName() {
        Shoe test = new Shoe("a", "a", 1, "a", "a", "a");

        assertFalse(shoe.isSame(test));
    }

    @Test
    public void isSameTestNoSize() {
        Shoe test = new Shoe("dummy", "a", 1, "a", "a", "a");

        assertFalse(shoe.isSame(test));
    }

    @Test
    public void isSameTestNoShoeSize() {
        Shoe test = new Shoe("dummy", "US M", 1, "a", "a", "a");

        assertFalse(shoe.isSame(test));
    }

    @Test
    public void isSameTestNoBrand() {
        Shoe test = new Shoe("dummy", "US M", 0.5, "a", "a", "a");

        assertFalse(shoe.isSame(test));
    }

    @Test
    public void isSameTestNoMainColor() {
        Shoe test = new Shoe("dummy", "US M", 0.5, "Dummy", "a", "a");

        assertFalse(shoe.isSame(test));
    }

    @Test
    public void isSameTestNoCondition() {
        Shoe test = new Shoe("dummy", "US M", 0.5, "Dummy", "rainbow", "a");

        assertFalse(shoe.isSame(test));
    }

    @Test
    public void isSameTestEverything() {
        Shoe test = new Shoe("a", "a", 1, "a", "Rainbow", "beat");

        assertFalse(shoe.isSame(test));
    }
}