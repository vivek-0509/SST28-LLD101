package com.example.pen;

import java.util.Objects;

public class Refill {
    private final RefillType type;
    private final Ink ink;

    public Refill(RefillType type, Ink ink) {
        Objects.requireNonNull(type, "refill type must not be null");
        Objects.requireNonNull(ink, "ink must not be null");
        this.type = type;
        this.ink = ink;
    }

    public RefillType getType() {
        return type;
    }

    public Ink getInk() {
        return ink;
    }

    public boolean hasInk() {
        return ink.hasInk();
    }
}
