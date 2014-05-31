package ViewController;

import Interfaces.IContactList;
import Model.ContactNumberType;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    public VBox PhoneNumberVBox;

    IContactList contactList;

    public void initController(MainController con) {
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
        choiceBox.setValue(choiceBox.getItems().get(0));

        HBox tempBox = new HBox();
        tempBox.getChildren().add(choiceBox);
        TextField nummerText = new TextField();
        tempBox.getChildren().add(nummerText);
        tempBox.setSpacing(10);

        PhoneNumberVBox.getChildren().add(tempBox);
    }
}
