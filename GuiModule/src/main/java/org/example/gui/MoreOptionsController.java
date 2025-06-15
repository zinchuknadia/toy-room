package org.example.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class MoreOptionsController {

    @FXML
    private void handleModifyThemes() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Coming Soon");
        alert.setHeaderText(null);
        alert.setContentText("Modify Toy Room Themes clicked.");
        alert.showAndWait();
    }

    @FXML
    private void handleModifyTypes() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Coming Soon");
        alert.setHeaderText(null);
        alert.setContentText("Modify Toy Types clicked.");
        alert.showAndWait();
    }
}
