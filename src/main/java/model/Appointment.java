package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int customerId;
    private int userId;

    public static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public Appointment(int appointmentId, String title, String description, String location, String contact, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerId, int userId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerId = customerId;
        this.userId = userId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getContact() {
        return contact;
    }
    public String getType() {
        return type;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }
    public String getStartDateTimeFormatted() {
        return startDateTime.toLocalDate().toString() + " " + startDateTime.toLocalTime().toString();
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
    public String getEndDateTimeFormatted() {
        return endDateTime.toLocalDate().toString() + " " + endDateTime.toLocalTime().toString();
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getUserId() {
        return userId;
    }

    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
