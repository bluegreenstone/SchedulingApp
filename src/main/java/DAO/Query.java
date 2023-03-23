package DAO;

import com.c195.schedulingappbb.AddNewAppointmentFormController;
import com.c195.schedulingappbb.AddNewCustomerFormController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class Query {

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

    public static void contactSelect() {
        String sql = "SELECT c.Contact_Name\n" +
                "FROM contacts AS c";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("Contact_Name");
                AddNewAppointmentFormController.contactsList.add(name);
            }
        } catch (Exception e) {

        }
    }

    public static void customerSelect() {
        String sql = "SELECT c.Customer_Name\n" +
                "FROM customers AS c";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("Customer_Name");
                AddNewAppointmentFormController.customersList.add(name);
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

    public static int customerIdFromNameSelect(String customerName) {
        String sql = "SELECT c.Customer_ID\n" +
                "FROM customers AS c\n" +
                "WHERE c.Customer_Name = ?";
        int customerId = -1;

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, customerName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                customerId = rs.getInt("Customer_ID");
            }
        } catch (Exception e) {
        }
        return customerId;
    }

    public static String customerNameFromIdSelect(int customerId) {
        String sql = "SELECT c.Customer_Name\n" +
                "FROM customers AS c\n" +
                "WHERE c.Customer_ID = ?";
        String customerName = "";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                customerName = rs.getString("Customer_Name");
            }
        } catch (Exception e) {
        }
        return customerName;
    }

    public static int contactIdFromNameSelect(String contactName) {
        String sql = "SELECT c.Contact_ID\n" +
                "FROM contacts AS c\n" +
                "WHERE c.Contact_Name = ?";
        int contactId = -1;

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, contactName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                contactId = rs.getInt("Contact_ID");
            }
        } catch (Exception e) {
        }
        return contactId;
    }
}
