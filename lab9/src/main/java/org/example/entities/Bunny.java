package org.example.entities;

import org.example.game.SharedMemory;
import org.example.model.Maze;
import org.example.model.Position;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class Bunny implements Runnable {
    private final Maze maze;
    private final SharedMemory sharedMemory;
    private Position position;
    private final Random random = new Random();
    private final AtomicBoolean paused = new AtomicBoolean(false);
    private final AtomicBoolean stopped = new AtomicBoolean(false);
    private final AtomicLong sleepTime = new AtomicLong(300);

    public Bunny(Maze maze, SharedMemory sharedMemory, Position startPosition) {
        this.maze = maze;
        this.sharedMemory = sharedMemory;
        this.position = startPosition;
        sharedMemory.tryOccupyCell(null, position, "bunny");
    }

    @Override
    public void run() {
        while (!sharedMemory.isGameOver() && !stopped.get()) {
            if (paused.get()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                continue;
            }

            move();

            try {
                Thread.sleep(sleepTime.get());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void move() {
        List<Position> neighbors = maze.getNeighbors(position);
        if (neighbors.isEmpty()) {
            return;
        }

        Position newPos = neighbors.get(random.nextInt(neighbors.size()));
        
        synchronized (sharedMemory) {
            if (sharedMemory.tryOccupyCell(position, newPos, "bunny")) {
                position = newPos;
                
                // Check if reached exit
                if (maze.getCell(position).isExit()) {
                    sharedMemory.endGame("Bunny escaped! 🐰");
                }
            }
        }
    }

    public Position getPosition() {
        return position;
    }

    public void pause() {
        paused.set(true);
    }

    public void resume() {
        paused.set(false);
    }

    public void stop() {
        stopped.set(true);
    }

    public void slowDown() {
        sleepTime.addAndGet(100);
    }

    public void speedUp() {
        long current = sleepTime.get();
        if (current > 100) {
            sleepTime.addAndGet(-100);
        }
    }
}
