package org.example.toyroom.models.toys;

import org.example.toyroom.models.MyColor;
import org.example.toyroom.models.Size;

public class Ball extends Toy {
    public Ball(Size size, MyColor color, String material) {
        super("ball", size, color, material);
    }

    public Ball() {
    }
}
