package controller.dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class DashboardFormController {
    public AnchorPane loadDashboard;

    @FXML
    void btnAddEmployeeOnAction(ActionEvent event) throws IOException {
        URL resourses=this.getClass().getResource("/view/employee.fxml");
        assert resourses !=null;

        Parent load= FXMLLoader.<Parent>load(resourses);
        this.loadDashboard.getChildren().clear();
        this.loadDashboard.getChildren().add(load);
    }

    @FXML
    void btnAddSupplierOnAction(ActionEvent event) throws IOException {
        URL resourses=this.getClass().getResource("/view/suppliermanegement.fxml");
        assert resourses !=null;

        Parent load= FXMLLoader.<Parent>load(resourses);
        this.loadDashboard.getChildren().clear();
        this.loadDashboard.getChildren().add(load);
    }

    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException {
        URL resourses=this.getClass().getResource("/view/admindashboardHome.fxml");
        assert resourses !=null;

        Parent load= FXMLLoader.<Parent>load(resourses);
        this.loadDashboard.getChildren().clear();
        this.loadDashboard.getChildren().add(load);
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("/view/home.fxml");
        assert resource != null;

        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        Stage homeStage = new Stage();
        homeStage.setScene(scene);
        homeStage.setTitle("Home");
        homeStage.show();
    }

    @FXML
    void btnProductDetailsOnAction(ActionEvent event) throws IOException {
        URL resourses = this.getClass().getResource("/view/productmanagement.fxml");
        assert resourses != null;

        Parent load = FXMLLoader.<Parent>load(resourses);
        this.loadDashboard.getChildren().clear();
        this.loadDashboard.getChildren().add(load);
    }

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) throws IOException {
        URL resourses=this.getClass().getResource("/view/customerhome.fxml");
        assert resourses !=null;

        Parent load = FXMLLoader.<Parent>load(resourses);
        this.loadDashboard.getChildren().clear();
        this.loadDashboard.getChildren().add(load);
    }

    public void btnAddSettingOnAction(ActionEvent actionEvent) {

    }
}
