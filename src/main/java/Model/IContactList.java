package Model;

/**
 * Created by baez on 29.05.14.
 */
public interface IContactList {
    /**
     * Liest alle Kontakte aus der Datenbank aus
     * @return Liste von Contact-Objekten
     */
    public IContactList getContactsFromDB();
}
