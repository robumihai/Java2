package org.example.game;

import org.example.entities.Bunny;
import org.example.entities.Robot;
import org.example.model.Cell;
import org.example.model.CellType;
import org.example.model.Maze;
import org.example.model.Position;

import java.util.List;

public class GameDisplay {
    private final Maze maze;

    public GameDisplay(Maze maze) {
        this.maze = maze;
    }

    public void display(Bunny bunny, List<Robot> robots) {
        clearScreen();
        
        char[][] display = new char[maze.getRows()][maze.getCols()];
        
        // Fill with maze structure
        for (int i = 0; i < maze.getRows(); i++) {
            for (int j = 0; j < maze.getCols(); j++) {
                Cell cell = maze.getCell(i, j);
                if (cell.getType() == CellType.WALL) {
                    display[i][j] = '█';
                } else if (cell.isExit()) {
                    display[i][j] = 'E';
                } else {
                    display[i][j] = ' ';
                }
            }
        }

        // Place robots
        for (Robot robot : robots) {
            Position pos = robot.getPosition();
            display[pos.getRow()][pos.getCol()] = 'R';
        }

        // Place bunny (overlays robots if same position)
        Position bunnyPos = bunny.getPosition();
        display[bunnyPos.getRow()][bunnyPos.getCol()] = 'B';

        // Print the display
        System.out.println("╔" + "═".repeat(maze.getCols()) + "╗");
        for (int i = 0; i < maze.getRows(); i++) {
            System.out.print("║");
            for (int j = 0; j < maze.getCols(); j++) {
                System.out.print(display[i][j]);
            }
            System.out.println("║");
        }
        System.out.println("╚" + "═".repeat(maze.getCols()) + "╝");
        System.out.println("B = Bunny 🐰 | R = Robot 🤖 | E = Exit | █ = Wall");
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void displayGameOver(String winner, long elapsedTime) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("GAME OVER!");
        System.out.println(winner);
        System.out.println("Time elapsed: " + (elapsedTime / 1000.0) + " seconds");
        System.out.println("=".repeat(50));
    }
}
