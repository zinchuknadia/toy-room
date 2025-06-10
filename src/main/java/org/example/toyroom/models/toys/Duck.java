package org.example.toyroom.models.toys;

import org.example.toyroom.models.Color;
import org.example.toyroom.models.Size;

public class Duck extends Toy {
    public Duck(Size size, Color color, String material) {
        super("duck", size, color, material);
    }

    public Duck() {
    }
}
