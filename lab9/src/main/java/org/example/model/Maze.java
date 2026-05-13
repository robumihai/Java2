package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Maze {
    private final Cell[][] grid;
    private final int rows;
    private final int cols;
    private Position exitPosition;

    public Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new Cell[rows][cols];
        generateMaze();
    }

    private void generateMaze() {
        Random random = new Random();
        
        // Initialize all cells as walls
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Cell(i, j, CellType.WALL);
            }
        }

        // Create a simple maze with paths
        // Start from (1,1) and create paths
        carvePath(1, 1, random);
        
        // Set an exit on the border
        setRandomExit(random);
    }

    private void carvePath(int row, int col, Random random) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) return;
        if (grid[row][col].getType() == CellType.EMPTY) return;

        grid[row][col] = new Cell(row, col, CellType.EMPTY);

        // Directions: up, right, down, left
        int[][] directions = {{-2, 0}, {0, 2}, {2, 0}, {0, -2}};
        shuffleArray(directions, random);

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            
            if (newRow > 0 && newRow < rows - 1 && newCol > 0 && newCol < cols - 1) {
                if (grid[newRow][newCol].getType() == CellType.WALL) {
                    // Carve the cell between
                    grid[row + dir[0] / 2][col + dir[1] / 2] = 
                        new Cell(row + dir[0] / 2, col + dir[1] / 2, CellType.EMPTY);
                    carvePath(newRow, newCol, random);
                }
            }
        }
    }

    private void shuffleArray(int[][] array, Random random) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int[] temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    private void setRandomExit(Random random) {
        List<Position> borderCells = new ArrayList<>();
        
        // Find empty cells on borders
        for (int i = 0; i < rows; i++) {
            if (grid[i][0].getType() == CellType.EMPTY) borderCells.add(new Position(i, 0));
            if (grid[i][cols - 1].getType() == CellType.EMPTY) borderCells.add(new Position(i, cols - 1));
        }
        for (int j = 1; j < cols - 1; j++) {
            if (grid[0][j].getType() == CellType.EMPTY) borderCells.add(new Position(0, j));
            if (grid[rows - 1][j].getType() == CellType.EMPTY) borderCells.add(new Position(rows - 1, j));
        }

        if (borderCells.isEmpty()) {
            // Fallback: create an exit
            exitPosition = new Position(rows - 2, cols - 1);
            grid[rows - 2][cols - 1] = new Cell(rows - 2, cols - 1, CellType.EXIT);
        } else {
            Position pos = borderCells.get(random.nextInt(borderCells.size()));
            exitPosition = pos;
            grid[pos.getRow()][pos.getCol()] = new Cell(pos.getRow(), pos.getCol(), CellType.EXIT);
        }
    }

    public Cell getCell(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return null;
        }
        return grid[row][col];
    }

    public Cell getCell(Position pos) {
        return getCell(pos.getRow(), pos.getCol());
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Position getExitPosition() {
        return exitPosition;
    }

    public Position getRandomEmptyPosition(Random random) {
        List<Position> emptyPositions = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j].isWalkable()) {
                    emptyPositions.add(new Position(i, j));
                }
            }
        }
        return emptyPositions.isEmpty() ? null : emptyPositions.get(random.nextInt(emptyPositions.size()));
    }

    public List<Position> getNeighbors(Position pos) {
        List<Position> neighbors = new ArrayList<>();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        for (int[] dir : directions) {
            int newRow = pos.getRow() + dir[0];
            int newCol = pos.getCol() + dir[1];
            Cell cell = getCell(newRow, newCol);
            if (cell != null && cell.isWalkable()) {
                neighbors.add(new Position(newRow, newCol));
            }
        }
        return neighbors;
    }
}
