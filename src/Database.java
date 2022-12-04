import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteDataSource;

/**
 * This class sets up SQLite database. Database should be accessed by the Inventory and Hero.
 * Inventory should be accessed by the entire inventory and Hero.
 * Database is already created and it will be accessed by the database class.
 * 
 * @author Jered Wiegel, Hyunggil Woo
 * @version 1.2
 */
public class Database {


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
     * name and type cannot be null.
     * 
     * @param theName name of an item to add
     * @param theType Type of an item to add
     * 
     * Source from: https://www.sqlitetutorial.net/sqlite-java/insert/
     */
    public void insert(final String theName, final String theType) {
        if (theName == null || theType == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }

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
     * Selects all items stored in the table. I am not sure if data from both table gets selected.
     * name of the table cannot be null.
     * 
     * @param theTable name of the Table
     * Source: https://www.sqlitetutorial.net/sqlite-java/select/
     */
    public void selectAll(final String theTable) {
        if (theTable == null) {
            throw new IllegalArgumentException("Table name cannot be null");
        }

        // Need to check if the below query statement is correct.
        String query = "SELECT * FROM " + theTable;

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