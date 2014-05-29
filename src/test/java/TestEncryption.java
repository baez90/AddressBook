import Interfaces.IEncryption;
import junit.framework.TestCase;

import java.io.*;
import java.nio.charset.Charset;
import java.util.logging.Logger;

/**
 * Created by baez on 29.05.14.
 */
public class TestEncryption extends TestCase {

    private static Logger logger = Logger.getLogger(IEncryption.class.getName());
    private File sourceFile = new File("test.txt");
    private File encryptedFile = new File("test.encrypted");
    private File decryptedFile = new File("test.decrypted");

    public void setUp() {
        try {
            sourceFile.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(sourceFile);
            outputStream.write("Hello World".getBytes());
            outputStream.close();
            logger.info("Test-Datei angelegt");
        } catch (Exception e) {
            logger.info("Excepion aufgetreten " + e.toString());
            e.printStackTrace();
        }
    }

    public void testEncryptionDecryption() {
        IEncryption.encryptFile("])nRL.JhG2>P>:ap", sourceFile.getPath(), encryptedFile.getPath());
        IEncryption.decryptFile("])nRL.JhG2>P>:ap", encryptedFile.getPath(), decryptedFile.getPath());

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(decryptedFile), Charset.forName("UTF-8")));
            String temp = bufferedReader.readLine();
            bufferedReader.close();
            assertEquals(temp, "Hello World");
            logger.info("Datei erfolgreich ver- und entschlüsselt");
            /*
            Dateien wieder löschen
             */
            sourceFile.delete();
            encryptedFile.delete();
            decryptedFile.delete();

        } catch (Exception e) {
            logger.info("Excepion aufgetreten " + e.toString());
            e.printStackTrace();
        }
    }
}
