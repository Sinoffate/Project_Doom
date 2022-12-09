import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

public class TestMonster {

    Monster im;
    Monster b;
    Monster a;

    @BeforeEach
    void reset() {
        im = new Imp();
        b = new BaronOfHell();
        a = new AnimeWaifu();
    }

    @Test
    void tGainHealth() {
        assertEquals(400,a.getHealth(),"Start hp bad");
        a.gainHealth(100);
        assertEquals(500,a.getHealth(),"Start hp bad");
        assertThrows(IllegalArgumentException.class,
                () -> a.gainHealth(-1),
                "Mon gh -1");
        assertThrows(IllegalArgumentException.class,
                () -> a.gainHealth(0),
                "Mon gh 0");
    }

    @Test
    void tGetWeapStats() {
        assertEquals(0.3d,a.getAccuracy(),"Bad Acc");
        assertEquals(0.8d,a.getFireRate(),"Bad FR");
        assertEquals(15d,a.getDamage(),"Bad Dam");
        assertEquals(900,a.getAmmo(),"Bad Ammo");
    }

    @Test
    void tGWeap() {
        Weapon t = a.getEquippedWeapon();
        assertEquals(t,new Weapon("WaifuBlade"),"Bad weap");
        t = im.getEquippedWeapon();
        assertEquals(t,new Weapon("FireBall"),"Bad weap");
        t = b.getEquippedWeapon();
        assertEquals(t,new Weapon("Claw"),"Bad weap");
    }

    @Test
    void tGSHealth() {
        assertEquals(400,a.getHealth(),"bad health");
        a.setHealth(2);
        assertEquals(2,a.getHealth(),"bad health");
        a.setHealth(-1);
        assertEquals(-1,a.getHealth(),"bad health");
        a.setHealth(0);
        assertEquals(0,a.getHealth(),"bad health");
    }

    @Test
    void tGName() {
        assertEquals("AnimeWaifu",a.getName(),"Bad name");
    }

    @Test
    void tToString() {
        assertEquals("AnimeWaifu",a.toString(),"Bad toString");
    }

    @Test
    void tAttackAccA() {
        DoomGuy d = new DoomGuy(1000,"DG",new Weapon("Shotgun"));

        int hitCount = 0;
        for (int i = 0; i < 20; i++) {
            String res = a.attack(d);
            if (!res.equals("Attack Missed")) {
                hitCount++;
            }
        }

        if (hitCount > 9 || hitCount < 3) {
            fail("Waifu hit count out of expected range: " + hitCount);
        }
        if (d.getHealth() < (1000-(17*9)) || d.getHealth() > (1000-(13*3))) {
            fail("Waifu damage count out of expected range: " + d.getHealth());
        }
    }

    @Test
    void tAttackAccI() {
        DoomGuy d = new DoomGuy(1000,"DG",new Weapon("Shotgun"));

        int hitCount = 0;
        for (int i = 0; i < 20; i++) {
            String res = im.attack(d);
            if (!res.equals("Attack Missed")) {
                hitCount++;
            }
        }

        if (hitCount > 17 || hitCount < 7) {
            fail("Imp hit count out of expected range: " + hitCount);
        }
        if (d.getHealth() < (1000-(12*17)) || d.getHealth() > (1000-(8*7))) {
            fail("Imp damage count out of expected range: " + d.getHealth());
        }
    }

    @Test
    void tAttackAccB() {
        DoomGuy d = new DoomGuy(1000,"DG",new Weapon("Shotgun"));

        int hitCount = 0;
        for (int i = 0; i < 20; i++) {
            String res = b.attack(d);
            if (!res.equals("Attack Missed")) {
                hitCount++;
            }
        }

        if (hitCount > 12 || hitCount < 4) {
            fail("Waifu hit count out of expected range: " + hitCount);
        }
        if (d.getHealth() < (1000-(6*12)) || d.getHealth() > (1000-(4*4))) {
            fail("Waifu damage count out of expected range: " + d.getHealth());
        }
    }

    @Test
    void tTakeDamage() {
        DoomGuy d = new DoomGuy(1000,"DG",new Weapon("BFG"));

        String res = d.attack(im);
        if (im.getHealth() > -800) {
            fail("Bad imp hp: " + im.getHealth());
        }
        assertEquals(1000,d.getHealth(),"Doomguy hit himself");

        res = d.attack(b);
        if (b.getHealth() > -700) {
            fail("Bad baron hp: " + b.getHealth());
        }
        assertEquals(990,d.getHealth(),"Doomguy didn't take recoil");

        res = d.attack(a);
        if (b.getHealth() > -400) {
            fail("Bad waifu hp: " + a.getHealth());
        }
        assertEquals(992,d.getHealth(),"Doomguy didn't get some lovin");
    }

}