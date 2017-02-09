package com.hillel.tictactoe.mvc;

import java.util.Random;

public class Game {
  private Player playerFirst;
  private Player playerSecond;
  private Player currentPlayer;
  private Board board;
  GameObserver observer;

  public Game(Board board, Player playerFirst, Player playerSecond) {
    this.playerFirst = playerFirst;
    this.playerSecond = playerSecond;
    this.board = board;
    start();
  }

  void registerObserver(GameObserver observer) {
    this.observer = observer;
  }

  private void whoPlaysFirst() {
    Random rand = new Random();
    int number = rand.nextInt(2);
    if (number == 0) {
      currentPlayer = playerFirst;
      playerSecond.setPlayerSymbol(CellState.O);
    } else {
      currentPlayer = playerSecond;
      playerFirst.setPlayerSymbol(CellState.O);
    }
    currentPlayer.setPlayerSymbol(CellState.X);
  }

  public void start() {
    board.reset();
    whoPlaysFirst();
  }

  public boolean isEndGame() {
    return board.isEndGame(currentPlayer.getPlayerSymbol());
  }

  public String showWinner() {
    return currentPlayer.getName();
  }

  public boolean needUserMove() {
    return currentPlayer.needUserMove();
  }

  public void updateGameState() {
    do {
      Move move = currentPlayer.makeMove();
      board.markCell(move.getRowCoordinate(), move.getColumnCoordinate(), currentPlayer.getPlayerSymbol());
      playerMadeMove();
      changePlayer();
    }
    while (!needUserMove());
  }

  public void playerMadeMove() {
    observer.updateBoard();
  }

  private void changePlayer() {
    if (currentPlayer == playerFirst) {
      currentPlayer = playerSecond;
    } else {
      currentPlayer = playerFirst;
    }
  }

  public String getCurrentPlayerName() {
    return currentPlayer.getName();
  }
}
