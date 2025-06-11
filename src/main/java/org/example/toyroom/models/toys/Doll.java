package org.example.toyroom.models.toys;

import org.example.toyroom.models.MyColor;
import org.example.toyroom.models.Size;

public class Doll extends Toy {
    public Doll(Size size, MyColor color, String material) {
        super("doll", size, color, material, "/images/doll.png");
    }

    public Doll() {
    }
}

