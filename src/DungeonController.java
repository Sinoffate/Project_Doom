public class DungeonController implements ActionListener {
    private Dungeon myDungeon;
    private DungeonView myView;
    private DoomGuy myDoomGuy;
    private Monster myMonster;

    /**
     * Creates a new DungeonController object.
     *
     */
    public DungeonController () {
        myDoomGuy = new DoomGuy(100, 0.5, 0.5, 10, "DoomGuy", new Weapon(10, 0.8, 0.5, 10, "Pistol"));
        myDungeon = new Dungeon(5);
        myMonster = new Monster(100, 0.8, 0.5, 10, "Baron of Hell", new Weapon(10, 0.8, 0.5, 10, "Whip"));
        myView = new DungeonView(myDungeon, myDoomGuy, myMonster);
    }


    /**
     * @param theEvent
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("North")) {
            myDoomGuy.moveNorth();
        } else if (command.equals("South")) {
            myDoomGuy.moveSouth();
        } else if (command.equals("East")) {
            myDoomGuy.moveEast();
        } else if (command.equals("West")) {
            myDoomGuy.moveWest();
        } else if (command.equals("Attack")) {
            myDoomGuy.attack(myMonster);
        }
        myView.update();
    }

}

public static void main(String[] args) {
        DungeonController controller = new DungeonController();
        controller.myView.display();

}

