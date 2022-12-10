package model;

abstract public class Monster extends DungeonCharacter {

    /**
     * Default constructor for DC abstract objects.
     *
     * @param theHealth         starting health of monster.
     * @param theName           name of monster.
     * @param theStartingWeapon base weapon of monster, not changeable
     */
    public Monster(final int theHealth, final String theName, final Weapon theStartingWeapon) {
        super(theHealth, theName, theStartingWeapon);
    }
}
