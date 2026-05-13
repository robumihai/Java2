package org.example.game;

import org.example.entities.Bunny;
import org.example.entities.Robot;
import org.example.model.Maze;
import org.example.model.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager {
    private final Maze maze;
    private final SharedMemory sharedMemory;
    private final Bunny bunny;
    private final List<Robot> robots;
    private final List<Thread> threads;
    private final GameDisplay display;
    private final long startTime;
    private Thread daemonThread;

    public GameManager(int mazeSize, int numberOfRobots) {
        this.maze = new Maze(mazeSize, mazeSize);
        this.sharedMemory = new SharedMemory();
        this.display = new GameDisplay(maze);
        this.threads = new ArrayList<>();
        this.robots = new ArrayList<>();
        this.startTime = System.currentTimeMillis();

        Random random = new Random();

        // Create bunny at random position
        Position bunnyPos = maze.getRandomEmptyPosition(random);
        this.bunny = new Bunny(maze, sharedMemory, bunnyPos);

        // Create robots at random positions
        for (int i = 0; i < numberOfRobots; i++) {
            Position robotPos;
            do {
                robotPos = maze.getRandomEmptyPosition(random);
            } while (robotPos.equals(bunnyPos) || isPositionOccupiedByRobot(robotPos));

            Robot robot = new Robot("Robot-" + (i + 1), maze, sharedMemory, robotPos);
            robots.add(robot);
        }
    }

    private boolean isPositionOccupiedByRobot(Position pos) {
        for (Robot robot : robots) {
            if (robot.getPosition().equals(pos)) {
                return true;
            }
        }
        return false;
    }

    public void start() {
        // Start bunny thread
        Thread bunnyThread = new Thread(bunny, "Bunny");
        bunnyThread.start();
        threads.add(bunnyThread);

        // Start robot threads
        for (Robot robot : robots) {
            Thread robotThread = new Thread(robot, robot.getId());
            robotThread.start();
            threads.add(robotThread);
        }

        // Start daemon manager thread
        startDaemonManager();

        // Start collision detector thread
        startCollisionDetector();
    }

    private void startDaemonManager() {
        daemonThread = new Thread(() -> {
            while (!sharedMemory.isGameOver()) {
                try {
                    Thread.sleep(1000);
                    long elapsed = System.currentTimeMillis() - startTime;
                    display.display(bunny, robots);
                    System.out.println("Running time: " + (elapsed / 1000) + "s | Explored: " + 
                                     sharedMemory.getExploredCount() + " cells");
                    
                    // Time limit: 60 seconds
                    if (elapsed > 60000) {
                        sharedMemory.endGame("Time limit exceeded! Game over.");
                        break;
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "DaemonManager");
        daemonThread.setDaemon(true);
        daemonThread.start();
    }

    private void startCollisionDetector() {
        Thread collisionThread = new Thread(() -> {
            while (!sharedMemory.isGameOver()) {
                try {
                    Thread.sleep(100);
                    checkCollisions();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "CollisionDetector");
        collisionThread.setDaemon(true);
        collisionThread.start();
    }

    private void checkCollisions() {
        Position bunnyPos = bunny.getPosition();
        for (Robot robot : robots) {
            if (robot.getPosition().equals(bunnyPos)) {
                sharedMemory.endGame(robot.getId() + " caught the bunny! 🤖");
                break;
            }
        }
    }

    public void waitForCompletion() {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        long elapsed = System.currentTimeMillis() - startTime;
        display.display(bunny, robots);
        display.displayGameOver(sharedMemory.getWinner(), elapsed);
    }

    public Bunny getBunny() {
        return bunny;
    }

    public List<Robot> getRobots() {
        return robots;
    }

    public SharedMemory getSharedMemory() {
        return sharedMemory;
    }
}
