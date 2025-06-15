package org.example.gui;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.gui.toyRoom.MenuViewController;
import org.example.toyroom.models.ToyRoom;
import org.example.toyroom.models.ToyRoomManager;
import org.example.toyroom.repository.ToyRepository;
import org.example.toyroom.repository.ToyRoomRepository;
import org.example.toyroom.service.ToyRoomService;
import org.example.toyroom.service.ToyService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    public MenuButton sortMenuButton;
    @FXML private ScrollPane scrollPane;
    @FXML private GridPane toyRoomGrid;

    private ToyRoomManager manager = new ToyRoomManager();
    private EntityManager em;
    private ToyRoomService toyRoomService;
    private final List<String> selectedSorts = new ArrayList<>();


    @FXML
    public void initialize() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("toyroomPU");
        em = emf.createEntityManager();
        toyRoomService = new ToyRoomService(new ToyRoomRepository(em));

        initSortMenu();
        loadToyRoomsFromDB();
    }

    private void initSortMenu() {
        ToggleGroup sortGroup = new ToggleGroup();
        String[] options = {"size", "budget", "last modified"};

//        RadioMenuItem noneItem = new RadioMenuItem("None");
//        noneItem.setToggleGroup(sortGroup);
////        noneItem.setSelected(true); // default
//        noneItem.setOnAction(e -> {
//            selectedSorts.clear();
//            sortMenuButton.setText("Sort"); // Reset label
//            loadToyRoomsFromDB();
//        });
//        sortMenuButton.getItems().add(noneItem);

        for (String opt : options) {
            RadioMenuItem item = new RadioMenuItem(capitalize(opt));
            item.setToggleGroup(sortGroup);
            item.setOnAction(e -> {
                selectedSorts.clear();
                selectedSorts.add(opt);
                sortMenuButton.setText(capitalize(opt)); // Update label to selected option
                loadToyRoomsFromDB();
            });
            sortMenuButton.getItems().add(item);

            if (opt.equals("last modified")) {
                item.setSelected(true);
                selectedSorts.clear();
                selectedSorts.add(opt);
                sortMenuButton.setText(capitalize(opt));
            }
        }

        // Load with default sorting
        loadToyRoomsFromDB();
    }

    private String capitalize(String text) {
        if (text == null || text.isEmpty()) return text;
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    private void loadToyRoomsFromDB() {
        toyRoomGrid.getChildren().clear();
        List<ToyRoom> rooms = toyRoomService.getSortedToyRooms(selectedSorts);

        int col = 0, row = 0;
        final int MAX_COLS = 4; // можна змінити кількість колонок

        for (ToyRoom room : rooms) {
            Node card = ToyRoomCard.create(room, () ->{
                toyRoomService.deleteById(room.getId());
                loadToyRoomsFromDB();
            });
            card.setOnMouseClicked(e -> openToyRoomViewInSameWindow(room, new ToyService(new ToyRepository(em))));
            toyRoomGrid.add(card, col++, row);
            manager.addToyRoom(room);

            if (col == MAX_COLS) {
                col = 0;
                row++;
            }
        }
    }

    private VBox createToyRoomCard(ToyRoom toyRoom) {
        VBox box = new VBox(5);
        box.setStyle("-fx-border-color: #ccc; -fx-border-radius: 10; -fx-padding: 10; -fx-background-radius: 10; -fx-background-color: #f9f9f9;");
        box.setOnMouseClicked(e -> openToyRoomViewInSameWindow(toyRoom, new ToyService(new ToyRepository(em))));

        Text nameText = new Text("Name: " + toyRoom.getName());
        Text themeText = new Text("Theme: " + toyRoom.getThemeName());
        Text budgetText = new Text("Budget: " + toyRoom.getBudget());

        box.getChildren().addAll(nameText, themeText, budgetText);
        return box;
    }

    @FXML
    private void openAddToyRoomWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddToyRoomView.fxml"));
            Parent root = loader.load();

            AddToyRoomController controller = loader.getController();
            controller.setListener(toyRoom -> {
                System.out.println("ToyRoom received in MainController: " + toyRoom);
                manager.addToyRoom(toyRoom);
                toyRoomService.saveToyRoom(toyRoom);
                loadToyRoomsFromDB();
            });

            Stage stage = new Stage();
            stage.setTitle("Create ToyRoom");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openToyRoomViewInSameWindow(ToyRoom toyRoom, ToyService toyService) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/gui/toyRoom/MenuView.fxml"));
            Parent root = loader.load();
            MenuViewController controller = loader.getController();
            controller.setToyRoomAndService(toyRoom, toyService);

            scrollPane.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleMore(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/gui/MoreOptionsView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("More Options");
            stage.setScene(new Scene(root));
            stage.setWidth(700);   // Set custom width
            stage.setHeight(450);  // Set custom height
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
