package org.example.gui;

import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.example.gui.toyRoom.ToyCardController;
import org.example.toyroom.models.ToyRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ToyRoomCardController {
    private static final Logger logger = LoggerFactory.getLogger(ToyRoomCardController.class);


    public ImageView toyRoomImage;
    public Button menuButton;
    @FXML private Label nameLabel;
    @FXML private Label themeLabel;
    @FXML private Label budgetLabel;
//    @FXML private Button openButton;
    @FXML private VBox cardBox;

    public void setToyRoom(ToyRoom room, Runnable onDelete) {
        nameLabel.setText(room.getName());
        themeLabel.setText("Theme: " + room.getThemeName());
        budgetLabel.setText(String.format("Budget: $%.2f", room.getBudget()));

        if (room.getThemeImage() != null) {
            var url = getClass().getResource(room.getThemeImage());
            if (url != null) {
                toyRoomImage.setImage(new Image(url.toExternalForm()));
            } else {
                System.out.println("Image not found at: " + room.getThemeImage());
                logger.error("Image not found at: " + room.getThemeImage());
            }
        }

//        ContextMenu menu = new ContextMenu();
//        menu.getStyleClass().add("toy-context-menu");
//
//        MenuItem deleteItem = new MenuItem("Delete");
//        deleteItem.getStyleClass().add("delete-menu-item");
//
//        deleteItem.setOnAction(e -> onOpen.run());
//        menu.getItems().add(deleteItem);

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
