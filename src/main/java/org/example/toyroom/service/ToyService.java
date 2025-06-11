package org.example.toyroom.service;

import org.example.toyroom.ToyRoom;
import org.example.toyroom.models.toys.Toy;
import org.example.toyroom.repository.ToyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ToyService {

    private final ToyRepository toyRepository;
    private final ToyRoom toyRoom;

    private static final Logger logger = LoggerFactory.getLogger(ToyRepository.class);

    public ToyService(ToyRoom toyRoom) {
        this(toyRoom, new ToyRepository());
    }

    public ToyService(ToyRoom toyRoom, ToyRepository toyRepository) {
        this.toyRoom = toyRoom;
        this.toyRepository = toyRepository;
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

    public List<Toy> getAllToys() {
        return toyRepository.findAll();
    }

    public boolean deleteToy(int id) {
        return toyRepository.deleteById(id);
    }

    public List<Toy> searchAndSortToys(String keyword, List<String> sortCriteria) {
        List<Toy> toys = toyRepository.findAll();

        // Filter
        if (keyword != null && !keyword.isBlank()) {
            String lowerKeyword = keyword.toLowerCase();
            toys = toys.stream()
                    .filter(toy -> toy.getSize().toString().toLowerCase().contains(lowerKeyword)
                            || toy.getType().toLowerCase().contains(lowerKeyword)
                            || toy.getMaterial().toLowerCase().contains(lowerKeyword))
                    .collect(Collectors.toList());
        }

        // Sort
        if (!sortCriteria.isEmpty()) {
            String crit = sortCriteria.get(0); // assuming only one selected
            Comparator<Toy> comparator = switch (crit) {
                case "type" -> Comparator.comparing(Toy::getType);
                case "size" -> Comparator.comparing(Toy::getSize);
                case "material" -> Comparator.comparing(Toy::getMaterial);
                case "price" -> Comparator.comparingDouble(Toy::getPrice).reversed();
                default -> null;
            };

            if (comparator != null) {
                toys = toys.stream().sorted(comparator).collect(Collectors.toList());
            }
        }

        return toys;
    }
}
