package Controller.employee;

import dbconnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeController implements EmployeeService{
    private static EmployeeController employeeController;

    private EmployeeController (){

    }

    public static EmployeeController getInstance(){
        if (employeeController==null){
            employeeController=new EmployeeController();
        }
        return employeeController;
    }

    @Override
    public ObservableList<Employee> getAllEmployee() {
        ObservableList<Employee> employeeObservableList = FXCollections.observableArrayList();
        try {
            ResultSet res = DBConnection.getInstance().getConnection().createStatement().executeQuery("select * from employee");
            while(res.next()){
                employeeObservableList.add(new Employee(res.getString(1),res.getString(2),res.getString(3),res.getString(4)));
            }
            return employeeObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addEmployee(Employee employee) {
        try {
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO employee VALUES (?,?,?,?)");
            stm.setObject(1, employee.getId());
            stm.setObject(2, employee.getName());
            stm.setObject(3, employee.getCompany());
            stm.setObject(4, employee.getEmail());
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        try {
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE employee SET name = ?, company = ?, email = ? WHERE id = ?");
            stm.setObject(1, employee.getName());
            stm.setObject(2, employee.getCompany());
            stm.setObject(3, employee.getEmail());
            stm.setObject(4, employee.getId());
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean deleteEmployee(String id) {
        try {
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM employee WHERE id = ?");
            stm.setObject(1, id);
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee searchEmployee(String id) {
        try {
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM employee WHERE id = ?");
            stm.setObject(1, id);
            ResultSet res = stm.executeQuery();
            if (res.next()) {
                return new Employee(res.getString(1), res.getString(2), res.getString(3), res.getString(4));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
