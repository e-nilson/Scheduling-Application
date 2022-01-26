package Utils;

import Model.User;
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
}
