package org.example.gui;

import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.example.toyroom.models.MyColor;
import org.example.toyroom.models.toys.Toy;

public class ToyCardController {
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
            }
        }

        if (toy.getColor() != null) {
            MyColor color = toy.getColor();
            String hex = color.getHexCode();
            cardBox.setStyle("-fx-border-color: " + hex + ";");
        }

        ContextMenu menu = new ContextMenu();
        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(e -> onDelete.run());
        menu.getItems().add(deleteItem);

        menuButton.setOnMouseClicked(e -> {
            if (menu.isShowing()) menu.hide();
            else menu.show(menuButton, Side.BOTTOM, 0, 0);
        });
    }
}
