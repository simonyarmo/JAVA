package assignment3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

public class Main {

    // static variables and constants only here.
    static Set<String> other;
    static Set<String> DFSDic;
    private static String startword;
    private static String lastword;
    private static int flag = 0;

    public static void main(String[] args) throws Exception {
        Scanner kb;     // input Scanner for commands
        PrintStream ps; // output file, for student testing and grading only
        // If arguments are specified, read/write from/to files instead of Std IO.
        if (args.length != 0) {
            flag = 1;
            kb = new Scanner(new File(args[0]));
            ps = new PrintStream(new File(args[1]));
            System.setOut(ps);              // redirect output to ps
        } else {
            kb = new Scanner(System.in);    // default input from Stdin
            ps = System.out;                // default output to Stdout
        }

        initialize();

        ArrayList<String> firstlist = parse(kb);
        if (firstlist.isEmpty()) {
            return;
        }
        String firstword = new String(firstlist.get(0));
        String lastword = new String(firstlist.get(1));

        ArrayList<String> createwordle = getWordLadderBFS(firstword, lastword);
        printLadder(createwordle);

        createwordle = getWordLadderDFS(firstword, lastword);
        printLadder(createwordle);


        // TODO methods to read in words, output ladder
    }

    public static void initialize() {
        // initialize your static variables or constants here.
        // We will call this method before running our JUnit tests.  So call it
        // only once at the start of main.

        other = makeDictionary();
        DFSDic = makeDictionary();


    }

    /**
     * @param keyboard Scanner connected to System.in
     * @return ArrayList of Strings containing start word and end word.
     * If command is /quit, return empty ArrayList.
     */
    public static ArrayList<String> parse(Scanner keyboard) {
        // TODO

        ArrayList<String> startandend = new ArrayList<String>();

        ArrayList<String> emptylist = new ArrayList<String>();

        String nxtword = keyboard.nextLine();
        if (nxtword.equals("/quit")) {
            return emptylist;
        }
        startandend = new ArrayList<String>(Arrays.asList(nxtword.split(" ")));

        return startandend;

    }

    public static ArrayList<String> getWordLadderDFS(String start, String end) {
        // Returned list should be ordered start to end.  Include start and end.
        // If ladder is empty, return list with just start and end.
        // TODO some code
        startword = start;
        lastword = end;

        ArrayList<String> ladderDFS = new ArrayList<>();

        if (start.equals(end)) {
            ladderDFS.add(start);
            ladderDFS.add(end);

            return ladderDFS;
        }
        if (!DFSDic.contains(start) || !DFSDic.contains(end)) {
            return null;
        }
        DFS recursion = new DFS(start, end);
        ladderDFS = recursion.createLadder();


        return ladderDFS; // replace this line later with real return
    }

    public static ArrayList<String> getWordLadderBFS(String start, String end) {

        startword = start;
        lastword = end;

        ArrayList<String> ladderBFS = new ArrayList<String>();


        // Set <String> other =  makeDictionary();

        if (start.length() == 0) {
            return null;
        }

        if (!other.contains(end) || !other.contains(start)) {
            return null;
        }



        ArrayList<String> trial = new ArrayList<String>();
        trial.add(start);
        LinkedList<ArrayList<String>> testpath = new LinkedList<>();
        testpath.add(trial);
        if (start.equals(end)) {
            trial.add(end);
            return trial;
        }


        other.remove(start);

        int b = 0;
        while (!testpath.isEmpty()) {

            ArrayList<String> another = testpath.remove();
            b = another.size() - 1;

            String first = another.get(b);

            if (first.equals(end)) {
                if(!another.get(b-1).equals(end)){
                    return another;
                }

                if (another.get(0).equals(start)) {
                    another.remove(0);
                }


                if (another.get(another.size() - 1).equals(end)) {
                    another.remove(another.size() - 1);
                }

                return another;
            }

            char[] removalword = first.toCharArray();
            for (int a = 0; a < removalword.length; a++) {

                for (char y = 'a'; y <= 'z'; y++) {
                    char test = removalword[a];
                    if (removalword[a] != y) {
                        removalword[a] = y;
                    }

                    String newtest = new String(removalword);
                    ArrayList<String> createladder = new ArrayList<String>();
                    if (other.contains(newtest)) {
                        createladder.addAll(another);
                        createladder.add(newtest);
                        testpath.add(createladder);
                        other.remove(newtest);
                    }

                    removalword[a] = test;
                }
            }
        }

        return ladderBFS; // replace this line later with real return
    }

    public static void printLadder(ArrayList<String> ladder) {
        if (ladder == null || ladder.size() == 0) {
            System.out.println("no word ladder can be found between " + startword + " and " + lastword + ".");
        } else {
            if (!ladder.get(ladder.size() - 1).equals(lastword)) {
                ladder.add(0, startword);
                ladder.add(lastword);
            }
            System.out.println("a " + ladder.size() + "-rung word ladder exists between " + ladder.get(0) + " and " + ladder.get(ladder.size() - 1) + ".");
            for (int i = 0; i < ladder.size(); i++) {
                System.out.println(ladder.get(i));
            }
        }

    }

    // TODO
    // Other private static methods here

    /* Do not modify makeDictionary */
    public static Set<String> makeDictionary() {
        Set<String> words = new HashSet<String>();
        Scanner infile = null;
        try {
            infile = new Scanner(new File("five_letter_words.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Dictionary File not Found!");
            e.printStackTrace();
            System.exit(1);
        }
        while (infile.hasNext()) {
            words.add(infile.next());
        }
        return words;
    }
}