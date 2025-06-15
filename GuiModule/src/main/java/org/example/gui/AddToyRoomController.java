package org.example.gui;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.gui.toyRoom.AddToyController;
import org.example.toyroom.factory.ToyRoomFactory;
import org.example.toyroom.models.ToyRoom;
import org.example.toyroom.repository.ThemeRepository;
import org.example.toyroom.service.ThemeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddToyRoomController {
    private static final Logger logger = LoggerFactory.getLogger(AddToyRoomController.class);

    @FXML private TextField nameField;
    @FXML private ComboBox<String> themeComboBox;
    @FXML private TextField budgetField;
    @FXML private Button createButton;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("toyroomPU");
    EntityManager em = emf.createEntityManager();
    ThemeService themeService;

    private ToyRoomCreatedListener listener;

    public interface ToyRoomCreatedListener {
        void onToyRoomCreated(ToyRoom toyRoom);
    }

    public void setListener(ToyRoomCreatedListener listener) {
        this.listener = listener;
    }

    @FXML
    public void initialize() {
        try {
            themeService = new ThemeService(new ThemeRepository(em));

            themeComboBox.getItems().addAll(themeService.getAllThemeNames());

            createButton.setOnAction(e -> {
                String name = nameField.getText();
                String theme = themeComboBox.getValue();
                double budget = Double.parseDouble(budgetField.getText());

                ToyRoom room = ToyRoomFactory.createToyRoom(name, theme, budget);

                if (listener != null) {
                    System.out.println("Calling listener with toyRoom: " + room);
                    listener.onToyRoomCreated(room);
                }

                // Close the window
                Stage stage = (Stage) createButton.getScene().getWindow();
                stage.close();
            });
        }catch (Exception e) {
            showAlert("Error: " + e.getMessage());
            logger.error(e.getMessage());
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
