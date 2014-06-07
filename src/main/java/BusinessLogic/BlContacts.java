package BusinessLogic;

import Interfaces.IBlContacts;
import Interfaces.IContact;
import Interfaces.IContactList;

/**
 * Klasse für die Interaktion mit dem SQLite-DB-File
 * @author Baez
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

    /**
     * Entfernt Kontakt in der Datenbank
     * @param contact Kontakt welcher gelöscht werden soll
     * @return Erfolgs-Code
     */
    @Override
    public int removeContactInDB(IContact contact) {
        return 0;
    }

    /**
     * legt Kontakt in der DB an
     *
     * @param contact neuer Kontakt welcher angelegt werden soll
     * @return Erfolgs-Code
     */
    @Override
    public int createContactInDB(IContact contact) {
        return 0;
    }

    /**
     * Initialisiert die DB, legt Tabellen an usw
     * @return boolean ob alles erfolgreich angelegt werden konnte
     */
    @Override
    public boolean initDB() {
        return false;
    }

    /**
     * Standard-Getter
     * @return Pfad zur DB-Datei
     */
    @Override
    public String getDbPath() {
        return DbPath;
    }

    /**
     * Standard-Setter
     * @param dbPath Pfad zum Datenbank-File
     */
    @Override
    public void setDbPath(String dbPath) {
        DbPath = dbPath;
    }

    /**
     * Liest alle Kontakte aus der DB aus
     * @return Liste aller Kontakte
     */
    @Override
    public IContactList getContactsFromDB() {
        return null;
    }
}
