package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.KioskApplication;
import sample.model.Genre;
import sample.model.Movie;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowMoviesByGenreController implements Initializable {
    @FXML
    private TableView<Movie> genreDisplayMovieTable;

    @FXML
    private TableColumn<Movie, String> movieTitleCol;

    @FXML
    private TableColumn<Movie, Integer> movieYearCol;

    @FXML
    private TableColumn<Movie, Genre> movieGenreCol;

    @FXML
    private TableColumn<Movie, Integer> moviePriceCol;

    @FXML
    private ListView<Genre> genreListView;

    @FXML
    void displayGenreMovies(ActionEvent event) {
        genreDisplayMovieTable.setItems(AddMovieController.movies.getMoviesInGenre(genreListView.getSelectionModel().getSelectedItem()));
    }

    @FXML
    void quitApplication(ActionEvent event) {
        CatalogueController.openMoviesBYGenreStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieTitleCol.setCellValueFactory(new PropertyValueFactory<Movie, String>("title"));
        movieYearCol.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("year"));
        movieGenreCol.setCellValueFactory(new PropertyValueFactory<Movie, Genre>("genre"));
        moviePriceCol.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("price"));

        genreListView.setItems(AddMovieController.movies.getGenres());
    }
}
