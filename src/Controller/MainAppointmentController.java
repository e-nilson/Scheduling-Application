package Controller;

import Model.Appointment;
import Model.Customer;
import Utils.ListProvider;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainAppointmentController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    public TableView<Appointment> appointmentTable;

    @FXML
    public TableColumn<Appointment, Integer> appointmentIdCol;

    @FXML
    public TableColumn<Appointment, String> appointmentTitleCol;

    @FXML
    public TableColumn<Appointment, String> appointmentDescriptionCol;

    @FXML
    public TableColumn<Appointment, String> appointmentLocationCol;

    @FXML
    public TableColumn<Appointment, String> appointmentContactIdCol;

    @FXML
    public TableColumn<Appointment, String> appointmentTypeCol;

    @FXML
    public TableColumn<Appointment, String> appointmentStartCol;

    @FXML
    public TableColumn<Appointment, String> appointmentEndCol;

    @FXML
    public TableColumn<Appointment, Integer> appointmentCustomerIdCol;

    @FXML
    public TableColumn<Appointment, Integer> appointmentUserIdCol;

    private static Appointment selectedAppointment;

    public static Appointment selectAppointmentUpdate() { return selectedAppointment;
    }

    /**
     * Loads the add appointment screen.
     *
     * @param event Add new appointment button clicked.
     * @throws IOException
     */
    public void onAddAppointmentButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddAppointment.fxml"));
        scene.setStyle(("-fx-font-family: 'serif';"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Loads the update appointment screen.
     *
     * @param event Update appointment button clicked.
     * @throws IOException
     */
    public void onUpdateAppointmentButton(ActionEvent event) throws IOException {

        selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            DialogPane dialogPane1 = errorAlert.getDialogPane();
            dialogPane1.setStyle("-fx-font-family: serif;");
            errorAlert.setTitle("Error");
            errorAlert.setContentText("Please select an appointment to update.");
            Optional<ButtonType> result = errorAlert.showAndWait();
        } else {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/UpdateAppointment.fxml"));
            scene.setStyle(("-fx-font-family: 'serif';"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/UpdateAppointment.fxml"));
        scene.setStyle(("-fx-font-family: 'serif';"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Deletes the appointment.
     *
     * @param event Delete button clicked.
     */
    public void onDeleteAppointmentButton(ActionEvent event) {

        selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            DialogPane dialogPane1 = errorAlert.getDialogPane();
            dialogPane1.setStyle("-fx-font-family: serif;");
            errorAlert.setTitle("Error");
            errorAlert.setContentText("Please select an appointment to delete.");
            Optional<ButtonType> result = errorAlert.showAndWait();
        }
        else {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            DialogPane dialogPane1 = confirmationAlert.getDialogPane();
            dialogPane1.setStyle("-fx-font-family: serif;");
            confirmationAlert.setTitle("Warning");
            confirmationAlert.setContentText("Are you sure you want to delete the selected appointment?");
            Optional<ButtonType> removeOption = confirmationAlert.showAndWait();

            if (removeOption.isPresent() && removeOption.get() == ButtonType.OK) {
                ListProvider.deleteAppointment(selectedAppointment);
            }
        }
    }

    /**
     * Exits the main controller and goes to login screen.
     *
     * @param event Back button clicked.
     * @throws IOException
     */
    public void onBackButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
        scene.setStyle(("-fx-font-family: 'serif';"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<Appointment> allAppointments = ListProvider.getAllAppointments();

        appointmentTable.setItems(ListProvider.getAllAppointments());
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("Start"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("End"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("Created_By"));
        appointmentUserIdCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        appointmentContactIdCol.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
    }
}

