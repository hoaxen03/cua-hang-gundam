package com.example.gundamstore.view;

import com.example.gundamstore.dao.CustomerDAO;
import com.example.gundamstore.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        table = new TableView<>();
        TableColumn<Customer, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<Customer, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        TableColumn<Customer, String> phoneNumberColumn = new TableColumn<>("Phone Number");
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        
        TableColumn<Customer, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

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

        addButton.setOnAction(e -> addCustomer());
        updateButton.setOnAction(e -> updateCustomer());
        deleteButton.setOnAction(e -> deleteCustomer());

        HBox formBox = new HBox(10, nameField, emailField, phoneNumberField, addressField);
        formBox.setAlignment(Pos.CENTER);
        formBox.setPadding(new Insets(10));

        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        getChildren().addAll(title, table, formBox, buttonBox);

        setAlignment(Pos.CENTER);
        setPadding(new Insets(20));
        setSpacing(10);

        logger.info("CustomerPanel initialized");

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

    public void searchCustomers(String keyword) {
        try {
            ObservableList<Customer> customerList = FXCollections.observableArrayList(customerDAO.search(keyword));
            table.setItems(customerList);
            if (customerList.isEmpty()) {
                logger.info("No customers found for keyword: {}", keyword);
            } else {
                logger.info("Customers found for keyword: {}", keyword);
            }
        } catch (Exception e) {
            logger.error("Failed to search customers.", e);
        }
    }

    public TableView<Customer> getTable() {
        return table;
    }

    private void addCustomer() {
        try {
            String name = nameField.getText();
            String email = emailField.getText();
            String phoneNumber = phoneNumberField.getText();
            String address = addressField.getText();

            Customer customer = new Customer();
            customer.setName(name);
            customer.setEmail(email);
            customer.setPhoneNumber(phoneNumber);
            customer.setAddress(address);

            customerDAO.save(customer);
            loadData(); // Reload data to refresh the table
            clearFields();
            logger.info("Customer added successfully.");
        } catch (Exception e) {
            logger.error("Failed to add customer.", e);
        }
    }

    private void updateCustomer() {
        try {
            Customer selectedCustomer = table.getSelectionModel().getSelectedItem();
            if (selectedCustomer != null) {
                selectedCustomer.setName(nameField.getText());
                selectedCustomer.setEmail(emailField.getText());
                selectedCustomer.setPhoneNumber(phoneNumberField.getText());
                selectedCustomer.setAddress(addressField.getText());

                customerDAO.update(selectedCustomer);
                loadData(); // Reload data to refresh the table
                clearFields();
                logger.info("Customer updated successfully.");
            } else {
                logger.warn("No customer selected for update.");
            }
        } catch (Exception e) {
            logger.error("Failed to update customer.", e);
        }
    }

    private void deleteCustomer() {
        try {
            Customer selectedCustomer = table.getSelectionModel().getSelectedItem();
            if (selectedCustomer != null) {
                customerDAO.deleteById(Integer.parseInt(selectedCustomer.getId()));
                loadData(); // Reload data to refresh the table
                clearFields();
                logger.info("Customer deleted successfully.");
            } else {
                logger.warn("No customer selected for deletion.");
            }
        } catch (Exception e) {
            logger.error("Failed to delete customer.", e);
        }
    }

    private void clearFields() {
        nameField.clear();
        emailField.clear();
        phoneNumberField.clear();
        addressField.clear();
    }
}