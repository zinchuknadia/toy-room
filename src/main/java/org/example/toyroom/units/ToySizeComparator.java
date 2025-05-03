package org.example.toyroom.units;

import org.example.toyroom.models.Toy;

import java.util.Comparator;

public class ToySizeComparator implements Comparator<Toy> {
    @Override
    public int compare(Toy toy1, Toy toy2) {
        return toy1.getSize().compareTo(toy2.getSize());
    }
}
