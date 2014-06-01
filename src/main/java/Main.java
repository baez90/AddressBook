import ViewController.MainController;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Adressbuch");
            primaryStage.setScene(new Scene(root, 900, 700));
            primaryStage.show();

            MainController controller = loader.getController();
            controller.initMainCointroller();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
