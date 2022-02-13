package Controller;

import Model.Appointment;
import Model.Contact;
import Utils.AppointmentDB;
import Utils.ListProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;

import static java.lang.Integer.valueOf;

/**
 * Controller class that provides logic for the Add Appointment screen of the application.
 *
 * @author Erik Nilson
 */
public class AddAppointmentController implements Initializable{
    Stage stage;
    Parent scene;

    /**
     * The appointment ID text field.
     */
    @FXML
    public TextField appointmentIDTextField;

    /**
     * The title text field.
     */
    @FXML
    public TextField titleTextField;

    /**
     * The description text field.
     */
    @FXML
    public TextField descriptionTextField;

    /**
     * The location text field.
     */
    @FXML
    public TextField locationTextField;

    /**
     * The type text field.
     */
    @FXML
    public TextField typeTextField;

    /**
     * The start text field.
     */
    @FXML
    public TextField startTextField;

    /**
     * The end text field.
     */
    @FXML
    public TextField endTextField;

    /**
     * The customer ID text field.
     */
    @FXML
    public TextField customerIDTextField;

    /**
     * The user ID text field.
     */
    @FXML
    public TextField userIDTextField;

    /**
     * The contact ID text field.
     */
    @FXML
    public TextField contactIDTextField;

    /**
     * The contact name combobox text field.
     */
    @FXML
    public ComboBox<Contact> contactName;

    /**
     * The time and date formatter for the timestamp fields
     */
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm:ss.S");

    /**
     * Used to convert local time to UTC
     */
    Long offsetToUTC = Long.valueOf((ZonedDateTime.now().getOffset()).getTotalSeconds());

    /**
     * Sets the contact name.
     *
     * @param event set contact name combo box clicked.
     *
     * @throws IOException
     */
    @FXML
    private void setContactName(ActionEvent event) throws IOException {
        if (contactName.getSelectionModel().isEmpty()) {
            return;
        }
        else {
            Contact c = contactName.getSelectionModel().getSelectedItem();
            contactIDTextField.setText(String.valueOf(c.getContact_ID()));
        }
    }

    /**
     * Saves appointment information and returns to the main appointments controller.
     *
     * @param event Save appointment button clicked.
     */
    @FXML
    boolean onSaveAppointment(ActionEvent event) throws IOException, SQLException{
        try {
            TimeZone EST = TimeZone.getTimeZone("America/New_York");
            Long offsetToEST = Long.valueOf(EST.getOffset(new Date().getTime()) /1000 /60);
            LocalDateTime startTime = LocalDateTime.parse(startTextField.getText(), formatter).minus(Duration.ofSeconds(offsetToUTC));
            startTime = startTime.plus(Duration.ofMinutes(offsetToEST));
            LocalDateTime endTime = LocalDateTime.parse(endTextField.getText(), formatter).minus(Duration.ofSeconds(offsetToUTC));
            endTime = endTime.plus(Duration.ofMinutes(offsetToEST));
            LocalTime businessHoursStart = LocalTime.of(8, 00);
            LocalTime businessHoursEnd = LocalTime.of(22, 00);

            int appointment_ID = 0;
            for (Appointment appointment : ListProvider.getAllAppointments()) {
                if (appointment.getAppointment_ID() > appointment_ID)
                    appointment_ID = (appointment.getAppointment_ID());
                appointment_ID = ++appointment_ID;
            }
            String title = titleTextField.getText();
            String description = descriptionTextField.getText();
            String location = locationTextField.getText();
            String type = typeTextField.getText();
            LocalDateTime start = LocalDateTime.parse(startTextField.getText(), formatter);
            LocalDateTime end = LocalDateTime.parse(endTextField.getText(), formatter);
            int user_ID = valueOf(userIDTextField.getText());
            int contact_ID = valueOf(contactIDTextField.getText());
            int customer_ID = valueOf(customerIDTextField.getText());

            // checks for missing values
            if (titleTextField.getText().isEmpty() || descriptionTextField.getText().isEmpty() || locationTextField.getText().isEmpty() || typeTextField.getText().isEmpty()
                    || startTextField.getText().isEmpty() || endTextField.getText().isEmpty() || customerIDTextField.getText().isEmpty()
                    || userIDTextField.getText().isEmpty() || contactIDTextField.getText().isEmpty()) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                DialogPane dialogPane1 = errorAlert.getDialogPane();
                dialogPane1.setStyle("-fx-font-family: serif;");
                errorAlert.setTitle("Missing values");
                errorAlert.setContentText("Please enter missing values.");
                errorAlert.showAndWait();
                return false;
            }

                // checks for overlapping appointments
                for (Appointment appointment : ListProvider.allAppointments) {
                    if (start.plusMinutes(1).isAfter(appointment.getStart()) && start.isBefore(appointment.getEnd()) ||
                            end.isAfter(appointment.getStart()) && end.isBefore(appointment.getEnd()) ||
                            start.isBefore(appointment.getStart()) && end.isAfter(appointment.getStart()))
                    {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        DialogPane dialogPane1 = errorAlert.getDialogPane();
                        dialogPane1.setStyle("-fx-font-family: serif;");
                        errorAlert.setContentText("Appointment time already taken, please enter different start and end times.");
                        errorAlert.showAndWait();
                        return false;
                    }
                }

            // checks if appointment is during business hours
            if (startTime.toLocalTime().isBefore(businessHoursStart) || endTime.toLocalTime().isAfter(businessHoursEnd)) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                DialogPane dialogPane1 = errorAlert.getDialogPane();
                dialogPane1.setStyle("-fx-font-family: serif;");
                errorAlert.setContentText("Please enter a time between 08:00 EST and 10:00 EST.");
                errorAlert.showAndWait();
            }

            else {
                Appointment appointmentAdded = new Appointment(appointment_ID, title, description, location, type, start, end, contact_ID, user_ID, customer_ID);
                ListProvider.addAppointment(appointmentAdded);

                AppointmentDB.addAppointment(appointment_ID, title, description, location, type, start, end, contact_ID, user_ID, customer_ID);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/MainAppointment.fxml"));
                scene.setStyle(("-fx-font-family: 'serif';"));
                stage.setScene(new Scene(scene));
                stage.show();
            }

        } catch (DateTimeParseException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            DialogPane dialogPane1 = errorAlert.getDialogPane();
            dialogPane1.setStyle("-fx-font-family: serif;");
            errorAlert.setContentText("Please use the following format for date and time fields YYYY-MM-DD'T'HH:MM");
            errorAlert.showAndWait();
            return false;
        }
        return false;
    }

    /**
     * Cancels the appointment add and returns to the main appointments controller.
     *
     * @param event Cancel button clicked.
     */
    @FXML
    public void onCancelAppointment(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainAppointment.fxml"));
        scene.setStyle(("-fx-font-family: 'serif';"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        contactName.setItems(ListProvider.getAllContacts());
    }
}

