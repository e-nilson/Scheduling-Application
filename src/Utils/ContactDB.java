package Utils;

import Model.Appointment;
import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contact database.
 *
 * @author Erik Nilson
 */
public class ContactDB {

    public static void select() throws SQLException {
        String sql = "SELECT * FROM contacts";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int contact_ID = rs.getInt("Contact_ID");
            String contact_Name = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            ListProvider.addContact(new Contact(contact_ID, contact_Name, email));
        }
    }

    /**
     * Used for the appointment and contact report.
     */
    public static ObservableList<Appointment> getApptsByContact(int contactID) {
        ObservableList<Appointment> contactApptResult = FXCollections.observableArrayList();
        AppointmentDB appointmentDB = new AppointmentDB();

        for (Appointment appointment : ListProvider.getAllAppointments()){
            if (appointment.getContact_ID() == contactID){
                contactApptResult.add(appointment);
            }
        }
        return contactApptResult;
    }
}
