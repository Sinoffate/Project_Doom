
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * Controller for the Project_Doom game.
 * @author Jered Wiegel
 * @version 1.0
 */
public class DungeonController implements ActionListener, KeyListener {
    /** Dungeon Object. */
    private Dungeon myDungeon;
    /** Dungeon View Object. */
    private DungeonView myView;
    /** Hero Object. */
    private DoomGuy myDoomGuy;
    /** Monster Object. */
    private Monster myMonster;

    /**
     * Creates a new DungeonController object.
     *
     */
    public DungeonController() {
        myDungeon = new Dungeon(5);
        myDoomGuy = new DoomGuy(100, "DoomGuy",
                new Weapon(10, 0.8, 0.5, 10, "Pistol"));
        myMonster = new Monster(100, "Baron of Hell",
                new Weapon(10, 0.8, 0.5, 10, "Whip"));
        myView = new DungeonView(myDungeon.getMapSize(), myDungeon.getPlayerPos());
        myDungeon.addPropertyChangeListener(Dungeon.HERO_POS, myView);

    }

    @Override
    public void actionPerformed(final ActionEvent theEvt) {


    }

    @Override
    public void keyTyped(final KeyEvent theEvt) {
        return;
    }

    @Override
    public void keyPressed(final KeyEvent theEvt) {
        switch (theEvt.getKeyCode()) {
            case KeyEvent.VK_W ->
                    myDungeon.setPlayerPos(new Point(myDungeon.getPlayerPos().x,
                            myDungeon.getPlayerPos().y + 1));
            case KeyEvent.VK_A ->
                    myDungeon.setPlayerPos(new Point(myDungeon.getPlayerPos().x - 1,
                            myDungeon.getPlayerPos().y));
            case KeyEvent.VK_S ->
                    myDungeon.setPlayerPos(new Point(myDungeon.getPlayerPos().x,
                            myDungeon.getPlayerPos().y - 1));
            case KeyEvent.VK_D ->
                    myDungeon.setPlayerPos(new Point(myDungeon.getPlayerPos().x + 1,
                            myDungeon.getPlayerPos().y));
            default -> {
            }
        }
    }

    @Override
    public void keyReleased(final KeyEvent theEvt) {
        return;
    }

    public static void main(final String[] theArgs) {
        DungeonController controller = new DungeonController();
    }
}





