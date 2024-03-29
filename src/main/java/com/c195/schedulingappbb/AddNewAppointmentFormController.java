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
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
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

    /**
     * This method loads the main form on cancel.
     * @param event
     * @throws IOException
     */
    public void onCancel(ActionEvent event) throws IOException {
        loadForm(event, "MainForm.fxml");
    }

    /**
     * This method validates the appointment and saves it to the database.
     * @param event
     * @throws IOException
     */
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
            int userId = LoginFormController.userList.get(0).getUserId();

            AppointmentImpl.appointmentInsert(title, description, location, type, startTimestamp, endTimestamp,
                    customerId, userId, contactId);
            loadForm(event, "MainForm.fxml");
        }
    }

    /**
     * Validates that date picker is not null
     * @param datePicker
     * @return
     */
    public static boolean datePickerValidation(DatePicker datePicker) {
        if (datePicker.getValue() == null) {
            datePicker.setStyle(warningHighlight);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Validates that the time string is in proper 00:00 format
     * @param timeString
     * @param textField
     * @return
     */
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

    /**
     * Validates that string can be parsed to a LocalTime object
     * @param timeString
     * @return
     */
    public static boolean checkTimeStringHoursMinutesValidation(String timeString) {
        try {
            LocalTime.parse(timeString);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    /**
     * Method converts a LocalDate and LocalTime to a Timestamp
     * @param date
     * @param localTime
     * @return
     */
    public static Timestamp convertToTimestamp(LocalDate date, LocalTime localTime) {
        LocalDateTime combined = convertToLocalDateTime(date, localTime);
        Timestamp timestamp = Timestamp.valueOf(combined);
        return timestamp;
    }

    /**
     * Method converts a String to a LocalTime object
     * @param time
     * @return
     */
    public static LocalTime convertToLocalTime(String time) {
        return LocalTime.parse(time);
    }

    /**
     * Method converts a LocalDate and LocalTime to a LocalDateTime
     * @param date
     * @param time
     * @return
     */
    public static LocalDateTime convertToLocalDateTime(LocalDate date, LocalTime time) {
        return LocalDateTime.of(date, time);
    }

    /**
     * Method sets the ComboBox for the Contacts
     * @param comboBox
     * @param list
     */
    public static void setContactsComboBox(ComboBox<String> comboBox, ObservableList<String> list) {
        list.clear();
        Query.contactSelect();
        comboBox.setItems(list);
    }

    /**
     * Method sets the ComboBox for the Customers
     * @param comboBox
     * @param list
     */
    public static void setCustomersComboBox(ComboBox<String> comboBox, ObservableList<String> list) {
        list.clear();
        Query.customerSelect();
        comboBox.setItems(list);
    }

    /**
     * Method returns DayOfWeek object from a LocalDateTime object
     * @param localDateTime
     * @return
     */
    public static DayOfWeek getDayOfTheWeek(LocalDateTime localDateTime) {
        return localDateTime.getDayOfWeek();
    }

    /**
     * Method validates that a date is not a weekend
     * @param datePicker
     * @param localDateTime
     * @return
     */
    public static boolean validateWeekend(DatePicker datePicker, LocalDateTime localDateTime) {
        DayOfWeek dayOfWeek = getDayOfTheWeek(localDateTime);
        if (dayOfWeek.getValue() == 6 || dayOfWeek.getValue() == 7) {
            datePicker.setStyle(warningHighlight);
            return true;
        } else {
            return false;
        }
    }


    /**
     * Method validates that time is not outside of business hours
     * @param textField
     * @param localDateTime
     * @return
     */
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

    /**
     * Method validates that the start time is before the end time
     * @param start
     * @param end
     * @return
     */
    public static boolean validateStartBeforeEnd(LocalDateTime start, LocalDateTime end) {
        if (start.isBefore(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method validates that the appointment does not overlap with another appointment
     * @param start
     * @param end
     * @param customerId
     * @param apptId
     * @param modifyFlag
     * @return
     */
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

    /**
     * Method for the logic of the validateAppointmentOverlap method
     * @param start
     * @param end
     * @param overlap
     * @param a
     * @return
     */
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

    /**
     * Method sets a label that tells the user business hours in their local time zone
     * @param label
     */
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

    /**
     * Method parses the hour to a string and adds a 0 if the hour is less than 10
     * @param hour
     * @return
     */
    public static String parseHour(int hour) {
        if (hour < 10) {
            return "0" + hour;
        } else {
            return Integer.toString(hour);
        }
    }

    /**
     * Method resets the validation styles on the appointment form
     * @param apptTitleTextField
     * @param apptDescriptionTextField
     * @param apptLocationTextField
     * @param apptTypeTextField
     * @param apptContactComboBox
     * @param apptCustomerComboBox
     * @param apptDateDatePicker
     * @param apptStartTextField
     * @param apptEndTextField
     */
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


}
