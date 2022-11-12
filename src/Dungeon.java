import java.awt.*;

public class Dungeon {
    private int myMapSize;
    private Point myHeroPosition;
    private Point myEnterPos;
    private Point myExitPos;

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


    public void setPlayerPos(final Point thePos) {
        myHeroPosition = thePos;
    }

    public String toString() {
        return "Dungeon";
    }
}