import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteDataSource;

/**
 * This class sets up SQLite database. Database should be accessed by the Inventory and Hero.
 * Inventory should be accessed by the entire inventory and Hero.
 * 
 * @author Jered Wiegel, Hyunggil Woo
 * @version 1.1
 */
public class Database {

    //TODO: a
    private SQLiteDataSource myDs = null;

    /**
     * Constructor for Database.
     * Database will contain non-null references.
     */
    public Database() {
        try {
            myDs = new SQLiteDataSource();
            myDs.setUrl("jdbc:sqlite:Project_Doom.db");
        } catch (final Exception theEvent) {
            theEvent.printStackTrace();
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    /**
     * This method inserts data into the tables.
     * This method may incremet the count of items if they are the same item
     *
     * @param theName name of an item to add
     * @param theType Type of an item to add
     * 
     * Source from: https://www.sqlitetutorial.net/sqlite-java/insert/
     */
    public void insert(final String theName, final String theType) {

        String query = "INSERT INTO inventory (NAME, TYPE) VALUES (?, ?)";

        try (Connection connection = myDs.getConnection();
             PreparedStatement prepStatement = connection.prepareStatement(query)) {
                
            prepStatement.setString(1, theName);
            prepStatement.setString(2, theType);
            prepStatement.executeUpdate();

        } catch (final SQLException theEvent) {
            theEvent.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Updates values in the corresponding database.
     * 
     * @param theName
     * @param theType
     */
    public void update(final String theName, final String theType) {

        //TODO: Check if the below query statement is correct

        String query = "UPDATE inventory SET NAME = ? , " +
                        "VALUE = ?";

        try (Connection connection = myDs.getConnection();
             PreparedStatement prepStatement = connection.prepareStatement(query)) {

            // sets the corresponding values
            prepStatement.setString(1, theName);
            prepStatement.setString(2, theType);

            prepStatement.executeUpdate();

        } catch (final SQLException theEvent) {
            theEvent.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Removes an item from the inventory
     * @param theItem
     * 
     * https://www.sqlitetutorial.net/sqlite-java/delete/
     */
    public void delete(final String theItem) {

        String query = "DELETE FROM inventory WHERE NAME = ?";

        try (Connection connection = myDs.getConnection();
                PreparedStatement prepStatement = connection.prepareStatement(query)) {

                prepStatement.setString(1, theItem);

                prepStatement.executeUpdate();
                
        } catch (final SQLException theEvent) {
            theEvent.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Resets all items in the inventory
     */
    public void reset() {

    }

    /**
     * Selects all items stored in the table. I am not sure if data from both table gets selected.
     * 
     * Source: https://www.sqlitetutorial.net/sqlite-java/select/
     */
    public void selectAll() {

        // Need to check if the below query statement is correct.
        String query = "SELECT * FROM inventory";

        try (Connection connection = myDs.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query);) {
            
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("ID") + "\t" +
                                    "Name: " + rs.getString("NAME") + "\t" +
                                    "Type: " + rs.getString("TYPE"));
            }
        } catch (final SQLException theEvent) {
            theEvent.printStackTrace();
            System.exit(0);
        }
    }


}