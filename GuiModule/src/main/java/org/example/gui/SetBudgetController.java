package org.example.gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.toyroom.ToyRoom;

public class SetBudgetController {

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
        }
    }
}
