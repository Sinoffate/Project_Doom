import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHealthPotion {

    HealthPotion p;

    @BeforeEach
    void reset() {
        p = new HealthPotion();
    }


    @Test
    void testConGood() {
        p = new HealthPotion();
    }

    @Test
    void testGetName() {
        assertEquals("Health Potion", p.getName(), "Bad name");
    }

    @Test
    void testToString() {
        assertEquals("Health Potion", p.toString(), "Bad toString");
    }

    @Test
    void testEquals() {
        Item i = new HealthPotion();
        HealthPotion p2 = new HealthPotion();
        assertEquals(p,i,"Item vs HealthPotion instantiation bad");
        assertEquals(p,p2,"HealthPotion vs HealthPotion instantiation bad");
        assertEquals(p,p,"Self test bad");
    }


    @Test
    void testUseHealthPotion() {
        DungeonCharacter player = new DoomGuy(100, "doomGuy", new Weapon("Pistol"));
        p.useHealthPotion(player);
    }
}
