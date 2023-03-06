package com.c195.schedulingappbb;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoginForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
        stage.setTitle("Initech Desktop Scheduler");
        stage.setScene(scene);
        stage.show();

        JDBC.openConnection();
        JDBC.closeConnection();
    }

    public static void main(String[] args) {
        launch();
    }
}