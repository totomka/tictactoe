package com.hillel.tictactoe.mvc;

import java.util.Random;

abstract public class AIPlayer implements Player {
  Board board;
  private CellState symbol;
  String name = "AI";
  Random rand = new Random();

  public void setBoard(Board board) {
    this.board = board;
  }

  public Board getBoard(){
    return board;
  }

  public Move makeEasyMove(){
    int i, j;
    do {
      i = rand.nextInt(board.getRows());
      j = rand.nextInt(board.getColumns());
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
