

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * Testing if the Inventory would return a correct output.
 * Testing to see if inventory can store itesm for the hero but also items for each room and monsters in each room
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
        assertEquals("Potion:1, Pillar:1, BFG:1", itemInventory.toString());
    }

    /**
     * Tests if the Inventory is correctly added when it is called.
     */
    @Test
    public void testAdd_OneItem() {
        itemInventory.addItem("Chain_Gun");
        assertEquals("Potion, Pillar, BFG, Chain_Gun",itemInventory.toString());
    }

    /**
     * Test if Inventory correctly displays the correct item listed in the inventory.
     */
    @Test
    public void testContains_FirstItem() {
        assertEquals(true, itemInventory.containsItem("Potion"));
    }

    @Test
    public void testContains_MiddleItem() {
        assertEquals(true, itemInventory.containsItem("Pillar"));
    }

    /**
     * Test if Inventory correctly displays the correct item listed in the inventory.
     */
    @Test
    public void testContains_LastItem() {
        assertEquals(true, itemInventory.containsItem("BFG"));
    }

    @Test
    public void testRemove_FirstItem(){
        itemInventory.removeItem("Potion");
        assertEquals("Pillar, BFG", itemInventory.toString());
    }

    /**
     * Tests if item is correctly removed from the list.
     */
    @Test
    public void testRemove_LastItem() {
        itemInventory.removeItem("BFG");
        assertEquals("Potion, Pillar", itemInventory.toString());
    }
}

