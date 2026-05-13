package org.example;

import org.example.game.CommandHandler;
import org.example.game.GameManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Bunny vs Robots Maze Game ===");
        System.out.println("Starting game...\n");

        // Create game with 21x21 maze and 3 robots
        GameManager gameManager = new GameManager(21, 3);

        // Start command handler thread
        Thread commandThread = new Thread(
            new CommandHandler(
                gameManager.getBunny(),
                gameManager.getRobots(),
                gameManager.getSharedMemory()
            ),
            "CommandHandler"
        );
        commandThread.setDaemon(false);
        commandThread.start();

        // Start the game
        gameManager.start();

        // Wait for game completion
        gameManager.waitForCompletion();

        System.out.println("\nGame finished. Press Enter to exit...");
    }
}
