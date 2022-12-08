import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void testGetRoom() {
        d.getRoom(0, 4);
        assertThrows(IllegalArgumentException.class,
                () -> d.getRoom(-1, -1), "Dungeon getRoom test");
        assertThrows(IllegalArgumentException.class,
                () -> d.getRoom(5, -1), "Dungeon getRoom test");
        assertThrows(IllegalArgumentException.class,
                () -> d.getRoom(-1, 5), "Dungeon getRoom test");
    }
}
