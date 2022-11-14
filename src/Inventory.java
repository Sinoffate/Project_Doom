import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * A hero can view the entire list of items.
 * Inventory will contain list of items and the number of it
 * that can be accessed.
 * 
 * Name: Hyunggil Woo
 * Version: 1.3
 * Date: November 14, 2022
 */
public class Inventory {

    
    /** contain non-null objects*/
    private Map<String, Integer> myInventory;

    //TODO: Use this until database of items is functioning.
    //TODO: Need to throw error if theObjects contain null.
    /**  */
    
    /**
     * List of items will be stored stored into a list of items
     * 
     * @requires: number of items in myInventory > 0
     * @param String[] theObjects a list of items stored in database
     * @constructor of Inventory
     */
    public Inventory(String[] theObjects, int theMax) {
        if (theMax < 0) {
            throw new IllegalArgumentException("Number must be >= 0");
        }
        this.myInventory = generateInventory(theObjects, theMax);
    }

    /**
     * Generates a map of the list of items that will be stored in each inventory.
     * It will generate a random number number of items in the list
     * 
     * @param String[] theList of objects stored in Database.
    *         int theMax maximum number of objects. 
     * @requires: Item will not contain null; RandomNumberGenerator is required
     * @throws: If itemList does not contain null, will not add the thing in the list
     * @spec.modifies: Item[]
     * @spec.effect: Generates a TreeMap of Item and a corresponding number of it.
     * @return count of items generated from the list
     */
    private Map<String, Integer> generateInventory(String[] theList, int theMax) {
        
        // checks if itemList contains Null
        for (int i = theList.length - 1; i > 0; i--) {
            assert theList[i] == null; 
        }

        Map<String, Integer> inventory = new TreeMap<>();
        RandomNumberGenerator rand = new RandomNumberGenerator();

        // choose a random number between 1 - theMax
        int numberOfItems = rand.nextInt(theMax) + 1;
        int itemOption = theList.length;
        
        // add items in the list with a random in a map
        for (int i = 0; i < numberOfItems; i++) {
            
            // randomly choose an thing from the list
            String randomItem = theList[rand.nextInt(itemOption)];
            
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

    /**
    * Add thing to a list of items. If adding thing already exist, increment counter.
    *
    * @requires theObject is non-null
    * @param theObject thing to be added
    * @throws: error if adding thing is null
    * @effects: this = this + theObject. New Item will be added with number 1
    *           If same thing already exists, increment counter 
    * @spec.modifies: this
    */
    public void addItem(String theObject) {
        if (theObject == null) {
            throw new IllegalArgumentException("Item is null");
        }

        // add new word in a Item list
        if (!myInventory.containsKey(theObject)) {
            myInventory.put(theObject, 1);
        } else {
            // increment count of thing that already exist
            int oldValue = myInventory.get(theObject);
            myInventory.put(theObject, oldValue + 1);
        }
    }

    /**
     * Remove an thing from an inventory
     * 
     * @requires: theObject is non-null
     * @param: theObject Item to be added in inventory
     * @throws: if thing does not exist, throw illegalArgumentException
     * @spec.effects: if (number) this > 1, (number) this - 1. 
     *           if (number) this = 1, (number) this = 0, else nothing
     * @spec.modifies: this
     */
    public void removeItem(String theObject) {
        if (theObject == null) {
            throw new IllegalArgumentException("You cannot remove null");
        }
        myInventory.remove(theObject);
    }

    /**
     * Checks if Item is present in Inventory.
     * 
     * @param theObject
     * @requires: theObject is non-null
     * @throws: IllegalArgumentException if checking null thing.
     * @spec.modifies: NA
     * @return: true if thing is present, ow false.
     */
    public boolean contains(String theObject) {
        if (theObject == null) {
            throw new IllegalArgumentException("You cannot find null");
        }
        return myInventory.containsKey(theObject);
    }

    //TODO: Make sure to return the copy of list instance, not the actual instance
    /**
     * Returns number of unique items in Inventory
     * 
     * @requires: size >= 0
     * @spec.modifies: NA
     * @return: number of items in inventory.
     */
    public int inventorySize() {
        return myInventory.size();
    }

    /**
     * Prints a String version of the entire list of items.
     * 
     * @return: String version of a general inventory.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> thing : myInventory.entrySet()) {
            sb.append(thing.getKey() + ":" + thing.getValue() + "\n");
        }
        return sb.toString();
    }
}