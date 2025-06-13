package org.example.toyroom.mapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.toyroom.entity.ToyEntity;
import org.example.toyroom.entity.ToyRoomEntity;
import org.example.toyroom.entity.Type;
import org.example.toyroom.models.toys.Toy;
import org.example.toyroom.models.MyColor;
import org.example.toyroom.models.Size;
import org.example.toyroom.repository.ToyRoomRepository;
import org.example.toyroom.repository.TypeRepository;

public class ToyMapper {

    public static Toy toModel(ToyEntity entity) {
        Toy model = new Toy();
        model.setId((long) (entity.getId() != null ? entity.getId().intValue() : 0)); // Optional: change to Long
//        model.setName(entity.getName());
        model.setRoomId(entity.getToyRoom().getId());

        model.setType(entity.getType().getName()); // Assuming getName() exists in Type

        // Parse Size from String
        model.setSize(Size.valueOf(entity.getSize()));

        // Create MyColor from hex code
        model.setColor(new MyColor(entity.getColor()));

        model.setMaterial(entity.getMaterial());
        model.setPrice(entity.getPrice());
        model.setImagePath(entity.getType().getImage());

        return model;
    }

    public static ToyEntity toEntity(Toy model) {
        ToyEntity entity = new ToyEntity();

        if (model.getId() != null) {
            entity.setId(model.getId());
        }

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("toyroomPU");
        EntityManager em = emf.createEntityManager();

        TypeRepository typeRepo = new TypeRepository(em);
        Type typeEntity = typeRepo.findByName(model.getType());
        entity.setType(typeEntity);

        ToyRoomRepository toyRoomRepo = new ToyRoomRepository(em);
        Long roomId = model.getRoomId();
        if (roomId == null) {
            throw new IllegalArgumentException("Room ID is null – cannot find ToyRoomEntity.");
        }
        ToyRoomEntity toyRoomEntity = toyRoomRepo.findById(model.getRoomId());
        entity.setToyRoom(toyRoomEntity);

        entity.setSize(model.getSize().name());
        entity.setColor(model.getColor().getHexCode());
        entity.setMaterial(model.getMaterial());
        entity.setPrice(model.getPrice());

        return entity;
    }
}
