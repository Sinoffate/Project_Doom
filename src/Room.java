


import java.util.Iterator;
import java.util.Set;

/**
  * Name: Hyunggil Woo
  * Version: 1.2
  * Date: November 14, 2022
  */
public class Room {

    /** Inventory is non-null object*/
    private Inventory myInventory;
    private Set<String> myMonster;

    /**
     * Room contains a {item(1), ... , item(n), monster(1), ... , monster(n)}
     *
     * @param theInventory list of items that can be accessed.
     * @param theMonster list of monsters that are present
     */
    public Room(Inventory theInventory, Set<String> theMonster) {
        this.myInventory = theInventory;
        this.myMonster = theMonster;
    }

    /**
     * Sets up an inventory in each room.
     * 
     * @requires: 
     * @param theInventory
     * @mspec.odifies Adds more items in the inventory
     */
    public void setInventory(String theInventory) {
        myInventory.addItem(theInventory);
    }

    /**
     * Creates monsters in the room
     *
     * @pre Monster cannot be null
     * @param theMonster Baron of Hell, Caco, etc
     * @modifies set.length = set.length + 1
     */
    public void setMonster(String theMonster) {
        myMonster.add(theMonster);
    }

    /**
     * Shows the item inside the inventory
     *
     * @param theItem item to use.
     * @pre if item exists in list, returns it (otherwise, return "")
     * @throw IllegalArgumentException if item is null. If item does not exist, return ""
     * @spec.effect Items are not removed from the inventory
     * @modifies Inventory is not modified.
     * @return name of item
     */
    public String getInventory(String theItem) {
        if (theItem == null) {
            throw new IllegalArgumentException("Cannot find null item");
        } else if (!myInventory.contains(theItem)) {
            return "";
        }

        Iterator<String> itemIterator = myInventory.iterator();
        String result = "";

        // iterates through a list to obtain the specified
        while(itemIterator.hasNext()) {
            String item = itemIterator.next();
            if (item == theItem) {
                result = item;
            }
        }
        return result;
    }
    
    /**
     * Shows the monster inside the list.
     *
     * @param theMonster Baron of Hell, Caco, etc
     * @pre if monster exists, return it (otherwise, return "")
     * @throw IllegalArgumentException if monster is null. If monster does not exist, return ""
     * @spec.effect Monsters are not removed from Room
     * @modifies List of monsters is not modified
     * @return name of monster
     */
    public String getMonster(String theMonster) {
        if (theMonster == null) {
            throw new IllegalArgumentException("Enter a monster's name");
        } else if (!myMonster.contains(theMonster)) {
            return "";
        }

        Iterator<String> monsterIterator = myMonster.iterator();
        String result = "";
        
        // Checks through the list of monsters to find it.
        while (monsterIterator.hasNext()) {
            String monster = monsterIterator.next();
            if (monster == theMonster) {
                result = monster;
            }
        }
        return result;
    }

    /**
     * Draws a room to be drawn on the console.
     *
     * Does not return null
     * @return Block representation of a Room.
     * @spec.effect No effect
     * @modifies Returns a String representation of a room
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        //TODO: Need to iterate over a generic class
        
        for (Inventory item: myInventory) {
            sb.append(item.toString() + "\n");
        }
        return sb.toString();
    }
}