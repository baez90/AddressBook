package BusinessLogic;

import Interfaces.IError;
import Interfaces.IErrorLog;
import Model.Error;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

/**
 * Implementierung des IErrorLogs
 * Implementierung als Singleton
 * Erweitert Liste
 *
 * @author baez
 */
public class ErrorLog extends LinkedList<IError> implements IErrorLog {

    /**
     * Instanz des Error-Logs für den Singleton
     */
    private static ErrorLog ourInstance = new ErrorLog();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    /**
     * privater Konstruktor für das Singleton
     */
    private ErrorLog() {
        super();
    }

    /**
     * Getter für den Singleton
     *
     * @return Instanz des ErrorLogs
     */
    public static ErrorLog getInstance() {
        return ourInstance;
    }

    /**
     * Fügt Fehler der Liste hin
     *
     * @param error Error welcher gespeichert werden soll
     */
    @Override
    public void saveError(IError error) {
        add(error);
    }

    /**
     * Speichert alle Fehler aus der Liste in ein Logfile
     */
    @Override
    public void saveAsLogFile(String filePath) {
        File logfile = new File(filePath);
        try {
            if (!logfile.exists()) {
                if (!logfile.createNewFile()) {
                    return;
                }
            }
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath, true));
            stream().forEach(e -> {
                try {
                    fileWriter.write(formatter.format(e.getErrorTime()) + "\t" + e.getErrorClass() + "\t" + e.getErrorMethod() + "\t" + e.getErrorClass() + "\t" + e.getErrorException());
                    fileWriter.newLine();
                } catch (IOException e1) {
                    saveError(new Error("ErrorLog", "saveAsLogFile", "Fehler beim schreiben der Datei", e1.toString(), e1.getStackTrace()));
                }
            });

            fileWriter.close();
        } catch (IOException e) {
            saveError(new Error("ErrorLog", "saveAsLogFile", "Fehler beim schreiben der Datei", e.toString(), e.getStackTrace()));
        }
    }
}
