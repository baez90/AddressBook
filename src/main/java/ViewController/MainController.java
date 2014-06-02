package ViewController;

import BusinessLogic.BlContacts;
import Interfaces.IBlContacts;
import Interfaces.IContact;
import Interfaces.IContactList;
import Model.Contact;
import Model.ContactList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

/**
 * ViewController.MainController-Klasse für alle Views
 * Kontrolliert alle Click-Events
 * befüllt etwaige Elemente mit Objekten
 */
public class MainController {
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
    public Menu ContactMenu;

    /**
     * Liste aller Kontakte aus der Datenbank
     */
    private IContactList contactList = null;
    /**
     * IBlContacts für den Datenbankzugriff
     */
    private IBlContacts blContacts = new BlContacts();

    /**
     * Init-Methode, erstellt die benötigten Tabellen
     */
    private void initContactTable() {
        /*
        OberserableList wrapped die contactList für die Anzeige in der Tabelle
         */
        //DEMO-CODE
        contactList = new ContactList();
        contactList.add(new Contact("hans", "im Glück", "hans.imGlueck@burger.lecker", LocalDate.now()));
        ObservableList<IContact> displayList = FXCollections.observableList(contactList);
        ContactTable.setItems(displayList);
    }

    /**
     * Updated den ContactTable nach Suche
     * @param contactList Liste von Einträgen welche das Suchkriterium erfüllen und angezeigt werden sollen
     */
    private void updateContactTable(IContactList contactList) {
        if (contactList != null || contactList.size() > 1) {
            /*
        OberserableList wrapped die contactList für die Anzeige in der Tabelle
         */
            ObservableList<IContact> displayList = FXCollections.observableList(contactList);
            ContactTable.setItems(displayList);
        }

    }

    /**
     * Zeigt Dialog zum anlegen von neuen Kontakten an
     */
    public void CreateContactClick() {
        initCreateEditContactView(null);
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
        /*
        FileChooser öffnet einen Dialog zum auswählen einer Datei
         */
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("DB (*.db)", "*.db");
        chooser.getExtensionFilters().add(extensionFilter);
        /*
        file speichert den Datei-Pfad der ausgewählten Datei
         */
        File file = chooser.showSaveDialog(new Stage());
        if (file != null) {
            /*
            prüft ob die richtige Datei-Endung angegeben wurde und hängt diese bei Bedarf an
             */
            String filePath = file.getAbsolutePath();
            if (!filePath.endsWith(".db")) {
                filePath += ".db";
            }
            blContacts.setDbPath(filePath);
            blContacts.initDB();
            ContactMenu.setDisable(false);
        }
    }

    /**
     * Öffnet Adressbuch
     * sollte prüfen ob Datenbank valide ist und ggf. Datenbank initialisieren
     */
    public void OpenAddressBookClick() {
        /*
        FileChooser öffnet einen Dialog zum auswählen einer Datei
         */
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("DB (*.db)", "*.db");
        chooser.getExtensionFilters().add(extensionFilter);

        /*
        file speichert den Datei-Pfad der ausgewählten Datei
         */
        File file = chooser.showOpenDialog(new Stage());
        if (file != null) {
            blContacts.setDbPath(file.getAbsolutePath());
            blContacts.getContactsFromDB();
            initContactTable();
            ContactMenu.setDisable(false);

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
        if (contactList == null || contactList.size() < 1) {
            Dialogs.create().title("Info").masthead("Kein Adressbuch").message("Es wurde noch kein Adressbuch geöffnet. Es muss erst ein Adressbuch erstellt oder geöffnet werden um etwas suchen zu können").showInformation();
        } else {
            updateContactTable(contactList.searchContacts(SearchBox.getText()));
        }

    }

    public void SearchBoxKeyDown(KeyEvent event) {
        if (contactList != null) {
            updateContactTable(contactList.searchContacts(SearchBox.getText() + event.getText()));
        }
    }

    /**
     * Ruft den Dialog zum verschlüsselten Speichern des Adressbuchs auf
     */
    public void SaveAddressBookEncryptedClick() {
        initSaveAddressBookEncryptedView();
    }

    /**
     * Ruft Dialog zum öffnen eines verschlüsselten Adressbuchs auf
     */
    public void OpenEncryptedAddressBookClick() {
        initOpenEncryptedAddressBookView();
    }

    /**
     * Öffnet die Hilfe
     */
    public void OpenHelpClick() {
        initHelpAboutView("help.html");
    }

    /**
     * Öffnet die About-Ansicht
     */
    public void OpenAboutClick() {
        initHelpAboutView("about.html");
    }

    /**
     * Getter für die zwischengespeicherte IContactList für die anderen Controller
     *
     * @return IContactList welche aus der DB gelesen wurde
     */
    public IContactList getContactList() {
        return contactList;
    }

    /**
     * Getter für die zwischengespeicherte IBlContacts für die anderen Controller
     * benötigt für den DB-Zugriff
     *
     * @return IBlContacts-Objekt
     */
    public IBlContacts getBlContacts() {
        return blContacts;
    }

    /**
     * initialisiert die CreateEditContactView
     * je nach Kontext wird ein Kontakt editiert oder ein neuer erstellt
     *
     * @param contactToEdit Objekt welches editiert werden soll, falls ein neues erstellt werden soll wird null übergeben
     */
    private void initCreateEditContactView(IContact contactToEdit) {
        try {
            /*
            lädt .fxml-Datei und bindet diese in eine neue Stage (Fenster)
             */
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateEditContact.fxml"));
            Parent createEditContactRoot = loader.load();
            Stage createEditContactStage = new Stage();
            createEditContactStage.setTitle("Neuer Kontakt");
            createEditContactStage.setScene(new Scene(createEditContactRoot));
            createEditContactStage.show();

            /*
            holt Controller von View und initialisiert View anschließend
             */
            CreateEditController createEditController = loader.getController();
            createEditController.initController(this);
            if (contactToEdit != null) {

            } else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * initialisiert die OpenEncryptedAddressBookView
     */
    private void initOpenEncryptedAddressBookView() {
        try {
            /*
            lädt .fxml-Datei und bindet diese in eine neue Stage (Fenster)
             */
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OpenEncrypted.fxml"));
            Parent openEncryptedAddressBookRoot = loader.load();
            Stage openEncryptedAddressBookStage = new Stage();
            openEncryptedAddressBookStage.setTitle("Verschlüsseltes Adressbuch öffnen");
            openEncryptedAddressBookStage.setScene(new Scene(openEncryptedAddressBookRoot));
            openEncryptedAddressBookStage.show();

            /*
            holt Controller von View und initialisiert View anschließend
             */
            OpenEncryptedController openEncryptedController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialisiert die SaveAddressBookEncryptionView
     */
    private void initSaveAddressBookEncryptedView() {
        try {

            /*
            lädt .fxml-Datei und bindet diese in eine neue Stage (Fenster)
             */
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SaveEncrypted.fxml"));
            Parent saveEncryptedRoot = loader.load();
            Stage saveEncryptedStage = new Stage();
            saveEncryptedStage.setTitle("Adressbuch verschlüsselt speichern");
            saveEncryptedStage.setScene(new Scene(saveEncryptedRoot));
            saveEncryptedStage.show();

            /*
            holt Controller von View und initialisiert View anschließend
             */
            SaveEncryptedController saveEncryptedController = loader.getController();
            saveEncryptedController.initSaveEncryptedController(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * initialisiert die HelpAboutView
     *
     * @param content Name der HTML-Seite welche geöffnet werden soll
     */
    private void initHelpAboutView(String content) {
        try {

            /*
            lädt .fxml-Datei und bindet diese in eine neue Stage (Fenster)
             */
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HelpAbout.fxml"));
            Parent helpAboutRoot = loader.load();
            Stage helpAboutStage = new Stage();

            /*
            je nach Kontext wird der Fenster-Titel gesetzt
             */
            switch (content) {
                case "about.html":
                    helpAboutStage.setTitle("Über das Adressbuch");
                    break;
                case "help.html":
                    helpAboutStage.setTitle("Hilfe");
                    break;
            }

            helpAboutStage.setScene(new Scene(helpAboutRoot));
            helpAboutStage.show();

            /*
            holt Controller von View und initialisiert View anschließend
             */
            HelpAboutController helpAboutController = loader.getController();
            helpAboutController.initHelpAboutView(content);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
