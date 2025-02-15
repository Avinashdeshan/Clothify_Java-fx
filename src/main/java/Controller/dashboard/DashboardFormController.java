package Controller.dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class DashboardFormController {
    public AnchorPane loadDashboard;

    @FXML
    void btnAddEmployeeOnAction(ActionEvent event) throws IOException {
        URL resourses=this.getClass().getResource("/view/employee.fxml");
        assert resourses !=null;

        Parent load= FXMLLoader.load(resourses);
        this.loadDashboard.getChildren().clear();
        this.loadDashboard.getChildren().add(load);
    }

    @FXML
    void btnAddSupplierOnAction(ActionEvent event) throws IOException {
        URL resourses=this.getClass().getResource("/view/customermanagement.fxml");
        assert resourses !=null;

        Parent load= FXMLLoader.load(resourses);
        this.loadDashboard.getChildren().clear();
        this.loadDashboard.getChildren().add(load);
    }

    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException {
        URL resourses=this.getClass().getResource("/view/home.fxml");
        assert resourses !=null;

        Parent load= FXMLLoader.load(resourses);
        this.loadDashboard.getChildren().clear();
        this.loadDashboard.getChildren().add(load);
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        URL resourses=this.getClass().getResource("/view/home.fxml");
        assert resourses !=null;

        Parent load= FXMLLoader.load(resourses);
        this.loadDashboard.getChildren().clear();
        this.loadDashboard.getChildren().add(load);
    }

    @FXML
    void btnOrderHistoryOnAction(ActionEvent event) {

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

    }

    @FXML
    void btnProductDetailsOnAction(ActionEvent event) {

    }

}
