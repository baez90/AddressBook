package Interfaces;

/**
 * Interface für Rufnummer-Liste
 *
 * @author baez
 */
public interface INumberList {
    /**
     * Sucht in der Liste nach passenden Rufnummern
     *
     * @param searchNr String nach welchem in den Rufnummern gesucht werden soll
     * @return INumberList mit den passenden Rufnummern
     */
    public INumberList searchNumber(String searchNr);
}
