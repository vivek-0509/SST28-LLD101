package com.example.parking;

import java.time.LocalDateTime;

public class TestRunner {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        testSlotTypeRates();
        testVehicleCreation();
        testVehicleNullValidation();
        testParkingSlotOccupyAndFree();
        testEntryGateDistance();
        testSlotCompatibilityTwoWheeler();
        testSlotCompatibilityCar();
        testSlotCompatibilityBus();
        testBillCalculationExactHours();
        testBillCalculationPartialHour();
        testBillCalculationLessThanOneHour();
        testBillBasedOnSlotTypeNotVehicleType();
        testParkBasicFlow();
        testParkAssignsNearestSlot();
        testParkReturnsNullWhenFull();
        testParkIncompatibleSlotThrows();
        testParkUnknownGateThrows();
        testExitInvalidTicketThrows();
        testExitBeforeEntryThrows();
        testExitFreesSlot();
        testDoubleExitThrows();
        testBikeInMediumSlot();
        testBikeInLargeSlot();
        testCarInLargeSlot();
        testBusCannotParkInSmall();
        testBusCannotParkInMedium();
        testFallbackToLargerSlot();
        testMultipleFloorsNearestGate();
        testStatusReflectsChanges();
        testParkingFloorCounts();
        testTicketContainsCorrectInfo();
        testBillContainsCorrectInfo();
        testManyVehiclesParkAndExit();
        testZeroDurationBill();

        System.out.println();
        System.out.println("========================================");
        System.out.println("Results: " + passed + " passed, " + failed + " failed, " + (passed + failed) + " total");
        if (failed == 0) {
            System.out.println("All tests passed.");
        }
        System.out.println("========================================");
    }

    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("PASS: " + name);
        } else {
            failed++;
            System.out.println("FAIL: " + name);
        }
    }

    private static void testSlotTypeRates() {
        check("SMALL rate is 30", SlotType.SMALL.getHourlyRate() == 30);
        check("MEDIUM rate is 50", SlotType.MEDIUM.getHourlyRate() == 50);
        check("LARGE rate is 100", SlotType.LARGE.getHourlyRate() == 100);
    }

    private static void testVehicleCreation() {
        Vehicle v = new Vehicle("KA-01-1234", VehicleType.CAR);
        check("Vehicle plate is KA-01-1234", v.getLicensePlate().equals("KA-01-1234"));
        check("Vehicle type is CAR", v.getType() == VehicleType.CAR);
    }

    private static void testVehicleNullValidation() {
        try {
            new Vehicle(null, VehicleType.CAR);
            check("Vehicle with null plate throws", false);
        } catch (NullPointerException e) {
            check("Vehicle with null plate throws", true);
        }
        try {
            new Vehicle("ABC", null);
            check("Vehicle with null type throws", false);
        } catch (NullPointerException e) {
            check("Vehicle with null type throws", true);
        }
    }

    private static void testParkingSlotOccupyAndFree() {
        ParkingSlot slot = new ParkingSlot("S1", SlotType.SMALL, 1, 1);
        check("Slot starts unoccupied", !slot.isOccupied());
        slot.occupy();
        check("Slot is occupied after occupy()", slot.isOccupied());
        slot.free();
        check("Slot is free after free()", !slot.isOccupied());
    }

    private static void testEntryGateDistance() {
        EntryGate gate = new EntryGate("G1", 1, 0);
        ParkingSlot sameFloor = new ParkingSlot("S1", SlotType.SMALL, 1, 5);
        ParkingSlot diffFloor = new ParkingSlot("S2", SlotType.SMALL, 3, 0);
        check("Same floor distance is position diff", gate.distanceTo(sameFloor) == 5);
        check("Different floor distance includes floor weight", gate.distanceTo(diffFloor) == 200);

        ParkingSlot both = new ParkingSlot("S3", SlotType.SMALL, 2, 3);
        check("Combined floor + position distance", gate.distanceTo(both) == 103);
    }

    private static void testSlotCompatibilityTwoWheeler() {
        check("Bike fits in SMALL", SlotCompatibility.isCompatible(VehicleType.TWO_WHEELER, SlotType.SMALL));
        check("Bike fits in MEDIUM", SlotCompatibility.isCompatible(VehicleType.TWO_WHEELER, SlotType.MEDIUM));
        check("Bike fits in LARGE", SlotCompatibility.isCompatible(VehicleType.TWO_WHEELER, SlotType.LARGE));
    }

    private static void testSlotCompatibilityCar() {
        check("Car does not fit in SMALL", !SlotCompatibility.isCompatible(VehicleType.CAR, SlotType.SMALL));
        check("Car fits in MEDIUM", SlotCompatibility.isCompatible(VehicleType.CAR, SlotType.MEDIUM));
        check("Car fits in LARGE", SlotCompatibility.isCompatible(VehicleType.CAR, SlotType.LARGE));
    }

    private static void testSlotCompatibilityBus() {
        check("Bus does not fit in SMALL", !SlotCompatibility.isCompatible(VehicleType.BUS, SlotType.SMALL));
        check("Bus does not fit in MEDIUM", !SlotCompatibility.isCompatible(VehicleType.BUS, SlotType.MEDIUM));
        check("Bus fits in LARGE", SlotCompatibility.isCompatible(VehicleType.BUS, SlotType.LARGE));
    }

    private static void testBillCalculationExactHours() {
        ParkingSlot slot = new ParkingSlot("S1", SlotType.MEDIUM, 1, 1);
        slot.occupy();
        LocalDateTime entry = LocalDateTime.of(2026, 1, 1, 10, 0);
        ParkingTicket ticket = new ParkingTicket("T-1", new Vehicle("X", VehicleType.CAR), slot, entry);
        Bill bill = new Bill(ticket, entry.plusHours(3));
        check("3 hours in MEDIUM = 150", bill.getAmount() == 150);
        check("Duration is 3 hours", bill.getDurationHours() == 3);
    }

    private static void testBillCalculationPartialHour() {
        ParkingSlot slot = new ParkingSlot("S1", SlotType.SMALL, 1, 1);
        slot.occupy();
        LocalDateTime entry = LocalDateTime.of(2026, 1, 1, 10, 0);
        ParkingTicket ticket = new ParkingTicket("T-1", new Vehicle("X", VehicleType.TWO_WHEELER), slot, entry);
        Bill bill = new Bill(ticket, entry.plusHours(2).plusMinutes(15));
        check("2h15m rounds up to 3h in SMALL = 90", bill.getAmount() == 90);
        check("Duration rounds up to 3 hours", bill.getDurationHours() == 3);
    }

    private static void testBillCalculationLessThanOneHour() {
        ParkingSlot slot = new ParkingSlot("S1", SlotType.LARGE, 1, 1);
        slot.occupy();
        LocalDateTime entry = LocalDateTime.of(2026, 1, 1, 10, 0);
        ParkingTicket ticket = new ParkingTicket("T-1", new Vehicle("X", VehicleType.BUS), slot, entry);
        Bill bill = new Bill(ticket, entry.plusMinutes(20));
        check("20 min rounds up to 1h in LARGE = 100", bill.getAmount() == 100);
        check("Duration rounds up to 1 hour", bill.getDurationHours() == 1);
    }

    private static void testBillBasedOnSlotTypeNotVehicleType() {
        ParkingSlot medSlot = new ParkingSlot("M1", SlotType.MEDIUM, 1, 1);
        medSlot.occupy();
        LocalDateTime entry = LocalDateTime.of(2026, 1, 1, 10, 0);
        ParkingTicket ticket = new ParkingTicket("T-1",
                new Vehicle("BIKE-1", VehicleType.TWO_WHEELER), medSlot, entry);
        Bill bill = new Bill(ticket, entry.plusHours(2));
        check("Bike in MEDIUM slot billed at MEDIUM rate: 2h * 50 = 100", bill.getAmount() == 100);

        ParkingSlot lgSlot = new ParkingSlot("L1", SlotType.LARGE, 1, 1);
        lgSlot.occupy();
        ParkingTicket ticket2 = new ParkingTicket("T-2",
                new Vehicle("CAR-1", VehicleType.CAR), lgSlot, entry);
        Bill bill2 = new Bill(ticket2, entry.plusHours(1));
        check("Car in LARGE slot billed at LARGE rate: 1h * 100 = 100", bill2.getAmount() == 100);
    }

    private static ParkingLot buildTestLot() {
        ParkingLot lot = new ParkingLot("Test Lot");

        ParkingFloor f1 = new ParkingFloor(1);
        f1.addSlot(new ParkingSlot("F1-S1", SlotType.SMALL, 1, 1));
        f1.addSlot(new ParkingSlot("F1-S2", SlotType.SMALL, 1, 2));
        f1.addSlot(new ParkingSlot("F1-M1", SlotType.MEDIUM, 1, 3));
        f1.addSlot(new ParkingSlot("F1-M2", SlotType.MEDIUM, 1, 4));
        f1.addSlot(new ParkingSlot("F1-L1", SlotType.LARGE, 1, 5));

        ParkingFloor f2 = new ParkingFloor(2);
        f2.addSlot(new ParkingSlot("F2-S1", SlotType.SMALL, 2, 1));
        f2.addSlot(new ParkingSlot("F2-M1", SlotType.MEDIUM, 2, 2));
        f2.addSlot(new ParkingSlot("F2-L1", SlotType.LARGE, 2, 3));

        lot.addFloor(f1);
        lot.addFloor(f2);
        lot.addGate(new EntryGate("G1", 1, 0));
        lot.addGate(new EntryGate("G2", 2, 0));
        return lot;
    }

    private static void testParkBasicFlow() {
        ParkingLot lot = buildTestLot();
        LocalDateTime now = LocalDateTime.of(2026, 1, 1, 10, 0);
        Vehicle car = new Vehicle("C-1", VehicleType.CAR);
        ParkingTicket ticket = lot.park(car, now, SlotType.MEDIUM, "G1");
        check("Park returns non-null ticket", ticket != null);
        check("Ticket has correct vehicle", ticket.getVehicle() == car);
        check("Ticket slot is MEDIUM", ticket.getSlot().getType() == SlotType.MEDIUM);
    }

    private static void testParkAssignsNearestSlot() {
        ParkingLot lot = buildTestLot();
        LocalDateTime now = LocalDateTime.of(2026, 1, 1, 10, 0);

        Vehicle bike = new Vehicle("B-1", VehicleType.TWO_WHEELER);
        ParkingTicket t1 = lot.park(bike, now, SlotType.SMALL, "G1");
        check("Bike from G1 gets F1-S1 (nearest small)", t1.getSlot().getSlotId().equals("F1-S1"));

        Vehicle bike2 = new Vehicle("B-2", VehicleType.TWO_WHEELER);
        ParkingTicket t2 = lot.park(bike2, now, SlotType.SMALL, "G2");
        check("Bike from G2 gets F2-S1 (nearest small to G2)", t2.getSlot().getSlotId().equals("F2-S1"));

        Vehicle bike3 = new Vehicle("B-3", VehicleType.TWO_WHEELER);
        ParkingTicket t3 = lot.park(bike3, now, SlotType.SMALL, "G1");
        check("Third bike from G1 gets F1-S2 (next nearest)", t3.getSlot().getSlotId().equals("F1-S2"));
    }

    private static void testParkReturnsNullWhenFull() {
        ParkingLot lot = new ParkingLot("Tiny Lot");
        ParkingFloor f = new ParkingFloor(1);
        f.addSlot(new ParkingSlot("S1", SlotType.LARGE, 1, 1));
        lot.addFloor(f);
        lot.addGate(new EntryGate("G1", 1, 0));

        LocalDateTime now = LocalDateTime.of(2026, 1, 1, 10, 0);
        lot.park(new Vehicle("B1", VehicleType.BUS), now, SlotType.LARGE, "G1");
        ParkingTicket t = lot.park(new Vehicle("B2", VehicleType.BUS), now, SlotType.LARGE, "G1");
        check("Park returns null when lot is full", t == null);
    }

    private static void testParkIncompatibleSlotThrows() {
        ParkingLot lot = buildTestLot();
        LocalDateTime now = LocalDateTime.of(2026, 1, 1, 10, 0);
        try {
            lot.park(new Vehicle("B1", VehicleType.BUS), now, SlotType.SMALL, "G1");
            check("Bus in SMALL slot throws", false);
        } catch (IllegalArgumentException e) {
            check("Bus in SMALL slot throws", true);
        }
        try {
            lot.park(new Vehicle("B1", VehicleType.BUS), now, SlotType.MEDIUM, "G1");
            check("Bus in MEDIUM slot throws", false);
        } catch (IllegalArgumentException e) {
            check("Bus in MEDIUM slot throws", true);
        }
        try {
            lot.park(new Vehicle("C1", VehicleType.CAR), now, SlotType.SMALL, "G1");
            check("Car in SMALL slot throws", false);
        } catch (IllegalArgumentException e) {
            check("Car in SMALL slot throws", true);
        }
    }

    private static void testParkUnknownGateThrows() {
        ParkingLot lot = buildTestLot();
        LocalDateTime now = LocalDateTime.of(2026, 1, 1, 10, 0);
        try {
            lot.park(new Vehicle("C1", VehicleType.CAR), now, SlotType.MEDIUM, "G999");
            check("Unknown gate throws", false);
        } catch (IllegalArgumentException e) {
            check("Unknown gate throws", true);
        }
    }

    private static void testExitInvalidTicketThrows() {
        ParkingLot lot = buildTestLot();
        try {
            lot.exit("FAKE-TICKET", LocalDateTime.of(2026, 1, 1, 12, 0));
            check("Exit with invalid ticket throws", false);
        } catch (IllegalArgumentException e) {
            check("Exit with invalid ticket throws", true);
        }
    }

    private static void testExitBeforeEntryThrows() {
        ParkingLot lot = buildTestLot();
        LocalDateTime now = LocalDateTime.of(2026, 1, 1, 10, 0);
        ParkingTicket t = lot.park(new Vehicle("C1", VehicleType.CAR), now, SlotType.MEDIUM, "G1");
        try {
            lot.exit(t.getTicketId(), now.minusHours(1));
            check("Exit before entry throws", false);
        } catch (IllegalArgumentException e) {
            check("Exit before entry throws", true);
        }
    }

    private static void testExitFreesSlot() {
        ParkingLot lot = buildTestLot();
        LocalDateTime now = LocalDateTime.of(2026, 1, 1, 10, 0);
        ParkingTicket t = lot.park(new Vehicle("C1", VehicleType.CAR), now, SlotType.MEDIUM, "G1");
        String slotId = t.getSlot().getSlotId();
        check("Slot is occupied after park", t.getSlot().isOccupied());
        lot.exit(t.getTicketId(), now.plusHours(1));
        check("Slot is free after exit", !t.getSlot().isOccupied());

        ParkingTicket t2 = lot.park(new Vehicle("C2", VehicleType.CAR), now.plusHours(2), SlotType.MEDIUM, "G1");
        check("Freed slot can be reused", t2.getSlot().getSlotId().equals(slotId));
    }

    private static void testDoubleExitThrows() {
        ParkingLot lot = buildTestLot();
        LocalDateTime now = LocalDateTime.of(2026, 1, 1, 10, 0);
        ParkingTicket t = lot.park(new Vehicle("C1", VehicleType.CAR), now, SlotType.MEDIUM, "G1");
        lot.exit(t.getTicketId(), now.plusHours(1));
        try {
            lot.exit(t.getTicketId(), now.plusHours(2));
            check("Double exit throws", false);
        } catch (IllegalArgumentException e) {
            check("Double exit throws", true);
        }
    }

    private static void testBikeInMediumSlot() {
        ParkingLot lot = buildTestLot();
        LocalDateTime now = LocalDateTime.of(2026, 1, 1, 10, 0);
        ParkingTicket t = lot.park(new Vehicle("B1", VehicleType.TWO_WHEELER), now, SlotType.MEDIUM, "G1");
        check("Bike parks in MEDIUM slot", t != null && t.getSlot().getType() == SlotType.MEDIUM);
        Bill bill = lot.exit(t.getTicketId(), now.plusHours(1));
        check("Bike in MEDIUM billed at MEDIUM rate: 50", bill.getAmount() == 50);
    }

    private static void testBikeInLargeSlot() {
        ParkingLot lot = buildTestLot();
        LocalDateTime now = LocalDateTime.of(2026, 1, 1, 10, 0);
        ParkingTicket t = lot.park(new Vehicle("B1", VehicleType.TWO_WHEELER), now, SlotType.LARGE, "G1");
        check("Bike parks in LARGE slot", t != null && t.getSlot().getType() == SlotType.LARGE);
        Bill bill = lot.exit(t.getTicketId(), now.plusHours(1));
        check("Bike in LARGE billed at LARGE rate: 100", bill.getAmount() == 100);
    }

    private static void testCarInLargeSlot() {
        ParkingLot lot = buildTestLot();
        LocalDateTime now = LocalDateTime.of(2026, 1, 1, 10, 0);
        ParkingTicket t = lot.park(new Vehicle("C1", VehicleType.CAR), now, SlotType.LARGE, "G1");
        check("Car parks in LARGE slot", t != null && t.getSlot().getType() == SlotType.LARGE);
        Bill bill = lot.exit(t.getTicketId(), now.plusHours(2));
        check("Car in LARGE billed at LARGE rate: 200", bill.getAmount() == 200);
    }

    private static void testBusCannotParkInSmall() {
        ParkingLot lot = buildTestLot();
        LocalDateTime now = LocalDateTime.of(2026, 1, 1, 10, 0);
        try {
            lot.park(new Vehicle("BUS-1", VehicleType.BUS), now, SlotType.SMALL, "G1");
            check("Bus cannot request SMALL", false);
        } catch (IllegalArgumentException e) {
            check("Bus cannot request SMALL", true);
        }
    }

    private static void testBusCannotParkInMedium() {
        ParkingLot lot = buildTestLot();
        LocalDateTime now = LocalDateTime.of(2026, 1, 1, 10, 0);
        try {
            lot.park(new Vehicle("BUS-1", VehicleType.BUS), now, SlotType.MEDIUM, "G1");
            check("Bus cannot request MEDIUM", false);
        } catch (IllegalArgumentException e) {
            check("Bus cannot request MEDIUM", true);
        }
    }

    private static void testFallbackToLargerSlot() {
        ParkingLot lot = new ParkingLot("Fallback Lot");
        ParkingFloor f = new ParkingFloor(1);
        f.addSlot(new ParkingSlot("M1", SlotType.MEDIUM, 1, 1));
        f.addSlot(new ParkingSlot("L1", SlotType.LARGE, 1, 2));
        lot.addFloor(f);
        lot.addGate(new EntryGate("G1", 1, 0));

        LocalDateTime now = LocalDateTime.of(2026, 1, 1, 10, 0);

        ParkingTicket t1 = lot.park(new Vehicle("B1", VehicleType.TWO_WHEELER), now, SlotType.SMALL, "G1");
        check("Bike requesting SMALL falls back to MEDIUM", t1 != null && t1.getSlot().getType() == SlotType.MEDIUM);

        ParkingTicket t2 = lot.park(new Vehicle("B2", VehicleType.TWO_WHEELER), now, SlotType.SMALL, "G1");
        check("Second bike falls back to LARGE", t2 != null && t2.getSlot().getType() == SlotType.LARGE);

        ParkingTicket t3 = lot.park(new Vehicle("B3", VehicleType.TWO_WHEELER), now, SlotType.SMALL, "G1");
        check("Third bike gets null (all full)", t3 == null);
    }

    private static void testMultipleFloorsNearestGate() {
        ParkingLot lot = new ParkingLot("Multi Gate");
        ParkingFloor f1 = new ParkingFloor(1);
        f1.addSlot(new ParkingSlot("F1-M1", SlotType.MEDIUM, 1, 10));
        ParkingFloor f2 = new ParkingFloor(2);
        f2.addSlot(new ParkingSlot("F2-M1", SlotType.MEDIUM, 2, 1));
        lot.addFloor(f1);
        lot.addFloor(f2);
        lot.addGate(new EntryGate("G1", 1, 0));
        lot.addGate(new EntryGate("G2", 2, 0));

        LocalDateTime now = LocalDateTime.of(2026, 1, 1, 10, 0);
        ParkingTicket t1 = lot.park(new Vehicle("C1", VehicleType.CAR), now, SlotType.MEDIUM, "G1");
        check("Car from G1 gets F1-M1 (same floor)", t1.getSlot().getSlotId().equals("F1-M1"));

        ParkingTicket t2 = lot.park(new Vehicle("C2", VehicleType.CAR), now, SlotType.MEDIUM, "G2");
        check("Car from G2 gets F2-M1 (same floor)", t2.getSlot().getSlotId().equals("F2-M1"));
    }

    private static void testStatusReflectsChanges() {
        ParkingLot lot = buildTestLot();
        LocalDateTime now = LocalDateTime.of(2026, 1, 1, 10, 0);

        String before = lot.status();
        check("Status contains F1 SMALL: 2/2", before.contains("SMALL: 2/2 available"));
        check("Status contains F1 MEDIUM: 2/2", before.contains("MEDIUM: 2/2 available"));

        lot.park(new Vehicle("C1", VehicleType.CAR), now, SlotType.MEDIUM, "G1");
        String after = lot.status();
        check("After parking car, F1 MEDIUM shows 1/2", after.contains("MEDIUM: 1/2 available"));
    }

    private static void testParkingFloorCounts() {
        ParkingFloor f = new ParkingFloor(1);
        f.addSlot(new ParkingSlot("S1", SlotType.SMALL, 1, 1));
        f.addSlot(new ParkingSlot("S2", SlotType.SMALL, 1, 2));
        f.addSlot(new ParkingSlot("M1", SlotType.MEDIUM, 1, 3));

        check("Floor total SMALL = 2", f.countTotal(SlotType.SMALL) == 2);
        check("Floor total MEDIUM = 1", f.countTotal(SlotType.MEDIUM) == 1);
        check("Floor total LARGE = 0", f.countTotal(SlotType.LARGE) == 0);
        check("Floor available SMALL = 2", f.countAvailable(SlotType.SMALL) == 2);

        f.getSlots().get(0).occupy();
        check("Floor available SMALL after occupy = 1", f.countAvailable(SlotType.SMALL) == 1);
    }

    private static void testTicketContainsCorrectInfo() {
        ParkingLot lot = buildTestLot();
        LocalDateTime now = LocalDateTime.of(2026, 1, 1, 10, 30);
        Vehicle v = new Vehicle("KA-99-9999", VehicleType.CAR);
        ParkingTicket t = lot.park(v, now, SlotType.MEDIUM, "G1");

        check("Ticket vehicle matches", t.getVehicle() == v);
        check("Ticket entry time matches", t.getEntryTime().equals(now));
        check("Ticket slot type is MEDIUM", t.getSlot().getType() == SlotType.MEDIUM);
        check("Ticket ID is not empty", !t.getTicketId().isEmpty());
    }

    private static void testBillContainsCorrectInfo() {
        ParkingLot lot = buildTestLot();
        LocalDateTime entry = LocalDateTime.of(2026, 1, 1, 10, 0);
        LocalDateTime exit = LocalDateTime.of(2026, 1, 1, 13, 45);
        ParkingTicket t = lot.park(new Vehicle("C1", VehicleType.CAR), entry, SlotType.MEDIUM, "G1");
        Bill bill = lot.exit(t.getTicketId(), exit);

        check("Bill exit time matches", bill.getExitTime().equals(exit));
        check("Bill duration is 4h (3h45m rounded up)", bill.getDurationHours() == 4);
        check("Bill amount is 4 * 50 = 200", bill.getAmount() == 200);
        check("Bill ticket matches", bill.getTicket() == t);
    }

    private static void testManyVehiclesParkAndExit() {
        ParkingLot lot = buildTestLot();
        LocalDateTime now = LocalDateTime.of(2026, 1, 1, 10, 0);

        ParkingTicket[] tickets = new ParkingTicket[8];
        tickets[0] = lot.park(new Vehicle("B1", VehicleType.TWO_WHEELER), now, SlotType.SMALL, "G1");
        tickets[1] = lot.park(new Vehicle("B2", VehicleType.TWO_WHEELER), now, SlotType.SMALL, "G1");
        tickets[2] = lot.park(new Vehicle("B3", VehicleType.TWO_WHEELER), now, SlotType.SMALL, "G2");
        tickets[3] = lot.park(new Vehicle("C1", VehicleType.CAR), now, SlotType.MEDIUM, "G1");
        tickets[4] = lot.park(new Vehicle("C2", VehicleType.CAR), now, SlotType.MEDIUM, "G1");
        tickets[5] = lot.park(new Vehicle("C3", VehicleType.CAR), now, SlotType.MEDIUM, "G2");
        tickets[6] = lot.park(new Vehicle("BUS1", VehicleType.BUS), now, SlotType.LARGE, "G1");
        tickets[7] = lot.park(new Vehicle("BUS2", VehicleType.BUS), now, SlotType.LARGE, "G2");

        boolean allParked = true;
        for (ParkingTicket t : tickets) {
            if (t == null) { allParked = false; break; }
        }
        check("All 8 vehicles parked successfully", allParked);

        ParkingTicket overflow = lot.park(new Vehicle("BUS3", VehicleType.BUS), now, SlotType.LARGE, "G1");
        check("9th vehicle (bus) gets null (lot full for large)", overflow == null);

        int totalBill = 0;
        for (ParkingTicket t : tickets) {
            Bill b = lot.exit(t.getTicketId(), now.plusHours(2));
            totalBill += b.getAmount();
        }
        check("Total bill for all 8 vehicles is correct",
                totalBill == (3 * 60) + (3 * 100) + (2 * 200));

        check("Active tickets empty after all exits", lot.getActiveTickets().isEmpty());
    }

    private static void testZeroDurationBill() {
        ParkingSlot slot = new ParkingSlot("S1", SlotType.SMALL, 1, 1);
        slot.occupy();
        LocalDateTime entry = LocalDateTime.of(2026, 1, 1, 10, 0);
        ParkingTicket ticket = new ParkingTicket("T-1", new Vehicle("X", VehicleType.TWO_WHEELER), slot, entry);
        Bill bill = new Bill(ticket, entry);
        check("Zero duration bill has 0 amount", bill.getAmount() == 0);
        check("Zero duration is 0 hours", bill.getDurationHours() == 0);
    }
}
