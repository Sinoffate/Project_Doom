/**
 * This class represents a health potion that can be used to heal the player.
 * @author Jered Wiegel
 * @version 1.0
 */
public class HealthPotion extends Item {

    /** Amount of health to restore. */
    private static final int HEAL_AMOUNT = 50;


    /**
     * Default constructor.
     *
     */
    public HealthPotion() {
        super("Health Potion");
    }

    /**
     * Restore health to specified character.
     * @param theDoomGuy character to restore health to.
     */
    public void useHealthPotion(final DungeonCharacter theDoomGuy) {
        if (theDoomGuy == null) {
            throw new NullPointerException("HP.use : null user passed");
        }
        theDoomGuy.setHealth(theDoomGuy.getHealth() + HEAL_AMOUNT);
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
