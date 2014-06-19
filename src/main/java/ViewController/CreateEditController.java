package ViewController;

import Interfaces.IAddress;
import Interfaces.IContact;
import Interfaces.IContactList;
import Model.Address;
import Model.Contact;
import Model.ContactNumberType;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    public TextField FirstNameBox;
    /**
     * TextField für Nachmame
     */
    public TextField NameBox;
    /**
     * TextField für Email-Adresse
     */
    public TextField MailBox;
    /**
     * TextField für Strasse u. Hausnummer
     */
    public TextField StreetAddressBox;
    /**
     * TextField für die PLZ
     */
    public TextField ZipCodeBox;
    /**
     * TextField für den Wohnort
     */
    public TextField CityBox;
    /**
     * VBox in welcher die Telefonnummern hinzugefügt werden können
     */
    public VBox PhoneNumberVBox;
    /**
     * Zugriff auf den DatePicker um Startdatum etwas weiter in die Vergangenheit setzen zu können
     */
    public DatePicker BirthdayDatePicker;
    /**
     * IContactList als Zwischenspeicher um Veränderungen ablegen zu können.
     */
    private IContactList contactList;
    private MainController mainController;

    /**
     * Initialisiert den Controller mit Objekten aus dem MainController
     *
     * @param con MainController-Objekt
     */
    public void initController(MainController con) {
        contactList = con.getContactList();
        mainController = con;
        BirthdayDatePicker.setValue(LocalDate.of(1992, 1, 1));
    }

    /**
     * Speichert neuen Kontakt in die DB und fügt ihn der Kontaktliste hinzu
     *
     * @param actionEvent Event um auf den Dialog zugreifen zu können
     */
    public void SaveNewContactClick(ActionEvent actionEvent) {
        IContact newContact = new Contact(FirstNameBox.getText(), NameBox.getText(), MailBox.getText(), BirthdayDatePicker.getValue());
        IAddress newAddress = new Address(StreetAddressBox.getText(), ZipCodeBox.getText(), CityBox.getText());
        newContact.setAddress(newAddress);
        contactList.add(newContact);
        mainController.getBlContacts().createContactInDB(newContact);
        mainController.updateContactTable(contactList);
        //TODO Kontakt zu Liste hinzufügen, Table updaten und Kontakt in DB speichern
        closeModal(actionEvent);
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
        /*
        ChoiceBox für Telefonnummer-Typ
         */
        ChoiceBox<ContactNumberType> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().add(ContactNumberType.Mobile);
        choiceBox.getItems().add(ContactNumberType.Home);
        choiceBox.getItems().add(ContactNumberType.Work);
        choiceBox.setValue(choiceBox.getItems().get(0));

        /*
        HBox in welche das TextField und die ChoiceBox gefüllt werden
         */
        HBox tempBox = new HBox();
        tempBox.getChildren().add(choiceBox);
        TextField nummerText = new TextField();
        tempBox.getChildren().add(nummerText);
        tempBox.setSpacing(10);

        PhoneNumberVBox.getChildren().add(tempBox);
    }

    public void RemovePhoneNumber() {
        int PhoneNumberCount = PhoneNumberVBox.getChildren().size();
        PhoneNumberVBox.getChildren().remove(PhoneNumberCount - 1);
    }

    private void closeModal(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
