package org.example.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.example.toyroom.models.ToyRoom;

public class ToyRoomCardController {

    @FXML private Label nameLabel;
    @FXML private Label themeLabel;
    @FXML private Label budgetLabel;
    @FXML private Button openButton;
    @FXML private VBox cardBox;

    public void setToyRoom(ToyRoom room, Runnable onOpen) {
        nameLabel.setText("Name: " + room.getName());
        themeLabel.setText("Theme: " + room.getThemeName());
        budgetLabel.setText(String.format("Budget: $%.2f", room.getBudget()));

        openButton.setOnAction(e -> onOpen.run());
    }
}
