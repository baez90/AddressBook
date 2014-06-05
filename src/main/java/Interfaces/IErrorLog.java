package Interfaces;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by baez on 04.06.14.
 */
public interface IErrorLog {
    public static void saveError(String errorClass, String errorDescrption, String errorException) {
        try {
            File logFile = new File("errorLog.html");
            if (!logFile.exists()) {
                logFile.createNewFile();
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
