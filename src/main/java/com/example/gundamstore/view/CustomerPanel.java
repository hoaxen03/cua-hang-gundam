package com.example.gundamstore.view;

import com.example.gundamstore.model.Customer;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;

public class CustomerPanel extends VBox {
    private TableView<Customer> table;
    private TextField nameField;
    private TextField emailField;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;

    public CustomerPanel() {
        Text title = new Text("Customer Management");

        table = new TableView<>();
        TableColumn<Customer, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Customer, String> emailColumn = new TableColumn<>("Email");

        table.getColumns().addAll(nameColumn, emailColumn);

        nameField = new TextField();
        nameField.setPromptText("Name");
        emailField = new TextField();
        emailField.setPromptText("Email");
        addButton = new Button("Add");
        updateButton = new Button("Update");
        deleteButton = new Button("Delete");

        getChildren().addAll(title, table, nameField, emailField, addButton, updateButton, deleteButton);
    }
}