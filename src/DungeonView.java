import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
temp, documentation links;
https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
 */

/**
 * GUI based view for Dungeons.
 * @author james deal
 * @version 0.2
 */
public class DungeonView extends JPanel implements PropertyChangeListener {

    @Serial
    private static final long serialVersionUID = 4L;

    /** Image used for top/bottom of room display. */
    private static ImageIcon ROOM_TILE;
    /** Image used for player in room display. */
    private static ImageIcon PLAYER_TILE;

    /** Size of dungeon to display. */
    private final int myDungeonSize;

    /** GUI Map Panel. */
    private JPanel myMapPanel;
    /** GUI GridBag Constraints. */
    private GridBagConstraints myGBC;
    /** GUI collection of Map's Room Labels. */
    private Map<Point, JLabel> myMapLabels;
    /** GUI Map's Player Label. */
    private JLabel myPlayerLabel;

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
        BufferedImage roomImage = null;
        BufferedImage playerImage = null;

        try {
            roomImage = ImageIO.read(new File("fellcleave75.png"));
            playerImage = ImageIO.read(new File("emote75.png"));
        } catch (final IOException ioe) {
            System.out.println("Unable to fetch image.");
            ioe.printStackTrace();
        }

        assert roomImage != null;
        ROOM_TILE = new ImageIcon(roomImage);
        assert playerImage != null;
        PLAYER_TILE = new ImageIcon(playerImage);
    }

    /**
     * Initial setup of GUI.
     */
    private void setupComponents() {
        setLayout(new BorderLayout());

        //temp so we understand where things go
        add(new JLabel("Doomguy stats: Idk prob dead"), BorderLayout.NORTH);
        add(new JLabel("Bite me"), BorderLayout.SOUTH);
        add(new JLabel("Menu Options: Die"), BorderLayout.EAST);

        //setup dungeon map component
        myMapPanel = new JPanel();  //panel itself
        myMapPanel.setLayout(new GridBagLayout());  //grid for inside the panel
        myGBC = new GridBagConstraints();   //ability to use grid effectively
        add(myMapPanel, BorderLayout.WEST); //put grid in the panel
        myMapLabels = new HashMap<>();   //ability to reference grid-objects

        //Setup player label.
        myPlayerLabel = new JLabel(PLAYER_TILE);

        //fill grid with rooms
        JLabel temp;
        for (int i = 0; i < myDungeonSize; i++) {
            for (int j = 0; j < myDungeonSize; j++) {
                temp = new JLabel(ROOM_TILE);
                myMapLabels.put(new Point(i, j), temp);
                myGBC.gridx = i;
                myGBC.gridy = j;
                myMapPanel.add(temp, myGBC);
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
        myGBC.gridx = thePosition.x;
        myGBC.gridy = thePosition.y;
        myMapPanel.add(myPlayerLabel, myGBC);
        myMapPanel.add(myMapLabels.get(thePosition), myGBC);
    }

    /**
     * Listener for model state changes.
     * @param theEvt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvt) {
        if (Dungeon.HERO_POS.equals(theEvt.getPropertyName())) {
            placePlayer((Point) theEvt.getNewValue());
        }
    }

}
