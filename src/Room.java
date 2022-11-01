/**
 * Overview: Room is mutable.
 * A typical
 * Room is {item(1), ... , item(n), monster(1), ... , monster(n), hero}
 *
 * First iteration will only check if the hero is able to travel around the rooms.
 */
public class Room {
    //TODO: Should the monsters = array of Monsters?
    //TODO: Should items be an array of Items?
//    private final Inventory myItems;
    // private final Monster myMonsters;

    /**
     * Constructs the Room.
     */
    public Room() {

    }

    //TODO: First iteration does not require
    public void setInventory(String theInventory) {

    }

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