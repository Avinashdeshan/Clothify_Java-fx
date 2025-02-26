package controller.adminregister;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class RegisterFormController {

    @FXML
    private PasswordField txtConfirmedPassword;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    void btnRegisterOnAction(ActionEvent event) throws IOException {
        boolean isReg = RegisterController.getInstance().setUsers(
                txtUsername.getText(),
                txtEmail.getText(),
                txtPassword.getText(),
                txtConfirmedPassword.getText()
        );

        if (isReg) {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/adminloginform.fxml")))));
            stage.show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Admin Registration Failed").show();
        }
    }

    @FXML
    void btnSigninOnAction(ActionEvent actionEvent) throws IOException {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();

        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/adminloginform.fxml"))));
        stage.show();
    }
}
