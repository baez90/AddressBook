package ViewController;

import BusinessLogic.ErrorLog;
import Interfaces.IError;
import Model.Error;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author baez
 */
public class ErrorConsoleController {

    @FXML
    private TableView<IError> ErrorTable;
    @FXML
    private TableColumn<IError, String> ClassColumn;
    @FXML
    private TableColumn<IError, String> MethodColumn;
    @FXML
    private TableColumn<IError, String> ContextColumn;
    @FXML
    private TableColumn<IError, String> ExceptionColumn;
    @FXML
    private TableColumn<IError, LocalDateTime> TimeColumn;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private ObservableList<IError> displayList = FXCollections.observableList(ErrorLog.getInstance());

    public void initErrorConsole() {
        ClassColumn.setCellValueFactory(cellData -> cellData.getValue().getErrorClassProperty());
        MethodColumn.setCellValueFactory(cellData -> cellData.getValue().getErrorMethodProperty());
        ContextColumn.setCellValueFactory(cellData -> cellData.getValue().getErrorContextProperty());
        ExceptionColumn.setCellValueFactory(cellData -> cellData.getValue().getErrorExceptionProperty());
        TimeColumn.setCellValueFactory(cellData -> cellData.getValue().getErrorTimeProperty());

        ErrorTable.setRowFactory(param -> {
            final TableRow<IError> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    initStackTraceViewer(row.getItem().getExceptionStracktrace());
                }
            });

            return row;
        });

        TimeColumn.setCellFactory(column -> new TableCell<IError, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText("");
                } else {
                    setText(formatter.format(item));
                }
            }
        });

        ErrorTable.setItems(displayList);
    }

    private void initStackTraceViewer(StackTraceElement[] stackTraceElements) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StackTraceViewer.fxml"));
        try {
            Parent stackTraceViewerParent = loader.load();
            Stage stackTraceViewerStage = new Stage();
            stackTraceViewerStage.setTitle("StackTrace Viewer");
            stackTraceViewerStage.setScene(new Scene(stackTraceViewerParent));
            stackTraceViewerStage.show();

            StackTraceViewerController viewerController = loader.getController();
            viewerController.initStackTraceViewer(stackTraceElements);
        } catch (IOException e) {
            ErrorLog.getInstance().add(new Error("ErrorConsoleController", "initStackTraceViewer", "Fehler beim laden der View", e.toString(), e.getStackTrace()));
            e.printStackTrace();
        }
    }
}
