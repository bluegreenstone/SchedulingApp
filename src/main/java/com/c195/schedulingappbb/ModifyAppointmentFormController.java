package com.c195.schedulingappbb;

import DAO.AppointmentImpl;
import DAO.Query;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

import static DAO.Query.contactIdFromNameSelect;
import static DAO.Query.customerIdFromNameSelect;
import static com.c195.schedulingappbb.AddNewAppointmentFormController.*;
import static com.c195.schedulingappbb.AddNewCustomerFormController.*;
import static com.c195.schedulingappbb.AddNewCustomerFormController.addWarningLabelText;
import static com.c195.schedulingappbb.MainFormController.loadForm;
import static com.c195.schedulingappbb.MainFormController.warningAlert;

public class ModifyAppointmentFormController implements Initializable {
    public Label apptTitleLabel;
    public Label apptDescriptionLabel;
    public Label apptLocationLabel;
    public Label apptTypeLabel;
    public Label apptCustomerLabel;
    public Label apptStartLabel;
    public TextField apptTitleTextField;
    public TextField apptDescriptionTextField;
    public TextField apptLocationTextField;
    public TextField apptTypeTextField;
    public ComboBox<String> apptContactComboBox;
    public ComboBox<String> apptCustomerComboBox;
    public DatePicker apptDateDatePicker;
    public TextField apptStartTextField;
    public TextField apptEndTextField;
    public Button cancelButton;
    public Button saveButton;
    public Label apptContactLabel;
    public Label apptDateLabel;
    public Label apptEndLabel;
    public Label warningLabel;
    public TextField apptIdTextField;
    Stage stage;

    public void onCancel(ActionEvent event) throws IOException {
        loadForm(event, "MainForm.fxml");
    }

    public void fillApptForm(Appointment selected) {

        apptIdTextField.setText(Integer.toString(selected.getAppointmentId()));
        apptTitleTextField.setText(selected.getTitle());
        apptDescriptionTextField.setText(selected.getDescription());
        apptLocationTextField.setText(selected.getLocation());
        apptTypeTextField.setText(selected.getType());

        AddNewAppointmentFormController.setContactsComboBox(apptContactComboBox, AddNewAppointmentFormController.contactsList);
        int contactListIndex = AddNewAppointmentFormController.contactsList.indexOf(selected.getContact());
        apptContactComboBox.getSelectionModel().select(contactListIndex);

        AddNewAppointmentFormController.setCustomersComboBox(apptCustomerComboBox, AddNewAppointmentFormController.customersList);
        int customerListIndex = AddNewAppointmentFormController.customersList.indexOf(Query.customerNameFromIdSelect(selected.getCustomerId()));
        apptCustomerComboBox.getSelectionModel().select(customerListIndex);

        apptDateDatePicker.setValue(selected.getStartDateTime().toLocalDate());

        String startTime = selected.getStartDateTime().toLocalTime().toString();
        String endTime = selected.getEndDateTime().toLocalTime().toString();
        apptStartTextField.setText(startTime);
        apptEndTextField.setText(endTime);

    }

    public void onSave(ActionEvent event) throws IOException {
        apptValidationReset(apptTitleTextField,
                apptDescriptionTextField,
                apptLocationTextField,
                apptTypeTextField,
                apptContactComboBox,
                apptCustomerComboBox,
                apptDateDatePicker,
                apptStartTextField,
                apptEndTextField);

        boolean errorFlag = false;

        String warningText = "*WARNING*\n\n";

        int apptId = Integer.parseInt(apptIdTextField.getText());
        String title = apptTitleTextField.getText();
        String description = apptDescriptionTextField.getText();
        String location = apptLocationTextField.getText();
        String type = apptTypeTextField.getText();
        String contact = "";
        String customer = "";
        LocalDate date = apptDateDatePicker.getValue();
        String start = apptStartTextField.getText();
        String end = apptEndTextField.getText();


        if (stringValidation(title, apptTitleTextField)) {
            errorFlag = true;
            warningText = addWarningLabelText(apptTitleLabel, warningText);
        } else {
            title = apptTitleTextField.getText();
        }


        if (stringValidation(description, apptDescriptionTextField)) {
            errorFlag = true;
            warningText = addWarningLabelText(apptDescriptionLabel, warningText);
        } else {
            description = apptDescriptionTextField.getText();
        }


        if (stringValidation(location, apptLocationTextField)) {
            errorFlag = true;
            warningText = addWarningLabelText(apptLocationLabel, warningText);
        } else {
            location = apptLocationTextField.getText();
        }


        if (stringValidation(type, apptTypeTextField)) {
            errorFlag = true;
            warningText = addWarningLabelText(apptTypeLabel, warningText);
        } else {
            type = apptTypeTextField.getText();
        }


        if (comboBoxValidation(apptContactComboBox)) {
            errorFlag = true;
            warningText = addWarningLabelText(apptContactLabel, warningText);
        } else {
            contact = apptContactComboBox.getSelectionModel().getSelectedItem();
        }

        if (comboBoxValidation(apptCustomerComboBox)) {
            errorFlag = true;
            warningText = addWarningLabelText(apptCustomerLabel, warningText);
        } else {
            customer = apptCustomerComboBox.getSelectionModel().getSelectedItem();
        }

        if (datePickerValidation(apptDateDatePicker)) {
            errorFlag = true;
            warningText = addWarningLabelText(apptDateLabel, warningText);
        } else {
            date = apptDateDatePicker.getValue();
        }

        if (stringValidation(start, apptStartTextField)) {
            errorFlag = true;
            warningText = addWarningLabelText(apptStartLabel, warningText);
        } else {
            start = apptStartTextField.getText();
        }

        if (stringValidation(end, apptEndTextField)) {
            errorFlag = true;
            warningText = addWarningLabelText(apptEndLabel, warningText);
        } else {
            end = apptEndTextField.getText();
        }

        if (timeStringValidation(apptStartTextField.getText(), apptStartTextField)) {
            start = apptStartTextField.getText();
        } else {
            errorFlag = true;
            warningText = warningText.concat("\n\nStart time must be in 00:00 format AND in military " +
                    "time\nFor example, 08:00 or 13:00");
        }

        if (timeStringValidation(apptEndTextField.getText(), apptEndTextField)) {
            end = apptEndTextField.getText();
        } else {
            errorFlag = true;
            warningText = warningText.concat("\n\nEnd time must be in 00:00 format AND in military " +
                    "time\nFor example, 08:00 or 13:00");
        }

        try {
            LocalDate localDate = apptDateDatePicker.getValue();
            LocalTime localTimeStart = null;
            LocalTime localTimeEnd = null;

            if (checkTimeStringHoursMinutesValidation(start)) {
                localTimeStart = convertToLocalTime(start);
            } else {
                errorFlag = true;
                warningText = warningText.concat("\n\nInvalid Time! \nStart time must be in 00:00 format AND in military " +
                        "time\nFor example, 08:00 or 13:00");
            }
            if (checkTimeStringHoursMinutesValidation(end)) {
                localTimeEnd = convertToLocalTime(end);
            } else {
                errorFlag = true;
                warningText = warningText.concat("\n\nInvalid Time! \nEnd time must be in 00:00 format AND in military " +
                        "time\nFor example, 08:00 or 13:00");

            }

            LocalDateTime localDateTimeStart = convertToLocalDateTime(localDate, localTimeStart);
            LocalDateTime localDateTimeEnd = convertToLocalDateTime(localDate, localTimeEnd);

            if (validateWeekend(apptDateDatePicker, localDateTimeStart)) {
                errorFlag = true;
                warningText = warningText.concat(("\nPlease select a weekday, Monday-Friday."));
            }

            if (validateOutsideBusinessHours(apptStartTextField, localDateTimeStart)) {
                errorFlag = true;
                warningText = warningText.concat("\n\nStart time occurs before 08:00 EST. \nPlease schedule a start time at 08:00 EST or later.");
            }
            if (validateOutsideBusinessHours(apptEndTextField,localDateTimeEnd)) {
                errorFlag = true;
                warningText = warningText.concat("\n\nEnd time occurs after 22:00 EST. \nPlease schedule an end time at 22:00 EST or earlier.");
            }

            if (!validateStartBeforeEnd(localDateTimeStart, localDateTimeEnd)) {
                errorFlag = true;
                warningText = warningText.concat("\n\nStart time occurs after end time. \nPlease schedule a start time before the end time.");
                System.out.println("Start: " + localDateTimeStart);
                System.out.println("End: " + localDateTimeEnd);
            }

            if (validateAppointmentOverlap(localDateTimeStart, localDateTimeEnd, customerIdFromNameSelect(customer), apptIdTextField, true)) {
                errorFlag = true;
                String bodyText = "\n\nAppointment overlaps with another appointment. \nPlease schedule an appointment that does not overlap with another appointment.";
                warningAlert("Appointment Overlap", "Appointment Overlap", bodyText);
            }

        } catch(Exception e) {
        }

        if (errorFlag) {
            warningLabel.setText(warningText);
            return;
        } else {
            Timestamp startTimestamp = convertToTimestamp(date, convertToLocalTime(start));
            Timestamp endTimestamp = convertToTimestamp(date, convertToLocalTime(end));
            int customerId = customerIdFromNameSelect(customer);
            int contactId = contactIdFromNameSelect(contact);
            //TODO: Get rid of hard-coded user id after doing login stuff
            int userId = 1;

            AppointmentImpl.appointmentUpdate(title, description, location, type, startTimestamp, endTimestamp,
                    customerId, userId, contactId, apptId);
            loadForm(event, "MainForm.fxml");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
