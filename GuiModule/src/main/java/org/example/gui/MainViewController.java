package org.example.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;
import org.example.toyroom.ToyRoom;
import org.example.toyroom.models.Toy;

import java.io.IOException;

public class MainViewController {

    private ToyRoom toyRoom;

    @FXML private StackPane contentPane;
//    @FXML private TableColumn<Toy, String> typeCol;
//    @FXML private TableColumn<Toy, String> sizeCol;
//    @FXML private TableColumn<Toy, String> colorCol;
//    @FXML private TableColumn<Toy, String> materialCol;

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

    private void refreshTable() {
        toys.setAll(toyRoom.getToyRepository().findAll());
    }

    @FXML
    public void onAddToy() {
        loadContent("AddToyView.fxml");
    }

    @FXML
    public void onReadFile() {
        toyRoom.readFile();
        refreshTable();
    }

    @FXML
    public void onFindToy() {
        loadContent("FindView.fxml");
    }

    @FXML
    public void onSortToys() {
        loadContent("SortView.fxml");
    }

    @FXML
    public void onDeleteToy() {
        loadContent("DeleteView.fxml");
    }

    @FXML
    public void onExit() {
        toyRoom.exit();
        System.exit(0);
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

}
