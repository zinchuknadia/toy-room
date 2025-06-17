package org.example.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "toyroom")
public class ToyRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "theme_id") // foreign key
    private Theme theme;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private double budget;

    @OneToMany(mappedBy = "toyRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ToyEntity> toys = new ArrayList<>();

    // ==== Getters and Setters ====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Theme getTheme() { return theme; }
    public void setTheme(Theme theme) { this.theme = theme; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public double getBudget() { return budget; }
    public void setBudget(double budget) { this.budget = budget; }

    public List<ToyEntity> getToys() { return toys; }
    public void setToys(List<ToyEntity> toys) { this.toys = toys; }
}
