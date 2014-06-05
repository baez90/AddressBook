import Interfaces.IErrorLog;
import junit.framework.TestCase;

import java.io.*;
import java.util.logging.Logger;

/**
 * @author baez
 */
public class TestErrorLog extends TestCase {

    private static Logger logger = Logger.getLogger(IErrorLog.class.getName());

    File errorLogFile = new File("errorLog.html");

    public void setUp() {
        IErrorLog.saveError("TestErrorLog", "erzeugt Logfile zu Test-Zwecken", "none");
    }

    public void testIErrorLog() {
        try {
            BufferedReader bw = new BufferedReader(new FileReader(errorLogFile));
            String input = bw.readLine();
            bw.close();
            assertEquals("<tr><td>TestErrorLog</td><td>erzeugt Logfile zu Test-Zwecken</td><td>none</td></tr>", input);
            logger.info("Error erfolgreich geschrieben");

            if (errorLogFile.exists()) {
                errorLogFile.delete();
            }
        } catch (FileNotFoundException e) {
            logger.info("Datei nicht gefunden");
            e.printStackTrace();
        } catch (IOException e) {
            logger.info("Datei konnte nicht gelesen werden");
            e.printStackTrace();
        }
    }

}
