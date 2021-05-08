package model;

public class Cell {

    private int id;
    private int row;
    private int col;
    private Cell up;
    private Cell down;
    private Cell right;
    private Cell left;
    private Player firstPlayer;

    public Cell(int r, int c){
        row = r;
        col = c;
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

    public String toString(){
        return "["+row+","+col+"]";
    }

    public void setId(int i){
        id = i;
    }

}
