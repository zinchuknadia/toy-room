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
import org.example.toyroom.models.Toy;

import java.io.IOException;
import java.util.List;

public class MainViewController {

    private ToyRoom toyRoom;
    @FXML
    private Label budgetLabel;

    @FXML private StackPane contentPane;
//    @FXML private TableColumn<Toy, String> typeCol;
//    @FXML private TableColumn<Toy, String> sizeCol;
//    @FXML private TableColumn<Toy, String> colorCol;
//    @FXML private TableColumn<Toy, String> materialCol;

    @FXML private Button addToyButton;
    @FXML private Button readFileButton;
    @FXML private Button findToyButton;
    @FXML private Button sortToysButton;
    @FXML private Button deleteToyButton;
    @FXML private Button editBudgetButton;


    private final ObservableList<Toy> toys = FXCollections.observableArrayList();

    public void setToyRoom(ToyRoom toyRoom) {
        this.toyRoom = toyRoom;
        refreshTable();

        // Now enable menu
        setMenuEnabled(true);

        updateBudgetLabel();

        // Load default view
        loadContent("AddToyView.fxml");
        highlightButton(addToyButton);

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
        for (Button button : List.of(addToyButton, readFileButton, findToyButton, sortToysButton, deleteToyButton, editBudgetButton)) {
            button.setDisable(!enabled);
        }
    }

    private void refreshTable() {
        toys.setAll(toyRoom.getToyService().findAll());
    }

    @FXML
    public void onAddToy() {
        loadContent("AddToyView.fxml");
        highlightButton(addToyButton);
    }

    @FXML
    public void onReadFile() {
        loadContent("ReadFileView.fxml");
        highlightButton(readFileButton);
    }

    @FXML
    public void onFindToy() {
        loadContent("FindView.fxml");
        highlightButton(findToyButton);
    }

    @FXML
    public void onSortToys() {
        loadContent("SortView.fxml");
        highlightButton(sortToysButton);
    }

    @FXML
    public void onDeleteToy() {
        loadContent("DeleteView.fxml");
        highlightButton(deleteToyButton);
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

            // передаємо ToyRoom у підлеглий контролер, якщо треба
            Object controller = loader.getController();

            if (controller instanceof ToyRoomAware) {
                ((ToyRoomAware) controller).setToyRoom(toyRoom);
            }

            if (controller instanceof SetBudgetController) {
                ((SetBudgetController) controller).setMainController(this);
            }

//            if (controller instanceof BudgetEditorController) {
//                BudgetEditorController budgetEditorController = (BudgetEditorController) controller;
//
//                // Set current budget value
//                budgetEditorController.setInitialBudget(toyRoom.getBudget());
//
//                // Set callback to update ToyRoom and label
//                budgetEditorController.setOnBudgetChanged(newBudget -> {
//                    toyRoom.setBudget(newBudget);
//                    updateBudgetLabel();
//                });
//            }

            contentPane.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void highlightButton(Button selectedButton) {
        // Remove "selected" from all buttons
        for (Button button : List.of(addToyButton, readFileButton, findToyButton, sortToysButton, deleteToyButton, editBudgetButton)) {
            button.getStyleClass().remove("selected");
        }
        // Add "selected" to the active button
        if (!selectedButton.getStyleClass().contains("selected")) {
            selectedButton.getStyleClass().add("selected");
        }
    }

}
