package org.example.toyroom.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ToyRoomManagerTest {

    @Test
    void testAddToyRoom() {
        ToyRoomManager manager = new ToyRoomManager();
        ToyRoom room = new ToyRoom();
        manager.addToyRoom(room);

        assertTrue(manager.getToyRooms().contains(room));
    }

    @Test
    void testRemoveToyRoom() {
        ToyRoomManager manager = new ToyRoomManager();
        ToyRoom room = new ToyRoom();
        manager.addToyRoom(room);
        manager.removeToyRoom(room);

        assertFalse(manager.getToyRooms().contains(room));
    }

    @Test
    void testGetToyRooms() {
        ToyRoomManager manager = new ToyRoomManager();
        ToyRoom room1 = new ToyRoom();
        ToyRoom room2 = new ToyRoom();
        manager.addToyRoom(room1);
        manager.addToyRoom(room2);

        assertEquals(2, manager.getToyRooms().size());
        assertTrue(manager.getToyRooms().contains(room1));
        assertTrue(manager.getToyRooms().contains(room2));
    }
}
