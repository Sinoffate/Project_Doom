import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

public class TestMonster {

    Monster m;

    @BeforeEach
    void reset() {
        m = new Monster(1,"Mon",
                new Weapon(1,1,1,1,"Weapon Test"));
    }

    @Test
    void testConBad() {
        assertThrows(IllegalArgumentException.class,
                () -> new Monster(0,"Mon",
                        new Weapon(1,1,1,1,"Mon Test")),
                "Mon con 0 HP");
        assertThrows(IllegalArgumentException.class,
                () -> new Monster(1,"",
                        new Weapon(1,1,1,1,"Mon Test")),
                "Mon con emp string");
        assertThrows(IllegalArgumentException.class,
                () -> new Monster(1,null,
                        new Weapon(1,1,1,1,"Mon Test")),
                "Mon con null name");
        assertThrows(IllegalArgumentException.class,
                () -> new Monster(1,"Mon",
                        null),
                "Mon con null weap");
    }

    @Test
    void testConGood() {
        m = new Monster(1, "Mon",
                new Weapon(1, 1, 1, 1, "Weapon Test"));
    }

    @Test
    void tGainHealth() {
        assertEquals(1,m.getHealth(),"Start hp bad");
        m.gainHealth(1);
        assertEquals(2,m.getHealth(),"Start hp bad");
        assertThrows(IllegalArgumentException.class,
                () -> m.gainHealth(-1),
                "Mon gh -1");
        assertThrows(IllegalArgumentException.class,
                () -> m.gainHealth(0),
                "Mon gh 0");
    }

    @Test
    void tGetWeapStats() {
        assertEquals(1d,m.getAccuracy(),"Bad Acc");
        assertEquals(1d,m.getFireRate(),"Bad FR");
        assertEquals(1d,m.getDamage(),"Bad Dam");
        assertEquals(1,m.getAmmo(),"Bad Ammo");
    }

    @Test
    void tGWeap() {
        Weapon t = m.getEquippedWeapon();
        assertEquals(t,new Weapon(1,1,1,1,"Weapon Test"),"Bad weap");
    }

    @Test
    void tGSHealth() {
        assertEquals(1,m.getHealth(),"bad health");
        m.setHealth(2);
        assertEquals(2,m.getHealth(),"bad health");
        m.setHealth(-1);
        assertEquals(-1,m.getHealth(),"bad health");
        m.setHealth(0);
        assertEquals(0,m.getHealth(),"bad health");
    }

    @Test
    void tGName() {
        assertEquals("Mon",m.getName(),"Bad name");
    }

    @Test
    void tToString() {
        assertEquals("Mon",m.toString(),"Bad toString");
    }

    @Test
    void tAttackAcc100() {
        DoomGuy d = new DoomGuy(1000,"DG",new Weapon(1,1,1,1,"DGW"));

        for (int i = 0; i < 20; i++) {
            if (m.attack(d).equals("Attack Missed")) {
                fail("Missed with 100% accuracy");
            }
        }
    }

    @Test
    void tAttackAcc80() {
        DoomGuy d = new DoomGuy(1000,"DG",new Weapon(1,1,1,1,"DGW"));
        m = new Monster(1,"Mon",
                new Weapon(1,1,.8,1,"Weapon Test"));

        int count = 0;
        for (int i = 0; i < 100; i++) {
            if (m.attack(d).equals("Attack Missed") && count++ > 30) {
                fail("Missed with 80% accuracy 30 times");
            }
        }

    }

    @Test
    void tAttackAcc0() {
        DoomGuy d = new DoomGuy(1000,"DG",new Weapon(1,1,1,1,"DGW"));
        m = new Monster(1,"Mon",
                new Weapon(1,1,.00000001d,1,"Weapon Test"));

        int count = 0;
        for (int i = 0; i < 100; i++) {
            if (!m.attack(d).equals("Attack Missed") && count++ > 1) {
                fail("Hit twice somehow");
            }
        }
    }

    @Test
    void tTakeDamage() {
        DoomGuy d = new DoomGuy(1000,"DG",new Weapon(1,1,1,1,"DGW"));
        m = new Monster(1,"Mon",
                new Weapon(10,1,1,1,"Weapon Test"));

        ArrayList<String> al = new ArrayList<>();
        for (int i = 8; i <= 12; i++) {
            al.add("Damage taken: " + i);
        }

        for (int i = 0; i < 10; i++) {
            String res = m.attack(d);
            if (!al.contains(res)) {
                fail("Attack was: " + res);
            }
        }

        if (d.getHealth() < (1000-120) || d.getHealth() > (1000-80)) {
            fail("Bad damage, D took: " + d.getHealth());
        }
    }

}