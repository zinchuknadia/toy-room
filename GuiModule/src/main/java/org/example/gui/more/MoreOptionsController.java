package org.example.gui.more;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MoreOptionsController {

    @FXML
    private void handleModifyThemes(ActionEvent event) {
        loadSubView("/org/example/gui/more/ThemeSettingsView.fxml", event);
    }

    @FXML
    private void handleModifyTypes(ActionEvent event) {
        loadSubView("/org/example/gui/more/TypeSettingsView.fxml", event);
    }

    private void loadSubView(String fxmlPath, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Отримуємо Stage через джерело події
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
