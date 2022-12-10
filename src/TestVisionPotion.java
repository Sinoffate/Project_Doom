import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestVisionPotion {

    VisionPotion p;

    @BeforeEach
    void reset() {
        p = new VisionPotion();
    }

    @Test
    void testConGood() {
        p = new VisionPotion();
    }

    @Test
    void testGetName() {
        assertEquals("Vision Potion", p.getName(), "Bad name");
    }

    @Test
    void testToString() {
        assertEquals("Vision Potion", p.toString(), "Bad toString");
    }

    @Test
    void testGetRadius() {
        assertEquals(5,  p.getRadius(), "Bad radius");
    }

    @Test
    void testEquals() {
        Item i = new VisionPotion();
        VisionPotion p2 = new VisionPotion();
        assertEquals(p,i,"Item vs VisionPotion instantiation bad");
        assertEquals(p,p2,"VisionPotion vs VisionPotion instantiation bad");
        assertEquals(p,p,"Self test bad");
    }

    @Test
    void testUseVisionPotion() {
        Dungeon d = new Dungeon(5);
        d.useVisionPotion();
        assertTrue(d.getRoom(0, 0).getDiscovered(), "Room 0,0 should be visible");
        assertTrue(d.getRoom(0, 1).getDiscovered(), "Room 0,1 should be visible");
        assertTrue(d.getRoom(1, 0).getDiscovered(), "Room 1,0 should be visible");
        assertFalse(d.getRoom(1, 2).getDiscovered(), "Room 1,2 should not be visible");
        assertFalse(d.getRoom(2, 2).getDiscovered(), "Room 2,2 should not be visible");
        assertFalse(d.getRoom(2, 1).getDiscovered(), "Room 2,1 should not be visible");
    }

}
