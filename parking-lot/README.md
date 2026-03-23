# Multilevel Parking Lot

## Class Diagram

```
+-------------------+       +-------------------+
|   <<enum>>        |       |   <<enum>>        |
|   VehicleType     |       |   SlotType        |
|-------------------|       |-------------------|
| TWO_WHEELER       |       | SMALL (30/hr)     |
| CAR               |       | MEDIUM (50/hr)    |
| BUS               |       | LARGE (100/hr)    |
+-------------------+       +-------------------+

+-------------------+       +-------------------+
|    Vehicle        |       |   ParkingSlot     |
|-------------------|       |-------------------|
| - licensePlate    |       | - slotId          |
| - type            |       | - type: SlotType  |
|-------------------|       | - floor: int      |
| + getLicensePlate |       | - position: int   |
| + getType()       |       | - occupied: bool  |
+-------------------+       |-------------------|
                            | + occupy(): void  |
                            | + free(): void    |
                            | + isOccupied()    |
                            +-------------------+
                                    |
                                    | slot
                                    |
+-------------------+       +-------------------+
|   EntryGate       |       |  ParkingTicket    |
|-------------------|       |-------------------|
| - gateId          |       | - ticketId        |
| - floor: int      |       | - vehicle         |
| - position: int   |       | - slot            |
|-------------------|       | - entryTime       |
| + distanceTo(slot)|       +-------------------+
+-------------------+               |
        |                           | ticket
        |                           |
        |                    +-------------------+
        |                    |      Bill         |
        |                    |-------------------|
        |                    | - ticket          |
        |                    | - exitTime        |
        |                    | - durationHours   |
        |                    | - amount          |
        |                    +-------------------+
        |
+-------------------+
| SlotCompatibility |
|-------------------|
| + compatibleSlots |
|   (vehicleType)   |
| + isCompatible    |
|   (vType, sType)  |
+-------------------+

+-------------------+       +-------------------------------+
|  ParkingFloor     |       |         ParkingLot            |
|-------------------|       |-------------------------------|
| - floorNumber     |       | - name                        |
| - slots: List     |       | - floors: List<ParkingFloor>  |
|-------------------|       | - gates: Map<String,EntryGate>|
| + addSlot(slot)   |       | - activeTickets: Map          |
| + countAvailable  |       |-------------------------------|
|   (type)          |       | + park(vehicle, entry,        |
| + countTotal(type)|       |     slotType, gateId): Ticket |
+-------------------+       | + exit(ticketId, exitTime)    |
                            |     : Bill                    |
                            | + status(): String            |
                            +-------------------------------+
```

## Design Decisions

- SlotType holds hourly rates directly, so billing is always tied to the allocated slot type, not the vehicle type. If a bike parks in a medium slot, it pays the medium rate.
- SlotCompatibility encapsulates the rules for which vehicle types can park in which slot types. A two-wheeler fits in small, medium, or large. A car fits in medium or large. A bus fits only in large.
- EntryGate has a floor and position. Distance to a slot is computed as a weighted combination of floor difference and position difference, with floor changes being more costly. The nearest available compatible slot is assigned.
- When the requested slot type is full, the system falls back to larger compatible slot types. For example, if a bike requests SMALL but none are free, it tries MEDIUM, then LARGE.
- Bill calculates duration by rounding up to the next full hour.
- ParkingLot tracks active tickets in a map keyed by ticket ID for fast lookup during exit.

## APIs

- `park(vehicle, entryTime, requestedSlotType, entryGateId)` -- parks a vehicle and returns a ticket, or null if no slot is available
- `status()` -- returns current availability by slot type per floor
- `exit(ticketId, exitTime)` -- processes exit and returns a bill with the total amount

## Build and Run

```bash
cd parking-lot/src
javac com/example/parking/*.java
java com.example.parking.App
```
