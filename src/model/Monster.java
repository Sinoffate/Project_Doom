package model;

/**
 * Abstract class that defines the basic properties of a monster.
 * @author Jered Wiegel
 * @version 1.0
 */
public abstract class Monster extends DungeonCharacter {

    /**
     * Default constructor for DC abstract objects.
     * @param theHealth         starting health of monster.
     * @param theName           name of monster.
     * @param theStartingWeapon base weapon of monster, not changeable
     */
    public Monster(final int theHealth, final String theName, final Weapon theStartingWeapon) {
        super(theHealth, theName, theStartingWeapon);
    }
}
