import Interfaces.IFileEncryption;
import junit.framework.TestCase;

import java.io.*;
import java.nio.charset.Charset;
import java.util.logging.Logger;

/**
 * Created by baez on 29.05.14.
 */
public class TestEncryption extends TestCase {

    private static final String validCharacters = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ!§$%&/()=?";
    private static Logger logger = Logger.getLogger(IFileEncryption.class.getName());
    private File sourceFile = new File("test.txt");
    private File encryptedFile = new File("test.encrypted");
    private File decryptedFile = new File("test.decrypted");
    private String password = "";

    public void setUp() {

        while (password.length() < 16 && password.length() != 16) {
            int random = ((int) (Math.random() * validCharacters.length()));
            password += validCharacters.charAt(random);
        }

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
        IFileEncryption.encryptFile(password, sourceFile.getPath(), encryptedFile.getPath());
        IFileEncryption.decryptFile(password, encryptedFile.getPath(), decryptedFile.getPath());

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
