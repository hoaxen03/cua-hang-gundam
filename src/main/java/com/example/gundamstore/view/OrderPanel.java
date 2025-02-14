package com.example.gundamstore.view;

import com.example.gundamstore.dao.OrderDao;
import com.example.gundamstore.model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class OrderPanel extends VBox {
    private static final Logger logger = LoggerFactory.getLogger(OrderPanel.class);

    private TableView<Order> table;
    private TextField customerIdField;
    private TextField orderDateField;
    private TextField totalAmountField;
    private TextField customerField;
    private TextField gundamsField;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;
    private Button searchButton;
    private TextField searchField;

    private final OrderDao orderDAO;

    @Autowired
    public OrderPanel(OrderDao orderDAO) {
        this.orderDAO = orderDAO;

        Text title = new Text("Order Management");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        table = new TableView<>();
        TableColumn<Order, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Order, Integer> customerIdColumn = new TableColumn<>("Customer ID");
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        
        TableColumn<Order, String> orderDateColumn = new TableColumn<>("Order Date");
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        
        TableColumn<Order, Double> totalAmountColumn = new TableColumn<>("Total Amount");
        totalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));

        TableColumn<Order, String> customerColumn = new TableColumn<>("Customer");
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));

        TableColumn<Order, String> gundamsColumn = new TableColumn<>("Gundams");
        gundamsColumn.setCellValueFactory(new PropertyValueFactory<>("gundams"));

        List<TableColumn<Order, ?>> columns = new ArrayList<>();
        columns.add(idColumn);
        columns.add(customerIdColumn);
        columns.add(orderDateColumn);
        columns.add(totalAmountColumn);
        columns.add(customerColumn);
        columns.add(gundamsColumn);

        table.getColumns().addAll(columns);

        customerIdField = new TextField();
        customerIdField.setPromptText("Customer ID");
        orderDateField = new TextField();
        orderDateField.setPromptText("Order Date");
        totalAmountField = new TextField();
        totalAmountField.setPromptText("Total Amount");
        customerField = new TextField();
        customerField.setPromptText("Customer");
        gundamsField = new TextField();
        gundamsField.setPromptText("Gundams");

        addButton = new Button("Add");
        updateButton = new Button("Update");
        deleteButton = new Button("Delete");
        searchButton = new Button("Search");
        searchField = new TextField();
        searchField.setPromptText("Search orders...");

        addButton.setOnAction(e -> addOrder());
        updateButton.setOnAction(e -> updateOrder());
        deleteButton.setOnAction(e -> deleteOrder());
        searchButton.setOnAction(e -> searchOrders(searchField.getText()));

        HBox formBox = new HBox(10, customerIdField, orderDateField, totalAmountField, customerField, gundamsField);
        formBox.setAlignment(Pos.CENTER);
        formBox.setPadding(new Insets(10));

        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton, searchField, searchButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        getChildren().addAll(title, table, customerIdField, orderDateField, totalAmountField, customerField, gundamsField, addButton, updateButton, deleteButton);

        setAlignment(Pos.CENTER);
        setPadding(new Insets(20));
        setSpacing(10);

        logger.info("OrderPanel initialized");

        // Load data from the database
        loadData();
    }

    @PostConstruct
    private void loadData() {
        try {
            ObservableList<Order> orderList = FXCollections.observableArrayList(orderDAO.findAll());
            table.setItems(orderList);
            if (orderList.isEmpty()) {
                logger.info("No data found.");
            } else {
                logger.info("Data loaded successfully.");
            }
        } catch (Exception e) {
            logger.error("Failed to load data.", e);
        }
    }

    public void searchOrders(String keyword) {
        try {
            ObservableList<Order> orderList = FXCollections.observableArrayList(orderDAO.search(keyword));
            table.setItems(orderList);
            if (orderList.isEmpty()) {
                logger.info("No orders found for keyword: {}", keyword);
            } else {
                logger.info("Orders found for keyword: {}", keyword);
            }
        } catch (Exception e) {
            logger.error("Failed to search orders.", e);
        }
    }

    public TableView<Order> getTable() {
        return table;
    }

    private void addOrder() {
        try {
            int customerId = Integer.parseInt(customerIdField.getText());
            String orderDate = orderDateField.getText();
            double totalAmount = Double.parseDouble(totalAmountField.getText());
            String customer = customerField.getText();
            String gundams = gundamsField.getText();

            Order order = new Order();
            order.setCustomerId(customerId);
            order.setOrderDate(java.sql.Date.valueOf(orderDate));
            order.setTotalAmount(totalAmount);
            order.setCustomer(customer);
            order.setGundams(gundams);

            orderDAO.save(order);
            loadData(); // Reload data to refresh the table
            clearFields();
            logger.info("Order added successfully.");
        } catch (Exception e) {
            logger.error("Failed to add order.", e);
        }
    }

    private void updateOrder() {
        try {
            Order selectedOrder = table.getSelectionModel().getSelectedItem();
            if (selectedOrder != null) {
                selectedOrder.setCustomerId(Integer.parseInt(customerIdField.getText()));
                selectedOrder.setOrderDate(java.sql.Date.valueOf(orderDateField.getText()));
                selectedOrder.setTotalAmount(Double.parseDouble(totalAmountField.getText()));
                selectedOrder.setCustomer(customerField.getText());
                selectedOrder.setGundams(gundamsField.getText());

                orderDAO.update(selectedOrder);
                loadData(); // Reload data to refresh the table
                clearFields();
                logger.info("Order updated successfully.");
            } else {
                logger.warn("No order selected for update.");
            }
        } catch (Exception e) {
            logger.error("Failed to update order.", e);
        }
    }

    private void deleteOrder() {
        try {
            Order selectedOrder = table.getSelectionModel().getSelectedItem();
            if (selectedOrder != null) {
                orderDAO.deleteById(selectedOrder.getId());
                loadData(); // Reload data to refresh the table
                clearFields();
                logger.info("Order deleted successfully.");
            } else {
                logger.warn("No order selected for deletion.");
            }
        } catch (Exception e) {
            logger.error("Failed to delete order.", e);
        }
    }

    private void clearFields() {
        customerIdField.clear();
        orderDateField.clear();
        totalAmountField.clear();
        customerField.clear();
        gundamsField.clear();
    }
}