package Controller;

import Model.Appointment;
import Utils.AppointmentDB;
import Utils.ListProvider;
import javafx.collections.FXCollections;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Controller class that provides logic for the Main Appointment screen of the application.
 *
 * @author Erik Nilson
 */
public class MainAppointmentController implements Initializable {
    Stage stage;
    Parent scene;

    /**
     * The appointment table.
     */
    @FXML
    public TableView<Appointment> appointmentTable;

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
     * The appointment location column.
     */
    @FXML
    public TableColumn<Appointment, String> appointmentLocationCol;

    /**
     * The contact ID column.
     */
    @FXML
    public TableColumn<Appointment, String> appointmentContactIdCol;

    /**
     * The appointment type column.
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
     * The appointment user ID column.
     */
    @FXML
    public TableColumn<Appointment, Integer> appointmentUserIdCol;

    @FXML
    private static Appointment selectedAppointment;

    @FXML
    public static Appointment selectAppointmentUpdate() { return selectedAppointment;
    }

    /**
     * The all appointments radio button.
     */
    @FXML
    public RadioButton allAppointmentsRadioButton;

    /**
     * The weekly appointments radio button.
     */
    @FXML
    public RadioButton weekRadioButton;

    /**
     * The monthly appointments radio button.
     */
    @FXML
    public RadioButton monthRadioButton;

    /**
     * The appointments toggle group.
     */
    @FXML
    public ToggleGroup appointmentToggle;

    ObservableList<Appointment> weeklyAppointmentsList = FXCollections.observableArrayList();
    ObservableList<Appointment> monthlyAppointmentsList = FXCollections.observableArrayList();


    @FXML
    public void onAllAppointments(ActionEvent event) {
            appointmentTable.setItems(ListProvider.getAllAppointments());
    }

    /**
     * Logic for weekly appointments.
     * @param event weekly radio button clicked
     */
    @FXML
    public void onWeeklyAppointments(ActionEvent event) {
        LocalDateTime today = LocalDateTime.from(ZonedDateTime.now());
        LocalDateTime oneWeekOut = LocalDateTime.from(ZonedDateTime.now().plusWeeks(1));

        if ((this.appointmentToggle.getSelectedToggle().equals(this.weekRadioButton))) {
            Predicate<Appointment> weekView = appointment -> (appointment.getStart().toLocalDateTime().equals(today))
                    || appointment.getStart().toLocalDateTime().isAfter((today))
                    && appointment.getStart().toLocalDateTime().isBefore((oneWeekOut));

            var weeklyAppointments= ListProvider.getAllAppointments().stream().filter(weekView).collect(Collectors.toList());
            appointmentTable.setItems(weeklyAppointmentsList = FXCollections.observableList(weeklyAppointments));
        }
    }

    /**
     * Logic for monthly appointments.
     * @param event monthly radio button clicked
     */
    @FXML
    public void onMonthlyAppointments(ActionEvent event) {
        LocalDateTime today = LocalDateTime.from(ZonedDateTime.now());
        LocalDateTime oneMonthOut = LocalDateTime.from(ZonedDateTime.now().plusMonths(1));

        if ((this.appointmentToggle.getSelectedToggle().equals(this.monthRadioButton))) {
            Predicate<Appointment> monthView = appointment -> (appointment.getStart().toLocalDateTime().equals(today))
                    || appointment.getStart().toLocalDateTime().isAfter((today))
                    && appointment.getStart().toLocalDateTime().isBefore((oneMonthOut));

            var monthlyAppointments= ListProvider.getAllAppointments().stream().filter(monthView).collect(Collectors.toList());
            appointmentTable.setItems(monthlyAppointmentsList = FXCollections.observableList(monthlyAppointments));
        }
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
            confirmationAlert.setContentText("Are you sure you want to delete appointment type: " + selectedAppointment.getType() + " ID Number: " + selectedAppointment.getAppointment_ID() + "?");
            Optional<ButtonType> removeOption = confirmationAlert.showAndWait();

            if (removeOption.isPresent() && removeOption.get() == ButtonType.OK) {
                ListProvider.deleteAppointment(selectedAppointment);
                AppointmentDB.deleteAppointment(selectedAppointment.getAppointment_ID());
                System.out.println("Appointment deleted!");
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

    /**
     * Initializes the controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        appointmentTable.setItems(ListProvider.getAllAppointments());
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
        appointmentContactIdCol.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("Start"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("End"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        appointmentUserIdCol.setCellValueFactory(new PropertyValueFactory<>("User_ID"));
    }
}

