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
import javafx.stage.Stage;
import org.example.gui.toyRoom.MenuViewController;
import org.example.toyroom.factory.ToyFactory;
import org.example.toyroom.mappers.ToyMapper;
import org.example.toyroom.mappers.ToyRoomMapper;
import org.example.toyroom.models.ToyRoom;
import org.example.toyroom.models.ToyRoomManager;
import org.example.repository.ThemeRepository;
import org.example.repository.ToyRepository;
import org.example.repository.ToyRoomRepository;
import org.example.repository.TypeRepository;
import org.example.toyroom.service.ThemeService;
import org.example.toyroom.service.ToyRoomService;
import org.example.toyroom.service.ToyService;
import org.example.toyroom.service.TypeService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML private ScrollPane scrollPane;
    @FXML private GridPane toyRoomGrid;
    public MenuButton sortMenuButton;

    private ToyRoomManager manager = new ToyRoomManager();

    private EntityManagerFactory emf;
    private EntityManager em;

    private ToyRoomService toyRoomService;
    private ToyService toyService;
    private ThemeService themeService;
    private TypeService typeService;

    private ToyMapper toyMapper;
    private ToyRoomMapper toyRoomMapper;

    private final List<String> selectedSorts = new ArrayList<>();

    @FXML
    public void initialize() {
        // 1. Create EntityManager
        emf = Persistence.createEntityManagerFactory("toyroomPU");
        em = emf.createEntityManager();

        // 2. Create repositories
        ThemeRepository themeRepository = new ThemeRepository(em);
        ToyRepository toyRepository = new ToyRepository(em);
        ToyRoomRepository toyRoomRepository = new ToyRoomRepository(em);
        TypeRepository typeRepository = new TypeRepository(em);

        // 3. Create services
        themeService = new ThemeService(themeRepository);
        typeService = new TypeService(typeRepository);

        toyService = new ToyService(toyRepository, null);  // Pass mapper later
        toyRoomService = new ToyRoomService(toyRoomRepository, null);  // Pass mapper later

        // 4. Create mappers - pass services needed
        toyMapper = new ToyMapper(toyRoomService, typeService, new ToyFactory(typeService));
        toyRoomMapper = new ToyRoomMapper(themeService, toyService, toyMapper);

        // 5. Inject mappers into services (if needed)
        // (If you have setters or constructors that accept mappers)
        toyService.setMapper(toyMapper); // or recreate toyService with mapper param
        toyRoomService.setMapper(toyRoomMapper);

        // 6. Init UI
        initSortMenu();
        loadToyRoomsFromDB();
    }

    private void initSortMenu() {
        ToggleGroup sortGroup = new ToggleGroup();
        String[] options = {"size", "budget", "last modified"};

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
            card.setOnMouseClicked(e -> openToyRoomViewInSameWindow(room, new ToyService(new ToyRepository(em), toyMapper)));
            toyRoomGrid.add(card, col++, row);
            manager.addToyRoom(room);

            if (col == MAX_COLS) {
                col = 0;
                row++;
            }
        }
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
            controller.setServices(toyRoomService, themeService, typeService);

            scrollPane.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleMore(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/gui/more/MoreOptionsView.fxml"));
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
