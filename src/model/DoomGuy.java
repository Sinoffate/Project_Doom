package model;

/**
 * DoomGuy is the main character of the game. He is the only character that can
 * move around the map and interact with the environment.
 *
 * @author James R. Deal
 * @version 1.0
 */
public class DoomGuy extends DungeonCharacter {

    /** Max health allowed for DGuy. */
    private final int myMaxHealth;
    /** Reference to DGuy inventory. */
    private final Inventory myInventory;
    /** Count of Pillars. */
    private int myPillarCount;

    /**
     * Default constructor. Applies a starting weapon's stats as DGuy's base stats in DCha.
     * @param theHealth starting health.
     * @param theName name of Dguy.
     * @param theStartingWeapon starting weapon for DGuy.
     */
    public DoomGuy(final int theHealth, final String theName, final Weapon theStartingWeapon) {
        super(theHealth, theName, theStartingWeapon);
        myMaxHealth = theHealth;
        myInventory = new Inventory();
        myInventory.addItem(theStartingWeapon);
        myPillarCount = 0;
    }

    /**
     * Add specified item to DGuy inventory.
     * @param theItem item to add.
     */
    public void addToInventory(final Item theItem) {
        if (theItem == null) {
            throw new NullPointerException("DG.addToInventory : null item passed");
        }
        myInventory.addItem(theItem);
        if (theItem instanceof Pillar) {
            myPillarCount++;
        }
    }

    /**
     * Get the number of pillars collected.
     * @return number of pillars collected.
     */
    public int pillarCount() {
        return myPillarCount;
    }

    /**
     * Remove specified item from DGuy inventory.
     * @param theItem item to remove.
     */
    public void removeFromInventory(final Item theItem) {
        if (theItem == null) {
            throw new NullPointerException("DG.removeFromInventory : null item passed");
        }
        myInventory.removeItem(theItem);
    }

    /**
     * Set specified weapon as active weapon.
     * @param theWeapon weapon object to equip.
     */
    public void equipWeapon(final Weapon theWeapon) {
        if (myInventory.containsItem(theWeapon)) {
            setEquippedWeapon(theWeapon);
        }
    }

    /**
     * This class provides the logic for the player
     * to heal themselves.
     */
    public void useItem(final Item theItem) {
        if (theItem == null) {
            throw new NullPointerException("DG.useItem : null item passed");
        }
        if (theItem instanceof HealthPotion) {
            if (myInventory.containsItem(theItem)) {
                ((HealthPotion) theItem).useHealthPotion(this);
                myInventory.removeItem(theItem);
            }
        }
    }

    /**
     * Check if DGuy inventory contains specified item.
     * @param theItem item to find.
     * @return true if found.
     */
    public boolean inventoryContains(final Item theItem) {
        //System.out.println(myInventory.toString());
        return myInventory.containsItem(theItem);
    }

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
}
