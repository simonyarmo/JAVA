package assignment2;

import java.util.Dictionary;
import java.util.Scanner;

public class Driver {

    public static void start(GameConfiguration config) {
        Scanner input = new Scanner(System.in);
        System.out.println("Hello! Welcome to Wordle.");
        System.out.println("Do you want to play a new game? (y/n)");
        String answer = input.next();
        while(!answer.equals("y") && !answer.equals("n")){
            System.out.println("Do you want to play a new game?");
            answer = input.next();
        }
        if(answer.equals("y")){
            while(answer.equals("y")){
                Game one = new Game(config, input);
                one.runGame();
                System.out.println("Do you want to play a new game? (y/n)");
                answer = input.next();
            }
        }



    }

    public void start_hardmode(GameConfiguration config) {
        // TODO: complete this method for extra credit
        // We will call this method from our JUnit test cases.
    }

    public static void main(String[] args) {
        GameConfiguration config = new GameConfiguration(5, 7, true);
        start(config);

    }
}
