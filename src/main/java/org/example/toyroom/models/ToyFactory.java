package org.example.toyroom.models;

import org.example.toyroom.models.toys.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class ToyFactory {
    private static final Map<String, Supplier<Toy>> toySuppliers = new HashMap<>();

    static {
        toySuppliers.put("Doll", () -> new Doll());
        toySuppliers.put("Car", () -> new Car());
        toySuppliers.put("Ball", () -> new Ball());
        toySuppliers.put("Duck", () -> new Duck());
    }

    public static Set<String> getToyTypes() {
        return toySuppliers.keySet();
    }

    public static Toy createToy(String type, Size size, Color color, String material) {
        switch (type) {
            case "Doll": return new Doll(size, color, material);
            case "Car": return new Car(size, color, material);
            case "Ball": return new Ball(size, color, material);
            case "Duck":  return new Duck(size, color, material);
            default: throw new IllegalArgumentException("Unknown toy type: " + type);
        }
    }

    public static double getPrice(String type) {
        switch (type) {
            case "Doll": return 15.0;
            case "Car": return 25.0;
            case "Ball": return 10.0;
            case "Duck": return 5.0;
            default: return 0;
        }
    }
}
