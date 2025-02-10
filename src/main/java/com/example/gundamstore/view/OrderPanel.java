package com.example.gundamstore.view;

import com.example.gundamstore.dao.OrderDao;
import com.example.gundamstore.model.Order;
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
public class OrderPanel extends VBox {
    private TableView<Order> table;
    private TextField customerIdField;
    private TextField orderDateField;
    private TextField totalAmountField;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;

    private final OrderDao orderDAO;

    @Autowired
    public OrderPanel(OrderDao orderDAO) {
        this.orderDAO = orderDAO;

        Text title = new Text("Order Management");

        table = new TableView<>();
        TableColumn<Order, Integer> customerIdColumn = new TableColumn<>("Customer ID");
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

        // Load data from the database
        loadData();
    }

    @PostConstruct
    private void loadData() {
        try {
            ObservableList<Order> orderList = FXCollections.observableArrayList(orderDAO.findAll());
            table.setItems(orderList);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }
}