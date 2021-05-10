package model;

public class Score {

    private Score left;
    private Score right;
    private Score parent;
    private final int points;
    private final String name;
    private final char piece;
    private int inOrderNumber;

    public Score(int p, String n, char pi){
        piece = pi;
        points = p;
        name = n;
    }

    public Score getLeft() {
        return left;
    }

    public void setLeft(Score left) {
        this.left = left;
    }

    public Score getRight() {
        return right;
    }

    public void setRight(Score right) {
        this.right = right;
    }

    public void setParent(Score parent) {
        this.parent = parent;
    }

    public int getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

    public char getPiece(){
        return piece;
    }

    public int getInOrderNumber(){
        return inOrderNumber;
    }

    public void setInOrderNumber(int i){
        inOrderNumber = i;
    }

}
