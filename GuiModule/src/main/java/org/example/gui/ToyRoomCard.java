package org.example.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import org.example.gui.toyRoom.ToyCard;
import org.example.toyroom.models.ToyRoom;
import org.example.toyroom.repository.ToyRepository;
import org.example.toyroom.service.ToyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ToyRoomCard {
    private static final Logger logger = LoggerFactory.getLogger(ToyRoomCard.class);

    public static Node create(ToyRoom room, Runnable onOpen) {
        try {
            FXMLLoader loader = new FXMLLoader(ToyRoomCard.class.getResource("ToyRoomCard.fxml"));
            Node node = loader.load();

            ToyRoomCardController controller = loader.getController();
            controller.setToyRoom(room, onOpen);

            return node;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Failed to load card: " + e.getMessage());
            return new Label("Failed to load card");
        }
    }
}
