import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Console based view for Dungeons.
 * @author james deal
 * @version 0.1
 */
public class DungeonView implements PropertyChangeListener {

    /** Character used for top/bottom of room display. */
    private static final String CEILING_TILE = "-";
    /** Character used for sides of room display. */
    private static final String WALL_TILE = "|";
    /** Character used for player in room display. */
    private static final char PLAYER_TILE = 'P';
    /** Character used for empty room in room display. */
    private static final char EMPTY_TILE = ' ';

    /** Size of dungeon to display. */
    private final int myDungeonSize;

    /**
     * Default constructor to ready the view.
     * @param theSize dimension of square dungeon.
     * @param thePlayer starting position of the player.
     */
    public DungeonView(final int theSize, final Point thePlayer) {
        myDungeonSize = theSize;
        drawMap(thePlayer);
    }

    /* Example 3x3 with Player at 0,0
        -------
        |P| | |
        -------
        | | | |
        -------
        | | | |
        -------
    */
    /**
     * Draws map containing player position.
     * @param thePlayer position of player.
     */
    private void drawMap(final Point thePlayer) {
        clearScreen();

        for (int row = 0; row < myDungeonSize; row++) {
            System.out.println(CEILING_TILE.repeat(myDungeonSize * 2 + 1));

            for (int col = 0; col < myDungeonSize; col++) {
                System.out.print(WALL_TILE);
                if (new Point(row, col).equals(thePlayer)) {
                    System.out.print(PLAYER_TILE);
                } else {
                    System.out.print(EMPTY_TILE);
                }
            }
            System.out.print(WALL_TILE + "\n");
        }

        System.out.println(CEILING_TILE.repeat(myDungeonSize * 2 + 1));
    }

    /**
     * Listener for model state changes.
     * @param theEvt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvt) {
        if (Dungeon.HERO_POS.equals(theEvt.getPropertyName())) {
            drawMap((Point) theEvt.getNewValue());
        }
    }

    //utilities

    /**
     * Clears the terminal by invoking the environment's clear command.
     * Differs between Windows and Unix
     * <p>
     * <a href="https://stackoverflow.com/questions/2979383/java-clear-the-console">Dealing with OS's</a>
     * <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/System.html#getenv-java.lang.String-">What is Term?</a>
     */
    public static void clearScreen() {
        try { //windows
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (final Exception e) { //mac and linux
            try {
                final String term = System.getenv("TERM");
                if (term != null && !"dumb".equals(term)) {
                    new ProcessBuilder("clear").inheritIO().start().waitFor();
                }
            } catch (final Exception ignored) {
                //guess we don't clear the screen today, oh well
            }
        }
    }
}
