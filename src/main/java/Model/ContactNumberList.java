package Model;

import Interfaces.IContactNumber;
import Interfaces.IContactNumberList;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.NumberType;

import java.util.Collection;
import java.util.LinkedList;

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
        return null;
    }

    /**
     * gibt eine gefilterte Liste von Rufnummern zurück
     * @param type Typ auf welchen gefiltert werden soll
     * @return neue Liste mit gefilterten Ergebnissen
     */
    @Override
    public IContactNumberList getNumbersByType(NumberType type) {
        return null;
    }

    @Override
    public boolean add(IContactNumber cn) {
        //TODO implement
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends IContactNumber> c) {
        //TODO implement
        return false;
    }


}
