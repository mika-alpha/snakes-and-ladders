package model;

public class Board {

    private Player actualPlayer;
    private Player firstBoardPlayer;
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
        ldChar = 106;          //i decided to use normal lower case letters to make it less confusing for the user (from j and onwards).
        plChar = 945;       //and also, i decided to  use greek characters (just because they look better and there are a lot).
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
        System.out.println("The player " + actualPlayer.getPiece() + " has rolled a " + pThrow + " on the dice.");
        int nCell = actualPlayer.getCurrentCell().getId() + pThrow;
        actualPlayer.setMoves(actualPlayer.getMoves()+1);
        if (nCell >= rows*cols){
            return true; //returns true if the player won
        } else {
            Cell moveTo = searchCellByID(nCell);
            actualPlayer.getCurrentCell().removePlayer(actualPlayer.getPiece());
            if (moveTo.getWarp() == null) {
                moveTo.addPlayer(actualPlayer);
                actualPlayer.setCurrentCell(moveTo);
            } else {
                System.out.println("The player " + actualPlayer.getPiece() + " has fallen in the special cell "+ moveTo.getCellChar());
                moveTo.getWarp().addPlayer(actualPlayer);
                actualPlayer.setCurrentCell(moveTo.getWarp());
            }
            actualPlayer = actualPlayer.getNext();
        }
        return false;
    }


    public String boardPieces(){
        String pieces ="";
        return boardPieces(firstBoardPlayer, pieces);
    }

    private String boardPieces(Player current, String pieces){
        if (current != null){
            pieces += current.getPiece();
            if (current.getNext() != firstBoardPlayer){
                return boardPieces(current.getNext(),pieces);
            }
        }
        return pieces;
    }

    public void addPlayersByNumber(int n){
        if (n != 0) {
            Player toAdd = new Player(plChar, firstCell);
            Player toAddB = new Player(plChar,firstCell);
            addPlayer(toAdd,toAddB);
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
            Player toAddB = new Player(chars.charAt(current), firstCell);
            addPlayer(toAdd,toAddB);
            addPlayersByChar(chars,current+1);
        }
    }


    public void addPlayer(Player toAdd, Player toAddB){
        firstCell.addPlayer(toAdd);
        nPlayers++;
        if (firstBoardPlayer == null){
            firstBoardPlayer = toAddB;
            toAddB.setNext(toAddB);
            toAddB.setPrevious(toAddB);
        } else {
            toAddB.setPrevious(firstBoardPlayer.getPrevious());
            firstBoardPlayer.getPrevious().setNext(toAddB);
            toAddB.setNext(firstBoardPlayer);
            firstBoardPlayer.setPrevious(toAddB);
        }
    }

    public Cell searchCellByID(int i){
        int row = (int)Math.ceil((double)i/(double) cols); //finds the row where the cell is located.
        return searchCellByID(i, firstCell, row);
    }

    private Cell searchCellByID(int i, Cell current, int r){
        if (current.getId() == i){
            return current;
        } else if (current.getRow() < r){
            return searchCellByID(i,current.getUp(),r);
        } else {
            return searchCellByID(i,current.getRight(),r);
        }
    }

    public void initialSetUp(){
        actualPlayer = firstBoardPlayer;
        cellSize = (int) Math.log10(rows*cols)+1+nPlayers+3;
        enumerateBoard();
        addSnakesAndLadders();
    }



    public String printGameCell(Cell current){
        String c = "[";
        if (current.getCellChar() != 0){
            c += current.getCellChar();
        }
        c += current.playersPieces();
        if (c.length() < cellSize-1){
            c = fixCell(c,cellSize-1);
        }
        c += "]";
        return c;
    }

    public String printEnumeratedCell(Cell current){
        String c = "[";
        int floor = cellSize-nPlayers-3;
        int cD = (int)Math.log10(current.getId())+1;
        if (cD < floor){
            c = fixCell(c,  (floor-cD) +1);
        }
        c += current.getId();
        if (current.getCellChar() != 0){
            c += current.getCellChar();
        } else {
            c += " ";
        }
        c += "]";
        return c;
    }

    public String printBoard(Cell current, String b, boolean boe){
        if (boe) {
            b += printGameCell(current);
        } else {
            b += printEnumeratedCell(current);
        }
        if (current.getRight() != null){
            return printBoard(current.getRight(),b,boe);
        } else if (current.getDown() != null){
            b += "\n";
            return printBoard(searchRow(current.getRow()-1),b,boe);
        } else {
            return b;
        }
    }

    public String fixCell(String c, int ul){
        if (c.length() < ul){
            c += " ";
            return fixCell(c,ul);
        }
        return c;
    }

    public String printGameBoard(){
        String b = "";
        return printBoard(searchRow(rows),b,true);
    }

    public String printEnumeratedBoard(){
        String b = "";
        return printBoard(searchRow(rows),b,false);
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


    public Cell searchCol (Cell current, int c){
        if (current.getCol() != c){
            return searchCol(current.getRight(),c);
        } else {
            return current;
        }
    }

    public void addSnakes(boolean sol) {
        int startRow;
        int endRow;
        int startCol;
        int endCol;
        if (sol) {
            startRow = (int) (Math.random() * (rows - 1)) + 2;
            endRow = (int) (Math.random() * (rows - 2)) + 1;
        } else {
            startRow = (int) (Math.random() * (rows - 1)) + 1;
            endRow = (int) (Math.random() * (rows - 1)) + 2;
        }
        if (startRow == endRow) {
            addSnakes(sol);
        } else {
            if (sol) {
                if (endRow == 1) {
                    endCol = (int) (Math.random() * (cols - 1)) + 2;
                } else {
                    endCol = (int) (Math.random() * cols) + 1;
                }
                if (rows % 2 == 0) {
                    startCol = (int) (Math.random() * (cols - 1)) + 2;
                } else {
                    startCol = (int) (Math.random() * (cols - 2)) + 1;
                }
            } else {
                if (startRow == 1) {
                    startCol = (int) (Math.random() * (cols - 1)) + 2;
                } else {
                    startCol = (int) (Math.random() * cols) + 1;
                }
                if (rows % 2 == 0) {
                    endCol = (int) (Math.random() * (cols - 1)) + 2;
                } else {
                    endCol = (int) (Math.random() * (cols - 2)) + 1;
                }
            }
            Cell startRowCell = searchRow(startRow);
            Cell endRowCell = searchRow(endRow);
            Cell startCell = searchCol(startRowCell, startCol);
            Cell endCell = searchCol(endRowCell, endCol);
            if (startCell.getCellChar() != 0 || endCell.getCellChar() != 0) {
                addSnakes(sol);
            } else {
                startCell.setWarp(endCell);
                if (sol) {
                    startCell.setCellChar(snChar);
                    endCell.setCellChar(snChar);
                    snChar++;
                } else {
                    startCell.setCellChar(ldChar);
                    endCell.setCellChar(ldChar);
                    ldChar++;
                }
            }
        }
    }


    public void addSnakesAndLadders(){
        if (snakes > 0){
            addSnakes(true);
            snakes--;
            addSnakesAndLadders();
        } else if (ladders > 0){
            addSnakes(false);
            ladders--;
            addSnakesAndLadders();
        }
    }

    public Player getActualPlayer() {
        return actualPlayer;
    }

    public void setActualPlayer(Player p){
        actualPlayer = p;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
