import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * Testing if the Inventory would return a correct output.
 */

public class testInventory {
    Set<String> itemList;
    Inventory itemInventory; 
    
    @Before
    public void initalizeDefaultInventory() {
        itemList = new TreeSet<>(){"Potion", "Pillar", "BFG"};
        itemInventory = new Inventory(itemList);
    }


    /**
     * Checks if the initial inventory contains nothing.
     */
    @Test
    public void testDefaultConstructor() {
        initalizeDefaultInventory();
        assertEquals("Potion:1, Pillar:1, BFG:1", itemInventory.toString());
    }

    /**
     * Tests if the Inventory is correctly added when it is called.
     */
    @Test
    public void testAdd_OneItem() {
        initalizeDefaultInventory();
        itemInventory.addItem("Chain_Gun");
        assertEquals("Potion, Pillar, BFG, Chain_Gun",itemInventory.toString());
    }


    @Test
    public void testRemove_FirstItem(){
        initalizeDefaultInventory();
        itemInventory.removeItem("Potion");
        assertEquals("Pillar, BFG", itemInventory.toString());
    }

    /**
     * Tests if item is correctly removed from the list.
     */
    @Test
    public void testRemove_LastItem() {
        initalizeDefaultInventory();
        itemInventory.removeItem("BFG");
        assertEquals("Potion, Pillar", itemInventory.toString());
    }

    /**
     * Test if Inventory correctly displays the correct item listed in the inventory.
     */
    @Test
    public void testContains_FirstItem() {
        initalizeDefaultInventory();
        assertEquals(true, itemInventory.containsItem("Potion"));
    }

    @Test
    public void testContains_MiddleItem() {
        initalizeDefaultInventory();
        assertEquals(true, itemInventory.containsItem("Pillar"));
    }

    /**
     * Test if Inventory correctly displays the correct item listed in the inventory.
     */
    @Test
    public void testContains_LastItem() {
        initalizeDefaultInventory();
        assertEquals(true, itemInventory.containsItem("BFG"));
    }

}