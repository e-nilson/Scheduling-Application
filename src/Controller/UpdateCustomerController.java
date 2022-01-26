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
 * Controller class that provides logic for the Add Appointment screen of the application.
 *
 * @author Erik Nilson
 */
public class UpdateCustomerController implements Initializable {
    Stage stage;
    Parent scene;

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

    /**
     * The customer country combobox.
     */
    @FXML
    private ComboBox<Country> customerCountryComboBox;

    /**
     * The customer division combobox.
     */
    @FXML
    private ComboBox<Division> customerDivisionComboBox;

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
    private Customer customerToUpdate;

    /**
     * Saves customer information and returns to the main customer controller.
     *
     * @param event Save button clicked.
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    boolean onSaveCustomer(ActionEvent event) throws IOException, SQLException{
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

            if (customerName.isBlank() || address.isEmpty() || postalCode.isEmpty() || phone.isEmpty()) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                DialogPane dialogPane1 = errorAlert.getDialogPane();
                dialogPane1.setStyle("-fx-font-family: serif;");
                errorAlert.setTitle("Missing values");
                errorAlert.setContentText("Please enter customer name, address, postal code, and phone number.");
                errorAlert.showAndWait();

            } else {
                customerToUpdate.setCustomer_Name(customerName);
                customerToUpdate.setAddress(address);
                customerToUpdate.setPostal_Code(postalCode);
                customerToUpdate.setPhone(phone);
                customerToUpdate.setDivision_ID(division_ID);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/MainCustomer.fxml"));
                scene.setStyle(("-fx-font-family: 'serif';"));
                stage.setScene(new Scene(scene));
                stage.show();

                return CustomerDB.updateCustomer(
                        Integer.valueOf(customerIDTextField.getText()),
                        customerNameTextField.getText(),
                        customerAddressTextField.getText(),
                        customerPostalCodeTextField.getText(),
                        customerPhoneTextField.getText(),
                        Integer.valueOf(String.valueOf(customerDivisionComboBox.getSelectionModel().getSelectedItem().getDivision_ID())));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Exits the update customer controller and returns to the main customer controller.
     *
     * @param event Cancel button clicked.
     * @throws IOException
     */
    @FXML
    void onCancelCustomer(ActionEvent event) throws IOException {
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
     * @throws SQLException
     */
    @FXML
    public void setDivisionID(MouseEvent mouseEvent) throws SQLException, IOException{
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
        customerToUpdate = MainCustomerController.selectCustomerUpdate();
        customerIDTextField.setText(String.valueOf(customerToUpdate.getCustomer_ID()));
        customerNameTextField.setText(customerToUpdate.getCustomer_Name());
        customerAddressTextField.setText(customerToUpdate.getAddress());
        customerPostalCodeTextField.setText(customerToUpdate.getPostal_Code());
        customerPhoneTextField.setText(customerToUpdate.getPhone());
        int comboBoxPreset = customerToUpdate.getDivision_ID();

        Division div = new Division(comboBoxPreset);

        try {
            System.out.println(DivisionDB.getDivisionByID(comboBoxPreset));
            customerDivisionComboBox.setValue(DivisionDB.getDivisionByID(comboBoxPreset));

        } catch (SQLException exc) {
            exc.printStackTrace();
        }

        // sets the combobox
        if (div.getDivision_ID() < 55)
        {
            String countryName = "U.S";
            Country c = new Country(countryName);
            customerCountryComboBox.setValue(c);
        }
        else if (div.getDivision_ID() > 55 && div.getDivision_ID() < 73)
        {
            String countryName = "Canada";
            Country c = new Country(countryName);
            customerCountryComboBox.setValue(c);
        }
        else if (div.getDivision_ID() > 73)
        {
            String countryName = "UK";
            Country c = new Country(countryName);
            customerCountryComboBox.setValue(c);
        }

        try {
            customerCountryComboBox.setItems(ListProvider.getAllCountries());

        } catch (Exception exc) {
            exc.printStackTrace();
        }

        customerDivisionComboBox.setItems(ListProvider.getAllDivisions());

            // US filter
            if (customerCountryComboBox.getSelectionModel().getSelectedItem().getCountryName().equals("U.S")) {
                try {
                    customerDivisionComboBox.setItems(DivisionDB.getusFilteredDivision());

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

            // UK filter
            else if (customerCountryComboBox.getSelectionModel().getSelectedItem().getCountryName().equals("UK")) {
                try {
                    customerDivisionComboBox.setItems(DivisionDB.getukFilteredDivision());

                } catch (SQLException exc) {
                    exc.printStackTrace();
                }
            }
        }
}
