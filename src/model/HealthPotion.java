package model;

/**
 * This class represents a health potion that can be used to heal the player.
 * @author Jered Wiegel
 * @version 1.0
 */
public class HealthPotion extends Item {

    /** Amount of health to restore. */
    private final int myHealAmount;

    /**
     * Default constructor.
     */
    public HealthPotion() {
        super("Health Potion");
        final Database db = Database.getInstance();
        final String potVal = db.selectOne("Items", "HealthPotion");
        myHealAmount = Integer.parseInt(potVal);
    }

    /**
     * Restore health to specified character.
     * @param theDoomGuy character to restore health to.
     */
    public void useHealthPotion(final DungeonCharacter theDoomGuy) {
        if (theDoomGuy == null) {
            throw new NullPointerException("HP.use : null user passed");
        }
        theDoomGuy.setHealth(theDoomGuy.getHealth() + myHealAmount);
    }

    /**
     * String representation of item.
     * @return Name of item.
     */
    @Override
    public String toString() {
        return getName();
    }
}
