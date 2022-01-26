package Controller;

import Model.Appointment;
import Model.Contact;
import Utils.ContactDB;
import Utils.ListProvider;
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
import java.sql.Timestamp;
import java.util.ResourceBundle;

/**
 * Controller class that provides logic for the Contact Schedule Report screen of the application.
 *
 * @author Erik Nilson
 */
public class ContactSchedulesReportController implements Initializable {
    Stage stage;
    Parent scene;

    /**
     * The contact schedule table.
     */
    @FXML
    public TableView<Appointment> contactScheduleTable;

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
    public ComboBox<Contact> chooseContactComboBox;

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

    /**
     * Allows the user to select a contact.
     *
     * @param event choose contact button clicked.
     * @throws SQLException
     */
    @FXML
    public void onChooseContact(ActionEvent event) throws SQLException {
        int selectedContact = chooseContactComboBox.getSelectionModel().getSelectedItem().getContact_ID();
        contactScheduleTable.setItems(ContactDB.getApptsByContact(selectedContact));
    }

    /**
     * Initializes the controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactScheduleTable.setItems(ListProvider.getAllAppointments());
        chooseContactComboBox.setItems(ListProvider.getAllContacts());
        chooseContactComboBox.getSelectionModel().selectFirst();

        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("Start"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("End"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
    }
}