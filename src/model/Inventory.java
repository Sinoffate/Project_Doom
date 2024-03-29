package model;

import java.io.Serializable;
import java.util.*;

/**
 * A hero can view the entire list of items.
 * Inventory will contain list of items and the number of it
 * that can be accessed.
 * 
 * @author Hyunggil Woo
 * @version 1.4
 * Date: November 15, 2022
 */
public class Inventory implements Serializable {

    /** contain non-null objects. */
    private final Map<Item , Integer> myInventory;

    /**
     * List of items will be stored into a list of items.
     */
    public Inventory() {
        this.myInventory = new HashMap<>();
    }

    /**
    * Add thing to a list of items.
    * Inventory  = List of item + item
    * this = this + theObject. New Item will be added with number 1
    * If same thing already exists, increment counter 
    * Throws illegal argument exception if added item is null.
    *
    * @param theObject is non-null
    */
    public void addItem(final Item theObject) {
        if (theObject == null) {
            throw new IllegalArgumentException("Item is null");
        }
        myInventory.merge(theObject, 1, Integer::sum);
    }

    /**
     * Remove a thing from an inventory.
     * Modifies instance of inventory.
     * Effect: If count of item >= 1, count = count - 1, else count = 0
     * if thing does not exist, throw illegalArgumentException
     * @param theObject is non-null
     */
    public void removeItem(final Item theObject) {
        if (theObject == null) {
            throw new IllegalArgumentException("You cannot remove null");
        }
        myInventory.merge(theObject, -1, Integer::sum);

        if (myInventory.get(theObject) <= 0) {
            myInventory.remove(theObject);
        }
    }

    /**
     * Checks if Item is present in model.Inventory.
     * throws IllegalArgumentException if item is null. Nothing is modified.
     * @param theObject is non-null
     * @return true if thing is present, ow false.
     */
    public boolean containsItem(final Item theObject) {
        if (theObject == null) {
            throw new IllegalArgumentException("You cannot find null");
        }
        return myInventory.containsKey(theObject);
    }

    /**
     * Returns number of unique items in Inventory
     * Size of inventory must be >= 0. It does not modify itself
     * or other fields.
     * @return number of items in inventory.
     */
    public int inventorySize() {
        return this.myInventory.size();
    }

    /**
     * Returns a list of all items in inventory.
     * @return list of items in inventory.
     */
    public List<Item> getItems() {
        List<Item> res = new ArrayList<>();
        for (Item i:myInventory.keySet()) {
            int count = myInventory.get(i);
            for (int j = 0; j < count; j++) {
                res.add(i);
            }
        }
        return res;
    }

    /**
     * Gets an item from inventory.
     * @param theItem items to check for
     * @return item if it exists, else null
     */
    public Item getItem(final Item theItem) {
        if (containsItem(theItem)) {
            for (Item i: myInventory.keySet()) {
                if (i.equals(theItem)) {
                    return i;
                }
            }
        }
        return null;
    }

    /**
     * Prints a String version of the entire list of items.
     * @return String version of a general inventory.
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (Map.Entry<Item, Integer> thing : myInventory.entrySet()) {
            sb.append(thing.getKey()).append(" : ").append(thing.getValue()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Checks if an inventory is empty.
     * @return true if inventory is empty, else false.
     */
    public boolean isEmpty() {
        return myInventory.isEmpty();
    }
}
