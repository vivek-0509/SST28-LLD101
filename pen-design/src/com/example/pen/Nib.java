package com.example.pen;

public class Nib {
    private final double thickness;

    public Nib(double thickness) {
        if (thickness <= 0) {
            throw new IllegalArgumentException("thickness must be positive");
        }
        this.thickness = thickness;
    }

    public double getThickness() {
        return thickness;
    }
}
