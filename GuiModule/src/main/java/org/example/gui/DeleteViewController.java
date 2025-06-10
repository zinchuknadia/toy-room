package org.example.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.toyroom.ToyRoom;
import org.example.toyroom.models.toys.Toy;

public class DeleteViewController implements ToyRoomAware {

    @FXML private TextField idField;
    @FXML private TableView<Toy> toyTable;
    @FXML private TableColumn<Toy, Integer> idCol;
    @FXML private TableColumn<Toy, String> typeCol;
    @FXML private TableColumn<Toy, String> sizeCol;
    @FXML private TableColumn<Toy, String> colorCol;
    @FXML private TableColumn<Toy, String> materialCol;
    @FXML private TableColumn<Toy, String> priceCol;

    private final ObservableList<Toy> toys = FXCollections.observableArrayList();
    private ToyRoom toyRoom;

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
        priceCol.setCellValueFactory(data -> {
            double price = data.getValue().getPrice();
            return new javafx.beans.property.SimpleStringProperty(String.format("%.2f", price));
        });

        toyTable.setItems(toys);

        // Select toy from table and fill the ID field
        toyTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idField.setText(String.valueOf(newSelection.getId()));
            }
        });
    }

    @FXML
    public void onDelete() {
        String text = idField.getText();
        try {
            int id = Integer.parseInt(text);
            boolean success = toyRoom.getToyService().deleteById(id);
            if (success) {
                showAlert("Success", "Toy deleted successfully.", Alert.AlertType.INFORMATION);
                refreshTable();
            } else {
                showAlert("Error", "Toy with ID " + id + " not found.", Alert.AlertType.WARNING);
            }
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter a valid numeric ID.", Alert.AlertType.ERROR);
        }
    }

    private void refreshTable() {
        toys.setAll(toyRoom.getToyService().findAll());
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
