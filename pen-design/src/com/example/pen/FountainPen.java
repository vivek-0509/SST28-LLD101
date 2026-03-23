package com.example.pen;

public class FountainPen extends Pen {

    public FountainPen(String brand, Nib nib, Refill refill) {
        super(brand, nib, refill);
        if (refill.getType() != RefillType.FOUNTAIN) {
            throw new IllegalArgumentException("FountainPen requires a FOUNTAIN refill");
        }
    }

    @Override
    protected boolean canRefill() {
        return true;
    }

    @Override
    protected RefillType getExpectedRefillType() {
        return RefillType.FOUNTAIN;
    }

    @Override
    protected double getInkConsumptionPerChar() {
        return 0.05;
    }
}
