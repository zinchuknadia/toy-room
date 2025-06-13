package org.example.toyroom.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.example.toyroom.entity.Type;

import java.util.List;

public class TypeRepository {
    private final EntityManager em;

    public TypeRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Type type) {
        em.getTransaction().begin();
        em.persist(type);
        em.getTransaction().commit();
    }

    public Type findById(Long id) {
        return em.find(Type.class, id);
    }

    public List<Type> findAll() {
        return em.createQuery("SELECT t FROM Type t", Type.class).getResultList();
    }

    public void deleteById(Long id) {
        Type type = em.find(Type.class, id);
        if (type != null) {
            em.getTransaction().begin();
            em.remove(type);
            em.getTransaction().commit();
        }
    }

    public Type findByName(String name) {
        try {
            return em
                    .createQuery("SELECT t FROM Type t WHERE t.name = :name", Type.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // or throw custom exception
        }
    }
}
