package org.example.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import org.example.toyroom.models.toys.Toy;

import java.io.IOException;

public class ToyCard {
    public static Node create(Toy toy, Runnable onDelete) {
        try {
            FXMLLoader loader = new FXMLLoader(ToyCard.class.getResource("ToyCard.fxml"));
            Node node = loader.load();

            ToyCardController controller = loader.getController();
            controller.setToy(toy, onDelete);

            return node;
        } catch (IOException e) {
            e.printStackTrace();
            return new Label("Failed to load card");
        }
    }
}


