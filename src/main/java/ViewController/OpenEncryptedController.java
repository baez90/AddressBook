package ViewController;

import Interfaces.IFileEncryption;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;

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
    private MainController mainController;

    /**
     * Schließt etwaige Dialoge
     *
     * @param actionEvent Event um auf den Dialog zugreifen zu können
     */
    public void CancelModalClick(ActionEvent actionEvent) {
        closeModal(actionEvent);
    }

    /**
     * Entchlüsselt das Addressbuch, speichert es zwischen und öffnet es
     *
     * @param actionEvent Event um auf den Dialog zugreifen zu können
     */
    public void OpenAddressBookClick(ActionEvent actionEvent) {
        if (sourcePathBox.getText().length() < 1) {
            Dialogs.create().title("Info").masthead("Kein Adressbuch ausgewählt").message("Es wurde kein Pfad zu einem verschlüsselten Adressbuch angegeben").showInformation();
        } else if (targetPathBox.getText().length() < 1) {
            Dialogs.create().title("Info").masthead("Kein Zielfpad ausgewählt").message("Es wurde kein Pfad zum Speichern des entschlüsselten Adressbuchs angegeben").showInformation();
        } else {
            //TODO in eine Methode im MainController auslagern
            IFileEncryption.decryptFile(PasswordBox.getText(), sourcePathBox.getText(), targetPathBox.getText());
            mainController.getBlContacts().setDbPath(targetPathBox.getText());
            mainController.getBlContacts().getContactsFromDB();
            mainController.initContactTable();
            closeModal(actionEvent);
        }
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
        /*
      File-Objekt zum zwischenspeichern der Quell-Datei
     */
        File encryptedAddressBook = chooser.showOpenDialog(new Stage());

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
        /*
      File-Objekt zum zwischenspeichern der Ziel-Datei
     */
        File decryptedAddressBook = chooser.showSaveDialog(new Stage());

        /*
        speichert Pfad in die entsprechende TextView um zu sehen was man öffnet
         */
        if (decryptedAddressBook != null) {
            targetPathBox.setText(decryptedAddressBook.getAbsolutePath());
        }
    }

    public void initOpenEncryptedController(MainController main) {
        mainController = main;
    }

    private void closeModal(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
