package model;

public class BaronOfHell extends Monster {
    /** The damage BoH does back to the DG. */
    static final int THE_RECOIL = 10;

    public BaronOfHell() {
        super(100, "BaronOfHell", new Weapon("Claw"));
    }

    /**
     * Applies damage to character.
     * @param theDamageTaken Damage amount to round down and apply to character.
     * @return String containing resulting information for use in View.
     */
    @Override
    public String takeDamage(final double theDamageTaken, final DungeonCharacter theOpponent) {
        if (theDamageTaken < 0) {
            throw new IllegalArgumentException("DunCha.takeDamage, positive number passed: " + theDamageTaken);
        }
        this.setHealth((int) (this.getHealth() - Math.floor(theDamageTaken)));
        theOpponent.setHealth(theOpponent.getHealth() - THE_RECOIL);

        return "Baron takes " + (int) theDamageTaken + " and hits DoomGuy for " + THE_RECOIL + "!";
    }

}
