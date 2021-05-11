package model;

public class Ranking {

    private Score root;
    private int counter;


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
                current.setRight(newScore);
            }
        } else {
            if (current.getLeft() != null){
                addScore(current.getLeft(), newScore);
            } else {
                current.setLeft(newScore);
            }
        }
    }

    private void inOrder(Score score) {
        if (score == null) {
            return;
        }
        inOrder(score.getLeft());
        counter++;
        System.out.println(counter+". " +score.getName() +" | piece: " + score.getPiece() + " | points: " +score.getPoints());
        inOrder(score.getRight());
    }

    public void inOrder() {
        counter = 0;
        inOrder(root);
    }


    public Score getRoot(){
        return root;
    }

}
