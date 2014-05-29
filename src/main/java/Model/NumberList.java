package Model;

import Interfaces.INumberList;

import java.util.LinkedList;

/**
 * Repräsentiert Liste aller Nummern eines Kontakts
 *
 * @author baez
 */
public class NumberList extends LinkedList<ContactNumber> implements INumberList {
    /**
     * Sucht nach Rufnummern in der Liste
     *
     * @param searchNr String nach welchem in den Rufnummern gesucht werden soll
     * @return INumberList der Ergebnisse
     */
    @Override
    public INumberList searchNumber(String searchNr) {
        return null;
    }
}
