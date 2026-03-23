package com.example.snakesladders;

import java.util.Random;

public class Dice {
    private final int faces;
    private final Random random;

    public Dice(int faces) {
        if (faces <= 0) {
            throw new IllegalArgumentException("faces must be positive");
        }
        this.faces = faces;
        this.random = new Random();
    }

    public int roll() {
        return random.nextInt(faces) + 1;
    }

    public int getFaces() {
        return faces;
    }
}
