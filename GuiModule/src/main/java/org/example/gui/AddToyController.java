package org.example.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.toyroom.ToyRoom;
import org.example.toyroom.models.Color;
import org.example.toyroom.models.Size;
import org.example.toyroom.models.ToyFactory;
import org.example.toyroom.models.toys.Toy;

public class AddToyController implements ToyRoomAware {

    private ToyRoom toyRoom;

    @FXML private ComboBox<String> typeComboBox;
    @FXML private ComboBox<Size> sizeComboBox;
    @FXML private TextField colorField;
    @FXML private TextField materialField;
    @FXML private Label priceLabel;

    @Override
    public void setToyRoom(ToyRoom toyRoom) {
        this.toyRoom = toyRoom;
    }

    public void initialize() {
        typeComboBox.getItems().addAll(ToyFactory.getToyTypes());
        typeComboBox.setOnAction(e -> {
            String selectedType = typeComboBox.getValue();
            double price = ToyFactory.getPrice(selectedType);
            priceLabel.setText("Price: $" + price);
        });
        sizeComboBox.getItems().addAll(Size.values());
    }

    @FXML
    public void handleAddToy() {
        String type = typeComboBox.getValue();
        String sizeStr = sizeComboBox.getValue().toString();
        String colorStr = colorField.getText();
        String material = materialField.getText();

        if (type == null || sizeStr.isEmpty() || colorStr.isEmpty() || material.isEmpty()) {
            showAlert("Please fill in all fields.");
            return;
        }

        try {
            Size size = parseSize(sizeStr);
            Color color = new Color(colorStr.toLowerCase());
            double price = ToyFactory.getPrice(type);

            Toy toy = ToyFactory.createToy(type, size, color, material);
            toy.setPrice(price);

            toyRoom.getToyService().buyToy(toy);
            showAlert("Toy added successfully!");
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Error: " + e.getMessage());
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
