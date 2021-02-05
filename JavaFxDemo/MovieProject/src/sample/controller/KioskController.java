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

public class KioskController implements Initializable {

    public static Stage adminStage;
    public static Stage exploreCatStage;
    public static Stage topUpStage;
    public static Stage favStage;
    public static Stage custRecordStage;
    public static Stage allMoviesStage;


    @FXML
    void openAdministrationPage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/Admin.fxml"));
            adminStage.setTitle("Administration");
            adminStage.setScene(new Scene(root, 757, 484));
            adminStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openCustmoreRecordPage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/CustomerRecord.fxml"));
            custRecordStage.setScene(new Scene(root, 780, 680));
            custRecordStage.setTitle("Customer Record");
            custRecordStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openExploreCateloguePage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/Catalogue.fxml"));
            exploreCatStage.setScene(new Scene(root, 757, 484));
            exploreCatStage.setTitle("Explore Catalogue");
            exploreCatStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openFavMoviePage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/FavouriteMovies.fxml"));
            favStage.setScene(new Scene(root, 757, 484));
            favStage.setTitle("Favourite Movies");
            favStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openTopupAccountPage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/TopUpAccount.fxml"));
            topUpStage.setScene(new Scene(root, 757, 347));
            topUpStage.setTitle("Top-up");
            topUpStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void quitApplication(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adminStage = new Stage();
        exploreCatStage = new Stage();
        topUpStage = new Stage();
        favStage = new Stage();
        custRecordStage = new Stage();
        allMoviesStage = new Stage();
    }
}
