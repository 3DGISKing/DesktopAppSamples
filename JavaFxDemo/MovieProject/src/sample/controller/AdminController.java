package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public static Stage addMovieStage;
    public static Stage addnewCustomerStage;
    public static Stage removeCustomerStage;
    public static Stage removeMovieStage;
    public static Stage viewAllCustStage;

    @FXML
    void openAddMoviePage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/AddMovie.fxml"));
            addMovieStage.setScene(new Scene(root, 757, 484));
            addMovieStage.setTitle("Add New Movies");
            addMovieStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openAddnewCustomersPage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/AddCustomer.fxml"));
            addnewCustomerStage.setScene(new Scene(root, 757, 484));
            addnewCustomerStage.setTitle("Add New Customers");
            addnewCustomerStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openRemoveCustomersPage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/RemoveCustomer.fxml"));
            removeCustomerStage.setScene(new Scene(root, 757, 484));
            removeCustomerStage.setTitle("Remove Customers");
            removeCustomerStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openRemoveMoviesPage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/RemoveMovie.fxml"));
            removeMovieStage.setScene(new Scene(root, 757, 484));
            removeMovieStage.setTitle("Remove Movies");
            removeMovieStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openViewAllCustomersPage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/ShowAllCustomers.fxml"));
            viewAllCustStage.setScene(new Scene(root, 757, 484));
            viewAllCustStage.setTitle("View All Customers");
            viewAllCustStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openViewallMoviesPage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/ShowAllMovies.fxml"));
            KioskController.allMoviesStage.setScene(new Scene(root, 757, 484));
            KioskController.allMoviesStage.setTitle("All Movies");
            KioskController.allMoviesStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void quitApplication(ActionEvent event) {
        KioskController.adminStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addMovieStage = new Stage();
        addnewCustomerStage = new Stage();
        removeCustomerStage = new Stage();
        removeMovieStage = new Stage();
        viewAllCustStage = new Stage();
    }
}
