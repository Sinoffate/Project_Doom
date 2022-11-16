import java.awt.Point;

/**
 * This class represents a potion and its functionality that gives the player vision.
 * @author Jered Wiegel
 * @version 1.0
 */
public class VisionPotion extends Item {

    /** Vision Potion's vision range. */
    private static final int RADIUS = 1;

    /**
     * Default constructor.
     *
     * @param theName name of item.
     */
    public VisionPotion(final String theName) {
        super(theName);
    }

    public void usePotion(final Point thePlayer) {
        for (int row = (int) (thePlayer.getX() - RADIUS); row <= thePlayer.getX() + RADIUS; row++) {
            for (int col = (int) (thePlayer.getY() - RADIUS); col <= thePlayer.getY() + RADIUS; col++) {
                if (row >= 0 && row < Dungeon.getMapSize() && col >= 0 && col < Dungeon.getMapSize()) {
                    Dungeon.getRoom(row, col).setDiscovered(true);
                }
            }
        }
    }

    @Override
    public String toString() {
        return getName();
    }
}
