public abstract class DungeonCharacter {

    /** Weapon currently equipped. */
    protected Weapon myEquippedWeapon;

    /** Current health. */
    private int myHealth;
    /** Name of character. */
    private final String myName;

    /**
     * Default constructor for DC abstract objects.
     * @param theHealth starting health of DC.
     * @param theName name of DC.
     * @param theWeapon starting weapon of DC.
     */
    public DungeonCharacter(final int theHealth, final String theName, final Weapon theWeapon) {
        if (theHealth <= 0 || theName == null || "".equals(theName) || theWeapon == null) {
            throw new IllegalArgumentException("DC.con bad arguments: HP:" + theHealth);
        }
        myHealth = theHealth;
        myName = theName;
        myEquippedWeapon = theWeapon;
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
        if (DiceRoll.nextFloat() > myEquippedWeapon.getAccuracy()) {
            return "Attack Missed";
        }

        //Roll Damage
        return theOpponent.takeDamage(myEquippedWeapon.rollDamage());
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
        return "Damage taken: " + (int) theDamageTaken;
    }

    /**
     * Applies relative health gain amounts to DC. Does not allow for health loss.
     * @param theGains amount of health to gain.
     */
    public void gainHealth(final int theGains) {
        if (theGains > 0) {
            myHealth += theGains;
        } else {
            throw new IllegalArgumentException("DunCha.gainHealth, negative number given: " + theGains);
        }
    }

    /**
     * Returns accuracy of current weapon.
     * @return accuracy.
     */
    public double getAccuracy() {
        return myEquippedWeapon.getAccuracy();
    }

    /**
     * Returns FireRate of current weapon.
     * @return FireRate.
     */
    public double getFireRate() {
        return myEquippedWeapon.getFireRate();
    }

    /**
     * Returns Damage for the current weapon.
     * @return Damage.
     */
    public double getDamage() {
        return myEquippedWeapon.getDamage();
    }

    /**
     * Returns Ammo for the current weapon.
     * @return Ammo.
     */
    public int getAmmo() {
        return myEquippedWeapon.getAmmo();
    }

    /**
     * Get current health value.
     * @return current health.
     */
    public int getHealth() {
        return myHealth;
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

    /**
     * Get currently equipped weapon.
     * @return active weapon.
     */
    public Weapon getEquippedWeapon() {
        return myEquippedWeapon;
    }

    @Override
    public String toString() {
        return myName;
    }
}
