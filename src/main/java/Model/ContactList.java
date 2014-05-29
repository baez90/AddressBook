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
     * Sucht Kontakte in der aktuellen Liste
     * Sollte suche in Vorname, Nachname, Email-Adresse und aller Telefonnummern ermöglichen
     *
     * @param searchString String nach welchem gesucht werden soll
     * @return Liste der zum searchString passenden Elemente
     */
    @Override
    public IContactList searchContacts(String searchString) {
        //TODO Kontakte in Liste suchen, Rufnummern können auch durchsucht werden
        return null;
    }
}
