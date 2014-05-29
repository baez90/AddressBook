import Interfaces.IContact;
import Model.ContactList;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Controller-Klasse für alle Views
 * Kontrolliert alle Click-Events
 * befüllt etwaige Elemente mit Objekten
 */
public class Controller {
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
     * Liste aller Kontakte aus der Datenbank
     */
    private ContactList contactList = new ContactList();

    /**
     * Init-Methode, erstellt die benötigten Tabellen
     */
    private void initContactTable() {
        //TODO
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
        //TODO
    }

    /**
     * Bricht anlegen eines neuen Kontakts ab
     *
     * @param actionEvent Standard-Event
     */
    public void CancelNewContactClick(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
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
            contactList.setDbPath(filePath);
            contactList.initDB();
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
            contactList.setDbPath(file.getAbsolutePath());
            contactList.getContactsFromDB();
        }
    }

    /**
     * löscht Kontakt aus Datenbank und aus der Liste
     */
    public void deleteContactClick() {
        //TODO
    }

    /**
     * Sucht Kontakt in der Liste und zeigt Result-List im ContactTable an
     */
    public void SearchButtonClick() {
        //TODO
    }
}
