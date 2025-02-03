package com.example.gundamstore.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainFrame extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gundam Store");

        BorderPane borderPane = new BorderPane();
        VBox vbox = new VBox();
        Button btnCustomers = new Button("Customers");
        Button btnGundams = new Button("Gundams");
        Button btnOrders = new Button("Orders");

        vbox.getChildren().addAll(btnCustomers, btnGundams, btnOrders);
        borderPane.setLeft(vbox);

        // Create instances of the panels
        CustomerPanel customerPanel = new CustomerPanel();
        GundamPanel gundamPanel = new GundamPanel();
        OrderPanel orderPanel = new OrderPanel();

        // Set actions for buttons
        btnCustomers.setOnAction(e -> borderPane.setCenter(customerPanel));
        btnGundams.setOnAction(e -> borderPane.setCenter(gundamPanel));
        btnOrders.setOnAction(e -> borderPane.setCenter(orderPanel));

        Scene scene = new Scene(borderPane, 720, 320);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}