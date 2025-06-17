package org.example.toyroom.models;

public class ThemeInfo {
    private Long id;
    private String name;
    private String image;

    public ThemeInfo(){}

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    @Override
    public String toString() {
        return getName();
    }
}