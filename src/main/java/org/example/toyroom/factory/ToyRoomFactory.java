package org.example.toyroom.factory;

import org.example.toyroom.models.ToyRoom;

import java.time.LocalDateTime;

public class ToyRoomFactory {

    public static ToyRoom createToyRoom(String name, String themeName, String themeImage, double initialBudget) {
        ToyRoom toyRoom = new ToyRoom();

        toyRoom.setName(name);
        toyRoom.setThemeName(themeName);
        toyRoom.setThemeImage(themeImage);
        toyRoom.setBudget(initialBudget);
        toyRoom.setCreatedAt(LocalDateTime.now());
        toyRoom.setUpdatedAt(LocalDateTime.now());

        return toyRoom;
    }

    public static ToyRoom createDefaultRoom() {
        return createToyRoom("My Toy Room", "Classic", "/images/classic.png", 100.0);
    }
}
