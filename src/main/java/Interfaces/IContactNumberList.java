package Interfaces;

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
     * sortiert nach der Rufnummer
     *
     * @return sortierte Rufnummerliste
     */
    public IContactNumberList sort();

    /**
     * Gruppiert Liste nach Nummertyp
     *
     * @return gruppierte Rufnummerliste
     */
    public IContactNumberList groupByType();
}
