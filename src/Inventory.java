

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.text.html.HTMLDocument.Iterator;

/**
 * A hero can view the entire list of items.
 * Inventory will contain list of items and the number of it
 * that can be accessed.
 * 
 */
public class Inventory {

    final static int MAX_ITEM_NUMBER = 15;

    /** contain non-null objects*/
    private Map<String, Integer> myItems;

    //TODO: Use this until database of items is functioning.
    /**  */
    private final String[] myItemData = {"Health_Potion", "BFG", "ChainGun", "Pistol"};
    
    /**
     * List of items will be stored stored into a list of items
     * 
     * @requires: number of items in myItems > 0
     * @param theItems
     * @constructor of Inventory
     */
    public Inventory() {
        this.myItems = generateInventory(myItemData);
    }

    /**
     * Generates a map of the list of items that will be stored in each inventory.
     * It will generate a random number number of items in the list
     * 
     * @param theItemList
     * @requires: Item will not contain null; RandomNumberGenerator is required
     * @throws: If itemList does not contain null, will not add the item in the list
     * @spec.modifies: Item[]
     * @spec.effect: Generates a TreeMap of Item and a corresponding number of it.
     * @return count of items generated from the list
     */
    private Map<String, Integer> generateInventory(String[] theItemList) {
        
        // checks if itemList contains Null
        for (int i = theItemList.length - 1; i > 0; i--) {
            assert theItemList[i] == null; 
        }
        
        Map<String, Integer> itemInventory = new TreeMap<>();
        RandomNumberGenerator rand = new RandomNumberGenerator();

        // choose a random number between 1 - MAX_ITEM_NUMBER
        int numberOfItems = rand.nextInt(MAX_ITEM_NUMBER) + 1;
        int itemOption = myItemData.length;
        
        // add items in the list with a random in a map
        for (int i = 0; i < numberOfItems; i++) {
            
            // randomly choose an item from the list
            String randomItem = myItemData[rand.nextInt(itemOption)];
            
            // add new word in a Item list
            if (!itemInventory.containsKey(randomItem)) {
                itemInventory.put(randomItem, 1);
            } else {
                // increment count of item that already exist
                int oldValue = itemInventory.get(randomItem);
                itemInventory.put(randomItem, oldValue + 1);
            }
        }
        return itemInventory;
    }

    /**
    * Add item to the end of a list

    * @requires theItem is non-null
    * @param theItem item to be added
    * @throws: error if adding item is null
    * @effects: this = this + theItem. New Item will be added with number 1
    *           If same item already exists, increment counter 
    * @spec.modifies: this
    */
    public void addItem(String theItem) {
        if (theItem == null) {
            throw new IllegalArgumentException("Item is null");
        }
        Iterator<String, Integer> itemIterator = myItems.iterator();
        
        // searches through the set for item
        while (itemIterator.hasNext()) {
            String thing = itemIterator.next();
            myItems.put(thing, 1);

        }
        myItems.add(theItem);
    }

    /**
     * Remove an item from an inventory
     * @requires: theItem is non-null
     * @param: theItem Item to be added in inventory
     * @throws: if item does not exist, throw illegalArgumentException
     * @spec.effects: if (number) this > 1, (number) this - 1. 
     *           if (number) this = 1, (number) this = 0, else nothing
     * @spec.modifies: this
     */
    public void removeItem(String theItem) {
        if (theItem == null) {
            throw new IllegalArgumentException("You cannot remove null");
        }
        myItems.remove(theItem);
    }

    /**
     * Checks if Item is present in Inventory.
     * @param theItem
     * @requires: theItem is non-null
     * @throws: IllegalArgumentException if checking null item.
     * @spec.modifies: NA
     * @return: true if item is present, ow false.
     */
    public boolean containsItem(String theItem) {
        if (theItem == null) {
            throw new IllegalArgumentException("You cannot find null");
        }
        return myItems.contains(theItem);
    }

    //TODO: Make sure to return the copy of list instance, not the actual instance
    /**
     * Returns number of unique items in Inventory
     * @requires: size >= 0
     * @spec.modifies: NA
     * @return: number of items in inventory.
     */
    public int inventorySize() {
        return myItems.size();
    }

    //TODO: make Inventory compatible with Map data structure
    /**
     * Prints a String version of the entire list of items.
     * 
     * @return: String version of the item inventory.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String thing : myItems) {
            sb.append(thing);
        }
        return sb.toString();
    }

}
