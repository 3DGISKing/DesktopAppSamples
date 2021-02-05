package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.model.Genre;
import sample.model.Movie;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowMoviesByYearController implements Initializable {
    @FXML
    private TableView<Movie> yearDisplayMovieTable;

    @FXML
    private TableColumn<Movie, String> movieTitleCol;

    @FXML
    private TableColumn<Movie, Integer> movieYearCol;

    @FXML
    private TableColumn<Movie, Genre> movieGenreCol;

    @FXML
    private TableColumn<Movie, Integer> moviePriceCol;

    @FXML
    private ListView<Integer> yearListView;

    @FXML
    void displayYearMovies(ActionEvent event) {
        yearDisplayMovieTable.setItems(AddMovieController.movies.getMoviesByYear(yearListView.getSelectionModel().getSelectedItem()));
    }

    @FXML
    void quitApplication(ActionEvent event) {
        CatalogueController.openMoviesByYearStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieTitleCol.setCellValueFactory(new PropertyValueFactory<Movie, String>("title"));
        movieYearCol.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("year"));
        movieGenreCol.setCellValueFactory(new PropertyValueFactory<Movie, Genre>("genre"));
        moviePriceCol.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("price"));
        yearListView.setItems(AddMovieController.movies.getYears());
    }
}
