package Controller;

import Model.Country;
import Model.Customer;
import Model.Division;
import Utils.CustomerDB;
import Utils.DivisionDB;
import Utils.ListProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controller class that provides logic for the Add Customer screen of the application.
 *
 * @author Erik Nilson
 */
public class AddCustomerController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    public TableView<Customer> customerTable;

    /**
     * The customer ID text field.
     */
    @FXML
    public TextField customerIDTextField;

    /**
     * The customer name text field.
     */
    @FXML
    public TextField customerNameTextField;

    /**
     * The customer address text field.
     */
    @FXML
    public TextField customerAddressTextField;

    @FXML
    public TextField customerDivisionIDTextField;

    /**
     * The customer country combobox.
     */
    @FXML
    public ComboBox<Country> customerCountryComboBox;

    /**
     * The customer division combobox.
     */
    @FXML
    public ComboBox<Division> customerDivisionComboBox;

    /**
     * The customer postal code text field.
     */
    @FXML
    public TextField customerPostalCodeTextField;

    /**
     * The customer phone text field.
     */
    @FXML
    public TextField customerPhoneTextField;

    @FXML
    private Customer customerToAdd;

    public AddCustomerController() throws SQLException {
    }

    /**
     * Saves customer information and returns to the main appointments controller.
     *
     * @param event Save button clicked.
     * @return
     */
    @FXML
    boolean onSaveCustomer(ActionEvent event) throws IOException {
        try {
            int customer_ID = 0;
            for (Customer customer : ListProvider.getAllCustomers()) {
                if (customer.getCustomer_ID() > customer_ID)
                    customer_ID = (customer.getCustomer_ID());
                customer_ID = ++customer_ID;
            }

            String customerName = customerNameTextField.getText();
            String address = customerAddressTextField.getText();
            String postalCode = customerPostalCodeTextField.getText();
            String phone = customerPhoneTextField.getText();
            int division_ID = Integer.valueOf(String.valueOf(customerDivisionComboBox.getSelectionModel().getSelectedItem().getDivision_ID()));

            if (!customerName.equals("") && !address.equals("") && !postalCode.equals("") && !phone.equals("")) {
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/MainCustomer.fxml"));
                scene.setStyle(("-fx-font-family: 'serif';"));
                stage.setScene(new Scene(scene));
                stage.show();

                Customer newCustomerAdded = new Customer(customer_ID, customerName, address, postalCode, phone, division_ID);
                ListProvider.addCustomer(newCustomerAdded);

                CustomerDB.addCustomer(customer_ID, customerName, address, postalCode, phone, division_ID);

            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                DialogPane dialogPane1 = errorAlert.getDialogPane();
                dialogPane1.setStyle("-fx-font-family: serif;");
                errorAlert.setTitle("Missing values");
                errorAlert.setContentText("Please enter customer name, address, postal code, and phone number.");
                errorAlert.showAndWait();
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * Cancels customer information and returns to the main appointments controller.
     *
     * @param event Cancel button clicked.
     * @throws IOException
     */
    public void onCancelCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainCustomer.fxml"));
        scene.setStyle(("-fx-font-family: 'serif';"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Sets the filtered lists in the division combobox.
     *
     * @param mouseEvent Country button clicked.
     * @throws IOException
     */
    @FXML
    void SetDivisionID(MouseEvent mouseEvent) throws IOException, SQLException{
        if (customerCountryComboBox.getSelectionModel().isEmpty()) {
            System.out.println(customerCountryComboBox.getSelectionModel().toString());
            return;
        }

        // US filter
        else if (customerCountryComboBox.getSelectionModel().getSelectedItem().getCountryName().equals("U.S")) {
            try {
                customerDivisionComboBox.setItems(DivisionDB.getusFilteredDivision());
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }

        // UK filter
        else if (customerCountryComboBox.getSelectionModel().getSelectedItem().getCountryName().equals("UK")) {
            try {
                customerDivisionComboBox.setItems(DivisionDB.getukFilteredDivision());

            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }

        // Canada filter
        else if (customerCountryComboBox.getSelectionModel().getSelectedItem().getCountryName().equals("Canada")) {
            try {
                customerDivisionComboBox.setItems(DivisionDB.getcanadaFilteredDivision());

            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }
    }

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        customerCountryComboBox.setItems(ListProvider.getAllCountries());
        customerDivisionComboBox.setItems(ListProvider.getAllDivisions());
    }
}
