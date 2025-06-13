package org.example.toyroom.repository;

import jakarta.persistence.EntityManager;
import org.example.toyroom.entity.ToyEntity;

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

    public List<ToyEntity> findAll() {
        return em.createQuery("SELECT t FROM ToyEntity t", ToyEntity.class).getResultList();
    }

    public List<ToyEntity> findByToyRoomId(Long toyRoomId) {
        return em.createQuery("SELECT t FROM ToyEntity t WHERE t.toyRoom.id = :toyRoomId", ToyEntity.class)
                .setParameter("toyRoomId", toyRoomId)
                .getResultList();
    }

    public void deleteById(Long id) {
        ToyEntity toy = em.find(ToyEntity.class, id);
        if (toy != null) {
            em.getTransaction().begin();
            em.remove(toy);
            em.getTransaction().commit();
        }
    }
}
