package Controller;

import Model.Appointment;
import Model.Customer;
import Utils.ListProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class AddAppointmentController {
    Stage stage;
    Parent scene;

    public TextField appointmentIDTextField;
    public TextField titleTextField;
    public TextField descriptionTextField;
    public TextField locationTextField;
    public TextField contactTextField;
    public TextField typeTextField;
    public TextField startTextField;
    public TextField endTextField;
    public TextField customerIDTextField;
    public TextField userIDTextField;

    private Appointment appointmentToUpdate;

    /**
     * variable are used when converting the users local time to UTC and making sure they are formatted properly
     */
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    Long offsetToUTC = Long.valueOf((ZonedDateTime.now().getOffset()).getTotalSeconds());


    /**
     * Saves appointment information and returns to the main appointments controller.
     *
     * @param event Save appointment button clicked.
     */
    @FXML
    public void onSaveAppointment(ActionEvent event) {
        try {
            int appointmentId = 0;
            for (Appointment appointment : ListProvider.getAllAppointments()) {
                if (appointment.getAppointmentId() > appointmentId)
                    appointmentId = (appointment.getCustomerId());
                appointmentId = ++appointmentId;
            }

            String title = titleTextField.getText();
            String description = descriptionTextField.getText();
            String location = locationTextField.getText();
            String type = typeTextField.getText();
            LocalDateTime start = LocalDateTime.parse(startTextField.getText(), formatter).minus(Duration.ofSeconds(offsetToUTC));
            LocalDateTime end = LocalDateTime.parse(endTextField.getText(), formatter).minus(Duration.ofSeconds(offsetToUTC));
            int customerId = appointmentToUpdate.getCustomerId();
            int userId = appointmentToUpdate.getUserId();
            int contactId = appointmentToUpdate.getContactId();

            boolean appointmentAdded = false;

            try {
                Appointment newAppointmentAdded = new Appointment(appointmentId, title, description, location, type, /*start, end,*/ customerId, userId, contactId);
                ListProvider.addAppointment(newAppointmentAdded);
                appointmentAdded = true;

            } catch (Exception e) {
                //displayAlert(1);
            }

            if (appointmentAdded) {
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/MainAppointment.fxml"));
                scene.setStyle(("-fx-font-family: 'serif';"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        } catch(Exception e) {
            //displayAlert(1);
        }
    }

    /**
     * Cancels appointment information and returns to the main appointments controller.
     *
     * @param event Cancel button clicked.
     */
    public void onCancelAppointment(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainAppointment.fxml"));
        scene.setStyle(("-fx-font-family: 'serif';"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
