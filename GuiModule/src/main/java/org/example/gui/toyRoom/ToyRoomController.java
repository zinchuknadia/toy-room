package org.example.gui.toyRoom;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.example.toyroom.models.ToyRoom;
import org.example.toyroom.models.Toy;
import org.example.toyroom.service.ToyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ToyRoomController implements ToyRoomAware {
    private static final Logger logger = LoggerFactory.getLogger(ToyRoomController.class);

    @FXML private TextField searchField;
    @FXML private MenuButton sortMenuButton;
    @FXML private GridPane toyGrid;

    ToyRoom toyRoom;
    ToyService toyService;
    private final List<String> selectedSorts = new ArrayList<>();

    @Override
    public void setToyRoomAndService(ToyRoom toyRoom, ToyService toyService) {
        this.toyRoom = toyRoom;
        this.toyService = toyService;
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
            sortMenuButton.setText("Sort"); // Reset label
            refreshToyGrid();
        });
        sortMenuButton.getItems().add(noneItem);

        for (String opt : options) {
            RadioMenuItem item = new RadioMenuItem(capitalize(opt));
            item.setToggleGroup(sortGroup);
            item.setOnAction(e -> {
                selectedSorts.clear();
                selectedSorts.add(opt);
                sortMenuButton.setText(capitalize(opt)); // Update label to selected option
                refreshToyGrid();
            });
            sortMenuButton.getItems().add(item);
        }
    }

    private String capitalize(String text) {
        if (text == null || text.isEmpty()) return text;
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }


    private void initSearch() {
        searchField.setOnKeyReleased(e -> refreshToyGrid());
    }

    private void refreshToyGrid() {
        toyGrid.getChildren().clear();
        String keyword = searchField.getText();
        List<Toy> toys = toyService.searchAndSortToys(toyRoom, keyword, selectedSorts);

        int col = 0, row = 0;
        for (Toy toy : toys) {
            Node card = ToyCard.create(toy, () -> {
                toyService.deleteById(toy.getId());
                refreshToyGrid();
            });
            toyGrid.add(card, col++, row);
            if (col == 3) { col = 0; row++; }
        }
    }
}
