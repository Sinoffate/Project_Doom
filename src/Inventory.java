import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * A hero can view the entire list of items.
 * Inventory will contain list of items and the number of it
 * that can be accessed.
 * 
 * Name: Hyunggil Woo
 * Version: 1.4
 * Date: November 15, 2022
 */
public class Inventory {

    /** contain non-null objects*/
    private Map< Item , Integer > myInventory;

    /**
     * List of items will be stored stored into a list of items
     * 
     * @requires: number of items in myInventory > 0
     * @constructor of Inventory
     */
    public Inventory() {
        this.myInventory = new HashMap<>();
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
    public void addItem(final Item theObject) {
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
    public void removeItem(final Item theObject) {
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
    public boolean containsItem(final Item theObject) {
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
        return this.myInventory.size();
    }

    /**
     * Prints a String version of the entire list of items.
     * 
     * @return: String version of a general inventory.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Item, Integer> thing : myInventory.entrySet()) {
            sb.append(thing.getKey()).append(":").append(thing.getValue()).append("\n");
        }
        return sb.toString();
    }
}
