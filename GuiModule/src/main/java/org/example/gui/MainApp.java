package org.example.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.toyroom.ToyRoom;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/gui/MainView.fxml"));
        Scene scene = new Scene(loader.load(),900,500);

        // передати ToyRoom у контролер
        MainViewController controller = loader.getController();
        controller.setToyRoom(new ToyRoom());

        primaryStage.setTitle("Toy Room");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
