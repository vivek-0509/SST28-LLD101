package com.example.snakesladders;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Board board;
    private final Dice dice;
    private final List<Player> players;
    private final List<Player> winners;

    public Game(Board board, Dice dice, List<Player> players) {
        if (players.size() < 2) {
            throw new IllegalArgumentException("need at least 2 players");
        }
        this.board = board;
        this.dice = dice;
        this.players = new ArrayList<>(players);
        this.winners = new ArrayList<>();
    }

    public void play() {
        board.printBoard();
        System.out.println();

        int round = 0;
        while (activePlayers() >= 2) {
            round++;
            System.out.println("=== Round " + round + " ===");
            for (Player player : players) {
                if (player.hasWon()) continue;

                int rolled = dice.roll();
                int oldPos = player.getPosition();
                int newPos = board.movePlayer(oldPos, rolled);
                player.setPosition(newPos);

                if (oldPos == newPos) {
                    System.out.println(player.getName() + " rolled " + rolled + " at position " + oldPos + ", cannot move beyond " + board.getTotalCells());
                } else {
                    System.out.println(player.getName() + " rolled " + rolled + ", moved from " + oldPos + " to " + newPos);
                }

                if (newPos == board.getTotalCells()) {
                    player.markWon();
                    winners.add(player);
                    System.out.println(player.getName() + " has won! (Rank #" + winners.size() + ")");
                }

                if (activePlayers() < 2) break;
            }
            System.out.println();
        }

        printResults();
    }

    private int activePlayers() {
        int count = 0;
        for (Player p : players) {
            if (!p.hasWon()) count++;
        }
        return count;
    }

    private void printResults() {
        System.out.println("=== Game Over ===");
        for (int i = 0; i < winners.size(); i++) {
            System.out.println("Rank #" + (i + 1) + ": " + winners.get(i).getName());
        }
        for (Player p : players) {
            if (!p.hasWon()) {
                System.out.println("Did not finish: " + p.getName() + " (at position " + p.getPosition() + ")");
            }
        }
    }
}
