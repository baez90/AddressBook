package Model;

import Interfaces.IContactNumber;
import Interfaces.IContactNumberList;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Repräsentiert Liste aller Nummern eines Kontakts
 * ist per Implementierung gruppiert
 *
 * @author baez
 */
public class ContactNumberList extends LinkedList<IContactNumber> implements IContactNumberList {
    /**
     * Sucht nach Rufnummern in der Liste
     *
     * @param searchNr String nach welchem in den Rufnummern gesucht werden soll
     * @return INumberList der Ergebnisse
     */
    @Override
    public IContactNumberList searchNumber(String searchNr) {
        return this.stream().filter(ic -> ic.getNumber().contains(searchNr)).collect(Collectors.toCollection(ContactNumberList::new));
    }

    /**
     * gibt eine gefilterte Liste von Rufnummern zurück
     *
     * @param type Typ auf welchen gefiltert werden soll
     * @return neue Liste mit gefilterten Ergebnissen
     */
    @Override
    public IContactNumberList getNumbersByType(ContactNumberType type) {
        return this.stream().filter(ic -> ic.getType().equals(type)).collect(Collectors.toCollection(ContactNumberList::new));
    }

    /**
     * Fügt Elemente gruppiert nach Typ ein
     *
     * @param cn IContactNumber welcher eingefügt werden soll
     * @return true
     */
    @Override
    public boolean add(IContactNumber cn) {
        int insertionPoint = Collections.binarySearch(this, cn, (o1, o2) -> o1.compareTo(o2));
        super.add((insertionPoint > -1) ? insertionPoint : (-insertionPoint) - 1, cn);
        return true;

    }

    /**
     * Überschreibung der Methode der LinkedList
     * Fügt alle Elemente einer Collection zu dieser Liste hinzu
     *
     * @param c Collection welche zur Liste hinzugefügt werden soll
     * @return boolsches Ergebnis
     */
    @Override
    public boolean addAll(Collection<? extends IContactNumber> c) {
        c.forEach(this::add);
        return false;
    }


}
