package org.example.toyroom.service;

import org.example.toyroom.ToyRoom;
import org.example.toyroom.models.MyColor;
import org.example.toyroom.models.Size;
import org.example.toyroom.models.toys.Toy;
import org.example.toyroom.repository.ToyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ToyService {

    private final ToyRepository toyRepository = new ToyRepository();
    private final ToyRoom toyRoom;

    private static final Logger logger = LoggerFactory.getLogger(ToyRepository.class);

    public ToyService(ToyRoom toyRoom) {
//        this.toyRepository = toyRepository;
        this.toyRoom = toyRoom;
    }

    public boolean buyToy(Toy toy) {
        double currentBudget = toyRoom.getBudget();
        if (currentBudget >= toy.getPrice()) {
            toyRepository.add(toy);
            toyRoom.setBudget(currentBudget - toy.getPrice());
            return true; // success
        } else {
            return false; // not enough budget
        }
    }

    public void saveToys(List<Toy> toys) {
        toyRepository.saveToys(toys);
    }

    public List<Toy> findAll() {
        return toyRepository.findAll();
    }

    public List<Toy> findAllSortedByColor() {
        return toyRepository.findAllSortedByColor();
    }

    public List<Toy> findAllSortedBySize() {
        return toyRepository.findAllSortedBySize();
    }

    public List<Toy> findByColor(MyColor color) {
        return toyRepository.findByColor(color);
    }

    public List<Toy> findByType(String type) {
        return toyRepository.findByType(type);
    }

    public List<Toy> findBySize(Size size) {
        return toyRepository.findBySize(size);
    }

    public boolean deleteById(int id) {
        return toyRepository.deleteById(id);
    }
}
