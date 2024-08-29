package minesweeper;

/**
 * A square on the Minesweeper board.
 * Each square may contain a bomb and can be in different states.
 */
public class Square {

  private boolean hasBomb;
  private boolean isFlagged;
  private boolean isDug;
  private int adjacentBombs;

  // Abstraction function:
  // Represents a square on the Minesweeper board.
  // - If hasBomb is true, the square contains a bomb.
  // - If isFlagged is true, the square is flagged by the user.
  // - If isDug is true, the square has been dug.
  // - adjacentBombs counts the number of neighboring squares with bombs, relevant
  // when isDug is true.

  // Rep invariant:
  // adjacentBombs >= 0 && adjacentBombs <= 8

  // Safety from rep exposure:
  // All fields are private and not directly exposed.

  public Square(boolean hasBomb) {
    this.hasBomb = hasBomb;
    this.isDug = false;
    this.isFlagged = false;
    this.adjacentBombs = 0;
    checkRep();
  }

  private void checkRep() {
    assert adjacentBombs >= 0 && adjacentBombs <= 8;
  }

  public boolean hasBomb() {
    return hasBomb;
  }

  public boolean isFlagged() {
    return isFlagged;
  }

  public void setFlagged(boolean flagged) {
    this.isFlagged = flagged;
  }

  public boolean isDug() {
    return isDug;
  }

  public void dig() {
    this.isDug = true;
  }

  public int getAdjacentBombs() {
    return adjacentBombs;
  }

  public void setAdjacentBombs(int adjacentBombs) {
    this.adjacentBombs = adjacentBombs;
    checkRep();
  }
}
