package com.example.parking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParkingFloor {
    private final int floorNumber;
    private final List<ParkingSlot> slots;

    public ParkingFloor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.slots = new ArrayList<>();
    }

    public void addSlot(ParkingSlot slot) {
        slots.add(slot);
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public List<ParkingSlot> getSlots() {
        return Collections.unmodifiableList(slots);
    }

    public int countAvailable(SlotType type) {
        int count = 0;
        for (ParkingSlot slot : slots) {
            if (slot.getType() == type && !slot.isOccupied()) {
                count++;
            }
        }
        return count;
    }

    public int countTotal(SlotType type) {
        int count = 0;
        for (ParkingSlot slot : slots) {
            if (slot.getType() == type) {
                count++;
            }
        }
        return count;
    }
}
