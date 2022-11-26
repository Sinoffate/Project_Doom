
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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

    /** Current Game State. */
    GameState myCurrentState;
    /** Current Menu Position. */
    private int myMenuPosition;
    /** Current Menu. */
    private String[] myCurrentMenu;

    private final PropertyChangeSupport myPcs;
    static final String MENU_POS = "MenuPos";
    static final String MENU = "Menu";

    /**
     * Creates a new DungeonController object.
     *
     */
    public DungeonController() {
        myDungeon = new Dungeon(5);
        myDoomGuy = new DoomGuy(100, "DoomGuy",
                                new Weapon(10, 0.8, 0.5, 10, "Pistol"));
        //make view
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
     * Defines the action that is triggered depending on current state of the game (WASD).
     * @param theEvt the key pressed.
     */
    @Override
    public void keyPressed(final KeyEvent theEvt) {
        switch (theEvt.getKeyCode()) {
            case KeyEvent.VK_W -> {
                switch (myCurrentState) {
                    case MAP_STATE -> myDungeon.movePlayer(new Point(0,-1));
                    case COMBAT_STATE -> myDoomGuy.attack(myDungeon.getRoom((int) myDungeon.getPlayerPos().getX(),
                                                                            (int) myDungeon.getPlayerPos().getY()).getMonster());
                    case MENU_STATE -> menuMovement(-1);
                }
            }
            case KeyEvent.VK_A -> {
                switch (myCurrentState) {
                    case MAP_STATE -> myDungeon.movePlayer(new Point(-1, 0));
                    case COMBAT_STATE -> myDoomGuy.attack(myDungeon.getRoom((int) myDungeon.getPlayerPos().getX(),
                                                                            (int) myDungeon.getPlayerPos().getY()).getMonster());
                    case MENU_STATE -> System.out.println("Menu");
                }
            }
            case KeyEvent.VK_S -> {
                switch (myCurrentState) {
                    case MAP_STATE -> myDungeon.movePlayer(new Point(0, 1));
                    case COMBAT_STATE -> myDoomGuy.attack(myDungeon.getRoom((int) myDungeon.getPlayerPos().getX(),
                                                                            (int) myDungeon.getPlayerPos().getY()).getMonster());
                    case MENU_STATE -> menuMovement(1);
                }
            }
            case KeyEvent.VK_D -> {
                switch (myCurrentState) {
                    case MAP_STATE -> myDungeon.movePlayer(new Point(1, 0));
                    case COMBAT_STATE -> myDoomGuy.attack(myDungeon.getRoom((int) myDungeon.getPlayerPos().getX(),
                                                                            (int) myDungeon.getPlayerPos().getY()).getMonster());
                    case MENU_STATE -> System.out.println("Menu");
                }
            }
            case KeyEvent.VK_ESCAPE -> {
                switch (myCurrentState) {
                    case MAP_STATE ->  enactMenuState();
                    case MENU_STATE -> enactMapState();

                }
            }
            case KeyEvent.VK_E -> {
                switch (myCurrentState) {
                    //case MAP_STATE -> myDungeon.lootRoom();
                    case MENU_STATE -> selectMenuOption(); //select menu option
                    case COMBAT_STATE -> myDoomGuy.attack(myMonster);
                }
            }
            case KeyEvent.VK_Q -> {
                switch (myCurrentState) {
                    //case MAP_STATE -> no current action planned
                    case MENU_STATE -> backMenuOption(); //back a menu option
                    case COMBAT_STATE -> myDoomGuy.attack(myMonster);
                }
            }
            default -> {
            }
        }
    }

    @Override
    public void keyReleased(final KeyEvent theEvt) {
        return;
    }

    private void enactMapState() {
        myCurrentState = GameState.MAP_STATE;

        String[] old = myCurrentMenu;
        myCurrentMenu = MAP_MENU;
        myPcs.firePropertyChange(MENU, old, myCurrentMenu);
        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null,  "Entered Map State");
    }

    /**
     * Load into menu state, set default menu position.
     */
    private void enactMenuState() {
        myCurrentState = GameState.MENU_STATE;
        myMenuPosition = 0;

        String[] old = myCurrentMenu;
        myCurrentMenu = MAIN_MENU;

        myPcs.firePropertyChange(MENU, old, MAIN_MENU);
        myPcs.firePropertyChange(MENU_POS, old, myMenuPosition);
        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null,  "Entered Menu State");
    }

    /**
     * Move to new menu option.
     */
    private void menuMovement(final int theMovement) {
        if ((myMenuPosition == 0 && theMovement == -1) || (myMenuPosition == MAIN_MENU.length-1 && theMovement == 1)) {
            return;
        }
        final int oldPos = myMenuPosition;
        myMenuPosition += theMovement;

        System.out.println("Attempting menu movement: " + theMovement);
        myPcs.firePropertyChange(MENU_POS, oldPos, myMenuPosition);
    }

    /**
     * Select menu option.
     */
    private void selectMenuOption() {
        System.out.println("Got here: " + MAIN_MENU[myMenuPosition]);
    }

    /**
     * Back menu option.
     */
    private void backMenuOption() {
        System.out.println("Want to leave here: " + MAIN_MENU[myMenuPosition]);
    }


//    public void combatHandler() {
//
//    }

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
    private void createAndShowGUI() {
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
