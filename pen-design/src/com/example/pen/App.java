package com.example.pen;

public class App {
    public static void main(String[] args) {
        testBallPenBasicFlow();
        testGelPenRefill();
        testFountainPenWrongRefill();
        testInkExhaustion();
        testDoubleStartAndClose();
        testRefillAfterInkRunsOut();
        testWriteEmptyString();
        testConstructorValidation();

        System.out.println();
        System.out.println("All tests passed.");
    }

    private static void testBallPenBasicFlow() {
        System.out.println("--- Test: Ball Pen Basic Flow ---");
        Pen pen = new BallPen("Cello", new Nib(0.5), new Refill(RefillType.BALL, new Ink("Blue", 2.0)));

        pen.write("Should not work before start");
        pen.start();
        pen.write("Hello from ball pen");
        pen.close();
        pen.write("Should not work after close");
        System.out.println();
    }

    private static void testGelPenRefill() {
        System.out.println("--- Test: Gel Pen Refill ---");
        Pen pen = new GelPen("Pilot", new Nib(0.7), new Refill(RefillType.GEL, new Ink("Black", 1.5)));

        pen.start();
        pen.write("Smooth gel writing");
        Refill newRefill = new Refill(RefillType.GEL, new Ink("Red", 1.5));
        pen.refill(newRefill);
        pen.write("Now writing in red");
        pen.close();
        System.out.println();
    }

    private static void testFountainPenWrongRefill() {
        System.out.println("--- Test: Fountain Pen Wrong Refill Type ---");
        Pen pen = new FountainPen("Parker", new Nib(1.0), new Refill(RefillType.FOUNTAIN, new Ink("Blue-Black", 3.0)));

        pen.start();
        pen.write("Elegant fountain pen text");
        Refill wrongRefill = new Refill(RefillType.BALL, new Ink("Green", 2.0));
        pen.refill(wrongRefill);
        pen.close();
        System.out.println();
    }

    private static void testInkExhaustion() {
        System.out.println("--- Test: Ink Exhaustion ---");
        Pen pen = new BallPen("Cello", new Nib(0.5), new Refill(RefillType.BALL, new Ink("Blue", 0.1)));

        pen.start();
        pen.write("First line works");
        pen.write("Second line should fail because ink ran out");
        pen.close();
        System.out.println();
    }

    private static void testDoubleStartAndClose() {
        System.out.println("--- Test: Double Start and Double Close ---");
        Pen pen = new GelPen("Pilot", new Nib(0.7), new Refill(RefillType.GEL, new Ink("Black", 1.5)));

        pen.start();
        pen.start();
        pen.close();
        pen.close();
        System.out.println();
    }

    private static void testRefillAfterInkRunsOut() {
        System.out.println("--- Test: Refill After Ink Runs Out ---");
        Pen pen = new BallPen("Cello", new Nib(0.5), new Refill(RefillType.BALL, new Ink("Blue", 0.05)));

        pen.start();
        pen.write("Use up all the ink in this pen");
        pen.write("This should fail");
        Refill fresh = new Refill(RefillType.BALL, new Ink("Blue", 2.0));
        pen.refill(fresh);
        pen.write("Writing works again after refill");
        pen.close();
        System.out.println();
    }

    private static void testWriteEmptyString() {
        System.out.println("--- Test: Write Empty String ---");
        Pen pen = new FountainPen("Parker", new Nib(1.0), new Refill(RefillType.FOUNTAIN, new Ink("Black", 3.0)));

        pen.start();
        pen.write("");
        pen.close();
        System.out.println();
    }

    private static void testConstructorValidation() {
        System.out.println("--- Test: Constructor Validation ---");

        try {
            new BallPen("Test", new Nib(0.5), new Refill(RefillType.GEL, new Ink("Blue", 1.0)));
            System.out.println("FAIL: Should have thrown for wrong refill type");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: " + e.getMessage());
        }

        try {
            new Ink("Blue", -1.0);
            System.out.println("FAIL: Should have thrown for negative ink");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: " + e.getMessage());
        }

        try {
            new Nib(0);
            System.out.println("FAIL: Should have thrown for zero thickness");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: " + e.getMessage());
        }

        try {
            new Pen("Test", null, new Refill(RefillType.BALL, new Ink("Blue", 1.0))) {
                protected boolean canRefill() { return false; }
                protected RefillType getExpectedRefillType() { return RefillType.BALL; }
                protected double getInkConsumptionPerChar() { return 0.02; }
            };
            System.out.println("FAIL: Should have thrown for null nib");
        } catch (NullPointerException e) {
            System.out.println("PASS: " + e.getMessage());
        }

        System.out.println();
    }
}
