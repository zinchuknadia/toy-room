package org.example.toyroom.units;

import org.example.toyroom.models.Toy;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ToyColorComparator implements Comparator<Toy> {
    private static Map<String, Integer> COLOR_ORDER = new HashMap<>();

    static {
        COLOR_ORDER.put("red", 1);
        COLOR_ORDER.put("blue", 2);
        COLOR_ORDER.put("green", 3);
        COLOR_ORDER.put("yellow", 4);
        COLOR_ORDER.put("white", 5);
        COLOR_ORDER.put("gray", 6);
        COLOR_ORDER.put("pink", 7);
        COLOR_ORDER.put("black", 8);
    }

    @Override
    public int compare(Toy toy1, Toy toy2) {
        return Integer.compare(COLOR_ORDER.get(toy1.getColor()), COLOR_ORDER.get(toy2.getColor()));

    }
}
