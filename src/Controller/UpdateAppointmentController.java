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


    // The time and date formatter for the timestamp fields
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm:ss.S");

    // Used to convert local time to UTC
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
    boolean onSaveAppointment(ActionEvent event) throws SQLException, IOException {
        TimeZone EST = TimeZone.getTimeZone("America/New_York");
        Long offsetToEST = Long.valueOf(EST.getOffset(new Date().getTime()) /1000 /60);
        LocalDateTime startTime = LocalDateTime.parse(startTextField.getText(), format).minus(Duration.ofSeconds(offsetToUTC));
        startTime = startTime.plus(Duration.ofMinutes(offsetToEST));
        LocalDateTime endTime = LocalDateTime.parse(endTextField.getText(), format).minus(Duration.ofSeconds(offsetToUTC));
        endTime = endTime.plus(Duration.ofMinutes(offsetToEST));
        LocalTime businessHoursStart = LocalTime.of(8, 00);
        LocalTime businessHoursEnd = LocalTime.of(22, 00);
        Timestamp startDateTime = Timestamp.valueOf(startTextField.getText());
        Timestamp endDateTime = Timestamp.valueOf(endTextField.getText());

        try {
            if (appointmentIDTextField.getText().isEmpty() || descriptionTextField.getText().isEmpty() || locationTextField.getText().isEmpty() || typeTextField.getText().isEmpty()
                    || startTextField.getText().isEmpty() || endTextField.getText().isEmpty() || customerIDTextField.getText().isEmpty()
                    || userIDTextField.getText().isEmpty() || contactIDTextField.getText().isEmpty()) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                DialogPane dialogPane1 = errorAlert.getDialogPane();
                dialogPane1.setStyle("-fx-font-family: serif;");
                errorAlert.setContentText("Please enter missing values.");
                errorAlert.showAndWait();
            }

            // checks for overlapping appointments
            for (Appointment appointment : ListProvider.getAllAppointments()) {
                if((startDateTime.equals(appointment.getStart()) || startDateTime.after(appointment.getStart()) && startDateTime.before(appointment.getEnd()))) {
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

            else if (!titleTextField.equals("") && !appointmentIDTextField.equals("") && !descriptionTextField.equals("") && !locationTextField.equals("")){
                String title = titleTextField.getText();
                String description = descriptionTextField.getText();
                String location = locationTextField.getText();
                String type = typeTextField.getText();
                Timestamp start = Timestamp.valueOf(startTextField.getText());
                Timestamp end = Timestamp.valueOf(endTextField.getText());
                int user_ID = valueOf(userIDTextField.getText());
                int contact_ID = valueOf(contactIDTextField.getText());
                int customer_ID = valueOf(customerIDTextField.getText());

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

                return AppointmentDB.updateAppointment(
                        valueOf(appointmentIDTextField.getText()),
                        titleTextField.getText(),
                        descriptionTextField.getText(),
                        locationTextField.getText(),
                        typeTextField.getText(),
                        Timestamp.valueOf(startTextField.getText()),
                        Timestamp.valueOf(endTextField.getText()),
                        valueOf(userIDTextField.getText()),
                        valueOf(contactIDTextField.getText()),
                        valueOf(customerIDTextField.getText()));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
