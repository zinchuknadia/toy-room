package org.example.toyroom.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "toy")
public class ToyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private String name;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "toyroom_id")
    private ToyRoomEntity toyRoom;

    private String size;
    private String color;
    private String material;


    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }

    public ToyRoomEntity getToyRoom() { return toyRoom; }
    public void setToyRoom(ToyRoomEntity toyRoom) { this.toyRoom = toyRoom; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }
}
