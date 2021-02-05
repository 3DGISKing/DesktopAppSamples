package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.model.Kiosk;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {
    @FXML
    private TextField customerIDFeild;

    @FXML
    private TextField customerNameFeild;

    @FXML
    private TextField customerBalanceFeild;
    @FXML
    private Label addCustMsgLabel;

    public static Kiosk customers = new Kiosk();

    @FXML
    void addCustomer(ActionEvent event) {
        if (customerIDFeild.getText().matches("[0-9]+") && customerNameFeild.getText().matches("[A-za-z]+") && customerBalanceFeild.getText().matches("[0-9]+")) {
            customers.addCustomer(Integer.valueOf(customerIDFeild.getText()), customerNameFeild.getText(), Integer.valueOf(customerBalanceFeild.getText()));
            addCustMsgLabel.setText("Customer added to kiosk");
        } else {
            addCustMsgLabel.setText("Please Enter Valid Data in above Fields !!! Error");
        }

    }

    @FXML
    void quitApplication(ActionEvent event) {
        AdminController.addnewCustomerStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
