package ViewController;

import Interfaces.IContactNumberList;
import Model.ContactNumber;
import Model.ContactNumberList;
import Model.ContactNumberType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;

/**
 * @author baez
 */
public class CreateEditNumberController {

    IContactNumberList contactNumbers = new ContactNumberList();
    CreateEditController controller;
    @FXML
    private TextField NrBox;
    @FXML
    private ChoiceBox<ContactNumberType> TypeChoiceBox;

    public void SaveNumberClick(ActionEvent actionEvent) {
        if (NrBox.getText().length() > 0) {
            contactNumbers.add(new ContactNumber(TypeChoiceBox.getValue(), NrBox.getText()));
            controller.updatePhoneNrTable();
            closeModal(actionEvent);
        } else {
            Dialogs.create().title("Info").masthead("Keine Nummer").message("Es wurde keine Nummer eingegeben").showInformation();
        }

    }

    public void CancelClick(ActionEvent actionEvent) {
        closeModal(actionEvent);
    }

    public void initController(CreateEditController con) {
        controller = con;
        TypeChoiceBox.getItems().add(ContactNumberType.Home);
        TypeChoiceBox.getItems().add(ContactNumberType.Mobile);
        TypeChoiceBox.getItems().add(ContactNumberType.Work);

        if (con.getNumberToEdit() != null) {
            TypeChoiceBox.setValue(con.getNumberToEdit().getType());
            NrBox.setText(con.getNumberToEdit().getNumber());
        }
        if (con.getContactToEdit() != null) {
            contactNumbers = con.getContactToEdit().getContactNumbers();
        }
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


}
