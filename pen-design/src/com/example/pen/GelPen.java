package com.example.pen;

public class GelPen extends Pen {

    public GelPen(String brand, Nib nib, Refill refill) {
        super(brand, nib, refill);
        if (refill.getType() != RefillType.GEL) {
            throw new IllegalArgumentException("GelPen requires a GEL refill");
        }
    }

    @Override
    protected boolean canRefill() {
        return true;
    }

    @Override
    protected RefillType getExpectedRefillType() {
        return RefillType.GEL;
    }

    @Override
    protected double getInkConsumptionPerChar() {
        return 0.03;
    }
}
