package model;

public class Board {

    private Player actualPlayer;
    private Player firstPlayer;
    private Cell firstCell;
    private final int rows;
    private final int cols;
    private char snChar;
    private char ldChar;
    private char plChar;
    private int snakes;
    private int ladders;
    private int cellSize;
    private int nPlayers;

    public Board(int n, int m, int s, int l){
        snChar = 65;
        ldChar = 97;          //i decided to use normal lower case letters to make it less confusing for the user.
        plChar = 945;       //and also, i decided to  use unicode greek characters (just because they look better and there are a lot).
        rows = n;
        cols = m;
        snakes = s;
        ladders = l;
        nPlayers = 0;
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


    public boolean movePlayer(){
        int pThrow = (int)(Math.random()*6) + 1;
        int nCell = actualPlayer.getCurrentCell().getId() + pThrow;
        if (nCell >= rows*cols){
            return true; //returns true if the player won
        } else {
            Cell moveTo = searchCell(nCell);
            actualPlayer.getCurrentCell().removePlayer(actualPlayer.getPiece());
            if (moveTo.getWarp() == null) {
                moveTo.addPlayer(actualPlayer);
                actualPlayer.setCurrentCell(moveTo);
            } else {
                moveTo.getWarp().addPlayer(actualPlayer);
                actualPlayer.setCurrentCell(moveTo.getWarp());
            }
            actualPlayer = actualPlayer.getNext();
            return false;
        }
    }

    public void addPlayersByNumber(int n){
        if (n != 0) {
            Player toAdd = new Player(plChar, firstCell);
            addPlayer(toAdd);
            plChar++;
            addPlayersByNumber(n-1);
        }
    }

    public void addPlayersByChar(String chars){
        addPlayersByChar(chars,0);
    }

    private void addPlayersByChar(String chars, int current){
        if (current < chars.length()) {
            Player toAdd = new Player(chars.charAt(current), firstCell);
            addPlayer(toAdd);
            addPlayersByChar(chars,current+1);
        }
    }

    public void addPlayer(Player toAdd){
        firstCell.addPlayer(toAdd);
        nPlayers++;
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

    public void initialSetUp(){
        actualPlayer = firstPlayer;
        cellSize = (int)(Math.log10(rows*cols)+1)+nPlayers+3;
        enumerateBoard();
    }

    public String printGameBoard(){
        String b = "";
        return printGameBoard(searchRow(rows),b);
    }

    private String printGameBoard(Cell current, String b){
        b += printGameCell(current);
        if (current.getRight() != null){
            return printGameBoard(current.getRight(),b);
        } else if (current.getDown() != null){
            b += "\n";
            return printGameBoard(searchRow(current.getRow()-1),b);
        } else {
            return b;
        }
    }

    public String printGameCell(Cell current){
        String c = "[";
        if (current.getCellChar() != 0){
            c += current.getCellChar();
        }
        c += current.playersPieces();
        if (c.length() < cellSize-1){
            c = fixCell(c);
        }
        c += "]";
        return c;
    }

    public String fixCell(String c){
        if (c.length() < cellSize-1){
            c += " ";
            return fixCell(c);
        }
        return c;
    }

    public Cell searchRow(int r){
        return searchRow(firstCell, r);
    }

    private Cell searchRow(Cell current, int r){
        if (current.getRow() != r){
            current = searchRow(current.getUp(),r);
        }
        return current;
    }

    public void printEnumeratedBoard(){
    }

    public Cell getFirstCell(){
        return firstCell;
    }
}
