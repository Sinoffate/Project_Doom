import java.util.Iterator;

/**
  * Name: Hyunggil Woo
  * Version: 1.2
  * Date: November 14, 2022
  */
public class Room {

    /** Non-null objects */
    /** Hard coded items to be used for items */
    private final int MAX_ITEM_NUMBER = 15;
    private final int MAX_MONSTER_NUMBER = 10;

    private final String[] ITEMS = {"Health_Potion", "BFG", "ChainGun", "Pistol"};
    private final String[] MONSTERS = {"Baron_of_Hell", "Caco", "Imp"};

    /** Inventory is non-null object*/
    private Inventory myInventory;
    private String myMonster;

    /** Flag to check if the room is visited */
    private boolean myIsVisited;

    /**
     * Instantiates a room containing list of items.
     * Room contains a {item(1), ... , item(n) and 1 random monster
     * 
     * @requires: Items and Monsters must not contain null objects;
     *          number of max >= 0
     * @throws: IllegalArgumentException if number < 0
     * 
     */
    public Room() {
        // TODO: Test if inventory constructor is redundant with getInventory
        this.myInventory = new Inventory(ITEMS, MAX_ITEM_NUMBER);
        this.myMonster = null;
        this.myIsVisited = false;
    }

    /**
     * Sets up an inventory in each room.
     * 
     * @requires: theInventory is non-null
     * @param theInventory
     * @mspec.odifies Adds more items in the inventory
     */
    public void setInventory(String theInventory) {
        assert theInventory != null;

        myInventory.addItem(theInventory);
    }

    /**
     * Creates monsters in the room.
     *
     * @pre Monster cannot be null
     * @param theMonster Baron of Hell, Caco, etc
     * @modifies myMonster = a random monster from a list.
     */
    public void setMonster(String theMonster) {
        assert theMonster != null;

        myMonster = MONSTERS[DiceRoll.nextInt(MONSTERS.length)];
    }

    /**
     * Shows the entire items inside the inventory
     *
     * @param theItem String item to use.
     * @pre if item exists in list, returns it (otherwise, return "")
     * @throw IllegalArgumentException if item is null. If item does not exist, return ""
     * @spec.effect Items are not removed from the inventory
     * @modifies Inventory is not modified.
     * @return name of item
     */
    public Inventory getInventory() {
        return this.myInventory;
    }

    /**
     * Shows the entire monsters inside the Inventory.
     *
     * @param theMonster Baron of Hell, Caco, etc
     * @pre if monster exists, return it (otherwise, return "")
     * @throw IllegalArgumentException if monster is null. If monster does not exist, return ""
     * @spec.effect Monsters are not removed from Room
     * @modifies List of monsters is not modified
     * @return name of monster
     */
    public String getMonster(String theMonster) {
        return this.myMonster;
    }

    /**
     * Prints a list of items and monsters in a room.
     *
     * @requires: Not return null
     * @spec.effect No effect
     * @modifies Returns a String representation of a room
     * @return List of items and monsters in a room.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // prints each item in the room
        sb.append("Inventory:\n");
        sb.append(myInventory.toString());

        // prints each monsters in the room
        sb.append("Monsters:\n");
        sb.append(myMonster.toString());

        return sb.toString();
    }

    /**
     * This class sets up whether the room has been obscured or not. Related to Fog of War.
     * @param theDiscovered boolean value to set up whether the room has been discovered or not.
     */
    public void setDiscovered(final boolean theDiscovered) {
        myIsVisited = theDiscovered;
    }
}