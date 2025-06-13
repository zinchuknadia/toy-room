package org.example.toyroom.mapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.toyroom.entity.Theme;
import org.example.toyroom.entity.ToyRoomEntity;
import org.example.toyroom.models.ToyRoom;
import org.example.toyroom.repository.ThemeRepository;
import org.example.toyroom.repository.ToyRepository;
import org.example.toyroom.repository.ToyRoomRepository;
import org.example.toyroom.service.ToyRoomService;

public class ToyRoomMapper {

    public static ToyRoom toModel(ToyRoomEntity entity) {
        ToyRoom model = new ToyRoom();

        model.setId(entity.getId());
        model.setName(entity.getName());

        Theme theme = entity.getTheme();
        model.setThemeName(theme.getName());
        model.setThemeImage(theme.getImage());

        model.setCreatedAt(entity.getCreatedAt());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setBudget(entity.getBudget());

//        Theme theme = entity.getTheme();
//        if (theme != null) {
//            model.setThemeName(theme.getName());
//            model.setThemeImage(theme.getImage());
//        }

        return model;
    }

    public static ToyRoomEntity toEntity(ToyRoom model) {
        ToyRoomEntity entity = new ToyRoomEntity();

        if (model.getId() != null) {
            entity.setId(model.getId());
        }
        entity.setName(model.getName());

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("toyroomPU");
        EntityManager em = emf.createEntityManager();

        ThemeRepository themeRepo = new ThemeRepository(em);
        entity.setTheme(themeRepo.findByName(model.getThemeName()));

        entity.setCreatedAt(model.getCreatedAt());
        entity.setUpdatedAt(model.getUpdatedAt());
        entity.setBudget(model.getBudget());

        ToyRepository toyRepo = new ToyRepository(em);
        entity.setToys(toyRepo.findByToyRoomId(entity.getId()));

//        if (model.getThemeName() != null || model.getThemeImage() != null) {
//            Theme theme = new Theme();
//            theme.setName(model.getThemeName());
//            theme.setImage(model.getThemeImage());
//            entity.setTheme(theme);
//        }

        return entity;
    }
}
