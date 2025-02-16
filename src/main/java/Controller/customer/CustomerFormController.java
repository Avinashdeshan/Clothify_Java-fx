package Controller.customer;

import Controller.employee.EmployeeController;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.Employee;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colCity;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colFirstName;

    @FXML
    private TableColumn<?, ?> colLastName;

    @FXML
    private TableColumn<?, ?> colPostalCode;

    @FXML
    private TableView<?> tblCustomer;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtFirstName;

    @FXML
    private JFXTextField txtLastName;

    @FXML
    private JFXTextField txtPostalCode;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        boolean isAdded = CustomerController.getInstance().addCustomer(new Customer(txtFirstName.getText(), txtLastName.getText(), txtEmail.getText(), txtCity.getText(), txtPostalCode.getText()));
        new Alert(Alert.AlertType.INFORMATION, isAdded ? "Customer Added Successfully" : "Customer Not Added Successfully").show();
        if (isAdded)
            LoadTable();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        boolean isDeleted = EmployeeController.getInstance().deleteEmployee(txtEmail.getText());
        new Alert(Alert.AlertType.INFORMATION, isDeleted ? "Customer Deleted Successfully" : "Customer Deletion Failed").show();
        if (isDeleted)
            LoadTable();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        Customer customer = CustomerController.getInstance().searchCustomer(txtEmail.getText());
        if (customer != null) {
            setValueToText(customer);
        } else {
            new Alert(Alert.AlertType.WARNING, "Customer Not Found").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        boolean isUpdated = CustomerController.getInstance().updateEmployee(new Customer(txtFirstName.getText(), txtLastName.getText(), txtEmail.getText(), txtCity.getText(), txtPostalCode.getText()));
        new Alert(Alert.AlertType.INFORMATION, isUpdated ? "Employee Updated Successfully" : "Employee Update Failed").show();
        if (isUpdated)
            LoadTable();
    }

    private void setValueToText(Customer newValue) {
        txtFirstName.setText(newValue.getFirstname());
        txtLastName.setText(newValue.getLastname());
        txtEmail.setText(newValue.getEmail());
        txtCity.setText(newValue.getCity());
        txtPostalCode.setText(newValue.getPostalcode());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalcode"));
        LoadTable();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, customer, newValue) -> {
            if (newValue != null) {
                setValueToText((Customer) newValue);
            }
        });
    }

    private void LoadTable() {
        ObservableList<Customer> allCustomer = CustomerController.getInstance().getAllCustomers();
        if (allCustomer != null) {
            tblCustomer.setItems(allCustomer);
        } else {
            tblCustomer.setItems(FXCollections.observableArrayList());
        }
    }
}
