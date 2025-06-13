package org.example.gui.toyRoom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.toyroom.models.ToyRoom;
import org.example.toyroom.repository.ToyRoomRepository;
import org.example.toyroom.service.ToyRoomService;
import org.example.toyroom.service.ToyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class BudgetEditorController implements ToyRoomAware {
    private static final Logger logger = LoggerFactory.getLogger(BudgetEditorController.class);

    ToyRoom toyRoom;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("toyroomPU");
    EntityManager em = emf.createEntityManager();
    ToyRoomService toyRoomService = new ToyRoomService(new ToyRoomRepository(em));

    @FXML
    private Label currentBudgetLabel;
    @FXML
    private TextField amountField;

    private double currentBudget = 0.0;
    private Consumer<Double> onBudgetChanged;


    @Override
    public void setToyRoomAndService(ToyRoom toyRoom, ToyService toyService) {
        this.toyRoom = toyRoom;
        this.currentBudget = toyRoom.getBudget();
        updateLabel();
    }

    private void updateLabel() {
        currentBudgetLabel.setText(String.format("Current Budget: $%.2f", currentBudget));
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
}
