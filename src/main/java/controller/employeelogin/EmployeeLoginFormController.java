package controller.employeelogin;

import dbconnection.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeLoginFormController {
    @FXML
    private TextField txtEmail1;

    @FXML
    private TextField txtPassword1;

    public void btnLoginOnAction(ActionEvent actionEvent) {
        String email = txtEmail1.getText().trim();
        String password = txtPassword1.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill in both email and password.");
            return;
        }

        if (validateEmployee(email, password)) {
            try {
                Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                currentStage.close();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
                Scene scene = new Scene(loader.load());

                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Dashboard");
                stage.show();

            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Error loading dashboard: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Invalid email or password.");
        }
    }

    private boolean validateEmployee(String email, String password) {
        String query = "SELECT * FROM employee WHERE email = ? AND password = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.show();
    }

    public void btnForgotpasswordOnAction(ActionEvent actionEvent) {

    }
}
