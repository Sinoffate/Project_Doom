import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDatabase {

    private Database database = Database.getInstance();;
    private Weapon testWeapon;
    private HealthPotion testHealthPotion;
    private VisionPotion testVisionPotion;

    private Item[] ITEMS = {new Weapon("BFG"), new Weapon("Shotgun"),
            new Weapon("Pistol"), new Weapon("Rawket Lawnchair"),
            new VisionPotion(), new HealthPotion()};

    @Test
    void testHealthPotionValue() {
        String healthPotion = database.selectOne("Items", "HealthPotion");
        assertEquals("50", healthPotion);
    }

    @Test
    void testVisionPotionValue() {
        String visionPotion = database.selectOne("Items", "VisionPotion");
        assertEquals("1", visionPotion);
    }

    @Test
    void testSelectBFG() {
        String weapon = database.selectOne("Weapons", "BFG");
        assertEquals("1000,69,1.0,420", weapon);
    }
    @Test
    void testSelectShotgun() {
        String weapon = database.selectOne("Weapons", "Shotgun");
        assertEquals("40,0.5,0.7,20", weapon);
    }
    @Test
    void testSelectRocket() {
        String weapon = database.selectOne("Weapons", "Rawket Lawnchair");
        assertEquals("80,0.2,0.9,3", weapon);
    }

    @Test
    void testSelectPistol() {
        String weapon = database.selectOne("Weapons", "Pistol");
        assertEquals("10,0.8,0.5,100", weapon);
    }

    @Test
    void testSelectWaifuBalde() {
        String weapon = database.selectOne("Weapons", "WaifuBlade");
        assertEquals("15,0.8,0.3,900", weapon);
    }

    @Test
    void testSelectClaw() {
        String weapon = database.selectOne("Weapons", "Claw");
        assertEquals("5,0.8,0.4,900", weapon);
    }
    @Test
    void testSelectFireBall() {
        String weapon = database.selectOne("Weapons", "FireBall");
        assertEquals("10,0.4,0.6,900", weapon);
    }
}
