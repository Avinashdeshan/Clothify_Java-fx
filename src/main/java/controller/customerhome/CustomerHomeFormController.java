package controller.customerhome;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.Customer;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerHomeFormController implements Initializable {

    @FXML
    private JFXComboBox<?> cmbStatus;

    @FXML
    private TableColumn<?, ?> colCity;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colFirstname;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colLastname;

    @FXML
    private TableColumn<?, ?> colPostalcode;

    @FXML
    private TableView<Customer> tblCustomer;

    @FXML
    private TextField txtCity;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtPostalCode;

    @FXML
    private TextField txtSearchCustomerBar;

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {
        boolean isAdded = CustomerHomeController.getInstance().addCustomer(new Customer(txtID.getText(), txtFirstName.getText(), txtLastName.getText(), txtEmail.getText(), txtCity.getText(), txtPostalCode.getText()));
        new Alert(Alert.AlertType.INFORMATION, isAdded ? "Customer Added Successfully" : "Employee Not Added Successfully").show();
        if (isAdded)
            LoadTable();
    }

    @FXML
    void btnDeleteCustomerOnAction(ActionEvent event) {
        boolean isDeleted = CustomerHomeController.getInstance().deleteCustomer(txtID.getText());
        new Alert(Alert.AlertType.INFORMATION, isDeleted ? "Customer Deleted Successfully" : "Customer Deletion Failed").show();
        if (isDeleted)
            LoadTable();
    }

    @FXML
    void btnUpdateCustomerOnAction(ActionEvent event) {
        boolean isUpdated = CustomerHomeController.getInstance().updateCustomer(new Customer(txtID.getText(), txtFirstName.getText(), txtLastName.getText(), txtEmail.getText(), txtCity.getText(), txtPostalCode.getText()));
        new Alert(Alert.AlertType.INFORMATION, isUpdated ? "Customer Updated Successfully" : "Customer Update Failed").show();
        if (isUpdated)
            LoadTable();
    }

    @FXML
    void txtSearchCustomer(KeyEvent event) {
        String searchText = txtSearchCustomerBar.getText().toLowerCase();
        ObservableList<Customer> allCustomers = CustomerHomeController.getInstance().getAllCustomer();

        ObservableList<Customer> filteredList = allCustomers.filtered(customer ->
                customer.getId().toLowerCase().contains(searchText) ||
                        customer.getFirstname().toLowerCase().contains(searchText) ||
                        customer.getLastname().toLowerCase().contains(searchText) ||
                        customer.getEmail().toLowerCase().contains(searchText) ||
                        customer.getCity().toLowerCase().contains(searchText) ||
                        customer.getPostalcode().toLowerCase().contains(searchText)
        );
        tblCustomer.setItems(filteredList);
    }

    private void setValueToText(Customer newValue) {
        txtID.setText(newValue.getId());
        txtFirstName.setText(newValue.getFirstname());
        txtLastName.setText(newValue.getLastname());
        txtEmail.setText(newValue.getEmail());
        txtCity.setText(newValue.getCity());
        txtPostalCode.setText(newValue.getPostalcode());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        colLastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colPostalcode.setCellValueFactory(new PropertyValueFactory<>("postalcode"));
        LoadTable();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, employee, newValue) -> {
            if (newValue != null) {
                setValueToText(newValue);
            }
        });
    }

    private void LoadTable() {
        ObservableList<Customer> allCustomer = CustomerHomeController.getInstance().getAllCustomer();
        tblCustomer.setItems(allCustomer);
    }
}
