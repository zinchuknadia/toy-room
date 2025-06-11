package org.example.gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.toyroom.ToyRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetBudgetController {
    private static final Logger logger = LoggerFactory.getLogger(SetBudgetController.class);

    @FXML private TextField budgetField;

    private MenuViewController mainController;

    public void setMainController(MenuViewController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void onSubmit() {
        try {
            double budget = Double.parseDouble(budgetField.getText());
            ToyRoom toyRoom = new ToyRoom(budget);
            mainController.setToyRoom(toyRoom); // set toy room and load main view
        } catch (NumberFormatException e) {
            budgetField.setStyle("-fx-border-color: red;");
            budgetField.setPromptText("Invalid number!");
            logger.error("Can't set budget: " + e.getMessage());
        }
    }
}
