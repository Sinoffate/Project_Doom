package model;

/**
 * Imp class defines behaviors of a monster that can be encountered in the game.
 * @author James Deal
 * @version 1.0
 */
public class Imp extends Monster {

    /** Imp's unique effect. */
    static final int FRAILTY = 10;

    /**
     * Constructor used to create an Imp.
     */
    public Imp() {
        super(40, "Imp", new Weapon("FireBall"));
    }

    /**
     * Applies damage to character.
     * @param theDamageTaken Damage amount to round down and apply to character.
     * @return String containing resulting information for use in View.
     */
    @Override
    public String takeDamage(final double theDamageTaken, final DungeonCharacter theOpponent) {
        if (theDamageTaken < 0) {
            throw new IllegalArgumentException("DunCha.takeDamage, positive number passed: "
                    + theDamageTaken);
        }
        this.setHealth((int) (this.getHealth() - Math.floor(theDamageTaken)));
        this.setHealth(this.getHealth() - FRAILTY);

        return "Imp takes " + (int) theDamageTaken + " and falls downs some stairs for "
                + FRAILTY + "!";
    }
}
