import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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



}
