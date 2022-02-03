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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;
import static java.lang.Integer.valueOf;

/**
 * Controller class that provides logic for the Add Appointment screen of the application.
 *
 * @author Erik Nilson
 */
public class UpdateAppointmentController implements Initializable {
    Stage stage;
    Parent scene;

    private Appointment selectedAppt;
    int index;

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
     * The contact ID text field.
     */
    @FXML
    public TextField contactIDTextField;

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
     * The contact name combobox.
     */
    @FXML
    public ComboBox<Contact> contactName;

    @FXML
    private Appointment appointmentToUpdate;

    /**
     * The time and date formatter for the timestamp fields
     */
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm:ss.S");

    /**
     * Used to convert local time to UTC
     */
    Long offsetToUTC = Long.valueOf((ZonedDateTime.now().getOffset()).getTotalSeconds());

    /**
     * Logic for contact name combobox.
     *
     * @param event Contact name combobox clicked.
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
     * Cancels appointment update and returns to the main appointments controller.
     *
     * @param event Cancel button clicked.
     * @throws IOException
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
     * Saves appointment information and returns to the main appointments controller.
     *
     * @param event Save appointment button clicked.
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    public void onSaveAppointment(ActionEvent event) throws SQLException, IOException {
        TimeZone EST = TimeZone.getTimeZone("America/New_York");
        Long offsetToEST = Long.valueOf(EST.getOffset(new Date().getTime()) /1000 /60);
        LocalDateTime startTime = LocalDateTime.parse(startTextField.getText(), formatter).minus(Duration.ofSeconds(offsetToUTC));
        startTime = startTime.plus(Duration.ofMinutes(offsetToEST));
        LocalDateTime endTime = LocalDateTime.parse(endTextField.getText(), formatter).minus(Duration.ofSeconds(offsetToUTC));
        endTime = endTime.plus(Duration.ofMinutes(offsetToEST));
        LocalTime businessHoursStart = LocalTime.of(8, 00);
        LocalTime businessHoursEnd = LocalTime.of(22, 00);

        try {
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
            Timestamp start = Timestamp.valueOf(startTextField.getText());
            Timestamp end = Timestamp.valueOf(endTextField.getText());
            int user_ID = valueOf(userIDTextField.getText());
            int contact_ID = valueOf(contactIDTextField.getText());
            int customer_ID = valueOf(customerIDTextField.getText());

            Appointment overlapAppt = AppointmentDB.appointmentOverlap(start, end, customer_ID);

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
            }

            // checks if appointment is during business hours
            if (startTime.toLocalTime().isBefore(businessHoursStart) || endTime.toLocalTime().isAfter(businessHoursEnd)) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                DialogPane dialogPane1 = errorAlert.getDialogPane();
                dialogPane1.setStyle("-fx-font-family: serif;");
                errorAlert.setContentText("Please enter a time between 08:00 EST and 10:00 EST.");
                errorAlert.showAndWait();
            }

            // checks for overlapping appointments
            if (overlapAppt != null) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                DialogPane dialogPane1 = errorAlert.getDialogPane();
                dialogPane1.setStyle("-fx-font-family: serif;");
                errorAlert.setContentText("Appointment time already taken, please enter different start and end times.");
                errorAlert.showAndWait();
                /*
                //to check if appointments collide

        for (Appointment apt : DataStorage.getAllAppointments()) {
            LocalDateTime MS = startDateTime;
            LocalDateTime ME = endDateTime;
            LocalDateTime JS = apt.getStartDateNTime();
            LocalDateTime JE = apt.getEndDateNTime();

            if(apt.getAptId()!= aptId) {
                if (customerId == apt.getCustomerId()) {
                    if ((MS.isAfter(JS) || MS.isEqual(JS)) && MS.isBefore(JE)) {
                        Alert noSelection = new Alert(Alert.AlertType.INFORMATION);
                        noSelection.setTitle("Appointment collision");
                        noSelection.setContentText("There is an existing appointment in this time interval!\n" + "Please change times!");
                        noSelection.showAndWait();
                        return -1;
                    } else if (ME.isAfter(JS) && (ME.isBefore(JE) || ME.isEqual(JE))) {
                        Alert noSelection = new Alert(Alert.AlertType.INFORMATION);
                        noSelection.setTitle("Appointment collision");
                        noSelection.setContentText("There is an existing appointment in this time interval!\n" + "Please change times!");
                        noSelection.showAndWait();
                        return -1;
                    } else if ((MS.isBefore(JS) || MS.isEqual(JS)) && (ME.isAfter(JE) || ME.isEqual(JE))) {
                        Alert noSelection = new Alert(Alert.AlertType.INFORMATION);
                        noSelection.setTitle("Appointment collision");
                        noSelection.setContentText("There is an existing appointment in this time interval!\n" + "Please change times!");
                        noSelection.showAndWait();
                        return -1;
                    }
                }
            }
        }
                 */

            } else {
                appointmentToUpdate.setTitle(title);
                appointmentToUpdate.setDescription(description);
                appointmentToUpdate.setLocation(location);
                appointmentToUpdate.setType(type);
                appointmentToUpdate.setStart(start);
                appointmentToUpdate.setEnd(end);
                appointmentToUpdate.setUser_ID(user_ID);
                appointmentToUpdate.setContact_ID(contact_ID);
                appointmentToUpdate.setCustomer_ID(customer_ID);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/MainAppointment.fxml"));
                scene.setStyle(("-fx-font-family: 'serif';"));
                stage.setScene(new Scene(scene));
                stage.show();

                AppointmentDB.updateAppointment(
                        Integer.valueOf(appointmentIDTextField.getText()),
                        titleTextField.getText(),
                        descriptionTextField.getText(),
                        locationTextField.getText(),
                        typeTextField.getText(),
                        Timestamp.valueOf(startTextField.getText()),
                        Timestamp.valueOf(endTextField.getText()),
                        Integer.valueOf(userIDTextField.getText()),
                        Integer.valueOf(contactIDTextField.getText()),
                        Integer.valueOf(customerIDTextField.getText()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentToUpdate = MainAppointmentController.selectAppointmentUpdate();
        appointmentIDTextField.setText(String.valueOf(appointmentToUpdate.getAppointment_ID()));
        titleTextField.setText(appointmentToUpdate.getTitle());
        descriptionTextField.setText(appointmentToUpdate.getDescription());
        locationTextField.setText(String.valueOf(appointmentToUpdate.getLocation()));
        contactIDTextField.setText(String.valueOf(appointmentToUpdate.getContact_ID()));
        typeTextField.setText(appointmentToUpdate.getType());
        startTextField.setText(String.valueOf(appointmentToUpdate.getStart()));
        endTextField.setText(String.valueOf(appointmentToUpdate.getEnd()));
        customerIDTextField.setText(String.valueOf(appointmentToUpdate.getCustomer_ID()));
        userIDTextField.setText(String.valueOf(appointmentToUpdate.getUser_ID()));
    }
}

