package com.c195.schedulingappbb;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {

    public Label locationLabel;
    Stage stage;

    public void onLogin(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainFormController.appointmentAlert(Appointment.getAllAppointments());

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public static ZoneId getLocalZoneId() {
        return ZoneId.systemDefault();
    }

    public static void setLocationLabel(Label locationLabel) {
        locationLabel.setText("USER LOCATION: " + getLocalZoneId().toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLocationLabel(locationLabel);
    }
}