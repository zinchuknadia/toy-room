package org.example.toyroom.models.toys;

import org.example.toyroom.models.Color;
import org.example.toyroom.models.Size;

public class Car extends Toy {
    public Car(Size size, Color color, String material) {
        super("car", size, color, material);
    }

    public Car() {
    }
}
