package controller.product;

import dbconnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Product;

import java.sql.*;

public class ProductManagementController implements ProductService {
    private static ProductManagementController productManagementController;

    private ProductManagementController() { }

    public static ProductManagementController getInstance() {
        if (productManagementController == null) {
            productManagementController = new ProductManagementController();
        }
        return productManagementController;
    }

    @Override
    public ObservableList<Product> getAllProduct() {
        ObservableList<Product> productObservableList = FXCollections.observableArrayList();
        try {
            ResultSet res = DBConnection.getInstance().getConnection().createStatement()
                    .executeQuery("SELECT * FROM product");
            while (res.next()) {
                productObservableList.add(new Product(
                        res.getInt("productID"),
                        res.getString("productName"),
                        res.getDouble("productPrice"),
                        res.getInt("productStock"),
                        res.getInt("productSupplierID"),
                        res.getInt("productCategoryID"),
                        res.getString("productImagePath")
                ));
            }
            return productObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addProduct(Product product) {
        String query = "INSERT INTO product (productName, productPrice, productStock, productSupplierID, productCategoryID, productImagePath) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = DBConnection.getInstance().getConnection().prepareStatement(query)) {
            stmt.setString(1, product.getProductName());
            stmt.setDouble(2, product.getProductPrice());
            stmt.setInt(3, product.getProductStock());
            stmt.setInt(4, product.getProductSupplierID());
            stmt.setInt(5, product.getProductCategoryID());
            stmt.setString(6, product.getProductImagePath());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteProduct(int productID) {
        String query = "DELETE FROM product WHERE productID = ?";
        try {
            PreparedStatement stmt = DBConnection.getInstance().getConnection().prepareStatement(query);
            stmt.setInt(1, productID);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Product> searchProducts(String keyword) {
        ObservableList<Product> productObservableList = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM product WHERE productName LIKE ?";
            PreparedStatement stmt = DBConnection.getInstance().getConnection().prepareStatement(query);
            stmt.setString(1, "%" + keyword + "%");
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                productObservableList.add(new Product(
                        res.getInt("productID"),
                        res.getString("productName"),
                        res.getDouble("productPrice"),
                        res.getInt("productStock"),
                        res.getInt("productSupplierID"),
                        res.getInt("productCategoryID"),
                        res.getString("productImagePath")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productObservableList;
    }
}
