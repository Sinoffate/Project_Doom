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
     * model.VisionPotion constructor.
     */
    public VisionPotion() {
        super("Vision Potion");
        final Database db = Database.getInstance();
        final String potVal = db.selectOne("Items", "VisionPotion");
        myRadius = Integer.parseInt(potVal);
    }

    public int getRadius() {
        return myRadius;
    }

    @Override
    public String toString() {
        return getName();
    }
}
