package com.chess;
import com.chess.engine.board.*;
import com.chess.gui.Table;

/**
 * Created by Tom on 20/01/2017.
 * //
 */

public class JChess {

  public static void main(String[] args){

      Board board = Board.createStandardBoard();
      System.out.println(board);

      Table table = new Table();
  }

}
