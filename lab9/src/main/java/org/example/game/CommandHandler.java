package org.example.game;

import org.example.entities.Bunny;
import org.example.entities.Robot;

import java.util.List;
import java.util.Scanner;

public class CommandHandler implements Runnable {
    private final Bunny bunny;
    private final List<Robot> robots;
    private final SharedMemory sharedMemory;

    public CommandHandler(Bunny bunny, List<Robot> robots, SharedMemory sharedMemory) {
        this.bunny = bunny;
        this.robots = robots;
        this.sharedMemory = sharedMemory;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        printHelp();

        while (!sharedMemory.isGameOver()) {
            try {
                if (System.in.available() > 0) {
                    String command = scanner.nextLine().trim().toLowerCase();
                    processCommand(command);
                }
                Thread.sleep(100);
            } catch (Exception e) {
                break;
            }
        }
    }

    private void processCommand(String command) {
        String[] parts = command.split(" ");
        String action = parts[0];

        switch (action) {
            case "help":
                printHelp();
                break;
            case "pause":
                handlePause(parts);
                break;
            case "resume":
                handleResume(parts);
                break;
            case "speedup":
                handleSpeedUp(parts);
                break;
            case "slowdown":
                handleSlowDown(parts);
                break;
            case "stop":
                handleStop(parts);
                break;
            case "quit":
                sharedMemory.endGame("Game stopped by user.");
                break;
            default:
                System.out.println("Unknown command. Type 'help' for available commands.");
        }
    }

    private void handlePause(String[] parts) {
        if (parts.length == 1) {
            bunny.pause();
            robots.forEach(Robot::pause);
            System.out.println("All entities paused.");
        } else if (parts[1].equals("bunny")) {
            bunny.pause();
            System.out.println("Bunny paused.");
        } else if (parts[1].startsWith("robot")) {
            pauseRobot(parts[1]);
        } else if (parts[1].equals("robots")) {
            robots.forEach(Robot::pause);
            System.out.println("All robots paused.");
        }
    }

    private void handleResume(String[] parts) {
        if (parts.length == 1) {
            bunny.resume();
            robots.forEach(Robot::resume);
            System.out.println("All entities resumed.");
        } else if (parts[1].equals("bunny")) {
            bunny.resume();
            System.out.println("Bunny resumed.");
        } else if (parts[1].startsWith("robot")) {
            resumeRobot(parts[1]);
        } else if (parts[1].equals("robots")) {
            robots.forEach(Robot::resume);
            System.out.println("All robots resumed.");
        }
    }

    private void handleSpeedUp(String[] parts) {
        if (parts.length == 1) {
            bunny.speedUp();
            robots.forEach(Robot::speedUp);
            System.out.println("All entities sped up.");
        } else if (parts[1].equals("bunny")) {
            bunny.speedUp();
            System.out.println("Bunny sped up.");
        } else if (parts[1].startsWith("robot")) {
            speedUpRobot(parts[1]);
        } else if (parts[1].equals("robots")) {
            robots.forEach(Robot::speedUp);
            System.out.println("All robots sped up.");
        }
    }

    private void handleSlowDown(String[] parts) {
        if (parts.length == 1) {
            bunny.slowDown();
            robots.forEach(Robot::slowDown);
            System.out.println("All entities slowed down.");
        } else if (parts[1].equals("bunny")) {
            bunny.slowDown();
            System.out.println("Bunny slowed down.");
        } else if (parts[1].startsWith("robot")) {
            slowDownRobot(parts[1]);
        } else if (parts[1].equals("robots")) {
            robots.forEach(Robot::slowDown);
            System.out.println("All robots slowed down.");
        }
    }

    private void handleStop(String[] parts) {
        if (parts.length == 1) {
            bunny.stop();
            robots.forEach(Robot::stop);
            sharedMemory.endGame("Game stopped by user.");
        } else if (parts[1].equals("bunny")) {
            bunny.stop();
            System.out.println("Bunny stopped.");
        } else if (parts[1].startsWith("robot")) {
            stopRobot(parts[1]);
        } else if (parts[1].equals("robots")) {
            robots.forEach(Robot::stop);
            System.out.println("All robots stopped.");
        }
    }

    private void pauseRobot(String robotId) {
        for (Robot robot : robots) {
            if (robot.getId().equalsIgnoreCase(robotId)) {
                robot.pause();
                System.out.println(robot.getId() + " paused.");
                return;
            }
        }
        System.out.println("Robot not found: " + robotId);
    }

    private void resumeRobot(String robotId) {
        for (Robot robot : robots) {
            if (robot.getId().equalsIgnoreCase(robotId)) {
                robot.resume();
                System.out.println(robot.getId() + " resumed.");
                return;
            }
        }
        System.out.println("Robot not found: " + robotId);
    }

    private void speedUpRobot(String robotId) {
        for (Robot robot : robots) {
            if (robot.getId().equalsIgnoreCase(robotId)) {
                robot.speedUp();
                System.out.println(robot.getId() + " sped up.");
                return;
            }
        }
        System.out.println("Robot not found: " + robotId);
    }

    private void slowDownRobot(String robotId) {
        for (Robot robot : robots) {
            if (robot.getId().equalsIgnoreCase(robotId)) {
                robot.slowDown();
                System.out.println(robot.getId() + " slowed down.");
                return;
            }
        }
        System.out.println("Robot not found: " + robotId);
    }

    private void stopRobot(String robotId) {
        for (Robot robot : robots) {
            if (robot.getId().equalsIgnoreCase(robotId)) {
                robot.stop();
                System.out.println(robot.getId() + " stopped.");
                return;
            }
        }
        System.out.println("Robot not found: " + robotId);
    }

    private void printHelp() {
        System.out.println("\n=== Available Commands ===");
        System.out.println("help                    - Show this help message");
        System.out.println("pause [bunny|robots|Robot-N] - Pause entity/entities");
        System.out.println("resume [bunny|robots|Robot-N] - Resume entity/entities");
        System.out.println("speedup [bunny|robots|Robot-N] - Speed up entity/entities");
        System.out.println("slowdown [bunny|robots|Robot-N] - Slow down entity/entities");
        System.out.println("stop [bunny|robots|Robot-N] - Stop entity/entities");
        System.out.println("quit                    - End the game");
        System.out.println("=========================\n");
    }
}
