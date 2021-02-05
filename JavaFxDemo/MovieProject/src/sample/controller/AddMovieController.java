
package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.model.Catalogue;

import java.net.URL;
import java.util.ResourceBundle;

public class AddMovieController implements Initializable {
    @FXML
    private TextField movieTitleFeidl;

    @FXML
    private TextField movieYearFeild;

    @FXML
    private TextField movieGenreFeild;

    @FXML
    private TextField moviePriceFeild;

    @FXML
    private Label addMovieMsgLabel;
    public static Catalogue movies = new Catalogue(AddCustomerController.customers);

    @FXML
    void addMovie(ActionEvent event) {

        if (movieTitleFeidl.getText().matches("[A-Za-z]+") && movieYearFeild.getText().matches("[0-9]+") && moviePriceFeild.getText().matches("[0-9]+") && movieGenreFeild.getText().matches("[A-Za-z]+")) {
            movies.addMovie(movieTitleFeidl.getText(), Integer.valueOf(movieYearFeild.getText()), movieGenreFeild.getText(), Integer.valueOf(moviePriceFeild.getText()));
            addMovieMsgLabel.setText("Movie Added to Catalogue");
        } else {
            addMovieMsgLabel.setText("Error!!! Please Enter Valid Data");
        }

    }

    @FXML
    void quitApplication(ActionEvent event) {
        AdminController.addMovieStage.close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
