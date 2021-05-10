package model;

public class Player {

    private Player next;
    private Player previous;
    private final char piece;
    private Cell currentCell;
    private int moves;

    public Player(char p, Cell c){
        piece = p;
        currentCell = c;
    }

    public Player getNext() {
        return next;
    }

    public void setNext(Player nx) {
        next = nx;
    }


    public char getPiece() {
        return piece;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(Cell cc) {
        currentCell = cc;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int m) {
        moves = m;
    }

    public void setPrevious(Player p){
        previous = p;
    }

    public Player getPrevious(){
        return previous;
    }
}
