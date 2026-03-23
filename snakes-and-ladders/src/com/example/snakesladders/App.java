package com.example.snakesladders;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter board size (n for n x n board): ");
        int n = scanner.nextInt();

        System.out.print("Enter number of players: ");
        int x = scanner.nextInt();

        System.out.print("Enter difficulty level (easy/hard): ");
        String level = scanner.next().trim().toLowerCase();

        DifficultyLevel difficulty;
        if (level.equals("hard")) {
            difficulty = DifficultyLevel.HARD;
        } else {
            difficulty = DifficultyLevel.EASY;
        }

        scanner.nextLine();
        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= x; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            String name = scanner.nextLine().trim();
            players.add(new Player(name));
        }

        Board board = new Board(n, difficulty);
        Dice dice = new Dice(6);
        Game game = new Game(board, dice, players);
        game.play();

        scanner.close();
    }
}
