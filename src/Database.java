import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.sqlite.SQLiteDataSource;

/**
 * This class sets up SQLite database.
 * @author Jered Wiegel
 * @version 1.0
 */
public class Database {

    private SQLiteDataSource ds = null;

    /**
     * Constructor for Database.
     */
    public Database() {
        try {
            ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:Project_Doom.db");
        } catch (final Exception theEvent) {
            theEvent.printStackTrace();
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    /**
     * This method creates the tables in the database.
     */
    public void createTables() {
        String query = "CREATE TABLE IF NOT EXISTS inventory ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT NOT NULL, " +
                "TYPE TEXT NOT NULL )";

        String query2 = "CREATE TABLE IF NOT EXISTS player ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT NOT NULL, " +
                "HEALTH INTEGER NOT NULL, " +
                "INVENTORY_ID INTEGER NOT NULL, " +
                "FOREIGN KEY (INVENTORY_ID) REFERENCES inventory(ID) )";


        try (Connection connection = ds.getConnection();
             Statement statement = connection.createStatement()) {
            int rv = statement.executeUpdate(query);
            int rv2 = statement.executeUpdate(query2);
            System.out.println("executeUpdate() returned " + rv);
            System.out.println("executeUpdate() returned " + rv2);
        } catch (final SQLException theEvent) {
            theEvent.printStackTrace();
            System.exit(0);
        }
        System.out.println("Created inventory table successfully");
    }

    /**
     * This method inserts data into the tables.
     */
    public void insertData() {
        System.out.println("Attempting to insert two rows into inventory table");
        String query = "INSERT INTO inventory (NAME, TYPE) VALUES ('Sword', 'Weapon')";
        try (Connection connection = ds.getConnection();
             Statement statement = connection.createStatement()) {
            int rv = statement.executeUpdate(query);
            System.out.println("executeUpdate() returned " + rv);
        } catch (final SQLException theEvent) {
            theEvent.printStackTrace();
            System.exit(0);
        }
        System.out.println("Inserted data into inventory table successfully");
    }

    /**
     * This method selects all data from the tables.
     */
    public void selectData() {
        System.out.println("Selecting all rows from inventory table");
        String query = "SELECT * FROM inventory";
        try (Connection connection = ds.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("ID"));
                System.out.println("Name: " + rs.getString("NAME"));
                System.out.println("Type: " + rs.getString("TYPE"));
            }
        } catch (final SQLException theEvent) {
            theEvent.printStackTrace();
            System.exit(0);
        }
        System.out.println("Selected all data from inventory table successfully");
    }


}