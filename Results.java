package assignment2;

import java.util.ArrayList;
import java.util.List;

public class Results {
    public static String[] results(String wordGuess, String targetWord, int wordLength) {
        int numOfLettersInWord = 0;
        int numOfLettersInGuess = 1;
        List<Character> list = new ArrayList<>();
        String[] result = new String[wordLength];
        for(int k =0;k<wordLength;k++){
            result[k]="_";
        }

        //Goes throught the entered word
        for (int i = 0; i < wordLength; i++) {
            //assigns the character to letter
            Character letter = wordGuess.charAt(i);
            //checks how many times the letter appears in wordGuess
            if(!list.contains(letter)) {
                for (int j = i + 1; j < wordLength; j++) {
                    if (wordGuess.charAt(j) == letter) {
                        numOfLettersInGuess++;
                    }
                }
                //If there is a duplicate letter it is added to list.
                if (numOfLettersInGuess > 1) {
                    list.add(letter);
                }

                //Checks how many times the letter appears in the TargetWord.
                for (int k = 0; k < wordLength; k++) {
                    if (letter == targetWord.charAt(k)) {
                        numOfLettersInWord++;
                    }
                }
                //Checks if the letter is in the word
                if (numOfLettersInWord == 0) {
                    result[i] = "_";
                }
                //checks if there is only one occurance of the word
                if (numOfLettersInWord == 1 && numOfLettersInGuess==1) {
                    Character answer = targetWord.charAt(i);
                    if (answer.equals(letter)) {
                        result[i] = "G";
                    } else {
                        result[i] = "Y";
                    }
                    //Checks if there are more letters in the word and only one in the Guess
                }else if(numOfLettersInWord > 1 && numOfLettersInGuess == 1){
                    int index = wordGuess.indexOf(letter);
                    if(letter== targetWord.charAt(index)){
                        result[index] ="G";
                    }
                    else{
                        result[index] ="Y";
                    }

                    //Checks if there is only one in the word but multiple in the guess
                } else if (numOfLettersInWord == 1 && numOfLettersInGuess > 1) {
                    int check = targetWord.indexOf(letter);
                    int index = wordGuess.indexOf(letter);
                    while(index !=-1 ) {
                        if(wordGuess.charAt(check)==letter && numOfLettersInWord>0){

                            result[check] = "G";
                            numOfLettersInWord--;
                            break;
                        }else if(numOfLettersInWord==0){

                            result[index]="_";
                        }else{
                            result[index] ="Y";
                            numOfLettersInWord--;
                        }
                        if (index == wordLength - 1) {
                            index = -1;
                        } else {
                            index = wordGuess.indexOf(letter, index + 1);
                        }
                    }
                } else if (numOfLettersInWord > 1 && numOfLettersInGuess > 1) {
                    int index = targetWord.indexOf(letter);
                    while (index != -1) {
                        if (wordGuess.charAt(index) == letter) {
                            result[index] = "G";
                            numOfLettersInWord--;
                        }
                        if (index == wordLength - 1) {
                            index = -1;
                        } else {
                            index = targetWord.indexOf(letter, index + 1);
                        }
                    }
                    index = wordGuess.indexOf(letter);
                    while (index!=-1) {
                        if (wordGuess.charAt(index) != targetWord.charAt(index)) {
                            if(numOfLettersInWord==0){
                                result[index] ="_";
                            }else{result[index] = "Y";}

                            numOfLettersInWord--;
                        }
                        if (index == wordLength - 1) {
                            index=-1;
                        } else {
                            index = wordGuess.indexOf(letter, index + 1);
                        }
                    }
                }
            }
            numOfLettersInWord =0;
            numOfLettersInGuess =1;
        }
        return result;
    }
}
