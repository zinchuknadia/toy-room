package org.example.toyroom.commands;

import org.example.toyroom.models.Room;

public class FindItem implements Command {

    private Room room;

    public FindItem(Room room){
        this.room = room;
    }

    @Override
    public void execute() {
        room.find();
    }
}
