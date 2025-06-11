package org.example.toyroom;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.example.toyroom.models.*;
import org.example.toyroom.models.toys.Toy;
import org.example.toyroom.service.ToyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

public class ToyRoom {
    private static final Logger logger = LoggerFactory.getLogger(ToyRoom.class);

    private final ToyService toyService;
    private final DoubleProperty budget = new SimpleDoubleProperty();

    public ToyRoom() {
        this.toyService = new ToyService(this);
        this.budget.set(0.0);
    }

    public ToyRoom(double initialBudget) {
        this.toyService = new ToyService(this);
        this.budget.set(initialBudget);
    }

    public ToyRoom(ToyService toyService, double initialBudget) {
        this.toyService = toyService;
        this.budget.set(initialBudget);
    }

    public ToyRoom(ToyService toyService) {
        this.toyService = toyService;
        this.budget.set(0.0);
    }

    public ToyService getToyService() {
        return toyService;
    }

    public DoubleProperty budgetProperty() {
        return budget;
    }

    public double getBudget() {
        return budget.get();
    }

    public void setBudget(double value) {
        budget.set(value);
    }

    public static Size parseSize(String sizeStr){
        switch(sizeStr.toLowerCase()){
            case "large": return Size.LARGE;
            case "medium": return Size.MEDIUM;
            case "small": return Size.SMALL;
            default: {
                logger.error("Invalid size: {}", sizeStr);
                throw new IllegalArgumentException("Invalid Size: " + sizeStr);
            }
        }
    }
}
