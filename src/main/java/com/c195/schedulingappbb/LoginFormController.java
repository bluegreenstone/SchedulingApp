package com.c195.schedulingappbb;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

import static DAO.UserImpl.validateUser;
import static com.c195.schedulingappbb.MainFormController.loadForm;

public class LoginFormController implements Initializable {

    public Label locationLabel;
    public TextField usernameTextField;
    public Label initechConsultingLabel;
    public Label desktopSchedulerLabel;
    public TextField passwordTextField;
    public Button loginButton;
    public Label passwordLabel;
    public Label usernameLabel;
    Stage stage;

    public static ObservableList<User> userList = FXCollections.observableArrayList();

    public static String alertTitle = "Error";
    public static String alertHeader = "Invalid Username or Password";
    public static String alertContent = "Please try again.";

    /**
     * This method validates the user's login credentials and loads the MainForm if successful while also logging attempt.
     * @param event
     * @throws IOException
     */
    public void onLogin(ActionEvent event) throws IOException {
        if (validateUser(usernameTextField.getText(), passwordTextField.getText())) {
            writeLoginActivity(true, usernameTextField.getText(), passwordTextField.getText());
            System.out.println(usernameTextField.getText());
            System.out.println(passwordTextField.getText());
            loadForm(event, "MainForm.fxml");
            MainFormController.appointmentAlert(Appointment.allAppointments);
        } else {
            writeLoginActivity(false, usernameTextField.getText(), passwordTextField.getText());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(alertTitle);
            alert.setHeaderText(alertHeader);
            alert.setContentText(alertContent);
            alert.showAndWait();
        }
    }

    /**
     * This method returns the user's local ZoneId.
     * @return
     */
    public static ZoneId getLocalZoneId() {
        return ZoneId.systemDefault();
    }

    /**
     * This method sets the location label to the user's local ZoneId.
     * @param locationLabel
     */
    public static void setLocationLabel(Label locationLabel) {
        locationLabel.setText("USER LOCATION: " + getLocalZoneId().toString());
    }

    /**
     * This method writes login attempts to a text file.
     * @param loginSuccess
     * @param username
     * @param password
     */
    public static void writeLoginActivity(Boolean loginSuccess, String username, String password) {
        try {
            FileWriter fileWriter = new FileWriter("login_activity.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println("Username: " + username + ", Password: " + password + ", Login Success: " + loginSuccess + ", Date and Time: " + LocalDateTime.now());
            printWriter.close();
        } catch (IOException e) {

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLocationLabel(locationLabel);

        ResourceBundle rb = ResourceBundle.getBundle("com.c195.schedulingappbb/login", Locale.FRENCH);
        if (Locale.getDefault().getLanguage().equals("fr")) {
            initechConsultingLabel.setText(rb.getString("name"));
            desktopSchedulerLabel.setText(rb.getString("product"));
            usernameLabel.setText(rb.getString("Username"));
            passwordLabel.setText(rb.getString("Password"));
            loginButton.setText(rb.getString("login"));

            String location = rb.getString("location");
            locationLabel.setText(location + getLocalZoneId().toString());

            alertTitle = rb.getString("login_failed_title");
            alertHeader = rb.getString("login_failed_header");
            alertContent = rb.getString("login_failed_body");
        }
    }
}