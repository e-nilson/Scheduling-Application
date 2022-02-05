package Controller;

import Model.Appointment;
import Model.User;
import Utils.ListProvider;
import Utils.UserDB;
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
import java.sql.Timestamp;
import java.util.ResourceBundle;

/**
 * Controller class that provides logic for the Appointments by User Report screen of the application.
 *
 * @author Erik Nilson
 */
public class AppointmentsByUserReportController implements Initializable {
    Stage stage;
    Parent scene;

    /**
     * The contact schedule table.
     */
    @FXML
    public TableView<Appointment> userAppointmentTable;

    /**
     * The appointment ID column.
     */
    @FXML
    public TableColumn<Appointment, Integer> appointmentIdCol;

    /**
     * The appointment title column.
     */
    @FXML
    public TableColumn<Appointment, String> appointmentTitleCol;

    /**
     * The appointment description column.
     */
    @FXML
    public TableColumn<Appointment, String> appointmentDescriptionCol;

    /**
     * The appointment ID column.
     */
    @FXML
    public TableColumn<Appointment, String> appointmentTypeCol;

    /**
     * The appointment start column.
     */
    @FXML
    public TableColumn<Appointment, Timestamp> appointmentStartCol;

    /**
     * The appointment end column.
     */
    @FXML
    public TableColumn<Appointment, Timestamp> appointmentEndCol;

    /**
     * The customer ID column.
     */
    @FXML
    public TableColumn<Appointment, Integer> appointmentCustomerIdCol;

    /**
     * The choose contact combobox.
     */
    @FXML
    public ComboBox<User> chooseUserComboBox;

    /**
     * Goes back to the main controller
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
    public void onChooseUser(ActionEvent event) {
    }

    /**
     * Allows the user to select a user.
     *
     * @param event view results button clicked.
     */
    @FXML
    public void onViewResultsButton(ActionEvent event) {
        int selectedUser = chooseUserComboBox.getSelectionModel().getSelectedItem().getUserId();
        userAppointmentTable.setItems(UserDB.getApptsByUser(selectedUser));
    }

    /**
     * Initializes the controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userAppointmentTable.setItems(ListProvider.getAllAppointments());
        chooseUserComboBox.setItems(ListProvider.getAllUsers());

        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("Start"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("End"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
    }
}
