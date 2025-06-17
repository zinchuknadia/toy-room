package org.example.toyroom.mappers;

import org.example.entity.ToyEntity;
import org.example.toyroom.factory.ToyFactory;
import org.example.toyroom.models.TypeInfo;
import org.example.toyroom.models.Toy;
import org.example.toyroom.models.MyColor;
import org.example.toyroom.models.Size;
import org.example.toyroom.service.ToyRoomService;
import org.example.toyroom.service.TypeService;

public class ToyMapper {
    private final ToyRoomService toyRoomService;
    private final TypeService typeService;
    private final ToyFactory toyFactory;

    public ToyMapper(ToyRoomService toyRoomService, TypeService typeService, ToyFactory toyFactory) {
        this.toyRoomService = toyRoomService;
        this.typeService = typeService;
        this.toyFactory = toyFactory;
    }

    public Toy toModel(ToyEntity entity) {
        TypeInfo typeInfo = TypeMapper.toModel(entity.getType());
        return toyFactory.createToy(
                entity.getId(),
                entity.getToyRoom().getId(),
                typeInfo,
                Size.valueOf(entity.getSize()),
                new MyColor(entity.getColor()),
                entity.getMaterial()
        );
    }

    public ToyEntity toEntity(Toy model) {
        ToyEntity entity = new ToyEntity();
        entity.setId(model.getId());
        entity.setToyRoom(toyRoomService.getById(model.getRoomId()));
        entity.setType(typeService.getTypeByName(model.getType()));
        entity.setSize(model.getSize().name());
        entity.setColor(model.getColor().getHexCode());
        entity.setMaterial(model.getMaterial());
        return entity;
    }
}
