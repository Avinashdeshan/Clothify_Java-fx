package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    private int productID;
    private String productName;
    private Double productPrice;
    private int productStock;
    private int productSupplierID;
    private int productCategoryID;
    private String productImagePath;

}
