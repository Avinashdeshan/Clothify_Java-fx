package controller.supplier;

import dbconnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Supplier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierManagementController implements SupplierService{
    private static SupplierManagementController supplierManagementController;

    private SupplierManagementController (){

    }

    public static SupplierManagementController getInstance(){
        if (supplierManagementController==null){
            supplierManagementController=new SupplierManagementController();
        }
        return supplierManagementController;
    }

    @Override
    public ObservableList<Supplier> getAllSupplier() {
        ObservableList<Supplier> supplierObservableList = FXCollections.observableArrayList();
        try {
            ResultSet res = DBConnection.getInstance().getConnection().createStatement().executeQuery("select * from suppliers");
            while(res.next()){
                supplierObservableList.add(new Supplier(res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5)));
            }
            return supplierObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addSupplier(Supplier supplier) {
        try {
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO suppliers VALUES (?,?,?,?,?)");
            stm.setObject(1, supplier.getId());
            stm.setObject(2, supplier.getName());
            stm.setObject(3, supplier.getCompany());
            stm.setObject(4, supplier.getEmail());
            stm.setObject(5, supplier.getItem());
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateSupplier(Supplier supplier) {
        try {
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE suppliers SET name = ?, company = ?, email = ?, item = ? WHERE id = ?");
            stm.setObject(1,supplier.getName());
            stm.setObject(2,supplier.getCompany());
            stm.setObject(3,supplier.getEmail());
            stm.setObject(4,supplier.getItem());
            stm.setObject(5,supplier.getId());
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteSupplier(String id) {
        try {
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM suppliers WHERE id = ?");
            stm.setObject(1, id);
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Supplier searchSupplier(String id) {
        try {
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM suppliers WHERE id = ?");
            stm.setObject(1, id);
            ResultSet res = stm.executeQuery();
            if (res.next()) {
                return new Supplier(res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
