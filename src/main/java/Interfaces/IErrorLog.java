package Interfaces;

import java.util.List;

/**
 * Fehlerklasse für die Analyse
 *
 * @author baez
 */
public interface IErrorLog extends List<IError> {
    /**
     * Fügt Fehler der Liste hin
     *
     * @param error Error welcher gespeichert werden soll
     */
    public void saveError(IError error);

    /**
     * Speichert alle Fehler aus der Liste in ein Logfile
     */
    public void saveAsLogFile(String filePath);
}
