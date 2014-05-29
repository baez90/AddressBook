package Model;

/**
 * @author baez
 */
public interface IContact {
    /**
     * Soll Kontakt in der Datenbank aktualisieren
     * @return int-Wert welcher verschiedene Erfolgs-Stati repräsentiert
     */
    public int updateContactInDB();

    /**
     * Entfernt Kontakt aus der Datanbank
     * @return int-Wert welcher verschiedene Erfolgs-Stati repäsentiert
     */
    public int removeContactInDB();

    /**
     * erstellt Kontakt in der DB
     * sollte auch prüfen ob Kontakt bereits existiert
     * @return int-Wert welcher verschiedene Erfolgs-Stati repräsentiert
     */
    public int createContactInDB();
}
