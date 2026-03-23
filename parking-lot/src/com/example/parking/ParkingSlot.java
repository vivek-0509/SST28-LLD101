package com.example.parking;

public class ParkingSlot {
    private final String slotId;
    private final SlotType type;
    private final int floor;
    private final int position;
    private boolean occupied;

    public ParkingSlot(String slotId, SlotType type, int floor, int position) {
        this.slotId = slotId;
        this.type = type;
        this.floor = floor;
        this.position = position;
        this.occupied = false;
    }

    public String getSlotId() {
        return slotId;
    }

    public SlotType getType() {
        return type;
    }

    public int getFloor() {
        return floor;
    }

    public int getPosition() {
        return position;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void occupy() {
        this.occupied = true;
    }

    public void free() {
        this.occupied = false;
    }

    @Override
    public String toString() {
        return slotId + " (" + type + ", floor " + floor + ", pos " + position + ")";
    }
}
