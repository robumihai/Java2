package org.example.entities;

import org.example.game.SharedMemory;
import org.example.model.Maze;
import org.example.model.Position;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class Robot implements Runnable {
    private final String id;
    private final Maze maze;
    private final SharedMemory sharedMemory;
    private Position position;
    private final Set<Position> visited = new HashSet<>();
    private final Stack<Position> pathStack = new Stack<>();
    private final AtomicBoolean paused = new AtomicBoolean(false);
    private final AtomicBoolean stopped = new AtomicBoolean(false);
    private final AtomicLong sleepTime = new AtomicLong(400);

    public Robot(String id, Maze maze, SharedMemory sharedMemory, Position startPosition) {
        this.id = id;
        this.maze = maze;
        this.sharedMemory = sharedMemory;
        this.position = startPosition;
        sharedMemory.tryOccupyCell(null, position, id);
        visited.add(position);
        pathStack.push(position);
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

            exploreSystematically();

            try {
                Thread.sleep(sleepTime.get());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void exploreSystematically() {
        sharedMemory.markExplored(position);
        
        // Get unvisited neighbors
        List<Position> neighbors = maze.getNeighbors(position);
        List<Position> unvisitedNeighbors = new ArrayList<>();
        
        for (Position neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                unvisitedNeighbors.add(neighbor);
            }
        }

        Position newPos = null;
        
        if (!unvisitedNeighbors.isEmpty()) {
            // Depth-first search: explore an unvisited neighbor
            newPos = unvisitedNeighbors.get(0);
        } else if (!pathStack.isEmpty()) {
            // Backtrack
            pathStack.pop();
            if (!pathStack.isEmpty()) {
                newPos = pathStack.peek();
            }
        }

        if (newPos != null) {
            synchronized (sharedMemory) {
                if (sharedMemory.tryOccupyCell(position, newPos, id)) {
                    Position oldPos = position;
                    position = newPos;
                    
                    if (!visited.contains(newPos)) {
                        visited.add(newPos);
                        pathStack.push(newPos);
                    }
                }
            }
        }
    }

    public Position getPosition() {
        return position;
    }

    public String getId() {
        return id;
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
