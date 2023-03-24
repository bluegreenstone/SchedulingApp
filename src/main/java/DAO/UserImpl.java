package DAO;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static com.c195.schedulingappbb.LoginFormController.userList;

public abstract class UserImpl {

    /**
     * Validates user login credentials against database
     * @param userName
     * @param password
     * @return
     */
    public static Boolean validateUser(String userName, String password) {
        try {
            String sql = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getInt("User_ID"));
                userList.add(user);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
