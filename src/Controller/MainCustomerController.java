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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainCustomerController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    public TableView<Customer> customerTable;

    @FXML
    public TableColumn<Customer, Integer> customerIdCol;

    @FXML
    public TableColumn<Customer, String> customerNameCol;

    @FXML
    public TableColumn<Customer, String> customerAddressCol;

    @FXML
    public TableColumn<Customer, String> customerCountryCol;

    @FXML
    public TableColumn<Customer, String> customerStateCol;

    @FXML
    public TableColumn<Customer, String> customerPostalCol;

    @FXML
    public TableColumn<Customer, String> customerPhoneCol;

    private static Customer selectedCustomer;

    public static Customer selectCustomerUpdate() { return selectedCustomer;
    }

    /**
     * Loads the add customer screen.
     *
     * @param event Add new customer button clicked.
     * @throws IOException
     */
    public void onAddCustomerButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddCustomer.fxml"));
        scene.setStyle(("-fx-font-family: 'serif';"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Loads the modify customer screen.
     *
     * @param event Modify customer button clicked.
     * @throws IOException
     */
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

    public void onDeleteCustomerButton(ActionEvent event) {

        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            DialogPane dialogPane1 = errorAlert.getDialogPane();
            dialogPane1.setStyle("-fx-font-family: serif;");
            errorAlert.setTitle("Error");
            errorAlert.setContentText("Please select a customer to delete.");
            Optional<ButtonType> result = errorAlert.showAndWait();
        }
        else {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            DialogPane dialogPane1 = confirmationAlert.getDialogPane();
            dialogPane1.setStyle("-fx-font-family: serif;");
            confirmationAlert.setTitle("Warning");
            confirmationAlert.setContentText("Are you sure you want to delete the selected customer?");
            Optional<ButtonType> removeOption = confirmationAlert.showAndWait();

            if (removeOption.isPresent() && removeOption.get() == ButtonType.OK) {
                    ListProvider.deleteCustomer(selectedCustomer);
                }
            }
        }

    /**
     * Reverts back to the main appointments screen.
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

    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        customerTable.setItems(ListProvider.getAllCustomers());
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
        //customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("Country"));
        //customerStateCol.setCellValueFactory(new PropertyValueFactory<>("State"));
        customerPostalCol.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
    }
}
