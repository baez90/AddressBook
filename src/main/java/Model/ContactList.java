package Model;

import Interfaces.IContact;
import Interfaces.IContactList;

import java.util.Collection;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Repräsentiert Liste aller Kontakte
 * ist per Implementierung sortiert
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
        //TODO Kontakte in Liste suchen, Rufnummern können auch durchsucht werden!
        return null;
    }

    @Override
    public boolean add(IContact contact) {
        ListIterator listIterator = this.listIterator();
        while (listIterator.hasNext()){
            if(contact.compareTo((IContact)listIterator.next()) >= 0){
                listIterator.add(contact);
                break;
            }
        }
        return false;
    }

    /**
     * Fügt alle Elemente einer Collection der Liste hinzu
     * @param c Collection deren Elemente das Interface IContact implementiert
     * @return boolean ob erfolgreich alle Elemente hinzugefügt wurden
     */
    @Override
    public boolean addAll(Collection<? extends IContact> c) {
        c.forEach(this::add);
        return true;
    }
}
