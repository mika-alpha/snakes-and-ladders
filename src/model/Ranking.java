package model;

public class Ranking {

    private Score root;


    public Ranking(){
    }

    public void addScore(int p, String n, char pi){
        Score s = new Score(p,n,pi);
        if (root == null){
            root = s;
        } else {
            addScore(root,s);
        }
    }

    private void addScore(Score current, Score newScore){
        if (newScore.getPoints() <= current.getPoints()){
            if (current.getRight() != null) {
                addScore(current.getRight(), newScore);
            } else {
                newScore.setParent(current);
                current.setRight(newScore);
            }
        } else {
            if (current.getLeft() != null){
                addScore(current.getLeft(), newScore);
            } else {
                newScore.setParent(current);
                current.setLeft(newScore);
            }
        }
    }

    private void inOrder(Score score) {//code by Javin Paul from educative.io
        if (score == null) {
            return;
        }
        int counter = score.getInOrderNumber();
        inOrder(score.getLeft());
        score.setInOrderNumber(++counter);
        System.out.println(score.getInOrderNumber() + ". " +score.getName() +" | piece: " + score.getPiece() + " | points: " +score.getPoints());
        inOrder(score.getRight());
    }

    public void inOrder() {
        inOrder(root);
    }


    public Score getRoot(){
        return root;
    }
}
