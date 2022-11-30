import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class TestPillar {

    Item p;

    @BeforeEach
    void reset() {
        p = new Pillar("JUnit");
    }

    @Test
    void testConBad() {
        assertThrows(IllegalArgumentException.class,
                () -> new Pillar(null), "Damage constructor test");
        assertThrows(IllegalArgumentException.class,
                () -> new Pillar(""), "Damage constructor test");
    }

    @Test
    void testConGood() {
        p = new Pillar("Junit2");
    }

    @Test
    void testGetName() {
        assertEquals("JUnit", p.getName(), "Bad name");
    }

    @Test
    void testToString() {
        assertEquals("JUnit", p.toString(), "Bad toString");
    }

    @Test
    void testEquals() {
        Item i = new Pillar("JUnit");
        Pillar p2 = new Pillar("JUnit");
        assertEquals(p,i,"Item vs Pillar instantiation bad");
        assertEquals(p,p2,"Pillar vs Pillar instantiation bad");
        assertEquals(p,p,"Self test bad");
        i = new Pillar("Bad");
        p2 = new Pillar("Bad");
        assertNotEquals(p,i,"Item vs Pillar instantiation bad");
        assertNotEquals(p,p2,"Pillar vs Pillar instantiation bad");
    }

}
