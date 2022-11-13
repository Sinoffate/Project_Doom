import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


/**
 *
 */
public class DungeonController implements ActionListener, PropertyChangeListener, KeyListener {
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
        myView = new DungeonView(myDungeon.getMapSize(), myDungeon.getPlayerPos());
    }

    @Override
    public void actionPerformed(final ActionEvent e) {


    }

    @Override
    public void keyTyped(final KeyEvent e) {
        return;
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W ->
                    myDungeon.setPlayerPos(new Point(myDungeon.getPlayerPos().x, myDungeon.getPlayerPos().y + 1));
            case KeyEvent.VK_A ->
                    myDungeon.setPlayerPos(new Point(myDungeon.getPlayerPos().x - 1, myDungeon.getPlayerPos().y));
            case KeyEvent.VK_S ->
                    myDungeon.setPlayerPos(new Point(myDungeon.getPlayerPos().x, myDungeon.getPlayerPos().y - 1));
            case KeyEvent.VK_D ->
                    myDungeon.setPlayerPos(new Point(myDungeon.getPlayerPos().x + 1, myDungeon.getPlayerPos().y));
            default -> {
            }
        }
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        return;
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (theEvent.getPropertyName().equals("move")) {
            myDungeon.setPlayerPos((Point) theEvent.getNewValue());
            myView.update( );
        }
        if (theEvent.getPropertyName().equals("menu")) {
            myView.update();
        }
    }


    public void tossToView() {
        myDungeon.addPropertyChangeListener(Dungeon.HERO_POS, this);
    }


    public static void main(String[] args) {
        DungeonController controller = new DungeonController();
        controller.myView.display();
        while (true) {

        }
    }



}





