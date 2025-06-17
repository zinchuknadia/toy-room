package org.example.toyroom.service;

import org.example.entity.Theme;
import org.example.entity.ToyRoomEntity;
import org.example.toyroom.mappers.ToyRoomMapper;
import org.example.toyroom.models.ToyRoom;
import org.example.repository.ToyRoomRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ToyRoomService {
    private final ToyRoomRepository repository;
    private ToyRoomMapper mapper;

    public void setMapper(ToyRoomMapper toyRoomMapper) {
        this.mapper = toyRoomMapper;
    }

    public ToyRoomService(ToyRoomRepository repository, ToyRoomMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void createToyRoom(String name, Theme theme, double budget) {
        ToyRoomEntity room = new ToyRoomEntity();
        room.setName(name);
        room.setTheme(theme);
        room.setBudget(budget);
        room.setCreatedAt(LocalDateTime.now());
        room.setUpdatedAt(LocalDateTime.now());
        repository.saveOrUpdate(room);
    }

    public void saveToyRoom(ToyRoom room) {
        ToyRoomEntity toyRoomEntity = mapper.toEntity(room);
        repository.saveOrUpdate(toyRoomEntity);
    }

    public ToyRoomEntity getById(Long id) {
        return repository.findById(id);
    }

    public List<ToyRoom> getAll() {
        List<ToyRoomEntity> toyRoomEntities = repository.findAll();
        List<ToyRoom> toyRooms= new ArrayList<>();
        for (ToyRoomEntity room : toyRoomEntities) {
            ToyRoom toyRoom = mapper.toModel(room);
            toyRooms.add(toyRoom);
        }
        return toyRooms;
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public void updateBudget(ToyRoom toyRoom) {
        repository.updateBudget(toyRoom.getId(), toyRoom.getBudget());
    }

    public void updateUpdatedAt(Long id) {
        ToyRoomEntity toyRoom = repository.findById(id);
        if (toyRoom != null) {
            toyRoom.setUpdatedAt(LocalDateTime.now());
            repository.saveOrUpdate(toyRoom);
        }
    }

    public void updateToyRoom(ToyRoom toyRoom) {
        ToyRoomEntity entity = mapper.toEntity(toyRoom);
        entity.setUpdatedAt(LocalDateTime.now());
        repository.saveOrUpdate(entity);
    }

    public List<ToyRoom> getSortedToyRooms(List<String> sortCriteria) {
        List<ToyRoom> rooms = getAll();

        // Sort
        if (sortCriteria != null && !sortCriteria.isEmpty()) {
            String crit = sortCriteria.get(0); // only first criterion
            Comparator<ToyRoom> comparator = switch (crit) {
//                case "name" -> Comparator.comparing(ToyRoom::getName);
                case "size" -> Comparator.comparingInt((ToyRoom room) -> room.getToys().size()).reversed();
//                case "theme" -> Comparator.comparing(ToyRoom::getThemeName);
                case "budget" -> Comparator.comparingDouble(ToyRoom::getBudget).reversed();
                case "last modified" -> Comparator.comparing(ToyRoom::getUpdatedAt).reversed();
                default -> null;
            };

            if (comparator != null) {
                rooms = rooms.stream().sorted(comparator).collect(Collectors.toList());
            }
        }

        // Convert entities to models
        return rooms;
//                .map(ToyMapper::toModel)
//                .collect(Collectors.toList());
    }

}
