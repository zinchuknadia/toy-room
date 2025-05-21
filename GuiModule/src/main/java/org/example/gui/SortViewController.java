package org.example.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.toyroom.ToyRoom;
import org.example.toyroom.models.Toy;

public class SortViewController implements ToyRoomAware {

    private ToyRoom toyRoom;
    private final ObservableList<Toy> toys = FXCollections.observableArrayList();

    @FXML private TableView<Toy> toyTable;
    @FXML private TableColumn<Toy, String> typeCol;
    @FXML private TableColumn<Toy, String> sizeCol;
    @FXML private TableColumn<Toy, String> colorCol;
    @FXML private TableColumn<Toy, String> materialCol;

    @FXML
    public void initialize() {
        typeCol.setCellValueFactory(cell -> cell.getValue().typeProperty());
        sizeCol.setCellValueFactory(cell -> cell.getValue().sizeProperty().asString());
        colorCol.setCellValueFactory(cell -> cell.getValue().colorProperty().asString());
        materialCol.setCellValueFactory(cell -> cell.getValue().materialProperty());

        toyTable.setItems(toys);
    }

    @Override
    public void setToyRoom(ToyRoom toyRoom) {
        this.toyRoom = toyRoom;
    }

    @FXML
    public void onSortByColor() {
        toys.setAll(toyRoom.getToyRepository().findAllSortedByColor());
    }

    @FXML
    public void onSortBySize() {
        toys.setAll(toyRoom.getToyRepository().findAllSortedBySize());
    }
}
