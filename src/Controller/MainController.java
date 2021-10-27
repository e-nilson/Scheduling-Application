package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class MainController {
    Stage stage;
    Parent scene;

    /**
     * Opens the main appointments controller.
     *
     * @param event Appointment button clicked.
     * @throws IOException
     */
    public void onAppointmentButton(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainAppointment.fxml"));
        scene.setStyle(("-fx-font-family: 'serif';"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Opens the main customer controller.
     *
     * @param event Customer button clicked.
     * @throws IOException
     */
    public void onCustomerButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainCustomer.fxml"));
        scene.setStyle(("-fx-font-family: 'serif';"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Opens the reports controller.
     *
     * @param event Report button clicked.
     * @throws IOException
     */
    public void onReportsButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Report.fxml"));
        scene.setStyle(("-fx-font-family: 'serif';"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Opens the login controller.
     *
     * @param event Back button clicked.
     * @throws IOException
     */
    public void onBackButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
        scene.setStyle(("-fx-font-family: 'serif';"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}

