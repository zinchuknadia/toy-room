package org.example.toyroom.mappers;

import org.example.entity.Type;
import org.example.toyroom.models.TypeInfo;

public class TypeMapper {

    public static TypeInfo toModel(Type entity) {
        TypeInfo dto = new TypeInfo();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setImage(entity.getImage());
        dto.setPrice(entity.getPrice());
        return dto;
    }


    public static Type toEntity(TypeInfo type) {
        Type typeEntity = new Type();
        typeEntity.setId(type.getId());
        typeEntity.setName(type.getName());
        typeEntity.setImage(type.getImage());
        typeEntity.setPrice(type.getPrice());
        return typeEntity;
    }
}
