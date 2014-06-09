package Interfaces;

/**
 * Created by baez on 29.05.14.
 */
public interface IBlContacts {
    /**
     * Soll Kontakt in der Datenbank aktualisieren
     *
     * @return int-Wert welcher verschiedene Erfolgs-Stati repräsentiert
     */
    public int updateContactInDB(IContact contact);

    /**
     * Entfernt Kontakt aus der Datanbank
     *
     * @return int-Wert welcher verschiedene Erfolgs-Stati repäsentiert
     */
    public int removeContactInDB(IContact contact);

    /**
     * erstellt Kontakt in der DB
     * sollte auch prüfen ob Kontakt bereits existiert
     *
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

    public void ExecuteQuery(String query);
}
