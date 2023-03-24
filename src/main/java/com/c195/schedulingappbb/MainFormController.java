package com.c195.schedulingappbb;

import DAO.AppointmentImpl;
import DAO.CustomerImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {
    static Stage stage;
    public TableColumn<Customer, Integer> customerRecCustomerIdCol;
    public TableColumn<Customer, String> customerRecNameCol;
    public TableColumn<Customer, String> customerRecAddressCol;
    public TableColumn<Customer, String> customerRecPhoneCol;
    public TableColumn<Customer, String> customerRecCountryCol;
    public TableColumn<Customer, String> customerRecDivisionCol;
    public TableColumn<Customer, String> customerRecPostalCodeCol;
    public TableColumn<Appointment, Integer> upcomingApptAppointmentIdCol;
    public TableColumn<Appointment, String> upcomingApptTitleCol;
    public TableColumn<Appointment, String> upcomingApptDescriptionCol;
    public TableColumn<Appointment, String> upcomingApptLocationCol;
    public TableColumn<Appointment, String> upcomingApptContactCol;
    public TableColumn<Appointment, String> upcomingApptTypeCol;
    public TableColumn<Appointment, String> upcomingApptStartCol;
    public TableColumn<Appointment, String> upcomingApptEndCol;
    public TableColumn<Appointment, Integer> upcomingApptCustomerIdCol;
    public TableColumn<Appointment, Integer> upcomingApptUserIdCol;
    public TableView<Appointment> upcomingApptTable;
    public TableView<Customer> customerRecTable;
    public RadioButton viewAllRadioButton;
    public RadioButton monthRadioButton;
    public RadioButton weekRadioButton;
    public RadioButton allContactsRadioButton;
    public RadioButton customersCountryRadioButton;
    public RadioButton customersMonthTypeRadioButton;
    public Button generateButton;

    /**
     * This method loads the LoginForm.fxml file
     * @param event
     * @throws IOException
     */
    public void onLogout(ActionEvent event) throws IOException {
        LoginFormController.userList.clear();
        loadForm(event, "LoginForm.fxml");
    }

    /**
     * This method loads the AddNewCustomerForm.fxml file
     * @param event
     * @throws IOException
     */
    public void onAddNewCustomer(ActionEvent event) throws IOException {
        loadForm(event, "AddNewCustomerForm.fxml");
    }


    /**
     * This method asks for confirmation before deleting a customer from the database and removing associated appointments.
     * @param event
     * @throws IOException
     */
    public void onDeleteCustomer(ActionEvent event) throws IOException {
        Customer selected = customerRecTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            String title = "Delete Customer";
            String header = "Are you sure you want to delete this customer?";
            String content = "Deleting this customer will also delete all associated appointments.";
            if (confirmationAlert(title, header, content)) {
                int customerId = selected.getCustomerId();
                CustomerImpl.customerDelete(customerId);
                CustomerImpl.customerAssocApptDelete(customerId);
                Appointment.allAppointments.remove(selected);
                Customer.allCustomers.remove(selected);
                loadForm(event, "MainForm.fxml");

            }
        }
    }

    /**
     * This method loads the ModifyCustomerForm.fxml file
     * @param event
     * @throws IOException
     */
    public void onModifyCustomer(ActionEvent event) throws IOException {
        Customer selected = customerRecTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ModifyCustomerForm.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            ModifyCustomerFormController modifyCustomerFormController = fxmlLoader.getController();
            modifyCustomerFormController.fillCustomerForm(selected);

            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * This method loads the AddNewAppointmentForm.fxml file
     * @param event
     * @throws IOException
     */
    public void onAddNewAppointment(ActionEvent event) throws IOException {
        loadForm(event, "AddNewAppointmentForm.fxml");
    }

    /**
     * This method loads the ModifyAppointmentForm.fxml file when an item is selected.
     * @param event
     * @throws IOException
     */
    public void onModifyAppointment(ActionEvent event) throws IOException {
        Appointment selected = upcomingApptTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ModifyAppointmentForm.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            ModifyAppointmentFormController modifyAppointmentFormController = fxmlLoader.getController();
            modifyAppointmentFormController.fillApptForm(selected);

            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * This method asks for confirmation before deleting an appointment from the database.
     */
    public void onDeleteAppointment() {
        Appointment selected = upcomingApptTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            String title = "Delete Appointment";
            String header = "Are you sure you want to delete this appointment?";
            String content = "Please select OK to delete appointment.";
            if (confirmationAlert(title, header, content)) {
                int apptId = selected.getAppointmentId();
                AppointmentImpl.appointmentDelete(apptId);
                Appointment.allAppointments.remove(selected);
            }
        }
    }

    /**
     * This is a general method for loading forms.
     * @param event
     * @param form
     * @throws IOException
     */
    public static void loadForm(ActionEvent event, String form) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(form));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method sets the customer table.
     * @param id
     * @param name
     * @param address
     * @param phone
     * @param country
     * @param division
     * @param postalCode
     * @param table
     * @param list
     */
    public static void setCustomerTable(TableColumn<Customer, Integer> id,
                                        TableColumn<Customer, String> name,
                                        TableColumn<Customer, String> address,
                                        TableColumn<Customer, String> phone,
                                        TableColumn<Customer, String> country,
                                        TableColumn<Customer, String> division,
                                        TableColumn<Customer, String> postalCode,
                                        TableView<Customer> table,
                                        ObservableList<Customer> list) {
        id.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        phone.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
        country.setCellValueFactory(new PropertyValueFactory<>("Country"));
        division.setCellValueFactory(new PropertyValueFactory<>("LevelOneDivision"));
        postalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        table.setItems(list);
    }

    /**
     * This method sets the appointment table.
     * @param apptId
     * @param apptTitle
     * @param apptDescription
     * @param apptLocation
     * @param apptContact
     * @param apptType
     * @param apptStart
     * @param apptEnd
     * @param apptCustomerId
     * @param apptUserId
     * @param table
     * @param list
     */
    public static void setAppointmentTable(TableColumn<Appointment, Integer> apptId,
                                           TableColumn<Appointment, String> apptTitle,
                                           TableColumn<Appointment, String> apptDescription,
                                           TableColumn<Appointment, String> apptLocation,
                                           TableColumn<Appointment, String> apptContact,
                                           TableColumn<Appointment, String> apptType,
                                           TableColumn<Appointment, String> apptStart,
                                           TableColumn<Appointment, String> apptEnd,
                                           TableColumn<Appointment, Integer> apptCustomerId,
                                           TableColumn<Appointment, Integer> apptUserId,
                                           TableView<Appointment> table,
                                           ObservableList<Appointment> list) {
        apptId.setCellValueFactory(new PropertyValueFactory<>("AppointmentId"));
        apptTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        apptDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        apptLocation.setCellValueFactory(new PropertyValueFactory<>("Location"));
        apptContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        apptType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        apptStart.setCellValueFactory(new PropertyValueFactory<>("StartDateTimeFormatted"));
        apptEnd.setCellValueFactory(new PropertyValueFactory<>("EndDateTimeFormatted"));
        apptCustomerId.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        apptUserId.setCellValueFactory(new PropertyValueFactory<>("UserId"));
        table.setItems(list);
    }


    /**
     * This method is a general method to create a confirmation alert.
     * @param title
     * @param header
     * @param content
     * @return
     */
    public static boolean confirmationAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        return alert.showAndWait().get() == ButtonType.OK;
    }


    /**
     * This method is a general method to create a warning alert.
     * @param title
     * @param header
     * @param content
     */
    public static void warningAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * This method notifies the user if an appointment is within 15 minutes or there are no upcoming appointments.
     * @param list
     */
    public static void appointmentAlert(ObservableList<Appointment> list) {
        LocalDateTime now = LocalDateTime.now();
        boolean flag = false;
        for (Appointment a : list) {
            if (a.getStartDateTime().isAfter(now) && a.getStartDateTime().isBefore(now.plusMinutes(15))) {
                flag = true;
                warningAlert("Appointment Alert",
                        "Appointment within 15 minutes",
                        "Appointment ID: " + a.getAppointmentId() +
                                "\nDate: " + a.getStartDateTime().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()) + ", " + a.getStartDateTime().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + a.getStartDateTime().getDayOfMonth() +
                                "\nTime: " + a.getStartDateTime().getHour() + ":" + a.getStartDateTime().getMinute() + " to " + a.getEndDateTime().getHour() + ":" + a.getEndDateTime().getMinute());
            }
        }
        if (!flag) {
            warningAlert("Appointment Alert",
                    "No appointments within 15 minutes",
                    "There are no upcoming appointments");
        }
    }

    /**
     * This method shows all appointments in the table.
     */
    public void onViewAllRadioButton() {
        if (viewAllRadioButton.isSelected()) {
            Appointment.allAppointments.clear();
            AppointmentImpl.appointmentSelect();
            ObservableList<Appointment> list = Appointment.getAllAppointments();
            setAppointmentTable(upcomingApptAppointmentIdCol, upcomingApptTitleCol, upcomingApptDescriptionCol,
                    upcomingApptLocationCol, upcomingApptContactCol, upcomingApptTypeCol, upcomingApptStartCol,
                    upcomingApptEndCol, upcomingApptCustomerIdCol, upcomingApptUserIdCol, upcomingApptTable,
                    list);
        }
    }

    /**
     * This method shows appointments within the month in the table.
     */
    public void onMonthRadioButton() {
        if (monthRadioButton.isSelected()) {
            Appointment.allAppointments.clear();
            AppointmentImpl.appointmentSelect();
            ObservableList<Appointment> list = Appointment.getAllAppointments();
            ObservableList<Appointment> filteredList = FXCollections.observableArrayList();
            for (Appointment a : list) {
                if (a.getStartDateTime().getMonth() == LocalDateTime.now().getMonth()) {
                    filteredList.add(a);
                }
            }
            setAppointmentTable(upcomingApptAppointmentIdCol, upcomingApptTitleCol, upcomingApptDescriptionCol,
                    upcomingApptLocationCol, upcomingApptContactCol, upcomingApptTypeCol, upcomingApptStartCol,
                    upcomingApptEndCol, upcomingApptCustomerIdCol, upcomingApptUserIdCol, upcomingApptTable,
                    filteredList);
        }
    }

    /**
     * This method shows appointments within the week in the table.
     */
    public void onWeekRadioButton() {
        if (weekRadioButton.isSelected()) {
            Appointment.allAppointments.clear();
            AppointmentImpl.appointmentSelect();
            ObservableList<Appointment> list = Appointment.getAllAppointments();
            ObservableList<Appointment> filteredList = FXCollections.observableArrayList();
            for (Appointment a : list) {
                if (a.getStartDateTime().getMonth() == LocalDateTime.now().getMonth() && a.getStartDateTime().getDayOfMonth() >= LocalDateTime.now().getDayOfMonth() && a.getStartDateTime().getDayOfMonth() <= LocalDateTime.now().getDayOfMonth() + 7) {
                    filteredList.add(a);
                }
            }
            setAppointmentTable(upcomingApptAppointmentIdCol, upcomingApptTitleCol, upcomingApptDescriptionCol,
                    upcomingApptLocationCol, upcomingApptContactCol, upcomingApptTypeCol, upcomingApptStartCol,
                    upcomingApptEndCol, upcomingApptCustomerIdCol, upcomingApptUserIdCol, upcomingApptTable,
                    filteredList);
        }
    }

    /**
     * This method controls the radio buttons for the reports.
     * @param event
     * @throws IOException
     */
    public void onGenerate(ActionEvent event) throws IOException {
        if (customersMonthTypeRadioButton.isSelected()) {
            onCustomersMonthTypeRadioButton();
        } else if (customersCountryRadioButton.isSelected()) {
            onCustomersCountryRadioButton();
        } else if (allContactsRadioButton.isSelected()) {
            onAllContactsRadioButton(event);
        }
    }

    /**
     * This method creates an alert showing the number of appointments by month and type.
     */
    public void onCustomersMonthTypeRadioButton() {
        Appointment.allAppointments.clear();
        AppointmentImpl.appointmentSelect();
        ObservableList<Appointment> list = Appointment.getAllAppointments();
        ObservableList<String> typeList = FXCollections.observableArrayList();
        int monthApptTotal = 0;
        int typeApptTotal = 0;


        list.forEach(a -> {
                    if (!typeList.contains(a.getType())) {
                        typeList.add(a.getType());
                    }
                });

        String output = "Customer Appointments By Month and Type\n\nBy Month:\n\n";
        for (int i = 1; i <= 12; i++) {
            int monthTotal = 0;
            for (Appointment a : list) {
                if (a.getStartDateTime().getMonthValue() == i) {
                    monthTotal++;
                    monthApptTotal++;
                }
            }
            if (monthTotal > 0) {
                output += Month.of(i).getDisplayName(TextStyle.FULL, Locale.getDefault()) + ": " + monthTotal + "\n";
            }
        }

        output += "\n***Total: " + monthApptTotal + "\n\nBy Type:\n\n";

        for (String s : typeList) {
            int typeTotal = 0;
            for (Appointment a : list) {
                if (a.getType().equals(s)) {
                    typeTotal++;
                    typeApptTotal++;
                }
            }
            output += s + ": " + typeTotal + "\n";
        }

        output += "\n***Total: " + typeApptTotal + "\n\n";

        warningAlert("Customer Appointments by Month and Type", "Customer Appointments by Month and Type", output);
    }


    /**
     * This method loads the report form.
     * @param event
     * @throws IOException
     */
    public void onAllContactsRadioButton(ActionEvent event) throws IOException {
        loadForm(event, "ReportForm.fxml");
    }

    /**
     * This method creates an alert showing the number of customers by country.
     */
    public void onCustomersCountryRadioButton() {
        Customer.allCustomers.clear();
        CustomerImpl.customerSelect();
        ObservableList<Customer> list = Customer.getAllCustomers();
        ObservableList<String> countryList = FXCollections.observableArrayList();
        int countryTotal = 0;
        String output = "Customers by Country\n\n";

        list.forEach(c -> {
                    if (!countryList.contains(c.getCountry())) {
                        countryList.add(c.getCountry());
                    }
                });


        for (String s : countryList) {
            int countryCount = 0;
            for (Customer c : list) {
                if (c.getCountry().equals(s)) {
                    countryCount++;
                    countryTotal++;
                }
            }
            output += s + ": " + countryCount + "\n";
        }

        output += "\n***Total Customers: " + countryTotal + "\n\n";

        warningAlert("Customers by Country", "Customers by Country", output);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Customer.allCustomers.clear();
        CustomerImpl.customerSelect();
        setCustomerTable(customerRecCustomerIdCol, customerRecNameCol, customerRecAddressCol, customerRecPhoneCol,
                customerRecCountryCol, customerRecDivisionCol, customerRecPostalCodeCol, customerRecTable,
                Customer.getAllCustomers());

        Appointment.allAppointments.clear();
        AppointmentImpl.appointmentSelect();
        setAppointmentTable(upcomingApptAppointmentIdCol, upcomingApptTitleCol, upcomingApptDescriptionCol,
                upcomingApptLocationCol, upcomingApptContactCol, upcomingApptTypeCol, upcomingApptStartCol,
                upcomingApptEndCol, upcomingApptCustomerIdCol, upcomingApptUserIdCol, upcomingApptTable,
                Appointment.getAllAppointments());
    }
}
