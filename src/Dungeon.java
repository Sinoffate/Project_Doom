

public class Dungeon {
    private Point myHeroPosition;
    private int myMapSize;
    private Point myEnterPos;
    private Point myExitPos;


    public Dungeon(final myMapSize) {
        this.myMapSize = myMapSize;
        this.myHeroPosition = new Point(0, 0);
        this.myEnterPos = new Point(0, 0);
        this.myExitPos = new Point(myMapSize - 1, myMapSize - 1);
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

    public Point getMyHeroPosition() {
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

    public void setPlayerPos(final Point pos) {
        myHeroPosition = pos;
    }

    public String toString() {
        return "Dungeon";
    }
}