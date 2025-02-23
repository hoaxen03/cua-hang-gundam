package com.example.gundamstore.view;

import com.example.gundamstore.dao.GundamDAO;
import com.example.gundamstore.model.Gundam;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableCell;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class GundamPanel extends VBox {
    private static final Logger logger = LoggerFactory.getLogger(GundamPanel.class);

    private TableView<Gundam> table;
    private TextField nameField;
    private TextField modelField;
    private TextField priceField;
    private TextField seriesField;
    private TextField stockField;
    private TextField imageUrlField;
    private TextField descriptionField;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;

    private final GundamDAO gundamDAO;

    @Autowired
    public GundamPanel(GundamDAO gundamDAO) {
        this.gundamDAO = gundamDAO;

        Text title = new Text("Gundam Management");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        table = new TableView<>();

        TableColumn<Gundam, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Gundam, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<Gundam, String> modelColumn = new TableColumn<>("Model");
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        
        TableColumn<Gundam, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Gundam, String> seriesColumn = new TableColumn<>("Series");
        seriesColumn.setCellValueFactory(new PropertyValueFactory<>("series"));

        TableColumn<Gundam, Integer> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<Gundam, String> imageColumn = new TableColumn<>("Image");
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("imageUrl"));

        TableColumn<Gundam, String> descriptionColumn = new TableColumn<>("Description"); // Thêm cột mô tả
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setCellFactory(new Callback<TableColumn<Gundam, String>, TableCell<Gundam, String>>() {
            @Override
            public TableCell<Gundam, String> call(TableColumn<Gundam, String> param) {
                return new TableCell<Gundam, String>() {
                    private final Text text = new Text();

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            text.setText(item);
                            text.setWrappingWidth(descriptionColumn.getWidth() - -100); // Thiết lập độ rộng để xuống dòng
                            setGraphic(text);
                        }
                    }
                };
            }
        });
        
        imageColumn.setCellFactory(new Callback<TableColumn<Gundam, String>, TableCell<Gundam, String>>() {            
            @Override
            public TableCell<Gundam, String> call(TableColumn<Gundam, String> param) {
                return new TableCell<Gundam, String>() {
                    private final ImageView imageView = new ImageView();

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            try {
                                // Sử dụng đường dẫn tương đối để tải ảnh
                                String imagePath = "/images/" + item;
                                logger.info("Loading image from path: " + imagePath);
                                Image image = new Image(getClass().getResourceAsStream(imagePath));
                                if (image.isError()) {
                                    throw new Exception("Image not found: " + imagePath);
                                }
                                imageView.setImage(image);
                                imageView.setFitHeight(50);
                                imageView.setFitWidth(50);
                                imageView.setPreserveRatio(true); // Giữ tỷ lệ ảnh
                                setGraphic(imageView);
                            } catch (Exception e) {
                                logger.error("Failed to load image: " + item, e);
                                setGraphic(null);
                            }
                        }
                    }
                };
            }
        });

        List<TableColumn<Gundam, ?>> columns = new ArrayList<>();
        columns.add(idColumn);
        columns.add(nameColumn);
        columns.add(modelColumn);
        columns.add(priceColumn);
        columns.add(seriesColumn);
        columns.add(stockColumn);
        columns.add(imageColumn);
        columns.add(descriptionColumn);

        table.getColumns().addAll(columns);

        nameField = new TextField();
        nameField.setPromptText("Name");
        modelField = new TextField();
        modelField.setPromptText("Model");
        priceField = new TextField();
        priceField.setPromptText("Price");
        seriesField = new TextField();
        seriesField.setPromptText("Series");
        stockField = new TextField();
        stockField.setPromptText("Stock");
        imageUrlField = new TextField();
        imageUrlField.setPromptText("Image URL");
        descriptionField = new TextField(); // Thêm trường mô tả
        descriptionField.setPromptText("Description"); // Đặt mô tả

        addButton = new Button("Add");
        updateButton = new Button("Update");
        deleteButton = new Button("Delete");

        addButton.setOnAction(e -> addGundam());
        updateButton.setOnAction(e -> updateGundam());
        deleteButton.setOnAction(e -> deleteGundam());

        HBox formBox = new HBox(10, nameField, modelField, priceField, seriesField, stockField, imageUrlField,descriptionField);
        formBox.setAlignment(Pos.CENTER);
        formBox.setPadding(new Insets(10));

        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        getChildren().addAll(title, table, formBox, buttonBox);

        setAlignment(Pos.CENTER);
        setPadding(new Insets(20));
        setSpacing(10);

        logger.info("GundamPanel initialized");

        // Load data from the database
        loadData();
        checkImagesDirectory();
    }

    @PostConstruct
    private void checkImagesDirectory() {
        File imagesDir = new File(getClass().getResource("/images").getFile());
        if (!imagesDir.exists() || !imagesDir.isDirectory()) {
            logger.error("Images directory does not exist: " + imagesDir.getAbsolutePath());
        } else {
            logger.info("Images directory found: " + imagesDir.getAbsolutePath());
        }
    }
    private void loadData() {
        try {
            ObservableList<Gundam> gundamList = FXCollections.observableArrayList(gundamDAO.findAll());
            table.setItems(gundamList);
            if (gundamList.isEmpty()) {
                logger.info("No data found.");
            } else {
                logger.info("Data loaded successfully.");
            }
        } catch (Exception e) {
            logger.error("Failed to load data.", e);
        }
    }

    public void searchProducts(String keyword) {
        try {
            ObservableList<Gundam> gundamList = FXCollections.observableArrayList(gundamDAO.search(keyword));
            table.setItems(gundamList);
            if (gundamList.isEmpty()) {
                logger.info("No products found for keyword: {}", keyword);
            } else {
                logger.info("Products found for keyword: {}", keyword);
            }
        } catch (Exception e) {
            logger.error("Failed to search products.", e);
        }
    }

    public TableView<Gundam> getTable() {
        return table;
    }

    private void addGundam() {
        try {
            String name = nameField.getText();
            String model = modelField.getText();
            Double price = Double.parseDouble(priceField.getText());
            String series = seriesField.getText();
            int stock = Integer.parseInt(stockField.getText());
            String imageUrl = imageUrlField.getText(); // Lấy URL ảnh
            String description = descriptionField.getText(); // Lấy mô tả

            Gundam gundam = new Gundam();
            gundam.setName(name);
            gundam.setModel(model);
            gundam.setPrice(price);
            gundam.setSeries(series);
            gundam.setStock(stock);
            gundam.setImageUrl(imageUrl); // Đặt URL ảnh
            gundam.setDescription(description); // Đặt mô tả

            gundamDAO.save(gundam);
            loadData(); // Reload data to refresh the table
            clearFields();
            logger.info("Gundam added successfully.");
        } catch (Exception e) {
            logger.error("Failed to add gundam.", e);
        }
    }

    private void updateGundam() {
        try {
            Gundam selectedGundam = table.getSelectionModel().getSelectedItem();
            if (selectedGundam != null) {
                selectedGundam.setName(nameField.getText());
                selectedGundam.setModel(modelField.getText());
                selectedGundam.setPrice(Double.parseDouble(priceField.getText()));
                selectedGundam.setSeries(seriesField.getText());
                selectedGundam.setStock(Integer.parseInt(stockField.getText()));
                selectedGundam.setImageUrl(imageUrlField.getText());
                selectedGundam.setDescription(descriptionField.getText()); // Thiết lập giá trị mô tả

                gundamDAO.update(selectedGundam);
                loadData(); // Reload data to refresh the table
                clearFields();
                logger.info("Gundam updated successfully.");
            } else {
                logger.warn("No gundam selected for update.");
            }
        } catch (Exception e) {
            logger.error("Failed to update gundam.", e);
        }
    }

    private void deleteGundam() {
        try {
            Gundam selectedGundam = table.getSelectionModel().getSelectedItem();
            if (selectedGundam != null) {
                gundamDAO.deleteById(Integer.parseInt(selectedGundam.getId()));
                loadData(); // Reload data to refresh the table
                clearFields();
                logger.info("Gundam deleted successfully.");
            } else {
                logger.warn("No gundam selected for deletion.");
            }
        } catch (Exception e) {
            logger.error("Failed to delete gundam.", e);
        }
    }

    private void clearFields() {
        nameField.clear();
        modelField.clear();
        priceField.clear();
        seriesField.clear();
        stockField.clear();
        imageUrlField.clear(); // Xóa URL ảnh
        descriptionField.clear(); // Xóa giá trị mô tả
    }
}