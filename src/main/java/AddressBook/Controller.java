import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class Controller {

    @FXML
    TextField FirstNameBox;
    @FXML
    TextField NameBox;
    @FXML
    TextField CellNrBox;
    @FXML
    TextField PhoneNrBox;
    @FXML
    TextField MailBox;

    public void CreateContactClick(ActionEvent actionEvent) {
        try {
            Parent createContactRoot = FXMLLoader.load(getClass().getResource("FXMLViews/CreateContact.fxml"));
            Stage createContactStage = new Stage();
            createContactStage.setTitle("Neuer Kontakt");
            createContactStage.setScene(new Scene(createContactRoot));
            createContactStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SaveNewContactClick(ActionEvent actionEvent) {

    }

    public void CancelNewContactClick(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
