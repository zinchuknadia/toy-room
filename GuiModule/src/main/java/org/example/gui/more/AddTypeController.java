package org.example.gui.more;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.toyroom.models.TypeInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Consumer;

public class AddTypeController {

    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private ImageView imagePreview;
    @FXML private Label imagePathLabel;

    private String selectedImagePath;
    private Consumer<TypeInfo> listener;

    public void setListener(Consumer<TypeInfo> listener) {
        this.listener = listener;
    }

    @FXML
    private void handleChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Type Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(getStage());

        if (selectedFile != null) {
            try {
                File targetDir = new File("user-data/images/types");
                if (!targetDir.exists()) targetDir.mkdirs();

                File targetFile = new File(targetDir, selectedFile.getName());

                if (!targetFile.exists()) {
                    Files.copy(selectedFile.toPath(), targetFile.toPath());
                }

                selectedImagePath = selectedFile.getName();
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
        String priceText = priceField.getText().trim();

        if (name.isEmpty() || priceText.isEmpty()) {
            showAlert("Name and Price are required.");
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            TypeInfo type = new TypeInfo();
            type.setName(name);
            type.setPrice(price);
            type.setImage(selectedImagePath);

            if (listener != null) {
                listener.accept(type);
            }

            closeWindow();

        } catch (NumberFormatException e) {
            showAlert("Invalid price format.");
        }
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
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Add Type");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
