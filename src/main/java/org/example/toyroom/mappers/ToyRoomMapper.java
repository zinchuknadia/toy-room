package org.example.toyroom.mappers;

import org.example.entity.Theme;
import org.example.entity.ToyEntity;
import org.example.entity.ToyRoomEntity;
import org.example.toyroom.models.Toy;
import org.example.toyroom.models.ToyRoom;
import org.example.toyroom.service.ThemeService;
import org.example.toyroom.service.ToyService;

import java.util.ArrayList;
import java.util.List;

public class ToyRoomMapper {
    private final ThemeService themeService;
    private final ToyService toyService;
    private final ToyMapper toyMapper;

    public ToyRoomMapper(ThemeService themeService, ToyService toyService, ToyMapper toyMapper) {
        this.themeService = themeService;
        this.toyService = toyService;
        this.toyMapper = toyMapper;
    }

    public ToyRoom toModel(ToyRoomEntity entity) {
        ToyRoom model = new ToyRoom();
        model.setId(entity.getId());
        model.setName(entity.getName());

        Theme theme = entity.getTheme();
        model.setThemeName(theme.getName());
        model.setThemeImage(theme.getImage());

        model.setCreatedAt(entity.getCreatedAt());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setBudget(entity.getBudget());

        List<Toy> toys = new ArrayList<>();
        for (ToyEntity toy : entity.getToys()) {
            Toy modelToy = toyMapper.toModel(toy);
            toys.add(modelToy);
        }
        model.setToys(toys);

        return model;
    }

    public ToyRoomEntity toEntity(ToyRoom model) {
        ToyRoomEntity entity = new ToyRoomEntity();

        if (model.getId() != null) {
            entity.setId(model.getId());
        }

        entity.setName(model.getName());
        entity.setTheme(themeService.getThemeByName(model.getThemeName()));
        entity.setCreatedAt(model.getCreatedAt());
        entity.setUpdatedAt(model.getUpdatedAt());
        entity.setBudget(model.getBudget());

        // Normally, this should only happen if room is already saved (has ID)
        if (model.getId() != null) {
            List<ToyEntity> toyEntities = new ArrayList<>();
            List<Toy> toys = toyService.getToysByRoomId(model.getId());
            for (Toy toy : toys) {
                toyEntities.add(toyMapper.toEntity(toy));
            }
            entity.setToys(toyEntities);
        } else {
            entity.setToys(new ArrayList<>());
        }

        return entity;
    }
}

