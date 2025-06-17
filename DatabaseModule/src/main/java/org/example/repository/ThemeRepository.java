package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.example.entity.Theme;

import java.util.List;

public class ThemeRepository {
    private final EntityManager em;

    public ThemeRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Theme theme) {
        em.getTransaction().begin();
        em.persist(theme);
        em.getTransaction().commit();
    }

    public Theme findById(Long id) {
        return em.find(Theme.class, id);
    }

    public List<Theme> findAll() {
        return em.createQuery("SELECT t FROM Theme t", Theme.class).getResultList();
    }

    public boolean deleteById(Long id) {
        long toyRoomCount = em.createQuery(
                        "SELECT COUNT(tr) FROM ToyRoomEntity tr WHERE tr.theme.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult();

        if (toyRoomCount > 0) {
            // Warn the user in GUI
            System.out.println("Cannot delete theme: it is used by " + toyRoomCount + " toy room(s)");
            return false;
        }

        em.getTransaction().begin();
        Theme theme = em.find(Theme.class, id);
        em.remove(theme);
        em.getTransaction().commit();
        return true;
    }

    public Theme findByName(String name) {
        try {
            return em
                    .createQuery("SELECT t FROM Theme t WHERE t.name = :name", Theme.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}

