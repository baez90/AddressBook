package ViewController;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * @author baez
 */
public class StackTraceViewerController {
    /**
     * TableView für die StackTrace
     */
    @FXML
    private TableView<StackTraceElement> StackTraceTable;
    /**
     * Spalte für die Zeilennummern
     */
    @FXML
    private TableColumn<StackTraceElement, Integer> LineColumn;
    /**
     * Spalte für den Klassen-Namen
     */
    @FXML
    private TableColumn<StackTraceElement, String> ClassColumn;
    /**
     * Spalte für den Methoden-Namen
     */
    @FXML
    private TableColumn<StackTraceElement, String> MethodColumn;

    /**
     * Initialisiert den StackTraceViewer
     *
     * @param stackTraceElements StackTrace des entsprechenden Elements
     */
    public void initStackTraceViewer(StackTraceElement[] stackTraceElements) {
        LineColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getLineNumber()));
        ClassColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClassName()));
        MethodColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMethodName()));
        if (stackTraceElements != null) {
            ObservableList<StackTraceElement> elements = FXCollections.observableArrayList(stackTraceElements);
            StackTraceTable.setItems(elements);
        }

    }
}
