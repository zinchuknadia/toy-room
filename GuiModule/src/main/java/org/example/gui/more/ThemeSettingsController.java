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
import org.example.toyroom.models.ThemeInfo;
import org.example.repository.ThemeRepository;
import org.example.toyroom.service.ThemeService;

import java.io.IOException;
import java.util.List;

public class ThemeSettingsController {
    @FXML public GridPane themeGrid;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("toyroomPU");
    EntityManager em = emf.createEntityManager();
    private ThemeService themeService;

    @FXML
    public void initialize() {
        themeService = new ThemeService(new ThemeRepository(em));
        refreshThemeGrid();
    }

    public void refreshThemeGrid() {
        themeGrid.getChildren().clear();
        List<ThemeInfo> themes = themeService.getAllThemes();

        int col = 0, row = 0;
        for (ThemeInfo theme : themes) {
            Node card = ThemeCard.create(theme, () -> {
                if(!themeService.deleteTheme(theme.getId())){
                    Alert alert = new Alert(Alert.AlertType.WARNING, "This theme is still used in some toy rooms. Please change the theme before deleting.");
                    alert.showAndWait();
                }
                refreshThemeGrid();
            });
            themeGrid.add(card, col++, row);
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

    public void handleAddTheme(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddThemeView.fxml"));
            Parent root = loader.load();

            AddThemeController controller = loader.getController();
            controller.setListener(theme -> {
                System.out.println("New theme added: " + theme);
                themeService.saveTheme(theme);
                refreshThemeGrid();
            });

            Stage stage = new Stage();
            stage.setTitle("Create Theme");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
