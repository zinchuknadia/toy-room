package org.example.gui.more;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.toyroom.models.TypeInfo;
import org.example.repository.TypeRepository;
import org.example.toyroom.service.TypeService;

import java.io.IOException;
import java.util.List;

public class TypeSettingsController {
    @FXML private GridPane typeGrid;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("toyroomPU");
    EntityManager em = emf.createEntityManager();
    private TypeService typeService;

    @FXML
    public void initialize() {
        typeService = new TypeService(new TypeRepository(em));
        refreshTypeGrid();
    }

    public void refreshTypeGrid() {
        typeGrid.getChildren().clear();
        List<TypeInfo> types = typeService.getAllTypes();

        int col = 0, row = 0;
        for (TypeInfo type : types) {
            Node card = TypeCard.create(type, () -> {
                if(!typeService.deleteById(type.getId())){
                    Alert alert = new Alert(Alert.AlertType.WARNING, "This type is still used in some toys. Please change the type before deleting.");
                    alert.showAndWait();
                }
                refreshTypeGrid();
            });
            typeGrid.add(card, col++, row);
            if (col == 3) { col = 0; row++; }
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/gui/more/MoreOptionsView.fxml"));
            Parent root = loader.load();

            // Отримуємо Stage через джерело події
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleAddType(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddTypeView.fxml"));
            Parent root = loader.load();

            AddTypeController controller = loader.getController();
            controller.setListener(type -> {
                System.out.println("New type added: " + type);
                typeService.saveType(type);
                refreshTypeGrid();
            });

            Stage stage = new Stage();
            stage.setTitle("Create Type");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
