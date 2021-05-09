package ui;

import model.Board;

import java.util.Scanner;

public class Menu {

    private Board gameBoard;
    private Scanner sc;

    public Menu(){
        System.out.println("| Welcome to snakes and ladders |");
        sc = new Scanner(System.in);
        menuOptions();
    }

    public void menuOptions(){
        System.out.println("1. To create a new board.");
        System.out.println("2. To see the ranking for the current board.");
        System.out.println("3. To close the game.");
        mainMenu(sc.nextInt());
    }

    private void mainMenu(int i){
        if (i == 1){
            System.out.println("Please enter the ");
        }
    }

    public void play(){
    }

}
