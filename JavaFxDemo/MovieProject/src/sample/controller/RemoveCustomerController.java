package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.KioskApplication;
import sample.model.Customer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RemoveCustomerController implements Initializable {
    @FXML
    private TableView<Customer> removeCustomerTable;
    @FXML
    private TableColumn<Customer, Integer> customerIDCol;
    @FXML
    private TableColumn<Customer, String> customerNameCol;
    @FXML
    private TableColumn<Customer, Integer> customerBalanceCol;

    @FXML
    void quitApplication(ActionEvent event) {
        AdminController.removeCustomerStage.close();
    }

    @FXML
    void removeCustomerFromTable(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Alert");
        if(removeCustomerTable.getSelectionModel().getSelectedItem() != null){
            alert.setContentText(removeCustomerTable.getSelectionModel().getSelectedItem().getName() + " movie Removed Successfully!!! \n Please Refresh Your Page");
            AddCustomerController.customers.removeCustomer(removeCustomerTable.getSelectionModel().getSelectedItem());
            alert.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerIDCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("Id"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        customerBalanceCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("balance"));
        if (AddCustomerController.customers.getCustomers().size() > 0) {
            System.out.println("we have Customers in my array");
            removeCustomerTable.setItems(AddCustomerController.customers.getCustomers());
        } else {
            System.out.println("No added Customers");
        }
    }
}
