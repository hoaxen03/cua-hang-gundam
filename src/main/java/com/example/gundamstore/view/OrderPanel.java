package com.example.gundamstore.view;

import com.example.gundamstore.model.Order;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;

public class OrderPanel extends VBox {
    private TableView<Order> table;
    private TextField customerIdField;
    private TextField orderDateField;
    private TextField totalAmountField;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;

    public OrderPanel() {
        Text title = new Text("Order Management");

        table = new TableView<>();
        TableColumn<Order, String> customerIdColumn = new TableColumn<>("Customer ID");
        TableColumn<Order, String> orderDateColumn = new TableColumn<>("Order Date");
        TableColumn<Order, Double> totalAmountColumn = new TableColumn<>("Total Amount");

        table.getColumns().addAll(customerIdColumn, orderDateColumn, totalAmountColumn);

        customerIdField = new TextField();
        customerIdField.setPromptText("Customer ID");
        orderDateField = new TextField();
        orderDateField.setPromptText("Order Date");
        totalAmountField = new TextField();
        totalAmountField.setPromptText("Total Amount");
        addButton = new Button("Add");
        updateButton = new Button("Update");
        deleteButton = new Button("Delete");

        getChildren().addAll(title, table, customerIdField, orderDateField, totalAmountField, addButton, updateButton, deleteButton);
    }
}