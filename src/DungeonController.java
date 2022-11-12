import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


/**
 *
 */
public class DungeonController implements PropertyChangeListener {
    private Dungeon myDungeon;
    private DungeonView myView;
    private DoomGuy myDoomGuy;
    private Monster myMonster;

    /**
     * Creates a new DungeonController object.
     *
     */
    public DungeonController () {
        myDungeon = new Dungeon(5);
        myDoomGuy = new DoomGuy(100, 0.5, 0.5, 10, "DoomGuy", new Weapon(10, 0.8, 0.5, 10, "Pistol"));
        myMonster = new Monster(100, 0.8, 0.5, 10, "Baron of Hell", new Weapon(10, 0.8, 0.5, 10, "Whip"));
        myView = new DungeonView(myDungeon, myDoomGuy, myMonster);
    }

    @Override
    public void propertyChange(PropertyChangeEvent theEvent) {
        if (theEvent.getPropertyName().equals("move")) {
            myDungeon.setPlayerPos((Point) theEvent.getNewValue());
            myView.update( );
        }
        if (theEvent.getPropertyName().equals("menu")) {
            myView.update();
        }

    }


    public static void main(String[] args) {
        DungeonController controller = new DungeonController();
        controller.myView.display();
        while (true) {

        }
    }

}





