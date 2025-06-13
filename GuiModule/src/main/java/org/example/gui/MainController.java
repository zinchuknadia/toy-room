package org.example.gui;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.gui.toyRoom.MenuViewController;
import org.example.gui.toyRoom.ToyCard;
import org.example.toyroom.entity.ToyRoomEntity;
import org.example.toyroom.mapper.ToyRoomMapper;
import org.example.toyroom.models.ToyRoom;
import org.example.toyroom.models.ToyRoomManager;
import org.example.toyroom.models.toys.Toy;
import org.example.toyroom.repository.ToyRepository;
import org.example.toyroom.repository.ToyRoomRepository;
import org.example.toyroom.service.ToyRoomService;
import org.example.toyroom.service.ToyService;

import java.io.IOException;
import java.util.List;

public class MainController {
    @FXML private ScrollPane scrollPane;
    @FXML private GridPane toyRoomGrid;

    private ToyRoomManager manager = new ToyRoomManager();
    private EntityManager em;
    private ToyRoomService toyRoomService;

    @FXML
    public void initialize() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("toyroomPU");
        em = emf.createEntityManager();
        toyRoomService = new ToyRoomService(new ToyRoomRepository(em));

        loadToyRoomsFromDB();
    }

    private void loadToyRoomsFromDB() {
        toyRoomGrid.getChildren().clear();
        List<ToyRoom> rooms = toyRoomService.getAll();

        int col = 0, row = 0;
        final int MAX_COLS = 5; // можна змінити кількість колонок

        for (ToyRoom room : rooms) {
            VBox card = createToyRoomCard(room);
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
}
