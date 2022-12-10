package model;

/**
  * @author Hyunggil Woo
  * @author 1.2
  */
public class Room {

    /** Inventory<String> is non-null object*/
    private Inventory myInventory;
    /** Holds monster object*/
    private Monster myMonster;

    /** Flag to check if the room is visited */
    private boolean myIsVisited;

    /**
     * Instantiates a room containing list of items.
     * Room contains a {item(1), ... , item(n) and 1 random monster
     * Items and Monsters must not contain null objects;
     *          
     */
    public Room() {
        this.myInventory = new Inventory();
        this.myMonster = null;
        this.myIsVisited = false;
    }

    /**
     * Sets up an inventory in each room.
     * 
     * @param theInventory is non-null
     */
    public void setInventory(final Inventory theInventory) {
        assert theInventory != null;

        myInventory = theInventory;
    }

    /**
     * Creates monsters in the room.
     *
     * @param theMonster cannot be null
     */
    public void setMonster(final Monster theMonster) {
        this.myMonster = theMonster;
    }

    /**
     * Shows the entire items inside the inventory
     * Items are not removed from the inventory
     * Inventory<String> is not modified.
     * 
     * @return name of item
     */
    public Inventory getInventory() {
        return this.myInventory;
    }

    /**
     * Shows the entire monsters inside the Inventory<String>.
     * Monsters are not removed from Room
     * List of monsters is not modified
     * 
     * @return name of monster
     */
    public Monster getMonster() {
        return this.myMonster;
    }

    /**
     * Checks discovered status of the room.
     * @return true if room is discovered, else false
     */
    public boolean getDiscovered() {
        
        return this.myIsVisited;
    }
    
    /**
     * This class sets up whether the room has been obscured or not. Related to Fog of War.
     * 
     * @param theDiscovered boolean value to set up whether the room has been discovered or not.
     */
    public void setDiscovered(final boolean theDiscovered) {
        myIsVisited = theDiscovered;
    }

    /**
     * Prints a list of items and monsters in a room.
     * If room is empty, it should return empty room. Otherwise, returns a list of items and monster 
     * 
     * @return List of items and monsters in a room.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // prints each item in the room
        sb.append("Inventory<String>:\n");
        sb.append(myInventory.toString());

        // prints each monster in the room
        sb.append("Monsters:\n");
        sb.append(myMonster.toString());

        return sb.toString();
    }


}