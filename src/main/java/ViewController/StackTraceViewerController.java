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

    @FXML
    private TableView<StackTraceElement> StackTraceTable;
    @FXML
    private TableColumn<StackTraceElement, Integer> LineColumn;
    @FXML
    private TableColumn<StackTraceElement, String> ClassColumn;
    @FXML
    private TableColumn<StackTraceElement, String> MethodColumn;

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
