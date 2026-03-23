package com.example.parking;

public class EntryGate {
    private final String gateId;
    private final int floor;
    private final int position;

    public EntryGate(String gateId, int floor, int position) {
        this.gateId = gateId;
        this.floor = floor;
        this.position = position;
    }

    public String getGateId() {
        return gateId;
    }

    public int getFloor() {
        return floor;
    }

    public int getPosition() {
        return position;
    }

    public int distanceTo(ParkingSlot slot) {
        int floorDiff = Math.abs(this.floor - slot.getFloor());
        int posDiff = Math.abs(this.position - slot.getPosition());
        return floorDiff * 100 + posDiff;
    }

    @Override
    public String toString() {
        return gateId + " (floor " + floor + ", pos " + position + ")";
    }
}
