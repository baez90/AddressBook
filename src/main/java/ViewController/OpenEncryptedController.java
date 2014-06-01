package ViewController;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * @author baez
 */
public class OpenEncryptedController {

    public PasswordField PasswordBox;
    public TextField sourcePathBox;
    public TextField targetPathBox;

    private File encryptedAddressBook;
    private File decryptedAddressBook;

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

    public void OpenAddressBookClick(ActionEvent actionEvent) {

    }

    public void setSourcePathClick() {
        FileChooser chooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("crypt (*.crypt)", "*.crypt");
        chooser.getExtensionFilters().add(extensionFilter);

        encryptedAddressBook = chooser.showOpenDialog(new Stage());

        if (encryptedAddressBook != null) {
            sourcePathBox.setText(encryptedAddressBook.getAbsolutePath());
        }
    }

    public void setTargetPathClick() {
        FileChooser chooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("DB (*.db)", "*.db");
        chooser.getExtensionFilters().add(extensionFilter);

        decryptedAddressBook = chooser.showOpenDialog(new Stage());

        if (encryptedAddressBook != null) {
            targetPathBox.setText(decryptedAddressBook.getAbsolutePath());
        }
    }
}
