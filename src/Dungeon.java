import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Dungeon {
    private int myMapSize;

    private Room[][] myRooms;
    private Point myHeroPosition;
    private Point myEnterPos;
    private Point myExitPos;

    static final String HERO_POS = "HeroPos";
    static final String TEXT_UPDATE = "TextUpdate";
    static final String ROOM_VIS = "RoomVisibility";

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
        myRooms = generateDungeon();
        addMonsters();
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

    private void addMonsters() {
        for (int i = 0; i < myMapSize; i++) {
            for (int j = 0; j < myMapSize; j++) {
                if (DiceRoll.nextFloat(1) < 0.5 && myRooms[i][j].getMonster() == null &&
                    !(i == myEnterPos.x && j == myEnterPos.y)) {
                    myRooms[i][j].setMonster(new Monster(100, "Baron of Hell",
                            new Weapon(10, 0.8, 0.5, 10, "Whip")));
                }
            }
        }
    }

    public Room getRoom(final int theRow, final int theCol) {
        if (theRow < 0 || theRow >= myMapSize || theCol < 0 || theCol >= myMapSize) {
            throw new IllegalArgumentException("Out of bounds");
        }
        return myRooms[theRow][theCol];
    }

    /**
     * @return the myMapSize
     */
    public int getMapSize() {
        return this.myMapSize;
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
     * @return The Monster in the room
     */
    public Monster getMonster() {
        return myRooms[(int) myHeroPosition.getX()][(int) myHeroPosition.getY()].getMonster();
    }

    /**
     * @return A list of items
     */

    public Inventory getItems() {
        return myRooms[(int) myHeroPosition.getX()][(int) myHeroPosition.getY()].getInventory();
    }

    private void setItems(final Inventory theItems) {
        myRooms[(int) myHeroPosition.getX()][(int) myHeroPosition.getY()].setInventory(theItems);
    }

    public boolean hasMonster() {
        return myRooms[(int) myHeroPosition.getX()][(int) myHeroPosition.getY()].getMonster() != null;
    }

    public boolean hasItems() {
        return myRooms[(int) myHeroPosition.getX()][(int) myHeroPosition.getY()].getInventory().size() > 0;
    }

    /**
     * Set specified room as visible.
     * @param theRoomLocation location to set visible.
     */
    public void setRoomVisible(final Point theRoomLocation) {
        myRooms[(int)theRoomLocation.getX()][(int)theRoomLocation.getY()].setDiscovered(true);
        myPcs.firePropertyChange(ROOM_VIS,null,theRoomLocation);
    }

    public void useVisionPotion() {
        VisionPotion vp = new VisionPotion();

        for (int row = (int) (myHeroPosition.getX() - vp.getRadius()); row <= myHeroPosition.getX() + vp.getRadius(); row++) {
            for (int col = (int) (myHeroPosition.getY() - vp.getRadius()); col <= myHeroPosition.getY() + vp.getRadius(); col++) {
                if (row >= 0 && row < myMapSize && col >= 0 && col < myMapSize) {
                    setRoomVisible(new Point(row,col));
                }
            }
        }

    }

    /**
     * Move player in a given direction.
     * @param theDirection direction to move.
     */
    public void movePlayer(final Point theDirection) {
        setPlayerPos(new Point((int) getPlayerPos().getX() + (int) theDirection.getX(),
                               (int) getPlayerPos().getY() + (int) theDirection.getY()));
        setRoomVisible(getPlayerPos());
    }

    /**
     * Sets the Player's position to a new value within the maps bounds.
     * @param thePos the myHeroPosition to set
     */
    private void setPlayerPos(final Point thePos) {
        if (thePos.getX() < 0 || thePos.getX() >= myMapSize
                || thePos.getY() < 0 || thePos.getY() >= myMapSize) {
            return;
        }
        final Point oldPos = myHeroPosition;
        myHeroPosition = thePos;
        myPcs.firePropertyChange(HERO_POS, oldPos, myHeroPosition);
        myPcs.firePropertyChange(TEXT_UPDATE, null, "Hero Position: " + " x = " +  myHeroPosition.getX() + ", y = " + myHeroPosition.getY());
        System.out.println("Hero Position: " + " x = " +  myHeroPosition.getX() + ", y = " + myHeroPosition.getY());
    }

    /**
     * @param theListener the listener to add
     * @param thePropertyName the property to listen to
     */
    public void addPropertyChangeListener(final String thePropertyName,
                                          final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(thePropertyName, theListener);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Map Size: ").append(myMapSize).append(" Hero Position: ").append(myHeroPosition).append(" Enter Position: ").append(myEnterPos).append(" Exit Position: ").append(myExitPos);
        return sb.toString();
    }
}