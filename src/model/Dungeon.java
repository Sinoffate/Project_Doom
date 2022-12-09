package model;

import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Dungeon {
    public static final String HERO_POS = "HeroPos";
    public static final String TEXT_UPDATE = "TextUpdate";
    public static final String ROOM_VIS = "RoomVisibility";
    public static final String ROOM_CONTENT = "RoomContent";
    private int myMapSize;

    private Room[][] myRooms;
    private Point myHeroPosition;
    private Point myEnterPos;
    private Point myExitPos;

    private final PropertyChangeSupport myPcs;

    /**
     * Creates a new model.Dungeon object.
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
        addItems();
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
     * @return The model.Monster in the room
     */
    public Monster getMonster() {
        return myRooms[(int) myHeroPosition.getX()][(int) myHeroPosition.getY()].getMonster();
    }

    /**
     * @return A list of items
     */

    public Inventory getRoomInventory() {
        return myRooms[(int) myHeroPosition.getX()][(int) myHeroPosition.getY()].getInventory();
    }

    private void setItems(final Inventory theItems) {
        myRooms[(int) myHeroPosition.getX()][(int) myHeroPosition.getY()].setInventory(theItems);
    }

    public boolean hasMonster() {
        return myRooms[(int) myHeroPosition.getX()][(int) myHeroPosition.getY()].getMonster() != null;
    }

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

    public void useVisionPotion() {
        VisionPotion vp = new VisionPotion();

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
        //myPcs.firePropertyChange(TEXT_UPDATE, null, "Hero Position: " + " x = " +  myHeroPosition.getX() + ", y = " + myHeroPosition.getY());
        //System.out.println("Hero Position: " + " x = " +  myHeroPosition.getX() + ", y = " + myHeroPosition.getY());
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