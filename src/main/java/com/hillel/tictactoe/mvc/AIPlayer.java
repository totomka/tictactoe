package com.hillel.tictactoe.mvc;

abstract public class AIPlayer implements Player {
  protected Board board;
  private CellState symbol;
  private String name = "AI";
  AIRandom rand;

  public AIPlayer(Board board, AIRandom random) {
    this.board = board;
    rand = random;
  }

  public Board getBoard() {
    return board;
  }

  public Move makeEasyMove() {
    int i, j;
    do {
      i = rand.getRandomData(board.getRows());
      j = rand.getRandomData(board.getColumns());
    } while (board.getCellState(i, j) != CellState.EMPTY);
    return new Move(i, j);
  }

  public abstract Move makeMove();

  public CellState getPlayerSymbol() {
    return symbol;
  }

  public void setPlayerSymbol(CellState state) {
    symbol = state;
  }

  public String getName() {
    return name;
  }

  public boolean needUserMove() {
    return false;
  }
}
