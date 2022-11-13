import main.Inventory;

public class DoomGuy extends DungeonCharacter{

    /**TEMP Number to shift random generator output by for damage calculation. */
    private static final float DAMAGE_MULTIPLIER_OFFSET = 0.5f;

    /** Max health allowed for DGuy. */
    private final int myMaxHealth;
    /** Reference to DGuy inventory. */
    private final Inventory myInventory;
    /** Weapon currently equipped. */
    private Weapon myEquippedWeapon;

    /**
     * Default constructor. Applies a starting weapon's stats as DGuy's base stats in DCha.
     * @param theHealth starting health.
     * @param theAcc base accuracy stat.
     * @param theFireRate base fire-rate sta.
     * @param theDamage base damage stat.
     * @param theName name of Dguy.
     * @param theStartingWeapon starting weapon for DGuy.
     */
    public DoomGuy(final int theHealth, final double theAcc, final double theFireRate,
                   final double theDamage, final String theName, final Weapon theStartingWeapon) {
        super(theHealth,theAcc,theFireRate,theDamage,theName);
        myMaxHealth = theHealth;
        myInventory = new Inventory();
//        myInventory.addItem(theStartingWeapon);
//        equipWeapon(theStartingWeapon);
    }

    /**
     * Add specified item to DGuy inventory.
     * @param theItem item to add.
     */
//    public void addToInventory(final Item theItem) {
//        if (theItem == null) {
//            throw new NullPointerException("DG.addToInventory : null item passed");
//        }
//        myInventory.addItem(theItem);
//    }

    /**
     * Remove specified item from DGuy inventory.
     * @param theItem item to remove.
     */
//    public void removeFromInventory(final Item theItem) {
//        if (theItem == null) {
//            throw new NullPointerException("DG.removeFromInventory : null item passed");
//        }
//        myInventory.removeItem(theItem);
//    }

    /**
     * Set specified weapon as active weapon.
     * @param theWeapon weapon object to equip.
     */
//    public void equipWeapon(final Weapon theWeapon) {
//        if (myInventory.containsItem(theWeapon)) {
//            myEquippedWeapon = theWeapon;
//        }
//    }

    /**
     * Check if DGuy inventory contains specified item.
     * @param theItem item to find.
     * @return true if found.
     */
//    public boolean inventoryContains(final Item theItem) {
//        return myInventory.containsItem(theItem);
//    }

    //Overridden methods

    /**
     * Performs attack against another DC object.
     * @param theOpponent target of attack.
     * @return String of attack result.
     */
    @Override
    public String attack(final DungeonCharacter theOpponent) {
        if (theOpponent == null) {
            throw new NullPointerException("DGuy.attack, opponent was null");
        }

        //Roll accuracy
        if (DICE_ROLL.nextFloat() > myEquippedWeapon.getAccuracy()) {
            return "Attack Missed";
        }

        //Roll Damage
        final float multiplier = DICE_ROLL.nextFloat() + DAMAGE_MULTIPLIER_OFFSET;
        return theOpponent.takeDamage(multiplier * myEquippedWeapon.getDamage());
    }

    /**
     * Returns accuracy of current weapon.
     * @return accuracy.
     */
    @Override
    public double getAccuracy() {
        return myEquippedWeapon.getAccuracy();
    }

    /**
     * Returns FireRate of current weapon.
     * @return FireRate.
     */
    @Override
    public double getFireRate() {
        return myEquippedWeapon.getFireRate();
    }

    /**
     * Returns Damage for the current weapon.
     * @return Damage.
     */
    @Override
    public double getDamage() {
        return myEquippedWeapon.getDamage();
    }

    //GetSet Methods

    /**
     * Get max health DGuy allowed to have.
     * @return max health allowed.
     */
    public int getMaxHealth() {
        return myMaxHealth;
    }

    /**
     * Get inventory object of DGuy.
     * @return inventory.
     */
    public Inventory getInventory() {
        return myInventory;
    }

    /**
     * Get currently equipped weapon.
     * @return active weapon.
     */
    public Weapon getEquippedWeapon() {
        return myEquippedWeapon;
    }

}
