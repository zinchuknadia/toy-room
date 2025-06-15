package org.example.toyroom.mapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.toyroom.entity.Type;
import org.example.toyroom.models.TypeInfo;
import org.example.toyroom.repository.TypeRepository;
import org.example.toyroom.service.TypeService;

public class TypeMapper {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("toyroomPU");
    static EntityManager em = emf.createEntityManager();
    static TypeService typeService = new TypeService(new TypeRepository(em));

    public static TypeInfo toModel(Type entity) {
        TypeInfo dto = new TypeInfo();
        dto.setName(entity.getName());
        dto.setImage(entity.getImage());
        dto.setPrice(entity.getPrice());
        return dto;
    }

    public static Type toEntity(String type) {
        return typeService.getTypeByName(type);
    }
}
