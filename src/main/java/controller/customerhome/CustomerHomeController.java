package controller.customerhome;

import dbconnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerHomeController implements CustomerService{
    private static CustomerHomeController customerHomeController;

    private CustomerHomeController (){

    }

    public static CustomerHomeController getInstance(){
        if (customerHomeController==null){
            customerHomeController=new CustomerHomeController();
        }
        return customerHomeController;
    }

    @Override
    public ObservableList<Customer> getAllCustomer() {
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        try {
            ResultSet res = DBConnection.getInstance().getConnection().createStatement().executeQuery("select * from customer");
            while(res.next()){
                customerObservableList.add(new Customer(res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5),res.getString(6)));
            }
            return customerObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addCustomer(Customer customer) {
        try {
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO customer VALUES (?,?,?,?,?,?)");
            stm.setObject(1, customer.getId());
            stm.setObject(2, customer.getFirstname());
            stm.setObject(3, customer.getLastname());
            stm.setObject(4, customer.getEmail());
            stm.setObject(5, customer.getCity());
            stm.setObject(6, customer.getPostalcode());
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        try {
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE customer SET firstname = ?, lastname = ?, email = ?, city = ?, postalcode = ? WHERE id = ?");
            stm.setObject(1, customer.getFirstname());
            stm.setObject(2, customer.getLastname());
            stm.setObject(3, customer.getEmail());
            stm.setObject(4, customer.getCity());
            stm.setObject(5, customer.getPostalcode());
            stm.setObject(6, customer.getId());
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCustomer(String id) {
        try {
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM customer WHERE id = ?");
            stm.setObject(1, id);
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer searchCustomer(String id) {
        try {
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM customer WHERE id=?");
            stm.setObject(1, id);
            ResultSet res = stm.executeQuery();
            if (res.next()) {
                return new Customer(res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
