package Interfaces;

/**
 * Interface für die Kontaktliste
 *
 * @author baez
 */
public interface IContactList {
    /**
     * Liest alle Kontakte aus der Datenbank aus
     *
     * @return Liste von Contact-Objekten
     */
    public IContactList getContactsFromDB();

    /**
     * Sucht nach Kontakten in der Liste
     *
     * @param searchString String nach welchem gesucht werden soll
     * @return neue IContactList mit den Suchergebnissen
     */
    public IContactList searchContacts(String searchString);

    /**
     * Initialisiert die Datenbank mit der benötigten Struktur
     *
     * @return boolean ob Initialisierung erfolgreich war
     */
    public boolean initDB();
}
