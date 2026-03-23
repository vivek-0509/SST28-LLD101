package com.example.parking;

import java.time.LocalDateTime;
import java.time.Duration;

public class Bill {
    private final ParkingTicket ticket;
    private final LocalDateTime exitTime;
    private final long durationHours;
    private final int amount;

    public Bill(ParkingTicket ticket, LocalDateTime exitTime) {
        this.ticket = ticket;
        this.exitTime = exitTime;

        long minutes = Duration.between(ticket.getEntryTime(), exitTime).toMinutes();
        this.durationHours = (minutes + 59) / 60;

        this.amount = (int) (durationHours * ticket.getSlot().getType().getHourlyRate());
    }

    public ParkingTicket getTicket() {
        return ticket;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public long getDurationHours() {
        return durationHours;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Bill[" + ticket.getTicketId() + "] vehicle=" + ticket.getVehicle().getLicensePlate()
                + " slot=" + ticket.getSlot().getSlotId() + " (" + ticket.getSlot().getType() + ")"
                + " duration=" + durationHours + "h amount=" + amount;
    }
}
