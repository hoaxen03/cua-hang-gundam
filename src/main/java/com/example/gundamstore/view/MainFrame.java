package com.example.gundamstore.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.example.gundamstore.config.AppConfig;

public class MainFrame extends Application {

    private static final String SEARCH_RESULTS_TITLE = "Search Results";

    @Override
    public void start(Stage primaryStage) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        BorderPane borderPane = new BorderPane();

        CustomerPanel customerPanel = context.getBean(CustomerPanel.class);
        GundamPanel gundamPanel = context.getBean(GundamPanel.class);
        OrderPanel orderPanel = context.getBean(OrderPanel.class);

        // Create buttons for navigation
        Button btnCustomers = new Button("Customers");
        Button btnGundams = new Button("Products");
        Button btnOrders = new Button("Orders");

        // Create search fields and buttons
        TextField searchProductField = new TextField();
        searchProductField.setPromptText("Search products...");
        Button btnSearchProduct = new Button("Search Product");

        TextField searchCustomerField = new TextField();
        searchCustomerField.setPromptText("Search customers...");
        Button btnSearchCustomer = new Button("Search Customer");

        TextField searchOrderField = new TextField();
        searchOrderField.setPromptText("Search orders...");
        Button btnSearchOrder = new Button("Search Order");

        // Set actions for buttons
        btnCustomers.setOnAction(e -> borderPane.setCenter(customerPanel));
        btnGundams.setOnAction(e -> borderPane.setCenter(gundamPanel));
        btnOrders.setOnAction(e -> borderPane.setCenter(orderPanel));
        btnSearchProduct.setOnAction(e -> showSearchResults(searchProductField.getText(), context, "product"));
        btnSearchCustomer.setOnAction(e -> showSearchResults(searchCustomerField.getText(), context, "customer"));
        btnSearchOrder.setOnAction(e -> showSearchResults(searchOrderField.getText(), context, "order"));

        // Create top panel with title and navigation buttons
        Label titleLabel = new Label("Gunpla Store Management");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        HBox titleBox = new HBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(10));

        HBox navBox = new HBox(10, btnCustomers, btnGundams, btnOrders);
        navBox.setAlignment(Pos.CENTER);
        navBox.setPadding(new Insets(10));

        HBox searchBox = new HBox(10, searchProductField, btnSearchProduct, searchCustomerField, btnSearchCustomer, searchOrderField, btnSearchOrder);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.setPadding(new Insets(10));

        VBox topContainer = new VBox(titleBox, navBox, searchBox);
        topContainer.setAlignment(Pos.TOP_CENTER);
        topContainer.setPadding(new Insets(10));

        borderPane.setTop(topContainer);

        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gundam Store Management");
        primaryStage.show();
    }

    private void showSearchResults(String keyword, ApplicationContext context, String type) {
        Stage searchStage = new Stage();
        VBox searchResultsPanel = new VBox();
        searchResultsPanel.setPadding(new Insets(10));
        searchResultsPanel.setSpacing(10);

        Label searchResultsTitle = new Label(SEARCH_RESULTS_TITLE);
        searchResultsTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        searchResultsPanel.getChildren().add(searchResultsTitle);

        if (type.equals("product")) {
            GundamPanel gundamPanel = context.getBean(GundamPanel.class);
            gundamPanel.searchProducts(keyword);
            if (gundamPanel.getTable().getItems().isEmpty()) {
                showAlert("No products found for keyword: " + keyword);
            } else {
                searchResultsPanel.getChildren().add(gundamPanel);
                Scene searchScene = new Scene(searchResultsPanel, 800, 600);
                searchStage.setScene(searchScene);
                searchStage.setTitle(SEARCH_RESULTS_TITLE);
                searchStage.show();
            }
        } else if (type.equals("customer")) {
            CustomerPanel customerPanel = context.getBean(CustomerPanel.class);
            customerPanel.searchCustomers(keyword);
            if (customerPanel.getTable().getItems().isEmpty()) {
                showAlert("No customers found for keyword: " + keyword);
            } else {
                searchResultsPanel.getChildren().add(customerPanel);
                Scene searchScene = new Scene(searchResultsPanel, 800, 600);
                searchStage.setScene(searchScene);
                searchStage.setTitle(SEARCH_RESULTS_TITLE);
                searchStage.show();
            }
        } else if (type.equals("order")) {
            OrderPanel orderPanel = context.getBean(OrderPanel.class);
            orderPanel.searchOrders(keyword);
            if (orderPanel.getTable().getItems().isEmpty()) {
                showAlert("No orders found for keyword: " + keyword);
            } else {
                searchResultsPanel.getChildren().add(orderPanel);
                Scene searchScene = new Scene(searchResultsPanel, 1200 , 600);
                searchStage.setScene(searchScene);
                searchStage.setTitle(SEARCH_RESULTS_TITLE);
                searchStage.show();
            }
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}