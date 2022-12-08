import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;

/*
temp, documentation links;
https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
 */

/**
 * GUI based view for Dungeons.
 * @author james deal
 * @version 0.3.33 this is (not) the end
 */
public class DungeonView extends JPanel implements PropertyChangeListener {

    @Serial
    private static final long serialVersionUID = 4L;

    /** Image used for FoW room display. */
    private static ImageIcon FOG_ROOM_TILE;
    /** Image used for Explored room display. */
    private static ImageIcon VISIBLE_ROOM_TILE;
    /** Image used for player in room display. */
    private static ImageIcon PLAYER_TILE;

    /** Image used for room display when has monster and item. */
    private static ImageIcon ROOM_CONTENT_BOTH;
    /** Image used for room display when has item. */
    private static ImageIcon ROOM_CONTENT_ITEM;
    /** Image used for room display when has monster. */
    private static ImageIcon ROOM_CONTENT_MONSTER;
    /** Image used for room display when has jack. */
    private static ImageIcon ROOM_CONTENT_NOTHING;

    /** Size of dungeon to display. */
    private final int myDungeonSize;

    /** GUI Map Panel. */
    private JPanel myMapPanel;
    /** GUI GridBag Constraints. */
    private GridBagConstraints myGBC;
    /** GUI collection of Map's Room Labels. */
    private Map<Point, JLabel> myMapLabels;
    /** GUI collection of Map's Content Labels. */
    private Map<Point, JLabel> myOverlayLabels;
    /** GUI Map's Player Label. */
    private JLabel myPlayerLabel;

    /** Current Menu Choices. */
    private String[] myMenuOptions;
    /** GUI Label to hold menu. */
    private JLabel myMenuLabel;

    /** GUI Label to hold text log. */
    private JScrollPane myLogScrollPane;
    /** GUI Element to fire updates to. */
    private JTextArea myLogTextArea;

    /**
     * Default constructor to ready the view.
     * @param theSize dimension of square dungeon.
     * @param thePlayer starting position of the player.
     */
    public DungeonView(final int theSize, final Point thePlayer) {
        super();
        myDungeonSize = theSize;
        loadImages();
        setupComponents();
        placePlayer(thePlayer);
    }

    /**
     * Load file images in to use in GUI.
     */
    private void loadImages() {
        BufferedImage fogRoomImage = null;
        BufferedImage playerImage = null;
        BufferedImage visibleRoomImage = null;
        BufferedImage jackImage = null;
        BufferedImage itemImage = null;
        BufferedImage monsterImage = null;
        BufferedImage itemMonsterImage = null;

        try {
            fogRoomImage = ImageIO.read(new File("fellcleave75.png"));
            playerImage = ImageIO.read(new File("emote75.png"));
            visibleRoomImage = ImageIO.read(new File("AYAYA75.png"));
            jackImage = ImageIO.read(new File("jack.png"));
            itemImage = ImageIO.read(new File("i.png"));
            monsterImage = ImageIO.read(new File("m.png"));
            itemMonsterImage = ImageIO.read(new File("mi.png"));
        } catch (final IOException ioe) {
            System.out.println("Unable to fetch image.");
            ioe.printStackTrace();
        }

        assert fogRoomImage != null;
        FOG_ROOM_TILE = new ImageIcon(fogRoomImage);
        assert playerImage != null;
        PLAYER_TILE = new ImageIcon(playerImage);
        assert visibleRoomImage != null;
        VISIBLE_ROOM_TILE = new ImageIcon(visibleRoomImage);

        assert jackImage != null;
        ROOM_CONTENT_NOTHING = new ImageIcon(jackImage);
        assert itemImage != null;
        ROOM_CONTENT_ITEM = new ImageIcon(itemImage);
        assert monsterImage != null;
        ROOM_CONTENT_MONSTER = new ImageIcon(monsterImage);
        assert itemMonsterImage != null;
        ROOM_CONTENT_BOTH = new ImageIcon(itemMonsterImage);
    }

    /**
     * Initial setup of GUI.
     */
    private void setupComponents() {
        setLayout(new BorderLayout());

        //temp so we understand where things go
        //add(new JLabel("Doomguy stats: Idk prob dead"), BorderLayout.NORTH);
        //add(new JLabel("Bite me"), BorderLayout.SOUTH);

        //setup menu
        myMenuLabel = new JLabel("Menu Options: Die");
        myMenuLabel.setPreferredSize(new Dimension(400, 100));
        myMenuLabel.setFont(new Font("Serif", Font.PLAIN, 23));
        add(myMenuLabel, BorderLayout.CENTER);

        //setup text log
        myLogTextArea = new JTextArea(10, 30);
        myLogTextArea.setFont(new Font("Serif", Font.PLAIN, 16));
        myLogScrollPane = new JScrollPane(myLogTextArea);
        myLogScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        myLogScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        myLogScrollPane.setBackground(Color.GRAY);
        myLogTextArea.setBackground(Color.GRAY);
        add(myLogScrollPane, BorderLayout.EAST);

        //setup dungeon map component
        myMapPanel = new JPanel();  //panel itself
        myMapPanel.setLayout(new GridBagLayout());  //grid for inside the panel
        myGBC = new GridBagConstraints();   //ability to use grid effectively
        add(myMapPanel, BorderLayout.WEST); //put grid in the panel
        myMapLabels = new HashMap<>();   //ability to reference grid-objects
        myOverlayLabels = new HashMap<>(); //ability to reference room-content objects

        //Setup player label.
        myPlayerLabel = new JLabel(PLAYER_TILE);

        //fill grid with rooms
        JLabel mapLabelToSet;
        JLabel contentLabelToSet;
        for (int i = 0; i < myDungeonSize; i++) {
            for (int j = 0; j < myDungeonSize; j++) {
                mapLabelToSet = new JLabel(FOG_ROOM_TILE);
                contentLabelToSet = new JLabel(ROOM_CONTENT_NOTHING);
                myMapLabels.put(new Point(i, j), mapLabelToSet);
                myOverlayLabels.put(new Point(i, j), contentLabelToSet);
                myGBC.gridx = i;
                myGBC.gridy = j;
                myMapPanel.add(contentLabelToSet, myGBC);
                myMapPanel.add(mapLabelToSet, myGBC);
            }
        }

        myMapPanel.setOpaque(true);
        myMapPanel.setBackground(Color.DARK_GRAY);
        this.setOpaque(true);
        this.setBackground(Color.GRAY);
    }

    /**
     * Draw player in designated spot. Also redraws dungeon tile.
     * @param thePosition position to draw at.
     */
    private void placePlayer(final Point thePosition) {
        myGBC.gridx = (int) thePosition.getX();
        myGBC.gridy = (int) thePosition.getY();
        myMapPanel.add(myOverlayLabels.get(thePosition), myGBC);
        myMapPanel.add(myPlayerLabel, myGBC);
        myMapPanel.add(myMapLabels.get(thePosition), myGBC);
        myMapPanel.updateUI();
    }

    /**
     * Draw current menu.
     */
    private void drawMenu(final int theHighlight) {
        final StringBuilder builder = new StringBuilder("<html>");

        for (int i = 0; i < myMenuOptions.length; i++) {
            if (i == theHighlight) {
                builder.append(">").append(myMenuOptions[i]).append("<br>");
            } else {
                builder.append("&nbsp;&nbsp;&nbsp;").append(myMenuOptions[i]).append("<br>");
            }
        }

        myMenuLabel.setText(builder.toString());
        updateUI();
    }

    /**
     * Set current menu.
     */
    private void setNewMenu(final String[] theMenuOptions) {
        myMenuOptions = theMenuOptions;
        drawMenu(-1);
    }

    /**
     * Draw current menu choice.
     */
    private void setMenuChoice(final int theMenuChoice) {
        drawMenu(theMenuChoice);
    }

    /**
     * Place line of text in log.
     * @param theNewLine text to display.
     */
    private void updateTextLog(final String theNewLine) {
        myLogTextArea.setCaretPosition(myLogTextArea.getDocument().getLength());
        myLogTextArea.append(theNewLine);
        myLogTextArea.append(System.lineSeparator());
    }

    /**
     * Set current room as visible.
     * @param thePosition position of room.
     */
    private void setRoomVisible(final Point thePosition) {
        final JLabel lab = myMapLabels.get(thePosition);
        lab.setIcon(VISIBLE_ROOM_TILE);
    }

    /**
     * Set overlay contents of room.
     * @param thePosition room position.
     * @param theContent overlay contents.
     */
    private void setRoomContent(final Point thePosition, final String theContent) {
        final JLabel lab = myOverlayLabels.get(thePosition);
        switch (theContent) {
            case "MI" -> lab.setIcon(ROOM_CONTENT_BOTH);
            case "M" -> lab.setIcon(ROOM_CONTENT_MONSTER);
            case "I" -> lab.setIcon(ROOM_CONTENT_ITEM);
            case "" -> lab.setIcon(ROOM_CONTENT_NOTHING);
        }
    }

    /**
     * Called when player selects new game.
     * @param thePlayerPos starting position of player.
     */
    public void resetMap(final Point thePlayerPos) {
        for (int row = 0; row < myDungeonSize; row++) {
            for (int col = 0; col < myDungeonSize; col++) {
                JLabel lb = myMapLabels.get(new Point(row, col));
                lb.setIcon(FOG_ROOM_TILE);
                lb = myOverlayLabels.get(new Point(row, col));
                lb.setIcon(ROOM_CONTENT_NOTHING);
            }
        }

        placePlayer(thePlayerPos);
        updateUI();
    }

    /**
     * Listener for model state changes.
     * @param theEvt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvt) {
        //System.out.println("View: prop change");
        if (Dungeon.HERO_POS.equals(theEvt.getPropertyName())) {
            placePlayer((Point) theEvt.getNewValue());
        }
        if (DungeonController.MENU.equals(theEvt.getPropertyName())) {
            setNewMenu((String[]) theEvt.getNewValue());
        }
        if (DungeonController.MENU_POS.equals(theEvt.getPropertyName())) {
            setMenuChoice((int) theEvt.getNewValue());
        }
        if (Dungeon.TEXT_UPDATE.equals(theEvt.getPropertyName())) {
            updateTextLog((String) theEvt.getNewValue());
        }
        if (Dungeon.ROOM_VIS.equals(theEvt.getPropertyName())) {
            setRoomVisible((Point) theEvt.getNewValue());
        }
        if (Dungeon.ROOM_CONTENT.equals(theEvt.getPropertyName())) {
            setRoomContent((Point) theEvt.getOldValue(), (String) theEvt.getNewValue());
        }
        if (DungeonController.RESET_MAP.equals(theEvt.getPropertyName()))  {
            resetMap((Point) theEvt.getNewValue());
        }
    }

}
