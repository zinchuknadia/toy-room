package org.example.toyroom.models;

import java.util.ArrayList;
import java.util.List;

public class ToyRoomManager {


    private final List<ToyRoom> toyRooms = new ArrayList<>();

    public void addToyRoom(ToyRoom toyRoom) {
        toyRooms.add(toyRoom);
    }

    public void removeToyRoom(ToyRoom toyRoom) {
        toyRooms.remove(toyRoom);
    }

    public List<ToyRoom> getToyRooms() {
        return toyRooms;
    }
}
