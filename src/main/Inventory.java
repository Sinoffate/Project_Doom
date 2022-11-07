package main;

import java.sql.Connection;

/**
 * A hero can view the entire list of items in disposal.
 * Each room can also access the list of items that one can access.
 *
 * Executes the specified command on the database query connection
 */
public class Inventory {

    // Database Connection
    private Connection myConn;

    /** Referred item may not be null */
    private String Item myItem;

    /**
     * Should this sql table contain table for items only?
     */
    public Inventory() {

    }

    /**
     * Each Inventory may have a list of items to be accessed
     */


     /**
      * You have access to add items to the inventory
      */
    public void addItems() {

    }

      /**
       * Hero can add items into one's own inventory
       */
    public void removeItems() {

    }

    /**
     * prints String version of Inventory
     */
    public String toString() {

    }
}
