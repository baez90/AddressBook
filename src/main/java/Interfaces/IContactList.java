package Interfaces;

import java.util.List;

/**
 * Interface für die Kontaktliste
 *
 * @author baez
 */
public interface IContactList extends List<IContact> {

    /**
     * Sucht nach Kontakten in der Liste
     *
     * @param searchString String nach welchem gesucht werden soll
     * @return neue IContactList mit den Suchergebnissen
     */
    public IContactList searchContacts(String searchString);
}
