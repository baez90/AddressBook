import BusinessLogic.BlContacts;
import Interfaces.IBlContacts;
import Interfaces.IContact;
import Interfaces.IContactList;
import Interfaces.IFileEncryption;
import Model.ContactList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;

import java.io.File;
import java.io.IOException;

/**
 * MainController-Klasse für alle Views
 * Kontrolliert alle Click-Events
 * befüllt etwaige Elemente mit Objekten
 */
public class MainController {
    /**
     * TextField für Vorname
     */
    public TextField FirstNameBox;
    /**
     * TextField für Nachmame
     */
    public TextField NameBox;
    /**
     * TextField für Handynummer
     */
    public TextField CellNrBox;
    /**
     * TextField für Festnetznummer
     */
    public TextField PhoneNrBox;
    /**
     * TextField für Email-Adresse
     */
    public TextField MailBox;
    /**
     * Tabelle zum anzeigen der Kontakte
     */
    public TableView<IContact> ContactTable;
    /**
     * Button zum suchen von Kontakten
     */
    public Button SearchButton;
    /**
     * TextField für Suchbegriffe
     */
    public TextField SearchBox;
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

    /**
     * Liste aller Kontakte aus der Datenbank
     */
    private IContactList contactList = new ContactList();
    private IBlContacts blContacts = new BlContacts();

    /**
     * Init-Methode, erstellt die benötigten Tabellen
     */
    private void initContactTable() {
        ObservableList<IContact> displayList = FXCollections.observableList(contactList);
        //TODO ContactTable mit Einträgen aus der DB füllen
    }

    /**
     * Zeigt Dialog zum anlegen von neuen Kontakten an
     */
    public void CreateContactClick() {
        try {
            Parent createContactRoot = FXMLLoader.load(getClass().getResource("CreateContact.fxml"));
            Stage createContactStage = new Stage();
            createContactStage.setTitle("Neuer Kontakt");
            createContactStage.setScene(new Scene(createContactRoot));
            createContactStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Speichert neuen Kontakt in die DB und fügt ihn der Kontaktliste hinzu
     */
    public void SaveNewContactClick() {
        //TODO Kontakt zu Liste hinzufügen, Table updaten und Kontakt in DB speichern
    }

    /**
     * Beendet Applikation
     */
    public void QuitApplication() {
        Platform.exit();
    }

    /**
     * Erstellt ein neues Addressbuch und initialisiert die Datenbank
     */
    public void CreateAddressBookClick() {
        FileChooser chooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("DB (*.db)", "*.db");
        chooser.getExtensionFilters().add(extensionFilter);

        File file = chooser.showSaveDialog(new Stage());
        if (file != null) {
            String filePath = file.getAbsolutePath();
            if (!filePath.endsWith(".db")) {
                filePath += ".db";
            }
            blContacts.setDbPath(filePath);
            blContacts.initDB();
        }
    }

    /**
     * Öffnet Adressbuch
     * sollte prüfen ob Datenbank valide ist und ggf. Datenbank initialisieren
     */
    public void OpenAddressBookClick() {
        FileChooser chooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("DB (*.db)", "*.db");
        chooser.getExtensionFilters().add(extensionFilter);

        File file = chooser.showSaveDialog(new Stage());
        if (file != null) {
            blContacts.setDbPath(file.getAbsolutePath());
            blContacts.getContactsFromDB();
            initContactTable();

            /*
            TODO Benachrichtigung für aktuelle Geburtstag anzeigen
            Notifications.create().title("Test").text("Hello World").showInformation();
             */
        }
    }

    /**
     * löscht Kontakt aus Datenbank und aus der Liste
     */
    public void deleteContactClick() {
        //TODO Kontakt aus Liste und DB löschen, Table updaten
    }

    /**
     * Sucht Kontakt in der Liste und zeigt Result-List im ContactTable an
     */
    public void SearchButtonClick() {
        //TODO Kontakte in der Liste suchen
    }

    /**
     * Ruft den Dialog zum verschlüsselten Speichern des Adressbuchs auf
     */
    public void SaveAddressBookEncryptedClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SaveEncrypted.fxml"));
            Parent createContactRoot = loader.load();
            Stage createContactStage = new Stage();
            createContactStage.setTitle("Adressbuch verschlüsselt speichern");
            createContactStage.setScene(new Scene(createContactRoot));
            //MainController c = loader.<MainController>getController();

            createContactStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ruft Dialog zum öffnen eines verschlüsselten Adressbuchs auf
     */
    public void OpenEncryptedAddressBookClick() {
        try {
            Parent createContactRoot = FXMLLoader.load(getClass().getResource("OpenEncrypted.fxml"));
            Stage createContactStage = new Stage();
            createContactStage.setTitle("Verschlüsseltes Adressbuch öffnen");
            createContactStage.setScene(new Scene(createContactRoot));
            createContactStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        Dialog zum auswählen des Speicherorts
         */
        FileChooser chooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("crypt (*.crypt)", "*.crypt");
        chooser.getExtensionFilters().add(extensionFilter);

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
