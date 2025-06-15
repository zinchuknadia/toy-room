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

        if (toy.getImagePath() != null) {
            var url = getClass().getResource(toy.getImagePath());
            if (url != null) {
                toyImage.setImage(new Image(url.toExternalForm()));
            } else {
                System.out.println("Image not found at: " + toy.getImagePath());
                logger.error("Image not found at: " + toy.getImagePath());
            }
        }

        if (toy.getColor() != null) {
            MyColor color = toy.getColor();
            String hex = color.getHexCode();
            cardBox.setStyle("-fx-border-color: " + hex + ";");
        }

//        ContextMenu menu = new ContextMenu();
//        menu.getStyleClass().add("toy-context-menu");
//
//        MenuItem deleteItem = new MenuItem("Delete");
//        deleteItem.getStyleClass().add("delete-menu-item");
//
//        deleteItem.setOnAction(e -> onDelete.run());
//        menu.getItems().add(deleteItem);

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
//        {
//            if (menu.isShowing()) menu.hide();
//            else menu.show(menuButton, Side.BOTTOM, 0, 0);
//        });
    }
}
