
package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.model.Genre;
import sample.model.Movie;

import java.net.URL;
import java.util.ResourceBundle;

public class FavouriteMoviesController implements Initializable {
    @FXML
    private TableView<Movie> favouriteMovieTable;

    @FXML
    private TableColumn<Movie, String> movieTitleCol;

    @FXML
    private TableColumn<Movie, Integer> movieYearCol;

    @FXML
    private TableColumn<Movie, Genre> movieGenreCol;

    @FXML
    private TableColumn<Movie, Integer> moviePriceCol;

    @FXML
    private TextField customerIDFeild;
    @FXML
    private Label favMovieMsgLabel;

    @FXML
    void quitApplication(ActionEvent event) {
        KioskController.favStage.close();
    }

    @FXML
    void showFavouriteMovies(ActionEvent event) {
        if (customerIDFeild.getText().matches("[0-9]+")) {
            if (AddMovieController.movies.getCustomer(Integer.valueOf(customerIDFeild.getText())) != null) {
                favouriteMovieTable.setItems(AddMovieController.movies.getCustomer(Integer.valueOf(customerIDFeild.getText())).favouriteMovies());
                favMovieMsgLabel.setText("");
            } else {
                favMovieMsgLabel.setText("This Customer Does not Exists");
            }
        } else {
            favMovieMsgLabel.setText("Customer ID field cannot be empty");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieTitleCol.setCellValueFactory(new PropertyValueFactory<Movie, String>("title"));
        movieYearCol.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("year"));
        movieGenreCol.setCellValueFactory(new PropertyValueFactory<Movie, Genre>("genre"));
        moviePriceCol.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("price"));
    }
}
