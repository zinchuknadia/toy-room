package org.example.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.example.toyroom.models.ToyRoom;

import java.io.IOException;

public class ToyRoomCard {
    public static Node create(ToyRoom room, Runnable onOpen) {
        try {
            FXMLLoader loader = new FXMLLoader(ToyRoomCard.class.getResource("ToyRoomCard.fxml"));
            Node node = loader.load();

            ToyRoomCardController controller = loader.getController();
            controller.setToyRoom(room, onOpen);

            return node;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
