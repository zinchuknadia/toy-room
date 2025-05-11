package org.example.toyroom.units;

import org.example.toyroom.models.Size;
import org.example.toyroom.models.Toy;

import java.util.List;
import java.util.stream.Collectors;

public class ToyFinder {

    public static List<Toy> findToysBySize(List<Toy> toys, Size size){
        return toys.stream()
                .filter(toy -> toy.getSize() == size)
                .collect(Collectors.toList());
    }

    public static List<Toy> findToysByColor(List<Toy> toys, String color){
        return toys.stream()
                .filter(toy -> toy.getColor().getHexCode().equalsIgnoreCase(color))
                .collect(Collectors.toList());
    }

    public static List<Toy> findToysByColorAndSize(List<Toy> toys, String color, Size size, boolean useOr){
        return toys.stream()
                .filter(toy -> useOr
                        ? toy.getColor().getHexCode().equalsIgnoreCase(color) || toy.getSize() == size
                        :toy.getColor().getHexCode().equalsIgnoreCase(color) && toy.getSize() == size)
                .collect(Collectors.toList());
    }
}
