package org.example.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.toyroom.ToyRoom;
import org.example.toyroom.models.MyColor;
import org.example.toyroom.models.Size;
import org.example.toyroom.models.ToyFactory;
import org.example.toyroom.models.toys.Toy;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

public class AddToyController implements ToyRoomAware {

    private ToyRoom toyRoom;

    @FXML private ComboBox<String> typeComboBox;
    @FXML private ComboBox<Size> sizeComboBox;
    @FXML private TextField colorField;
    @FXML private ColorPicker colorPicker;
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
//        String colorStr = colorField.getText();
        String material = materialField.getText();

        if (type == null || sizeStr.isEmpty() || material.isEmpty()) {
            showAlert("Please fill in all fields.");
            return;
        }

        try {
            Size size = parseSize(sizeStr);
            double price = ToyFactory.getPrice(type);

            Color fxColor = colorPicker.getValue();
            String hexColor = String.format("#%02x%02x%02x",
                    (int)(fxColor.getRed() * 255),
                    (int)(fxColor.getGreen() * 255),
                    (int)(fxColor.getBlue() * 255)
            );

            MyColor myColor = new MyColor(hexColor);

            Toy toy = ToyFactory.createToy(type, size, myColor, material);
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
//        typeComboBox.getSelectionModel().clearSelection();
//        sizeComboBox.getSelectionModel().clearSelection();
//        colorField.clear();
        materialField.clear();
        colorPicker.setValue(Color.WHITE); // Reset to white or your default
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Toy");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
