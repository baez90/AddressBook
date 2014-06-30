import BusinessLogic.ErrorLog;
import Interfaces.IErrorLog;
import Model.Error;
import junit.framework.TestCase;

import java.io.*;
import java.util.logging.Logger;

/**
 * @author Baez
 */
public class TestErrorLog extends TestCase {

    private static Logger logger = Logger.getLogger(IErrorLog.class.getName());
    IErrorLog errorLog = ErrorLog.getInstance();

    public void setUp() {

        errorLog.saveError(new Error("TestErrorLog", "setUp", "testError", "exception", null));
        errorLog.saveAsLogFile("logfile.txt");
    }

    public void testErrorLog() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("logfile.txt"));
            String s = br.readLine();
            assertTrue(s.contains("TestErrorLog"));
            assertTrue(s.contains("setUp"));
            assertTrue(s.contains("exception"));

            br.close();
            new File("logfile.txt").deleteOnExit();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
