package Interfaces;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * Interface zur Ver- und Entschlüsselung von Dateien
 *
 * @author baez
 */
public interface IEncryption {

    /**
     * Verschlüsselt Datei
     *
     * @param password   Passwort für die Verschlüsselung
     * @param sourcePath Pfad zur Datei welche verschlüsselt werden soll
     * @param targetPath Ziepfad wo verschlüsselte Datei abgelegt werden soll
     * @return boolean ob Datei erfolgreich verschlüsselt werden konnte
     */
    public static boolean encryptFile(String password, String sourcePath, String targetPath) {
        File sourceFile = new File(sourcePath);
        File targetFile = new File(targetPath);
        FileInputStream fis;
        FileOutputStream fos;
        CipherInputStream cis;

        if (password.length() > 15) {
            password = password.substring(0, 15);
        } else {
            while (password.length() < 16) {
                password += "0";
            }
        }

        SecretKeySpec secretKey = new SecretKeySpec(password.getBytes(), "AES");
        try {
            Cipher encrypt = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
            encrypt.init(Cipher.ENCRYPT_MODE, secretKey);
            fis = new FileInputStream(sourceFile);
            cis = new CipherInputStream(fis, encrypt);
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }

            fos = new FileOutputStream(targetFile);
            byte[] bTemp = new byte[8];
            int iTemp = cis.read(bTemp);
            while (iTemp != -1) {
                fos.write(bTemp, 0, iTemp);
                iTemp = cis.read(bTemp);
            }

            fos.flush();
            fos.close();
            cis.close();
            fis.close();
            return true;
        } catch (NoSuchAlgorithmException e) {
            //TODO Fehler loggen
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            //TODO Fehler loggen
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            //TODO Fehler loggen
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            //TODO Fehler loggen
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            //TODO Fehler loggen
            e.printStackTrace();
        } catch (IOException e) {
            //TODO Fehler loggen
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Entschlüsselt Datei
     *
     * @param password   Passwort für die Entschlüsselung
     * @param sourcePath Pfad zur Datei welche entschlüsselt werden soll
     * @param targetPath Ziepfad wo entschlüsselte Datei abgelegt werden soll
     * @return boolean ob Entschlüsselung erfolgreich war
     */
    public static boolean decryptFile(String password, String sourcePath, String targetPath) {
        /*
        Dateien für die Input- und OutputStreams
         */
        File sourceFile = new File(sourcePath);
        File targetFile = new File(targetPath);

        /*
        Input-, Output- und CipherStream Objekte zum lesen, schreiben und entschlüsseln von Dateien
         */
        FileInputStream fis;
        FileOutputStream fos;
        CipherInputStream cis;

        /*
        Passwort-Länge für AES anpassen
         */
        if (password.length() > 15) {
            password = password.substring(0, 15);
        } else {
            while (password.length() < 16) {
                password += "0";
            }
        }

    /*
    Key anlegen
     */
        SecretKeySpec secretKey = new SecretKeySpec(password.getBytes(), "AES");
        try {
            /*
            Cipher-Modus festlegen
             */
            Cipher decrypt = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
            decrypt.init(Cipher.DECRYPT_MODE, secretKey);
            /*
            Datei lesen -> byte-Array entschlüsseln -> Ergebnis schreiben
             */
            fis = new FileInputStream(sourceFile);
            cis = new CipherInputStream(fis, decrypt);
            /*
            Bei Bedarf Ziel-Datei anlegen
             */
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
            fos = new FileOutputStream(targetFile);

            byte[] bTemp = new byte[8];
            int iTemp = cis.read(bTemp);

            while (iTemp != -1) {
                fos.write(bTemp, 0, iTemp);
                iTemp = cis.read(bTemp);
            }
            /*
            Schließen aller Streams
             */
            fos.flush();
            fos.close();
            cis.close();
            fis.close();
            return true;
        } catch (NoSuchAlgorithmException e) {
            //TODO Fehler loggen
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            //TODO Fehler loggen
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            //TODO Fehler loggen
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            //TODO Fehler loggen
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            //TODO Fehler loggen
            e.printStackTrace();
        } catch (IOException e) {
            //TODO Fehler loggen
            e.printStackTrace();
        }
        return false;
    }

}
