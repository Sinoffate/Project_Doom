package main;
/**
 * Overview: Room is mutable.
 * A typical
 * Room is {item(1), ... , item(n), monster(1), ... , monster(n), hero}
 *
 * First iteration will only check if the hero is able to travel around the rooms.
 */

 /**
  * Hyunggil Woo
  * Version: 1
  */
public class Room {

    /**
     * Constructs the Room.
     */
    public Room() {

    }

    /**
     * Does not return null
     * @return Block representation of a Room.
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