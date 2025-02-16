package Controller.customer;

import dbconnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerController implements CustomerService{
    private static CustomerController customerController;

    private CustomerController (){

    }

    public static CustomerController getInstance(){
        if (customerController==null){
            customerController= new CustomerController();
        }
        return customerController;
    }

    @Override
    public ObservableList<Customer> getAllCustomers() {
            ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
            try {
                ResultSet res = DBConnection.getInstance().getConnection().createStatement().executeQuery("select * from customer");
                while (res.next()) {
                    customerObservableList.add(new Customer(res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5)));
                }
                return customerObservableList;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    @Override
    public boolean addCustomer(Customer customer) {
        try {
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO customer VALUES (?,?,?,?,?)");
            stm.setObject(1, customer.getFirstname());
            stm.setObject(2, customer.getLastname());
            stm.setObject(3, customer.getEmail());
            stm.setObject(4, customer.getCity());
            stm.setObject(5, customer.getPostalcode());
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
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCustomer(String email) {
        try {
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM customer WHERE email = ?");
            stm.setObject(3, email);
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer searchCustomer(String email) {
        try {
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM customer WHERE email = ?");
            stm.setObject(3, email);
            ResultSet res = stm.executeQuery();
            if (res.next()) {
                return new Customer(res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
