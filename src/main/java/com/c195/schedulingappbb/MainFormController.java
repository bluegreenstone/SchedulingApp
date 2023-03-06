package com.c195.schedulingappbb;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFormController {

    static Stage stage;

    public void onLogout(ActionEvent event) throws IOException {
        loadForm(stage, event, "LoginForm.fxml");
    }

    public void onAddNewCustomer(ActionEvent event) throws IOException {
        loadForm(stage, event, "AddNewCustomerForm.fxml");
    }

    public void onModifyCustomer(ActionEvent event) throws IOException {
        loadForm(stage, event, "ModifyCustomerForm.fxml");
    }

    public static void loadForm(Stage stage, ActionEvent event, String form) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(form));
        Scene scene = new Scene(fxmlLoader.load());

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
