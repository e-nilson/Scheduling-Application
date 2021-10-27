package Controller;

import Model.Appointment;
import Utils.ListProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateAppointmentController implements Initializable {
    Stage stage;
    Parent scene;

    private ObservableList<Appointment> originalAppointment = FXCollections.observableArrayList();

    @FXML
    public TextField appointmentIDTextField;

    @FXML
    public TextField titleTextField;

    @FXML
    public TextField descriptionTextField;

    @FXML
    public TextField locationTextField;

    @FXML
    public TextField contactIdTextField;

    @FXML
    public TextField typeTextField;

    @FXML
    public TextField startTextField;

    @FXML
    public TextField endTextField;

    @FXML
    public TextField customerIDTextField;

    @FXML
    public TextField userIDTextField;

    private Appointment appointmentToUpdate;


    public void onCancelAppointment(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainAppointment.fxml"));
        scene.setStyle(("-fx-font-family: 'serif';"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    @FXML
    void onSaveAppointment(ActionEvent event) {
        try {
            int appointmentId = appointmentToUpdate.getAppointmentId();
            String title = titleTextField.getText();
            String description = descriptionTextField.getText();
            String location = locationTextField.getText();
            String type = typeTextField.getText();
            //LocalDateTime start = appointmentToUpdate.getStart();
            //LocalDateTime end = appointmentToUpdate.getEnd();
            int customerId = appointmentToUpdate.getCustomerId();
            int userId = appointmentToUpdate.getUserId();
            int contactId = appointmentToUpdate.getContactId();

            boolean appointmentAdded = false;

                try {
                    Appointment newAppointmentUpdate = new Appointment(appointmentId, title, description, location, type, /*start, end,*/ customerId, userId, contactId);
                    ListProvider.addAppointment(newAppointmentUpdate);
                    appointmentAdded = true;

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (appointmentAdded) {
                ListProvider.updateAppointment(appointmentToUpdate);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/MainAppointment.fxml"));
                scene.setStyle(("-fx-font-family: 'serif';"));
                stage.setScene(new Scene(scene));
                stage.show();
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        appointmentToUpdate = MainAppointmentController.selectAppointmentUpdate();

        /**
         * Sets columns and rows with data for appointments.
         */
        customerIDTextField.setText(String.valueOf(appointmentToUpdate.getAppointmentId()));
        titleTextField.setText(appointmentToUpdate.getTitle());
        descriptionTextField.setText(appointmentToUpdate.getDescription());
        locationTextField.setText(String.valueOf(appointmentToUpdate.getLocation()));
        contactIdTextField.setText(String.valueOf(appointmentToUpdate.getContactId()));
        typeTextField.setText(appointmentToUpdate.getType());
        //startTextField.setText(String.valueOf(appointmentToUpdate.getStart()));
        //endTextField.setText(String.valueOf(appointmentToUpdate.getEnd()));
        customerIDTextField.setText(String.valueOf(appointmentToUpdate.getCustomerId()));
        userIDTextField.setText(String.valueOf(appointmentToUpdate.getUserId()));
    }
}
