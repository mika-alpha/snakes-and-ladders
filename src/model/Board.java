package model;

public class Board {

    private Player firstPlayer;
    private Cell firstCell;
    private final int rows;
    private final int cols;
    private final int cellSize;
    private char snChar;
    private char ldChar;
    private char plChar;
    private int snakes;
    private int ladders;

    public Board(int n, int m){
        snChar = 65;
        ldChar = 97;          //i decided to use normal lower case letters to make it less confusing for the user.
        plChar = 12068;       //and also, i decided to  use unicode kanji characters (just because they look better).
        rows = n;
        cols = m;
        cellSize = (int)(Math.log10(n*m)+1)+1;
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
            }
            createCol(r,c+1,current);
        }
    }

    public void enumerateBoard() {
        enumerateBoard(firstCell,1);
    }

    private void enumerateBoard(Cell current, int id){
        current.setId(id);
        if (current.getRow() % 2 != 0 && current.getRight() != null){
            enumerateBoard(current.getRight(),id+1);
        } else if (current.getRow() % 2 == 0 && current.getLeft() != null ){
            enumerateBoard(current.getLeft(),id+1);
        } else if (current.getId() != cols*rows) {
            enumerateBoard(current.getUp(),id+1);
        }
    }

    private int play(){
        return play(firstPlayer);
    }

    private int play(Player current){
        if (movePlayer(current)){
            return 1;
        } else {
            return play(current.getNext());
        }
    }



    public boolean movePlayer(Player p){
        int pThrow = (int)(Math.random()*6) + 1;
        int nCell = p.getCurrentCell().getId() + pThrow;
        if (nCell >= rows*cols){
            return true; //returns true if the player won
        } else {
            Cell moveTo = searchCell(nCell);
            p.getCurrentCell().removePlayer(p.getPiece());
            if (moveTo.getWarp() == null) {
                moveTo.addPlayer(p);
                p.setCurrentCell(moveTo);
            } else {
                moveTo.getWarp().addPlayer(p);
                p.setCurrentCell(moveTo.getWarp());
            }
            return false;
        }
    }



    public void placePlayers(char p){
        Player toAdd = new Player(p,firstCell);
        firstCell.addPlayer(toAdd);
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

    public Cell searchCell(int i){
        int row = (int)Math.ceil((double)i/(double) cols); //finds the row where the cell is located.
        System.out.println(row);
        return searchCell(i, firstCell, row);
    }

    private Cell searchCell(int i, Cell current, int r){
        if (current.getId() == i){
            return current;
        } else if (current.getRow() < r){
            return searchCell(i,current.getUp(),r);
        } else {
            return searchCell(i,current.getRight(),r);
        }
    }
}
