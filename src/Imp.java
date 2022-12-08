public class Imp extends Monster {
    final static int FRAILTY = 10;
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
            throw new IllegalArgumentException("DunCha.takeDamage, positive number passed: " + theDamageTaken);
        }
        this.setHealth((int) (this.getHealth() - Math.floor(theDamageTaken)));
        theOpponent.setHealth(theOpponent.getHealth() - FRAILTY);

        return "Imp takes " + (int) theDamageTaken + " and falls downs some stairs for " + FRAILTY + "!";
    }
}
