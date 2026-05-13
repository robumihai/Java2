package org.example.game;

import org.example.model.Position;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SharedMemory {
    private final Set<Position> occupiedCells = Collections.synchronizedSet(new HashSet<>());
    private final Set<Position> exploredCells = Collections.synchronizedSet(new HashSet<>());
    private final Map<String, Position> lastKnownBunnyPosition = new ConcurrentHashMap<>();
    private volatile boolean gameOver = false;
    private volatile String winner = null;

    public synchronized boolean tryOccupyCell(Position oldPos, Position newPos, String entityId) {
        if (occupiedCells.contains(newPos)) {
            return false;
        }
        if (oldPos != null) {
            occupiedCells.remove(oldPos);
        }
        occupiedCells.add(newPos);
        return true;
    }

    public synchronized void freeCell(Position pos) {
        occupiedCells.remove(pos);
    }

    public void markExplored(Position pos) {
        exploredCells.add(pos);
    }

    public boolean isExplored(Position pos) {
        return exploredCells.contains(pos);
    }

    public void reportBunnySighting(Position pos, String robotId) {
        lastKnownBunnyPosition.put("last", pos);
        lastKnownBunnyPosition.put("reportedBy", new Position(robotId.hashCode(), 0));
    }

    public Position getLastKnownBunnyPosition() {
        return lastKnownBunnyPosition.get("last");
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public synchronized void endGame(String winner) {
        if (!gameOver) {
            this.gameOver = true;
            this.winner = winner;
        }
    }

    public String getWinner() {
        return winner;
    }

    public int getExploredCount() {
        return exploredCells.size();
    }
}
