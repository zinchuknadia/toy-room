package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.entity.ToyRoomEntity;

import java.util.List;

public class ToyRoomRepository {
    private final EntityManager em;

    public ToyRoomRepository(EntityManager em) {
        this.em = em;
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

    public ToyRoomEntity saveOrUpdate(ToyRoomEntity toyRoom) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            ToyRoomEntity merged = em.merge(toyRoom);
            tx.commit();
            return merged;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

}
