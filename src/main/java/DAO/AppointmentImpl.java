package DAO;

import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public abstract class AppointmentImpl {

    /**
     * This method inserts an appointment into the database.
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerId
     * @param userId
     * @param contactId
     * @return
     */
    public static int appointmentInsert(
            String title,
            String description,
            String location,
            String type,
            Timestamp start,
            Timestamp end,
            int customerId,
            int userId,
            int contactId) {
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) \n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ps.setInt(7, customerId);
            ps.setInt(8, userId);
            ps.setInt(9, contactId);


            int rowsAffected = ps.executeUpdate();
            return rowsAffected;

        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * This method updates an appointment in the database.
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerId
     * @param userId
     * @param contactId
     * @param apptId
     * @return
     */
    public static int appointmentUpdate(String title,
                                        String description,
                                        String location,
                                        String type,
                                        Timestamp start,
                                        Timestamp end,
                                        int customerId,
                                        int userId,
                                        int contactId,
                                        int apptId) {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, " +
                "End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ps.setInt(7, customerId);
            ps.setInt(8, userId);
            ps.setInt(9, contactId);
            ps.setInt(10, apptId);


            int rowsAffected = ps.executeUpdate();
            return rowsAffected;

        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * This method deletes an appointment from the database.
     * @param apptId
     * @return
     */
    public static int appointmentDelete(int apptId) {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ps.setInt(1, apptId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected;

        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * This method selects all appointments from the database.
     */
    public static void appointmentSelect() {
        String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, c.Contact_Name, a.Type, a.Start, a.End, a.Customer_ID, a.User_ID\n" +
                "FROM appointments AS a\n" +
                "INNER JOIN contacts c ON c.Contact_ID=a.Contact_ID";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String contact = rs.getString("Contact_Name");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");

                Appointment newAppointment = new Appointment(appointmentId, title, description,
                        location, contact, type, start, end, customerId, userId);
                Appointment.allAppointments.add(newAppointment);
            }
        } catch (Exception e) {

        }
    }

    /**
     * This method selects all appointments by contact from the database.
     * @param contactId
     */
    public static void appointmentSelectByContact(int contactId) {
        String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, c.Contact_Name, a.Type, a.Start, a.End, a.Customer_ID, a.User_ID\n" +
                "FROM appointments AS a\n" +
                "INNER JOIN contacts c ON c.Contact_ID=a.Contact_ID\n" +
                "WHERE a.Contact_ID = ?";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, contactId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String contact = rs.getString("Contact_Name");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");

                Appointment newAppointment = new Appointment(appointmentId, title, description,
                        location, contact, type, start, end, customerId, userId);
                Appointment.contactAppointments.add(newAppointment);
            }
        } catch (Exception e) {
        }
    }
}
