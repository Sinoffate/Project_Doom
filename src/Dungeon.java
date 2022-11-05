

public class Dungeon {
    private int myMapSize;
    private Point myHeroPosition;
    private Point myEnterPos;
    private Point myExitPos;


    public Dungeon(final int theMapSize) {
        this.myMapSize = theMapSize;
        this.myHeroPosition = new Point(0, 0);
        this.myEnterPos = new Point(0, 0);
        this.myExitPos = new Point(theMapSize - 1, theMapSize - 1);
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

    public Point getPlayerPos() {
        return myHeroPosition;
    }

    public Point getEnterFlag() {
        return myEnterPos;
    }

    public Point getExitFlag() {
        return myExitPos;
    }

    public Monster getMonster() {
        return myMonster;
    }

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