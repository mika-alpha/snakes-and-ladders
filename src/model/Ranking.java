package model;

public class Ranking {

    private Score root;

    public Ranking(){
    }

    public void addScore(double p, String n){
        Score s = new Score(p,n);
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

    private void inOrder(Score score) {  //code by Javin Paul from educative.io
        if (score == null) {
            return;
        }
        inOrder(score.getLeft());
        System.out.print(score.getName()+" ");
        inOrder(score.getRight());
    }

    public void inOrder() {
        inOrder(root);
    }

}
