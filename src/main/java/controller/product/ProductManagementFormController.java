package controller.product;

import com.jfoenix.controls.JFXComboBox;
import dbconnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import model.Product;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ProductManagementFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbCategory, cmbSupplier;

    @FXML
    private TableColumn<Product, Integer> colId, colStock, colCategory;

    @FXML
    private TableColumn<Product, String> colPName, colPSupplier;

    @FXML
    private TableColumn<Product, Double> colPrice;

    @FXML
    private TextField searchBar, txtName, txtProductPrice, txtProductStock;

    @FXML
    private TableView<Product> tblProduct;

    private String selectedImagePath = "";

    @FXML
    void btnAddProductOnAction(ActionEvent event) {
        try {
            if (txtName.getText().isEmpty() || txtProductPrice.getText().isEmpty() || txtProductStock.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please fill in all fields.");
                return;
            }

            String productName = txtName.getText();
            double price = Double.parseDouble(txtProductPrice.getText());
            int stock = Integer.parseInt(txtProductStock.getText());

            if (cmbSupplier.getValue() == null || cmbCategory.getValue() == null) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please select a supplier and category.");
                return;
            }

            int supplierID = Integer.parseInt(cmbSupplier.getValue().split(" - ")[0]);
            int categoryID = Integer.parseInt(cmbCategory.getValue().split(" - ")[0]);

            if (selectedImagePath.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please select an image.");
                return;
            }

            String imagePath = selectedImagePath;

            Product product = new Product(0, productName, price, stock, supplierID, categoryID, imagePath);
            boolean isAdded = ProductManagementController.getInstance().addProduct(product);

            if (isAdded) {
                loadProducts();
                clearFields();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Product added successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add product.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter valid numbers for price and stock.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String success, String s) {
    }

    @FXML
    void btnBrowseOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            selectedImagePath = file.getAbsolutePath();
        }
    }

    @FXML
    void btnDeleteProductOnAction(ActionEvent event) {
        Product selectedProduct = tblProduct.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            boolean isDeleted = ProductManagementController.getInstance().deleteProduct(selectedProduct.getProductID());

            if (isDeleted) {
                loadProducts();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a product to delete.");
        }
    }

    @FXML
    void searchBarOnAction(KeyEvent event) {
        String keyword = searchBar.getText();
        ObservableList<Product> searchResults = ProductManagementController.getInstance().searchProducts(keyword);
        tblProduct.setItems(searchResults);
    }

    private void clearFields() {
        txtName.clear();
        txtProductPrice.clear();
        txtProductStock.clear();
        cmbSupplier.getSelectionModel().clearSelection();
        cmbCategory.getSelectionModel().clearSelection();
        selectedImagePath = "";
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("productID"));
        colPName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("productStock"));
        colPSupplier.setCellValueFactory(new PropertyValueFactory<>("productSupplierID"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("productCategoryID"));

        loadCategories();
        loadSuppliers();
        loadProducts();
    }

    private void loadCategories() {
        Task<ObservableList<String>> loadCategoriesTask = new Task<>() {
            @Override
            protected ObservableList<String> call() throws Exception {
                ObservableList<String> categoryList = FXCollections.observableArrayList();
                try (ResultSet rs = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM category")) {
                    while (rs.next()) {
                        categoryList.add(rs.getInt("categoryID") + " - " + rs.getString("categoryName"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return categoryList;
            }
        };
        loadCategoriesTask.setOnSucceeded(event -> cmbCategory.setItems(loadCategoriesTask.getValue()));
        new Thread(loadCategoriesTask).start();
    }

    private void loadSuppliers() {
        Task<ObservableList<String>> loadSuppliersTask = new Task<>() {
            @Override
            protected ObservableList<String> call() throws Exception {
                ObservableList<String> supplierList = FXCollections.observableArrayList();
                try (ResultSet rs = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM suppliers")) {
                    while (rs.next()) {
                        supplierList.add(rs.getInt("id") + " - " + rs.getString("name"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return supplierList;
            }
        };
        loadSuppliersTask.setOnSucceeded(event -> cmbSupplier.setItems(loadSuppliersTask.getValue()));
        new Thread(loadSuppliersTask).start();
    }

    private void loadProducts() {
        Task<ObservableList<Product>> loadProductsTask = new Task<>() {
            @Override
            protected ObservableList<Product> call() throws Exception {
                return ProductManagementController.getInstance().getAllProduct();
            }
        };
        loadProductsTask.setOnSucceeded(event -> tblProduct.setItems(loadProductsTask.getValue()));
        new Thread(loadProductsTask).start();
    }
}
