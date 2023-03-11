package DAO;

import com.c195.schedulingappbb.AddNewCustomerFormController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Query {

    public static void countrySelect() {
        String sql = "SELECT c.Country\n" +
                "FROM countries AS c";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String country = rs.getString("Country");
                AddNewCustomerFormController.countryList.add(country);
            }
        } catch (Exception e) {
        }
    }

    public static void divisionSelect(String country) {
        String sql = "SELECT d.Division_ID, d.Division, c.Country\n" +
                "FROM first_level_divisions AS d\n" +
                "INNER JOIN countries c ON c.Country_ID=d.Country_ID\n" +
                "WHERE c.Country = ?;";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, country);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String division = rs.getString("Division");

                AddNewCustomerFormController.divisionList.add(division);
            }

        } catch (Exception e) {
        }
    }

    public static int divisionIdFromNameSelect(String division) {
        String sql = "SELECT d.Division_ID, d.Division\n" +
                "FROM first_level_divisions AS d\n" +
                "Where d.Division = ?;";

        int divisionId = -1;

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, division);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                divisionId = rs.getInt("Division_ID");
            }
        } catch (Exception e) {
        }

        return divisionId;
    }
}
