package model;

public class Score {

    private Score left;
    private Score right;
    private final int points;
    private final String name;
    private final char piece;

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

    public int getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

    public char getPiece(){
        return piece;
    }

}
