package org.example.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.toyroom.ToyRoom;
import org.example.toyroom.models.Color;
import org.example.toyroom.models.Size;
import org.example.toyroom.models.Toy;

public class AddToyController implements ToyRoomAware {

    private ToyRoom toyRoom;

    @FXML private TextField typeField;
    @FXML private TextField sizeField;
    @FXML private TextField colorField;
    @FXML private TextField materialField;

    @Override
    public void setToyRoom(ToyRoom toyRoom) {
        this.toyRoom = toyRoom;
    }

    @FXML
    public void handleAddToy() {
        String type = typeField.getText();
        String sizeStr = sizeField.getText();
        String colorStr = colorField.getText();
        String material = materialField.getText();

        if (type.isEmpty() || sizeStr.isEmpty() || colorStr.isEmpty() || material.isEmpty()) {
            showAlert("Please fill in all fields.");
            return;
        }

        try {
            Size size = parseSize(sizeStr);
            Color color = new Color(colorStr.toLowerCase());
            Toy toy = new Toy(type, size, color, material);
            toyRoom.getToyRepository().add(toy);
            showAlert("Toy added successfully!");
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Size must be an integer.");
        }
    }

    private static Size parseSize(String sizeStr){
        switch(sizeStr.toLowerCase()){
            case "large": return Size.LARGE;
            case "medium": return Size.MEDIUM;
            case "small": return Size.SMALL;
            default: throw new IllegalArgumentException("Invalid Size: " + sizeStr);
        }
    }

    private void clearFields() {
        typeField.clear();
        sizeField.clear();
        colorField.clear();
        materialField.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Toy");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
