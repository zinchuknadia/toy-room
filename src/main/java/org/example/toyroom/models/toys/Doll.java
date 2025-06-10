package org.example.toyroom.models.toys;

import org.example.toyroom.models.Color;
import org.example.toyroom.models.Size;

public class Doll extends Toy {
    public Doll(Size size, Color color, String material) {
        super("doll", size, color, material);
    }

    public Doll() {
    }
}

