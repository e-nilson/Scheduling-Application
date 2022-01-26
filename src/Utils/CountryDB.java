package Utils;

import Model.Country;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Country database.
 *
 * @author Erik Nilson
 */
public class CountryDB {

    public static void select() throws SQLException {
        String sql = "SELECT * FROM countries";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int country_ID = rs.getInt("Country_ID");
            String country = rs.getString("Country");
            System.out.println(country_ID + "|" + country);

            ListProvider.addCountry(new Country(country_ID, country));
        }
    }
}
