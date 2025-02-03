package com.example.gundamstore.view;

import com.example.gundamstore.model.Gundam;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;

public class GundamPanel extends VBox {
    private TableView<Gundam> table;
    private TextField nameField;
    private TextField modelField;
    private TextField priceField;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;

    public GundamPanel() {
        Text title = new Text("Gundam Management");

        table = new TableView<>();
        TableColumn<Gundam, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Gundam, String> modelColumn = new TableColumn<>("Model");
        TableColumn<Gundam, Double> priceColumn = new TableColumn<>("Price");

        table.getColumns().addAll(nameColumn, modelColumn, priceColumn);

        nameField = new TextField();
        modelField = new TextField();
        priceField = new TextField();
        addButton = new Button("Add");
        updateButton = new Button("Update");
        deleteButton = new Button("Delete");

        getChildren().addAll(title, table, nameField, modelField, priceField, addButton, updateButton, deleteButton);
    }
}