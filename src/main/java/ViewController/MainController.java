package ViewController;

import BusinessLogic.BlContacts;
import Interfaces.*;
import Model.ContactList;
import Model.ContactNumberList;
import Model.ContactNumberType;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import org.controlsfx.dialog.Dialogs;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * MainController-Klasse für alle Views
 * Kontrolliert alle Click-Events
 * befüllt etwaige Elemente mit Objekten
 */
public class MainController {
    private final Clipboard clipboard = Clipboard.getSystemClipboard();
    private final ClipboardContent clipboardContent = new ClipboardContent();
    /**
     * Tabelle zum anzeigen der Kontakte
     */
    public TableView<IContact> ContactTable;
    public TableView<IContactNumber> PhoneNrTable;
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
    public TableColumn<IContact, String> FirstNameColumn;
    /**
     * Spalte für den Nachnamen
     */
    public TableColumn<IContact, String> LastNameColumn;
    /**
     * Spalte für die Email-Adresse
     */
    public TableColumn<IContact, String> EmailAddressColumn;
    /**
     * Spalte für das Geburtsdatum
     */
    public TableColumn<IContact, LocalDate> BirthdayColumn;
    /**
     * Spalte für Straße und Hausnummer
     */
    public TableColumn<IContact, String> StreetAddressColumn;
    /**
     * Spalte für die Postleitzahl
     */
    public TableColumn<IContact, String> ZipCodeColumn;
    /**
     * Spalte für den Wohnort
     */
    public TableColumn<IContact, String> CityColumn;
    /**
     * Spalte für alle Festnetz-Nummern eines Kontakts
     */
    public TableColumn<IContactNumber, ContactNumberType> NumberTypeColumn;
    /**
     * Spalte für alle Mobil-Nummern eines Kontakts
     */
    public TableColumn<IContactNumber, String> NumberColumn;
    /**
     * DateTimeFormatter zur Anzeige des Geburtsdatums im deutschen Format
     */
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    /**
     * Liste aller Kontakte aus der Datenbank
     */
    private IContactList contactList = new ContactList();
    /**
     * Wrapper um die contactList
     */
    private ObservableList<IContact> displayList = FXCollections.observableList(contactList);
    private ObservableList<IContactNumber> phoneNrList = FXCollections.observableList(new ContactNumberList());
    /**
     * Zwischenspeicher für den Edit
     */
    private IContact selectedContact = null;
    /**
     * IBlContacts für den Datenbankzugriff
     */
    private IBlContacts blContacts = new BlContacts();

    /**
     * Init-Methode, erstellt die benötigten Tabellen
     */
    public void initContactTable() {
        FirstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        LastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        EmailAddressColumn.setCellValueFactory(cellData -> cellData.getValue().getMailAddressProperty());
        BirthdayColumn.setCellValueFactory(cellData -> cellData.getValue().getBirthdayProperty());
        StreetAddressColumn.setCellValueFactory(cellData -> cellData.getValue().getAddress().getStreetAddressProperty());
        ZipCodeColumn.setCellValueFactory(cellData -> cellData.getValue().getAddress().getZipCodeProperty());
        CityColumn.setCellValueFactory(cellData -> cellData.getValue().getAddress().getCityProperty());
        NumberTypeColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
        NumberColumn.setCellValueFactory(cellData -> cellData.getValue().getNumberProperty());

        /*
        Kontext-Menü
         */

        ContactTable.setRowFactory(param -> {
            final TableRow<IContact> row = new TableRow<>();
            final ContextMenu contactMenu = new ContextMenu();
            final ContextMenu createMenu = new ContextMenu();
            final MenuItem editItem = new MenuItem("Bearbeiten");
            editItem.setOnAction(event -> {
                selectedContact = row.getItem();
                initCreateEditContactView(true);
            });
            final MenuItem removeItem = new MenuItem("Löschen");
            removeItem.setOnAction(event -> {
                blContacts.removeContactInDB(row.getItem());
                contactList.remove(row.getItem());
                updateContactTable(contactList);
            });
            final MenuItem createItem = new MenuItem("Neuer Kontakt");
            createItem.setOnAction(event -> initCreateEditContactView(false));
            final MenuItem copyMailItem = new MenuItem("Email-Adresse kopieren");
            copyMailItem.setOnAction(event -> {
                clipboardContent.putString(row.getItem().getMailAddress());
                clipboard.setContent(clipboardContent);
            });
            final MenuItem copyStreetItem = new MenuItem("Straße kopieren");
            copyStreetItem.setOnAction(event -> {
                clipboardContent.putString(row.getItem().getAddress().getStreetAddress());
                clipboard.setContent(clipboardContent);
            });
            final MenuItem copyZipItem = new MenuItem("PLZ kopieren");
            copyZipItem.setOnAction(event -> {
                clipboardContent.putString(row.getItem().getAddress().getZipCode());
                clipboard.setContent(clipboardContent);
            });
            final MenuItem copyCityItem = new MenuItem("Wohnort kopieren");
            copyCityItem.setOnAction(event -> {
                clipboardContent.putString(row.getItem().getAddress().getCity());
                clipboard.setContent(clipboardContent);
            });

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    selectedContact = row.getItem();
                    initCreateEditContactView(true);
                }
            });


            contactMenu.getItems().add(editItem);
            contactMenu.getItems().add(removeItem);
            contactMenu.getItems().add(copyMailItem);
            contactMenu.getItems().add(copyStreetItem);
            contactMenu.getItems().add(copyZipItem);
            contactMenu.getItems().add(copyCityItem);

            createMenu.getItems().add(createItem);
            row.contextMenuProperty().bind(Bindings.when(row.emptyProperty()).then(createMenu).otherwise(contactMenu));
            return row;
        });




        /*
        Geburtstage nach deutschem Format anzeigen
         */
        BirthdayColumn.setCellFactory(column -> new TableCell<IContact, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    // Format date.
                    setText(formatter.format(item));
                }
            }
        });
        displayList.clear();
        displayList.addAll(contactList);
        ContactTable.setItems(displayList);
        PhoneNrTable.setItems(phoneNrList);


        ContactTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedContact = newValue;
            phoneNrList.clear();
            if (selectedContact != null && selectedContact.getContactNumbers().size() > 0) {
                phoneNrList.addAll(selectedContact.getContactNumbers());
                PhoneNrTable.setItems(phoneNrList);
            }
        });
    }

    /**
     * Updated den ContactTable nach Suche
     *
     * @param contactList Liste von Einträgen welche das Suchkriterium erfüllen und angezeigt werden sollen
     */
    public void updateContactTable(IContactList contactList) {
        displayList.clear();
        displayList.addAll(contactList);
        ContactTable.getSelectionModel().clearSelection();
    }

    /**
     * Zeigt Dialog zum anlegen von neuen Kontakten an
     */
    public void CreateContactClick() {
        initCreateEditContactView(false);
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
            contactList = new ContactList();
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
        if (selectedContact != null) {
            if (blContacts.removeContactInDB(selectedContact) == 1) {
                contactList.remove(selectedContact);
                selectedContact = null;
                updateContactTable(contactList);
            }
        } else {
            Dialogs.create().title("Info").masthead("Kein Eintrag markiert").message("Es wurde kein Eintrag zum löschen markiert").showInformation();
        }

    }

    /**
     * Sucht Kontakt in der Liste und zeigt Result-List im ContactTable an
     */
    public void SearchButtonClick() {
        if (contactList == null || contactList.size() < 1) {
            Dialogs.create().title("Info").masthead("Leere Liste").message("Keine Kontakte vorhanden zum durchsuchen").showInformation();
        } else {
            updateContactTable(contactList.searchContacts(SearchBox.getText()));
        }

    }

    /**
     * Live-Suche in den Kontakten während des Eintippen des Suchbegriffs
     *
     * @param event Event welches die gedrückte Taste enthält
     */
    public void SearchBoxKeyDown(KeyEvent event) {
        if (contactList != null && event.getText().length() > 0) {
            if (Character.isLetter(event.getText().charAt(0))) {
                updateContactTable(contactList.searchContacts(SearchBox.getText() + event.getText()));
            } else {
                updateContactTable(contactList.searchContacts(SearchBox.getText()));
            }

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

    /**
     * Zeigt die Error-Listen-Webseite an
     */
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
     * Getter für CreateEditController
     *
     * @return IContact-Objekt welches editiert werden soll
     */
    public IContact getSelectedContact() {
        return selectedContact;
    }

    /**
     * initialisiert die CreateEditContactView
     * je nach Kontext wird ein Kontakt editiert oder ein neuer erstellt
     */
    private void initCreateEditContactView(boolean edit) {
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
            createEditController.initController(this, edit);
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
        if (!blContacts.initDB()) {
            Notifications.create().title("Warnung").text("Datenbank ist korrupt.").showInformation();
        }
        contactList = blContacts.getContactsFromDB();
        initContactTable();
        ContactMenu.setDisable(false);

        contactList.stream().filter(IContact::dateOfBirthIsToday).forEach(c -> Notifications.create().title("Geburtstag").text(c.toString() + " hat heute Geburtstag").showInformation());
    }

    /**
     * Initialisiert ebenfalls nur die CreateEditContactView
     * dadurch, dass das Objekt selectedContact nicht null ist, wird ein Edit ausgeführt
     */
    public void EditContactClick() {
        if (selectedContact != null) {
            initCreateEditContactView(true);
        } else {
            Dialogs.create().title("Info").masthead("Kein Eintrag markiert").message("Es wurde kein Eintrag zum editieren markiert").showInformation();
        }
    }
}
