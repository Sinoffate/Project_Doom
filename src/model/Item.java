package model;

import java.io.Serializable;

/**
 * Simple item abstract for items to inherit from.
 * @author james deal, jered wiegel
 * @version 1.0
 */
public abstract class Item implements Serializable {

    /** Name of item. */
    private final String myName;

    /**
     * Default constructor.
     * @param theName name of item.
     */
    public Item(final String theName) {
        if (theName == null || "".equals(theName)) {
            throw new IllegalArgumentException("Item.con did not like name empty or null.");
        }
        myName = theName;
    }

    /**
     * Compare items based on name.
     * Design currently allows for the limitations this brings.
     * @param theOther item to compare against.
     * @return true if names equal.
     */
    @Override
    public boolean equals(final Object theOther) {
        if (theOther instanceof Item) {
            return this.getName().equals(((Item) theOther).getName());
        }
        return false;
    }

    /**
     * defines hashcode based on name used for equality.
     * @return hashcode based on name.
     */
    @Override
    public int hashCode() {
        return this.myName.hashCode();
    }

    /**
     * Name of item.
     * @return name of item.
     */
    public String getName() {
        return myName;
    }

    /**
     * Left to child classes to implement.
     * @return string of item state.
     */
    @Override
    public abstract String toString();
}
