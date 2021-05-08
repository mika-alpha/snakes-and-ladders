package model;

public class Board {

    public Cell firstCell;
    public int rows;
    public int cols;

    public Board(int n, int m){
        rows = n;
        cols = m;
        createBoard();
    }

    /**
        this is just a variation, all of the original code related to creating the board ("matrix") was done by: seyerman
        check the oc here: https://github.com/seyerman/java-intermediate-course-examples/blob/master/linked-matrix-base/src/model/LinkedMatrix.java
        then you may ask, why is this a variation?, the answer is:
        this has as the first node the one on the bottom left (since the game will always start there)
        also, this one has a much simpler way to link the nodes with their *up* and *down* node.
        don't believe me? go check the original code yourself.
     **/

    private void createBoard(){
        firstCell = new Cell(1,1);
        createRow(1,1,firstCell);
    }

    private void createRow(int r, int c, Cell firstRowCell) {
        createCol(r,c+1,firstRowCell);
        if (r+1 <= rows){
            Cell upFirstRow = new Cell(r+1,c);
            upFirstRow.setDown(firstRowCell);
            firstRowCell.setUp(upFirstRow);
            createRow(r+1,c, upFirstRow);
        }
    }

    private void createCol(int r, int c, Cell prev) {
        if (c <= cols){
            Cell current = new Cell(r,c);
            current.setLeft(prev);
            prev.setRight(current);
            if (prev.getDown() != null){
                current.setDown(prev.getDown().getRight());
                prev.getDown().getRight().setUp(current);
                System.out.println(current.toString() + " : " +  current.getDown().toString() + "\n"+ prev.getDown().getRight() + " : " + prev.getDown().getRight().getUp().toString());
            }
            createCol(r,c+1,current);
        }
    }

    public String toString(){
        String msg;
        msg = toStringRow(firstCell);
        return msg;
    }

    private String toStringRow(Cell firstCell) {
        String msg ="";
        if (firstCell != null) {
            msg = toStringCol(firstCell) +"\n";
            msg += toStringRow(firstCell.getUp());
        }
        return msg;
    }

    private String toStringCol(Cell current) {
        String msg ="";
        if (current != null){
            msg = current.toString();
            msg += toStringCol(current.getRight());
        }
        return msg;
    }

    public void enumerateBoard() {
        enumerateBoard(firstCell,1);
    }

    private void enumerateBoard(Cell current, int id){
        current.setId(id);
        if (current.getCol() == cols && current.getRow() == rows){
            return;
        } else if (current.getRow() % 2 != 0 && current.getRight() != null){
            enumerateBoard(current.getRight(),id+1);
        } else if (current.getRow() % 2 == 0 && current.getLeft() != null ){
            enumerateBoard(current.getLeft(),id+1);
        } else {
            enumerateBoard(current.getUp(),id+1);
        }
    }
}