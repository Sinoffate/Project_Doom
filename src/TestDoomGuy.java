import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

public class TestDoomGuy {

    DoomGuy d;

    @BeforeEach
    void reset() {
        d = new DoomGuy(1,"DG",new Weapon("Pistol"));
    }

    @Test
    void testConBad() {
        assertThrows(IllegalArgumentException.class,
                () -> new DoomGuy(0,"DG",
                        new Weapon("Pistol")),
                "DG con 0 HP");
        assertThrows(IllegalArgumentException.class,
                () -> new DoomGuy(1,"",
                        new Weapon("Pistol")),
                "DG con emp string");
        assertThrows(IllegalArgumentException.class,
                () -> new DoomGuy(1,null,
                        new Weapon("Pistol")),
                "DG con null name");
        assertThrows(IllegalArgumentException.class,
                () -> new DoomGuy(1,"DG",
                        null),
                "DG con null weap");
    }

    @Test
    void testConGood() {
        d = new DoomGuy(1,"DG",new Weapon("Pistol"));
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
        assertEquals(0.5d, d.getAccuracy(),"Bad Acc");
        assertEquals(0.8d, d.getFireRate(),"Bad FR");
        assertEquals(10d, d.getDamage(),"Bad Dam");
        assertEquals(100, d.getAmmo(),"Bad Ammo");
    }

    @Test
    void tGWeap() {
        Weapon t = d.getEquippedWeapon();
        assertEquals(t,new Weapon("Pistol"),"Bad weap");
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
        Monster m = new BaronOfHell();

        Weapon w = new Weapon("BFG");
        d.addToInventory(w);
        d.equipWeapon(w);

        for (int i = 0; i < 20; i++) {
            if (d.attack(m).equals("Attack Missed")) {
                fail("Missed with 100% accuracy");
            }
        }
    }

    @Test
    void tAttackAcc50() {
        Monster m = new Imp();

        int count = 0;
        for (int i = 0; i < 100; i++) {
            if (d.attack(m).equals("Attack Missed") && count++ > 70) {
                fail("Missed with 50% accuracy 70 times");
            }
        }

    }

    @Test
    void tTakeDamage() {
        Monster m = new BaronOfHell();

        d.setHealth(1000);
        Weapon w = new Weapon("Shotgun");
        d.addToInventory(w);
        d.equipWeapon(w);

        //40d,0.5f,0.7a,20am

        for (int i = 0; i < 20; i++) {
            m.attack(d);
        }

        if (d.getHealth() < (1000-(6*20*0.6)) || d.getHealth() > (1000-(4*20*0.4))) {
            fail("Bad damage, D HP: " + d.getHealth());
        }
    }

    @Test
    void tGMHealth() {
        assertEquals(1,d.getMaxHealth(),"Max HP bad");
    }

    @Test
    void tAddToInv() {
        Item test = new Pillar("JUnit");
        Weapon weap = new Weapon("Shotgun");
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
        Weapon weap = new Weapon("Shotgun");
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
        Weapon weap = new Weapon("Shotgun");
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
        Weapon weap = new Weapon("Shotgun");
        d.addToInventory(weap);

        Weapon temp = d.getEquippedWeapon();

        d.equipWeapon(weap);

        assertEquals(weap,d.getEquippedWeapon(),"Weap swap");

        d.equipWeapon(temp);

        assertEquals(temp,d.getEquippedWeapon(),"and back");
    }

}