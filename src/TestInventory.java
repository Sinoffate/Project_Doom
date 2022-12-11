

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * Testing if the Inventory would return a correct output.
 * Testing to see if inventory can store items for the hero but also items for each room and monsters in each room
 * @author Hyunggil Woo
 * @version 1.2
 * Date: December 10, 2022
 */

public class TestInventory {


    Inventory itemInventory; 

    /** Hard coded items to be used for items */

    private Item[] ITEMS = {new Weapon("BFG"), new Weapon("Shotgun"), 
                new Weapon("Pistol"), new Weapon("Rawket Lawnchair"), 
                new VisionPotion(), new HealthPotion()};

    /**
     * Inventory will contain an entire list of items
     */
    @BeforeEach
    public void reset() {
        itemInventory = new Inventory();
    }

    /**
     * Checks if the initial inventory contains nothing.
     */
    @Test
     public void testEmptyInventory() {
        assertEquals("", itemInventory.toString());
    }

    @Test
    public void addBFGWeapon() {
        itemInventory.addItem(ITEMS[0]); // add BFG
        assertEquals("BFG 420 : 1\n", itemInventory.toString());
    }

    @Test
    public void addTwoWeapons() {
        assertEquals("", itemInventory.toString());
        itemInventory.addItem(ITEMS[0]); // add BFG
        assertEquals("BFG 420 : 1\n", itemInventory.toString());
        itemInventory.addItem(ITEMS[1]); // add shotgun
        assertEquals("BFG 420 : 1\nShotgun 20 : 1\n", itemInventory.toString());
    }

    @Test
    public void addHealthPotion() {
        assertEquals("", itemInventory.toString());
        itemInventory.addItem(ITEMS[5]);
        assertEquals("Health Potion : 1\n", itemInventory.toString());
    }

    @Test
    public void addTwoShotguns() {
        itemInventory.addItem(ITEMS[1]); // add shotgun
        assertEquals("Shotgun 20 : 1\n", itemInventory.toString());
        itemInventory.addItem(ITEMS[1]); // add shotgun
        assertEquals("Shotgun 20 : 2\n", itemInventory.toString());
    }

    @Test
    public void addMultiplePotions() {
        itemInventory.addItem(ITEMS[5]);
        assertEquals("Health Potion : 1\n", itemInventory.toString());
        itemInventory.addItem(ITEMS[5]);
        assertEquals("Health Potion : 2\n", itemInventory.toString());
    }

    @Test
    public void removePotion() {
        itemInventory.addItem(ITEMS[5]);
        assertEquals("Health Potion : 1\n", itemInventory.toString());
        itemInventory.addItem(ITEMS[5]);
        assertEquals("Health Potion : 2\n", itemInventory.toString());
        assertEquals(true, itemInventory.containsItem(ITEMS[5]));
        assertEquals(false, itemInventory.containsItem(ITEMS[1])); // testing if shotgun does not exist
        itemInventory.addItem(ITEMS[1]);
        assertEquals(true, itemInventory.containsItem(ITEMS[1])); // testing if shotgun exists
        itemInventory.removeItem(ITEMS[5]);
        assertEquals("Shotgun 20 : 1\nHealth Potion : 1\n", itemInventory.toString());
        itemInventory.removeItem(ITEMS[5]);
        assertEquals("Shotgun 20 : 1\n", itemInventory.toString());
        itemInventory.removeItem(ITEMS[5]);
        assertEquals("Shotgun 20 : 1\n", itemInventory.toString());
    }

}

