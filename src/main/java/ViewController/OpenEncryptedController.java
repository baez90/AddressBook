package ViewController;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * @author baez
 */
public class OpenEncryptedController {
    /**
     * Passwort-Feld der GUI für die entschlüsselung der Datei
     */
    public PasswordField PasswordBox;
    /**
     * TextField in welche der Pfad zur Quelldatei zwischengespeichert wird
     */
    public TextField sourcePathBox;
    /**
     * TextField in welche der Pfad zur Ziel-Datei zwischengespeicher wird
     */
    public TextField targetPathBox;
    /**
     * File-Objekt zum zwischenspeichern der Quell-Datei
     */
    private File encryptedAddressBook;
    /**
     * File-Objekt zum zwischenspeichern der Ziel-Datei
     */
    private File decryptedAddressBook;

    /**
     * Schließt etwaige Dialoge
     *
     * @param actionEvent Event um auf den Dialog zugreifen zu können
     */
    public void CancelModalClick(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Entchlüsselt das Addressbuch, speichert es zwischen und öffnet es
     *
     * @param actionEvent Event um auf den Dialog zugreifen zu können
     */
    public void OpenAddressBookClick(ActionEvent actionEvent) {
        //TODO öffnen
    }

    /**
     * öffnet einen FileChooser um den Pfad zur verschlüsselten Datei zu öffnen
     */
    public void setSourcePathClick() {

        /*
        FileChooser öffnet einen Dialog zum auswählen einer Datei
         */
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("crypt (*.crypt)", "*.crypt");
        chooser.getExtensionFilters().add(extensionFilter);

        /*
        encryptedAddressBook speichert den Datei-Pfad der ausgewählten Datei
         */
        encryptedAddressBook = chooser.showOpenDialog(new Stage());

        /*
        speichert Pfad in die entsprechende TextView um zu sehen was man öffnet
         */
        if (encryptedAddressBook != null) {
            sourcePathBox.setText(encryptedAddressBook.getAbsolutePath());
        }
    }

    /**
     * öffnet einen FileChooser um den Pfad zum speichern der entschlüsselten Datei fest zu legen
     */
    public void setTargetPathClick() {
        /*
        FileChooser öffnet einen Dialog zum auswählen einer Datei
         */
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("DB (*.db)", "*.db");
        chooser.getExtensionFilters().add(extensionFilter);

        /*
        decryptedAddressBook speichert den Datei-Pfad der ausgewählten Datei
         */
        decryptedAddressBook = chooser.showOpenDialog(new Stage());

        /*
        speichert Pfad in die entsprechende TextView um zu sehen was man öffnet
         */
        if (encryptedAddressBook != null) {
            targetPathBox.setText(decryptedAddressBook.getAbsolutePath());
        }
    }
}
