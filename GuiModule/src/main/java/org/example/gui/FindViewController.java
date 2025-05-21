package org.example.gui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.toyroom.models.Size;
import org.example.toyroom.models.Toy;
import org.example.toyroom.repository.ToyRepository;

public class FindViewController {

    @FXML
    private TextField typeField;
    @FXML
    private ComboBox<Size> sizeComboBox;
    @FXML
    private TableView<Toy> toyTable;
    @FXML
    private TableColumn<Toy, String> typeCol;
    @FXML
    private TableColumn<Toy, Size> sizeCol;
    @FXML
    private TableColumn<Toy, String> colorCol;
    @FXML
    private TableColumn<Toy, String> materialCol;

    private final ToyRepository toyRepository = new ToyRepository();

    @FXML
    public void initialize() {
        sizeComboBox.setItems(FXCollections.observableArrayList(Size.values()));

        typeCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getType()));

        sizeCol.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getSize()));

        colorCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getColor().getHexCode()));

        materialCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMaterial()));
    }


    @FXML
    private void onFindByType() {
        String type = typeField.getText().trim();
        if (!type.isEmpty()) {
            ObservableList<Toy> toys = FXCollections.observableArrayList(toyRepository.findByType(type));
            toyTable.setItems(toys);
        }
    }

    @FXML
    private void onFindBySize() {
        Size size = sizeComboBox.getValue();
        if (size != null) {
            ObservableList<Toy> toys = FXCollections.observableArrayList(toyRepository.findBySize(size));
            toyTable.setItems(toys);
        }
    }
}
