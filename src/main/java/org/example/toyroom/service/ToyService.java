package org.example.toyroom.service;

import org.example.entity.ToyEntity;
import org.example.entity.Type;
import org.example.toyroom.mappers.ToyMapper;
import org.example.toyroom.models.ToyRoom;
import org.example.toyroom.models.Toy;
import org.example.repository.ToyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ToyService {
    private static final Logger logger = LoggerFactory.getLogger(ToyService.class);

    private final ToyRepository repository;
    private ToyMapper toyMapper;

    public void setMapper(ToyMapper toyMapper) {
        this.toyMapper = toyMapper;
    }

    public ToyService(ToyRepository repository, ToyMapper toyMapper) {
        this.repository = repository;
        this.toyMapper = toyMapper;
    }

    public void saveToy(Toy toy, Type type) {
        ToyEntity entity = toyMapper.toEntity(toy);
        repository.save(entity);
    }

    public Toy getById(Long id) {
        ToyEntity entity = repository.findById(id);
        Toy toy = toyMapper.toModel(entity);
        return toy;
    }

    public List<Toy> getAllToys() {
        List<Toy> toys = new ArrayList<>();
        List<ToyEntity> entities = repository.findAll();
        for (ToyEntity entity : entities) {
            Toy toy = toyMapper.toModel(entity);
            toys.add(toy);
        }
        return toys;
    }

    public List<Toy> getToysByRoomId(Long toyRoomId) {
        List<ToyEntity> entities = repository.findByToyRoomId(toyRoomId);
        List<Toy> toys = new ArrayList<>();
        for (ToyEntity entity : entities) {
            Toy toy = toyMapper.toModel(entity);
            toys.add(toy);
        }
        return toys;
    }

    public Toy getToyByTypeName(String typeName) {
        ToyEntity toyEntity = repository.findByTypeName(typeName);
        return toyMapper.toModel(toyEntity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean buyToy(Toy toy,ToyRoom room) {
        double currentBudget = room.getBudget();
        if (currentBudget >= toy.getPrice()) {
            repository.save(toyMapper.toEntity(toy));
            room.setBudget(currentBudget - toy.getPrice());
            return true; // success
        } else {
            logger.warn("Toy budget exceeded toy");
            return false; // not enough budget
        }
    }

    public List<Toy> searchAndSortToys(ToyRoom toyRoom, String keyword, List<String> sortCriteria) {
        List<Toy> toys = getToysByRoomId(toyRoom.getId());

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
        if (sortCriteria != null && !sortCriteria.isEmpty()) {
            String crit = sortCriteria.get(0); // only first criterion
            Comparator<Toy> comparator = switch (crit) {
                case "type" -> Comparator.comparing(toy -> toy.getType());
                case "size" -> Comparator.comparing(Toy::getSize);
                case "material" -> Comparator.comparing(Toy::getMaterial);
                case "price" -> Comparator.comparingDouble(Toy::getPrice).reversed();
                default -> null;
            };

            if (comparator != null) {
                toys = toys.stream().sorted(comparator).collect(Collectors.toList());
            }
        }

        // Convert entities to models
        return toys;
//                .map(ToyMapper::toModel)
//                .collect(Collectors.toList());
    }
}
