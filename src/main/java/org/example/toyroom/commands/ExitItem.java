package org.example.toyroom.commands;

import org.example.toyroom.models.Room;

public class ExitItem implements Command {

    private Room room;

    public ExitItem(Room room){
        this.room = room;
    }

    @Override
    public void execute() {
        room.exit();
    }
}
