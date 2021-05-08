package model;

public class Score {

    private Score left;
    private Score right;
    private Score parent;
    private final double points;
    private final String name;

    public Score(double p, String n){
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

    public Score getParent() {
        return parent;
    }

    public void setParent(Score parent) {
        this.parent = parent;
    }

    public double getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

}
