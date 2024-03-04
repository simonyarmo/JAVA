package assignment3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class DFS {

    private String start;
    private String target;
    private int count=0;

    //notAPath keeps track of the words that lead to a dead end.
    private ArrayList<String> notAPath= new ArrayList<>();
    //visisted keeps track of the values I have visited
    private ArrayList<String> visited= new ArrayList<>();

    //Constructor that adds value to the private variables. Start is the starting word, target is the final word.
    public DFS(String start, String target){
        this.start =start;
        this.target = target;
    }
    //reset() removes all of the values in the ladder that do not lead anywhere
    public void reset(ArrayList<String> list, ArrayList<String> lad){
        lad.remove(lad.size()-1);
        while(notAPath.contains(lad.get(lad.size()-1))){
            lad.remove(lad.size()-1);
        }

    }

    //findBestWord() searches through the list created of adjacent words and finds the best word to use as the next pivot.
    public String findBestWord(ArrayList<String> list, String word, ArrayList<String> ladder) {
        String simString =null;
        int counter = 0;

        // the array first checks through the list to see if the word leads to a dead end. If all of the values in the list have been visited before, is the word or is a notAPath word the word gets added to notAPath Array.
        if (list.size() > 1) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(start) || list.get(i).equals(word) || notAPath.contains(list.get(i))) {
                    counter++;
                }

            }
            if (counter == list.size()) {
                notAPath.add(word);
                ladder.remove(ladder.size()-1);
                if(ladder.isEmpty()){
                    simString= start;
                }else{
                    simString = ladder.get(ladder.size()-1);
                }

                return simString;
            }
        }

        int similar=0;
        int maxSim=0;
        //This for loop goes through the list of adjacent words. It finds the word with the most letters in common with the target word.
        //Similar is a counter for how many similar letters a word in the list has with the target word. If the amount of similar letters is greater than maxSim then the word with the most similar letters is stored in simString.
        for(int k=0; k<list.size();k++){
            String placeHolder = list.get(k);
            for(int i =0; i<placeHolder.length();i++){
                if(placeHolder.charAt(i)==target.charAt(i)){
                    similar++;
                }
            }
            if(similar>maxSim && !notAPath.contains(placeHolder)&& !visited.contains(placeHolder)){
                simString = placeHolder;
                maxSim =similar;
            }
            similar=0;
        }
        //If none of the words are close in common simString will stay with the value null
        //This next block of code checks if the first value in the list is possible.
        if(simString==null){
            if(!ladder.contains(list.get(0))&& !list.get(0).equals(start)&& !notAPath.contains(list.get(0))){
                simString = list.get(0);
            }
            else {
                int flag=0;
                int i=0;
                //If the first word could not be used, this while loop goes through the rest of the list to find a combatable word. Checks if the word is the starting word, not already in the ladder, not in notAPath, and is not the starting word.
                while (i<list.size()){
                    if(!list.get(i).equals(start) && !ladder.contains(list.get(i))&& !notAPath.contains(list.get(i))&& !list.get(i).equals(word)){
                       flag=1;
                       simString= list.get(i);
                       i= list.size();
                    }
                    i++;
                }
                //If none of the words in the list work. We add this word to notAPath and assign the simString to the last value add to the ladder.
                if(flag ==0){
                    notAPath.add(word);
                    simString = ladder.get(ladder.size()-1);
                }
            }

        }
        //This checks to see if the simString value was already in ladder. If it is, it removes all of the values that lead to a dead end. It then assigns simString to the last value in ladder.
        if(ladder.contains(simString)){
            reset(list, ladder);
            simString =ladder.get(ladder.size()-1);
        }

        visited.add(simString);
        return simString;
    }

    public ArrayList<String> findAjacent(String word, ArrayList<String> ladder){
        if(count ==1000){
            ladder.clear();
            return null;
        }
        if(word.equals(target)){
            return ladder;
        }
        int counter =0;
        ArrayList<String> ajacent = new ArrayList<>();
        File file = new File("five_letter_words.txt");

        try {

            Scanner sc = new Scanner(file);

            //Goes through each word and checks if they have 4 similar letters in the same location.
            //If it does it adds it to an array list.
            while (sc.hasNextLine()) {
                String i = sc.next();
                for(int k=0;k<i.length();k++){
                    if(word.charAt(k) ==i.charAt(k)){
                        counter++;
                    }
                }
                if(counter>=4){
                    ajacent.add(i);
                }
                counter=0;
            }
            int notAPathCount=0;
            for(int i=0; i<ajacent.size();i++){
                if(ajacent.get(i).equals(start)||notAPath.contains(ajacent.get(i))){
                    notAPathCount++;
                }

            }
            if(notAPathCount==ajacent.size()){
                ladder.clear();
                ladder =null;
               return ladder;
            }
            notAPathCount=0;


            //finds the best word to add to the ladder.
            String newAdd =findBestWord(ajacent, word, ladder);
            //Adds the word to the ladder
            if(!newAdd.equals(start)&& !ladder.contains(newAdd)){
                ladder.add(newAdd);
            }
            count++;
            word = newAdd;
            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return findAjacent(word,ladder);

    }

    //this creates the ladder. This is what you should call to build and make a finished ladder.
    //Creates the ladder and then calls the recursive function
    public ArrayList<String> createLadder() {
        ArrayList<String> ladder = new ArrayList<>();
        if(start.equals(target)){
            ladder.add(target);
            return ladder;
        }
        ladder.add(start);
        ladder = findAjacent(start, ladder);
        return ladder;
    }

}


