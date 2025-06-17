package org.example.toyroom.factory;

import org.example.entity.Theme;
import org.example.toyroom.models.*;
import org.example.toyroom.service.ThemeService;

import java.time.LocalDateTime;

public class ToyRoomFactory {

    private final ThemeService themeService;

    public ToyRoomFactory(ThemeService themeService) {
        this.themeService = themeService;
    }

    public ToyRoom createToyRoom(String name, String themeName, double initialBudget) {
        ToyRoom toyRoom = new ToyRoom(name, themeName, initialBudget);

        Theme theme = themeService.getThemeByName(themeName);
        toyRoom.setThemeImage(theme.getImage());
        toyRoom.setCreatedAt(LocalDateTime.now());
        toyRoom.setUpdatedAt(LocalDateTime.now());

        return toyRoom;
    }
}
