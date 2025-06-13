package org.example.toyroom.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.example.toyroom.entity.Theme;
import org.example.toyroom.entity.Type;

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

    public void deleteById(Long id) {
        Theme theme = em.find(Theme.class, id);
        if (theme != null) {
            em.getTransaction().begin();
            em.remove(theme);
            em.getTransaction().commit();
        }
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

