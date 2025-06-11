package org.example.toyroom.models.toys;

import org.example.toyroom.models.MyColor;
import org.example.toyroom.models.Size;

public class Duck extends Toy {
    public Duck(Size size, MyColor color, String material) {
        super("duck", size, color, material, "/images/duck.png");
    }

    public Duck() {
    }
}
