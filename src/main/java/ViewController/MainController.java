package ViewController;

import BusinessLogic.BlContacts;
import Interfaces.IBlContacts;
import Interfaces.IContact;
import Interfaces.IContactList;
import Interfaces.IErrorLog;
import Model.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
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
    /**
     * Kontakt-Menü, wird erst aktiviert, sobald ein Adressbuch geöffnet wurde
     */
    public Menu ContactMenu;
    /**
     * Spalte für den Vornamen
     */
    public TableColumn FirstNameColumn;
    /**
     * Spalte für den Nachnamen
     */
    public TableColumn LastNameColumn;
    /**
     * Spalte für die Email-Adresse
     */
    public TableColumn EmailAddressColumn;
    /**
     * Spalte für das Geburtsdatum
     */
    public TableColumn BirthdayColumn;
    /**
     * VBox für die Telefnummer-Spalten
     */
    public VBox PhoneNrBox;


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
    public void initContactTable() {
        FirstNameColumn.setCellValueFactory(new PropertyValueFactory<IContact, String>("FirstName"));
        LastNameColumn.setCellValueFactory(new PropertyValueFactory<IContact, String>("LastName"));
        EmailAddressColumn.setCellValueFactory(new PropertyValueFactory<IContact, String>("MailAddress"));
        BirthdayColumn.setCellValueFactory(new PropertyValueFactory<IContact, LocalDate>("BirthDate"));
        /*
        OberserableList wrapped die contactList für die Anzeige in der Tabelle
         */
        //DEMO-CODE
        contactList = new ContactList();
        ContactNumberList temp = new ContactNumberList();
        temp.add(new ContactNumber(ContactNumberType.Home, "123456789"));
        Contact hans = new Contact("hans", "im Glück", "hans.imGlueck@burger.lecker", LocalDate.now());
        hans.setContactNumbers(temp);
        contactList.add(hans);
        ObservableList<IContact> displayList = FXCollections.observableList(contactList);
        ContactTable.setItems(displayList);
    }

    /**
     * Updated den ContactTable nach Suche
     *
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
            openAddressBook(file.getAbsolutePath());
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
        initHelpAboutView("HTML-Content/help.html");
    }

    /**
     * Öffnet die About-Ansicht
     */
    public void OpenAboutClick() {
        initHelpAboutView("HTML-Content/about.html");
    }

    public void ViewErrorListClick() {
        initHelpAboutView("HTML-Content/error.html");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateEdit.fxml"));
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
                //TODO zu editierenden Kontakt laden
            } else {

            }
        } catch (IOException e) {
            IErrorLog.saveError("MainController", "Fehler beim laden der CreateEditContactView", e.toString());
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
            openEncryptedController.initOpenEncryptedController(this);
        } catch (IOException e) {
            IErrorLog.saveError("MainController", "Fehler beim laden der OpenEncryptedAddressBookView", e.toString());
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
            IErrorLog.saveError("MainController", "Fehler beim laden der SaveAddressBookEncryptedView", e.toString());
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HTMLView.fxml"));
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
            HTMLViewController HTMLViewController = loader.getController();
            HTMLViewController.initHelpAboutView(content);

        } catch (IOException e) {
            IErrorLog.saveError("MainController", "Fehler beim laden der HelpAboutView ( " + content + "-Content)", e.toString());
        }
    }

    /**
     * Öffnet eine SQLite-Datei, liest deren Kontakte aus und schiebt diese in die Ansicht
     * prüft ob ein Kontakt zum aktuellen Datum Geburstag hat und zeigt bei Bedarf eine Notification an
     *
     * @param dbPath Pfad zur DB-Datei welche gelesen werden soll
     */
    public void openAddressBook(String dbPath) {
        blContacts.setDbPath(dbPath);
        contactList = blContacts.getContactsFromDB();
        initContactTable();
        ContactMenu.setDisable(false);

        contactList.stream().filter(IContact::dateOfBirthIsToday).forEach(c -> Notifications.create().title("Geburtstag").text(c.toString() + " hat heute Geburtstag").showInformation());
    }

}
