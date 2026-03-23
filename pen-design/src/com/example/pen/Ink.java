package com.example.pen;

import java.util.Objects;

public class Ink {
    private final String color;
    private double remainingMl;

    public Ink(String color, double remainingMl) {
        Objects.requireNonNull(color, "color must not be null");
        if (remainingMl < 0) {
            throw new IllegalArgumentException("ink volume cannot be negative");
        }
        this.color = color;
        this.remainingMl = remainingMl;
    }

    public String getColor() {
        return color;
    }

    public double getRemainingMl() {
        return remainingMl;
    }

    public boolean hasInk() {
        return remainingMl > 0;
    }

    public void use(double amount) {
        if (amount > remainingMl) {
            remainingMl = 0;
        } else {
            remainingMl -= amount;
        }
    }
}
