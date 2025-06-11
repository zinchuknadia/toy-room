package org.example.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.example.toyroom.ToyRoom;
import org.example.toyroom.models.toys.Toy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class MenuViewController {
    private static final Logger logger = LoggerFactory.getLogger(MenuViewController.class);

    private ToyRoom toyRoom;
    @FXML
    private Label budgetLabel;

    @FXML private StackPane contentPane;

    @FXML private Button addToyButton;
    @FXML private Button editBudgetButton;
    @FXML private Button mainButton;


    private final ObservableList<Toy> toys = FXCollections.observableArrayList();

    public void setToyRoom(ToyRoom toyRoom) {
        this.toyRoom = toyRoom;
        refreshTable();

        // Now enable menu
        setMenuEnabled(true);

        updateBudgetLabel();

        // Load default view
        loadContent("MainView.fxml");
        highlightButton(mainButton);

        toyRoom.budgetProperty().addListener((obs, oldVal, newVal) -> updateBudgetLabel());
    }

    public void updateBudgetLabel() {
        if (toyRoom != null && budgetLabel != null) {
            budgetLabel.setText(String.format("$%.2f", toyRoom.getBudget()));
        }
    }

    @FXML
    public void initialize() {
        loadContent("SetBudgetView.fxml");
        setMenuEnabled(false);
    }

    private void setMenuEnabled(boolean enabled) {
        for (Button button : List.of(mainButton, addToyButton, editBudgetButton)) {
            button.setDisable(!enabled);
        }
    }

    private void refreshTable() {
        toys.setAll(toyRoom.getToyService().getAllToys());
    }

    @FXML
    public void onMainButtonClicked() {
        loadContent("MainView.fxml");
        highlightButton(mainButton);
    }

    @FXML
    public void onAddToy() {
        loadContent("AddToyView.fxml");
        highlightButton(addToyButton);
    }

    @FXML
    public void handleEditBudget() throws IOException {
        loadContent("BudgetEditor.fxml");
        highlightButton(editBudgetButton);
    }


    private void loadContent(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node node = loader.load();

            Object controller = loader.getController();

            if (controller instanceof ToyRoomAware) {
                ((ToyRoomAware) controller).setToyRoom(toyRoom);
            }

            if (controller instanceof SetBudgetController) {
                ((SetBudgetController) controller).setMainController(this);
            }

            contentPane.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Can't load " + fxmlPath);
        }
    }

    private void highlightButton(Button selectedButton) {
        // Remove "selected" from all buttons
        for (Button button : List.of(mainButton, addToyButton, editBudgetButton)) {
            button.getStyleClass().remove("selected");
        }
        // Add "selected" to the active button
        if (!selectedButton.getStyleClass().contains("selected")) {
            selectedButton.getStyleClass().add("selected");
        }
    }

}
