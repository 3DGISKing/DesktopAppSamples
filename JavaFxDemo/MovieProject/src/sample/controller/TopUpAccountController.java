package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.KioskApplication;

import java.io.IOException;

public class TopUpAccountController {
    @FXML
    private TextField customerIDFeild;

    @FXML
    private TextField transactionAMountFeild;

    @FXML
    private Label transcationLabel;

    @FXML
    void quitApplication(ActionEvent event) {
        KioskController.topUpStage.close();
    }

    @FXML
    void topUpAmountAction(ActionEvent event) {

        if (customerIDFeild.getText().matches("[0-9]+")) {
            if (AddMovieController.movies.getCustomer(Integer.valueOf(customerIDFeild.getText())) != null) {
                if (transactionAMountFeild.getText().matches("[0-9]+")) {
                    transcationLabel.setText("Transaction Complete");
                    AddMovieController.movies.getCustomer(Integer.valueOf(customerIDFeild.getText())).topUpAccount(Integer.valueOf(transactionAMountFeild.getText()));
                } else {
                    transcationLabel.setText("Please enter Digits in Balance Feild");
                }
            } else {
                transcationLabel.setText("This Customer Does not exists");
            }
        } else {
            transcationLabel.setText("Sorry Customer Field cannot be empty");
        }
    }
}
