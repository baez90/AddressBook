package Interfaces;

import Model.ContactNumberType;

import java.util.List;

/**
 * Interface für Rufnummer-Liste
 *
 * @author baez
 */
public interface IContactNumberList extends List<IContactNumber> {
    /**
     * Sucht in der Liste nach passenden Rufnummern
     *
     * @param searchNr String nach welchem in den Rufnummern gesucht werden soll
     * @return INumberList mit den passenden Rufnummern
     */
    public IContactNumberList searchNumber(String searchNr);

    /**
     * gibt eine gefilterte Liste von Rufnummern zurück
     *
     * @param type Typ auf welchen gefiltert werden soll
     * @return neue Liste mit gefilterten Ergebnissen
     */
    public IContactNumberList getNumbersByType(ContactNumberType type);
}
