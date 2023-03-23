package DAO;

import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class CustomerImpl {

    public static int customerInsert(String customerName,
                                     String address,
                                     String postalCode,
                                     String phoneNumber,
                                     int divisionId) {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phoneNumber);
            ps.setInt(5, divisionId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected;

        } catch (Exception e) {
            return -1;
        }
    }

    public static int customerUpdate(int customerId,
                                     String customerName,
                                     String address,
                                     String postalCode,
                                     String phoneNumber,
                                     int divisionId) {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phoneNumber);
            ps.setInt(5, divisionId);
            ps.setInt(6, customerId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected;

        } catch (Exception e) {
            return -1;
        }
    }

    //TODO: Add delete appointment functionality when Appointments finished
    public static int customerDelete(int customerId) {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ps.setInt(1, customerId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected;

        } catch (Exception e) {
            return -1;
        }
    }

    public static int customerAssocApptDelete(int customerId) {
        String sql = "DELETE FROM appointments WHERE Customer_ID = ?";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ps.setInt(1, customerId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected;

        } catch (Exception e) {
            return -1;
        }
    }

    //TODO: Overload select to get data for reports, use WHERE with a setter to select ID
    public static void customerSelect() {
        String sql = "SELECT c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, c.Phone, f.Division, c2.Country\n" +
                "FROM customers AS c \n" +
                "INNER JOIN first_level_divisions f ON c.Division_ID=f.Division_ID\n" +
                "INNER JOIN countries c2 ON f.Country_ID=c2.Country_ID";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String phone = rs.getString("Phone");
                String postalCode = rs.getString("Postal_Code");
                String division = rs.getString("Division");
                String country = rs.getString("Country");

                Customer newCustomer = new Customer(customerId, name, address, phone, country, division, postalCode);
                Customer.allCustomers.add(newCustomer);

            }

        } catch (Exception e) {

        }
    }




}
