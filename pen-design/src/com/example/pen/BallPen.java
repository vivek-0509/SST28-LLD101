package com.example.pen;

public class BallPen extends Pen {

    public BallPen(String brand, Nib nib, Refill refill) {
        super(brand, nib, refill);
        if (refill.getType() != RefillType.BALL) {
            throw new IllegalArgumentException("BallPen requires a BALL refill");
        }
    }

    @Override
    protected boolean canRefill() {
        return true;
    }

    @Override
    protected RefillType getExpectedRefillType() {
        return RefillType.BALL;
    }

    @Override
    protected double getInkConsumptionPerChar() {
        return 0.02;
    }
}
