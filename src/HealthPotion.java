public class HealthPotion extends Item {

    /** Amount of health to restore. */
    private static final int HEAL_AMOUNT = 50;


/**
     * Default constructor.
     * @param theName name of item.
     */
    public HealthPotion(final String theName) {
        super(theName);
    }

    /**
     * Restore health to specified character.
     * @param theDoomGuy character to restore health to.
     */
    public void use(final DungeonCharacter theDoomGuy) {
        if (theDoomGuy == null) {
            throw new NullPointerException("HP.use : null user passed");
        }
        theDoomGuy.setHealth(theDoomGuy.getHealth() + HEAL_AMOUNT);
    }

    @Override
    public String toString() {
        return null;
    }
}
