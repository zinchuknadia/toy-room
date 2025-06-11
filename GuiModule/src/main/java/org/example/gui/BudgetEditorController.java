package org.example.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.toyroom.ToyRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class BudgetEditorController implements ToyRoomAware{
    private static final Logger logger = LoggerFactory.getLogger(BudgetEditorController.class);

    ToyRoom toyRoom;

    @FXML
    private Label currentBudgetLabel;
    @FXML
    private TextField amountField;

    private double currentBudget = 0.0;
    private Consumer<Double> onBudgetChanged;


    @Override
    public void setToyRoom(ToyRoom toyRoom) {
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

        } catch (NumberFormatException e) {
            amountField.setStyle("-fx-border-color: red;");
            logger.error("Can't modify budget: " + e.getMessage());
        }
    }
}
