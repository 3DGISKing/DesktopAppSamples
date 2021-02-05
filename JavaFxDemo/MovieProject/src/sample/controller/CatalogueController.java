
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

public class CatalogueController implements Initializable {
    public static Stage allAvailableMoviesStage;
    public static Stage openMoviesBYGenreStage;
    public static Stage openMoviesByYearStage;
    public static Stage openReturnAMovieStage;
    public static Stage openRentAMovieStage;

    @FXML
    void openAllMoviesPage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/ShowAllMovies.fxml"));
            KioskController.allMoviesStage.setScene(new Scene(root, 757, 450));
            KioskController.allMoviesStage.setTitle("All Movies");
            KioskController.allMoviesStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openAvailableMoviesPage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/ShowAvailableMovies.fxml"));
            allAvailableMoviesStage.setScene(new Scene(root, 757, 484));
            allAvailableMoviesStage.setTitle("All Available Movies");
            allAvailableMoviesStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openMovieByGenrePage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/ShowMoviesByGenre.fxml"));
            openMoviesBYGenreStage.setScene(new Scene(root, 757, 484));
            openMoviesBYGenreStage.setTitle("Search Movies By Genre");
            openMoviesBYGenreStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openRentAMoviePage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/RentMovie.fxml"));
            openRentAMovieStage.setScene(new Scene(root, 757, 530));
            openRentAMovieStage.setTitle("Rented Movies");
            openRentAMovieStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openReturnAMoviePage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/ReturnMovie.fxml"));
            openReturnAMovieStage.setScene(new Scene(root, 757, 530));
            openReturnAMovieStage.setTitle("Return Movies");
            openReturnAMovieStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void opennMoviesByYearPage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/ShowMoviesByYear.fxml"));
            openMoviesByYearStage.setScene(new Scene(root, 757, 484));
            openMoviesByYearStage.setTitle("Search Movies By Year");
            openMoviesByYearStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void quitApplication(ActionEvent event) {
       KioskController.exploreCatStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allAvailableMoviesStage = new Stage();
        openMoviesBYGenreStage = new Stage();
        openMoviesByYearStage = new Stage();
        openReturnAMovieStage = new Stage();
        openRentAMovieStage = new Stage();
    }
}
