package com.example.gundamstore.view;

import com.example.gundamstore.dao.CustomerDAO;
import com.example.gundamstore.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerPanel extends VBox {
    private static final Logger logger = LoggerFactory.getLogger(CustomerPanel.class);

    private TableView<Customer> table;
    private TextField nameField;
    private TextField emailField;
    private TextField phoneNumberField;
    private TextField addressField;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;

    private final CustomerDAO customerDAO;

    @Autowired
    public CustomerPanel(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;

        Text title = new Text("Customer Management");

        table = new TableView<>();
        TableColumn<Customer, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Customer, String> emailColumn = new TableColumn<>("Email");
        TableColumn<Customer, String> phoneNumberColumn = new TableColumn<>("Phone Number");
        TableColumn<Customer, String> addressColumn = new TableColumn<>("Address");

        List<TableColumn<Customer, ?>> columns = new ArrayList<>();
        columns.add(nameColumn);
        columns.add(emailColumn);
        columns.add(phoneNumberColumn);
        columns.add(addressColumn);

        table.getColumns().addAll(columns);
        nameField = new TextField();
        nameField.setPromptText("Name");
        emailField = new TextField();
        emailField.setPromptText("Email");
        phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Phone Number");
        addressField = new TextField();
        addressField.setPromptText("Address");
        addButton = new Button("Add");
        updateButton = new Button("Update");
        deleteButton = new Button("Delete");

        getChildren().addAll(title, table, nameField, emailField, phoneNumberField, addressField, addButton, updateButton, deleteButton);

        // Load data from the database
        loadData();
    }

    @PostConstruct
    private void loadData() {
        try {
            ObservableList<Customer> customerList = FXCollections.observableArrayList(customerDAO.findAll());
            table.setItems(customerList);
            if (customerList.isEmpty()) {
                logger.info("No data found.");
            } else {
                logger.info("Data loaded successfully.");
            }
        } catch (Exception e) {
            logger.error("Failed to load data.", e);
        }
    }
    public void init() {
        logger.info("CustomerPanel post construct initialization");
    }
}