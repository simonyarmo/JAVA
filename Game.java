package assignment2;
import java.util.*;
import java.lang.*;

public class Game {
    private boolean showWord;
    private String file;
    private int guessAmount;
    private String targetWord;
    private Scanner guess;
    private int wordLength;

    public Game(GameConfiguration config, Scanner input){
        wordLength = config.wordLength;
        guess=input;
        showWord = config.testMode;
        if(config.wordLength ==4){
            file = "4_letter_words.txt";

        }else if(config.wordLength ==5){
            file = "5_letter_words.txt";


        }else if (config.wordLength ==6){
            file = "6_letter_words.txt";

        }else{
            System.out.println("Unacceptable word length.");
            return;
        }
        guessAmount = config.numGuesses;
    }

    public void runGame(){
        String hist ="[history]";
        int count=0;
        String[] prev = new String[guessAmount];
        HashMap<Integer, String> history = new HashMap<Integer, String>();
        History gameHistory = new History(history);
        assignment2.Dictionary wordle = new assignment2.Dictionary(file);
        targetWord =wordle.getRandomWord();
        if(showWord){
            System.out.println(targetWord);
        }

        String wordGuess="";
        while(!wordGuess.equals(targetWord)){
            if(guessAmount==0){
                lost(targetWord);
                break;
            }
            System.out.println("Enter your guess:");
            wordGuess =guess.next();
            if(wordGuess.equals(hist)){
                if(gameHistory.isEmpty()){
                    System.out.println("--------");

                }else{
                    gameHistory.getHistoy(prev);
                }
            }
            else if(wordGuess.length() != wordLength ){
                System.out.println("This word has an incorrect length. Please try again.");
            }else{
                if(!wordle.containsWord(wordGuess)){
                    System.out.println("This word is not in the dictionary. Please try again.");
                }else{

                    guessAmount--;
                    String[] result = Results.results(wordGuess, targetWord, wordLength);
                    String end="";
                    for(int i =0; i<wordLength;i++){
                        System.out.print(result[i]);
                        end+=result[i];
                    }
                    prev[count] = wordGuess;
                    gameHistory.add(count, end);
                    count++;
                    end="";
                    System.out.println("");

                    if(wordGuess.equals(targetWord)){
                        System.out.println("Congratulations! You have guessed the word correctly.");
                    }else{
                        if(guessAmount!=0) {
                            System.out.println("You have " + guessAmount + " guess(es) remaining.");
                        }
                    }

                }
            }

        }





    }

    public void lost(String word){
        System.out.println("You have run out of guesses.");
        System.out.println("The correct word was \""+ word+"\".");

    }



}
