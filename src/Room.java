/**
 * Rooms will become a set of all items and monsters.
 * Dungeon should be able to see the entire list of items & monsters & hero
 * in each room
 */
public class Room {
    //TODO: Should the monsters = array of Monsters?
    //TODO: Should items be an array of Items?
//    private final Inventory myItems;
    private final Monster myMonsters;

    /**
     * Constructs the Room.
     */
    public Room() {

    }

    //TODO: First iteration does not require
    // public void setInventory(Inventory theInventory) {

    // }

    public Inventory getInventory(Inventory theInventory) {
        return theInventory;
    }

    //TODO: Determine if getDoors() is necessary.
//    /**
//     * This is
//     */
//    public void getDoors() {
//        System.out.println("getting doors");
//    }

    public Monster getMonster(Monster theMonster) {
        return theMonster;
    }

    //TODO: Determine if setDoors will be necessary.
//    public void setDoors(?) {
//        System.out.println("Setting doors");
//    }

    /**
     *
     * @return
     */
    public String toString() {

    }
}