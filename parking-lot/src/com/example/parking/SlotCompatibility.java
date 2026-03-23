package com.example.parking;

import java.util.Arrays;
import java.util.List;

public class SlotCompatibility {

    public static List<SlotType> compatibleSlots(VehicleType vehicleType) {
        switch (vehicleType) {
            case TWO_WHEELER:
                return Arrays.asList(SlotType.SMALL, SlotType.MEDIUM, SlotType.LARGE);
            case CAR:
                return Arrays.asList(SlotType.MEDIUM, SlotType.LARGE);
            case BUS:
                return Arrays.asList(SlotType.LARGE);
            default:
                throw new IllegalArgumentException("unknown vehicle type: " + vehicleType);
        }
    }

    public static boolean isCompatible(VehicleType vehicleType, SlotType slotType) {
        return compatibleSlots(vehicleType).contains(slotType);
    }
}
