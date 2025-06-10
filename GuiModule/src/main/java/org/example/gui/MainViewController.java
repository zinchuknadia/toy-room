package org.example.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import org.example.toyroom.ToyRoom;
import org.example.toyroom.models.Toy;

import java.io.IOException;
import java.util.List;

public class MainViewController {

    private ToyRoom toyRoom;

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


    private final ObservableList<Toy> toys = FXCollections.observableArrayList();

    public void setToyRoom(ToyRoom toyRoom) {
        this.toyRoom = toyRoom;
        refreshTable();
    }

//    @FXML
//    public void initialize() {
//        typeCol.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
//        sizeCol.setCellValueFactory(cellData -> cellData.getValue().sizeProperty().asString());
//        colorCol.setCellValueFactory(cellData -> cellData.getValue().colorProperty().asString());
//        materialCol.setCellValueFactory(cellData -> cellData.getValue().materialProperty());
//
//        toyTable.setItems(toys);
//    }

    @FXML
    public void initialize() {
        // Load default view (e.g., AddToyView)
        loadContent("AddToyView.fxml");
        highlightButton(addToyButton);
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

    private void loadContent(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node node = loader.load();

            // передаємо ToyRoom у підлеглий контролер, якщо треба
            Object controller = loader.getController();
            if (controller instanceof ToyRoomAware) {
                ((ToyRoomAware) controller).setToyRoom(toyRoom);
            }

            contentPane.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void highlightButton(Button selectedButton) {
        // Remove "selected" from all buttons
        for (Button button : List.of(addToyButton, readFileButton, findToyButton, sortToysButton, deleteToyButton)) {
            button.getStyleClass().remove("selected");
        }
        // Add "selected" to the active button
        if (!selectedButton.getStyleClass().contains("selected")) {
            selectedButton.getStyleClass().add("selected");
        }
    }

}
