package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.entity.ToyEntity;

import java.util.List;

public class ToyRepository {
    private final EntityManager em;

    public ToyRepository(EntityManager em) {
        this.em = em;
    }

    public void save(ToyEntity toy) {
        em.getTransaction().begin();
        em.persist(toy);
        em.getTransaction().commit();
    }

    public ToyEntity findById(Long id) {
        return em.find(ToyEntity.class, id);
    }

    public ToyEntity findByTypeName(String typeName) {
//        Type type = TypeMapper.toEntity(typeName);
//        long id = type.getId();
        return em.createQuery("SELECT t FROM ToyEntity t WHERE t.type.name = :typeName", ToyEntity.class)
                .setParameter("typeName", typeName)
                .getSingleResult();
    }

    public List<ToyEntity> findAll() {
        return em.createQuery("SELECT t FROM ToyEntity t", ToyEntity.class).getResultList();
    }

    public List<ToyEntity> findByToyRoomId(Long toyRoomId) {
        return em.createQuery("SELECT t FROM ToyEntity t WHERE t.toyRoom.id = :toyRoomId", ToyEntity.class)
                .setParameter("toyRoomId", toyRoomId)
                .getResultList();
    }

    public void deleteById(Long id) {
        em.getTransaction().begin();
        em.createQuery("DELETE FROM ToyEntity t WHERE t.id = :id")
                .setParameter("id", id)
                .executeUpdate();
        em.getTransaction().commit();
    }
}
