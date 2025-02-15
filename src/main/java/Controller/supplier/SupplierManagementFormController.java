package Controller.supplier;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Supplier;

import java.net.URL;
import java.util.ResourceBundle;

public class SupplierManagementFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colItem;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colcompany;

    @FXML
    private TableView<Supplier> tblSupplier;

    @FXML
    private JFXTextField txtCompany;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXTextField txtItem;

    @FXML
    private JFXTextField txtName;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        boolean isAdded = SupplierManagementController.getInstance().addSupplier(new Supplier(txtID.getText(), txtName.getText(), txtCompany.getText(), txtEmail.getText(), txtItem.getText()));
        new Alert(Alert.AlertType.INFORMATION, isAdded ? "Supplier Added Successfully" : "Supplier Not Added Successfully").show();
        if (isAdded)
            LoadTable();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        boolean isDeleted = SupplierManagementController.getInstance().deleteSupplier(txtID.getText());
        new Alert(Alert.AlertType.INFORMATION, isDeleted ? "Supplier Deleted Successfully" : "Supplier Deletion Failed").show();
        if (isDeleted)
            LoadTable();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        Supplier supplier = SupplierManagementController.getInstance().searchSupplier(txtID.getText());
        if (supplier != null) {
            setValueToText(supplier);
        } else {
            new Alert(Alert.AlertType.WARNING, "Supplier Not Found").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        boolean isUpdated = SupplierManagementController.getInstance().updateSupplier(new Supplier(txtID.getText(), txtName.getText(), txtCompany.getText(), txtEmail.getText(), txtItem.getText()));
        new Alert(Alert.AlertType.INFORMATION, isUpdated ? "Supplier Updated Successfully" : "Supplier Update Failed").show();
        if (isUpdated)
            LoadTable();
    }

    private void setValueToText(Supplier newValue) {
        txtID.setText(newValue.getId());
        txtName.setText(newValue.getName());
        txtCompany.setText(newValue.getCompany());
        txtEmail.setText(newValue.getEmail());
        txtItem.setText(newValue.getItem());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colcompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colItem.setCellValueFactory(new PropertyValueFactory<>("item"));
        LoadTable();

        tblSupplier.getSelectionModel().selectedItemProperty().addListener((observableValue, supplier, newValue) -> {
            if (newValue != null) {
                setValueToText(newValue);
            }
        });
    }

    private void LoadTable() {
        ObservableList<Supplier> allSupplier = SupplierManagementController.getInstance().getAllSupplier();
        tblSupplier.setItems(allSupplier);
    }
}
