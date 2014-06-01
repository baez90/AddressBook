package ViewController;

import Interfaces.IBlContacts;
import Interfaces.IFileEncryption;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;

import java.io.File;

/**
 * @author baez
 */
public class SaveEncryptedController {
    /**
     * Button im Verschlüsselt-Speichern Dialog,
     * Button-Text wird verändert nach erfolgreichem speichern
     */
    public Button SaveEncryptedCancelButton;
    /**
     * PasswordField zum eingeben des Verschlüsselungspassworts
     */
    public PasswordField PasswordBox;
    /**
     * PasswordField zum wiederholen des Passworts um Schreibweise zu kontrollieren
     */
    public PasswordField PasswordRepeatBox;
    /**
     * TextField zum anzeigen des Speicherpfads nach dem Speichern zur Übersichtlichkeit
     */
    public TextField EncryptedSavePathBox;
    private IBlContacts blContacts;

    /**
     * initialisiert den SaveEncryptedController
     *
     * @param con MainController zum Zugriff auf die DB etc
     */
    public void initSaveEncryptedController(MainController con) {
        blContacts = con.getBlContacts();
    }

    /**
     * Speichert das aktuelle Adressbuch verschlüsselt an einen Pfad welcher ausgewählt wird
     */
    public void SaveEncryptedClick() {
        if (blContacts.getDbPath() == null || blContacts.getDbPath().equals("")) {
            Dialogs.create().title("Warnung").masthead("Kein Adressbuch vorhanden").message("Es wurde weder ein Adressbuch geöffnet, noch eines angelegt.").showWarning();
            return;
        } else if (!PasswordBox.getText().equals(PasswordRepeatBox.getText())) {
            Dialogs.create().title("Warnung").masthead("Passwortfehler").message("Die Passwörter stimmen nicht überein").showWarning();
            return;
        }
        /*
        FileChooser öffnet einen Dialog zum auswählen einer Datei
         */
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("crypt (*.crypt)", "*.crypt");
        chooser.getExtensionFilters().add(extensionFilter);

        /*
        file speichert den Datei-Pfad der ausgewählten Datei
         */
        File file = chooser.showSaveDialog(new Stage());
        if (file != null) {
            if (IFileEncryption.encryptFile(PasswordBox.getText(), blContacts.getDbPath(), file.getAbsolutePath())) {
                EncryptedSavePathBox.setText(file.getAbsolutePath());
                Dialogs.create().title("Info").masthead("Erfolg").message("Adressbuch erfolgreich verschlüsselt").showConfirm();
            }

        }
        SaveEncryptedCancelButton.setText("Schließen");
    }

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
}
