package com.example.parking;

public enum SlotType {
    SMALL(30),
    MEDIUM(50),
    LARGE(100);

    private final int hourlyRate;

    SlotType(int hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public int getHourlyRate() {
        return hourlyRate;
    }
}
