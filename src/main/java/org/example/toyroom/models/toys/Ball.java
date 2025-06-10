package org.example.toyroom.models.toys;

import org.example.toyroom.models.Color;
import org.example.toyroom.models.Size;

public class Ball extends Toy {
    public Ball(Size size, Color color, String material) {
        super("ball", size, color, material);
    }

    public Ball() {
    }
}
