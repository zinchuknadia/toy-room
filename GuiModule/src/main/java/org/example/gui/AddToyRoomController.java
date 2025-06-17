package org.example.gui;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.toyroom.factory.ToyRoomFactory;
import org.example.toyroom.models.ToyRoom;
import org.example.repository.ThemeRepository;
import org.example.toyroom.service.ThemeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AddToyRoomController {
    private static final Logger logger = LoggerFactory.getLogger(AddToyRoomController.class);

    @FXML
    private ImageView themeImagePreview;
    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<String> themeComboBox;
    @FXML
    private TextField budgetField;
    @FXML
    private Button createButton;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("toyroomPU");
    EntityManager em = emf.createEntityManager();
    ThemeService themeService;
    ToyRoomFactory toyRoomFactory;

    private ToyRoomCreatedListener listener;

    public interface ToyRoomCreatedListener {
        void onToyRoomCreated(ToyRoom toyRoom);
    }

    public void setListener(ToyRoomCreatedListener listener) {
        this.listener = listener;
    }

    Map<String, String> themeImageMap;

    @FXML
    public void initialize() {
        try {
            themeService = new ThemeService(new ThemeRepository(em));
            toyRoomFactory = new ToyRoomFactory(themeService);

            themeImageMap = new HashMap<>();

            // Load themes and populate ComboBox and image map
            themeService.getAllThemes().forEach(theme -> {
                themeComboBox.getItems().add(theme.getName());
                themeImageMap.put(theme.getName(), theme.getImage()); // e.g., "forest.png"
            });

            // Update preview when theme is selected
            themeComboBox.setOnAction(e -> {
                String selectedTheme = themeComboBox.getValue();
                if (selectedTheme != null && themeImageMap.containsKey(selectedTheme)) {
                    String imageFileName = themeImageMap.get(selectedTheme);
                    File imageFile = new File("user-data/images/themes", imageFileName);
                    if (imageFile.exists()) {
                        themeImagePreview.setImage(new Image(imageFile.toURI().toString()));
                    } else {
                        themeImagePreview.setImage(null);
                        logger.warn("Image not found for theme: " + selectedTheme);
                    }
                }
            });

            // Handle create button click
            createButton.setOnAction(e -> {
                String name = nameField.getText();
                String theme = themeComboBox.getValue();
                double budget = Double.parseDouble(budgetField.getText());

                ToyRoom room = toyRoomFactory.createToyRoom(name, theme, budget);

                if (listener != null) {
                    System.out.println("Calling listener with toyRoom: " + room);
                    listener.onToyRoomCreated(room);
                }

                Stage stage = (Stage) createButton.getScene().getWindow();
                stage.close();
            });

        } catch (Exception e) {
            showAlert("Error: " + e.getMessage());
            logger.error("Initialization error: ", e);
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Toy Room");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
