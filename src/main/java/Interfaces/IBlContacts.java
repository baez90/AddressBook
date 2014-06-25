package Interfaces;

/**
 * @author baez
 */
public interface IBlContacts {
    /**
     * Soll Kontakt in der Datenbank aktualisieren
     * @param contact IContact welcher geupdatet werden soll
     * @return int-Wert welcher verschiedene Erfolgs-Stati repräsentiert
     */
    public int updateContactInDB(IContact contact);

    /**
     * Entfernt Kontakt aus der Datanbank
     * @param contact IContact welcher entfernt werden soll
     * @return int-Wert welcher verschiedene Erfolgs-Stati repäsentiert
     */
    public int removeContactInDB(IContact contact);

    /**
     * erstellt Kontakt in der DB
     * sollte auch prüfen ob Kontakt bereits existiert
     * @param contact Kontakt welcher erstellt werden soll
     * @return int-Wert welcher verschiedene Erfolgs-Stati repräsentiert
     */
    public int createContactInDB(IContact contact);

    /**
     * Initialisiert die Datenbank mit der benötigten Struktur
     *
     * @return boolean ob Initialisierung erfolgreich war
     */
    public boolean initDB();

    /**
     * Getter für DbPath
     *
     * @return Pfad zur Datenbank-Datei
     */
    public String getDbPath();

    /**
     * Setter für DbPath
     *
     * @param dbPath Pfad zum Datenbank-File
     */
    public void setDbPath(String dbPath);

    /**
     * Liest alle Kontakte aus der Datenbank aus
     *
     * @return Liste von Contact-Objekten
     */
    public IContactList getContactsFromDB();
}