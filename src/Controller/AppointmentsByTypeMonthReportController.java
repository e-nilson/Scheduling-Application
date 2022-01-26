package Controller;

import Model.Appointment;
import Utils.AppointmentDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controller class that provides logic for the Appointments Report screen of the application.
 *
 * @author Erik Nilson
 */
public class AppointmentsByTypeMonthReportController implements Initializable {
    Stage stage;
    Parent scene;

    /**
     * The table view.
     */
    @FXML
    public TableView reportTable;

    /**
     * The month column.
     */
    @FXML
    public TableColumn<Appointment, String> monthCol;

    /**
     * The type column.
     */
    @FXML
    public TableColumn<Appointment, String> typeCol;

    /**
     * The total column.
     */
    @FXML
    public TableColumn<Appointment, Integer> totalCol;

    /**
     * The month combobox.
     */
    @FXML
    public ComboBox monthComboBox;

    /**
     * The view month button.
     */
    @FXML
    public Button viewMonthButton;

    /**
     * Returns to the main appointments controller.
     *
     * @param event Back button clicked.
     * @throws IOException
     */
    @FXML
    public void onBackButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
        scene.setStyle(("-fx-font-family: 'serif';"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    public void onMonthComboBox(ActionEvent event) {
    }

    /**
     * Sends appointent info to the table based on the chosen month.
     *
     * @param event Cancel button clicked.
     * @throws SQLException
     */
    @FXML
    public void onViewMonthButton(ActionEvent event) throws SQLException {
        String selectedMonth = (String) monthComboBox.getSelectionModel().getSelectedItem();
        reportTable.setItems(AppointmentDB.getApptByMonthType(selectedMonth));
    }

    /**
     * Initializes the controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        monthCol.setCellValueFactory((new PropertyValueFactory<>("month")));
        typeCol.setCellValueFactory((new PropertyValueFactory<>("type")));
        totalCol.setCellValueFactory((new PropertyValueFactory<>("total")));

        // months for combobox
        ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March",
                "April", "May", "June", "July", "August", "September", "October", "November", "December");
        monthComboBox.setItems(months);
        monthComboBox.getSelectionModel().selectFirst();
    }
}

