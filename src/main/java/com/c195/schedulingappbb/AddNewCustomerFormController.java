package com.c195.schedulingappbb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static com.c195.schedulingappbb.MainFormController.loadForm;


public class AddNewCustomerFormController {
    static Stage stage;

    @FXML
    Button cancelButton;

    public void onCancel(ActionEvent event) throws IOException {
        loadForm(stage, event, "MainForm.fxml");
    }
}
