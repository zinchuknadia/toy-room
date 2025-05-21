package org.example.toyroom.services;

import org.example.toyroom.models.Color;
import org.example.toyroom.models.Toy;
import org.example.toyroom.repository.ToyRepository;

//import java.awt.*;
import java.util.List;

public class ToyService {
    private final ToyRepository toyRepository;

    public ToyService(ToyRepository toyRepository) {
        this.toyRepository = toyRepository;
    }

    public void addToy(Toy toy) {
        toyRepository.add(toy);
    }

    public void deleteToy(int id) {
        toyRepository.deleteById(id);
    }

    public List<Toy> getAllToys() {
        return toyRepository.findAll();
    }

    public List<Toy> findToysByColor(Color color) {
        return toyRepository.findByColor(color);
    }
}

