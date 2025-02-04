package com.example.gundamstore.view;

import com.example.gundamstore.model.Gundam;
import com.example.gundamstore.dao.GundamDAO;
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
import javax.annotation.PostConstruct;

@Component
public class GundamPanel extends VBox {
    private TableView<Gundam> table;
    private TextField nameField;
    private TextField modelField;
    private TextField priceField;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;

    private final GundamDAO gundamDAO;

    @Autowired
    public GundamPanel(GundamDAO gundamDAO) {
        this.gundamDAO = gundamDAO;

        Text title = new Text("Gundam Management");

        table = new TableView<>();
        TableColumn<Gundam, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Gundam, String> modelColumn = new TableColumn<>("Model");
        TableColumn<Gundam, Double> priceColumn = new TableColumn<>("Price");

        table.getColumns().addAll(nameColumn, modelColumn, priceColumn);

        nameField = new TextField();
        nameField.setPromptText("Name");
        modelField = new TextField();
        modelField.setPromptText("Model");
        priceField = new TextField();
        priceField.setPromptText("Price");
        addButton = new Button("Add");
        updateButton = new Button("Update");
        deleteButton = new Button("Delete");

        getChildren().addAll(title, table, nameField, modelField, priceField, addButton, updateButton, deleteButton);
    loadData();
    }
    @PostConstruct
    private void loadData() {
        ObservableList<Gundam> gundams = FXCollections.observableArrayList(gundamDAO.findAll());
        table.setItems(gundams);
    }
}