
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.TextEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.Serial;
import java.util.LinkedList;
import java.util.Queue;


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
    private static final String[] MAP_MENU = {"WASD to move!", "Hold Shift for Guns", "Hold Alt for Potions",
            "Escape key for menu!", "Watch Kung Fury!"};
    /** List of Weapon Radial menu items. */
    private static final String[] WEAPON_MENU = {"&lt;: Pistol", "^: BFG", "v: Rawket Lawnchair", "&gt;: Shotgun"};
    /** List of Potion Radial menu items. */
    private static final String[] POTION_MENU = {"^: Health Potion", "v: Vision Potion"};

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

    /** Property Change Object. */
    private final PropertyChangeSupport myPcs;
    /** PCS Type for Menu position. */
    static final String MENU_POS = "MenuPos";
    /** PCS Type for Menu State. */
    static final String MENU = "Menu";

    /** Tracks which DunCha attacked last. */
    boolean myDGAttacked;

    /**
     * Creates a new DungeonController object.
     *
     */
    public DungeonController() {
        myDungeon = new Dungeon(5);
        myDoomGuy = new DoomGuy(100, "DoomGuy",
                                new Weapon(10, 0.8, 0.5, 10, "Pistol"));
        myDoomGuy.addToInventory(new VisionPotion());
        myDoomGuy.addToInventory(new VisionPotion());
        myDoomGuy.addToInventory(new VisionPotion());
        myDoomGuy.addToInventory(new HealthPotion());
        myDoomGuy.addToInventory(new HealthPotion());
        //make view
        myView = new DungeonView(myDungeon.getMapSize(), myDungeon.getPlayerPos());

        //dungeon pcs
        myDungeon.addPropertyChangeListener(Dungeon.HERO_POS, myView);
        myDungeon.addPropertyChangeListener(Dungeon.TEXT_UPDATE, myView);
        myDungeon.addPropertyChangeListener(Dungeon.ROOM_VIS, myView);
        //control pcs
        this.myPcs = new PropertyChangeSupport(this);
        this.addPropertyChangeListener(MENU, myView);
        this.addPropertyChangeListener(MENU_POS, myView);
        this.addPropertyChangeListener(Dungeon.TEXT_UPDATE, myView);

        //Fenceposting creation
        enactMapState();
        myDungeon.setRoomVisible(myDungeon.getPlayerPos());
    }

    /**
     * Defines the action that is triggered depending on current state of the game (WASD).
     * @param theEvt the key pressed.
     */
    @Override
    public void keyPressed(final KeyEvent theEvt) {
        //Change weapon "&lt;: Pistol", "^: BFG", "v: Rawket Lawnchair", "&gt;: Shotgun"
        if (theEvt.isShiftDown()) {
            if (myCurrentState == GameState.COMBAT_STATE || myCurrentState == GameState.MAP_STATE) {
                myPcs.firePropertyChange(MENU, myCurrentMenu, WEAPON_MENU);
            }
            switch (theEvt.getKeyCode()) {
                case KeyEvent.VK_UP -> {
                    if (myDoomGuy.inventoryContains(new Weapon(1,1,1,1,"BFG"))) {
                        myDoomGuy.equipWeapon(new Weapon(1,1,1,1,"BFG"));
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE,null,"BFG EQUIPPED");
                    } else {
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE,null,"No BFG in Inventory :(");
                    }
                }
                case KeyEvent.VK_DOWN -> {
                    if (myDoomGuy.inventoryContains(new Weapon(1,1,1,1,"Rawket Lawnchair"))) {
                        myDoomGuy.equipWeapon(new Weapon(1,1,1,1,"Rawket Lawnchair"));
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE,null,"Rawket Lawnchair EQUIPPED");
                    } else {
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE,null,"No Rawket Lawnchair in Inventory :(");
                    }
                }
                case KeyEvent.VK_LEFT -> {
                    if (myDoomGuy.inventoryContains(new Weapon(1,1,1,1,"Pistol"))) {
                        myDoomGuy.equipWeapon(new Weapon(1,1,1,1,"Pistol"));
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE,null,"Pistol EQUIPPED");
                    } else {
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE,null,"No Pistol in Inventory :(");
                    }
                }
                case KeyEvent.VK_RIGHT -> {
                    if (myDoomGuy.inventoryContains(new Weapon(1,1,1,1,"Shotgun"))) {
                        myDoomGuy.equipWeapon(new Weapon(1,1,1,1,"Shotgun"));
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE,null,"Shotgun EQUIPPED");
                    } else {
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE,null,"No Shotgun in Inventory :(");
                    }
                }

            }
            return;
        }

        //Use potion
        if (theEvt.isAltDown()) {
            if (myCurrentState == GameState.COMBAT_STATE || myCurrentState == GameState.MAP_STATE) {
                myPcs.firePropertyChange(MENU, myCurrentMenu, POTION_MENU);
            }
            switch (theEvt.getKeyCode()) {
                case KeyEvent.VK_UP -> {
                    if (myDoomGuy.inventoryContains(new HealthPotion())) {
                        myDoomGuy.useItem(new HealthPotion());
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE,null,"DG HP: " + myDoomGuy.getHealth());
                    } else {
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE,null,"Go buy some drugs.");
                    }
                }
                case KeyEvent.VK_DOWN -> {
                    if (myDoomGuy.inventoryContains(new VisionPotion())) {
                        myDungeon.useVisionPotion();
                        myDoomGuy.removeFromInventory(new VisionPotion());
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE,null,"Vision Potion Used!");
                    } else {
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE,null,"No Vision Potion in Inventory!");
                    }
                }
            }
            return;
        }

        switch (theEvt.getKeyCode()) {
            case KeyEvent.VK_W -> {
                switch (myCurrentState) {
                    case MAP_STATE -> myDungeon.movePlayer(new Point(0,-1));
                    //case COMBAT_STATE -> myDoomGuy.attack(myDungeon.getRoom((int) myDungeon.getPlayerPos().getX(),
                                                                            //(int) myDungeon.getPlayerPos().getY()).getMonster());
                    case MENU_STATE -> menuMovement(-1);
                }
            }
            case KeyEvent.VK_A -> {
                switch (myCurrentState) {
                    case MAP_STATE -> myDungeon.movePlayer(new Point(-1, 0));
                    //case COMBAT_STATE -> myDoomGuy.attack(myDungeon.getRoom((int) myDungeon.getPlayerPos().getX(),
                                                                            //(int) myDungeon.getPlayerPos().getY()).getMonster());
                    case MENU_STATE -> System.out.println("Menu");
                }
            }
            case KeyEvent.VK_S -> {
                switch (myCurrentState) {
                    case MAP_STATE -> myDungeon.movePlayer(new Point(0, 1));
                   // case COMBAT_STATE -> myDoomGuy.attack(myDungeon.getRoom((int) myDungeon.getPlayerPos().getX(),
                                                                            //(int) myDungeon.getPlayerPos().getY()).getMonster());
                    case MENU_STATE -> menuMovement(1);
                }
            }
            case KeyEvent.VK_D -> {
                switch (myCurrentState) {
                    case MAP_STATE -> myDungeon.movePlayer(new Point(1, 0));
                   // case COMBAT_STATE -> myDoomGuy.attack(myDungeon.getRoom((int) myDungeon.getPlayerPos().getX(),
                                                                           // (int) myDungeon.getPlayerPos().getY()).getMonster());
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
                    //case COMBAT_STATE -> myDoomGuy.attack(myDungeon.getRoom((int) myDungeon.getPlayerPos().getX(),
                                                                            //(int) myDungeon.getPlayerPos().getY()).getMonster());
                }
            }
            case KeyEvent.VK_Q -> {
                switch (myCurrentState) {
                    //case MAP_STATE -> no current action planned
                    case MENU_STATE -> backMenuOption(); //back a menu option
                    case COMBAT_STATE -> combatAttack();
                }
            }
//            case KeyEvent.VK_SHIFT -> {
//                if (myCurrentState == GameState.COMBAT_STATE || myCurrentState == GameState.MAP_STATE) {
//                    myPcs.firePropertyChange(MENU, myCurrentMenu, WEAPON_MENU);
//                }
//            }
//            case KeyEvent.VK_ALT -> {
//                if (myCurrentState == GameState.COMBAT_STATE || myCurrentState == GameState.MAP_STATE) {
//                    myPcs.firePropertyChange(MENU, myCurrentMenu, POTION_MENU);
//                }
//            }
            default -> {
            }
        }
    }

    @Override
    public void keyReleased(final KeyEvent theEvt) {
        if (myDungeon.getRoom((int) myDungeon.getPlayerPos().getX(),
                (int) myDungeon.getPlayerPos().getY()).getMonster() != null && myCurrentState != GameState.COMBAT_STATE) {
            enactCombatState();
        }

        if (theEvt.getKeyCode() == KeyEvent.VK_SHIFT) {
            if (myCurrentState == GameState.COMBAT_STATE || myCurrentState == GameState.MAP_STATE) {
                myPcs.firePropertyChange(MENU, WEAPON_MENU, myCurrentMenu);
            }
        }
        if (theEvt.getKeyCode() == KeyEvent.VK_ALT) {
            if (myCurrentState == GameState.COMBAT_STATE || myCurrentState == GameState.MAP_STATE) {
                myPcs.firePropertyChange(MENU, POTION_MENU, myCurrentMenu);
            }
        }
    }

    @Override
    public void keyTyped(final KeyEvent theEvt) {
        return;
    }

    private void enactCombatState() {
        myCurrentState = GameState.COMBAT_STATE;
        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "Entered Combat State");
        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "You have encountered a monster!");
        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "Press QEWASD to attack!");

        final Room currentRoom = myDungeon.getRoom((int) myDungeon.getPlayerPos().getX(),
                (int) myDungeon.getPlayerPos().getY());
        double speedCalc = myDoomGuy.getFireRate() - currentRoom.getMonster().getFireRate();
        myDGAttacked = speedCalc >= 0;
        //combatLoop();
    }

    /**
     * Load into map state.
     */
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

    private void combatAttack() {
        combatRound();
        if (myCurrentState.equals(GameState.COMBAT_STATE)) {
            combatRound();
        }
    }

    private void combatRound() {
        final Room currentRoom = myDungeon.getRoom((int) myDungeon.getPlayerPos().getX(),
                (int) myDungeon.getPlayerPos().getY());
        String attackRes;

        if (!myDGAttacked) {
            attackRes = myDoomGuy.attack(currentRoom.getMonster());
        } else {
            attackRes = currentRoom.getMonster().attack(myDoomGuy);
        }
        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE,null,attackRes);
        String health = "DG HP: " + myDoomGuy.getHealth() + " Enemy Health: " + currentRoom.getMonster().getHealth();
        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE,null,health);

        if (!myDoomGuy.isAlive()) {
            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "You Died");
        }
        if (!currentRoom.getMonster().isAlive()) {
            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "You killed the " + currentRoom.getMonster().getName());
            currentRoom.setMonster(null);
            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "Exited Combat State");
            enactMapState();
        }

        myDGAttacked ^= true;
    }

    /**
     * Run combat loop until player or monster dies. Waits for player to press do an action before continuing.
     */
    private void combatLoop() {
        final Room currentRoom = myDungeon.getRoom((int) myDungeon.getPlayerPos().getX(),
                                                   (int) myDungeon.getPlayerPos().getY());
        final Monster currentMonster = currentRoom.getMonster();
        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "Entered Combat State");
        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "You have encountered a monster!");
        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "Press QEWASD to attack!");

        double speedCalc = myDoomGuy.getFireRate() - currentMonster.getFireRate();
        Queue<DungeonCharacter> turnOrder = new LinkedList<>();
        if (speedCalc >= 0) {
            turnOrder.add(myDoomGuy);
            turnOrder.add(currentMonster);
        } else {
            turnOrder.add(currentMonster);
            turnOrder.add(myDoomGuy);
        }

        while (myDoomGuy.isAlive() && currentMonster.isAlive()) {
            DungeonCharacter cur = turnOrder.poll();
            assert cur != null;
            String str = cur.attack(turnOrder.peek());
            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE,null,str);
            String health = "DG HP: " + myDoomGuy.getHealth() + " Enemy Health: " + currentMonster.getHealth();
            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE,null,health);
            turnOrder.add(cur);
        }

        if (!myDoomGuy.isAlive()) {
            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "You Died");
        } else if (!currentMonster.isAlive()) {
            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "You killed the " + currentMonster.getName());
            currentRoom.setMonster(null);
        }
        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "Exited Combat State");
        enactMapState();
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
    public void addPropertyChangeListener(final String thePropertyName, final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(thePropertyName, theListener);
    }

}
