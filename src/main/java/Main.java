import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main-Klasse, wird beim Aufruf gestartet
 * initialisiert die MainView.fxml
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
            primaryStage.setTitle("Adressbuch");
            primaryStage.setScene(new Scene(root, 900, 700));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
