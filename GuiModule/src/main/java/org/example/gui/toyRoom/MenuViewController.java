package org.example.gui.toyRoom;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
//import org.example.gui.SetBudgetController;
import org.example.toyroom.models.ToyRoom;
import org.example.toyroom.models.Toy;
import org.example.toyroom.service.ThemeService;
import org.example.toyroom.service.ToyRoomService;
import org.example.toyroom.service.ToyService;
import org.example.toyroom.service.TypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class MenuViewController implements ToyRoomAware {
    private static final Logger logger = LoggerFactory.getLogger(MenuViewController.class);
    public Label roomTitle;

    private ToyRoom toyRoom;
    private ToyService toyService;
    private ToyRoomService toyRoomService;
    ThemeService themeService;
    TypeService typeService;

    @FXML private Label budgetLabel;

    @FXML private StackPane contentPane;

    @FXML private Button backButton;
    @FXML private Button addToyButton;
    @FXML private Button mainButton;
    @FXML public Button settingsButton;

    private final ObservableList<Toy> toys = FXCollections.observableArrayList();

    public void setToyRoomAndService(ToyRoom toyRoom, ToyService toyService) {
        this.toyRoom = toyRoom;
        this.toyService = toyService;

        refreshTable();

        // Now enable menu
        setMenuEnabled(true);

        roomTitle.setText(toyRoom.getName());

        updateBudgetLabel();

        // Load default view
        loadContent("ToyRoomView.fxml");
        highlightButton(mainButton);

        roomTitle.setText(toyRoom.getName());
        toyRoom.nameProperty().addListener((obs, oldVal, newVal) -> {
            roomTitle.setText(newVal);
        });

        toyRoom.budgetProperty().addListener((obs, oldVal, newVal) -> updateBudgetLabel());
    }

    public void setServices(ToyRoomService toyRoomService, ThemeService themeService, TypeService typeService) {
        this.toyRoomService = toyRoomService;
        this.themeService = themeService;
        this.typeService = typeService;
    }

    public void updateBudgetLabel() {
        if (toyRoom != null && budgetLabel != null) {
            budgetLabel.setText(String.format("$%.2f", toyRoom.getBudget()));
        }
    }

    @FXML
    public void initialize() {
//        setMenuEnabled(false);
//        backButton.setVisible(false); // Hide back button initially
//        backButton.setOnAction(e -> onBackButtonClicked()); // Hook up back click
    }

    @FXML
    private void onBackButtonClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/gui/MainView.fxml"));
            Parent root = loader.load();

            Scene scene = backButton.getScene(); // Use any control to get the scene
            scene.setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void setMenuEnabled(boolean enabled) {
        for (Button button : List.of(backButton, mainButton, addToyButton, settingsButton)) {
            button.setDisable(!enabled);
        }
    }

    private void refreshTable() {
        toys.setAll(toyService.getToysByRoomId(toyRoom.getId()));
    }

    @FXML
    public void onMainButtonClicked() {
        loadContent("ToyRoomView.fxml");
        highlightButton(mainButton);
    }

    @FXML
    public void onAddToy() {
        loadContent("AddToyView.fxml");
        highlightButton(addToyButton);
    }

    @FXML
    public void changeSettings() throws IOException {
        loadContent("ToyRoomSettings.fxml");
        highlightButton(settingsButton);
    }


    private void loadContent(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node node = loader.load();

            Object controller = loader.getController();

            if (controller instanceof ToyRoomAware) {
                ((ToyRoomAware) controller).setToyRoomAndService(toyRoom, toyService);
            }

            if (controller instanceof ToyRoomSettingsController) {
                ((ToyRoomSettingsController) controller).setServices(toyRoomService, themeService);
            }

            if (controller instanceof AddToyController) {
                ((AddToyController) controller).setServices(toyRoomService, typeService);
            }

            contentPane.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Can't load " + fxmlPath);
        }
    }

    private void highlightButton(Button selectedButton) {
        // Remove "selected" from all buttons
        for (Button button : List.of(backButton, mainButton, addToyButton, settingsButton)) {
            button.getStyleClass().remove("selected");
        }
        // Add "selected" to the active button
        if (!selectedButton.getStyleClass().contains("selected")) {
            selectedButton.getStyleClass().add("selected");
        }
    }
}
