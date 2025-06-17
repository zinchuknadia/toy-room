package org.example.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.toyroom.models.ToyRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Optional;

public class ToyRoomCardController {
    private static final Logger logger = LoggerFactory.getLogger(ToyRoomCardController.class);

    public ImageView toyRoomImage;
    public Button menuButton;
    @FXML private Label nameLabel;
    @FXML private Label themeLabel;
    @FXML private Label budgetLabel;
//    @FXML private VBox cardBox;

    public void setToyRoom(ToyRoom room, Runnable onDelete) {
        nameLabel.setText(room.getName());
        themeLabel.setText("Theme: " + room.getThemeName());
        budgetLabel.setText(String.format("Budget: $%.2f", room.getBudget()));

        String imageFileName = room.getThemeImage(); // e.g., "forest.png"
        File imageFile = new File("user-data/images/themes", imageFileName);

        if (imageFile.exists()) {
            Image image = new Image(imageFile.toURI().toString());
            toyRoomImage.setImage(image);
        } else {
            System.out.println("Image not found: " + imageFile.getAbsolutePath());
        }

        menuButton.setOnMouseClicked(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this Toy Room?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                onDelete.run(); // тільки якщо користувач натиснув OK
            }
        });
    }
}
