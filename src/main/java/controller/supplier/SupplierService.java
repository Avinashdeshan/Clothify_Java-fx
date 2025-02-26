package controller.supplier;

import javafx.collections.ObservableList;
import model.Supplier;

public interface SupplierService {
    ObservableList<Supplier> getAllSupplier();

    boolean addSupplier(Supplier supplier);

    boolean updateSupplier(Supplier supplier);

    boolean deleteSupplier(String id);

    Supplier searchSupplier(String id);
}
