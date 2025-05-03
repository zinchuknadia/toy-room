package org.example.toyroom.commands;

import org.example.toyroom.models.Room;

public class DeleteItem implements Command{

    private Room room;

    public DeleteItem(Room room){
        this.room = room;
    }

    @Override
    public void execute() {
        room.delete();
    }
}
