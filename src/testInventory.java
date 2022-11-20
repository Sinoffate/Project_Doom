import static org.junit.Assert.assertEquals;


import org.junit.Before;

import org.junit.Test;

/**
 *
 * Testing if the Inventory would return a correct output.
 * Testing to see if inventory can store itesm for the hero but also items for each room and monsters in each room
 * @author Hyunggil Woo
 * Version: 1.2
 * Date: November 16, 2022
 * @param <E>
 */

public class testInventory {


    Inventory itemInventory; 


    /** Hard coded items to be used for items */
    private final int MAX_ITEM_NUMBER = 5;

    private String[] ITEMS = {"Potion", "Pillar", "BFG"};
  
    
    /**
     * Generates a map of the list of items that will be stored in each inventory.
     * It will generate a random number number of items in the list
     * 
     * @param E[] theList of objects stored in Database.
     *         int theMax maximum number of objects. 
     * @requires: Item will not contain null; 
     * @throws: If itemList does not contain null, will not add the thing in the list
     * @spec.modifies: Item[]
     * @spec.effect: Generates a TreeMap of Item and a corresponding number of it.
     * @return count of items generated from the list
     */
    private Map<String, Integer> generateInventory(final String[] theList, final int theMax) {
        
        // checks if itemList contains Null
        for (int i = theList.length - 1; i > 0; i--) {
            assert theList[i] == null; 
        }

        Map< String , Integer > inventory = new TreeMap<>();

        // choose a random number between 1 - theMax
        int numberOfItems = DiceRoll.nextInt(theMax) + 1;
        int itemOption = theList.length;
        
        // add items in the list with a random in a map
        for (int i = 0; i < numberOfItems; i++) {
            
            // randomly choose an thing from the list
            String randomItem = theList[DiceRoll.nextInt(itemOption)];
            
            // add new word in a Item list
            if (!inventory.containsKey(randomItem)) {
                inventory.put(randomItem, 1);
            } else {
                // increment count of thing that already exist
                int oldValue = inventory.get(randomItem);
                inventory.put(randomItem, oldValue + 1);
            }
        }
        return inventory;
    }

    @Test
    public void testGenerateInventory() {
                /** Hard coded items to be used for items */
        final int MAX_ITEM_NUMBER = 5;

        String[] ITEMS = {"Potion", "Pillar", "BFG"};

    }
    /**
     * Checks if the initial inventory contains nothing.
     */
    @Test
    public void testDefaultConstructor() {
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

    /**
     * Test if Inventory correctly displays the correct item listed in the inventory.
     */
    @Test
    public void testContains_FirstItem() {
        assertEquals(true, itemInventory.contains("Potion"));
    }

    @Test
    public void testContains_MiddleItem() {
        assertEquals(true, itemInventory.contains("Pillar"));
    }

    /**
     * Test if Inventory correctly displays the correct item listed in the inventory.
     */
    @Test
    public void testContains_LastItem() {
        assertEquals(true, itemInventory.contains("BFG"));
    }

}
