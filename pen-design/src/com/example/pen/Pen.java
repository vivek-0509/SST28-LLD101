package com.example.pen;

import java.util.Objects;

public abstract class Pen {
    private final String brand;
    private final Nib nib;
    private Refill refill;
    private PenState state;

    protected Pen(String brand, Nib nib, Refill refill) {
        Objects.requireNonNull(brand, "brand must not be null");
        Objects.requireNonNull(nib, "nib must not be null");
        Objects.requireNonNull(refill, "refill must not be null");
        this.brand = brand;
        this.nib = nib;
        this.refill = refill;
        this.state = PenState.CLOSED;
    }

    public void start() {
        if (state == PenState.OPEN) {
            System.out.println(brand + " pen is already open.");
            return;
        }
        state = PenState.OPEN;
        System.out.println(brand + " pen opened. Ready to write.");
    }

    public void write(String text) {
        Objects.requireNonNull(text, "text must not be null");
        if (state != PenState.OPEN) {
            System.out.println("Cannot write. Pen is closed. Call start() first.");
            return;
        }
        if (!refill.hasInk()) {
            System.out.println("Cannot write. Out of ink. Refill the pen.");
            return;
        }
        double inkNeeded = text.length() * getInkConsumptionPerChar();
        refill.getInk().use(inkNeeded);
        System.out.println("[" + refill.getInk().getColor() + ", " + nib.getThickness() + "mm] " + text);
    }

    public void close() {
        if (state == PenState.CLOSED) {
            System.out.println(brand + " pen is already closed.");
            return;
        }
        state = PenState.CLOSED;
        System.out.println(brand + " pen closed.");
    }

    public void refill(Refill newRefill) {
        Objects.requireNonNull(newRefill, "refill must not be null");
        if (!canRefill()) {
            System.out.println(brand + " pen does not support refilling.");
            return;
        }
        if (newRefill.getType() != getExpectedRefillType()) {
            System.out.println("Incompatible refill type. Expected " + getExpectedRefillType() + ".");
            return;
        }
        this.refill = newRefill;
        System.out.println(brand + " pen refilled with " + newRefill.getInk().getColor() + " ink.");
    }

    public String getBrand() {
        return brand;
    }

    public PenState getState() {
        return state;
    }

    public Refill getRefill() {
        return refill;
    }

    protected abstract boolean canRefill();

    protected abstract RefillType getExpectedRefillType();

    protected abstract double getInkConsumptionPerChar();
}
