package com.hillel.tictactoe.mvc;

import java.util.Scanner;

public class ConsoleView implements View, GameObserver {
  private Controller controller;
  private Game game;
  private Board board;

  public ConsoleView(Controller controller, Game game, Board board) {
    this.controller = controller;
    this.game = game;
    this.board = board;
  }

  public void clearScreen() {

  }

  private void printCells() {
    clearScreen();
    System.out.println(".____.____.____.");
    for (int i = 0; i < board.getRows(); i++) {
      for (int j = 0; j < board.getColumns(); j++) {
        System.out.print('|' + "   ");
        printCell(i, j, board.getCellState(i, j));
      }
      System.out.println('|');
      System.out.println(".____.____.____.");
    }
    System.out.println();
  }

  private void printCell(int i, int j, CellState state) {
    switch (state) {
      case X:
        System.out.print('X');
        break;
      case O:
        System.out.print('O');
        break;
      case EMPTY:
        System.out.print((i * board.getColumns() + 1) + j + "");
        break;
    }
  }

  public void run() {
    updateBoard();
    String symbol = null;
    while (true) {
      if(game.needUserMove()){
        symbol = readInput();
        if (symbol.equals("q")) {
          return;
        } else {
          Move move = decodeMove(symbol);
          controller.processUserMove(move);
        }
      }
      else{
        game.updateGameState();//нельзя во вью изменять модель
      }

    }
  }

  @Override
  public void updateBoard() {
    printCells();
    System.out.println(game.getCurrentPlayerName() + " is making turn (q -> quit): ");
    if (game.isEndGame()) {
      System.out.print("The game is over.");
      if (!board.isFull()) {
        System.out.println("The winner is " + game.showWinner());
      } else {
        System.out.println("Friendship has won. There are no empty cells anymore");
      }
      /*System.out.println("Would you like to play again?\nr -> restart\nany other key - quit");
      String symbol = readInput();
      if (symbol.equals("r")) {
        game.start();
      } else {
        return;
      }*/
    }
  }

  private Move decodeMove(String symbol) {
    Move move = null;
    switch (symbol) {
      case "1":
        move = new Move(0, 0);
        break;
      case "2":
        move = new Move(0, 1);
        break;
      case "3":
        move = new Move(0, 2);
        break;
      case "4":
        move = new Move(1, 0);
        break;
      case "5":
        move = new Move(1, 1);
        break;
      case "6":
        move = new Move(1, 2);
        break;
      case "7":
        move = new Move(2, 0);
        break;
      case "8":
        move = new Move(2, 1);
        break;
      case "9":
        move = new Move(2, 2);
        break;
      default:
        System.out.println("Wrong symbol");
    }
    return move;
  }

  private String readInput() {
    Scanner sc = new Scanner(System.in);
    return sc.nextLine();
  }

}
