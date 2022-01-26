package Controller;

import Model.Appointment;
import Model.Customer;
import Utils.AppointmentDB;
import Utils.CustomerDB;
import Utils.ListProvider;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class that provides logic for the Main Customer screen of the application.
 *
 * @author Erik Nilson
 */
public class MainCustomerController implements Initializable {
    Stage stage;
    Parent scene;

    /**
     * The customer table.
     */
    @FXML
    public TableView<Customer> customerTable;

    /**
     * The customer ID column.
     */
    @FXML
    public TableColumn<Customer, Integer> customerIDCol;

    /**
     * The customer name column.
     */
    @FXML
    public TableColumn<Customer, String> customerNameCol;

    /**
     * The customer address column.
     */
    @FXML
    public TableColumn<Customer, String> customerAddressCol;

    /**
     * The customer postal code column.
     */
    @FXML
    public TableColumn<Customer, String> customerPostalCol;

    /**
     * The customer phone column.
     */
    @FXML
    public TableColumn<Customer, String> customerPhoneCol;

    /**
     * The customer division ID column.
     */
    @FXML
    private TableColumn<Customer, Integer> customerDivisionIDCol;

    private static Customer selectedCustomer;

    public static Customer selectCustomerUpdate() { return selectedCustomer;
    }

    /**
     * Loads the add customer screen.
     *
     * @param event Add new customer button clicked.
     * @throws IOException
     */
    @FXML
    public void onAddCustomerButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddCustomer.fxml"));
        scene.setStyle(("-fx-font-family: 'serif';"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Loads the update customer screen. If no customer is selected, an error window pops up.
     *
     * @param event Update customer button clicked.
     * @throws IOException
     */
    @FXML
    public void onUpdateCustomerButton(ActionEvent event) throws IOException {
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            DialogPane dialogPane1 = errorAlert.getDialogPane();
            dialogPane1.setStyle("-fx-font-family: serif;");
            errorAlert.setTitle("Error");
            errorAlert.setContentText("Please select a customer to update.");
            Optional<ButtonType> result = errorAlert.showAndWait();
        } else {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/UpdateCustomer.fxml"));
            scene.setStyle(("-fx-font-family: 'serif';"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Deletes a customer from the main customer screen. If no customer is selected, an error window pops up.
     *
     * @param event Delete customer button clicked
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onDeleteCustomerButton(ActionEvent event) throws SQLException, IOException {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            DialogPane dialogPane = errorAlert.getDialogPane();
            dialogPane.setStyle("-fx-font-family: serif;");
            errorAlert.setTitle("Error");
            errorAlert.setContentText("Please select a customer to delete");
            Optional<ButtonType> removeOption = errorAlert.showAndWait();
        } else {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            DialogPane dialogPane2 = confirmationAlert.getDialogPane();
            dialogPane2.setStyle("-fx-font-family: serif;");
            confirmationAlert.setTitle("Warning");
            confirmationAlert.setContentText("Are you sure you want to delete the selected customer and any associated appointments?");
            Optional<ButtonType> removeOption = confirmationAlert.showAndWait();

            if (removeOption.isPresent() && removeOption.get() == ButtonType.OK) {
                ObservableList<Appointment> originalAppointments = selectedCustomer.getAllAppointments();

                for (int i = 0; i < originalAppointments.size(); i++) {
                    if (originalAppointments.get(i).getCustomer_ID() == selectedCustomer.getCustomer_ID()) {
                        AppointmentDB.deleteAppointment(originalAppointments.get(i).getAppointment_ID());
                    } else {
                        CustomerDB.deleteCustomer(selectedCustomer.getCustomer_ID());
                        ListProvider.deleteCustomer(selectedCustomer);
                        System.out.println("Customer deleted!");
                    }
                }
            }
        }
    }

    /**
     * Goes back to the main screen.
     *
     * @param event Back button clicked.
     * @throws IOException
     */
    @FXML
    public void onBackButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
        scene.setStyle(("-fx-font-family: 'serif';"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        customerTable.setItems(ListProvider.getAllCustomers());
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
        customerDivisionIDCol.setCellValueFactory(new PropertyValueFactory<>("Division_ID"));
        customerPostalCol.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
    }
}