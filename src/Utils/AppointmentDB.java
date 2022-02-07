package Utils;

import Model.Appointment;
import Model.ReportByMonth;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Appointment database.
 *
 * @author Erik Nilson
 */
public class AppointmentDB {

    public static void getAllAppointments() throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        PreparedStatement ps = DBConnection.connection.prepareStatement("SELECT * FROM appointments");
        ResultSet rs = ps.executeQuery();

        //Use LocalDateTime instead of Timestamp. .toLocalDateTime. Right now they are being displayed in UTC.
        // need to get rid of timezone conversions
        // zip up project to test in VM.

        while (rs.next()) {
            int appointment_ID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toInstant().atOffset(ZoneOffset.from(ZonedDateTime.now())).toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toInstant().atOffset(ZoneOffset.from(ZonedDateTime.now())).toLocalDateTime();
            //LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            //LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
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
    public static boolean addAppointment(int appointment_ID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int contact_ID, int customer_ID, int user_ID) throws SQLException {

        ZonedDateTime ldtZoned = start.atZone(ZoneId.systemDefault());
        ZonedDateTime utcStart = ldtZoned.withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime utcEnd = utcStart.plusHours(1);
        LocalDateTime startConvert = utcStart.toLocalDateTime();
        LocalDateTime endConvert = utcEnd.toLocalDateTime();

        try {
            String sql1 = "INSERT INTO appointments SET Appointment_ID='" + appointment_ID + "',Title='" + title + "',Description='" + description + "', Location='" + location + "', Type='" + type + "', Start='" + startConvert + "', End='" + endConvert + "', Customer_ID='" + customer_ID + "', User_ID='" + user_ID + "', Contact_ID=" + contact_ID;
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
    public static boolean updateAppointment(int appointment_ID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int contact_ID, int user_ID, int customer_ID) throws SQLException {
        
        ZonedDateTime ldtZoned = start.atZone(ZoneId.systemDefault());
        ZonedDateTime utcStart = ldtZoned.withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime utcEnd = utcStart.plusHours(1);
        LocalDateTime startConvert = utcStart.toLocalDateTime();
        LocalDateTime endConvert = utcEnd.toLocalDateTime();

        try {
            String sql2 = "UPDATE appointments SET Title='" + title + "',Description='" + description + "', Location='" + location + "', Type='" + type + "', Start='" + startConvert + "', End='" + endConvert + "', User_ID='" + user_ID + "', Contact_ID='" + contact_ID + "', Customer_ID='" + customer_ID + "' WHERE Appointment_ID =" + appointment_ID;
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

    public static Appointment appointmentOverlap(LocalDateTime start, LocalDateTime end, int customer_ID) throws SQLException{
        Appointment apptOverlap = null;

        ObservableList<Appointment> apptList = CustomerDB.getCustAppt(customer_ID);

        for (Appointment appointment : apptList) {
            if (start.isAfter(appointment.getStart()) && start.isBefore(appointment.getEnd()) ||
                    end.isAfter(appointment.getStart()) && end.isBefore(appointment.getEnd()) ||
                    start.isBefore(appointment.getStart()) && end.isAfter(appointment.getStart()) ||
                    start.equals(appointment.getStart()) && end.equals(appointment.getEnd()) ||
                    start.equals(appointment.getStart()) || end.equals(appointment.getStart())) {

                apptOverlap = appointment;
            }
        }
        return apptOverlap;
    }
}
