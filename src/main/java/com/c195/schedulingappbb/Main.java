package com.c195.schedulingappbb;

import DAO.CustomerImpl;
import DAO.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.*;
import java.util.TimeZone;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoginForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 760);
        stage.setResizable(false);
        stage.setTitle("Initech Desktop Scheduler");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        LocalDate myLd = LocalDate.of(2020, 03, 21);
        LocalTime myLt = LocalTime.of(22, 0);

        LocalDateTime myLtd = LocalDateTime.of(myLd, myLt);

        ZoneId myZoneId = ZoneId.systemDefault();

        ZonedDateTime myZdt = ZonedDateTime.of(myLtd, myZoneId);

        System.out.println(myZdt);

        JDBC.openConnection();
        launch();
        JDBC.closeConnection();
    }
}