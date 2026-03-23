package com.example.snakesladders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Board {
    private final int size;
    private final int totalCells;
    private final List<Snake> snakes;
    private final List<Ladder> ladders;
    private final Map<Integer, Integer> snakeMap;
    private final Map<Integer, Integer> ladderMap;

    public Board(int size, DifficultyLevel difficulty) {
        if (size < 3) {
            throw new IllegalArgumentException("board size must be at least 3");
        }
        this.size = size;
        this.totalCells = size * size;
        this.snakes = new ArrayList<>();
        this.ladders = new ArrayList<>();
        this.snakeMap = new HashMap<>();
        this.ladderMap = new HashMap<>();
        generateSnakesAndLadders(size, difficulty);
    }

    private void generateSnakesAndLadders(int count, DifficultyLevel difficulty) {
        Random random = new Random();
        Set<Integer> occupied = new HashSet<>();
        occupied.add(1);
        occupied.add(totalCells);

        for (int i = 0; i < count; i++) {
            Snake snake = generateSnake(random, occupied, difficulty);
            if (snake == null) break;
            snakes.add(snake);
            snakeMap.put(snake.getHead(), snake.getTail());
        }

        for (int i = 0; i < count; i++) {
            Ladder ladder = generateLadder(random, occupied, difficulty);
            if (ladder == null) break;
            ladders.add(ladder);
            ladderMap.put(ladder.getStart(), ladder.getEnd());
        }
    }

    private Snake generateSnake(Random random, Set<Integer> occupied, DifficultyLevel difficulty) {
        int maxAttempts = 1000;
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            boolean relaxed = attempt > maxAttempts / 2;

            int head = 2 + random.nextInt(totalCells - 2);
            if (occupied.contains(head)) continue;

            int minTail;
            int maxTail;
            if (!relaxed && difficulty == DifficultyLevel.HARD) {
                minTail = 1;
                maxTail = Math.max(1, head / 2);
            } else {
                minTail = 1;
                maxTail = head - 1;
            }
            if (minTail > maxTail) continue;

            int tail = minTail + random.nextInt(maxTail - minTail + 1);
            if (occupied.contains(tail)) continue;

            if (wouldCreateCycle(head, tail, true)) continue;

            occupied.add(head);
            occupied.add(tail);
            return new Snake(head, tail);
        }
        return null;
    }

    private Ladder generateLadder(Random random, Set<Integer> occupied, DifficultyLevel difficulty) {
        int maxAttempts = 1000;
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            boolean relaxed = attempt > maxAttempts / 2;

            int start = 2 + random.nextInt(totalCells - 2);
            if (occupied.contains(start)) continue;

            int minEnd;
            int maxEnd;
            if (!relaxed && difficulty == DifficultyLevel.EASY) {
                minEnd = Math.max(start + 1, start + (totalCells - start) / 2);
                maxEnd = totalCells - 1;
            } else {
                minEnd = start + 1;
                maxEnd = totalCells - 1;
            }
            if (minEnd > maxEnd) continue;

            int end = minEnd + random.nextInt(maxEnd - minEnd + 1);
            if (occupied.contains(end)) continue;

            if (wouldCreateCycle(start, end, false)) continue;

            occupied.add(start);
            occupied.add(end);
            return new Ladder(start, end);
        }
        return null;
    }

    private boolean wouldCreateCycle(int from, int to, boolean isSnake) {
        if (isSnake) {
            return ladderMap.containsKey(to) || snakeMap.containsKey(to);
        } else {
            return snakeMap.containsKey(to) || ladderMap.containsKey(to);
        }
    }

    public int movePlayer(int currentPosition, int diceValue) {
        int newPosition = currentPosition + diceValue;
        if (newPosition > totalCells) {
            return currentPosition;
        }
        if (snakeMap.containsKey(newPosition)) {
            int tail = snakeMap.get(newPosition);
            System.out.println("  Bitten by snake at " + newPosition + ", sliding down to " + tail);
            newPosition = tail;
        } else if (ladderMap.containsKey(newPosition)) {
            int top = ladderMap.get(newPosition);
            System.out.println("  Climbed ladder at " + newPosition + ", moving up to " + top);
            newPosition = top;
        }
        return newPosition;
    }

    public int getTotalCells() {
        return totalCells;
    }

    public int getSize() {
        return size;
    }

    public List<Snake> getSnakes() {
        return Collections.unmodifiableList(snakes);
    }

    public List<Ladder> getLadders() {
        return Collections.unmodifiableList(ladders);
    }

    public void printBoard() {
        System.out.println("Board: " + size + "x" + size + " (" + totalCells + " cells)");
        System.out.println("Snakes:");
        for (Snake s : snakes) {
            System.out.println("  " + s);
        }
        System.out.println("Ladders:");
        for (Ladder l : ladders) {
            System.out.println("  " + l);
        }
    }
}
