package com.example.snakesladders;

import java.util.ArrayList;
import java.util.List;

public class TestRunner {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        testDice();
        testSnakeCreation();
        testLadderCreation();
        testPlayerCreation();
        testBoardCreationEasy();
        testBoardCreationHard();
        testBoardMinimumSize();
        testBoardSizeTooSmall();
        testSnakeCountMatchesSize();
        testLadderCountMatchesSize();
        testNoSnakeOrLadderOnFirstCell();
        testNoSnakeOrLadderOnLastCell();
        testSnakeHeadAlwaysGreaterThanTail();
        testLadderEndAlwaysGreaterThanStart();
        testNoOverlapBetweenSnakesAndLadders();
        testNoCyclesBetweenSnakesAndLadders();
        testMovePlayerNormal();
        testMovePlayerBeyondBoard();
        testMovePlayerLandsOnSnake();
        testMovePlayerLandsOnLadder();
        testMovePlayerToExactLastCell();
        testPlayerStartsAtZero();
        testPlayerWinState();
        testGameNeedsTwoPlayers();
        testGameWithTwoPlayers();
        testGameWithFivePlayers();
        testBoardSize4();
        testBoardSize7();
        testBoardSize15();
        testDiceDistribution();
        testMultipleBoardGenerationsNoInfiniteLoop();
        testAllSnakesWithinBoardBounds();
        testAllLaddersWithinBoardBounds();
        testPlayerCannotMoveFromZeroWithoutDice();

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

    private static void testDice() {
        Dice dice = new Dice(6);
        boolean allInRange = true;
        for (int i = 0; i < 1000; i++) {
            int val = dice.roll();
            if (val < 1 || val > 6) {
                allInRange = false;
                break;
            }
        }
        check("Dice rolls are between 1 and 6 (1000 rolls)", allInRange);

        try {
            new Dice(0);
            check("Dice with 0 faces throws exception", false);
        } catch (IllegalArgumentException e) {
            check("Dice with 0 faces throws exception", true);
        }

        try {
            new Dice(-3);
            check("Dice with negative faces throws exception", false);
        } catch (IllegalArgumentException e) {
            check("Dice with negative faces throws exception", true);
        }
    }

    private static void testSnakeCreation() {
        Snake s = new Snake(50, 10);
        check("Snake head is 50", s.getHead() == 50);
        check("Snake tail is 10", s.getTail() == 10);

        try {
            new Snake(5, 10);
            check("Snake with head <= tail throws exception", false);
        } catch (IllegalArgumentException e) {
            check("Snake with head <= tail throws exception", true);
        }

        try {
            new Snake(5, 5);
            check("Snake with head == tail throws exception", false);
        } catch (IllegalArgumentException e) {
            check("Snake with head == tail throws exception", true);
        }
    }

    private static void testLadderCreation() {
        Ladder l = new Ladder(10, 50);
        check("Ladder start is 10", l.getStart() == 10);
        check("Ladder end is 50", l.getEnd() == 50);

        try {
            new Ladder(50, 10);
            check("Ladder with end <= start throws exception", false);
        } catch (IllegalArgumentException e) {
            check("Ladder with end <= start throws exception", true);
        }

        try {
            new Ladder(10, 10);
            check("Ladder with end == start throws exception", false);
        } catch (IllegalArgumentException e) {
            check("Ladder with end == start throws exception", true);
        }
    }

    private static void testPlayerCreation() {
        Player p = new Player("Alice");
        check("Player name is Alice", p.getName().equals("Alice"));
        check("Player starts at position 0", p.getPosition() == 0);
        check("Player has not won initially", !p.hasWon());

        p.setPosition(42);
        check("Player position updated to 42", p.getPosition() == 42);

        p.markWon();
        check("Player marked as won", p.hasWon());

        try {
            new Player(null);
            check("Player with null name throws exception", false);
        } catch (NullPointerException e) {
            check("Player with null name throws exception", true);
        }
    }

    private static void testBoardCreationEasy() {
        Board board = new Board(10, DifficultyLevel.EASY);
        check("10x10 board has 100 total cells", board.getTotalCells() == 100);
        check("10x10 board size is 10", board.getSize() == 10);
        check("10x10 easy board has snakes", board.getSnakes().size() > 0);
        check("10x10 easy board has ladders", board.getLadders().size() > 0);
    }

    private static void testBoardCreationHard() {
        Board board = new Board(10, DifficultyLevel.HARD);
        check("10x10 hard board has snakes", board.getSnakes().size() > 0);
        check("10x10 hard board has ladders", board.getLadders().size() > 0);
    }

    private static void testBoardMinimumSize() {
        Board board = new Board(3, DifficultyLevel.EASY);
        check("3x3 board has 9 total cells", board.getTotalCells() == 9);
        check("3x3 board created without error", true);
    }

    private static void testBoardSizeTooSmall() {
        try {
            new Board(2, DifficultyLevel.EASY);
            check("Board size 2 throws exception", false);
        } catch (IllegalArgumentException e) {
            check("Board size 2 throws exception", true);
        }

        try {
            new Board(0, DifficultyLevel.EASY);
            check("Board size 0 throws exception", false);
        } catch (IllegalArgumentException e) {
            check("Board size 0 throws exception", true);
        }

        try {
            new Board(-1, DifficultyLevel.HARD);
            check("Board size -1 throws exception", false);
        } catch (IllegalArgumentException e) {
            check("Board size -1 throws exception", true);
        }
    }

    private static void testSnakeCountMatchesSize() {
        for (int size = 5; size <= 12; size++) {
            Board board = new Board(size, DifficultyLevel.EASY);
            check("Board " + size + "x" + size + " easy has " + size + " snakes",
                    board.getSnakes().size() == size);
        }
    }

    private static void testLadderCountMatchesSize() {
        for (int size = 5; size <= 12; size++) {
            Board board = new Board(size, DifficultyLevel.EASY);
            check("Board " + size + "x" + size + " easy has " + size + " ladders",
                    board.getLadders().size() == size);
        }
    }

    private static void testNoSnakeOrLadderOnFirstCell() {
        for (int i = 0; i < 20; i++) {
            Board board = new Board(10, DifficultyLevel.EASY);
            boolean snakeOnFirst = false;
            for (Snake s : board.getSnakes()) {
                if (s.getHead() == 1 || s.getTail() == 1) snakeOnFirst = true;
            }
            boolean ladderOnFirst = false;
            for (Ladder l : board.getLadders()) {
                if (l.getStart() == 1 || l.getEnd() == 1) ladderOnFirst = true;
            }
            if (snakeOnFirst || ladderOnFirst) {
                check("No snake or ladder on cell 1 (run " + (i + 1) + ")", false);
                return;
            }
        }
        check("No snake or ladder on cell 1 (20 boards checked)", true);
    }

    private static void testNoSnakeOrLadderOnLastCell() {
        for (int i = 0; i < 20; i++) {
            Board board = new Board(10, DifficultyLevel.EASY);
            int last = board.getTotalCells();
            boolean found = false;
            for (Snake s : board.getSnakes()) {
                if (s.getHead() == last || s.getTail() == last) found = true;
            }
            for (Ladder l : board.getLadders()) {
                if (l.getStart() == last || l.getEnd() == last) found = true;
            }
            if (found) {
                check("No snake or ladder on last cell (run " + (i + 1) + ")", false);
                return;
            }
        }
        check("No snake or ladder on last cell (20 boards checked)", true);
    }

    private static void testSnakeHeadAlwaysGreaterThanTail() {
        for (int i = 0; i < 50; i++) {
            Board board = new Board(10, DifficultyLevel.HARD);
            for (Snake s : board.getSnakes()) {
                if (s.getHead() <= s.getTail()) {
                    check("All snake heads > tails (50 boards)", false);
                    return;
                }
            }
        }
        check("All snake heads > tails (50 boards)", true);
    }

    private static void testLadderEndAlwaysGreaterThanStart() {
        for (int i = 0; i < 50; i++) {
            Board board = new Board(10, DifficultyLevel.EASY);
            for (Ladder l : board.getLadders()) {
                if (l.getEnd() <= l.getStart()) {
                    check("All ladder ends > starts (50 boards)", false);
                    return;
                }
            }
        }
        check("All ladder ends > starts (50 boards)", true);
    }

    private static void testNoOverlapBetweenSnakesAndLadders() {
        for (int run = 0; run < 50; run++) {
            Board board = new Board(10, DifficultyLevel.EASY);
            List<Integer> positions = new ArrayList<>();
            for (Snake s : board.getSnakes()) {
                positions.add(s.getHead());
                positions.add(s.getTail());
            }
            for (Ladder l : board.getLadders()) {
                positions.add(l.getStart());
                positions.add(l.getEnd());
            }
            java.util.Set<Integer> unique = new java.util.HashSet<>(positions);
            if (unique.size() != positions.size()) {
                check("No overlapping positions between snakes and ladders (50 boards)", false);
                return;
            }
        }
        check("No overlapping positions between snakes and ladders (50 boards)", true);
    }

    private static void testNoCyclesBetweenSnakesAndLadders() {
        for (int run = 0; run < 50; run++) {
            Board board = new Board(10, DifficultyLevel.EASY);
            java.util.Set<Integer> snakeHeads = new java.util.HashSet<>();
            java.util.Set<Integer> ladderStarts = new java.util.HashSet<>();
            for (Snake s : board.getSnakes()) snakeHeads.add(s.getHead());
            for (Ladder l : board.getLadders()) ladderStarts.add(l.getStart());

            for (Snake s : board.getSnakes()) {
                if (ladderStarts.contains(s.getTail()) || snakeHeads.contains(s.getTail())) {
                    check("No cycles: snake tail lands on another snake/ladder (50 boards)", false);
                    return;
                }
            }
            for (Ladder l : board.getLadders()) {
                if (snakeHeads.contains(l.getEnd()) || ladderStarts.contains(l.getEnd())) {
                    check("No cycles: ladder end lands on another snake/ladder (50 boards)", false);
                    return;
                }
            }
        }
        check("No cycles: snake tail and ladder end never land on another snake/ladder (50 boards)", true);
    }

    private static void testMovePlayerNormal() {
        Board board = new Board(10, DifficultyLevel.EASY);
        int result = board.movePlayer(0, 5);
        check("Move from 0 by 5 lands on 5 or a snake/ladder destination",
                result >= 1 && result <= 100);
    }

    private static void testMovePlayerBeyondBoard() {
        Board board = new Board(10, DifficultyLevel.EASY);
        int result = board.movePlayer(98, 5);
        check("Move from 98 by 5 stays at 98 (beyond 100)", result == 98);

        result = board.movePlayer(99, 6);
        check("Move from 99 by 6 stays at 99 (beyond 100)", result == 99);

        result = board.movePlayer(100, 1);
        check("Move from 100 by 1 stays at 100 (beyond 100)", result == 100);

        result = board.movePlayer(95, 6);
        check("Move from 95 by 6 stays at 95 (beyond 100)", result == 95);
    }

    private static void testMovePlayerLandsOnSnake() {
        Board board = new Board(10, DifficultyLevel.EASY);
        List<Snake> snakes = board.getSnakes();
        if (!snakes.isEmpty()) {
            Snake s = snakes.get(0);
            int from = s.getHead() - 1;
            int result = board.movePlayer(from, 1);
            check("Landing on snake head " + s.getHead() + " moves to tail " + s.getTail(),
                    result == s.getTail());
        } else {
            check("Landing on snake head moves to tail (no snakes to test)", true);
        }
    }

    private static void testMovePlayerLandsOnLadder() {
        Board board = new Board(10, DifficultyLevel.EASY);
        List<Ladder> ladders = board.getLadders();
        if (!ladders.isEmpty()) {
            Ladder l = ladders.get(0);
            int from = l.getStart() - 1;
            int result = board.movePlayer(from, 1);
            check("Landing on ladder start " + l.getStart() + " moves to end " + l.getEnd(),
                    result == l.getEnd());
        } else {
            check("Landing on ladder start moves to end (no ladders to test)", true);
        }
    }

    private static void testMovePlayerToExactLastCell() {
        Board board = new Board(10, DifficultyLevel.EASY);
        int result = board.movePlayer(97, 3);
        check("Move from 97 by 3 reaches exactly 100", result == 100);

        result = board.movePlayer(99, 1);
        check("Move from 99 by 1 reaches exactly 100", result == 100);

        result = board.movePlayer(94, 6);
        check("Move from 94 by 6 reaches exactly 100", result == 100);
    }

    private static void testPlayerStartsAtZero() {
        Player p = new Player("Test");
        check("New player starts at position 0", p.getPosition() == 0);
    }

    private static void testPlayerWinState() {
        Player p = new Player("Test");
        check("New player has not won", !p.hasWon());
        p.markWon();
        check("Player marked as won returns true", p.hasWon());
    }

    private static void testGameNeedsTwoPlayers() {
        Board board = new Board(10, DifficultyLevel.EASY);
        Dice dice = new Dice(6);
        List<Player> one = new ArrayList<>();
        one.add(new Player("Solo"));
        try {
            new Game(board, dice, one);
            check("Game with 1 player throws exception", false);
        } catch (IllegalArgumentException e) {
            check("Game with 1 player throws exception", true);
        }

        List<Player> empty = new ArrayList<>();
        try {
            new Game(board, dice, empty);
            check("Game with 0 players throws exception", false);
        } catch (IllegalArgumentException e) {
            check("Game with 0 players throws exception", true);
        }
    }

    private static void testGameWithTwoPlayers() {
        Board board = new Board(10, DifficultyLevel.EASY);
        Dice dice = new Dice(6);
        List<Player> players = new ArrayList<>();
        players.add(new Player("P1"));
        players.add(new Player("P2"));
        Game game = new Game(board, dice, players);
        game.play();
        int winners = 0;
        for (Player p : players) {
            if (p.hasWon()) winners++;
        }
        check("2-player game: at least 1 winner", winners >= 1);
        check("2-player game: game terminates", true);
    }

    private static void testGameWithFivePlayers() {
        Board board = new Board(10, DifficultyLevel.HARD);
        Dice dice = new Dice(6);
        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            players.add(new Player("P" + i));
        }
        Game game = new Game(board, dice, players);
        game.play();
        int winners = 0;
        for (Player p : players) {
            if (p.hasWon()) winners++;
        }
        check("5-player game: at least 4 winners (game stops at 1 remaining)", winners >= 4);
        check("5-player game: game terminates", true);
    }

    private static void testBoardSize4() {
        Board board = new Board(4, DifficultyLevel.EASY);
        check("4x4 board has 16 cells", board.getTotalCells() == 16);
        check("4x4 board created successfully", true);
    }

    private static void testBoardSize7() {
        Board board = new Board(7, DifficultyLevel.HARD);
        check("7x7 board has 49 cells", board.getTotalCells() == 49);
        check("7x7 hard board has snakes", board.getSnakes().size() > 0);
    }

    private static void testBoardSize15() {
        Board board = new Board(15, DifficultyLevel.EASY);
        check("15x15 board has 225 cells", board.getTotalCells() == 225);
        check("15x15 board has 15 snakes", board.getSnakes().size() == 15);
        check("15x15 board has 15 ladders", board.getLadders().size() == 15);
    }

    private static void testDiceDistribution() {
        Dice dice = new Dice(6);
        int[] counts = new int[7];
        int rolls = 6000;
        for (int i = 0; i < rolls; i++) {
            counts[dice.roll()]++;
        }
        boolean allPresent = true;
        for (int face = 1; face <= 6; face++) {
            if (counts[face] == 0) {
                allPresent = false;
            }
        }
        check("Dice produces all values 1-6 over 6000 rolls", allPresent);

        boolean reasonable = true;
        for (int face = 1; face <= 6; face++) {
            double ratio = (double) counts[face] / rolls;
            if (ratio < 0.10 || ratio > 0.25) {
                reasonable = false;
            }
        }
        check("Dice distribution is roughly uniform (each face 10-25%)", reasonable);
    }

    private static void testMultipleBoardGenerationsNoInfiniteLoop() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            new Board(10, DifficultyLevel.EASY);
            new Board(10, DifficultyLevel.HARD);
        }
        long elapsed = System.currentTimeMillis() - start;
        check("200 board generations (100 easy + 100 hard) complete in < 5s (took " + elapsed + "ms)",
                elapsed < 5000);
    }

    private static void testAllSnakesWithinBoardBounds() {
        for (int run = 0; run < 50; run++) {
            Board board = new Board(10, DifficultyLevel.HARD);
            for (Snake s : board.getSnakes()) {
                if (s.getHead() < 2 || s.getHead() >= board.getTotalCells()
                        || s.getTail() < 1 || s.getTail() >= board.getTotalCells()) {
                    check("All snakes within board bounds (50 boards)", false);
                    return;
                }
            }
        }
        check("All snakes within board bounds (50 boards)", true);
    }

    private static void testAllLaddersWithinBoardBounds() {
        for (int run = 0; run < 50; run++) {
            Board board = new Board(10, DifficultyLevel.EASY);
            for (Ladder l : board.getLadders()) {
                if (l.getStart() < 2 || l.getStart() >= board.getTotalCells()
                        || l.getEnd() < 2 || l.getEnd() >= board.getTotalCells()) {
                    check("All ladders within board bounds (50 boards)", false);
                    return;
                }
            }
        }
        check("All ladders within board bounds (50 boards)", true);
    }

    private static void testPlayerCannotMoveFromZeroWithoutDice() {
        Board board = new Board(10, DifficultyLevel.EASY);
        int result = board.movePlayer(0, 0);
        check("Move from 0 by 0 stays at 0", result == 0);
    }
}
