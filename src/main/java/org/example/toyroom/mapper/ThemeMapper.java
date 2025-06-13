package org.example.toyroom.mapper;

import org.example.toyroom.entity.Theme;
import org.example.toyroom.models.ThemeInfo;

public class ThemeMapper {
    public static ThemeInfo toModel(Theme entity) {
        ThemeInfo dto = new ThemeInfo();
        dto.setName(entity.getName());
        dto.setImage(entity.getImage());
        return dto;
    }
}

