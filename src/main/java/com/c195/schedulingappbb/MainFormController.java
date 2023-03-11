package com.c195.schedulingappbb;

import DAO.CustomerImpl;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {
    public TableView<Customer> customerTableView;
    public TableView<Appointment> appointmentTableView;
    static Stage stage;
    public TableColumn customerRecCustomerIdCol;
    public TableColumn customerRecNameCol;
    public TableColumn customerRecAddressCol;
    public TableColumn customerRecPhoneCol;
    public TableColumn customerRecCountryCol;
    public TableColumn customerRecDivisionCol;
    public TableColumn customerRecPostalCodeCol;
    public TableColumn upcomingApptAppointmentIdCol;
    public TableColumn upcomingApptTitleCol;
    public TableColumn upcomingApptDescriptionCol;
    public TableColumn upcomingApptLocationCol;
    public TableColumn upcomingApptContactCol;
    public TableColumn upcomingApptTypeCol;
    public TableColumn upcomingApptStartCol;
    public TableColumn upcomingApptEndCol;
    public TableColumn upcomingApptCustomerIdCol;
    public TableColumn upcomingApptUserIdCol;
    public TableView<Appointment> UpcomingApptTable;
    public TableView<Customer> customerRecTable;

    public void onLogout(ActionEvent event) throws IOException {
        loadForm(stage, event, "LoginForm.fxml");
    }

    public void onAddNewCustomer(ActionEvent event) throws IOException {
        loadForm(stage, event, "AddNewCustomerForm.fxml");
    }

    //TODO: Add appointment deleting when finishing appointments
    public void onDeleteCustomer(ActionEvent event) throws IOException {
        Customer selected = customerRecTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            String title = "Delete Customer";
            String header = "Are you sure you want to delete this customer?";
            String content = "Deleting this customer will also delete all associated appointments.";
            if (confirmationAlert(title, header, content)) {
                int customerId = selected.getCustomerId();
                int deleted = CustomerImpl.delete(customerId);

                int listIndex = Customer.allCustomers.indexOf(selected);
                Customer.allCustomers.remove(listIndex);
            }
        }
    }

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

    public void onAddNewAppointment(ActionEvent event) throws IOException {
        loadForm(stage, event, "AddNewAppointmentForm.fxml");
    }

    public void onModifyAppointment(ActionEvent event) throws IOException {
        loadForm(stage, event, "ModifyAppointmentForm.fxml");
    }

    public static void loadForm(Stage stage, ActionEvent event, String form) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(form));
        Scene scene = new Scene(fxmlLoader.load());

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public static void setCustomerTable(TableColumn id, TableColumn name, TableColumn address, TableColumn phone, TableColumn country,  TableColumn division, TableColumn postalCode, TableView table, ObservableList list) {
        id.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        phone.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
        country.setCellValueFactory(new PropertyValueFactory<>("Country"));
        division.setCellValueFactory(new PropertyValueFactory<>("LevelOneDivision"));
        postalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        table.setItems(list);
    }

    public static boolean confirmationAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        if (alert.showAndWait().get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Customer.allCustomers.clear();
        CustomerImpl.customerSelect();
        setCustomerTable(customerRecCustomerIdCol, customerRecNameCol, customerRecAddressCol, customerRecPhoneCol, customerRecCountryCol, customerRecDivisionCol, customerRecPostalCodeCol, customerRecTable, Customer.getAllCustomers());
    }
}
