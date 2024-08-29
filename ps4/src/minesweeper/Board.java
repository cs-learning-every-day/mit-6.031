/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

/**
 * Minesweeper board representing the state of the game.
 */
public class Board {
    private final int width;
    private final int height;
    private final Square[][] board;

    // Abstraction function:
    // Represents a Minesweeper game board of dimensions width x height.
    // - board[y][x] represents the square at (x, y) on the board.

    // Rep invariant:
    // width > 0, height > 0
    // board is a non-null rectangular array with dimensions [height][width]

    // Safety from rep exposure:
    // All fields are private and final. board array is not directly exposed.
    public Board(int width, int height, double bombProbaility) {
        this.width = width;
        this.height = height;
        this.board = new Square[height][width];
        initBoard(bombProbaility);
        checkRep();
    }

    private void checkRep() {
        assert width > 0 && height > 0;
        assert board != null;
        assert board.length == height;
        for (int i = 0; i < height; i++) {
            assert board[i].length == width;
        }
    }

    private void initBoard(double bombProbaility) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                boolean hasBomb = Math.random() < bombProbaility;
                board[y][x] = new Square(hasBomb);
            }
        }
        calculateAdjacentBombs();
    }

    private void calculateAdjacentBombs() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                board[y][x].setAdjacentBombs(countAdjacentBombs(x, y));
            }
        }
    }

    private int countAdjacentBombs(int x, int y) {
        int count = 0;
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0)
                    continue;
                int nx = x + dx;
                int ny = y + dy;
                if (isValidCoordinate(nx, ny)) {
                    if (board[ny][nx].hasBomb()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public void dig(int x, int y) {
        if (!isValidCoordinate(x, y)) {
            return;
        }
    }

    public boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}
