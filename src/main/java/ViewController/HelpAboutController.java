package ViewController;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Controller für HelpAbout View
 * öffnet Kontext abhängig entweder die about.html oder die help.html
 *
 * @author baez
 */
public class HelpAboutController {

    public WebView WebViewer;

    public void initHelpAboutView(String content) {
        URL contentUrl = getClass().getResource(content);
        WebEngine engine = WebViewer.getEngine();
        engine.load(contentUrl.toString());
    }

    public void CloseHelpAboutClick(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
