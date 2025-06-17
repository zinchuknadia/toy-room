package org.example.gui.more;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.toyroom.models.ToyRoom;
import org.example.toyroom.models.TypeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Optional;

public class TypeCardController {
    private static final Logger logger = LoggerFactory.getLogger(org.example.gui.more.TypeCardController.class);

    public ImageView typeImage;
    public Button menuButton;
    @FXML private Label nameLabel;
    @FXML private Label priceLabel;
//    @FXML private Label budgetLabel;
    //    @FXML private Button openButton;

    public void setType(TypeInfo type, Runnable onDelete) {
        nameLabel.setText(type.getName());
        priceLabel.setText("Price: " + type.getPrice());

        String imageFileName = type.getImage();
        File imageFile = new File("user-data/images/types", imageFileName);

        if (imageFile.exists()) {
            Image image = new Image(imageFile.toURI().toString());
            typeImage.setImage(image);
        } else {
            System.out.println("Image not found: " + imageFile.getAbsolutePath());
        }

        menuButton.setOnMouseClicked(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this Type?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                onDelete.run(); // тільки якщо користувач натиснув OK
            }
        });
    }
}