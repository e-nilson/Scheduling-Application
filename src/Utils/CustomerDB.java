package Utils;

import Model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDB {

    public static void select() throws SQLException {

        Object sql = "SELECT * FROM customers";

        //use connection reference
        PreparedStatement ps = DBConnection.connection.prepareStatement("SELECT * FROM customers");

        //returns a result set. executes the qry. holds objects from tables (users in this instance)
        ResultSet rs = ps.executeQuery();

        //rs.next advances through the result set
        while (rs.next()) {
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            //String country = rs.getString("Country");
            //String state = rs.getString("State");;
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            //rs.getTimestamp("Create_Date").toLocalDateTime(),
            //rs.getString("Created_By"),
            //rs.getTimestamp("Last_Update").toLocalDateTime(),
            //rs.getString("Last_Updated_By"),
            //rs.getInt("Division_ID")));

            ListProvider.addCustomer(new Customer(customerId, customerName, address, postalCode, phone));
        }
    }
}

