package controller;
import com.github.strikerx3.jxinput.XInputAxes;
import com.github.strikerx3.jxinput.XInputButtons;
import com.github.strikerx3.jxinput.XInputComponents;
import com.github.strikerx3.jxinput.XInputDevice;
import com.github.strikerx3.jxinput.enums.XInputButton;
import com.github.strikerx3.jxinput.exceptions.XInputNotLoadedException;
import com.github.strikerx3.jxinput.listener.XInputDeviceListener;
import view.DungeonView;
import model.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

/**
 * Controller for the Project_Doom game.
 * @author Jered Wiegel and James Deal
 * @version 1.1
 */
public class DungeonController extends JFrame implements KeyListener, Serializable, XInputDeviceListener {

    /** PCS Type for Menu position. */
    public static final String MENU_POS = "MenuPos";
    /** PCS Type for Menu State. */
    public static final String MENU = "Menu";
    /** PCS Type for New Game Fire. */
    public static final String RESET_MAP = "ResetMap";

    /** Serial ID of executable instance. */
    @Serial
    private static final long serialVersionUID = 1L;
    /** A ToolKit. */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();
    /** Screen Dimension. */
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();

    /** List of normal menu items. */
    private static final String[] MAIN_MENU = {"Inventory", "Save", "Load", "Quit"};
    /** List of map menu items. */
    private static final String[] MAP_MENU = {"WASD/ABXY to move", "E/SEL to Loot Room", "Shft/RSHL for Guns",
                                              "Alt/LSHL for Potions", "Esc/STRT for menu", "Watch Kung Fury!"};
    /** List of Weapon Radial menu items. */
    private static final String[] WEAPON_MENU = {"W/Y: BFG", "A/X: Pistol", "S/A: Rawket Lawnchair", "D/B: Shotgun"};
    /** List of Potion Radial menu items. */
    private static final String[] POTION_MENU = {"W/Y: Health Potion", "S/A: Vision Potion"};
    /** List of Title menu items. */
    private static final String[] TITLE_MENU = {"New Game", "Load Game", "Quit", "Watch Kung Fury!"};

    /** Map Size Variable. */
    private static final int DUNGEON_SIZE = 5;

    /** Property Change Object. */
    private final PropertyChangeSupport myPcs;

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

    /** Tracks which DunCha attacked last. */
    private boolean myDGAttacked;

    /** xInput device to use. */
    private XInputDevice myDevice;
    /** Components of XInput device. */
    XInputComponents myComponents;
    /** Buttons of XInput device. */
    XInputButtons myButtons;
    /** Axes of XInput device (DPAD lives here). */
    XInputAxes myAxes;

    /**
     * Creates a new DungeonController object.
     */
    public DungeonController() {
        myDungeon = new Dungeon(DUNGEON_SIZE);
        myDoomGuy = new DoomGuy(100, "DoomGuy", new Weapon("Pistol"));

        //initial items
        myDoomGuy.addToInventory(new VisionPotion());
        myDoomGuy.addToInventory(new HealthPotion());

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
        enactTitleState();
        myDungeon.setRoomVisible(myDungeon.getPlayerPos());

        try {
            xInputTest();
        } catch (XInputNotLoadedException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Constructor helper for instantiating Controller object for XInput.
     * @throws XInputNotLoadedException idk, documentation not loaded.
     */
    private void xInputTest() throws XInputNotLoadedException {
        // Retrieve all devices
        final XInputDevice[] devices = XInputDevice.getAllDevices();
        for (XInputDevice d: devices) {
            //System.out.println(d.poll());

            //find a connected device to use.
            if (d.poll()){
                myDevice = d;
            }
        }

        if (myDevice == null) {
            return;
        }

        // Retrieve the device for player X
        //device = XInputDevice.getDeviceFor(1);
        //device = devices[3];

        myComponents = myDevice.getComponents();
        myButtons = myComponents.getButtons();
        myAxes = myComponents.getAxes();

        myDevice.addListener(this);
    }

    /**
     * Defines the action that is triggered depending on current state of the game (WASD).
     * @param theEvt the key pressed.
     */
    @Override
    public void keyPressed(final KeyEvent theEvt) {

        //Change weapon
        if (theEvt.isShiftDown() && (myCurrentState == GameState.COMBAT_STATE || myCurrentState == GameState.MAP_STATE)) {
            myPcs.firePropertyChange(MENU, myCurrentMenu, WEAPON_MENU);
            switch (theEvt.getKeyCode()) {
                case KeyEvent.VK_W -> {
                    if (myDoomGuy.inventoryContains(new Weapon("BFG"))) {
                        myDoomGuy.equipWeapon((Weapon) myDoomGuy.getInventory().getItem(new Weapon("BFG")));
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "BFG EQUIPPED");
                    } else {
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "No BFG in Inventory :(");
                    }
                    monsterAttack();
                }
                case KeyEvent.VK_S -> {
                    if (myDoomGuy.inventoryContains(new Weapon("Rawket Lawnchair"))) {
                        myDoomGuy.equipWeapon((Weapon) myDoomGuy.getInventory().getItem(new Weapon("Rawket Lawnchair")));
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "Rawket Lawnchair EQUIPPED");
                    } else {
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "No Rawket Lawnchair in Inventory :(");
                    }
                    monsterAttack();
                }
                case KeyEvent.VK_A -> {
                    if (myDoomGuy.inventoryContains(new Weapon("Pistol"))) {
                        myDoomGuy.equipWeapon((Weapon) myDoomGuy.getInventory().getItem(new Weapon("Pistol")));
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "Pistol EQUIPPED");
                    } else {
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "No Pistol in Inventory :(");
                    }
                    monsterAttack();
                }
                case KeyEvent.VK_D -> {
                    if (myDoomGuy.inventoryContains(new Weapon("Shotgun"))) {
                        myDoomGuy.equipWeapon((Weapon) myDoomGuy.getInventory().getItem(new Weapon("Shotgun")));
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
                case KeyEvent.VK_W -> {
                    if (myDoomGuy.inventoryContains(new HealthPotion())) {
                        myDoomGuy.useItem(new HealthPotion());
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "DG HP: " + myDoomGuy.getHealth());
                    } else {
                        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "Go buy some drugs.");
                    }
                    monsterAttack();
                }
                case KeyEvent.VK_S -> {
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
                    case MENU_STATE -> selectMenuOption();
                    case TITLE_STATE -> selectTitleOption();
                    case COMBAT_STATE -> combatAttack();
                }
            }
            default -> {
            }
        }
    }

    /**
     * Defines the action that is triggered depending on current state of the game (WASD).
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
                myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "You only have: "
                                                                    + myDoomGuy.pillarCount() + " pillars you rube");
                myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "");
            }
        }

        //Hide Weapons
        if (theEvt.getKeyCode() == KeyEvent.VK_SHIFT) {
            if (myCurrentState == GameState.COMBAT_STATE || myCurrentState == GameState.MAP_STATE) {
                myPcs.firePropertyChange(MENU, WEAPON_MENU, myCurrentMenu);
            }
        }

        //Hide Drugs
        if (theEvt.getKeyCode() == KeyEvent.VK_ALT) {
            if (myCurrentState == GameState.COMBAT_STATE || myCurrentState == GameState.MAP_STATE) {
                myPcs.firePropertyChange(MENU, POTION_MENU, myCurrentMenu);
            }
        }
    }

    /**
     * Unused.
     * @param theEvt the event to be processed
     */
    @Override
    public void keyTyped(final KeyEvent theEvt) {
    }

    /**
     * Logic for entering combat state. Also calculates turn order.
     */
    private void enactCombatState() {
        myCurrentState = GameState.COMBAT_STATE;
        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "");
        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "You have encountered a "
                                                                                + myDungeon.getMonster().toString());
        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "Press E/SEL to attack!");

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

        final String[] old = myCurrentMenu;
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

        final String[] old = myCurrentMenu;
        myCurrentMenu = MAIN_MENU;

        myPcs.firePropertyChange(MENU, old, MAIN_MENU);
        myPcs.firePropertyChange(MENU_POS, old, myMenuPosition);
        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null,  "Entered Menu State");
    }

    /**
     * Load into Title state, set default menu position.
     */
    private void enactTitleState() {
        myCurrentState = GameState.TITLE_STATE;
        myMenuPosition = 0;

        final String[] old = myCurrentMenu;
        myCurrentMenu = TITLE_MENU;

        myPcs.firePropertyChange(MENU, old, TITLE_MENU);
        myPcs.firePropertyChange(MENU_POS, old, myMenuPosition);
        myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null,  "Entered Title State");
    }

    /**
     * Loot current room.
     */
    private void lootRoom() {
        final Room currentRoom = myDungeon.getRoom((int) myDungeon.getPlayerPos().getX(),
                (int) myDungeon.getPlayerPos().getY());

        if (!myDungeon.hasItems()) {
            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null,  "No Items To Loot");
            return;
        }

        for (Item i: myDungeon.getRoomInventory().getItems()) {
            myDoomGuy.addToInventory(i);
            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null,  "Looted: " + i.getName());
            myDungeon.getRoomInventory().removeItem(i);
        }

        myPcs.firePropertyChange(Dungeon.ROOM_CONTENT, new Point((int) myDungeon.getPlayerPos().getX(),
                (int) myDungeon.getPlayerPos().getY()), currentRoom.getMonster() != null ? "M" : "");
    }

    /**
     * Move to new menu option.
     */
    private void menuMovement(final int theMovement) {
        if ((myMenuPosition == 0 && theMovement == -1)
                || (myMenuPosition == myCurrentMenu.length - 1 && theMovement == 1)) {
            return;
        }
        final int oldPos = myMenuPosition;
        myMenuPosition += theMovement;
        myPcs.firePropertyChange(MENU_POS, oldPos, myMenuPosition);
    }

    /**
     * Select main menu option.
     */
    private void selectMenuOption() {
        //{"Inventory", "Save", "Load", "Quit"};

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

        if (myMenuPosition == 1) {
            saveData();
            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "");
            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "Game Saved");
            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "");
        }

        if (myMenuPosition == 2) {
            loadData();
            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "");
            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "Game Loaded");
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
        //{"New Game", "Load Game", "Quit", "Watch Kung Fury!"};

        //new game
        if (myMenuPosition == 0) {
            setupNewGame();
        }

        if (myMenuPosition == 1) {
            loadData();
        }

        //quit
        if (myMenuPosition == 2) {
            System.exit(0);
        }

        //watch kung fury
        if (myMenuPosition == 3) {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                final Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.browse(new URI("https://www.youtube.com/watch?v=bS5P_LAqiVg"));
                } catch (final IOException | URISyntaxException ioe) {
                    ioe.printStackTrace();
                }
            } else {
                final Runtime runtime = Runtime.getRuntime();
                try {
                    runtime.exec("xdg-open " + "https://www.youtube.com/watch?v=bS5P_LAqiVg");
                } catch (final IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    private void setupNewGame() {
        myDungeon = new Dungeon(5);
        myDoomGuy = new DoomGuy(100, "DoomGuy", new Weapon("Pistol"));

        //initial items
        myDoomGuy.addToInventory(new VisionPotion());
        myDoomGuy.addToInventory(new HealthPotion());

        //dungeon pcs
        myDungeon.addPropertyChangeListener(Dungeon.HERO_POS, myView);
        myDungeon.addPropertyChangeListener(Dungeon.TEXT_UPDATE, myView);
        myDungeon.addPropertyChangeListener(Dungeon.ROOM_VIS, myView);
        myDungeon.addPropertyChangeListener(Dungeon.ROOM_CONTENT, myView);

        //Fenceposting creation
        myPcs.firePropertyChange(RESET_MAP, null, myDungeon.getPlayerPos());
        enactMapState();
        myDungeon.setRoomVisible(myDungeon.getPlayerPos());
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


        final Runnable helloRunnable = new Runnable() {
            public void run() {
                myDevice.poll();
            }
        };

        final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(helloRunnable, 0, 20, TimeUnit.MILLISECONDS);
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
        final String attackRes;

        if (!myDGAttacked) {
            attackRes = myDoomGuy.attack(currentRoom.getMonster());
        } else {
            attackRes = currentRoom.getMonster().toString() + " Attacks! " + currentRoom.getMonster().attack(myDoomGuy);
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
        frame.setSize(1000, 500);
        frame.setLocation(SCREEN_SIZE.width / 2 - frame.getWidth() / 2,
                        SCREEN_SIZE.height / 2 - frame.getHeight() / 2);
        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.setVisible(true);
    }

    /**
     * Allows for serialized saving of game state.
     */
    private void saveData() {
        // Serialization
        try {

            // Saving of object in a file
            final FileOutputStream file = new FileOutputStream("savegame.doom");
            final ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(myDungeon);
            out.writeObject(myDoomGuy);

            out.close();
            file.close();
        } catch (final IOException ex) {
            System.out.println("IOException but probably worked anyway.");
        }
    }

    /**
     * Loads serialized game state.
     */
    private void loadData() {
        // Deserialization
        try {

            // Reading the object from a file
            final FileInputStream file = new FileInputStream("savegame.doom");
            final ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            myDungeon = (Dungeon) in.readObject();
            myDoomGuy = (DoomGuy) in.readObject();

            in.close();
            file.close();
        } catch (final IOException ex) {
            System.out.println("IOException is caught");
        } catch (final ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
        }

        //dungeon pcs
        myDungeon.addPropertyChangeListener(Dungeon.HERO_POS, myView);
        myDungeon.addPropertyChangeListener(Dungeon.TEXT_UPDATE, myView);
        myDungeon.addPropertyChangeListener(Dungeon.ROOM_VIS, myView);
        myDungeon.addPropertyChangeListener(Dungeon.ROOM_CONTENT, myView);

        myPcs.firePropertyChange(RESET_MAP, null, myDungeon.getPlayerPos());
        enactMapState();
        myDungeon.setRoomVisible(myDungeon.getPlayerPos());
        loadDataHelper();
    }

    /**
     * Helper to serialized loading, to set up room visibility again.
     */
    private void loadDataHelper() {
        for (int row = 0; row < DUNGEON_SIZE; row++) {
            for (int col = 0; col < DUNGEON_SIZE; col++) {
                if (myDungeon.getRoom(row, col).getDiscovered()) {
                    myDungeon.setRoomVisible(new Point(row, col));
                }
            }
        }
    }

    /**
     * Starts game instance.
     * @param theArgs unused.
     */
    public static void main(final String[] theArgs) {
        final DungeonController controller = new DungeonController();

        controller.runGame();
    }

    /**
     * @param theListener the listener to add
     * @param thePropertyName the property to listen to
     */
    public void addPropertyChangeListener(final String thePropertyName, final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(thePropertyName, theListener);
    }

    /**
     * Unused, if a controller connects late, that's too bad.
     */
    @Override
    public void connected() {
        //let game run
    }

    /**
     * Unused, if a controller disconnects, start game again.
     */
    @Override
    public void disconnected() {
        //let game run
    }

    /**
     * Handles event input from controller. Based on timer in runGame().
     * @param theXInputButton button changed.
     * @param theB true if pressed, false if released.
     */
    @Override
    public void buttonChanged(final XInputButton theXInputButton, final boolean theB) {
        //System.out.println(theXInputButton.toString() + " " + theB);

        //key pressed
        if (theB) {
            //Change weapon
            if (theXInputButton.equals(XInputButton.RIGHT_SHOULDER) && (myCurrentState == GameState.COMBAT_STATE || myCurrentState == GameState.MAP_STATE)) {
                myPcs.firePropertyChange(MENU, myCurrentMenu, WEAPON_MENU);
            }
            if (myButtons.rShoulder) {
                switch (theXInputButton) {
                    case Y -> {
                        if (myDoomGuy.inventoryContains(new Weapon("BFG"))) {
                            myDoomGuy.equipWeapon((Weapon) myDoomGuy.getInventory().getItem(new Weapon("BFG")));
                            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "BFG EQUIPPED");
                        } else {
                            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "No BFG in Inventory :(");
                        }
                        monsterAttack();
                    }
                    case A -> {
                        if (myDoomGuy.inventoryContains(new Weapon("Rawket Lawnchair"))) {
                            myDoomGuy.equipWeapon((Weapon) myDoomGuy.getInventory().getItem(new Weapon("Rawket Lawnchair")));
                            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "Rawket Lawnchair EQUIPPED");
                        } else {
                            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "No Rawket Lawnchair in Inventory :(");
                        }
                        monsterAttack();
                    }
                    case X -> {
                        if (myDoomGuy.inventoryContains(new Weapon("Pistol"))) {
                            myDoomGuy.equipWeapon((Weapon) myDoomGuy.getInventory().getItem(new Weapon("Pistol")));
                            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "Pistol EQUIPPED");
                        } else {
                            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "No Pistol in Inventory :(");
                        }
                        monsterAttack();
                    }
                    case B -> {
                        if (myDoomGuy.inventoryContains(new Weapon("Shotgun"))) {
                            myDoomGuy.equipWeapon((Weapon) myDoomGuy.getInventory().getItem(new Weapon("Shotgun")));
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
            } //end of weapon swap

            //Use potion
            if ((theXInputButton.equals(XInputButton.LEFT_SHOULDER) && (myCurrentState == GameState.COMBAT_STATE || myCurrentState == GameState.MAP_STATE)))
            {
                myPcs.firePropertyChange(MENU, myCurrentMenu, POTION_MENU);
            }
            if (myButtons.lShoulder) {
                switch (theXInputButton) {
                    case Y -> {
                        if (myDoomGuy.inventoryContains(new HealthPotion())) {
                            myDoomGuy.useItem(new HealthPotion());
                            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "DG HP: " + myDoomGuy.getHealth());
                        } else {
                            myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "Go buy some drugs.");
                        }
                        monsterAttack();
                    }
                    case A -> {
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
            } //end of pot swap

            switch (theXInputButton) {
                case Y -> {
                    switch (myCurrentState) {
                        case MAP_STATE -> myDungeon.movePlayer(new Point(0, -1));
                        case MENU_STATE, TITLE_STATE -> menuMovement(-1);
                    }
                }
                case X -> {
                    switch (myCurrentState) {
                        case MAP_STATE -> myDungeon.movePlayer(new Point(-1, 0));
                    }
                }
                case A -> {
                    switch (myCurrentState) {
                        case MAP_STATE -> myDungeon.movePlayer(new Point(0, 1));
                        case MENU_STATE, TITLE_STATE -> menuMovement(1);
                    }
                }
                case B -> {
                    switch (myCurrentState) {
                        case MAP_STATE -> myDungeon.movePlayer(new Point(1, 0));
                    }
                }
                case START -> {
                    switch (myCurrentState) {
                        case MAP_STATE ->  enactMenuState();
                        case MENU_STATE -> enactMapState();
                    }
                }
                case BACK -> {
                    switch (myCurrentState) {
                        case MAP_STATE -> lootRoom();
                        case MENU_STATE -> selectMenuOption();
                        case TITLE_STATE -> selectTitleOption();
                        case COMBAT_STATE -> combatAttack();
                    }
                }
//                case KeyEvent.VK_Q -> {
//                    switch (myCurrentState) {
//                        case COMBAT_STATE -> combatAttack();
//                    }
//                }
                default -> {
                }
            }
        }

        //key released
        else {
            //found combat
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
                    myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "You only have: "
                            + myDoomGuy.pillarCount() + " pillars you rube");
                    myPcs.firePropertyChange(Dungeon.TEXT_UPDATE, null, "");
                }
            }

            //Hide Weapons
            if (theXInputButton.equals(XInputButton.RIGHT_SHOULDER)) {
                if (myCurrentState == GameState.COMBAT_STATE || myCurrentState == GameState.MAP_STATE) {
                    myPcs.firePropertyChange(MENU, WEAPON_MENU, myCurrentMenu);
                }
            }

            //Hide Drugs
            if (theXInputButton.equals(XInputButton.LEFT_SHOULDER)) {
                if (myCurrentState == GameState.COMBAT_STATE || myCurrentState == GameState.MAP_STATE) {
                    myPcs.firePropertyChange(MENU, POTION_MENU, myCurrentMenu);
                }
            }
        }
    }

}
