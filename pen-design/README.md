# Pen Design

## Class Diagram

```
+----------------+          +----------------+
|   <<enum>>     |          |   <<enum>>     |
|   PenState     |          |  RefillType    |
|----------------|          |----------------|
| OPEN           |          | BALL           |
| CLOSED         |          | GEL            |
+----------------+          | FOUNTAIN       |
                            +----------------+
                                   |
                                   | type
+----------------+          +----------------+
|     Nib        |          |    Refill      |
|----------------|          |----------------|
| - thickness    |          | - type         |
|----------------|          | - ink          |
| + getThickness |          |----------------|
+----------------+          | + getType()    |
       |                    | + getInk()     |
       | nib                | + hasInk()     |
       |                    +----------------+
       |                           |
       |                           | refill
       |                           |
+------+--------------------------+--------+
|              Pen (abstract)              |
|------------------------------------------|
| - brand: String                          |
| - nib: Nib                               |
| - refill: Refill                         |
| - state: PenState                        |
|------------------------------------------|
| + start(): void                          |
| + write(text: String): void              |
| + close(): void                          |
| + refill(newRefill: Refill): void        |
| # canRefill(): boolean                   |
| # getExpectedRefillType(): RefillType    |
| # getInkConsumptionPerChar(): double     |
+------------------------------------------+
        |              |              |
        |              |              |
+------------+  +------------+  +---------------+
|  BallPen   |  |  GelPen    |  | FountainPen   |
+------------+  +------------+  +---------------+

+----------------+
|     Ink        |
|----------------|
| - color        |
| - remainingMl  |
|----------------|
| + getColor()   |
| + getRemainingMl|
| + hasInk()     |
| + use(amount)  |
+----------------+
```

## Design Decisions

- Pen is abstract because every pen must declare its refill compatibility and ink consumption rate.
- Refill is a separate class composed into Pen, following composition over inheritance. A refill holds an Ink object and a RefillType.
- Ink tracks remaining volume. Writing consumes ink proportional to text length, with the rate varying by pen type.
- Nib is its own class to allow different thicknesses independent of pen type.
- The refill() method validates that the new refill matches the pen type before accepting it.
- PenState ensures write only works when the pen is open.

## Build and Run

```bash
cd pen-design/src
javac com/example/pen/*.java
java com.example.pen.App
```
