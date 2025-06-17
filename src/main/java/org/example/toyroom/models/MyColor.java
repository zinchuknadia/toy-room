package org.example.toyroom.models;

public class MyColor {
    private final String hexCode;

    public MyColor(String hexCode) {
        this.hexCode = hexCode.startsWith("#") ? hexCode : "#" + hexCode;
    }

    public String getHexCode() {
        return hexCode;
    }

    public java.awt.Color toAwtColor() {
        return java.awt.Color.decode(hexCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyColor)) return false;
        MyColor other = (MyColor) o;
        return hexCode.equals(other.hexCode);
    }

    @Override
    public int hashCode() {
        return hexCode.hashCode();
    }

    @Override
    public String toString() {
        return hexCode;
    }
}