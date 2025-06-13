package org.example.gui.toyRoom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.toyroom.models.ToyRoom;
import org.example.toyroom.models.MyColor;
import org.example.toyroom.models.Size;
import org.example.toyroom.factory.ToyFactory;
import org.example.toyroom.models.toys.Toy;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import org.example.toyroom.repository.ToyRepository;
import org.example.toyroom.repository.ToyRoomRepository;
import org.example.toyroom.service.ToyRoomService;
import org.example.toyroom.service.ToyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddToyController implements ToyRoomAware {

    private static final Logger logger = LoggerFactory.getLogger(AddToyController.class);

    private ToyRoom toyRoom;
    private ToyService toyService;
    private ToyRoomService toyRoomService;

    @FXML private ComboBox<String> typeComboBox;
    @FXML private ComboBox<Size> sizeComboBox;
    @FXML private ColorPicker colorPicker;
    @FXML private TextField materialField;
    @FXML private Label priceLabel;

    @Override
    public void setToyRoomAndService(ToyRoom toyRoom, ToyService toyService) {
        this.toyRoom = toyRoom;
        this.toyService = toyService;
        }

    public void initialize() {
        typeComboBox.getItems().addAll(ToyFactory.getToyTypes());
        typeComboBox.setOnAction(e -> {
            String selectedType = typeComboBox.getValue();
            double price = ToyFactory.getPrice(selectedType);
            priceLabel.setText("Price: $" + price);
        });
        sizeComboBox.getItems().addAll(Size.values());

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("toyroomPU");
        EntityManager em = emf.createEntityManager();
        toyRoomService = new ToyRoomService(new ToyRoomRepository(em));
    }

    @FXML
    public void handleAddToy() {
        String type = typeComboBox.getValue();
        String sizeStr = sizeComboBox.getValue().toString();
        String material = materialField.getText();

        if (type == null || sizeStr.isEmpty() || material.isEmpty()) {
            showAlert("Please fill in all fields.");
            logger.warn("ToyRoom: type: {} size: {} material: {}", type, sizeStr, material);
            return;
        }

        try {
            Size size = parseSize(sizeStr);
            double price = ToyFactory.getPrice(type);
            String imagePath = ToyFactory.getImagePath(type);

            Color fxColor = colorPicker.getValue();
            String hexColor = String.format("#%02x%02x%02x",
                    (int)(fxColor.getRed() * 255),
                    (int)(fxColor.getGreen() * 255),
                    (int)(fxColor.getBlue() * 255)
            );

            MyColor myColor = new MyColor(hexColor);

            Toy toy = ToyFactory.createToy(type, size, myColor, material);
            toy.setPrice(price);
            toy.setImagePath(imagePath);

            toy.setRoomId(toyRoom.getId());

            boolean success = toyService.buyToy(toy, toyRoom);
            if(success){
                showAlert("Toy added successfully!");
            }else{
                showAlert("Not enough money!");
                logger.warn("Not enough money!");
            }
            toyRoomService.updateBudget(toyRoom);
            toyRoomService.updateUpdatedAt(toyRoom.getId());
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Error: " + e.getMessage());
            logger.error(e.getMessage());
        }
    }

    private static Size parseSize(String sizeStr){
        switch(sizeStr.toLowerCase()){
            case "large": return Size.LARGE;
            case "medium": return Size.MEDIUM;
            case "small": return Size.SMALL;
            default: throw new IllegalArgumentException("Invalid Size: " + sizeStr);
        }
    }

    private void clearFields() {
        materialField.clear();
        colorPicker.setValue(Color.WHITE);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Toy");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
