package Model;

import Interfaces.IContact;
import Interfaces.IContactList;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.Collectors;

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
        //TODO Rufnummern berücksichtigen
        return this.stream().filter(i -> i.getFirstName().toLowerCase().equals(searchString.toLowerCase()) || i.getLastName().toLowerCase().equals(searchString.toLowerCase())).collect(Collectors.toCollection(ContactList::new));
    }

    /**
     * Fügt Parameter automatisch sortiert ein
     * @param contact Kontakt welcher eingefügt werden soll
     * @return boolean ob erfolgreich eingefügt wurde
     */
    @Override
    public boolean add(IContact contact) {
        int insertionPoint = Collections.binarySearch(this, contact, (o1, o2) -> o1.compareTo(o2));
        super.add((insertionPoint > -1) ? insertionPoint : (-insertionPoint) - 1, contact);
        return true;
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
