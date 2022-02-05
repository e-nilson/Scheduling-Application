package Utils;

import Model.Appointment;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User database.
 *
 * @author Erik Nilson
 */
public class UserDB {

    public static void select() throws SQLException {
        String sql = "SELECT * FROM users";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int userId = rs.getInt("User_ID");
            String username = rs.getString("User_Name");
            String password = rs.getString("password");
            System.out.println(userId + "|" + username + "|" + password);

            ListProvider.addUser(new User(userId, username, password));
        }
    }


    /** This method gets the user based on the User ID. */
    public static boolean getLoggedInUser(String username, String password) {
        try {
            String sql = "SELECT User_Name, Password from users";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("User_Name").equals(username) && resultSet.getString("Password").equals(password))
                    return true;
            }
            return false;

        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Finds appointments based on user id
     */
    public static ObservableList<Appointment> getApptsByUser(int userId) {
        ObservableList<Appointment> userApptResult = FXCollections.observableArrayList();
        AppointmentDB appointmentDB = new AppointmentDB();

        for (Appointment appointment : ListProvider.getAllAppointments()){
            if (appointment.getUser_ID() == userId){
                userApptResult.add(appointment);
            }
        }
        return userApptResult;
    }
}
