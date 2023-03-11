package com.c195.schedulingappbb;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

import static com.c195.schedulingappbb.MainFormController.loadForm;

public class ModifyAppointmentFormController {
    Stage stage;

    public void onCancel(ActionEvent event) throws IOException {
        loadForm(stage, event, "MainForm.fxml");
    }
}
