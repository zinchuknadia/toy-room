package org.example.gui.toyRoom;

import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.example.toyroom.models.MyColor;
import org.example.toyroom.models.Toy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Optional;
import java.util.function.Consumer;

public class ToyCardController {
    private static final Logger logger = LoggerFactory.getLogger(ToyCardController.class);

    @FXML private Label typeLabel;
    @FXML private Label sizeLabel;
    @FXML private Label materialLabel;
    @FXML private Label priceLabel;
    @FXML private ImageView toyImage;
    @FXML private Button menuButton;

    @FXML private VBox cardBox;

    public void setToy(Toy toy, Runnable onDelete) {
        typeLabel.setText("Type: " + toy.getType());
        sizeLabel.setText("Size: " + toy.getSize().name());
        materialLabel.setText("Material: " + toy.getMaterial());
        priceLabel.setText("Price: $" + toy.getPrice());

        String imageFileName = toy.getImagePath();
        File imageFile = new File("user-data/images/types", imageFileName);

        if (imageFile.exists()) {
            Image image = new Image(imageFile.toURI().toString());
            toyImage.setImage(image);
        } else {
            System.out.println("Image not found: " + imageFile.getAbsolutePath());
        }

        if (toy.getColor() != null) {
            MyColor color = toy.getColor();
            String hex = color.getHexCode();
            cardBox.setStyle("-fx-border-color: " + hex + ";");
        }

        menuButton.setOnMouseClicked(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this toy?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                onDelete.run(); // тільки якщо користувач натиснув OK
            }
        });
    }
}
