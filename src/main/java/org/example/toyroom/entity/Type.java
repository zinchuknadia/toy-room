package org.example.toyroom.entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "type")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;    // e.g. "Plush", "Educational"
    private String image;   // Path or URL to icon/image

    // Getters and setters
    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}
