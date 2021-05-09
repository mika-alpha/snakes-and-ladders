package ui;

import model.Board;
import model.Ranking;

import java.util.Scanner;

public class Menu {

    private Board gameBoard;
    private Ranking ranking;
    private Scanner sc;

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
            if (settings.length != 5 ){
                System.out.println("\ninvalid input, please try again\n");
                mainMenu(i);
            }
            int nm = Integer.parseInt(settings[0]) * Integer.parseInt(settings[1]);
            int sal = Integer.parseInt(settings[2]) + Integer.parseInt(settings[3]); //sol stands for snakes and ladders
            if (nm % 2 == 0){
                if ((nm-2)/2 < sal){
                    System.out.println("\nunable to create a board of that size with that many snakes and ladders, try with a bigger board, or less snakes or ladders\n");
                    mainMenu(i);
                }
            } else {
                if (nm/3 < sal){
                    System.out.println("\nunable to create a board of that size with that many snakes and ladders, try with a bigger board, or less snakes or ladders\n");
                    mainMenu(i);
                }
            }

        } else if (i == 2){
            if (ranking.getRoot() == null){
                System.out.println("\nNo one has won a game yet, be the first!\n");
                menuOptions();
            }
        } else if (i == 3){
            System.out.println("\nThank you for playing!! :)\n\nMade by: Anemone (github.com/anima-anemone)");
        }
    }


}
