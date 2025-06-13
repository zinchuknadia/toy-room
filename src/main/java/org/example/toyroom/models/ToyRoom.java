package org.example.toyroom.models;

import javafx.beans.property.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class ToyRoom {
    private static final Logger logger = LoggerFactory.getLogger(ToyRoom.class);
//    private final ToyService toyService;

    private Long id;
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty themeName = new SimpleStringProperty();
    private final StringProperty themeImage = new SimpleStringProperty();
    private final DoubleProperty budget = new SimpleDoubleProperty();
    private final ObjectProperty<LocalDateTime> createdAt = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> updatedAt = new SimpleObjectProperty<>();

//    private final ToyService toyService;

    public ToyRoom() {
//        this.toyService = new ToyService(this);
        this.budget.set(0.0);
    }

    public ToyRoom(double initialBudget) {
//        this.toyService = new ToyService(this);
        this.budget.set(initialBudget);
    }

    public ToyRoom(String name, String themeName, double budget) {
        this.name.set(name);
        this.themeName.set(themeName);
        this.budget.set(budget);
        this.createdAt.set(LocalDateTime.now());
        this.updatedAt.set(LocalDateTime.now());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getThemeName() {
        return themeName.get();
    }

    public void setThemeName(String themeName) {
        this.themeName.set(themeName);
    }

    public StringProperty themeNameProperty() {
        return themeName;
    }

    public String getThemeImage() {
        return themeImage.get();
    }

    public void setThemeImage(String themeImage) {
        this.themeImage.set(themeImage);
    }

    public StringProperty themeImageProperty() {
        return themeImage;
    }

    public double getBudget() {
        return budget.get();
    }

    public void setBudget(double budget) {
        this.budget.set(budget);
    }

    public DoubleProperty budgetProperty() {
        return budget;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt.get();
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt.set(createdAt);
    }

    public ObjectProperty<LocalDateTime> createdAtProperty() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt.get();
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt.set(updatedAt);
    }

    public ObjectProperty<LocalDateTime> updatedAtProperty() {
        return updatedAt;
    }

//    public ToyService getToyService() {
//        return toyService;
//    }

    @Override
    public String toString() {
        return "ToyRoom: " + getName() + ", Budget: $" + getBudget();
    }
}
