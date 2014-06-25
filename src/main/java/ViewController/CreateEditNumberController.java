package ViewController;

import Model.ContactNumberType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * @author baez
 */
public class CreateEditNumberController {

    @FXML
    private TextField NrBox;
    @FXML
    private ChoiceBox<ContactNumberType> TypeChoiceBox;

    public void SaveNumberClick(ActionEvent actionEvent) {

    }

    public void CancelClick(ActionEvent actionEvent) {

    }
}
