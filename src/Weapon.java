/**
 * Concrete Weapon class for DungeonCharacters.
 * @author james deal, jered wiegel
 * @version 0.1
 */
public class Weapon extends Item {

    /** Hard-coded deviance for weapons. */
    private static final float DEVIANCE = 0.2f;

    /** Damage for weapon. */
    private double myDamage;
    /** Fire-rate for weapon. */
    private double myFireRate;
    /** Accuracy for weapon from 0-1. */
    private double myAccuracy;
    /** Ammo for weapon. */
    private int myAmmo;

    /**
     * Default constructor for weapon.
     * @param theDam damage.
     * @param theFR fire-rate.
     * @param theAcc accuracy 0-1.
     * @param theAmm ammo.
     * @param theNam name.
     */
    public Weapon(final double theDam, final double theFR, final double theAcc,
                  final int theAmm, final String theNam) {
        super(theNam);
        if (theDam <= 0 || theFR <= 0 || theAcc <= 0 || theAcc > 1 || theAmm < 1) {
            throw new IllegalArgumentException("Weapon.con failed with following"
                                     + " values: " + theDam + theFR + theAcc + theAmm);
        }
        myDamage = theDam;
        myFireRate = theFR;
        myAccuracy = theAcc;
        myAmmo = theAmm;
    }

    /**
     * Generates a random damage amount for an attack using this weapon.
     * Uses base damage wrapped around a specified acceptable range for the random amount.
     * @return amount of damage to deal.
     */
    public double rollDamage() {
        return (DiceRoll.nextFloat(DEVIANCE) + (1 - (DEVIANCE / 2))) * this.myDamage;
    }

    @Override
    public String toString() {
        return this.getName() + " " + myAmmo;
    }

    public double getDamage() {
        return myDamage;
    }

    public void setDamage(final double theDamage) {
        if (theDamage <= 0) {
            throw new IllegalArgumentException("Weapon.setDam given value: " + theDamage);
        }
        this.myDamage = theDamage;
    }

    public double getFireRate() {
        return myFireRate;
    }

    public void setFireRate(final double theFireRate) {
        if (theFireRate <= 0) {
            throw new IllegalArgumentException("Weapon.setFR given value: " + theFireRate);
        }
        this.myFireRate = theFireRate;
    }

    public double getAccuracy() {
        return myAccuracy;
    }

    public void setAccuracy(final double theAccuracy) {
        if (theAccuracy <= 0 || theAccuracy > 1) {
            throw new IllegalArgumentException("Weapon.setAcc given value: " + theAccuracy);
        }
        this.myAccuracy = theAccuracy;
    }

    public int getAmmo() {
        return myAmmo;
    }

    public void setAmmo(final int theAmmo) {
        if (theAmmo <= 0) {
            throw new IllegalArgumentException("Weapon.setAmmo given value: " + theAmmo);
        }
        this.myAmmo = theAmmo;
    }
}
