package Controller.employee;

import javafx.collections.ObservableList;
import model.Employee;

public interface EmployeeService {
    ObservableList<Employee> getAllEmployee();

    boolean addEmployee(Employee employee);

    boolean updateEmployee(Employee employee);

    boolean deleteEmployee(String id);

    Employee searchEmployee(String id);
}
