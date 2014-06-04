package BusinessLogic;

import Interfaces.IErrorLog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by baez on 04.06.14.
 */
public class ErrorLog implements IErrorLog {

    private String ErrorClass;
    private String ErrorDescription;
    private String ErrorException;

    public ErrorLog(String errorClass, String errorDescription, String errorException) {
        ErrorClass = errorClass;
        ErrorDescription = errorDescription;
        ErrorException = errorException;
    }

    @Override
    public void saveError() {
        try {
            File logFile = new File("errorLog.html");
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true));
            StringBuffer sb = new StringBuffer();
            sb.append("<tr><td> ").append(ErrorClass).append("</td><td>").append(ErrorDescription).append("</td><td>").append(ErrorException).append("</td></tr>");
            bw.write(sb.toString());
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
