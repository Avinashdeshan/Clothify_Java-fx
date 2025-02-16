package Controller.customer;

import javafx.collections.ObservableList;
import model.Customer;

public interface CustomerService {
    ObservableList<Customer> getAllCustomers();

    boolean addCustomer(Customer customer);

    boolean updateCustomer(Customer customer);

    boolean deleteCustomer(String id);

    Customer searchCustomer(String id);
}
