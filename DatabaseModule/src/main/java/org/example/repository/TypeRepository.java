package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.example.entity.Type;

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

    public boolean deleteById(Long id) {
        // Count how many toys use this type
        long toyCount = em.createQuery(
                        "SELECT COUNT(t) FROM ToyEntity t WHERE t.type.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult();

        if (toyCount > 0) {
            System.out.println("Cannot delete type: it is used by " + toyCount + " toy(s)");
            return false;
        }

        em.getTransaction().begin();
        Type type = em.find(Type.class, id);
        if (type != null) {
            em.remove(type);
        }
        em.getTransaction().commit();
        return true;
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
