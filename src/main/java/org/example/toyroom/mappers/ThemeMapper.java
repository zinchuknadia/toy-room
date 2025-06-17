package org.example.toyroom.mappers;

import org.example.entity.Theme;
import org.example.toyroom.models.ThemeInfo;

public class ThemeMapper {

    public static ThemeInfo toModel(Theme entity) {
        ThemeInfo dto = new ThemeInfo();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setImage(entity.getImage());
        return dto;
    }

    public static Theme toEntity(ThemeInfo theme) {
        Theme themeEntity = new Theme();
        themeEntity.setId(theme.getId());
        themeEntity.setName(theme.getName());
        themeEntity.setImage(theme.getImage());
        return themeEntity;
    }
}

