package org.example.toyroom.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.toyroom.entity.ToyRoomEntity;

import java.util.List;

public class ToyRoomRepository {
    private final EntityManager em;

    public ToyRoomRepository(EntityManager em) {
        this.em = em;
    }

    public void save(ToyRoomEntity toyRoom) {
        em.getTransaction().begin();
        em.persist(toyRoom);
        em.getTransaction().commit();
    }

    public ToyRoomEntity findById(Long id) {
        return em.find(ToyRoomEntity.class, id);
    }

    public List<ToyRoomEntity> findAll() {
        return em.createQuery("SELECT tr FROM ToyRoomEntity tr", ToyRoomEntity.class).getResultList();
    }

    public void deleteById(Long id) {
        ToyRoomEntity entity = em.find(ToyRoomEntity.class, id);
        if (entity != null) {
            em.getTransaction().begin();
            em.remove(entity);
            em.getTransaction().commit();
        }
    }

    public void updateBudget(Long id, double newBudget) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            ToyRoomEntity room = em.find(ToyRoomEntity.class, id);
            if (room != null) {
                room.setBudget(newBudget);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }
}
