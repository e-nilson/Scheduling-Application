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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller class that provides logic for the Login screen of the application.
 *
 * @author Erik Nilson
 */
public class LoginController implements Initializable {
    Stage stage;
    Parent scene;

    /**
     * The username text field.
     */
    @FXML
    public TextField usernameTextField;

    /**
     * The password text field.
     */
    @FXML
    public TextField passwordTextField;

    /**
     * The zone ID label.
     */
    @FXML
    public Label zoneIdLabel;

    /**
     * The zone ID 2 label.
     */
    @FXML
    public Label zoneIdLabel2;

    /**
     * The login page label.
     */
    @FXML
    public Label loginPageLabel;

    /**
     * The username label.
     */
    @FXML
    public Label usernameLabel;

    /**
     * The password label.
     */
    @FXML
    public Label passwordLabel;

    /**
     * The login button.
     */
    @FXML
    public Button loginButton;

    /**
     * The logout label.
     */
    @FXML
    public Button logoutButton;

    /**
     * The alert title text.
     */
    @FXML
    private String alertTitleText;

    /**
     * The alert header text.
     */
    @FXML
    private String alertHeaderText;

    /**
     * The context text.
     */
    @FXML
    private String alertContextText;

    /**
     * Accesses the main appointment screen with proper username and password. Use "test" for testing.
     *
     * After a successful login, it checks for appointments within the next 15 minutes.
     *
     * Send success and unsuccessful login information to login_activity.txt file.
     *
     * Contains appropriate error and alert windows.
     *
     * @param event Login button clicked.
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    private void onLoginButton(ActionEvent event) throws IOException, SQLException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        boolean match = false;

        for (User u : ListProvider.getAllUsers()) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                match = true;

                ListProvider.getAllAppointments();

                for (Appointment appointment : ListProvider.getAllAppointments()) {
                    LocalDateTime within15Minutes = LocalDateTime.now();
                    match = true;

                    if (within15Minutes.isAfter(appointment.getStart().toLocalDateTime().minusMinutes(15)) && within15Minutes.isBefore(appointment.getStart().toLocalDateTime())) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        DialogPane dialogPane1 = alert.getDialogPane();
                        dialogPane1.setStyle("-fx-font-family: serif;");
                        alert.setContentText("Appointment ID: " + appointment.getAppointment_ID() + " starts at " + appointment.getStart());
                        alert.showAndWait();
                        match = true;
                        break;
                    } else {
                        match = false;
                    }
                }

                if (match != true) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    DialogPane dialogPane1 = alert.getDialogPane();
                    dialogPane1.setStyle("-fx-font-family: serif;");
                    alert.setContentText("There are no appointments scheduled within the next 15 minutes.");
                    alert.showAndWait();
                }
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
                scene.setStyle(("-fx-font-family: 'serif';"));
                stage.setTitle("Main");
                stage.setScene(new Scene(scene));
                stage.show();

                fileLogValid(username);

            } else if (usernameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                DialogPane dialogPane1 = alert.getDialogPane();
                dialogPane1.setStyle("-fx-font-family: serif;");
                alert.setTitle(alertTitleText);
                alert.setHeaderText(alertHeaderText);
                alert.setContentText(alertContextText);
                alert.showAndWait();

                fileLogInvalid(username);
            }
        }
    }

    /**
     * Closes the database connection and logs out of the program.
     *
     * @param event Logout button clicked.
     */
    public void onLogoutButton(ActionEvent event) {
        DBConnection.closeConnection();
        System.exit(0);
    }

    /**
     * Logs successful login attempts in login_activity.txt file.
     *
     * Lamdba expression used to simplify/reduce amount of code.
     */
    public static void fileLogValid(String username) {
        try {
            String loginLog = "login_activity.txt";
            File file = new File(loginLog);
            FileWriter fw = new FileWriter(loginLog, true);
            PrintWriter results = new PrintWriter(fw);
            LocalDateTime localDateTime = LocalDateTime.now();

            results.println("User: " + username +  " Successfully logged in at: " + Timestamp.valueOf(localDateTime) );
            results.close();

            // Lambda prints out message when users login is successful.
            new Thread(() -> System.out.println(username + " Successful Login"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Logs unsuccessful login attempts in login_activity.txt file.
     *
     * Lamdba expression used to simplify/reduce amount of code.
     */
    public static void fileLogInvalid(String username) {
        try {
            String loginLog = "login_activity.txt";
            File file = new File(loginLog);
            FileWriter fw = new FileWriter(loginLog, true);
            PrintWriter results = new PrintWriter(fw);
            LocalDateTime localDateTime = LocalDateTime.now();

            results.println("User: " + username +  " Unsuccessful log in at: " + Timestamp.valueOf(localDateTime) );
            results.close();

            // Lambda prints out message when users login is unsuccessful.
            new Thread(() -> System.out.println(username + "Unsuccessful Login"));

        } catch (IOException e) {
            e.printStackTrace();
        }
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



