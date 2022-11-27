/**
 * This class represents a potion and its functionality that gives the player vision.
 * @author Jered Wiegel
 * @version 1.0
 */
public class VisionPotion extends Item {

    /** Vision Potion's vision range. */
    private static final int RADIUS = 1;

    /**
     * VisionPotion constructor.
     */
    public VisionPotion() {
        super("Vision Potion");
    }

    public int getRadius() {
        return RADIUS;
    }

    @Override
    public String toString() {
        return getName();
    }
}
