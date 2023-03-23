package com.c195.schedulingappbb;

import DAO.AppointmentImpl;
import DAO.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Appointment;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static DAO.Query.contactIdFromNameSelect;
import static DAO.Query.customerIdFromNameSelect;
import static com.c195.schedulingappbb.AddNewCustomerFormController.*;
import static com.c195.schedulingappbb.LoginFormController.getLocalZoneId;
import static com.c195.schedulingappbb.MainFormController.loadForm;
import static com.c195.schedulingappbb.MainFormController.warningAlert;

public class AddNewAppointmentFormController implements Initializable {
    public Label apptIdLabel;
    public Label apptTitleLabel;
    public Label apptDescriptionLabel;
    public Label apptLocationLabel;
    public Label apptTypeLabel;
    public Label apptCustomerLabel;
    public Label apptStartLabel;
    public TextField apptTitleTextField;
    public TextField apptDescriptionTextField;
    public TextField apptLocationTextField;
    public TextField apptStartTextField;
    public ComboBox<String> apptContactComboBox;
    public ComboBox<String> apptCustomerComboBox;
    public Button cancelButton;
    public TextField apptTypeTextField;
    public Label apptContactLabel;
    public Label apptDateLabel;
    public DatePicker apptDateDatePicker;
    public Label apptEndLabel;
    public TextField apptEndTextField;

    public static ObservableList<String> contactsList = FXCollections.observableArrayList();
    public static ObservableList<String> customersList = FXCollections.observableArrayList();
    public Label warningLabel;
    public Label localBusinessHoursLabel;
    public TextField apptApptIdTextField;

    public void onCancel(ActionEvent event) throws IOException {
        loadForm(event, "MainForm.fxml");
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
        LocalDateTime localDateTime = null;

        String warningText = "*WARNING*\n\n";


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
            //TODO: Add validation for time in ModifyAppointmentFormController
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

            if (validateAppointmentOverlap(localDateTimeStart, localDateTimeEnd, customerIdFromNameSelect(customer), apptApptIdTextField, false)) {
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

            AppointmentImpl.appointmentInsert(title, description, location, type, startTimestamp, endTimestamp,
                    customerId, userId, contactId);
            loadForm(event, "MainForm.fxml");
        }
    }

    public static boolean datePickerValidation(DatePicker datePicker) {
        if (datePicker.getValue() == null) {
            datePicker.setStyle(warningHighlight);
            return true;
        } else {
            return false;
        }
    }

    public static boolean timeStringValidation(String timeString, TextField textField) {
        Pattern pattern = Pattern.compile("\\d\\d:\\d\\d");
        Matcher matcher = pattern.matcher(timeString);
        boolean match = matcher.find();
        if (!match) {
            textField.setStyle(warningHighlight);
        } else {
            try {
                if (!checkTimeStringHoursMinutesValidation(timeString)) {
                    textField.setStyle(warningHighlight);
                }
            } catch (Exception e) {
                System.out.println("error");
            }
        }
        return match;
    }

    //TODO: Update on ModifyAppointmentFormController
    public static boolean checkTimeStringHoursMinutesValidation(String timeString) {
        try {
            LocalTime.parse(timeString);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    public static Timestamp convertToTimestamp(LocalDate date, LocalTime localTime) {
        LocalDateTime combined = convertToLocalDateTime(date, localTime);
        Timestamp timestamp = Timestamp.valueOf(combined);
        return timestamp;
    }

    public static LocalTime convertToLocalTime(String time) {
        return LocalTime.parse(time);
    }

    public static LocalDateTime convertToLocalDateTime(LocalDate date, LocalTime time) {
        return LocalDateTime.of(date, time);
    }

    public static void setContactsComboBox(ComboBox<String> comboBox, ObservableList<String> list) {
        list.clear();
        Query.contactSelect();
        comboBox.setItems(list);
    }

    public static void setCustomersComboBox(ComboBox<String> comboBox, ObservableList<String> list) {
        list.clear();
        Query.customerSelect();
        comboBox.setItems(list);
    }

    public static DayOfWeek getDayOfTheWeek(LocalDateTime localDateTime) {
        return localDateTime.getDayOfWeek();
    }

    public static boolean validateWeekend(DatePicker datePicker, LocalDateTime localDateTime) {
        DayOfWeek dayOfWeek = getDayOfTheWeek(localDateTime);
        if (dayOfWeek.getValue() == 6 || dayOfWeek.getValue() == 7) {
            datePicker.setStyle(warningHighlight);
            return true;
        } else {
            return false;
        }
    }

    //TODO: add setStyle to modify appt and start/end variable
    public static boolean validateOutsideBusinessHours(TextField textField, LocalDateTime localDateTime) {
        ZoneId estZoneId = ZoneId.of("America/New_York");
        LocalTime openingTime = LocalTime.of(8,0);
        LocalTime closingTime = LocalTime.of(22,0);
        ZoneId localZoneId = getLocalZoneId();

        ZonedDateTime localZonedDateTime = ZonedDateTime.of(localDateTime, localZoneId);
        ZonedDateTime estZonedDateTime = ZonedDateTime.ofInstant(localZonedDateTime.toInstant(), estZoneId);

        if (estZonedDateTime.toLocalTime().isBefore(openingTime)) {
            textField.setStyle(warningHighlight);
            return true;
        } else if (estZonedDateTime.toLocalTime().isAfter(closingTime)) {
            textField.setStyle(warningHighlight);
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateStartBeforeEnd(LocalDateTime start, LocalDateTime end) {
        if (start.isBefore(end)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateAppointmentOverlap(LocalDateTime start, LocalDateTime end, int customerId, TextField apptId, boolean modifyFlag) {
        boolean overlap = false;
        ObservableList<Appointment> appointments = Appointment.getAllAppointments();
        for (Appointment a : appointments) {
            if (a.getCustomerId() == customerId) {
                if (modifyFlag) {
                    if (a.getAppointmentId() != Integer.parseInt(apptId.getText())) {
                        overlap = isOverlap(start, end, overlap, a);
                    }
                } else {
                    overlap = isOverlap(start, end, overlap, a);
                }
            }
        }
        return overlap;
    }

    private static boolean isOverlap(LocalDateTime start, LocalDateTime end, boolean overlap, Appointment a) {
        if ((start.isAfter(a.getStartDateTime()) || start.isEqual(a.getStartDateTime())) && start.isBefore(a.getEndDateTime())) {
            overlap = true;
        } else if (end.isAfter(a.getStartDateTime()) && (end.isBefore(a.getEndDateTime()) || end.isEqual(a.getEndDateTime()))) {
            overlap = true;
        } else if ((start.isBefore(a.getStartDateTime()) || start.isEqual(a.getStartDateTime())) && (end.isAfter(a.getEndDateTime()) || end.isEqual(a.getEndDateTime()))) {
            overlap = true;
        }
        return overlap;
    }

    public static void setLocalBusinessHoursLabel(Label label) {
        ZoneId estZoneId = ZoneId.of("America/New_York");
        ZoneId localZoneId = getLocalZoneId();

        LocalDate estDate = LocalDate.now();
        LocalTime openingTime = LocalTime.of(8,0);
        LocalDateTime estDateTimeOpening = LocalDateTime.of(estDate, openingTime);

        LocalTime closingTime = LocalTime.of(22,0);
        LocalDateTime estDateTimeClosing = LocalDateTime.of(estDate, closingTime);

        ZonedDateTime estZonedDateTimeOpening = ZonedDateTime.of(estDateTimeOpening, estZoneId);
        ZonedDateTime estZonedDateTimeClosing = ZonedDateTime.of(estDateTimeClosing, estZoneId);

        ZonedDateTime localZonedDateTimeOpening = ZonedDateTime.ofInstant(estZonedDateTimeOpening.toInstant(), localZoneId);
        ZonedDateTime localZonedDateTimeClosing = ZonedDateTime.ofInstant(estZonedDateTimeClosing.toInstant(), localZoneId);

        String timezone = localZonedDateTimeOpening.getZone().toString();
        String openingHour = parseHour(localZonedDateTimeOpening.getHour());
        String closingHour = parseHour(localZonedDateTimeClosing.getHour());
        String fullText = "Your local time is set to " + timezone + ": Business hours in your timezone are from " +
                openingHour + ":00-" + closingHour + ":00";


        label.setText(fullText);

    }

    public static String parseHour(int hour) {
        if (hour < 10) {
            return "0" + hour;
        } else {
            return Integer.toString(hour);
        }
    }

    public static void apptValidationReset(TextField apptTitleTextField,
                                           TextField apptDescriptionTextField,
                                           TextField apptLocationTextField,
                                           TextField apptTypeTextField,
                                           ComboBox<String> apptContactComboBox,
                                           ComboBox<String> apptCustomerComboBox,
                                           DatePicker apptDateDatePicker,
                                           TextField apptStartTextField,
                                           TextField apptEndTextField) {
        apptTitleTextField.setStyle(null);
        apptDescriptionTextField.setStyle(null);
        apptLocationTextField.setStyle(null);
        apptTypeTextField.setStyle(null);
        apptContactComboBox.setStyle(null);
        apptCustomerComboBox.setStyle(null);
        apptDateDatePicker.setStyle(null);
        apptStartTextField.setStyle(null);
        apptEndTextField.setStyle(null);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setContactsComboBox(apptContactComboBox, contactsList);
        setCustomersComboBox(apptCustomerComboBox, customersList);
        setLocalBusinessHoursLabel(localBusinessHoursLabel);

    }

//    TODO: Create appt. overlap validation method.
}
