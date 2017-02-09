package com.hillel.tictactoe.mvc;

import java.util.Random;

public class SillyAIPlayer extends AIPlayer {

  public SillyAIPlayer(Board board) {
    setBoard(board);
  }

  @Override
  public Move makeMove() {
    return makeEasyMove();
  }
}
