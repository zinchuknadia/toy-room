package org.example.gui.toyRoom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import org.example.toyroom.entity.Theme;
import org.example.toyroom.models.ToyRoom;
import org.example.toyroom.repository.ThemeRepository;
import org.example.toyroom.repository.ToyRoomRepository;
import org.example.toyroom.service.ThemeService;
import org.example.toyroom.service.ToyRoomService;
import org.example.toyroom.service.ToyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.function.Consumer;

public class ToyRoomSettingsController implements ToyRoomAware {
    private static final Logger logger = LoggerFactory.getLogger(ToyRoomSettingsController.class);

    ToyRoom toyRoom;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("toyroomPU");
    EntityManager em = emf.createEntityManager();
    ToyRoomService toyRoomService;

    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<String> themeComboBox;
    @FXML
    private Label currentBudgetLabel;
    @FXML
    private TextField amountField;

    private double currentBudget = 0.0;
    private Consumer<Double> onBudgetChanged;


    @Override
    public void setToyRoomAndService(ToyRoom toyRoom, ToyService toyService) {
        this.toyRoom = toyRoom;
        this.toyRoomService = new ToyRoomService(new ToyRoomRepository(em));
        this.currentBudget = toyRoom.getBudget();
        ThemeService themeService = new ThemeService(new ThemeRepository(em));
        themeComboBox.getItems().setAll(themeService.getAllThemeNames());
        if (toyRoom != null) {
            nameField.setText(toyRoom.getName());
            themeComboBox.setValue(toyRoom.getThemeName());
            amountField.setText(String.format("%.2f", currentBudget));
        }
        updateLabel();
    }


    private void updateLabel() {
        currentBudgetLabel.setText(String.format("Budget:", currentBudget));
    }

    public void handleAdd() {
        modifyBudget(true);
    }

    public void handleSubtract() {
        modifyBudget(false);
    }

    private void modifyBudget(boolean add) {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (!add && amount > currentBudget) {
                amountField.setStyle("-fx-border-color: red;");
                return;
            }
            if (add) {
                currentBudget += amount;
            } else {
                currentBudget -= amount;
            }

            toyRoom.setBudget(currentBudget);
            updateLabel();
            amountField.clear();
            amountField.setStyle("");

            if (onBudgetChanged != null) {
                onBudgetChanged.accept(currentBudget);
            }
            toyRoomService.updateBudget(toyRoom);
            toyRoomService.updateUpdatedAt(toyRoom.getId());
        } catch (NumberFormatException e) {
            amountField.setStyle("-fx-border-color: red;");
            logger.error("Can't modify budget: " + e.getMessage());
        }
    }

    @FXML
    private void handleSave() {
        try {
            String name = nameField.getText();
            String theme = themeComboBox.getValue();
            double budget = Double.parseDouble(amountField.getText().replace(",", "."));
//            int capacity = Integer.parseInt(capacityField.getText());

            toyRoom.setName(name);
            toyRoom.setThemeName(theme);
            toyRoom.setBudget(budget);
//            toyRoom.setMaxCapacity(capacity);

            toyRoomService.updateToyRoom(toyRoom);
//            showAlert("Success", "Toy Room settings saved successfully.", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            showAlert("Error", "Capacity must be a number.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Error", "Failed to save settings: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleDelete(ActionEvent actionEvent) {
        // Confirm deletion
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Deletion");
        confirm.setHeaderText("Are you sure you want to delete this Toy Room?");
        confirm.setContentText("This action cannot be undone.");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Delete from DB
                toyRoomService.deleteById(toyRoom.getId());

                // Navigate back to main view (like your back button does)
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/gui/MainView.fxml"));
                    Parent root = loader.load();

                    Scene scene = ((javafx.scene.Node) actionEvent.getSource()).getScene();
                    scene.setRoot(root);
                } catch (IOException e) {
                    logger.error("Failed to return to MainView: " + e.getMessage());
                    showAlert("Error", "Failed to return to Toy Room Manager.", Alert.AlertType.ERROR);
                }
            }
        });
    }

}
