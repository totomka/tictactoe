package com.hillel.tictactoe.mvc;

public class SmartAIPlayer extends AIPlayer {
  private int gameStep = 0;

  public SmartAIPlayer(Board board) {
    super(board);
  }

  @Override
  public Move makeMove() {
    Move move = completeLine(getPlayerSymbol());
    if (move != null) {
      gameStep++;
      return move;
    }
    if (getPlayerSymbol() == CellState.X) {
      move = completeLine(CellState.O);
    } else {
      move = completeLine(CellState.X);
    }
    if (move != null) {
      gameStep++;
      return move;
    }
    move = smartStrategy();
    if (move != null) {
      gameStep++;
      return move;
    }
    move = makeEasyMove();
    gameStep++;
    return move;
  }

  @Override
  public void reset() {
    gameStep = 0;
  }

  private Move smartStrategy() {
    Move move;
    move = firstMoveStrategy();
    if (move != null) {
      return move;
    }
    move = secondMoveStrategy();
    if (move != null) {
      return move;
    }
    return thirdMoveStrategy();
  }

  private Move thirdMoveStrategy() {
    /*на пятом шаге игры важно компьютеру походить в смежных угол от угла (нужно проверить возможность походить в оба
     смежных угла, в котором уже находится его символ так, чтобы между ними было пустое поле и чтобы было пустое поле
      по диагонали от угла, в котором он будет ставить символ либо можно поставить рядом с символом в пустое поле,
      если по другую сторону от центра также пустое поле*/
    if (gameStep == 2 && getPlayerSymbol() == CellState.X) {
      if (board.getCellState(0, 0) == CellState.X) {
        if (board.getCellState(0, 1) == CellState.EMPTY
            && board.getCellState(0, 2) == CellState.EMPTY) {
          if (board.getCellState(2, 0) == CellState.EMPTY) {
            return new Move(0, 2);
          } else {
            if (board.getCellState(2, 1) == CellState.EMPTY) {
              return new Move(0, 1);
            } else {
              return null;
            }
          }
        } else {
          if (board.getCellState(1, 0) == CellState.EMPTY
              && board.getCellState(2, 0) == CellState.EMPTY) {
            if (board.getCellState(0, 2) == CellState.EMPTY) {
              return new Move(2, 0);
            } else {
              if (board.getCellState(1, 2) == CellState.EMPTY) {
                return new Move(1, 0);
              } else {
                return null;
              }
            }
          }
        }
      }
    } else {
      if (board.getCellState(0, 2) == CellState.X) {
        if (board.getCellState(1, 2) == CellState.EMPTY
            && board.getCellState(2, 2) == CellState.EMPTY) {
          if (board.getCellState(0, 0) == CellState.EMPTY) {
            return new Move(2, 2);
          } else {
            if (board.getCellState(1, 0) == CellState.EMPTY) {
              return new Move(1, 2);
            } else {
              return null;
            }
          }
        } else {
          if (board.getCellState(0, 1) == CellState.EMPTY
              && board.getCellState(0, 0) == CellState.EMPTY) {
            if (board.getCellState(2, 2) == CellState.EMPTY) {
              return new Move(0, 0);
            } else if (board.getCellState(2, 1) == CellState.EMPTY) {
              return new Move(0, 1);
            } else {
              return null;
            }
          }
        }
      } else {
        if (board.getCellState(2, 2) == CellState.X) {
          if (board.getCellState(2, 1) == CellState.EMPTY
              && board.getCellState(2, 0) == CellState.EMPTY) {
            if (board.getCellState(0, 2) == CellState.EMPTY) {
              return new Move(2, 0);
            } else if (board.getCellState(0, 1) == CellState.EMPTY) {
              return new Move(2, 1);
            } else {
              return null;
            }
          } else if (board.getCellState(1, 2) == CellState.EMPTY
              && board.getCellState(0, 2) == CellState.EMPTY) {
            if (board.getCellState(2, 0) == CellState.EMPTY) {
              return new Move(0, 2);
            } else if (board.getCellState(1, 0) == CellState.EMPTY) {
              return new Move(1, 2);
            } else {
              return null;
            }
          }
        } else {
          if (board.getCellState(2, 0) == CellState.X) {
            if (board.getCellState(2, 1) == CellState.EMPTY
                && board.getCellState(2, 0) == CellState.EMPTY) {
              if (board.getCellState(2, 2) == CellState.EMPTY) {
                return new Move(0, 0);
              } else if (board.getCellState(0, 2) == CellState.EMPTY) {
                return new Move(1, 0);
              } else {
                return null;
              }
            } else if (board.getCellState(2, 1) == CellState.EMPTY
                && board.getCellState(2, 2) == CellState.EMPTY) {
              if (board.getCellState(0, 0) == CellState.EMPTY) {
                return new Move(2, 2);
              } else if (board.getCellState(0, 1) == CellState.EMPTY) {
                return new Move(2, 1);
              } else {
                return null;
              }
            } else {
              return null;
            }
          }//примерно на этом уровне игры уже ясно, играют ли игроки вничью, или всё-таки кто-то выиграет
        }
      }
    }
    return null;
  }

  private Move secondMoveStrategy() {
    if (gameStep == 1 && getPlayerSymbol() == CellState.X) {
      if (board.getCellState(1, 1) == CellState.X) {
        return choosingCorner();
      } else {
        if (board.getCellState(1, 1) == CellState.EMPTY) {
          return new Move(1, 1);
        } else {
          //если центр занят, то тогда нам нужно ходить в противоположный угол по диогонали от того угла,
          // который уже занят символом компьютера
          if (board.getCellState(0, 0) == CellState.X) {
            return new Move(2, 2);
          } else if (board.getCellState(0, 2) == CellState.X) {
            return new Move(2, 0);
          } else if (board.getCellState(2, 2) == CellState.X) {
            return new Move(0, 0);
          } else if (board.getCellState(2, 0) == CellState.X) {
            return new Move(0, 2);
          } else {
            return null;
          }
        }
      }
    }
    /*на четвёртом шаге игры может сложиться опасная ситуация, когда игрок начал с хода в угол,
    при этом компьютер походит в центр, тогда опытный игрок походит по диагонали от угла,
    где находится его символ, в этой ситуации, главное не ходить в углы,
    чтобы не возникла беспроигрышная ситуация в пользу игрока*/
    else if (gameStep == 1 && getPlayerSymbol() == CellState.O) {
      if ((board.getCellState(0, 0) == CellState.X && board.getCellState(2, 2) == CellState.X) ||
          (board.getCellState(0, 2) == CellState.X && board.getCellState(2, 0)
              == CellState.X)) {
        return choosingSideCenter();
      }
    }
    return null;
  }

  Move choosingSideCenter() {
    int center_number = rand.nextInt(4) + 1;
    switch (center_number) {
      case 1:
        return new Move(0, 1);
      case 2:
        return new Move(1, 2);
      case 3:
        return new Move(2, 1);
      default:
        return new Move(1, 0);
    }
  }

  private Move firstMoveStrategy() {
    int choice = rand.nextInt(2);
    if (gameStep == 0 && getPlayerSymbol() == CellState.X) {
      if (choice == 0) {
        return choosingCorner();
      } else {
        return new Move(1, 1);
      }
    } else if (gameStep == 0 && getPlayerSymbol() == CellState.O) {
      if (board.getCellState(1, 1) == CellState.EMPTY) {
        return new Move(1, 1);
      } else {
        return choosingCorner();
      }
    }
    return null;
  }

  private Move choosingCorner() {
    int cornerNumber = rand.nextInt(4) + 1;
    switch (cornerNumber) {
      case 1:
        return new Move(0, 0);
      case 2:
        return new Move(0, 2);
      case 3:
        return new Move(2, 0);
      default:
        return new Move(2, 2);
    }
  }


  private Move completeLine(CellState symbol) {
    Board board = getBoard();
    int rows = board.getRows();
    int cols = board.getColumns();
    for (int i = 0; i < rows; i++)
      for (int j = 0; j < cols; j++) {
        if (board.getCellState(i, j) == symbol) {
          if (i == 0 && j == 0) {
            if (board.getCellState(1, 0) == symbol
                && board.getCellState(2, 0) == CellState.EMPTY) {

              return new Move(2, 0);
            } else if (board.getCellState(1, 0) == CellState.EMPTY
                && board.getCellState(2, 0) == symbol) {

              return new Move(1, 0);
            } else if (board.getCellState(1, 1) == CellState.EMPTY
                && board.getCellState(2, 2) == symbol) {

              return new Move(1, 1);
            } else if (board.getCellState(1, 1) == symbol
                && board.getCellState(2, 2) == CellState.EMPTY) {

              return new Move(2, 2);
            } else if (board.getCellState(0, 1) == symbol
                && board.getCellState(0, 2) == CellState.EMPTY) {

              return new Move(0, 2);
            } else if (board.getCellState(0, 1) == CellState.EMPTY
                && board.getCellState(0, 2) == symbol) {

              return new Move(0, 1);
            }
          } else if (i == 0 && j == 1) {
            if (board.getCellState(0, 2) == symbol
                && board.getCellState(0, 0) == CellState.EMPTY) {
              return new Move(0, 0);
            } else if (board.getCellState(1, 1) == symbol
                && board.getCellState(2, 1) == CellState.EMPTY) {

              return new Move(2, 1);
            } else if (board.getCellState(1, 1) == CellState.EMPTY
                && board.getCellState(2, 1) == symbol) {

              return new Move(1, 1);
            }
          } else if (i == 0 && j == 2) {
            if (board.getCellState(1, 1) == symbol
                && board.getCellState(2, 0) == CellState.EMPTY) {

              return new Move(2, 0);
            } else if (board.getCellState(1, 1) == CellState.EMPTY
                && board.getCellState(2, 0) == symbol) {

              return new Move(1, 1);
            } else if (board.getCellState(1, 2) == CellState.EMPTY
                && board.getCellState(2, 2) == symbol) {

              return new Move(1, 2);
            } else if (board.getCellState(1, 2) == symbol
                && board.getCellState(2, 2) == CellState.EMPTY) {

              return new Move(2, 3);
            }
          } else if (i == 1 && j == 0) {
            if (board.getCellState(2, 0) == symbol
                && board.getCellState(0, 0) == CellState.EMPTY) {

              return new Move(0, 0);
            } else if (board.getCellState(1, 1) == symbol
                && board.getCellState(1, 2) == CellState.EMPTY) {

              return new Move(1, 2);
            } else if (board.getCellState(1, 1) == CellState.EMPTY
                && board.getCellState(1, 2) == symbol) {

              return new Move(1, 1);
            }
          } else if (i == 1 && j == 1) {
            if (board.getCellState(2, 0) == symbol
                && board.getCellState(0, 2) == CellState.EMPTY) {

              return new Move(0, 2);
            } else if (board.getCellState(2, 1) == symbol

                && board.getCellState(0, 1) == CellState.EMPTY) {

              return new Move(0, 1);
            } else if (board.getCellState(2, 2) == symbol
                && board.getCellState(0, 0) == CellState.EMPTY) {

              return new Move(0, 0);
            } else if (board.getCellState(1, 2) == symbol
                && board.getCellState(1, 0) == CellState.EMPTY) {

              return new Move(1, 0);
            }
          } else if (i == 1 && j == 2) {
            if (board.getCellState(2, 2) == symbol
                && board.getCellState(0, 2) == CellState.EMPTY) {

              return new Move(0, 2);
            }
          } else if (i == 2 && j == 0) {
            if (board.getCellState(2, 1) == symbol
                && board.getCellState(2, 2) == CellState.EMPTY) {

              return new Move(2, 2);
            } else if (board.getCellState(2, 1) == CellState.EMPTY
                && board.getCellState(2, 2) == symbol) {

              return new Move(2, 1);
            }
          } else if (i == 2 && j == 1)
            if (board.getCellState(2, 2) == symbol
                && board.getCellState(2, 0) == CellState.EMPTY) {

              return new Move(2, 0);
            }
        }
      }
    return null;
  }
}
