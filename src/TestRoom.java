import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class TestRoom {
        /** Non-null objects */

        private final String[] MONSTERS = {"Baron_of_Hell", "Caco", "Imp"};
        Room room;
        Inventory inventory;
        Monster testMonster;

        @BeforeEach
        void reset() {
                //TODO: May need to ask the sql server to instantiate both the inventory, monster, and stuff
                testMonster = new Monster(25, "Caco", new Weapon(1,1,1,1,"DGW"));

                room = new Room();
        }

        @Test
        /**
         * Testing getter and setter methods of Room
         */
        void testGSInventory() {
                assertEquals(0, room.getInventory().inventorySize(), "Inventory is not instantiated well");
                Inventory testInv = new Inventory();
                room.setInventory(testInv);
                assertEquals(testInv, room.getInventory(), "Inventory is not set well");
        }

        @Test
        void testGSMonster() {
                assertEquals(null, room.getMonster(), "Room should not have any monster");
                room.setMonster(testMonster);
                assertEquals(testMonster, room.getMonster());
        }

        @Test
        void testSetDiscovered() {
             assertEquals(false, room.getDiscovered());
        }
}
