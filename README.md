# Project_Doom
This project was created by James Deal, Jered Wiegel, and Hyunggil Woo for TCSS 360 Software Development And Quality Assurance Techniques. The goal of the project was to enable collaborative development in a controlled setting while implementing concepts learned within the class.

## How to play
- WASD keys are used for movement.
- E is used to select menu options and to loot rooms.
- Q is used to attack monsters.
- While on the map or in combat you can hold SHIFT to access your weapons.
  - WASD is used to select a weapon.
- While on the map or in combat you can hold ALT to access your potions.
  - WASD is used to select a potion.
- While on the map you can press ESC for the main menu.
- Find all four pillars of Object-Oriented Programming to be able to exit the dungeon and receive your reward! But watch out, the exit is guarded by a fierce beast!

## Tools we used in this project
- Pivotal Tracker: Agile Style Management
  - Used to generate user stories and their point values
  - Assign tasks to team members
    - Track changes for each task using GitHub integration
- Toggl: Time Tracking
  - Used to track mock billable hours for the project
- GitHub
  - Used for remote version control
  - Utilized protected branches to simulate industry workflow
  - Generated PRs for each addition of every feature
- Discord
  - Used to facilitate communication between team members
- Adobe Photoshop
  - Used to resize images that were used in the project
- app.diagrams.net
  - Used to develop the UML diagram for the initial implementation
  - Updated to reflect changes within the project
- JUnit 5
  - Implemented to test independent functionality of classes to validate correct/expected output
- SQLite
  - Databases used for Item stat lines.
- DB Browser
  - Creating and adjusting SQLite database files.

## Design Patterns & Features
- Singleton: DiceRoll, Database
- Mock: Inventory (initial iteration)
- MVC: DungeonView, DungeonController
- Observer: PropertyChangeListeners
- Serializable: Save/Load functionality

## Known Bugs and "Features"
- Holding down movement key to move multiple spaces will skip combat in spaces passed. A fix is implemented in the model for this, but we like this bug, so it is a feature now.
- Sometimes a keypress will throw an exception and not fire properly. Assumed cause is large keyPressed method. Does not impact functionality enough to be considered for fix.
- Window has to be clicked first for Shift key to work on Windows, because Windows is silly.
- View imports Controller/Model, this is only for names of PCL fields. Likely remedy is a more structured PCL implementation.