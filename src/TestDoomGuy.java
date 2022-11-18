import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

public class TestDoomGuy {

    DoomGuy d;

    @BeforeEach
    void reset() {
        d = new DoomGuy(1,"DG",new Weapon(1,1,1,1,"DGW"));
    }

    @Test
    void testConBad() {
        assertThrows(IllegalArgumentException.class,
                () -> new DoomGuy(0,"DG",
                        new Weapon(1,1,1,1,"DG Test")),
                "DG con 0 HP");
        assertThrows(IllegalArgumentException.class,
                () -> new DoomGuy(1,"",
                        new Weapon(1,1,1,1,"DG Test")),
                "DG con emp string");
        assertThrows(IllegalArgumentException.class,
                () -> new DoomGuy(1,null,
                        new Weapon(1,1,1,1,"DG Test")),
                "DG con null name");
        assertThrows(IllegalArgumentException.class,
                () -> new DoomGuy(1,"DG",
                        null),
                "DG con null weap");
    }

    @Test
    void testConGood() {
        d = new DoomGuy(1,"DG",new Weapon(1,1,1,1,"DGW"));
    }

    @Test
    void tGainHealth() {
        assertEquals(1, d.getHealth(),"Start hp bad");
        d.gainHealth(1);
        assertEquals(2, d.getHealth(),"Start hp bad");
        assertThrows(IllegalArgumentException.class,
                () -> d.gainHealth(-1),
                "DG gh -1");
        assertThrows(IllegalArgumentException.class,
                () -> d.gainHealth(0),
                "DG gh 0");
    }

    @Test
    void tGetWeapStats() {
        assertEquals(1d, d.getAccuracy(),"Bad Acc");
        assertEquals(1d, d.getFireRate(),"Bad FR");
        assertEquals(1d, d.getDamage(),"Bad Dam");
        assertEquals(1, d.getAmmo(),"Bad Ammo");
    }

    @Test
    void tGWeap() {
        Weapon t = d.getEquippedWeapon();
        assertEquals(t,new Weapon(1,1,1,1,"DGW"),"Bad weap");
    }

    @Test
    void tGSHealth() {
        assertEquals(1, d.getHealth(),"bad health");
        d.setHealth(2);
        assertEquals(2, d.getHealth(),"bad health");
        d.setHealth(-1);
        assertEquals(-1, d.getHealth(),"bad health");
        d.setHealth(0);
        assertEquals(0, d.getHealth(),"bad health");
    }

    @Test
    void tGName() {
        assertEquals("DG", d.getName(),"Bad name");
    }

    @Test
    void tToString() {
        assertEquals("DG", d.toString(),"Bad toString");
    }

    @Test
    void tAttackAcc100() {
        Monster m = new Monster(1000,"M",new Weapon(1,1,1,1,"MW"));

        for (int i = 0; i < 20; i++) {
            if (d.attack(m).equals("Attack Missed")) {
                fail("Missed with 100% accuracy");
            }
        }
    }

    @Test
    void tAttackAcc80() {
        Monster m = new Monster(1000,"M",new Weapon(1,1,1,1,"MW"));
        d = new DoomGuy(1,"DG",
                new Weapon(1,1,.8,1,"Weapon Test"));

        int count = 0;
        for (int i = 0; i < 100; i++) {
            if (d.attack(m).equals("Attack Missed") && count++ > 30) {
                fail("Missed with 80% accuracy 30 times");
            }
        }

    }

    @Test
    void tAttackAcc0() {
        Monster m = new Monster(1000,"M",new Weapon(1,1,1,1,"MW"));
        d = new DoomGuy(1,"DG",
                new Weapon(1,1,.00000001d,1,"Weapon Test"));

        int count = 0;
        for (int i = 0; i < 100; i++) {
            if (!d.attack(m).equals("Attack Missed") && count++ > 1) {
                fail("Hit twice somehow");
            }
        }
    }

    @Test
    void tTakeDamage() {
        Monster m = new Monster(1000,"M",new Weapon(1,1,1,1,"MW"));
        d = new DoomGuy(1,"DG",
                new Weapon(10,1,1,1,"Weapon Test"));

        ArrayList<String> al = new ArrayList<>();
        for (int i = 8; i <= 12; i++) {
            al.add("Damage taken: " + i);
        }

        for (int i = 0; i < 10; i++) {
            String res = d.attack(m);
            if (!al.contains(res)) {
                fail("Attack was: " + res);
            }
        }

        if (m.getHealth() < (1000-120) || m.getHealth() > (1000-80)) {
            fail("Bad damage, M HP: " + m.getHealth());
        }
    }

    @Test
    void tGMHealth() {
        assertEquals(1,d.getMaxHealth(),"Max HP bad");
    }

    @Test
    void tAddToInv() {
        Item test = new Pillar("JUnit");
        Weapon weap = new Weapon(1,1,1,1,"Test");
        Inventory inv = d.getInventory();
        assertEquals(1,inv.inventorySize());
        d.addToInventory(test);
        assertEquals(2,inv.inventorySize());
        d.addToInventory(weap);
        assertEquals(3,inv.inventorySize());
    }

    @Test
    void tInvContains() {
        Item test = new Pillar("JUnit");
        Weapon weap = new Weapon(1,1,1,1,"Test");
        Inventory inv = d.getInventory();
        assertEquals(1,inv.inventorySize());
        d.addToInventory(test);
        assertEquals(2,inv.inventorySize());
        d.addToInventory(weap);
        assertEquals(3,inv.inventorySize());

        assertTrue(d.inventoryContains(weap));
        assertTrue(d.inventoryContains(test));
        assertTrue(d.inventoryContains(d.getEquippedWeapon()));
    }

    @Test
    void tInvRemove() {
        Item test = new Pillar("JUnit");
        Weapon weap = new Weapon(1,1,1,1,"Test");
        Inventory inv = d.getInventory();
        assertEquals(1,inv.inventorySize());
        d.addToInventory(test);
        assertEquals(2,inv.inventorySize());
        d.addToInventory(weap);
        assertEquals(3,inv.inventorySize());

        d.removeFromInventory(test);
        assertEquals(2,inv.inventorySize());
        d.removeFromInventory(weap);
        assertEquals(1,inv.inventorySize());
        assertFalse(d.inventoryContains(weap));
        assertFalse(d.inventoryContains(test));
    }

    @Test
    void tEquipWeap() {
        Weapon weap = new Weapon(1,1,1,1,"Test");
        d.addToInventory(weap);

        Weapon temp = d.getEquippedWeapon();

        d.equipWeapon(weap);

        assertEquals(new Weapon(1,1,1,1,"Test"),
                d.getEquippedWeapon(),"Weap swap");

        d.equipWeapon(temp);

        assertEquals(new Weapon(1,1,1,1,"DGW"),
                d.getEquippedWeapon(),"and back");
    }

}