package model;

/**
 * This class represents a potion and its functionality that gives the player vision.
 * @author Jered Wiegel
 * @version 1.0
 */
public class VisionPotion extends Item {

    /** Vision Potion's vision range. */
    private final int myRadius;

    /**
     * VisionPotion constructor.
     */
    public VisionPotion() {
        super("Vision Potion");
        final Database db = Database.getInstance();
        final String potVal = db.selectOne("Items", "VisionPotion");
        myRadius = Integer.parseInt(potVal);
    }

    /**
     * Returns the vision range of the potion.
     * @return vision range.
     */
    public int getRadius() {
        return myRadius;
    }

    /**
     * Returns the name of the item.
     * @return name of item.
     */
    @Override
    public String toString() {
        return getName();
    }
}
