package model;

/**
 * Concrete Weapon class for DungeonCharacters.
 * @author james deal, jered wiegel
 * @version 2.0
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
     * Default constructor for weapon. Depreciated, left in case needed for testing.
     * @param theDam damage.
     * @param theFR fire-rate.
     * @param theAcc accuracy 0-1.
     * @param theAmm ammo.
     * @param theNam name.
     */
    private Weapon(final double theDam, final double theFR, final double theAcc,
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
     * Constructor for Weapons.
     * @param theWeaponName name of weapon to create.
     */
    public Weapon(final String theWeaponName) {
        super(theWeaponName);
        final Database db = Database.getInstance();
        final String[] weaponAttri = db.selectOne("Weapons", theWeaponName).split(",");
        if (weaponAttri.length != 4) {
            throw new RuntimeException("Bad DB return, length: " + weaponAttri.length);
        }
        myDamage = Double.parseDouble(weaponAttri[0]);
        myFireRate = Double.parseDouble(weaponAttri[1]);
        myAccuracy = Double.parseDouble(weaponAttri[2]);
        myAmmo = Integer.parseInt(weaponAttri[3]);
    }

    /**
     * Generates a random damage amount for an attack using this weapon.
     * Uses base damage wrapped around a specified acceptable range for the random amount.
     * @return amount of damage to deal.
     */
    public double rollDamage() {
        return (DiceRoll.nextFloat(DEVIANCE) + (1 - (DEVIANCE / 2))) * this.myDamage;
    }

    /**
     * Returns the name and ammo of the weapon.
     * @return name and ammo as a String
     */
    @Override
    public String toString() {
        return this.getName() + " " + myAmmo;
    }

    /**
     * Gets the damage of the weapon.
     * @return damage.
     */
    public double getDamage() {
        return myDamage;
    }

    /**
     * Sets the damage of the weapon.
     * @param theDamage New damage value
     */
    public void setDamage(final double theDamage) {
        if (theDamage <= 0) {
            throw new IllegalArgumentException("Weapon.setDam given value: " + theDamage);
        }
        this.myDamage = theDamage;
    }

    /**
     * Gets the fire-rate of the weapon.
     * @return fire-rate.
     */
    public double getFireRate() {
        return myFireRate;
    }

    /**
     * Sets the fire-rate of the weapon.
     * @param theFireRate New fire-rate value
     */
    public void setFireRate(final double theFireRate) {
        if (theFireRate <= 0) {
            throw new IllegalArgumentException("Weapon.setFR given value: " + theFireRate);
        }
        this.myFireRate = theFireRate;
    }

    /**
     * Gets the accuracy of the weapon.
     * @return accuracy.
     */
    public double getAccuracy() {
        return myAccuracy;
    }

    /**
     * Sets the accuracy of the weapon.
     * @param theAccuracy New accuracy value
     */
    public void setAccuracy(final double theAccuracy) {
        if (theAccuracy <= 0 || theAccuracy > 1) {
            throw new IllegalArgumentException("Weapon.setAcc given value: " + theAccuracy);
        }
        this.myAccuracy = theAccuracy;
    }

    /**
     * Gets the ammo of the weapon.
     * @return ammo.
     */
    public int getAmmo() {
        return myAmmo;
    }

    /**
     * Sets the ammo of the weapon.
     * @param theAmmo New ammo value
     */
    public void setAmmo(final int theAmmo) {
        if (theAmmo < 0) {
            throw new IllegalArgumentException("Weapon.setAmmo given value: " + theAmmo);
        }
        this.myAmmo = theAmmo;
    }
}
