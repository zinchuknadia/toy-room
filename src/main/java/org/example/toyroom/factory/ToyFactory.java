package org.example.toyroom.factory;

import org.example.entity.Type;
import org.example.toyroom.models.MyColor;
import org.example.toyroom.models.Size;
import org.example.toyroom.models.Toy;
import org.example.toyroom.models.TypeInfo;
import org.example.toyroom.service.TypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToyFactory {

    private final TypeService typeService;

    public ToyFactory(TypeService typeService) {
        this.typeService = typeService;
    }

    public Toy createToy(Long id, Long roomId, TypeInfo type, Size size, MyColor color, String material) {
        Toy toy = new Toy(type.getName(), size, color, material);
        toy.setId(id);
        toy.setRoomId(roomId);
        toy.setImagePath(type.getImage());
        toy.setPrice(type.getPrice());
        return toy;
    }

    public Toy createToy(String typeName, Size size, MyColor color, String material) {
        Type type = typeService.getTypeByName(typeName);

        Toy toy = new Toy(type.getName(), size, color, material);
        toy.setImagePath(type.getImage());
        toy.setPrice(type.getPrice());
        return toy;
    }
}
