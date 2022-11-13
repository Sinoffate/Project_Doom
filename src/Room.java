


import java.util.Iterator;
import java.util.Set;

/**
  * Name: Hyunggil Woo
  * Version: 1.1
  * Date: November 2, 2022
  */
public class Room {

    private Set<String> myInventory;
    private Set<String> myMonster;

    /**
     * Room contains a {item(1), ... , item(n), monster(1), ... , monster(n)}
     *
     * @param theInventory list of items that can be accessed.
     * @param theMonster list of monsters that are present
     */
    public Room(Set<String> theInventory, Set<String> theMonster) {
        this.myInventory = theInventory;
        this.myMonster = theMonster;
    }

    /**
     * More item is added into the room
     *
     * @pre Inventory cannot be null.
     * @param theInventory
     * @modifies Adds more items in the inventory
     */
    public void setInventory(String theInventory) {
        myInventory.add(theInventory);
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
     * @spec.effect Items are not removed from the inventory
     * @modifies Inventory is not modified.
     * @return name of item
     */
    public String getInventory(String theItem) {
        Iterator<String> itemIterator = myInventory.iterator();
        String result = "";
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
     * @spec.effect Monsters are not removed from Room
     * @modifies List of monsters is not modified
     * @return name of monster
     */
    public String getMonster(String theMonster) {
        Iterator<String> monsterIterator = myMonster.iterator();
        String result = "";
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
        sb.append("______\n");
        sb.append("|    |\n");
        sb.append("|    |\n");
        sb.append("------\n");
        return sb.toString();
    }
}