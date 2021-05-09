package model;

public class Cell {

    private int id;
    private final int row;
    private final int col;
    private char cellChar;
    private Cell up;
    private Cell down;
    private Cell right;
    private Cell left;
    private Cell warp;
    private Player firstPlayer;

    public Cell(int r, int c){
        row = r;
        col = c;
    }

    public char getCellChar() {
        return cellChar;
    }

    public void setCellChar(char c){
        cellChar = c;
    }

    public Cell getWarp(){
        return warp;
    }

    public void setWarp(Cell w){
        warp = w;
    }

    public int getId(){
        return id;
    }
    public Cell getUp() {
        return up;
    }

    public void setUp(Cell u) {
        up = u;
    }

    public Cell getDown() {
        return down;
    }

    public void setDown(Cell d) {
        down = d;
    }

    public Cell getRight() {
        return right;
    }

    public void setRight(Cell r) {
        right = r;
    }

    public Cell getLeft() {
        return left;
    }

    public void setLeft(Cell l) {
        left = l;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public void setId(int i){
        id = i;
    }

    public void addPlayer(Player toAdd){
        if (firstPlayer== null){
            firstPlayer = toAdd;
            toAdd.setNext(toAdd);
            toAdd.setPrevious(toAdd);
        } else {
            toAdd.setPrevious(firstPlayer.getPrevious());
            firstPlayer.getPrevious().setNext(toAdd);
            toAdd.setNext(firstPlayer);
            firstPlayer.setPrevious(toAdd);
        }
    }

    public Player searchPlayer(char c){
        return searchCircularDoubleLN(c,firstPlayer);
    }

    private Player searchCircularDoubleLN(char c, Player current){
        if (current == null || current.getPiece() == c){
            return current;
        } else if (current.getNext() != firstPlayer){
            return searchCircularDoubleLN(c, current.getNext());
        } else {
            return null;
        }
    }

    public void removePlayer(char c){
        Player toRemove = searchPlayer(c);
        if (toRemove == firstPlayer && toRemove.getNext() == firstPlayer){
            firstPlayer = null;
        } else {
            toRemove.getPrevious().setNext(toRemove.getNext());
            toRemove.getNext().setPrevious(toRemove.getPrevious());
            if (toRemove == firstPlayer) {
                firstPlayer = toRemove.getNext();
            }
        }
    }

    public String playersPieces(){
        String pieces ="";
        return playersPieces(firstPlayer, pieces);
    }
    private String playersPieces(Player current, String pieces){
        if (current != null){
            pieces += current.getPiece();
            if (current.getNext() != firstPlayer){
                return playersPieces(current.getNext(),pieces);
            }
        }
        return pieces;
    }
}
