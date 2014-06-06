package BusinessLogic;

import Interfaces.IBlContacts;
import Interfaces.IContact;
import Interfaces.IContactList;

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
     * @param dbPath  Pfad zur Datenbank-Datei
     * @param contact Kontakt welcher geupdatet werden soll
     * @return Erfolgs-Code
     */
    @Override
    public int updateContactInDB(String dbPath, IContact contact) {
        return 0;
    }

    /**
     * Entfernt Kontakt in der Datenbank
     * @param dbPath Pfad zur Datenbank-Datei
     * @param contact Kontakt welcher gelöscht werden soll
     * @return Erfolgs-Code
     */
    @Override
    public int removeContactInDB(String dbPath, IContact contact) {
        return 0;
    }

    @Override
    public int createContactInDB(String dbPath, IContact contact) {
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
        return null;
    }
}
