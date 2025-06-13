package org.example.toyroom.mapper;

import org.example.toyroom.entity.Type;
import org.example.toyroom.models.TypeInfo;

public class TypeMapper {

    public static TypeInfo toModel(Type entity) {
        TypeInfo dto = new TypeInfo();
        dto.setName(entity.getName());
        dto.setImage(entity.getImage());
        return dto;
    }
}
