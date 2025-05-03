package org.example.toyroom.commands;

import org.example.toyroom.models.Room;

public class AddItem implements Command {

    private Room room;

    public AddItem(Room room){
        this.room = room;
    }

    @Override
    public void execute() {
        room.add();
    }
}
