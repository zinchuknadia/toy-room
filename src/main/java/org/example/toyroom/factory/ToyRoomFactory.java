package org.example.toyroom.factory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.toyroom.entity.Theme;
import org.example.toyroom.entity.Type;
import org.example.toyroom.models.*;
import org.example.toyroom.repository.ThemeRepository;
import org.example.toyroom.repository.TypeRepository;
import org.example.toyroom.service.ThemeService;
import org.example.toyroom.service.TypeService;

import java.time.LocalDateTime;

public class ToyRoomFactory {

    public static ToyRoom createToyRoom(String name, String themeName, double initialBudget) {
       ToyRoom toyRoom = new ToyRoom(name, themeName, initialBudget);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("toyroomPU");
        EntityManager em = emf.createEntityManager();
        ThemeService themeService = new ThemeService(new ThemeRepository(em));
        Theme theme = themeService.getThemeByName(themeName);

        toyRoom.setThemeImage(theme.getImage());
        toyRoom.setCreatedAt(LocalDateTime.now());
        toyRoom.setUpdatedAt(LocalDateTime.now());

        return toyRoom;
    }
}
