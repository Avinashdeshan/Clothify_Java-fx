package controller.employee;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Employee;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colCompany;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<Employee> tblEmployee;

    @FXML
    private JFXTextField txtCompany;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        boolean isAdded = EmployeeController.getInstance().addEmployee(new Employee(txtID.getText(), txtName.getText(), txtCompany.getText(), txtEmail.getText(), txtPassword.getText()));
        new Alert(Alert.AlertType.INFORMATION, isAdded ? "Employee Added Successfully" : "Employee Not Added Successfully").show();
        if (isAdded)
            LoadTable();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        boolean isDeleted = EmployeeController.getInstance().deleteEmployee(txtID.getText());
        new Alert(Alert.AlertType.INFORMATION, isDeleted ? "Employee Deleted Successfully" : "Employee Deletion Failed").show();
        if (isDeleted)
            LoadTable();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        Employee employee = EmployeeController.getInstance().searchEmployee(txtID.getText());
        if (employee != null) {
            setValueToText(employee);
        } else {
            new Alert(Alert.AlertType.WARNING, "Employee Not Found").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        boolean isUpdated = EmployeeController.getInstance().updateEmployee(new Employee(txtID.getText(), txtName.getText(), txtCompany.getText(), txtEmail.getText(), txtPassword.getText()));
        new Alert(Alert.AlertType.INFORMATION, isUpdated ? "Employee Updated Successfully" : "Employee Update Failed").show();
        if (isUpdated)
            LoadTable();
    }

    private void setValueToText(Employee newValue) {
        txtID.setText(newValue.getId());
        txtName.setText(newValue.getName());
        txtCompany.setText(newValue.getCompany());
        txtEmail.setText(newValue.getEmail());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        LoadTable();

        tblEmployee.getSelectionModel().selectedItemProperty().addListener((observableValue, employee, newValue) -> {
            if (newValue != null) {
                setValueToText(newValue);
            }
        });
    }

    private void LoadTable() {
        ObservableList<Employee> allEmployee = EmployeeController.getInstance().getAllEmployee();
            tblEmployee.setItems(allEmployee);
    }
}
