package Utils;

import Model.Customer;

import java.sql.*;

/**
 * Customer database.
 *
 * @author Erik Nilson
 */
public class CustomerDB {

    public static void getCustomers() throws SQLException {
        PreparedStatement ps = DBConnection.connection.prepareStatement("SELECT * FROM customers");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int customer_ID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            int division_ID = rs.getInt("Division_ID");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");

            ListProvider.addCustomer(new Customer(customer_ID, customerName, address, postalCode, phone, division_ID));
        }
    }

    /**
     * Add new customers.
     *
     * @throws SQLException
     */
    public static boolean addCustomer(int customer_id, String customerName, String address, String postalCode, String phone, int division_id) throws SQLException {
        try {
            String sql1 = "INSERT INTO customers SET Customer_ID='" + customer_id + "', Customer_Name='" + customerName + "', Address='" + address + "', Postal_Code='" + postalCode + "', Phone='" + phone + "', Division_ID= " + division_id;
            PreparedStatement ps1 = DBConnection.connection.prepareStatement(sql1);
            ps1.executeUpdate(sql1);
        } catch (SQLException exc) {
            System.out.println("SQLException: " + exc.getMessage());
        }
        return false;
    }

    /**
     * Update and save existing customer information.
     *
     * @throws SQLException
     */
    public static boolean updateCustomer(int customer_id, String customerName, String address, String postalCode, String phone, int division_id) throws SQLException {
        try {
            String sql = "UPDATE customers SET Customer_Name='" + customerName + "', Address='" + address + "', Postal_Code='" + postalCode + "', Phone='" + phone + "', Division_ID= " + division_id + " WHERE Customer_ID =" + customer_id;
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.executeUpdate(sql);

        } catch (SQLException exc) {
            System.out.println("SQLException: " + exc.getMessage());
        }
        return false;
    }

    /**
     * Deletes a customer.
     *
     * @param customer_ID This ID is needed to delete the customer.
     */
    public static boolean deleteCustomer(int customer_ID) {
        try {
            String query1 = "DELETE FROM customers WHERE Customer_ID=" + customer_ID;
            PreparedStatement ps1 = DBConnection.connection.prepareStatement(query1);
            ps1.executeUpdate(query1);

            String query2 = "DELETE FROM appointments WHERE Customer_ID=" + customer_ID;
            PreparedStatement ps2 = DBConnection.connection.prepareStatement(query2);
            ps2.executeUpdate(query2);

        } catch (SQLException exc) {
            System.out.println("SQLException: " + exc.getMessage());
        }
        return false;
    }
}
