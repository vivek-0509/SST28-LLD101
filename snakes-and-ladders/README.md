# Snakes and Ladders

## Class Diagram

```
+-------------------+
|  <<enum>>         |
|  DifficultyLevel  |
|-------------------|
| EASY              |
| HARD              |
+-------------------+

+-------------------+        +-------------------+
|      Snake        |        |      Ladder       |
|-------------------|        |-------------------|
| - head: int       |        | - start: int      |
| - tail: int       |        | - end: int        |
|-------------------|        |-------------------|
| + getHead(): int  |        | + getStart(): int |
| + getTail(): int  |        | + getEnd(): int   |
+-------------------+        +-------------------+
         |                            |
         | snakes (List)              | ladders (List)
         |                            |
+------------------------------------------------+
|                    Board                       |
|------------------------------------------------|
| - size: int                                    |
| - totalCells: int                              |
| - snakes: List<Snake>                          |
| - ladders: List<Ladder>                        |
| - snakeMap: Map<Integer, Integer>              |
| - ladderMap: Map<Integer, Integer>             |
|------------------------------------------------|
| + Board(size, difficulty)                      |
| + movePlayer(currentPos, diceVal): int         |
| + getTotalCells(): int                         |
| + getSnakes(): List<Snake>                     |
| + getLadders(): List<Ladder>                   |
| + printBoard(): void                           |
| - generateSnakesAndLadders(count, diff): void  |
| - generateSnake(random, occupied, diff): Snake |
| - generateLadder(random, occupied, diff): Ladder|
| - wouldCreateCycle(from, to, isSnake): boolean |
+------------------------------------------------+

+-------------------+       +-------------------+
|      Dice         |       |     Player        |
|-------------------|       |-------------------|
| - faces: int      |       | - name: String    |
| - random: Random  |       | - position: int   |
|-------------------|       | - hasWon: boolean  |
| + roll(): int     |       |-------------------|
| + getFaces(): int |       | + getName(): String|
+-------------------+       | + getPosition()   |
                            | + setPosition()   |
                            | + hasWon(): boolean|
                            | + markWon(): void  |
                            +-------------------+

+------------------------------------------------+
|                    Game                        |
|------------------------------------------------|
| - board: Board                                 |
| - dice: Dice                                   |
| - players: List<Player>                        |
| - winners: List<Player>                        |
|------------------------------------------------|
| + Game(board, dice, players)                   |
| + play(): void                                 |
| - activePlayers(): int                         |
| - printResults(): void                         |
+------------------------------------------------+

+-------------------+
|       App         |
|-------------------|
| + main(args): void|
+-------------------+
```

## Design Decisions

- Board generates n snakes and n ladders randomly, ensuring no cell is used twice and no cycles form between snakes and ladders.
- Difficulty affects snake and ladder lengths. In EASY mode, snakes are short and ladders are long. In HARD mode, snakes are long and ladders are short.
- Dice is its own class to allow different face counts if needed.
- Player tracks position and win state independently.
- Game loop continues until fewer than 2 players remain active. Winners are ranked in order of finishing.
- The movePlayer method on Board handles snake/ladder resolution in one step, keeping Game logic clean.

## Build and Run

```bash
cd snakes-and-ladders/src
javac com/example/snakesladders/*.java
java com.example.snakesladders.App
```

## Sample Input

```
Enter board size (n for n x n board): 10
Enter number of players: 3
Enter difficulty level (easy/hard): easy
Enter name for Player 1: Alice
Enter name for Player 2: Bob
Enter name for Player 3: Charlie
```
