package Model;

import Interfaces.IContactNumber;
import Interfaces.IContactNumberList;

import java.util.LinkedList;

/**
 * Repräsentiert Liste aller Nummern eines Kontakts
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
        return null;
    }

    /**
     * Sortiert Liste nach Rufnummer
     *
     * @return neue sortierte Rufnummerliste
     */
    @Override
    public IContactNumberList sort() {
        return null;
    }

    /**
     * Gruppiert Liste nach Nummern-Typ für die Anzeige
     *
     * @return neue gruppierte Rufnummernliste
     */
    @Override
    public IContactNumberList groupByType() {
        return null;
    }
}
