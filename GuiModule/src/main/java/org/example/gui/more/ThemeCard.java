package org.example.gui.more;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import org.example.toyroom.models.ThemeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ThemeCard {
    private static final Logger logger = LoggerFactory.getLogger(org.example.gui.more.ThemeCard.class);

    public static Node create(ThemeInfo theme, Runnable onDelete) {
        try {
            FXMLLoader loader = new FXMLLoader(org.example.gui.more.ThemeCard.class.getResource("ThemeCard.fxml"));
            Node node = loader.load();

            ThemeCardController controller = loader.getController();
            controller.setTheme(theme, onDelete);

            return node;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Failed to load card: " + e.getMessage());
            return new Label("Failed to load card");
        }
    }
}
