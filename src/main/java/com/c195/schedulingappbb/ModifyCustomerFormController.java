package com.c195.schedulingappbb;

import DAO.CustomerImpl;
import DAO.Query;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Customer;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.c195.schedulingappbb.AddNewCustomerFormController.*;
import static com.c195.schedulingappbb.MainFormController.loadForm;

public class ModifyCustomerFormController implements Initializable {
    public TextField customerIdTextField;
    public TextField customerNameTextField;
    public TextField customerAddressTextField;
    public TextField customerPhoneTextField;
    public TextField customerPostalCodeTextField;
    public ComboBox<String> customerCountryComboBox;
    public ComboBox<String> customerDivisionComboBox;
    public Label customerNameLabel;
    public Label customerAddressLabel;
    public Label customerPhoneLabel;
    public Label customerCountryLabel;
    public Label customerDivisionLabel;
    public Label customerPostalCodeLabel;
    public Label warningLabel;
    public Button cancelButton;
    Stage stage;

    /**
     * This method loads the Main Form when the Cancel button is clicked.
     * @param event
     * @throws IOException
     */
    public void onCancel(ActionEvent event) throws IOException {
        loadForm(event, "MainForm.fxml");
    }

    /**
     * This method fills in the TextFields from the selected Customer on MainForm
     * @param selected
     */
    public void fillCustomerForm(Customer selected) {

        customerIdTextField.setText(Integer.toString(selected.getCustomerId()));
        customerNameTextField.setText(selected.getName());
        customerAddressTextField.setText(selected.getAddress());
        customerPhoneTextField.setText(selected.getPhoneNumber());

        AddNewCustomerFormController.setCountryComboBox(customerCountryComboBox, AddNewCustomerFormController.countryList);
        int countryListIndex = AddNewCustomerFormController.countryList.indexOf(selected.getCountry());
        customerCountryComboBox.getSelectionModel().select(countryListIndex);

        AddNewCustomerFormController.setDivisionComboBox(customerDivisionComboBox, AddNewCustomerFormController.divisionList, customerCountryComboBox);
        int divisionListIndex = AddNewCustomerFormController.divisionList.indexOf(selected.getLevelOneDivision());
        customerDivisionComboBox.getSelectionModel().select(divisionListIndex);

        customerPostalCodeTextField.setText(selected.getPostalCode());

    }

    //

    /**
     * This method validates the input and saves the Customer to the database.
     * @param event
     * @throws IOException
     */
    public void onSave(ActionEvent event) throws IOException {
        customerValidationReset(customerNameTextField,
                customerAddressTextField,
                customerPhoneTextField,
                customerCountryComboBox,
                customerDivisionComboBox,
                customerPostalCodeTextField);

        String warningText = "*WARNING*\n\n";

        boolean errorFlag = false;

        String customerName = customerNameTextField.getText();
        String address = customerAddressTextField.getText();
        String phone = customerPhoneTextField.getText();
        String division = "";
        String postalCode = customerPostalCodeTextField.getText();

        if (stringValidation(customerName, customerNameTextField)) {
            errorFlag = true;
            warningText = addWarningLabelText(customerNameLabel, warningText);
        } else {
            customerName = customerNameTextField.getText();
        }

        if (stringValidation(address, customerAddressTextField)) {
            errorFlag = true;
            warningText = addWarningLabelText(customerAddressLabel, warningText);
        } else {
            address = customerAddressTextField.getText();
        }

        if (stringValidation(phone, customerPhoneTextField)) {
            errorFlag = true;
            warningText = addWarningLabelText(customerPhoneLabel, warningText);
        } else {
            phone = customerPhoneTextField.getText();
        }

        if (comboBoxValidation(customerCountryComboBox)) {
            errorFlag = true;
            warningText = addWarningLabelText(customerCountryLabel, warningText);
        } else {
            division = customerCountryComboBox.getSelectionModel().getSelectedItem();
        }

        if (comboBoxValidation(customerDivisionComboBox)) {
            errorFlag = true;
            warningText = addWarningLabelText(customerDivisionLabel, warningText);
        } else {
            division = customerDivisionComboBox.getSelectionModel().getSelectedItem();
        }

        if (stringValidation(postalCode, customerPostalCodeTextField)) {
            errorFlag = true;
            warningText = addWarningLabelText(customerPostalCodeLabel, warningText);
        } else {
            postalCode = customerPostalCodeTextField.getText();
        }



        if (errorFlag) {
            warningLabel.setText(warningText);
            return;
        } else {
            int divisionId = Query.divisionIdFromNameSelect(division);
            int customerId = Integer.parseInt(customerIdTextField.getText());
            int updateCustomer = CustomerImpl.customerUpdate(customerId, customerName, address, postalCode, phone, divisionId);
            loadForm(event, "MainForm.fxml");
        }
    }

    /**
     * This method resets the ComboBoxes when the Country ComboBox is changed.
     */
    public void onSelectCountry() {
        AddNewCustomerFormController.setDivisionComboBox(customerDivisionComboBox, divisionList, customerCountryComboBox);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
