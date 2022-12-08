import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.Serial;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.*;

/**
 * Controller for the Project_Doom game.
 * @author Jered Wiegel and James Deal
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
    /** List of Title menu items. */
    private static final String[] TITLE_MENU = {"New Game", "Quit", "Watch Kung Fury!"};

    /** Dungeon Object. */
    private Dungeon myDungeon;
    /** Dungeon View Object. */
    private final DungeonView myView;
    /** Hero Object. */
    private DoomGuy myDoomGuy;

    /** Current Game State. */
    private GameState myCurrentState;
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
    /** PCS Type for New Game Fire. */
    static final String RESET_MAP = "ResetMap";

    /** Tracks which DunCha attacked last. */
    private boolean myDGAttacked;

    /**
     * Creates a new DungeonController object.
     *
     */
    public DungeonController() {
        myDungeon = new Dungeon(5);
        myDoomGuy = new DoomGuy(100, "DoomGuy",
                                new Weapon(10, 0.8, 0.5, 100, "Pistol"));

        //test items
        myDoomGuy.addToInventory(new VisionPotion());
        //myDoomGuy.addToInventory(new VisionPotion());
        //myDoomGuy.addToInventory(new VisionPotion());
        //myDoomGuy.addToInventory(new HealthPotion());
        myDoomGuy.addToInventory(new HealthPotion());
        //myDoomGuy.addToInventory(new Weapon(1000, 100, 1, 1, "BFG"));

        //make view
        myView = new DungeonView(myDungeon.getMapSize(), myDungeon.getPlayerPos());

        //dungeon pcs
        myDungeon.addPropertyChangeListener(Dungeon.HERO_POS, myView);
        myDungeon.addPropertyChangeListener(Dungeon.TEXT_UPDATE, myView);
        myDungeon.addPropertyChangeListener(Dungeon.ROOM_VIS, myView);
        myDungeon.addPropertyChangeListener(Dungeon.ROOM_CONTENT, myView);
        //control pcs
        this.myPcs = new PropertyChangeSupport(this);
        this.addPropertyChangeListener(MENU, myView);
        this.addPropertyChangeListener(MENU_POS, myView);
        this.addPropertyChangeListener(Dungeon.TEXT_UPDATE, myView);
        this.addPropertyChangeListener(Dungeon.ROOM_CONTENT, myView);
        this.addPropertyChangeListener(RESET_MAP, myView);

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
        if (theEvt.isShiftDown() && (myCurrentState == GameState.COMBAT_STATE || myCurrentState == GameState.MAP_STATE)) {
            myPcs.firePropertyChange(MENU, myCurrentMenu, WEAPON_MENU);
            switch (theEvt.getKeyCode()) {
                case KeyEvent.VK_UP -> {
                    if (myDoomGuy.inventoryContains(new Weapon(1000, 100, 1, 1, "BFG"))) {
                        myDoomGuy.equipWeapon((Weapon) myDoomGuy.getInventory().getItem(new Weapon(1000, 100, 1, 1, "BFG")));
                        //myDoomGuy.equipWeapon(new Weapon(1000, 100, 1, 1, "BFG"));
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "BFG EQUIPPED");
                    } else {
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "No BFG in Inventory :(");
                    }
                    monsterAttack();
                }
                case KeyEvent.VK_DOWN -> {
                    if (myDoomGuy.inventoryContains(new Weapon(1, 1, 1, 1, "Rawket Lawnchair"))) {
                        myDoomGuy.equipWeapon((Weapon) myDoomGuy.getInventory().getItem(new Weapon(1000, 100, 1, 1, "Rawket Lawnchair")));
                        //myDoomGuy.equipWeapon(new Weapon(1, 1, 1, 1, "Rawket Lawnchair"));
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "Rawket Lawnchair EQUIPPED");
                    } else {
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "No Rawket Lawnchair in Inventory :(");
                    }
                    monsterAttack();
                }
                case KeyEvent.VK_LEFT -> {
                    if (myDoomGuy.inventoryContains(new Weapon(10, 0.8, 0.5, 10, "Pistol"))) {
                        myDoomGuy.equipWeapon((Weapon) myDoomGuy.getInventory().getItem(new Weapon(1000, 100, 1, 1, "Pistol")));
                        //myDoomGuy.equipWeapon(new Weapon(10, 0.8, 0.5, 10, "Pistol"));
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "Pistol EQUIPPED");
                    } else {
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "No Pistol in Inventory :(");
                    }
                    monsterAttack();
                }
                case KeyEvent.VK_RIGHT -> {
                    if (myDoomGuy.inventoryContains(new Weapon(1, 1, 1, 1, "Shotgun"))) {
                        myDoomGuy.equipWeapon((Weapon) myDoomGuy.getInventory().getItem(new Weapon(1000, 100, 1, 1, "Shotgun")));
                        //myDoomGuy.equipWeapon(new Weapon(1, 1, 1, 1, "Shotgun"));
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "Shotgun EQUIPPED");
                    } else {
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "No Shotgun in Inventory :(");
                    }
                    monsterAttack();
                }
                default -> {
                }
            }
            return;
        }

        //Use potion
        if (theEvt.isAltDown() && (myCurrentState == GameState.COMBAT_STATE || myCurrentState == GameState.MAP_STATE)) {
            myPcs.firePropertyChange(MENU, myCurrentMenu, POTION_MENU);
            switch (theEvt.getKeyCode()) {
                case KeyEvent.VK_UP -> {
                    if (myDoomGuy.inventoryContains(new HealthPotion())) {
                        myDoomGuy.useItem(new HealthPotion());
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "DG HP: " + myDoomGuy.getHealth());
                    } else {
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "Go buy some drugs.");
                    }
                    monsterAttack();
                }
                case KeyEvent.VK_DOWN -> {
                    if (myDoomGuy.inventoryContains(new VisionPotion())) {
                        myDungeon.useVisionPotion();
                        myDoomGuy.removeFromInventory(new VisionPotion());
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "Vision Potion Used!");
                    } else {
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "No Vision Potion in Inventory!");
                    }
                    monsterAttack();
                }
                default -> {
                }
            }
            return;
        }

        switch (theEvt.getKeyCode()) {
            case KeyEvent.VK_W -> {
                switch (myCurrentState) {
                    case MAP_STATE -> myDungeon.movePlayer(new Point(0, -1));
                    case MENU_STATE, TITLE_STATE -> menuMovement(-1);
                }
            }
            case KeyEvent.VK_A -> {
                switch (myCurrentState) {
                    case MAP_STATE -> myDungeon.movePlayer(new Point(-1, 0));
                    //case MENU_STATE -> System.out.println("Menu");
                }
            }
            case KeyEvent.VK_S -> {
                switch (myCurrentState) {
                    case MAP_STATE -> myDungeon.movePlayer(new Point(0, 1));
                    case MENU_STATE, TITLE_STATE -> menuMovement(1);
                }
            }
            case KeyEvent.VK_D -> {
                switch (myCurrentState) {
                    case MAP_STATE -> myDungeon.movePlayer(new Point(1, 0));
                    //case MENU_STATE -> System.out.println("Menu");
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
                    case MAP_STATE -> lootRoom();
                    case MENU_STATE -> selectMenuOption(); //select menu option
                    case TITLE_STATE -> selectTitleOption();
                }
            }
            case KeyEvent.VK_Q -> {
                switch (myCurrentState) {
                    case COMBAT_STATE -> combatAttack();
                }
            }
            default -> {
            }
        }
    }

    /**
     * Fenceposting for alt-menu usage.
     * Checking for combat.
     * @param theEvt the event to be processed
     */
    @Override
    public void keyReleased(final KeyEvent theEvt) {
        //Found Combat
        if (myDungeon.getRoom((int) myDungeon.getPlayerPos().getX(),
                (int) myDungeon.getPlayerPos().getY()).getMonster() != null
                && myCurrentState != GameState.COMBAT_STATE
                && myCurrentState != GameState.TITLE_STATE) {
            enactCombatState();
        }

        //Checking Exit Capability
        if (myDungeon.getPlayerPos().equals(myDungeon.getExitFlag()) && myCurrentState == GameState.MAP_STATE) {
            if (myDoomGuy.pillarCount() == 4) {
                myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "");
                myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "A WINRAR IS YOU");
                myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "");
                enactTitleState();
            } else {
                myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "");
                myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "You only have: " + myDoomGuy.pillarCount() + " pillars you rube");
                myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "");
            }
        }

        //Show Weapons
        if (theEvt.getKeyCode() == KeyEvent.VK_SHIFT) {
            if (myCurrentState == GameState.COMBAT_STATE || myCurrentState == GameState.MAP_STATE) {
                myPcs.firePropertyChange(MENU, WEAPON_MENU, myCurrentMenu);
            }
        }

        //Show Drugs
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

    /**
     * Logic for entering combat state. Also calculates turn order.
     */
    private void enactCombatState() {
        myCurrentState = GameState.COMBAT_STATE;
        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "");
        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "You have encountered a monster!");
        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "Press Q to attack!");

        final Room currentRoom = myDungeon.getRoom((int) myDungeon.getPlayerPos().getX(),
                (int) myDungeon.getPlayerPos().getY());
        final double speedCalc = myDoomGuy.getFireRate() - currentRoom.getMonster().getFireRate();
        myDGAttacked = speedCalc < 0;
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

    private void enactTitleState() {
        myCurrentState = GameState.TITLE_STATE;
        myMenuPosition = 0;

        String[] old = myCurrentMenu;
        myCurrentMenu = TITLE_MENU;

        myPcs.firePropertyChange(MENU, old, TITLE_MENU);
        myPcs.firePropertyChange(MENU_POS, old, myMenuPosition);
        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null,  "Entered Title State");
    }

    private void lootRoom() {
        final Room currentRoom = myDungeon.getRoom((int) myDungeon.getPlayerPos().getX(),
                (int) myDungeon.getPlayerPos().getY());

        if (!myDungeon.hasItems()) {
            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null,  "No Items To Loot");
            return;
        }

        for (Item i: myDungeon.getRoomInventory().getItems()) {
            while (myDungeon.getRoomInventory().containsItem(i)) {
                myDoomGuy.addToInventory(i);
                myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null,  "Looted: " + i.getName());
                myDungeon.getRoomInventory().removeItem(i);
            }
        }

        myPcs.firePropertyChange(Dungeon.ROOM_CONTENT, new Point((int) myDungeon.getPlayerPos().getX(),
                (int) myDungeon.getPlayerPos().getY()), currentRoom.getMonster() != null ? "M" : "");
    }

    /**
     * Move to new menu option.
     */
    private void menuMovement(final int theMovement) {
        if ((myMenuPosition == 0 && theMovement == -1) || (myMenuPosition == myCurrentMenu.length - 1 && theMovement == 1)) {
            return;
        }
        final int oldPos = myMenuPosition;
        myMenuPosition += theMovement;

        //System.out.println("Attempting menu movement: " + theMovement);
        myPcs.firePropertyChange(MENU_POS, oldPos, myMenuPosition);
    }

    /**
     * Select main menu option.
     */
    private void selectMenuOption() {

        //player inventory
        if (myMenuPosition == 0) {
            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "");
            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "Inventory Contains:");
            if (myDoomGuy.getInventory().isEmpty()) {
                myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "Nothing you poor fool!");
            }
            final String[] dgInvenToPrint = myDoomGuy.getInventory().toString().split("\n");
            for (String s: dgInvenToPrint) {
                myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, s);
            }
            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "");
        }

        if (myMenuPosition == 3) {
            System.exit(0);
        }
    }

    /**
     * Select title menu option.
     */
    private void selectTitleOption() {
        //new game
        if (myMenuPosition == 0) {
            myDungeon = new Dungeon(5);
            myDoomGuy = new DoomGuy(100, "DoomGuy",
                    new Weapon(10, 0.8, 0.5, 100, "Pistol"));

            //test items
            myDoomGuy.addToInventory(new VisionPotion());
            //myDoomGuy.addToInventory(new VisionPotion());
            //myDoomGuy.addToInventory(new VisionPotion());
            //myDoomGuy.addToInventory(new HealthPotion());
            myDoomGuy.addToInventory(new HealthPotion());
            //myDoomGuy.addToInventory(new Weapon(1, 1, 1, 1, "BFG"));

            //dungeon pcs
            myDungeon.addPropertyChangeListener(Dungeon.HERO_POS, myView);
            myDungeon.addPropertyChangeListener(Dungeon.TEXT_UPDATE, myView);
            myDungeon.addPropertyChangeListener(Dungeon.ROOM_VIS, myView);
            myDungeon.addPropertyChangeListener(Dungeon.ROOM_CONTENT, myView);

            //Fenceposting creation
            myPcs.firePropertyChange(RESET_MAP,null,myDungeon.getPlayerPos());
            enactMapState();
            myDungeon.setRoomVisible(myDungeon.getPlayerPos());
        }

        //quit
        if (myMenuPosition == 1) {
            System.exit(0);
        }

        //watch kung fury
        if (myMenuPosition == 2) {
            if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)){
                Desktop desktop = Desktop.getDesktop();
                try{
                    desktop.browse(new URI("https://www.youtube.com/watch?v=bS5P_LAqiVg"));
                }catch(IOException | URISyntaxException ioe){
                    ioe.printStackTrace();
                }
            }else{
                Runtime runtime = Runtime.getRuntime();
                try{
                    runtime.exec("xdg-open " + "https://www.youtube.com/watch?v=bS5P_LAqiVg");
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    /**
     * Initial start of game process.
     */
    private void runGame() {
        /* Use an appropriate Look and Feel */
        try {
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (final UnsupportedLookAndFeelException | IllegalAccessException
                       | InstantiationException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(this::createAndShowGUI);

    }

    /**
     * Method that performs one complete combat turn.
     * Should be used when player chooses to attack in combat.
     */
    private void combatAttack() {
        combatRound();
        if (myCurrentState.equals(GameState.COMBAT_STATE)) {
            combatRound();
        }
    }

    /**
     * Allow monster to counter-attack if player performs non-combative action in combat.
     */
    private void monsterAttack() {
        if (myCurrentState == GameState.COMBAT_STATE) {
            myDGAttacked = true;
            combatRound();
        }
    }

    /**
     * A single round of combat.
     */
    private void combatRound() {
        final Room currentRoom = myDungeon.getRoom((int) myDungeon.getPlayerPos().getX(),
                (int) myDungeon.getPlayerPos().getY());
        String attackRes;

        if (!myDGAttacked) {
            attackRes = "Doomguy Attacks! " + myDoomGuy.attack(currentRoom.getMonster());
        } else {
            attackRes = "Monster Attacks! " + currentRoom.getMonster().attack(myDoomGuy);
        }
        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, attackRes);
        final String health = "DG HP: " + myDoomGuy.getHealth() + " Enemy Health: " + currentRoom.getMonster().getHealth();
        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, health);

        if (!myDoomGuy.isAlive()) {
            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "YOU DIED");
            enactTitleState();
        }
        if (!currentRoom.getMonster().isAlive()) {
            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "You killed the " + currentRoom.getMonster().getName());
            currentRoom.setMonster(null);
            myPcs.firePropertyChange(Dungeon.ROOM_CONTENT, new Point((int) myDungeon.getPlayerPos().getX(),
                    (int) myDungeon.getPlayerPos().getY()), currentRoom.getInventory().inventorySize() != 0 ? "I" : "");
            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "");
            enactMapState();
        }

        myDGAttacked ^= true;
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

//        frame.setSize(SCREEN_SIZE.width / 2, SCREEN_SIZE.height / 2);
        frame.setSize(1000,500);
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
