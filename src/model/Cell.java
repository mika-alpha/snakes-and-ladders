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
}
