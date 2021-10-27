package Controller;

import Model.Customer;
import Utils.ListProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class UpdateCustomerController implements Initializable {
    Stage stage;
    Parent scene;

    private ObservableList<Customer> originalCustomer = FXCollections.observableArrayList();

    @FXML
    public TableView<Customer> customerTable;

    @FXML
    public TextField customerIDTextField;

    @FXML
    public TextField customerNameTextField;

    @FXML
    public TextField customerAddressTextField;
    @FXML

    public TextField customerCountryTextField;

    @FXML
    public TextField customerStateTextField;

    @FXML
    public TextField customerPostalCodeTextField;

    @FXML
    public TextField customerPhoneTextField;

    private Customer customerToUpdate;


    /**
     * Saves customer information and returns to the main appointments controller.
     *
     * @param event Save button clicked.
     */
    @FXML
    void onSaveCustomer(ActionEvent event) {
        try {
            int customerId = customerToUpdate.getCustomerId();
            String customerName = customerNameTextField.getText();
            String address = customerAddressTextField.getText();
            //String country = customerCountryTextField.getText();
            //String state = customerStateTextField.getText();
            String postalCode = customerPostalCodeTextField.getText();
            String phone = customerPhoneTextField.getText();

            boolean customerAdded = false;

                try {
                    Customer newCustomerUpdate = new Customer(customerId, customerName, address, postalCode, phone/*, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId*/);
                    ListProvider.addCustomer(newCustomerUpdate);
                    customerAdded = true;

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (customerAdded) {
                    ListProvider.updateCustomer(customerToUpdate);

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/View/MainCustomer.fxml"));
                    scene.setStyle(("-fx-font-family: 'serif';"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }

            } catch(Exception e) {
                e.printStackTrace();
            }
    }

    /**
     * Cancels customer information and returns to the main appointments controller.
     *
     * @param event Cancel button clicked.
     */
    @FXML
    void onCancelCustomer(ActionEvent event) throws IOException {

        //ListProvider.getAllCustomers().clear();

        //ListProvider.getAllCustomers().addAll(originalCustomer);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainCustomer.fxml"));
        scene.setStyle(("-fx-font-family: 'serif';"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        customerToUpdate = MainCustomerController.selectCustomerUpdate();

        /**
         * Sets columns and rows with data for customers.
         */
        customerIDTextField.setText(String.valueOf(customerToUpdate.getCustomerId()));
        customerNameTextField.setText(customerToUpdate.getCustomerName());
        customerAddressTextField.setText(customerToUpdate.getAddress());
        //customerCountryTextField.setText(String.valueOf(customerToUpdate.getCountry()));
        //customerStateTextField.setText(String.valueOf(customerToUpdate.getState()));
        customerPostalCodeTextField.setText(customerToUpdate.getPostalCode());
        customerPhoneTextField.setText(customerToUpdate.getPhone());
    }
}
