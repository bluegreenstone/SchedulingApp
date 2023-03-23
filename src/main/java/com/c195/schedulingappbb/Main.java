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

        System.out.println(ZoneId.systemDefault());
//        ZoneId.getAvailableZoneIds().stream().forEach(System.out::println);
//        ZoneId.getAvailableZoneIds().stream().filter(z->z.contains("America")).forEach(System.out::println);
        //ZonedDateTime Class
        LocalDate myLd = LocalDate.of(2020, 03, 21);
        LocalTime myLt = LocalTime.of(22, 0);

        LocalDateTime myLtd = LocalDateTime.of(myLd, myLt);

        ZoneId myZoneId = ZoneId.systemDefault();

        ZonedDateTime myZdt = ZonedDateTime.of(myLtd, myZoneId);

        System.out.println(myZdt);

        // Need to extract components to put in SQL
        System.out.println(myZdt.toLocalDate());
        System.out.println(myZdt.toLocalTime());
        System.out.println(myZdt.toLocalDate().toString() + " " + myZdt.toLocalTime().toString());

        // Convert user time to UTC
        System.out.println("User time: " + myZdt);
        ZoneId utcZoneId = ZoneId.of("UTC");
        ZonedDateTime utcZdt = ZonedDateTime.ofInstant(myZdt.toInstant(), utcZoneId);
        System.out.println("User Time to UTC: " + utcZdt);

        // Convert UTC to user time
        myZdt = ZonedDateTime.ofInstant(utcZdt.toInstant(), myZoneId);
        System.out.println("UTC to User Time: " + myZdt);

        System.out.println(myLtd.getDayOfWeek());




        JDBC.openConnection();
        launch();
        JDBC.closeConnection();
    }
}