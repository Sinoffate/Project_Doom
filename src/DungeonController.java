
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.Serial;


/**
 * Controller for the Project_Doom game.
 * @author Jered Wiegel
 * @version 1.0
 */
public class DungeonController extends JFrame implements KeyListener {

    @Serial
    private static final long serialVersionUID = 1L;
    /** A ToolKit. */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();
    /** Screen Dimension. */
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();

    /** List of normal menu items. */
    private static final String[] MAIN_MENU = {"Inventory", "Save", "Load", "Quit"};
    /** List of map menu items. */
    private static final String[] MAP_MENU = {"WASD to move!", "Escape key for menu!", "Watch Kung Fury!"};

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
    public DungeonController() throws IOException {
        myDungeon = new Dungeon(5);
        myDoomGuy = new DoomGuy(100, "DoomGuy",
                new Weapon(10, 0.8, 0.5, 10, "Pistol"));
        myMonster = new Monster(100, "Baron of Hell",
                new Weapon(10, 0.8, 0.5, 10, "Whip"));
        myView = new DungeonView(myDungeon.getMapSize(), myDungeon.getPlayerPos());
        //dungeon pcs
        myDungeon.addPropertyChangeListener(Dungeon.HERO_POS, myView);
        myDungeon.addPropertyChangeListener(Dungeon.TEXT_UPDATE, myView);
        //control pcs
        this.myPcs = new PropertyChangeSupport(this);
        this.addPropertyChangeListener(MENU, myView);
        this.addPropertyChangeListener(MENU_POS, myView);
        this.addPropertyChangeListener(Dungeon.TEXT_UPDATE, myView);

        enactMapState();
    }

    @Override
    public void keyTyped(final KeyEvent theEvt) {
        return;
    }

    /**
     * Moves the hero in the direction of the key pressed (WASD).
     * @param theEvt the key pressed.
     */
    @Override
    public void keyPressed(final KeyEvent theEvt) {
        switch (theEvt.getKeyCode()) {
            case KeyEvent.VK_W ->
                    myDungeon.setPlayerPos(new Point(myDungeon.getPlayerPos().x,
                            myDungeon.getPlayerPos().y - 1));
            case KeyEvent.VK_A ->
                    myDungeon.setPlayerPos(new Point(myDungeon.getPlayerPos().x - 1,
                            myDungeon.getPlayerPos().y));
            case KeyEvent.VK_S ->
                    myDungeon.setPlayerPos(new Point(myDungeon.getPlayerPos().x,
                            myDungeon.getPlayerPos().y + 1));
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

    private void runGame() {
        /* Use an appropriate Look and Feel */
        try {
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (final UnsupportedLookAndFeelException | IllegalAccessException |
                       InstantiationException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(this::createAndShowGUI);
    }

    /**
     * Create the GUI and show it. For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    public void createAndShowGUI ( ) {
        final JFrame frame = new JFrame("Project Doom");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(myView);
        frame.pack();

        frame.setSize(SCREEN_SIZE.width / 2, SCREEN_SIZE.height / 2);
        frame.setLocation(SCREEN_SIZE.width / 2 - frame.getWidth() / 2,
                SCREEN_SIZE.height / 2 - frame.getHeight() / 2);
        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.setVisible(true);
    }

    public static void main(final String[] theArgs) throws IOException {
        DungeonController controller = new DungeonController();

        controller.runGame();
    }

    /**
     * @param theListener the listener to add
     * @param thePropertyName the property to listen to
     */
    public void addPropertyChangeListener(final String thePropertyName,
                                          final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(thePropertyName, theListener);
    }

}
