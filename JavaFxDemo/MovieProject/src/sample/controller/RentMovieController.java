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

public class RentMovieController implements Initializable {
    @FXML
    private TableView<Movie> AvailableMovieTable;

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
    private Label rentMovieMsgLabel;

    @FXML
    void quitApplication(ActionEvent event) {
        CatalogueController.openRentAMovieStage.close();
    }

    @FXML
    void rentSelectedMovies(ActionEvent event) {
        if (AvailableMovieTable.getSelectionModel().getSelectedItem() != null) {
            AddMovieController.movies.rentMovieToCustomer(AvailableMovieTable.getSelectionModel().getSelectedItem(), AddMovieController.movies.getCustomer(Integer.valueOf(customerIDFeild.getText())));
            rentMovieMsgLabel.setText("Movie Rented");
        } else {
            rentMovieMsgLabel.setText("Please Select Movie First from above table!!!");
        }
    }

    @FXML
    void showAvailableMoviesForRent(ActionEvent event) {

        if (customerIDFeild.getText().matches("[0-9]+")) {
            if (AddMovieController.movies.getCustomer(Integer.valueOf(customerIDFeild.getText())) != null) {
                AvailableMovieTable.setItems(AddMovieController.movies.getAvailableMovies());
                rentMovieMsgLabel.setText("");
            } else {
                rentMovieMsgLabel.setText("Sorry this customer doest not exists!!!");
            }
        } else {
            rentMovieMsgLabel.setText("Sorry Customer Field cannot be empty!!!");
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
