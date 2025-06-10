package org.example.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.toyroom.ToyRoom;
import org.example.toyroom.models.toys.Toy;

import java.util.Collections;
import java.util.List;

public class ReadFileViewController implements ToyRoomAware {

    @FXML private TableView<Toy> toyTable;
    @FXML private TableColumn<Toy, Integer> idCol;
    @FXML private TableColumn<Toy, String> typeCol;
    @FXML private TableColumn<Toy, String> sizeCol;
    @FXML private TableColumn<Toy, String> colorCol;
    @FXML private TableColumn<Toy, String> materialCol;

    private final ObservableList<Toy> toys = FXCollections.observableArrayList();
    private ToyRoom toyRoom;
    private String filePath = "D:\\java_projects\\ToyRoom\\playRoomData.txt";

    @Override
    public void setToyRoom(ToyRoom toyRoom) {
        this.toyRoom = toyRoom;
        refreshTable();
    }

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        typeCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getType()));
        sizeCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getSize().toString()));
        colorCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getColor().getHexCode()));
        materialCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getMaterial()));

        toyTable.setItems(toys);
    }

    @FXML
    public void onReadFile() {
        try {
            toyRoom.importToysFromFile(filePath);
            refreshTable();
        } catch (Exception e) {
            showAlert("Error", "Failed to import toys:\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void refreshTable() {
        List<Toy> loadedToys = toyRoom.getToyService().findAll();
        Collections.reverse(loadedToys); // показуємо останні зверху
        toys.setAll(loadedToys);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
