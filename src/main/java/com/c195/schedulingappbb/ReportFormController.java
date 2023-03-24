package com.c195.schedulingappbb;

import DAO.AppointmentImpl;
import DAO.Query;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.c195.schedulingappbb.MainFormController.loadForm;
import static model.Appointment.contactAppointments;

public class ReportFormController implements Initializable {
    public ComboBox<String> contactComboBox;
    public TableView<Appointment> upcomingApptTable;
    public TableColumn<Appointment, Integer> upcomingApptAppointmentIdCol;
    public TableColumn<Appointment, String> upcomingApptTitleCol;
    public TableColumn<Appointment, String> upcomingApptDescriptionCol;
    public TableColumn<Appointment, String> upcomingApptLocationCol;
    public TableColumn<Appointment, String> upcomingApptTypeCol;
    public TableColumn<Appointment, String> upcomingApptStartCol;
    public TableColumn<Appointment, String> upcomingApptEndCol;
    public TableColumn<Appointment, Integer> upcomingApptCustomerIdCol;
    public Button returnButton;


    public void onReturnButton(ActionEvent event) throws IOException {
        loadForm(event, "MainForm.fxml");
    }

    public void onContactComboBox(ActionEvent event) throws IOException {
        if (contactComboBox.getValue() != null) {
            int contactId = Query.contactIdFromNameSelect(contactComboBox.getValue());
            Appointment.contactAppointments.clear();
            AppointmentImpl.appointmentSelectByContact(contactId);
            setContactTable(upcomingApptAppointmentIdCol,
                    upcomingApptTitleCol,
                    upcomingApptDescriptionCol,
                    upcomingApptLocationCol,
                    upcomingApptTypeCol,
                    upcomingApptStartCol,
                    upcomingApptEndCol,
                    upcomingApptCustomerIdCol,
                    contactAppointments,
                    upcomingApptTable);

        }
    }

    public void setContactTable(TableColumn<Appointment, Integer> apptId,
                                TableColumn<Appointment, String> apptTitle,
                                TableColumn<Appointment, String> apptDescription,
                                TableColumn<Appointment, String> apptLocation,
                                TableColumn<Appointment, String> apptType,
                                TableColumn<Appointment, String> apptStart,
                                TableColumn<Appointment, String> apptEnd,
                                TableColumn<Appointment, Integer> apptCustomerId,
                                ObservableList<Appointment> list,
                                TableView<Appointment> table) {
        apptId.setCellValueFactory(new PropertyValueFactory<>("AppointmentId"));
        apptTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        apptDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        apptLocation.setCellValueFactory(new PropertyValueFactory<>("Location"));
        apptType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        apptStart.setCellValueFactory(new PropertyValueFactory<>("StartDateTimeFormatted"));
        apptEnd.setCellValueFactory(new PropertyValueFactory<>("EndDateTimeFormatted"));
        apptCustomerId.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        table.setItems(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddNewAppointmentFormController.setContactsComboBox(contactComboBox, AddNewAppointmentFormController.contactsList);
        }
}
