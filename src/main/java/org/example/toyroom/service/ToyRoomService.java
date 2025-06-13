package org.example.toyroom.service;

import org.example.toyroom.entity.Theme;
import org.example.toyroom.entity.ToyRoomEntity;
import org.example.toyroom.mapper.ToyRoomMapper;
import org.example.toyroom.models.ToyRoom;
import org.example.toyroom.repository.ToyRoomRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ToyRoomService {
    private final ToyRoomRepository repository;

    public ToyRoomService(ToyRoomRepository repository) {
        this.repository = repository;
    }

    public void createToyRoom(String name, Theme theme, double budget) {
        ToyRoomEntity room = new ToyRoomEntity();
        room.setName(name);
        room.setTheme(theme);
        room.setBudget(budget);
        room.setCreatedAt(LocalDateTime.now());
        room.setUpdatedAt(LocalDateTime.now());
        repository.save(room);
    }

    public void saveToyRoom(ToyRoom room) {
        ToyRoomEntity toyRoomEntity = ToyRoomMapper.toEntity(room);
        repository.save(toyRoomEntity);
    }

    public ToyRoomEntity getById(Long id) {
        return repository.findById(id);
    }

    public List<ToyRoom> getAll() {
        List<ToyRoomEntity> toyRoomEntities = repository.findAll();
        List<ToyRoom> toyRooms= new ArrayList<>();
        for (ToyRoomEntity room : toyRoomEntities) {
            ToyRoom toyRoom = ToyRoomMapper.toModel(room);
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
            repository.save(toyRoom);
        }
    }

}
