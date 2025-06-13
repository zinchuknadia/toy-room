package org.example.toyroom.factory;

import org.example.toyroom.models.MyColor;
import org.example.toyroom.models.Size;
import org.example.toyroom.models.toys.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class ToyFactory {
    private static final Logger logger = LoggerFactory.getLogger(ToyFactory.class);

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

    public static Toy createToy(String type, Size size, MyColor color, String material) {
        switch (type) {
            case "Doll": return new Doll(size, color, material);
            case "Car": return new Car(size, color, material);
            case "Ball": return new Ball(size, color, material);
            case "Duck":  return new Duck(size, color, material);
            default: {
                logger.warn("Unknown toy type: {}", type);
                throw new IllegalArgumentException("Unknown toy type: " + type);
            }
        }
    }

    public static double getPrice(String type) {
        switch (type) {
            case "Doll": return 15.0;
            case "Car": return 25.0;
            case "Ball": return 10.0;
            case "Duck": return 5.0;
            default: {
                logger.warn("Unknown price type: {}", type);
                return 0;
            }
        }
    }

    public static String getImagePath(String type) {
        switch (type) {
            case "Doll": return "/images/doll.png";
            case "Car": return "/images/car.png";
            case "Ball": return "/images/ball.png";
            case "Duck": return "/images/duck.png";
            default:{
                logger.warn("Unknown image type: {}", type);
                return "";
            }
        }
    }
}
