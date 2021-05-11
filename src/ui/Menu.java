package ui;

import model.Board;
import model.Ranking;

import java.util.Scanner;

public class Menu {

    private Board gameBoard;
    private final Ranking ranking;
    private final Scanner sc;

    public Menu(){
        System.out.println("| Welcome to snakes and ladders |");
        sc = new Scanner(System.in);
        ranking = new Ranking();
    }

    public void menuOptions(){
        System.out.println("1. To create a new board.");
        System.out.println("2. To see the ranking for the current board.");
        System.out.println("3. To close the game.");
        mainMenu(Integer.parseInt(sc.nextLine()));
    }

    private void mainMenu(int i){
        if (i == 1){
            System.out.println("Please enter the board setup in the next format: rows cols snakes ladders players\n" +
                    "For players you can both, or write the players symbols or the numbers of players (and the symbols will be generated automatically)\n" +
                    "Examples of valid inputs are: 5 4 3 2 #%*   or   4 3 2 1 3");
            System.out.print("Your input: ");
            String input = sc.nextLine();
            String[] settings = input.split(" ");
            if (settings.length != 5){
                System.out.println("\ninvalid input, please try again\n");
                mainMenu(i);
            } else {
                validateBoard(settings);
                gameBoard.initialSetUp();
                System.out.println(gameBoard.printEnumeratedBoard());
                System.out.println(gameBoard.printGameBoard());
                play(null,false);
            }
        } else if (i == 2){
            if (ranking.getRoot() == null){
                System.out.println("\nNo one has won a game yet, be the first!\n");
            } else {
                ranking.inOrder();
                System.out.println();
            }
            menuOptions();
        } else if (i == 3){
            System.out.println("\nThank you for playing!! :)\n\nMade by: Anemone (github.com/anima-anemone)");
        }
    }

    public void play(String order, boolean si){
        if (order == null) {
            order = sc.nextLine();
        }
        if (order.isEmpty()){
            if (!gameBoard.movePlayer()){
                System.out.println(gameBoard.printGameBoard());
                if (!si) {
                    play(null, false);
                } else {
                    simulation();
                }
            } else {
                System.out.print("\nThe player " + gameBoard.getActualPlayer().getPiece() + " has won the game with "+ gameBoard.getActualPlayer().getMoves() +" moves.\n" +
                        "Please write the winner's nickname: ");
                String name = sc.next();
                ranking.addScore(gameBoard.getCols()* gameBoard.getRows()*gameBoard.getActualPlayer().getMoves(), name, gameBoard.getActualPlayer().getPiece());
                sc.nextLine();
                System.out.println();
                menuOptions();
            }
        } else if (order.equals("menu")) {
            menuOptions();
        } else if (order.equals("enum")){
            System.out.println(gameBoard.printEnumeratedBoard());
            play(null,false);
        } else if (order.equals("simul")){
            simulation();
        }
    }

    public void simulation(){ // i'm pretty sure this can be done better, since this always creates a new thread, but it works.
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(2000);
                play("",true);
            } catch (Exception ignore) {}
        });
        t1.start();
    }

    public  void validateBoard(String[] settings){
        boolean flag = true;
        int n = Integer.parseInt(settings[0]);
        int m = Integer.parseInt(settings[1]);
        int sn = Integer.parseInt(settings[2]);
        int lad = Integer.parseInt(settings[3]);
        if (n * m % 2 == 0) { /// this could be perfectly fine  without the -1, but, it'll be better this way for preventing possible bugs when generating the snakes and ladders
            if (((n * m - 2) - 1) / 2 < sn + lad) {  //since, if you have n possible snakes and ladders, but you take all the possible cells in the first and last row, you'll actually have n-1 possible snakes and ladders)
                System.out.println("\nunable to create a board of that size with that many snakes and ladders, try with a bigger board, or less snakes or ladders\n");
                flag = false;
            }
        } else {
            if (((n * m - 3) - 1) / 2 < sn + lad) {
                System.out.println("\nunable to create a board of that size with that many snakes and ladders, try with a bigger board, or less snakes or ladders\n");
                flag = false;
            }
        }
        if (!flag){
            mainMenu(1);
        } else {
            gameBoard = new Board(n,m, sn, lad);
            if (!Character.isDigit(settings[4].charAt(0))){
                gameBoard.addPlayersByChar(settings[4]);
            } else {
                gameBoard.addPlayersByNumber(Integer.parseInt(settings[4]));
            }
        }
    }
}
