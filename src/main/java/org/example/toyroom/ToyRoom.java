package org.example.toyroom;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.example.toyroom.models.*;
import org.example.toyroom.models.toys.Toy;
import org.example.toyroom.service.ToyService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

public class ToyRoom {

//    private String filePath = "D:\\java_projects\\ToyRoom\\playRoomData.txt";
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

    public void decreaseBudget(double amount) {
        budget.set(budget.get() - amount);
    }

    public void importToysFromFile(String pathToFile) {
        List<Toy> toys = readToysFromFile(pathToFile);
        toyService.saveToys(toys);
    }

    public List<Toy> readToysFromFile(String filePath){
        List<Toy> toys = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] details = line.split(",");
                String type = details[0];
                Size size = parseSize(details[1]);
                String hex = details[2].toLowerCase();
                Color color = new Color(hex);
                String material = details[3];
                Double price = Double.parseDouble(details[4]);
                toys.add(new Toy(type, size, color, material, price));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return toys;
    }

    public static Size parseSize(String sizeStr){
        switch(sizeStr.toLowerCase()){
            case "large": return Size.LARGE;
            case "medium": return Size.MEDIUM;
            case "small": return Size.SMALL;
            default: throw new IllegalArgumentException("Invalid Size: " + sizeStr);
        }
    }
}
