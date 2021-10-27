package Controller;

import Model.Customer;
import Utils.ListProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCustomerController {

    Stage stage;
    Parent scene;

    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    public TextField customerIDTextField;
    public TextField customerNameTextField;
    public TextField customerAddressTextField;
    //public ComboBox customerCountryComboBox;
    //public ComboBox customerStateComboBox;
    public TextField customerPostalCodeTextField;
    public TextField customerPhoneTextField;

    private Customer customerToAdd;
    private Object DateTimeParseException;

    /**
     * Saves customer information and returns to the main appointments controller.
     *
     * @param event Save button clicked.
     * @return
     */
    @FXML
    void onSaveCustomer(ActionEvent event) {

        try {
            int customerId = 0;
            for (Customer customer : ListProvider.getAllCustomers()) {
                if (customer.getCustomerId() > customerId)
                    customerId = (customer.getCustomerId());
                customerId = ++customerId;
            }

            //int customerId = customerToAdd.getCustomerId();
            String customerName = customerNameTextField.getText();
            String address = customerAddressTextField.getText();
            //ComboBox country = customerCountryComboBox.getText();
            //ComboBox state = customerStateComboBox.getText();
            String postalCode = customerPostalCodeTextField.getText();
            String phone = customerPhoneTextField.getText();

            boolean customerAdded = false;

            if (customerName.isEmpty()) {
                //displayAlert(1);
            } else {

                try {
                    Customer newCustomerAdded = new Customer(customerId, customerName, address, postalCode, phone);
                    ListProvider.addCustomer(newCustomerAdded);
                    customerAdded = true;

                } catch (Exception e) {
                    //displayAlert(1);
                }

                if (customerAdded) {
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/View/MainCustomer.fxml"));
                    scene.setStyle(("-fx-font-family: 'serif';"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
        } catch(Exception e) {
            //displayAlert(1);
            }
    }


    /**
     * Cancels customer information and returns to the main appointments controller.
     *
     * @param event Cancel button clicked.
     */
    public void onCancelCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainCustomer.fxml"));
        scene.setStyle(("-fx-font-family: 'serif';"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
