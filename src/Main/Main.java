package Main;

import Utils.AppointmentDB;
import Utils.CustomerDB;
import Utils.DBConnection;
import Utils.UserDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
        root.setStyle("-fx-font-family: 'Serif';");
        //primaryStage.setTitle("User Login");
        primaryStage.setScene(new Scene(root, 500, 325));
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {

        DBConnection.openConnection();

        //reaches to database
        UserDB.select();

        CustomerDB.select();

        AppointmentDB.select();

        launch(args);

        DBConnection.closeConnection();

    }
}