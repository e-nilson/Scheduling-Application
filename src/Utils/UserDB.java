package Utils;

import Model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB {

    public static void select() throws SQLException {

        String sql = "SELECT * FROM users";

        //use connection reference
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);

        //returns a result set. executes the qry. holds objects from tables (users in this instance)
        ResultSet rs = ps.executeQuery();

        //rs.next advances through the result set
        while(rs.next()){
            int userId = rs.getInt("User_ID");
            String username = rs.getString("User_Name");
            String password = rs.getString("password");
            System.out.println(userId + "|" + username + "|" + password);

            ListProvider.addUser(new User(userId, username, password));
        }
    }
}
