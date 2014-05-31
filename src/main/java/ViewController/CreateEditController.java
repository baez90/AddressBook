package ViewController;

import Interfaces.IContactList;
import Model.ContactNumberType;
import impl.org.controlsfx.spreadsheet.GridRow;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.LinkedList;

/**
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
    public GridPane PhoneNumberGrid;

    IContactList contactList;

    public CreateEditController(MainController con) {
        contactList = con.getContactList();
    }

    /**
     * Speichert neuen Kontakt in die DB und fügt ihn der Kontaktliste hinzu
     *
     * @param actionEvent Event um auf den Dialog zugreifen zu können
     */
    public void SaveNewContactClick(ActionEvent actionEvent) {
        //TODO Kontakt zu Liste hinzufügen, Table updaten und Kontakt in DB speichern
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
    public void addPhoneButtonClick(ActionEvent actionEvent){
        ChoiceBox<ContactNumberType> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().add(ContactNumberType.Mobile);
        choiceBox.getItems().add(ContactNumberType.Work);
        choiceBox.getItems().add(ContactNumberType.Work);

        int rowCount = PhoneNumberGrid.getRowConstraints().size();

        PhoneNumberGrid.add(choiceBox, rowCount, 0);
    }
}
