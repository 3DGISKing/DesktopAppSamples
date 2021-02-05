package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Kiosk {
    
    private Catalogue catalogue;
    private ObservableList<Customer> customers;
    
    public Kiosk() {
        
        this.catalogue = new Catalogue(this);
        this.customers = FXCollections.observableArrayList();
        
        //database of customers
        this.customers.add(new Customer(101, "Jaime", 10));
        this.customers.add(new Customer(102, "Luke", 10));
        this.customers.add(new Customer(103, "William", 1));     
        
    }
    
    public void addCustomer(int id, String name, int balance) {
        this.customers.add(new Customer(id, name, balance));
    }

        
    public void topUpAccount(Customer customer, int topUpAmount) {
        if (topUpAmount > 0 ) {
            customer.topUpAccount(topUpAmount);
        }       
    }

    public ObservableList<Customer> getCustomers() {
        return this.customers;
    }
 
    public Customer getCustomer(int id) {
        
        for (Customer p : this.customers) {
            if (p.getId() ==  id) return p;
        }
        
        return null;
    }

    public void removeCustomer(Customer customer) {
       this.customers.remove(customer); 
    }
    
    public Catalogue getCatalogue() {
        return this.catalogue;
    }

}
