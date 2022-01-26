package Controller;

import Model.Customer;
import Utils.ListProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class that provides logic for the Customer Information Report screen of the application.
 *
 * @author Erik Nilson
 */
public class CustomerInfoReportController implements Initializable {
    Stage stage;
    Parent scene;

    /**
     * The customer info table.
     */
    @FXML
    public TableView<Customer> customerInfoTable;

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
     * Goes back to the main controller.
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerInfoTable.setItems(ListProvider.getAllCustomers());
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
        customerPostalCol.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
    }
}
