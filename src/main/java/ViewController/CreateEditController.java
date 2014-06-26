package ViewController;

import Interfaces.*;
import Model.Address;
import Model.Contact;
import Model.ContactNumberList;
import Model.ContactNumberType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Controller für die CreateEditView
 *
 * @author baez
 */
public class CreateEditController {
    /**
     * TextField für Vorname
     */
    @FXML
    private TextField FirstNameBox;
    /**
     * TextField für Nachmame
     */
    @FXML
    private TextField NameBox;
    /**
     * TextField für Email-Adresse
     */
    @FXML
    private TextField MailBox;
    /**
     * TextField für Strasse u. Hausnummer
     */
    @FXML
    private TextField StreetAddressBox;
    /**
     * TextField für die PLZ
     */
    @FXML
    private TextField ZipCodeBox;
    /**
     * TextField für den Wohnort
     */
    @FXML
    private TextField CityBox;
    /**
     * Zugriff auf den DatePicker um Startdatum etwas weiter in die Vergangenheit setzen zu können
     */
    @FXML
    private DatePicker BirthdayDatePicker;
    /**
     * TableView für die Telefonnummern des Kontakts welcher markiert wurde
     */
    @FXML
    private TableView<IContactNumber> PhoneNrTable;
    /**
     * Spalte für den Typ der Telefonnummer
     */
    @FXML
    private TableColumn<IContactNumber, ContactNumberType> NrTypeColumn;
    /**
     * Spalte für die Telefonummern
     */
    @FXML
    private TableColumn<IContactNumber, String> NrColumn;
    /**
     * IContactList als Zwischenspeicher um Veränderungen ablegen zu können.
     */
    private IContactList contactList;
    /**
     * TableView benötigt Oberservable List zum anzeigen der Elemente
     */
    private ObservableList<IContactNumber> displayList = FXCollections.observableList(new ContactNumberList());
    /**
     * Zwischenspeicher für Kontakt-Objekt zum editieren
     */
    private IContact contactToEdit = new Contact();
    /**
     * MainController für den Zugriff auf die BlContacts, ContactList, ContactTable usw
     */
    private MainController mainController;
    /**
     * Zwischenspeicher für den SelectionHandler zum editieren
     */
    private IContactNumber numberToEdit = null;

    /**
     * Initialisiert den Controller mit Objekten aus dem MainController
     *
     * @param con  MainController-Objekt
     * @param edit boolean ob Edit oder Create
     */
    public void initController(MainController con, boolean edit) {
        contactList = con.getContactList();
        mainController = con;
        BirthdayDatePicker.setValue(LocalDate.of(1992, 1, 1));
        if (edit) {
            contactToEdit = mainController.getSelectedContact();
            FirstNameBox.setText(contactToEdit.getFirstName());
            NameBox.setText(contactToEdit.getLastName());
            MailBox.setText(contactToEdit.getMailAddress());
            BirthdayDatePicker.setValue(contactToEdit.getBirthDate());
            StreetAddressBox.setText(contactToEdit.getAddress().getStreetAddress());
            ZipCodeBox.setText(contactToEdit.getAddress().getZipCode());
            CityBox.setText(contactToEdit.getAddress().getCity());
        }
        initPhoneNrTable();
    }

    /**
     * initialisiert die TableView für die Telefonnummern, legt fest welche Variable in welcher Spalte angezeigt wird
     * registriert den ChangeListener
     */
    private void initPhoneNrTable() {
        NrTypeColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
        NrColumn.setCellValueFactory(cellData -> cellData.getValue().getNumberProperty());
        PhoneNrTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> numberToEdit = newValue);
        displayList.addAll(contactToEdit.getContactNumbers());
        PhoneNrTable.setItems(displayList);
    }

    /**
     * updatet den PhoneNrTable, aktualisiert die angezeigten Einträge
     */
    public void updatePhoneNrTable() {
        displayList.clear();
        displayList.addAll(contactToEdit.getContactNumbers());
        PhoneNrTable.setItems(displayList);
    }

    /**
     * Wrapper für Update und Create, prüft anhand des contactToEdit-Objekts ob ein neuer Kontakt angelegt wird oder nicht
     *
     * @param actionEvent Event um auf den Dialog zugreifen zu können
     */
    public void SaveNewContactClick(ActionEvent actionEvent) {
        if (contactToEdit.getContactID() > 0) {
            updateContact(actionEvent);
        } else {
            saveNewContact(actionEvent);
        }
    }

    /**
     * Schließt etwaige Dialoge
     *
     * @param actionEvent Event um auf den Dialog zugreifen zu können
     */
    public void CancelModalClick(ActionEvent actionEvent) {
        closeModal(actionEvent);
    }

    /**
     * fügt eine ChoiceBox und ein TextField in die VBox ein um zusätzliche Telefonummern eintragen zu können
     */
    public void addPhoneButtonClick() {
        initCreateEditNumber(false);
    }

    /**
     * ClickHandler für die Bearbeiten Button
     */
    public void editNumberClick() {
        if (numberToEdit != null) {
            initCreateEditNumber(true);
        } else {
            Dialogs.create().title("Info").masthead("Keine Nummer ausgewählt").message("Es wurde keine Nummer zum bearbeiten markiert.").showInformation();
        }

    }

    /**
     * entfernt unterste Telefonnummer aus der Liste
     */
    public void RemovePhoneNumber() {
        contactToEdit.getContactNumbers().remove(numberToEdit);
        displayList.clear();
        displayList.addAll(contactToEdit.getContactNumbers());
        PhoneNrTable.setItems(displayList);
        numberToEdit = null;
    }

    /**
     * Speichert die Formular-Daten in einen neuen Kontakt
     *
     * @param actionEvent Event um auf den Dialog zugreifen zu können
     */
    private void saveNewContact(ActionEvent actionEvent) {
        if (!IUtil.validateMailAddress(MailBox.getText())) {
            Dialogs.create().title("Info").masthead("Validierungsfehler").message("Die Email-Adresse hat kein gültiges Format").showInformation();
            return;
        }
        contactToEdit.setFirstName(FirstNameBox.getText());
        contactToEdit.setLastName(NameBox.getText());
        contactToEdit.setMailAddress(MailBox.getText());
        contactToEdit.setBirthDate(BirthdayDatePicker.getValue());
        IAddress newAddress = new Address(StreetAddressBox.getText(), ZipCodeBox.getText(), CityBox.getText());
        contactToEdit.setAddress(newAddress);

        contactToEdit.setContactID(mainController.getBlContacts().createContactInDB(contactToEdit));
        switch (contactToEdit.getContactID()) {
            case 0:
                Dialogs.create().title("Info").masthead("Kontakt bereits vorhanden").message("Ein Kontakt mit dem selben Namen ist bereits vorhanden. Dieser Kontakt wurde geupdated.").showInformation();
                break;
            case -1:
                Dialogs.create().title("Info").masthead("Fehler aufgetreten").message("Beim anlegen des Kontakts ist ein Fehler aufgetreten.").showInformation();
                break;
            default:
                contactList.add(contactToEdit);
        }
        mainController.updateContactTable(contactList);
        closeModal(actionEvent);
    }

    /**
     * Updated einen bestehenden Eintrag anhand der Formular-Daten
     *
     * @param actionEvent Event um auf den Dialog zugreifen zu können
     */
    private void updateContact(ActionEvent actionEvent) {
        if (!IUtil.validateMailAddress(MailBox.getText())) {
            Dialogs.create().title("Info").masthead("Validierungsfehler").message("Die Email-Adresse hat kein gültiges Format").showInformation();
            return;
        }

        contactToEdit.setFirstName(FirstNameBox.getText());
        contactToEdit.setLastName(NameBox.getText());
        contactToEdit.setMailAddress(MailBox.getText());
        contactToEdit.setBirthDate(BirthdayDatePicker.getValue());
        contactToEdit.getAddress().setStreetAddress(StreetAddressBox.getText());
        contactToEdit.getAddress().setZipCode(ZipCodeBox.getText());
        contactToEdit.getAddress().setCity(CityBox.getText());

        mainController.getBlContacts().updateContactInDB(contactToEdit);
        mainController.updateContactTable(contactList);

        closeModal(actionEvent);
    }

    /**
     * Schließt ein Fenster
     *
     * @param actionEvent Event um auf den Dialog zugreifen zu können
     */
    private void closeModal(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Initialisiert die CreateEditNumber View
     * bei Bedarf wird eine bestehende Nummer bearbeitet
     *
     * @param edit boolean ob Edit oder Create
     */
    private void initCreateEditNumber(boolean edit) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateEditNumber.fxml"));
            Parent createEditNumberRoot = loader.load();
            Stage createEditNumberStage = new Stage();

            if (edit) {
                createEditNumberStage.setTitle("Nummer editieren");
            } else {
                createEditNumberStage.setTitle("Neue Nummer erstellen");
            }

            createEditNumberStage.setScene(new Scene(createEditNumberRoot));
            createEditNumberStage.show();

            CreateEditNumberController controller = loader.getController();
            controller.initController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter für die CreateEditNumberView
     *
     * @return IContact-Objekt für den Zugriff auf die Telefonnummern des Kontakts
     */
    public IContact getContactToEdit() {
        return contactToEdit;
    }

    /**
     * Getter für die zu bearbeitende Nummer
     *
     * @return IContactNumber welche markiert ist
     */
    public IContactNumber getNumberToEdit() {
        return numberToEdit;
    }
}
