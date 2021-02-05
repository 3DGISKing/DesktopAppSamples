
package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.KioskApplication;
import sample.model.Genre;
import sample.model.Movie;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReturnMovieController implements Initializable {
    @FXML
    private TableView<Movie> RentedMovieTable;

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
    private Label returnMovieMsgLabel;

    @FXML
    void quitApplication(ActionEvent event) {
       CatalogueController.openReturnAMovieStage.close();
    }

    @FXML
    void rentSelectedMovies(ActionEvent event) {
        if (RentedMovieTable.getSelectionModel().getSelectedItem() != null) {
            AddMovieController.movies.returnMovieFromCustomer(RentedMovieTable.getSelectionModel().getSelectedItem(), AddMovieController.movies.getCustomer(Integer.valueOf(customerIDFeild.getText())));
            returnMovieMsgLabel.setText("Movie Returned");
        } else {
            returnMovieMsgLabel.setText("Please Select Movie first From above Table");
        }

    }

    @FXML
    void showRentedMoviesForRent(ActionEvent event) {
        if (customerIDFeild.getText().matches("[0-9]+")) {
            if (AddMovieController.movies.getCustomer(Integer.valueOf(customerIDFeild.getText())) != null) {
                RentedMovieTable.setItems(AddMovieController.movies.getCustomer(Integer.valueOf(customerIDFeild.getText())).currentlyRented());
                returnMovieMsgLabel.setText("");
            } else {
                returnMovieMsgLabel.setText("Sorry this customer does not exist");
            }
        } else {
            returnMovieMsgLabel.setText("Customer ID Field cannot be empty");
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
