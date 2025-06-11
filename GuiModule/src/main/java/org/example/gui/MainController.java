package org.example.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.example.toyroom.ToyRoom;
import org.example.toyroom.models.toys.Toy;
import org.example.toyroom.service.ToyService;

import java.util.ArrayList;
import java.util.List;

public class MainController implements ToyRoomAware{
    @FXML private TextField searchField;
    @FXML private MenuButton sortMenuButton;
    @FXML private GridPane toyGrid;

    ToyRoom toyRoom;
    ToyService toyService;
    private final List<String> selectedSorts = new ArrayList<>();

    @Override
    public void setToyRoom(ToyRoom toyRoom) {
        this.toyRoom = toyRoom;
        toyService = toyRoom.getToyService();
        refreshToyGrid();
    }

    @FXML
    public void initialize() {
        initSortMenu();
        initSearch();
    }

    private void initSortMenu() {
        ToggleGroup sortGroup = new ToggleGroup();
        String[] options = {"type", "size", "material", "price"};

        RadioMenuItem noneItem = new RadioMenuItem("None");
        noneItem.setToggleGroup(sortGroup);
        noneItem.setSelected(true); // default
        noneItem.setOnAction(e -> {
            selectedSorts.clear();
            refreshToyGrid();
        });
        sortMenuButton.getItems().add(noneItem);


        for (String opt : options) {
            RadioMenuItem item = new RadioMenuItem(opt);
            item.setToggleGroup(sortGroup);
            item.setOnAction(e -> {
                selectedSorts.clear();
                selectedSorts.add(opt); // only one selected
                refreshToyGrid();
            });
            sortMenuButton.getItems().add(item);
        }
    }

    private void initSearch() {
        searchField.setOnKeyReleased(e -> refreshToyGrid());
    }

    private void refreshToyGrid() {
        toyGrid.getChildren().clear();
        String keyword = searchField.getText();
        List<Toy> toys = toyService.searchAndSortToys(keyword, selectedSorts);

        int col = 0, row = 0;
        for (Toy toy : toys) {
            Node card = ToyCard.create(toy, () -> {
                toyService.deleteToy(toy.getId());
                refreshToyGrid();
            });
            toyGrid.add(card, col++, row);
            if (col == 3) { col = 0; row++; }
        }
    }
}
