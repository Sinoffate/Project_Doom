package model;

import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Dungeon class provides the main game backend logic.
 * @author Jered Wiegel
 * @version 1.0
 */
public class Dungeon implements Serializable {
    /** Constant for the Hero position property changes. */
    public static final String HERO_POS = "HeroPos";
    /** Constant for the updates to text log property changes. */
    public static final String TEXT_UPDATE = "TextUpdate";
    /** Constant for the updates to the map property changes. */
    public static final String ROOM_VIS = "RoomVisibility";
    /** Constant for the updates to the map property changes. */
    public static final String ROOM_CONTENT = "RoomContent";
    /** Variable holds square root of the size of the map. */
    private final int myMapSize;
    /** Variable holds all the rooms in the map. */
    private final Room[][] myRooms;
    /** Variable holds the current room the hero is in. */
    private Point myHeroPosition;
    /** Variable holds the location of the entrance to the dungeon. */
    private final Point myEnterPos;
    /** Variable holds the location of the exit of the dungeon. */
    private final Point myExitPos;
    /** Variable facilitates the property changes to fire. */
    private final PropertyChangeSupport myPcs;

    /**
     * Creates a new Dungeon object.
     * @param theMapSize size of map x and y.
     */
    public Dungeon(final int theMapSize) {
        if (theMapSize < 1) {
            throw new IllegalArgumentException("Map size must be greater than 0.");
        }
        this.myMapSize = theMapSize;
        this.myHeroPosition = new Point(0, 0);
        this.myEnterPos = new Point(0, 0);
        this.myExitPos = new Point(theMapSize - 1, theMapSize - 1);
        this.myPcs = new PropertyChangeSupport(this);
        myRooms = generateDungeon();
        addMonsters();
        addItems();
    }

    /**
     * Backend logic that builds each of the rooms in the dungeon.
     * @return the 2D array of rooms.
     */
    private Room[][] generateDungeon() {
        final Room[][] dungeon = new Room[myMapSize][myMapSize];
        for (int i = 0; i < myMapSize; i++) {
            for (int j = 0; j < myMapSize; j++) {
                dungeon[i][j] = new Room();
            }
        }
        return dungeon;
    }

    /**
     * Backend logic that adds monsters to the dungeon.
     */
    private void addMonsters() {
        for (int i = 0; i < myMapSize; i++) {
            for (int j = 0; j < myMapSize; j++) {
                if (DiceRoll.nextFloat(1) < 0.5 && myRooms[i][j].getMonster() == null
                        && !(i == myEnterPos.x && j == myEnterPos.y)) {
                    if (DiceRoll.nextInt(2) != 0) {
                        myRooms[i][j].setMonster(new BaronOfHell());
                    } else {
                        myRooms[i][j].setMonster(new Imp());
                    }
                }
            }
        }
        myRooms[myExitPos.x][myExitPos.y].setMonster(new AnimeWaifu());
    }

    /**
     * Backend logic that adds items to the dungeon.
     */
    private void addItems() {
        final Queue<Item> itemList = chooseItemsHelper();
        final HashSet<Point> invalidLoc = new HashSet<>();
        Point thisRoll;

        for (int i = 0; i < 7; i++) {
            thisRoll = new Point(DiceRoll.nextInt(myMapSize - 1), DiceRoll.nextInt(myMapSize - 1));
            while (invalidLoc.contains(thisRoll)) {
                thisRoll = new Point(DiceRoll.nextInt(myMapSize - 1), DiceRoll.nextInt(myMapSize - 1));
            }
            myRooms[(int) thisRoll.getX()][(int) thisRoll.getY()].getInventory().addItem(itemList.poll());
            invalidLoc.add(thisRoll);
        }

        while (!itemList.isEmpty()) {
            thisRoll = new Point(DiceRoll.nextInt(myMapSize - 1), DiceRoll.nextInt(myMapSize - 1));
            while (invalidLoc.contains(thisRoll)) {
                thisRoll = new Point(DiceRoll.nextInt(myMapSize - 1), DiceRoll.nextInt(myMapSize - 1));
            }
            myRooms[(int) thisRoll.getX()][(int) thisRoll.getY()].getInventory().addItem(itemList.poll());
        }
    }

    /**
     * Helper method for addItems.
     * @return a queue of items.
     */
    private Queue<Item> chooseItemsHelper() {
        final Queue<Item> itemsToAdd = new LinkedList<>();
        itemsToAdd.add(new Pillar("Polymorphic Bob"));
        itemsToAdd.add(new Pillar("Encapsulated George"));
        itemsToAdd.add(new Pillar("Sir Von Whiskers the III Twice Inherited"));
        itemsToAdd.add(new Pillar("Abstracted Not Null But Close Enough"));
        itemsToAdd.add(new Weapon("BFG"));
        itemsToAdd.add(new Weapon("Shotgun"));
        itemsToAdd.add(new Weapon("Rawket Lawnchair"));
        final int drugsToAdd = (int) ((myMapSize * myMapSize - itemsToAdd.size()) * 0.3);
        for (int i = 0; i < drugsToAdd; i++) {
            itemsToAdd.add(DiceRoll.nextInt(3) > 0 ? new HealthPotion() : new VisionPotion());
        }
        return itemsToAdd;
    }

    /**
     * Class that grabs the room passed in.
     * @param theRow row of the room
     * @param theCol column of the room
     * @return the room at the row and column
     */
    public Room getRoom(final int theRow, final int theCol) {
        if (theRow < 0 || theRow >= myMapSize || theCol < 0 || theCol >= myMapSize) {
            throw new IllegalArgumentException("Out of bounds");
        }
        return myRooms[theRow][theCol];
    }

    /**
     * Gets the square root of the map size.
     * @return the myMapSize
     */
    public int getMapSize() {
        return this.myMapSize;
    }

    /**
     * Gets the hero's current position.
     * @return The Hero's position as a Point.
     */
    public Point getPlayerPos() {
        return myHeroPosition;
    }

    /**
     * Gets the entrance position.
     * @return The Entrance Position
     */
    public Point getEnterFlag() {
        return myEnterPos;
    }

    /**
     * Gets the exit position.
     * @return The Exit Position
     */
    public Point getExitFlag() {
        return myExitPos;
    }

    /**
     * Gets the monster at the Hero's current position.
     * @return The Monster in the room
     */
    public Monster getMonster() {
        return myRooms[(int) myHeroPosition.getX()][(int) myHeroPosition.getY()].getMonster();
    }

    /**
     * Gets the inventory of the room at the Hero's current position.
     * @return A list of items
     */

    public Inventory getRoomInventory() {
        return myRooms[(int) myHeroPosition.getX()][(int) myHeroPosition.getY()].getInventory();
    }

    /**
     * Sets the inventory of the room at the Hero's current position.
     * @param theItems The new inventory
     */
    private void setItems(final Inventory theItems) {
        myRooms[(int) myHeroPosition.getX()][(int) myHeroPosition.getY()].setInventory(theItems);
    }

    /**
     * Checks if current hero position has a monster.
     * @return true if there is a monster, false otherwise
     */
    public boolean hasMonster() {
        return myRooms[(int) myHeroPosition.getX()][(int) myHeroPosition.getY()].getMonster() != null;
    }

    /**
     * Checks if current hero position has items.
     * @return true if there is an item, false otherwise
     */
    public boolean hasItems() {
        return myRooms[(int) myHeroPosition.getX()][(int) myHeroPosition.getY()].getInventory().inventorySize() > 0;
    }

    /**
     * Set specified room as visible.
     * @param theRoomLocation location to set visible.
     */
    public void setRoomVisible(final Point theRoomLocation) {
        myRooms[(int) theRoomLocation.getX()][(int) theRoomLocation.getY()].setDiscovered(true);
        String roomContent = "";
        if (myRooms[(int) theRoomLocation.getX()][(int) theRoomLocation.getY()].getMonster() != null) {
            roomContent += "M";
        }
        if (myRooms[(int) theRoomLocation.getX()][(int) theRoomLocation.getY()].getInventory().inventorySize() != 0) {
            roomContent += "I";
        }
        //code smell, unsure how to fix. need to give view room location + content
        myPcs.firePropertyChange(ROOM_CONTENT, theRoomLocation, roomContent);
        myPcs.firePropertyChange(ROOM_VIS, null, theRoomLocation);
    }

    /**
     * Backend logic for usage of a Vision Potion.
     */
    public void useVisionPotion() {
        final VisionPotion vp = new VisionPotion();

        for (int row = (int) (myHeroPosition.getX() - vp.getRadius()); row <= myHeroPosition.getX() + vp.getRadius(); row++) {
            for (int col = (int) (myHeroPosition.getY() - vp.getRadius()); col <= myHeroPosition.getY() + vp.getRadius(); col++) {
                if (row >= 0 && row < myMapSize && col >= 0 && col < myMapSize) {
                    setRoomVisible(new Point(row, col));
                }
            }
        }

    }

    /**
     * Move player in a given direction.
     * @param theDirection direction to move.
     */
    public void movePlayer(final Point theDirection) {
/*      Fix to known bug of holding down movement key allowing combat to be skipped.
        Commented out because we like the bug. It's a feature now.
        if (hasMonster()) {
            return;
        }*/
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
    }

    /**
     * @param theListener the listener to add
     * @param thePropertyName the property to listen to
     */
    public void addPropertyChangeListener(final String thePropertyName,
                                          final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(thePropertyName, theListener);
    }

    /**
     * Provides a string representation of important dungeon information.
     * @return a string representation of the dungeon
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Map Size: ").append(myMapSize).append(" Hero Position: ").
                append(myHeroPosition).append(" Enter Position: ").
                append(myEnterPos).append(" Exit Position: ").append(myExitPos);
        return sb.toString();
    }
}