package org.example.toyroom.models;

public enum Size {
    LARGE, MEDIUM, SMALL;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
