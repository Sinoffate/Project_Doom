import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Dungeon {
    private int myMapSize;
    private Point myHeroPosition;
    private Point myEnterPos;
    private Point myExitPos;

    static final String HERO_POS = "HeroPos";

    private final PropertyChangeSupport myPcs;

    /**
     * Creates a new Dungeon object.
     *
     * @param theMapSize size of map x and y.
     */
    public Dungeon(final int theMapSize) {
        this.myMapSize = theMapSize;
        this.myHeroPosition = new Point(0, 0);
        this.myEnterPos = new Point(0, 0);
        this.myExitPos = new Point(theMapSize - 1, theMapSize - 1);
        this.myPcs = new PropertyChangeSupport(this);
        generateDungeon();
    }

    private Room[][] generateDungeon() {
        Room[][] dungeon = new Room[myMapSize][myMapSize];
        for (int i = 0; i < myMapSize; i++) {
            for (int j = 0; j < myMapSize; j++) {
                dungeon[i][j] = new Room();
            }
        }
        return dungeon;
    }

    /**
     * @return the myMapSize
     */
    public int getMapSize() {
        return myMapSize;
    }

    /**
     * @return The Hero's position as a Point.
     */
    public Point getPlayerPos() {
        return myHeroPosition;
    }

    /**
     *
     * @return The Entrance Position
     */

    public Point getEnterFlag() {
        return myEnterPos;
    }

    /**
     *
     * @return The Exit Position
     */
    public Point getExitFlag() {
        return myExitPos;
    }

    /**
     * @return The Monster
     */
    public Monster getMonster() {
        return myMonster;
    }

    /**
     * @return A list of items
     */

    public Inventory getItems() {
        return myItems;
    }

    public boolean hasMonster() {
        return false;
    }

    public boolean hasItems() {
        return false;
    }


    /**
     * Sets the Player's position to a new value within the maps bounds.
     * @param thePos the myHeroPosition to set
     */
    public void setPlayerPos(final Point thePos) {
        if (thePos.getX() < 0 || thePos.getX() >= myMapSize || thePos.getY() < 0 || thePos.getY() >= myMapSize) {
            return ;
        }
        final Point oldPos = myHeroPosition;
        myHeroPosition = thePos;
        myPcs.firePropertyChange(HERO_POS, oldPos, myHeroPosition);
    }

    /**
     * @param theListener the listener to add
     * @param thePropertyName the property to listen to
     */
    public void addPropertyChangeListener(final String thePropertyName, final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(thePropertyName, theListener);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Map Size: " + myMapSize)
          .append(" Hero Position: " + myHeroPosition)
          .append(" Enter Position: " + myEnterPos)
          .append(" Exit Position: " + myExitPos);
        return sb.toString();
    }
}