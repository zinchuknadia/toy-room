package org.example.gui.more;

import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.example.gui.toyRoom.ToyCardController;
import org.example.toyroom.models.ThemeInfo;
import org.example.toyroom.models.ToyRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Optional;

public class ThemeCardController {
    private static final Logger logger = LoggerFactory.getLogger(org.example.gui.more.ThemeCardController.class);

    public ImageView themeImage;
    public Button menuButton;
    @FXML private Label nameLabel;
    //    @FXML private Button openButton;
//    @FXML private VBox cardBox;

    public void setTheme(ThemeInfo theme, Runnable onDelete) {
        nameLabel.setText(theme.getName());

        String imageFileName = theme.getImage(); // e.g., "forest.png"
        File imageFile = new File("user-data/images/themes", imageFileName);

        if (imageFile.exists()) {
            Image image = new Image(imageFile.toURI().toString());
            themeImage.setImage(image);
        } else {
            System.out.println("Image not found: " + imageFile.getAbsolutePath());
        }

        menuButton.setOnMouseClicked(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this Theme?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                onDelete.run(); // тільки якщо користувач натиснув OK
            }
        });
    }
}