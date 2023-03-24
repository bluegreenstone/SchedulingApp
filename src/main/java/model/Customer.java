package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

public class Customer {
    private int customerId;
    private String name;
    private String address;
    private String phoneNumber;
    private String country;
    private String levelOneDivision;
    private String postalCode;

    public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    /**
     * This is the constructor.
     * @param customerId
     * @param name
     * @param address
     * @param phoneNumber
     * @param country
     * @param levelOneDivision
     * @param postalCode
     */
    public Customer(int customerId, String name, String address, String phoneNumber, String country, String levelOneDivision, String postalCode) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.levelOneDivision = levelOneDivision;
        this.postalCode = postalCode;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public String getLevelOneDivision() {
        return levelOneDivision;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLevelOneDivision(String levelOneDivision) {
        this.levelOneDivision = levelOneDivision;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public static void addCustomer() {

    }

}
