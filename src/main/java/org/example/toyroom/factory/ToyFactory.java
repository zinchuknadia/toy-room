package org.example.toyroom.factory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.toyroom.entity.Type;
import org.example.toyroom.models.MyColor;
import org.example.toyroom.models.Size;
import org.example.toyroom.models.Toy;
import org.example.toyroom.models.TypeInfo;
import org.example.toyroom.repository.TypeRepository;
import org.example.toyroom.service.TypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToyFactory {
    private static final Logger logger = LoggerFactory.getLogger(ToyFactory.class);

    public static Toy createToy(Long id, Long roomId, TypeInfo type, Size size, MyColor color, String material) {
        Toy toy = new Toy(type.getName(), size, color, material);
        toy.setId(id);
        toy.setRoomId(roomId);
        toy.setImagePath(type.getImage());
        toy.setPrice(type.getPrice());
        return toy;
    }

    public static Toy createToy(String typeName, Size size, MyColor color, String material) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("toyroomPU");
        EntityManager em = emf.createEntityManager();
        TypeService typeService = new TypeService(new TypeRepository(em));
        Type type = typeService.getTypeByName(typeName);

        Toy toy = new Toy(type.getName(), size, color, material);
        toy.setImagePath(type.getImage());
        toy.setPrice(type.getPrice());
        return toy;
    }
}
