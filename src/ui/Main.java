package ui;

import model.Board;

public class Main {

    public static void main(String[] args){
        Board game = new Board(10,10);
        game.enumerateBoard();
    }
}
