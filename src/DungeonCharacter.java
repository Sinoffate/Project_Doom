import java.util.Random;

public abstract class DungeonCharacter {

    /** Random value source. */
    protected static final Random DICE_ROLL = new Random();

    /**Number to shift random generator output by for damage calculation. */
    private static final float DAMAGE_MULTIPLIER_OFFSET = 0.5f;

    /** Current health. */
    private int myHealth;
    /** Accuracy of shot. */
    private final double myAccuracy;
    /** Fire-rate during combat. */
    private final double myFireRate;
    /** Damage value of basic combat attack. */
    private final double myDamage;
    /** Name of character. */
    private final String myName;

    /**
     * Default constructor for DC abstract objects.
     * @param theHealth starting health of DC.
     * @param theAcc attack accuracy of DC.
     * @param theFireRate fire-rate/speed of DC.
     * @param theDamage unarmed damage of DC.
     * @param theName name of DC.
     */
    public DungeonCharacter(final int theHealth, final double theAcc,
                             final double theFireRate, final double theDamage, final String theName) {
        myHealth = theHealth;
        myAccuracy = theAcc;
        myFireRate = theFireRate;
        myDamage = theDamage;
        myName = theName;
    }

    /**
     * Performs attack against another DC object.
     * @param theOpponent target of attack.
     * @return String of attack result.
     */
    public String attack(final DungeonCharacter theOpponent) {
        if (theOpponent == null) {
            throw new NullPointerException("DunCha.attack, opponent was null");
        }

        //Roll accuracy
        if (DICE_ROLL.nextFloat() > myAccuracy) {
            return "Attack Missed";
        }

        //Roll Damage
        final float multiplier = DICE_ROLL.nextFloat() + DAMAGE_MULTIPLIER_OFFSET;
        return theOpponent.takeDamage(multiplier * myDamage);
    }

    /**
     * Applies damage to character.
     * @param theDamageTaken Damage amount to round down and apply to character.
     * @return String containing resulting information for use in View.
     */
    public String takeDamage(final double theDamageTaken) {
        if (theDamageTaken < 0) {
            throw new IllegalArgumentException("DunCha.takeDamage, positive number passed: " + theDamageTaken);
        }
        myHealth -= Math.floor(theDamageTaken);
        return "Damage taken: " + theDamageTaken;
    }

    /**
     * Applies relative health gain amounts to DC. Does not allow for health loss.
     * @param theGains amount of health to gain.
     * @return String containing resulting information for use in view.
     */
    public String gainHealth(final int theGains) {
        if (theGains > 0) {
            myHealth += theGains;
        } else {
            throw new IllegalArgumentException("DunCha.gainHealth, negative number given: " + theGains);
        }
        return Integer.toString(myHealth);
    }

    //GetSet Methods

    /**
     * Get current health value.
     * @return current health.
     */
    public int getHealth() {
        return myHealth;
    }

    /**
     * Get accuracy value.
     * @return accuracy.
     */
    public double getAccuracy() {
        return myAccuracy;
    }

    /**
     * Get damage value.
     * @return damage.
     */
    public double getDamage() {
        return myDamage;
    }

    /**
     * Get firerate value.
     * @return firerate.
     */
    public double getFireRate() {
        return myFireRate;
    }

    /**
     * Get character name.
     * @return name.
     */
    public String getName() {
        return myName;
    }

    /**
     * Set health to specific value.
     * @param theHealth value to change to.
     */
    public void setHealth(final int theHealth) {
        myHealth = theHealth;
    }
}
