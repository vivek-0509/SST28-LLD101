package com.example.snakesladders;

import java.util.Objects;

public class Player {
    private final String name;
    private int position;
    private boolean hasWon;

    public Player(String name) {
        Objects.requireNonNull(name, "name must not be null");
        this.name = name;
        this.position = 0;
        this.hasWon = false;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean hasWon() {
        return hasWon;
    }

    public void markWon() {
        this.hasWon = true;
    }

    @Override
    public String toString() {
        return name + " (at " + position + ")";
    }
}
