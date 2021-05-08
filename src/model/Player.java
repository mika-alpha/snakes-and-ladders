package model;

public class Player {

    private Player next;
    private Player previous;
    private final char piece;
    private Cell currentCell;

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

    public Player getPrevious() {
        return previous;
    }

    public void setPrevious(Player pr) {
        previous = pr;
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
}
