package org.example.gui.toyRoom;

import org.example.toyroom.models.ToyRoom;
import org.example.toyroom.service.ToyRoomService;
import org.example.toyroom.service.ToyService;

public interface ToyRoomAware {
    void setToyRoomAndService(ToyRoom toyRoom, ToyService toyService);
}