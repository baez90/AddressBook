package ViewController;

import BusinessLogic.ErrorLog;
import Interfaces.IError;
import Model.Error;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author baez
 */
public class ErrorConsoleController {
    /**
     * TableView für alle Fehler
     */
    @FXML
    private TableView<IError> ErrorTable;
    /**
     * Spalte für die Klasse in welcher der Fehler aufgetreten ist
     */
    @FXML
    private TableColumn<IError, String> ClassColumn;
    /**
     * Spalte für die Methode in welcher der Fehler aufgetreten ist
     */
    @FXML
    private TableColumn<IError, String> MethodColumn;
    /**
     * Spalte für den Kontext des Fehlers
     */
    @FXML
    private TableColumn<IError, String> ContextColumn;
    /**
     * Spalte für die genaue Exception
     */
    @FXML
    private TableColumn<IError, String> ExceptionColumn;
    /**
     * Spalte für den Timestamp wann der Fehler aufgetreten ist
     */
    @FXML
    private TableColumn<IError, LocalDateTime> TimeColumn;

    /**
     * DateTimeFormatter für deutsches Format
     */
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    /**
     * Wrapper der Error-List für die TableView
     */
    private ObservableList<IError> displayList = FXCollections.observableList(ErrorLog.getInstance());

    /**
     * Initialisiert die ErrorConsole
     * registriert Doppelklick-Handler
     */
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
        /*
        CellFactory um deutsches Datumsformat zu bekommen
         */
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

    /**
     * initialisiert den Viewer für die StackTrace des Fehlers
     *
     * @param stackTraceElements Array von StackTraceElements welche angezeigt werden sollen
     */
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

    public void SaveLogfileClick() {
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("txt (*.txt)", "*.txt");
        chooser.getExtensionFilters().add(extensionFilter);

        File logfile = chooser.showSaveDialog(new Stage());

        if (logfile != null) {
            if (!logfile.getAbsolutePath().endsWith(".txt")) {
                ErrorLog.getInstance().saveAsLogFile(logfile.getAbsolutePath() + ".txt");
            } else {
                ErrorLog.getInstance().saveAsLogFile(logfile.getAbsolutePath());
            }
        }

    }

    public void QuitClick(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
