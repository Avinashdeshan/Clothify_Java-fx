package controller.product;

import javafx.collections.ObservableList;
import model.Product;

public interface ProductService {

    ObservableList<Product> getAllProduct();

    boolean addProduct(Product product);

    boolean deleteProduct(int productID);

    ObservableList<Product> searchProducts(String keyword);

}
