package Interfaces;

import org.controlsfx.dialog.Dialogs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Fehlerklasse für die Analyse
 *
 * @author baez
 */
public interface IErrorLog {
    /**
     * Speichert Fehler als HTML-Tabellen-Zeile
     * HTML-Datei wird anschließend von der ErrorView geladen
     *
     * @param errorClass      Klasse in welcher der Fehler aufgetreten ist als String
     * @param errorDescrption Beschreibung des Fehlers als String
     * @param errorException  Exception to String
     */
    public static void saveError(String errorClass, String errorDescrption, String errorException) {
        try {
            File logFile = new File("errorLog.html");
            if (!logFile.exists()) {
                if (!logFile.createNewFile()) {
                    Dialogs.create().title("Warnung").masthead("Schreibrechte").message("Es konnte keine Errorlog-Datei angelegt werden, offenbar haben Sie keine ausreichenden Schreibrechte").showInformation();
                }
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true));
            bw.write("<tr><td>" + errorClass + "</td><td>" + errorDescrption + "</td><td>" + errorException + "</td></tr>");
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
