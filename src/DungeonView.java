import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DungeonView implements PropertyChangeListener {

    private final int myDungeonSize;
    private static final String CEILING_TILE = "-";
    private static final String WALL_TILE = "|";

    public DungeonView(final int theSize, final Point thePlayer) {
        myDungeonSize = theSize;
    }

    /*
-------
|P| | |
-------
| | | |
-------
| | | |
-------
 */
    private void drawMap(final Point thePlayer) {
        for (int i = 0; i < myDungeonSize; i++) {
            System.out.println(CEILING_TILE.repeat(myDungeonSize*2+1));
            for (int j = 0; j < myDungeonSize; j++) {
                System.out.print(WALL_TILE);
                if (new Point(i,j).equals(thePlayer)) {
                    System.out.print('P');
                } else {
                    System.out.print(' ');
                }
            }
            System.out.print(WALL_TILE);
        }
        System.out.println(CEILING_TILE.repeat(myDungeonSize*2+1));
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvt) {
        if ("HERO_POS".equals(theEvt.getPropertyName())) {
            drawMap((Point)theEvt.getNewValue());
        }
    }
}
