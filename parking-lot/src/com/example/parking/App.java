package com.example.parking;

import java.time.LocalDateTime;

public class App {
    public static void main(String[] args) {
        ParkingLot lot = buildParkingLot();

        LocalDateTime now = LocalDateTime.of(2026, 3, 23, 9, 0);

        System.out.println("=== Initial Status ===");
        System.out.println(lot.status());

        System.out.println("=== Parking Vehicles ===");

        Vehicle bike1 = new Vehicle("KA-01-1111", VehicleType.TWO_WHEELER);
        ParkingTicket t1 = lot.park(bike1, now, SlotType.SMALL, "G1");
        System.out.println("Parked: " + t1);

        Vehicle car1 = new Vehicle("KA-02-2222", VehicleType.CAR);
        ParkingTicket t2 = lot.park(car1, now.plusMinutes(10), SlotType.MEDIUM, "G1");
        System.out.println("Parked: " + t2);

        Vehicle bus1 = new Vehicle("KA-03-3333", VehicleType.BUS);
        ParkingTicket t3 = lot.park(bus1, now.plusMinutes(20), SlotType.LARGE, "G2");
        System.out.println("Parked: " + t3);

        Vehicle bike2 = new Vehicle("KA-04-4444", VehicleType.TWO_WHEELER);
        ParkingTicket t4 = lot.park(bike2, now.plusMinutes(30), SlotType.MEDIUM, "G2");
        System.out.println("Parked (bike in medium): " + t4);

        System.out.println();
        System.out.println("=== Status After Parking ===");
        System.out.println(lot.status());

        System.out.println("=== Exiting Vehicles ===");

        Bill b1 = lot.exit(t1.getTicketId(), now.plusHours(2));
        System.out.println(b1);

        Bill b2 = lot.exit(t2.getTicketId(), now.plusHours(3).plusMinutes(30));
        System.out.println(b2);

        Bill b3 = lot.exit(t3.getTicketId(), now.plusHours(1).plusMinutes(15));
        System.out.println(b3);

        Bill b4 = lot.exit(t4.getTicketId(), now.plusMinutes(45));
        System.out.println(b4);

        System.out.println();
        System.out.println("=== Final Status ===");
        System.out.println(lot.status());
    }

    private static ParkingLot buildParkingLot() {
        ParkingLot lot = new ParkingLot("City Mall Parking");

        ParkingFloor floor1 = new ParkingFloor(1);
        floor1.addSlot(new ParkingSlot("F1-S1", SlotType.SMALL, 1, 1));
        floor1.addSlot(new ParkingSlot("F1-S2", SlotType.SMALL, 1, 2));
        floor1.addSlot(new ParkingSlot("F1-M1", SlotType.MEDIUM, 1, 3));
        floor1.addSlot(new ParkingSlot("F1-M2", SlotType.MEDIUM, 1, 4));
        floor1.addSlot(new ParkingSlot("F1-L1", SlotType.LARGE, 1, 5));

        ParkingFloor floor2 = new ParkingFloor(2);
        floor2.addSlot(new ParkingSlot("F2-S1", SlotType.SMALL, 2, 1));
        floor2.addSlot(new ParkingSlot("F2-M1", SlotType.MEDIUM, 2, 2));
        floor2.addSlot(new ParkingSlot("F2-M2", SlotType.MEDIUM, 2, 3));
        floor2.addSlot(new ParkingSlot("F2-L1", SlotType.LARGE, 2, 4));

        lot.addFloor(floor1);
        lot.addFloor(floor2);

        lot.addGate(new EntryGate("G1", 1, 0));
        lot.addGate(new EntryGate("G2", 2, 0));

        return lot;
    }
}
