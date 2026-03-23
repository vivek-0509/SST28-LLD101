package com.example.parking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ParkingLot {
    private final String name;
    private final List<ParkingFloor> floors;
    private final Map<String, EntryGate> gates;
    private final Map<String, ParkingTicket> activeTickets;
    private int ticketCounter;

    public ParkingLot(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        this.floors = new ArrayList<>();
        this.gates = new HashMap<>();
        this.activeTickets = new HashMap<>();
        this.ticketCounter = 0;
    }

    public void addFloor(ParkingFloor floor) {
        floors.add(floor);
    }

    public void addGate(EntryGate gate) {
        gates.put(gate.getGateId(), gate);
    }

    public ParkingTicket park(Vehicle vehicle, LocalDateTime entryTime, SlotType requestedSlotType, String entryGateId) {
        Objects.requireNonNull(vehicle);
        Objects.requireNonNull(entryTime);
        Objects.requireNonNull(requestedSlotType);
        Objects.requireNonNull(entryGateId);

        EntryGate gate = gates.get(entryGateId);
        if (gate == null) {
            throw new IllegalArgumentException("unknown gate: " + entryGateId);
        }

        if (!SlotCompatibility.isCompatible(vehicle.getType(), requestedSlotType)) {
            throw new IllegalArgumentException(vehicle.getType() + " cannot park in " + requestedSlotType + " slot");
        }

        List<SlotType> compatible = SlotCompatibility.compatibleSlots(vehicle.getType());
        int requestedIndex = compatible.indexOf(requestedSlotType);
        List<SlotType> fallbackOrder = compatible.subList(requestedIndex, compatible.size());

        ParkingSlot nearest = findNearestSlot(gate, fallbackOrder);
        if (nearest == null) {
            return null;
        }

        nearest.occupy();
        ticketCounter++;
        String ticketId = "T-" + String.format("%04d", ticketCounter);
        ParkingTicket ticket = new ParkingTicket(ticketId, vehicle, nearest, entryTime);
        activeTickets.put(ticketId, ticket);
        return ticket;
    }

    private ParkingSlot findNearestSlot(EntryGate gate, List<SlotType> slotTypes) {
        ParkingSlot nearest = null;
        int minDistance = Integer.MAX_VALUE;

        for (ParkingFloor floor : floors) {
            for (ParkingSlot slot : floor.getSlots()) {
                if (slot.isOccupied()) continue;
                if (!slotTypes.contains(slot.getType())) continue;

                int distance = gate.distanceTo(slot);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearest = slot;
                }
            }
        }
        return nearest;
    }

    public Bill exit(String ticketId, LocalDateTime exitTime) {
        Objects.requireNonNull(ticketId);
        Objects.requireNonNull(exitTime);

        ParkingTicket ticket = activeTickets.remove(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("no active ticket found: " + ticketId);
        }

        if (exitTime.isBefore(ticket.getEntryTime())) {
            throw new IllegalArgumentException("exit time cannot be before entry time");
        }

        ticket.getSlot().free();
        return new Bill(ticket, exitTime);
    }

    public String status() {
        StringBuilder sb = new StringBuilder();
        sb.append("Parking Lot: ").append(name).append("\n");
        for (ParkingFloor floor : floors) {
            sb.append("  Floor ").append(floor.getFloorNumber()).append(":\n");
            for (SlotType type : SlotType.values()) {
                int available = floor.countAvailable(type);
                int total = floor.countTotal(type);
                if (total > 0) {
                    sb.append("    ").append(type).append(": ")
                            .append(available).append("/").append(total).append(" available\n");
                }
            }
        }
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public Map<String, ParkingTicket> getActiveTickets() {
        return new HashMap<>(activeTickets);
    }
}
