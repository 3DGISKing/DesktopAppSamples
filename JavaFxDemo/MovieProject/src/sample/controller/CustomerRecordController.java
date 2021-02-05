
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

public class CustomerRecordController implements Initializable {
    @FXML
    private TableView<Movie> rentedMovieTable;

    @FXML
    private TableColumn<Movie, String> movieTitleColRent;

    @FXML
    private TableColumn<Movie, Integer> movieYearColRent;

    @FXML
    private TableColumn<Movie, Genre> movieGenreColRent;

    @FXML
    private TableColumn<Movie, Integer> moviePriceColRent;

    @FXML
    private TextField customerIDFeild;

    @FXML
    private Label customerIDLabel;

    @FXML
    private Label customerNameLabel;

    @FXML
    private Label customerBalanceLabel;

    @FXML
    private TableView<Movie> rentingHistoryTable;

    @FXML
    private TableColumn<Movie, String> movieTitleColHis;

    @FXML
    private TableColumn<Movie, Integer> movieYearColHis;

    @FXML
    private TableColumn<Movie, Genre> movieGenreColHis;

    @FXML
    private TableColumn<Movie, Integer> moviePriceColHis;
    @FXML
    private Label custRecordErrorMsgLabel;


    @FXML
    void quitApplication(ActionEvent event) {
        KioskController.custRecordStage.close();
    }

    @FXML
    void showRentedAndRentingHistoryMovies(ActionEvent event) {
        if (customerIDFeild.getText().matches("[0-9]+")) {
            if (AddMovieController.movies.getCustomer(Integer.valueOf(customerIDFeild.getText())) != null) {
                custRecordErrorMsgLabel.setText("");
                customerIDLabel.setText(String.valueOf(AddMovieController.movies.getCustomer(Integer.valueOf(customerIDFeild.getText())).getId()));
                customerNameLabel.setText(AddMovieController.movies.getCustomer(Integer.valueOf(customerIDFeild.getText())).getName());
                customerBalanceLabel.setText("$"+String.valueOf(AddMovieController.movies.getCustomer(Integer.valueOf(customerIDFeild.getText())).getBalance()));
                rentedMovieTable.setItems(AddMovieController.movies.getCustomer(Integer.valueOf(customerIDFeild.getText())).currentlyRented());
                rentingHistoryTable.setItems(AddMovieController.movies.getCustomer(Integer.valueOf(customerIDFeild.getText())).rentingHistory());
            } else {
                customerIDLabel.setText("");
                customerNameLabel.setText("");
                customerBalanceLabel.setText("");
                custRecordErrorMsgLabel.setText("Sorry this Customer does not exists!!!");
            }
        } else {
            customerIDLabel.setText("");
            customerNameLabel.setText("");
            customerBalanceLabel.setText("");
            custRecordErrorMsgLabel.setText("Customer Field cannot be empty!!!");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieTitleColRent.setCellValueFactory(new PropertyValueFactory<Movie, String>("title"));
        movieYearColRent.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("year"));
        movieGenreColRent.setCellValueFactory(new PropertyValueFactory<Movie, Genre>("genre"));
        moviePriceColRent.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("price"));

        movieTitleColHis.setCellValueFactory(new PropertyValueFactory<Movie, String>("title"));
        movieYearColHis.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("year"));
        movieGenreColHis.setCellValueFactory(new PropertyValueFactory<Movie, Genre>("genre"));
        moviePriceColHis.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("price"));
    }
}
