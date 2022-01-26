package Main;

import Utils.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.SQLException;

/**
 * The Scheduling Application is used for the management of appointments and contacts.
 *
 * JavaDoc is located in folder the javadoc within the C195 folder. C195/javadoc.
 *
 * @author Erik Nilson
 */
public class Main extends Application {

    /**
     * The start method that loads the initial screen.
     *
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
        root.setStyle("-fx-font-family: 'Serif';");
        primaryStage.setScene(new Scene(root, 500, 325));
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {

        DBConnection.openConnection();

        AppointmentDB.select();

        ContactDB.select();

        CountryDB.select();

        CustomerDB.getCustomers();

        DivisionDB.select();

        UserDB.select();

        launch(args);

        DBConnection.closeConnection();

    }
}