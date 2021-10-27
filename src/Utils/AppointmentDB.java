package Utils;

import Model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentDB {

    public static void select() throws SQLException {

        String sql = "SELECT * FROM appointments";


        //use connection reference
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);

        //returns a result set. executes the qry. holds objects from tables (appointments in this instance)
        ResultSet rs = ps.executeQuery();

        //rs.next advances through the result set
        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            //LocalDateTime start = rs.getTimestamp("Start").toInstant().atOffset(ZoneOffset.from(ZonedDateTime.now())).toLocalDateTime();
            //LocalDateTime end = rs.getTimestamp("End").toInstant().atOffset(ZoneOffset.from(ZonedDateTime.now())).toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");

            ListProvider.addAppointment(new Appointment(appointmentId, title, description, location, type, /*start, end,*/ customerId, userId, contactId));

        }
    }
}
