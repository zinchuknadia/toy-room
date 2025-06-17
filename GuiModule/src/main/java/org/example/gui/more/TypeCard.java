package org.example.gui.more;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import org.example.toyroom.models.TypeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TypeCard {
    private static final Logger logger = LoggerFactory.getLogger(org.example.gui.more.TypeCard.class);

    public static Node create(TypeInfo type, Runnable onDelete) {
        try {
            FXMLLoader loader = new FXMLLoader(org.example.gui.more.TypeCard.class.getResource("TypeCard.fxml"));
            Node node = loader.load();

            TypeCardController controller = loader.getController();
            controller.setType(type, onDelete);

            return node;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Failed to load card: " + e.getMessage());
            return new Label("Failed to load card");
        }
    }
}
