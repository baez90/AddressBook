package BusinessLogic;

import Interfaces.IBlContacts;
import Interfaces.IContact;
import Interfaces.IContactList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by baez on 29.05.14.
 */
public class BlContacts implements IBlContacts {
    /**
     * Name des verwendeten Datenbank-Treibers
     */
    private String DriverName = "org.sqlite.JDBC";
    /**
     * Speichert den Pfad zur Datenbank-Datei ab
     */
    private String DbPath;

    /**
     * Kontakt in der DB updaten
     *
     * @param contact Kontakt welcher geupdatet werden soll
     * @return Erfolgs-Code
     */
    @Override
    public int updateContactInDB(IContact contact) {
        return 0;
    }

    @Override
    public int removeContactInDB(IContact contact) {
        return 0;
    }

    @Override
    public int createContactInDB(IContact contact) {
        return 0;
    }

    @Override
    public boolean initDB() {
        return false;
    }

    @Override
    public String getDbPath() {
        return DbPath;
    }

    @Override
    public void setDbPath(String dbPath) {
        DbPath = dbPath;
    }

    @Override
    public IContactList getContactsFromDB() {
        try {
            Class.forName(DriverName);
            Connection con = DriverManager.getConnection(DbPath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
