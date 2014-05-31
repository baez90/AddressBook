package ViewController;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * @author baez
 */
public class OpenEncryptedController {
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
}
