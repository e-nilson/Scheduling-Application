package Controller;


import Model.Appointment;
import Model.User;
import Utils.DBConnection;
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
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    Stage stage;
    Parent scene;

    public TextField usernameTextField;

    public TextField passwordTextField;

    public Label zoneIdLabel;

    public Label zoneIdLabel2;

    public Label loginPageLabel;

    public Label usernameLabel;

    public Label passwordLabel;

    public Button loginButton;

    public Button logoutButton;

    private String alertTitleText;

    private String alertHeaderText;

    private String alertContextText;

    /**
     * Accesses the main appointment screen with proper username and password. Use "test" for testing.
     *
     * Contains appropriate error and alert windows.
     *
     * @param event Login button clicked.
     * @throws IOException FXMLLoader
     */
    @FXML
    private void onLoginButton(ActionEvent event) throws IOException, SQLException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        boolean match = false;

        for(User u: ListProvider.getAllUsers()){
            if (u.getUsername().equals(username) && u.getPassword().equals(password)){
                match = true;
                break; //this stops it from searching the DB once found
            }
        }
        if(match){
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
            scene.setStyle(("-fx-font-family: 'serif';"));
            stage.setTitle("Main");
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            DialogPane dialogPane1 = alert.getDialogPane();
            dialogPane1.setStyle("-fx-font-family: serif;");
            alert.setTitle(alertTitleText);
            alert.setHeaderText(alertHeaderText);
            alert.setContentText(alertContextText);
            alert.showAndWait();
        }
    }

    /**
     * Closes the database connection and logs out of the program.
     *
     * @param event Logout button clicked.
     */
    public void onLogoutButton(ActionEvent event) {
        //Button sourceButton = (Button) event.getSource();
        //exitButton.setText(sourceButton.getText());
        DBConnection.closeConnection();
        System.exit(0);
    }

    /**
     * Initializes the Login Controller.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Locale.setDefault(Locale.FRANCE);
        rb = ResourceBundle.getBundle("Languages/Login", Locale.getDefault());

        zoneIdLabel.setText(String.valueOf(ZoneId.systemDefault()));
        zoneIdLabel2.setText(rb.getString("Zone_ID2"));
        loginPageLabel.setText(rb.getString("Login_Page"));
        usernameLabel.setText(rb.getString("Username"));
        passwordLabel.setText(rb.getString("Password"));
        loginButton.setText(rb.getString("Login"));
        logoutButton.setText(rb.getString("Logout"));
        alertTitleText = rb.getString("alertTitleText");
        alertHeaderText = rb.getString("alertHeaderText");
        alertContextText = rb.getString("alertContextText");
    }
}



