package model;

/**
 * AnimeWaifu is a class that defines the special behavior
 * of the monster that is the final boss of the game.
 * @author Jered Wiegel
 * @version 1.0
 */
public class AnimeWaifu extends Monster {
    /** Healing amount that AnimeWaifu will heal the player. */
    static final int THE_LOVE = 2;

    /**
     * Constructor for the AnimeWaifu class.
     */
    public AnimeWaifu() {
        super(400, "AnimeWaifu", new Weapon("WaifuBlade"));
    }

    /**
     * Applies damage to character.
     * @param theDamageTaken Damage amount to round down and apply to character.
     * @return String containing resulting information for use in View.
     */
    @Override
    public String takeDamage(final double theDamageTaken, final DungeonCharacter theOpponent) {
        if (theDamageTaken < 0) {
            throw new IllegalArgumentException("DunCha.takeDamage, "
                    + "positive number passed: " + theDamageTaken);
        }
        this.setHealth((int) (this.getHealth() - Math.floor(theDamageTaken)));
        theOpponent.setHealth(theOpponent.getHealth() + THE_LOVE);

        return "Damage taken: " + (int) theDamageTaken + " but "
                + "AnimeWaifu still loves you, so you heal "
                + THE_LOVE + " HP: " + (theOpponent.getHealth());
    }
}
