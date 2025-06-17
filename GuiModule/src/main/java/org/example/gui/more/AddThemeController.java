package org.example.gui.more;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.toyroom.models.ThemeInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Consumer;

public class AddThemeController {

    @FXML private TextField nameField;
    @FXML
    private ImageView imagePreview;
    @FXML private Label imagePathLabel;

    private String selectedImagePath;
    private Consumer<ThemeInfo> listener;

    public interface ThemeCreatedListener {
        void onThemeCreated(ThemeInfo theme);
    }

    public void setListener(Consumer<ThemeInfo> listener) {
        this.listener = listener;
    }

    @FXML
    private void handleChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Theme Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(getStage());

        if (selectedFile != null) {
            try {
                // Define target directory for storing images
                File targetDir = new File("user-data/images/themes");
                if (!targetDir.exists()) targetDir.mkdirs();

                // Copy file to target location
                File targetFile = new File(targetDir, selectedFile.getName());

                if (!targetFile.exists()) {
                    Files.copy(selectedFile.toPath(), targetFile.toPath());
                }

                // ✅ Store ONLY the filename in DB (e.g. "forest.png")
                selectedImagePath = selectedFile.getName();

                // ✅ For preview, load image from full file system path
                imagePreview.setImage(new Image(targetFile.toURI().toString()));
                imagePathLabel.setText(selectedImagePath);

            } catch (IOException e) {
                e.printStackTrace();
                imagePathLabel.setText("Error copying file.");
            }
        }
    }




    @FXML
    private void handleCreate() {
        String name = nameField.getText().trim();

        if (name.isEmpty()) {
            nameField.setPromptText("Name is required");
            nameField.setStyle("-fx-border-color: red;");
            return;
        }

        ThemeInfo theme = new ThemeInfo();
        theme.setName(name);
        theme.setImage(selectedImagePath);  // can be null

        if (listener != null) {
            listener.accept(theme);
        }

        closeWindow();
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private Stage getStage() {
        return (Stage) nameField.getScene().getWindow();
    }

    private void closeWindow() {
        getStage().close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Toy Room");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
