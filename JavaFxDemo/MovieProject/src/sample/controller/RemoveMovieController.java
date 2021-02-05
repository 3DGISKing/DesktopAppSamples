package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.KioskApplication;
import sample.model.Genre;
import sample.model.Movie;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RemoveMovieController implements Initializable {

    @FXML
    private TableView<Movie> removeMovieTable;

    @FXML
    private TableColumn<Movie, String> movieTitleCol;

    @FXML
    private TableColumn<Movie, Integer> movieYearCol;

    @FXML
    private TableColumn<Movie, Genre> movieGenreCol;

    @FXML
    private TableColumn<Movie, Integer> moviePriceCol;

    @FXML
    void quitApplication(ActionEvent event) {
        AdminController.removeMovieStage.close();
    }

    @FXML
    void removeMovieFromTable(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Alert");
        if (removeMovieTable.getSelectionModel().getSelectedItem() != null) {
            alert.setContentText(removeMovieTable.getSelectionModel().getSelectedItem().getTitle() + " movie Removed Successfully!!! \n Please Refresh Your Page");
            AddMovieController.movies.removeMovie(removeMovieTable.getSelectionModel().getSelectedItem());
            alert.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieTitleCol.setCellValueFactory(new PropertyValueFactory<Movie, String>("title"));
        movieYearCol.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("year"));
        movieGenreCol.setCellValueFactory(new PropertyValueFactory<Movie, Genre>("genre"));
        moviePriceCol.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("price"));
        if (AddMovieController.movies.getAllMovies().size() > 0) {
            System.out.println("we have movies in my array");
            removeMovieTable.setItems(AddMovieController.movies.getAllMovies());
        } else {
            System.out.println("No added Movies");
        }
    }
}
