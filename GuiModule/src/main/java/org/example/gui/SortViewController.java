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

    @FXML
    private ComboBox<String> sortComboBox;
    @FXML
    private TableView<Toy> toyTable;
    @FXML
    private TableColumn<Toy, String> typeCol;
    @FXML
    private TableColumn<Toy, String> sizeCol;
    @FXML
    private TableColumn<Toy, String> colorCol;
    @FXML
    private TableColumn<Toy, String> materialCol;

    @FXML
    public void initialize() {
        typeCol.setCellValueFactory(cell -> cell.getValue().typeProperty());
        sizeCol.setCellValueFactory(cell -> cell.getValue().sizeProperty().asString());
        colorCol.setCellValueFactory(cell -> cell.getValue().colorProperty().asString());
        materialCol.setCellValueFactory(cell -> cell.getValue().materialProperty());

        toyTable.setItems(toys);

        // Populate sort options
        sortComboBox.getItems().addAll("Color", "Size");
    }

    @Override
    public void setToyRoom(ToyRoom toyRoom) {
        this.toyRoom = toyRoom;
    }

    @FXML
    public void onSortChanged() {
        String choice = sortComboBox.getValue();
        if (choice == null) return;

        switch (choice) {
            case "Color" -> toys.setAll(toyRoom.getToyService().findAllSortedByColor());
            case "Size" -> toys.setAll(toyRoom.getToyService().findAllSortedBySize());
//            case "Type" -> toys.setAll(toyRoom.getToyService().findAllSortedByType());
            default -> toys.setAll(toyRoom.getToyService().findAll());
        }
    }
}