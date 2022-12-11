package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.sqlite.SQLiteDataSource;

/**
 * This class sets up SQLite database.
 * Items table and Weapons table are already created, and they will be
 * accessed by this database class.
 * 
 * @author Jered Wiegel, Hyunggil Woo
 * @version 1.2
 */
public final class Database {

    /** Returns an instance of this object. Singleton. */
    private static final Database INSTANCE = new Database();
    /** The database we have. */
    private SQLiteDataSource myDs;

    /**
     * This accesses a databases that are already created in the source file.
     * Database will contain non-null references.
     */
    private Database() {
        try {
            myDs = new SQLiteDataSource();
            myDs.setUrl("jdbc:sqlite:src/model/Project_Doom.db");
        } catch (final Exception event) {
            event.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Returns an instance of this object.
     *
     * @return Database object.
     */
    public static Database getInstance() {
        return INSTANCE;
    }

    /**
     * Selects specific item from specific table and returns associated values.
     * @param theTable name of the Table.
     * @param theEntry name of the item.
     * Source: <a href="https://www.sqlitetutorial.net/sqlite-java/select/">Hello</a>
     */
    public String selectOne(final String theTable, final String theEntry) {
        if (theTable == null) {
            throw new IllegalArgumentException("Null table name");
        }
        if (!"Items".equals(theTable) && !"Weapons".equals(theTable)) {
            throw new IllegalArgumentException("Bad table name: " + theTable);
        }

        final String query = "SELECT * FROM " + theTable + " WHERE Name = '" + theEntry + "'";
        final StringBuilder sb = new StringBuilder();

        try (Connection connection = myDs.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            if ("Items".equals(theTable)) {
                final String res = rs.getString("Value");
                sb.append(res);
            }
            if ("Weapons".equals(theTable)) {
                String res = rs.getString("Damage");
                sb.append(res).append(",");
                res = rs.getString("FireRate");
                sb.append(res).append(",");
                res = rs.getString("Accuracy");
                sb.append(res).append(",");
                res = rs.getString("Ammo");
                sb.append(res);

            }
        } catch (final SQLException event) {
            event.printStackTrace();
            System.exit(0);
        }
        return sb.toString();
    }
}