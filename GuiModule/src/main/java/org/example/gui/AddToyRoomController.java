package org.example.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.toyroom.models.ToyRoom;

public class AddToyRoomController {

    @FXML private TextField nameField;
    @FXML private ComboBox<String> themeComboBox;
    @FXML private TextField budgetField;
    @FXML private Button createButton;

    private ToyRoomCreatedListener listener;

    public interface ToyRoomCreatedListener {
        void onToyRoomCreated(ToyRoom toyRoom);
    }

    public void setListener(ToyRoomCreatedListener listener) {
        this.listener = listener;
    }

    @FXML
    public void initialize() {
        themeComboBox.getItems().addAll("Classic", "Modern", "Fantasy");

        createButton.setOnAction(e -> {
            String name = nameField.getText();
            String theme = themeComboBox.getValue();
            double budget = Double.parseDouble(budgetField.getText());

            ToyRoom room = new ToyRoom(name, theme, budget);

            if (listener != null) {
                System.out.println("Calling listener with toyRoom: " + room);
                listener.onToyRoomCreated(room);
            }

            // Close the window
            Stage stage = (Stage) createButton.getScene().getWindow();
            stage.close();
        });
    }
}
