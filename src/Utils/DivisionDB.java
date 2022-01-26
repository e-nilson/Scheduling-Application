package Utils;

import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Division database.
 *
 * @author Erik Nilson
 */
public class DivisionDB {

    public static void select() throws SQLException {
        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int division_ID = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            int country_ID = rs.getInt("Country_ID");

            ListProvider.addDivision(new Division(division_ID, division, country_ID));
        }
    }

    /**
     * the selected division
     */
    public static Division divison;

    /**
     * Gets a division based on a id selected.
     *
     * @param id the division id
     * @throws SQLException throws a exception if there is an error in the query
     */
    public static Division getDivisionByID(int id) throws SQLException {
        try {
            String sql = ("SELECT * FROM first_level_divisions WHERE Division_ID = " + id);
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                divison = (new Division(
                        rs.getInt("Division_ID"),
                        rs.getString("Division"),
                        rs.getInt("Country_ID")));
            }

            return divison;

        } catch (SQLException exc) {
            Logger.getLogger(exc.toString());
        }
        return null;
    }

        /**
         * List of US divisions.
         */
        public static ObservableList<Division> usFilteredDivision = FXCollections.observableArrayList();

        /**
         * US divisions.
         *
         * @throws SQLException
         */
        public static ObservableList<Division> getusFilteredDivision() throws SQLException {
            usFilteredDivision.clear();
            try {
                String sql = ("SELECT * FROM first_level_divisions WHERE COUNTRY_ID = 1");
                PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    usFilteredDivision.add(new Division(
                            rs.getInt("Division_ID"),
                            rs.getString("Division"),
                            rs.getInt("Country_ID")));
                }

                return usFilteredDivision;

            } catch (SQLException e) {
                Logger.getLogger(e.toString());
            }
            return null;
        }

    /**
     * List of UK divisions
     */
    public static ObservableList<Division> ukFilteredDivision = FXCollections.observableArrayList();

    /**
     *  Gets the UK divisions.
     *
     * @throws SQLException
     */
    public static ObservableList<Division> getukFilteredDivision() throws SQLException {
        ukFilteredDivision.clear();
        try {
            String sql = ("SELECT * FROM first_level_divisions WHERE COUNTRY_ID = 2");
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ukFilteredDivision.add(new Division(
                        rs.getInt("Division_ID"),
                        rs.getString("Division"),
                        rs.getInt("Country_ID")));
            }

            return ukFilteredDivision;

        } catch (SQLException e) {
            Logger.getLogger(e.toString());
        }
        return null;
    }

    /**
     * List of Canada divisions.
     */
    public static ObservableList<Division> canadaFilteredDivision = FXCollections.observableArrayList();

    /**
     * Gets the Canada divisions.
     *
     * @throws SQLException
     */
    public static ObservableList<Division> getcanadaFilteredDivision() throws SQLException {
        canadaFilteredDivision.clear();
        try {
            String sql = ("SELECT * FROM first_level_divisions WHERE COUNTRY_ID = 3");
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                canadaFilteredDivision.add(new Division(
                        rs.getInt("Division_ID"),
                        rs.getString("Division"),
                        rs.getInt("Country_ID")));
            }

            return canadaFilteredDivision;

        } catch (SQLException e) {
            Logger.getLogger(e.toString());
        }
        return null;
    }
}