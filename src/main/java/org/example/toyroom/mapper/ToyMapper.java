package org.example.toyroom.mapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.toyroom.entity.ToyEntity;
import org.example.toyroom.factory.ToyFactory;
import org.example.toyroom.models.TypeInfo;
import org.example.toyroom.models.Toy;
import org.example.toyroom.models.MyColor;
import org.example.toyroom.models.Size;
import org.example.toyroom.repository.ToyRoomRepository;
import org.example.toyroom.service.ToyRoomService;

public class ToyMapper {

    private static TypeMapper typeMapper;

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("toyroomPU");
    static EntityManager em = emf.createEntityManager();
    private static ToyRoomService toyRoomService = new ToyRoomService(new ToyRoomRepository(em));

    public ToyMapper(TypeMapper typeMapper) {
        this.typeMapper = typeMapper;
    }

    public static Toy toModel(ToyEntity entity) {
        TypeInfo typeInfo = typeMapper.toModel(entity.getType());

        return ToyFactory.createToy(
                entity.getId(),
                entity.getToyRoom().getId(),
                typeInfo,
                Size.valueOf(entity.getSize()),
                new MyColor(entity.getColor()),
                entity.getMaterial()
        );
    }

    public static ToyEntity toEntity(Toy model) {
        ToyEntity entity = new ToyEntity();
        entity.setId(model.getId());
        entity.setToyRoom(toyRoomService.getById(model.getRoomId()));
        entity.setType(typeMapper.toEntity(model.getType()));
        entity.setSize(model.getSize().name());
        entity.setColor(model.getColor().getHexCode());
        entity.setMaterial(model.getMaterial());
        return entity;
    }
}
