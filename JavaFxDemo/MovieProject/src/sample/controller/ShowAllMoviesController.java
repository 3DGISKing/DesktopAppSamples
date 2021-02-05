package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.model.Genre;
import sample.model.Movie;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowAllMoviesController implements Initializable {

    @FXML
    private TableView<Movie> allMovieTable;

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
        KioskController.allMoviesStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieTitleCol.setCellValueFactory(new PropertyValueFactory<Movie, String>("title"));
        movieYearCol.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("year"));
        movieGenreCol.setCellValueFactory(new PropertyValueFactory<Movie, Genre>("genre"));
        moviePriceCol.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("price"));
        if(AddMovieController.movies.getAllMovies().size()>0){
            System.out.println("we have movies in my array");
            allMovieTable.setItems(AddMovieController.movies.getAllMovies());
        }else{
            System.out.println("No added Movies");
        }
    }
}
