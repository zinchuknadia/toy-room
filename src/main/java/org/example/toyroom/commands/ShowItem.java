package org.example.toyroom.commands;

import org.example.toyroom.models.Room;

public class ShowItem implements Command{

    private Room room;

    public ShowItem(Room room){
        this.room = room;
    }

    @Override
    public void execute() {
        room.show();
    }
}
