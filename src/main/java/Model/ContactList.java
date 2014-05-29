package Model;

import Interfaces.IContact;
import Interfaces.IContactList;

import java.util.LinkedList;

/**
 * Repräsentiert Liste aller Kontakte
 *
 * @author baez
 */
public class ContactList extends LinkedList<IContact> implements IContactList {
    /**
     * Name des verwendeten Datenbank-Treibers
     */
    private String DriverName = "org.sqlite.JDBC";
    /**
     * Pfad zum SQLite Datenbank-Files
     */
    private String DbPath;

    /**
     * Liest alle Kontakte aus der DB
     *
     * @return Liste aller vorhandenen Kontakte in der Datenbank
     */
    @Override
    public IContactList getContactsFromDB() {
        //TODO
        return null;
    }

    /**
     * Sucht Kontakte in der aktuellen Liste
     * Sollte suche in Vorname, Nachname, Email-Adresse und aller Telefonnummern ermöglichen
     *
     * @param searchString String nach welchem gesucht werden soll
     * @return Liste der zum searchString passenden Elemente
     */
    @Override
    public IContactList searchContacts(String searchString) {
        //TODO
        return null;
    }

    /**
     * Initialisiert die Datenbank-struktur
     *
     * @return boolean ob erfolgreich initialisiert werden konnte
     */
    @Override
    public boolean initDB() {
        //TODO
        return false;
    }

    /**
     * Standard-Setter für Pfad zum Datenbank-File
     *
     * @param dbPath Absoluter Pfad zum Datenbank-File
     */
    public void setDbPath(String dbPath) {
        DbPath = dbPath;
    }
}
