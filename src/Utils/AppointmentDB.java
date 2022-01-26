package Utils;

import Model.Appointment;
import Model.ReportByMonth;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Appointment database.
 *
 * @author Erik Nilson
 */
public class AppointmentDB {

    public static void select() throws SQLException {
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int appointment_ID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            int customer_ID = rs.getInt("Customer_ID");
            int user_ID = rs.getInt("User_ID");
            int contact_ID = rs.getInt("Contact_ID");

            ListProvider.addAppointment(new Appointment(appointment_ID, title, description, location, type, start, end, customer_ID, user_ID, contact_ID));
        }
    }

    /**
     * Adds appointment information.
     *
     * @throws SQLException
     */
    public static boolean addAppointment(int appointment_id, String title, String description, String location, String type, Timestamp start, Timestamp end, int contact_id, int customer_id, int user_id) throws SQLException {
        try {
            String sql1 = ("INSERT INTO appointments SET Appointment_ID='" + appointment_id + "',Title='" + title + "',Description='" + description + "', Location='" + location + "', Type='" + type + "', Start='" + start + "', End='" + end + "', Customer_ID='" + customer_id + "', User_ID='" + user_id + "', Contact_ID=" + contact_id);
            PreparedStatement ps1 = DBConnection.connection.prepareStatement(sql1);
            ps1.executeUpdate(sql1);
        } catch (SQLException exc) {
            System.out.println("SQLException: " + exc.getMessage());
        }
        return false;
    }

    /**
     * Updates and saves existing appointment information.
     *
     * @throws SQLException
     */
    public static boolean updateAppointment(int appointment_id, String title, String description, String location, String type, Timestamp start, Timestamp end, int contact_id, int user_id, int customer_id) throws SQLException {
        try {
            String sql2 = ("UPDATE appointments SET Title='" + title + "',Description='" + description + "', Location='" + location + "', Type='" + type + "', Start='" + start + "', End='" + end + "', User_ID='" + user_id + "', Contact_ID='" + contact_id + "', Customer_ID='" + customer_id + "' WHERE Appointment_ID =" + appointment_id);
            PreparedStatement ps2 = DBConnection.connection.prepareStatement(sql2);
            ps2.executeUpdate(sql2);
        } catch (SQLException exc) {
            System.out.println("SQLException: " + exc.getMessage());
        }
        return false;
    }

    /**
     * Deletes an appointment.
     *
     * @param appointment_ID This ID is needed to delete the appointment.
     * @return returns true or false.
     */
    public static boolean deleteAppointment(int appointment_ID) {

        try {
            String query1 = "DELETE FROM appointments WHERE Appointment_ID =" + appointment_ID;
            PreparedStatement ps1 = DBConnection.connection.prepareStatement(query1);
            ps1.executeUpdate(query1);

        } catch (SQLException exc) {
            System.out.println("SQLException: " + exc.getMessage());
        }
        return false;
    }

    /**
     * Used for appointment by month/type report.
     *
     * @throws SQLException
     */
    public static ObservableList<ReportByMonth> getApptByMonthType(String selectedMonth) throws SQLException {
        ObservableList<ReportByMonth> monthAndTypeReport = FXCollections.observableArrayList();

        String selectAppt = "SELECT count(Title) as Count,Type,MONTHNAME(Start) as mn,MONTH(Start) as Month from appointments where monthname(Start) = '" + selectedMonth + "' group by MONTH(Start),mn,Type order by Month;";
        PreparedStatement ps = DBConnection.connection.prepareStatement(selectAppt);


        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {

            String month = resultSet.getString("Month");
            String type = resultSet.getString("Type");
            int count = resultSet.getInt("Count");

            ReportByMonth rbm = new ReportByMonth(month, type, count);
            monthAndTypeReport.add(rbm);

        }
        return monthAndTypeReport;
    }
}
