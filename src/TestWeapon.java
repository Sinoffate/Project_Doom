import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.Stack;

public class TestWeapon {

    Weapon w;

    @BeforeEach
    void reset() {
        w = new Weapon("Pistol");
    }

//    @Test
//    void testConBad() {
//        assertThrows(IllegalArgumentException.class,
//                ()-> new Weapon(0,1,1,1," "), "Damage constructor test");
//        assertThrows(IllegalArgumentException.class,
//                ()-> new Weapon(1,0,1,1," "), "FR constructor test");
//        assertThrows(IllegalArgumentException.class,
//                ()-> new Weapon(1,1,0,1," "), "Acc constructor test min");
//        assertThrows(IllegalArgumentException.class,
//                ()-> new Weapon(1,1,1.1,1," "), "Acc constructor max");
//        assertThrows(IllegalArgumentException.class,
//                ()-> new Weapon(1,1,1,0," "), "Ammo constructor test");
//        assertThrows(IllegalArgumentException.class,
//                ()-> new Weapon(1,1,1,1,""), "String constructor test empty");
//        assertThrows(IllegalArgumentException.class,
//                ()-> new Weapon(1,1,1,1,null), "String constructor null");
//    }

    @Test
    void testDamageGS() {
        assertEquals(10d,w.getDamage(),"Damage not equal");
        w.setDamage(2d);
        assertEquals(2d,w.getDamage(),"Damage not equal");
        assertThrows(IllegalArgumentException.class,
                ()-> w.setDamage(0), "Damage 0 test");
        assertThrows(IllegalArgumentException.class,
                ()-> w.setDamage(-1), "Damage -1 test");
    }

    @Test
    void testFRGS() {
        assertEquals(0.8d,w.getFireRate(),"FR not equal");
        w.setFireRate(2d);
        assertEquals(2d,w.getFireRate(),"FR not equal");
        assertThrows(IllegalArgumentException.class,
                ()-> w.setFireRate(0), "FR 0 test");
        assertThrows(IllegalArgumentException.class,
                ()-> w.setFireRate(-1), "FR -1 test");
    }

    @Test
    void testAccGS() {
        assertEquals(0.5d,w.getAccuracy(),"Acc not equal");
        w.setAccuracy(0.6d);
        assertEquals(0.6d,w.getAccuracy(),"Acc not equal");
        assertThrows(IllegalArgumentException.class,
                ()-> w.setAccuracy(0), "Acc 0 test");
        assertThrows(IllegalArgumentException.class,
                ()-> w.setAccuracy(-1), "Acc -1 test");
        assertThrows(IllegalArgumentException.class,
                ()-> w.setAccuracy(1.1), "Acc 1.1 test");
    }

    @Test
    void testAmmoGS() {
        assertEquals(100,w.getAmmo(),"Ammo not equal");
        w.setAmmo(2);
        assertEquals(2,w.getAmmo(),"Ammo not equal");
        w.setAmmo(0);
        assertEquals(0,w.getAmmo(), "Ammo 0 test");
        assertThrows(IllegalArgumentException.class,
                ()-> w.setAmmo(-1), "Ammo -1 test");
    }

    @Test
    void testNameG() {
        assertEquals("Pistol",w.getName(),"Name not equal");
    }

    @Test
    void testDamRoll() {
        Stack<Double> rolls = new Stack<>();
        for (int i = 0; i < 42; i++) {
            rolls.push(w.rollDamage());
        }
        while (!rolls.isEmpty()) {
            double val = rolls.pop();
            if (val < 8 || val > 12) {
                fail("Bad damage roll: " + val);
            }
        }
    }

    @Test
    void testToString() {
        assertEquals("Pistol " + 100,w.toString(),"ToString bad");
    }

    @Test
    void testEquals() {
        Weapon b = new Weapon("Pistol");
        assertEquals(w,b,"Weapon copy not equal");
        b = new Weapon("WaifuBlade");
        assertNotEquals(w,b,"Bad Weapon copy is equal");
        Object c = new Object();
        assertNotEquals(w,c,"Base Object is equal");
    }

}
