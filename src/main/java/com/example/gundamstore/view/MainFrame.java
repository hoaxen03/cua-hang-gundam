package com.example.gundamstore.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.example.gundamstore.config.AppConfig;

public class MainFrame extends Application {


    @Override
    public void start(Stage primaryStage) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        primaryStage.setTitle("Gundam Store");

        BorderPane borderPane = new BorderPane();

        // Create and set the title
        Text title = new Text("Ứng dụng Quản lý Cửa hàng Gundam");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        BorderPane.setAlignment(title, Pos.CENTER);
        borderPane.setTop(title);

        VBox vbox = new VBox();
        Button btnCustomers = new Button("Quản lý Khách hàng");
        Button btnGundams = new Button("Quản lý sản phẩm");
        Button btnOrders = new Button("Quản lý đơn hàng ");

        // Set spacing, padding, and alignment for the VBox
        vbox.setSpacing(10); // Set spacing between buttons
        vbox.setPadding(new Insets(10)); // Set padding around the VBox
        vbox.setAlignment(Pos.TOP_LEFT); // Align buttons to the top left

        vbox.getChildren().addAll(btnCustomers, btnGundams, btnOrders);
        borderPane.setLeft(vbox);

        // Create instances of the panels using Spring's ApplicationContext
        CustomerPanel customerPanel = context.getBean(CustomerPanel.class);
        GundamPanel gundamPanel = context.getBean(GundamPanel.class);
        OrderPanel orderPanel = context.getBean(OrderPanel.class);

        // Set actions for buttons
        btnCustomers.setOnAction(e -> borderPane.setCenter(customerPanel));
        btnGundams.setOnAction(e -> borderPane.setCenter(gundamPanel));
        btnOrders.setOnAction(e -> borderPane.setCenter(orderPanel));

        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}