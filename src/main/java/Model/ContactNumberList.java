package Model;

import Interfaces.IContactNumber;
import Interfaces.IContactNumberList;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.NumberType;

import java.util.Collection;
import java.util.LinkedList;
import java.util.ListIterator;

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
     *
     * @param type Typ auf welchen gefiltert werden soll
     * @return neue Liste mit gefilterten Ergebnissen
     */
    @Override
    public IContactNumberList getNumbersByType(NumberType type) {
        return null;
    }

    /**
     * Fügt Elemente gruppiert nach Typ ein
     * @param cn IContactNumber welcher eingefügt werden soll
     * @return true
     */
    @Override
    public boolean add(IContactNumber cn) {
        //TODO durch comparable-Interface ersetzen
        if(isEmpty()){
            return super.add(cn);
        }
        ListIterator<IContactNumber> listIterator = listIterator();
        while (listIterator.hasNext()) {
            if (listIterator.next().getType().equals(cn.getType())) {
                listIterator.add(cn);
                return true;
            }
        }
       super.addLast(cn);
        return true;

    }

    @Override
    public boolean addAll(Collection<? extends IContactNumber> c) {
        c.forEach(this::add);
        return false;
    }


}
