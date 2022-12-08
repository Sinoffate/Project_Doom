import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestDungeon {

    private Dungeon d;

    @BeforeEach
    void reset() {
        d = new Dungeon(5);
    }

    @Test
    void testCon() {
        assertThrows(IllegalArgumentException.class,
                () -> new Dungeon(0), "Dungeon constructor test");
        assertThrows(IllegalArgumentException.class,
                () -> new Dungeon(-1), "Dungeon constructor test");
    }

    @Test
    void testGetRoomFail() {
        assertThrows(IllegalArgumentException.class,
                () -> d.getRoom(-1, -1), "Dungeon getRoom test");
        assertThrows(IllegalArgumentException.class,
                () -> d.getRoom(5, -1), "Dungeon getRoom test");
        assertThrows(IllegalArgumentException.class,
                () -> d.getRoom(-1, 5), "Dungeon getRoom test");
    }

    @Test
    void testGetRoomGood() {
        d.getRoom(0, 0);
        d.getRoom(4, 4);
    }

    @Test
    void testGetMapSize() {
        assertEquals(5, d.getMapSize(), "Dungeon getMapSize test");
    }

    @Test
    void testGetPlayerPos() {
        assertEquals(new Point(0, 0), d.getPlayerPos(), "Dungeon getPlayerPos test");
    }

    @Test
    void testGetEnterFlag() {
        assertEquals(new Point(0, 0), d.getEnterFlag(), "Dungeon getEnterFlag test");
    }

    @Test
    void testGetExitFlag() {
        assertEquals(new Point(4, 4), d.getExitFlag(), "Dungeon getExitFlag test");
    }

    @Test
    void testGetMonster() {
        assertNull(d.getMonster(), "Dungeon getMonster test");
    }

    @Test
    void testGetRoomInventory() {
        assertEquals(new Inventory(), d.getRoomInventory(), "Dungeon getRoomInventory test");
    }

    @Test
    void testMovePlayer() {
        d.movePlayer(new Point(0, 0));
        assertEquals(new Point(0, 0), d.getPlayerPos(), "Dungeon movePlayer test");
        d.movePlayer(new Point(0, 1));
        assertEquals(new Point(0, 1), d.getPlayerPos(), "Dungeon movePlayer test");
        d.movePlayer(new Point(1, 1));
        assertEquals(new Point(1, 1), d.getPlayerPos(), "Dungeon movePlayer test");
        d.movePlayer(new Point(0, 1));
        assertEquals(new Point(0, 1), d.getPlayerPos(), "Dungeon movePlayer test");
    }

}
