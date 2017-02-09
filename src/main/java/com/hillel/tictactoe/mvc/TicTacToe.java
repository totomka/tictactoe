package com.hillel.tictactoe.mvc;

public class TicTacToe {
  public static void main(String[] args) {
    UserMove userMove = new UserMove();
    Player human = new HumanPlayer(userMove);
    Board board = new TicTacToeBoard();
    AIPlayer AI = new SmartAIPlayer(board);
    Game game = new Game(board, human, AI);
    Controller controller = new GameController(game, userMove);
    View view = new ConsoleView(controller, game, board);
    game.registerObserver((GameObserver) view);
    view.run();
  }
}
