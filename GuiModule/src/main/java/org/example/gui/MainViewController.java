package org.example.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.toyroom.ToyRoom;
import org.example.toyroom.models.Toy;

public class MainViewController {

    private ToyRoom toyRoom;

    @FXML private TableView<Toy> toyTable;
    @FXML private TableColumn<Toy, String> typeCol;
    @FXML private TableColumn<Toy, String> sizeCol;
    @FXML private TableColumn<Toy, String> colorCol;
    @FXML private TableColumn<Toy, String> materialCol;

    private final ObservableList<Toy> toys = FXCollections.observableArrayList();

    public void setToyRoom(ToyRoom toyRoom) {
        this.toyRoom = toyRoom;
        refreshTable();
    }

    @FXML
    public void initialize() {
        typeCol.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        sizeCol.setCellValueFactory(cellData -> cellData.getValue().sizeProperty().asString());
        colorCol.setCellValueFactory(cellData -> cellData.getValue().colorProperty().asString());
        materialCol.setCellValueFactory(cellData -> cellData.getValue().materialProperty());

        toyTable.setItems(toys);
    }

    private void refreshTable() {
        toys.setAll(toyRoom.getToyRepository().findAll());
    }

    @FXML
    public void onAddToy() {
        toyRoom.add(); // у майбутньому — діалогове вікно
        refreshTable();
    }

    @FXML
    public void onReadFile() {
        toyRoom.readFile();
        refreshTable();
    }

    @FXML
    public void onFindToy() {
        toyRoom.find(); // у майбутньому — нове вікно або діалог
    }

    @FXML
    public void onSortToys() {
        toyRoom.sort();
        refreshTable();
    }

    @FXML
    public void onDeleteToy() {
        toyRoom.delete();
        refreshTable();
    }

    @FXML
    public void onExit() {
        toyRoom.exit();
        System.exit(0);
    }
}
