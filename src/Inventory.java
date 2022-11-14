

import java.sql.Connection;
import java.util.List;
import java.util.Set;

/**
 * A hero can view the entire list of items in disposal.
 * Each room can also access the list of items that one can access.
 *
 * Executes the specified command on the database query connection
 */
public class Inventory {


    /** Referred item may not be null */
    private Set<String> myItems;

    /**
     * Should this sql table contain table for items only?
     */
    public Inventory(Set<String> theItems) {
        this.myItems = theItems;
    }

    /**
     * Each Inventory may have a list of items to be accessed
     */


    /**
    * Add item to the end of a list
    * @requires theItem is non-null
    * @param theItem item to be added
    * @throws: error if adding item is null
    * @effects: this = this + theItem. New Item will be at the end of a list
    *           ow no effect. 
    *           If new item is added, add it to end of a list. If same item already exists, 
    *           Increment counter 
    * @spec.modifies: this
    */
    public void addItem(String theItem) {
        if (theItem == null) {
            throw new IllegalArgumentException("Item is null");
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

    //TODO: make Inventory compatible with Set data structure
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
