package org.example.toyroom.commands;

import org.example.toyroom.models.Room;

public class SortItem implements Command{

    private Room room;

    public SortItem(Room room){
        this.room = room;
    }

    @Override
    public void execute() {
        room.sort();
    }
}
