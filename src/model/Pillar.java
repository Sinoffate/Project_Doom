package model;

/**
 * Pillar item class.
 * @author james deal, jered wiegel
 * @version 1.0
 */
public class Pillar extends Item {
    /**
     * Pillar creation.
     * @param theName name of pillar.
     */
    public Pillar(final String theName) {
        super(theName);
    }

    /**
     * Returns name of item.
     * @return name.
     */
    @Override
    public String toString() {
        return this.getName();
    }
}
