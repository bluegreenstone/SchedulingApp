package com.c195.schedulingappbb;

import DAO.CustomerImpl;
import DAO.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.c195.schedulingappbb.MainFormController.loadForm;


public class AddNewCustomerFormController implements Initializable {
    static Stage stage;
    public TextField addCustomerNameTextfield;
    public TextField addCustomerAddressTextField;
    public TextField addCustomerPhone;
    public TextField addCustomerPostalCode;
    public ComboBox<String> addCustomerCountry;
    public ComboBox<String> addCustomerDivision;
    public Button saveButton;
    public Label warningLabel;
    public Label customerNameLabel;
    public Label customerAddressLabel;
    public Label customerPhoneLabel;
    public Label customerCountryLabel;
    public Label customerDivisionLabel;
    public Label customerPostalCodeLabel;

    @FXML
    Button cancelButton;

    private static final String warningHighlight = "-fx-border-color:red;";
    private static final String defaultHighlight = "";

    public static ObservableList<String> countryList = FXCollections.observableArrayList();
    public static ObservableList<String> divisionList = FXCollections.observableArrayList();


    public void onCancel(ActionEvent event) throws IOException {
        loadForm(stage, event, "MainForm.fxml");
    }


    public void onSave(ActionEvent event) throws IOException {
        customerValidationReset(addCustomerNameTextfield,
                addCustomerAddressTextField,
                addCustomerPhone,
                addCustomerCountry,
                addCustomerDivision,
                addCustomerPostalCode);

        String warningText = "*WARNING*\n\n";

        boolean errorFlag = false;

        String customerName = addCustomerNameTextfield.getText();
        String address = addCustomerAddressTextField.getText();
        String phone = addCustomerPhone.getText();
        String division = "";
        String postalCode = addCustomerPostalCode.getText();

        if (stringValidation(customerName, addCustomerNameTextfield)) {
            errorFlag = true;
            warningText = addWarningLabelText(customerNameLabel, warningText);
        } else {
            customerName = addCustomerNameTextfield.getText();
        }

        if (stringValidation(address, addCustomerAddressTextField)) {
            errorFlag = true;
            warningText = addWarningLabelText(customerAddressLabel, warningText);
        } else {
            address = addCustomerAddressTextField.getText();
        }

        if (stringValidation(phone, addCustomerPhone)) {
            errorFlag = true;
            warningText = addWarningLabelText(customerPhoneLabel, warningText);
        } else {
            phone = addCustomerPhone.getText();
        }

        if (comboBoxValidation(addCustomerCountry)) {
            errorFlag = true;
            warningText = addWarningLabelText(customerCountryLabel, warningText);
        } else {
            division = addCustomerCountry.getSelectionModel().getSelectedItem().toString();
        }

        if (comboBoxValidation(addCustomerDivision)) {
            errorFlag = true;
            warningText = addWarningLabelText(customerDivisionLabel, warningText);
        } else {
            division = addCustomerDivision.getSelectionModel().getSelectedItem().toString();
        }

        if (stringValidation(postalCode, addCustomerPostalCode)) {
            errorFlag = true;
            warningText = addWarningLabelText(customerPostalCodeLabel, warningText);
        } else {
            postalCode = addCustomerPostalCode.getText();
        }



        if (errorFlag) {
            warningLabel.setText(warningText);
            return;
        } else {
            int divisionId = Query.divisionIdFromNameSelect(division);
            int insertCustomer = CustomerImpl.customerInsert(customerName, address, postalCode, phone, divisionId);
            loadForm(stage, event, "MainForm.fxml");
        }
    }

    public static boolean stringValidation(String s, TextField textField) {
        if (s.isBlank()) {
            textField.setStyle(warningHighlight);
            return true;
        } else {
            return false;
        }
    }

    public static boolean comboBoxValidation(ComboBox<String> comboBox) {
        if (comboBox.getSelectionModel().getSelectedItem() == null) {
            comboBox.setStyle(warningHighlight);
            return true;
        } else {
            return false;
        }
    }

    public static String addWarningLabelText(Label textFieldLabel, String s) {
        s = s.concat(textFieldLabel.getText() + " cannot be empty!\n");
        return s;
    }

    public static void customerValidationReset(TextField addCustomerNameTextfield,
                                               TextField addCustomerAddressTextField,
                                               TextField addCustomerPhone,
                                               ComboBox<String> addCustomerCountry,
                                               ComboBox<String> addCustomerDivision,
                                               TextField addCustomerPostalCode) {
        addCustomerNameTextfield.setStyle(null);
        addCustomerAddressTextField.setStyle(null);
        addCustomerPhone.setStyle(null);
        addCustomerCountry.setStyle(null);
        addCustomerDivision.setStyle(null);
        addCustomerPostalCode.setStyle(null);

    }

    public void onSelectCountry(ActionEvent event) {
        if (addCustomerCountry.getSelectionModel().getSelectedItem() != null) {
            addCustomerDivision.setDisable(false);
            setDivisionComboBox(addCustomerDivision, divisionList, addCustomerCountry);
        }

    }

    public static void setCountryComboBox(ComboBox<String> comboBox, ObservableList<String> list) {
        list.clear();
        Query.countrySelect();
        comboBox.setItems(list);
    }

    public static void setDivisionComboBox(ComboBox<String> divisionComboBox,
                                           ObservableList<String> list,
                                           ComboBox<String> countryComboBox) {
        if (countryComboBox.getSelectionModel().getSelectedItem() != null) {
            list.clear();
            Query.divisionSelect(countryComboBox.getSelectionModel().getSelectedItem().toString());
            divisionComboBox.setItems(list);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCountryComboBox(addCustomerCountry, countryList);

    }


}
