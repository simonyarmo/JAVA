package assignment3;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class MainTest {
    private static Set<String> dict;
    private static ByteArrayOutputStream outContent;

    private static final int SHORT_TIMEOUT = 300; // ms
    private static final int SEARCH_TIMEOUT = 30000; // ms


    @Rule // Comment this rule and the next line out when debugging to remove timeouts
    public Timeout globalTimeout = new Timeout(SEARCH_TIMEOUT);

    @Before // this method is run before each test
    public void setUp() {
        Main.initialize();
        dict = Main.makeDictionary();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

    }

    @After
    public void cleanup() {

    }

    private boolean verifyLadder(ArrayList<String> ladder, String start, String end) {
        String prev = null;
        if (ladder == null) {
            return true;
        }
        for (String word : ladder) {
            if (!dict.contains(word.toUpperCase()) && !dict.contains(word.toLowerCase())) {
                return false;
            }
            if (prev != null && !differByOne(prev, word)) {
                return false;
            }
            prev = word;
        }
        return ladder.size() > 0
                && ladder.get(0).equals(start.toLowerCase())
                && ladder.get(ladder.size() - 1).equals(end.toLowerCase());
    }

    private static boolean differByOne(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }

        int diff = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i) && ++diff > 1) {
                return false;
            }
        }

        return true;
    }


    //Test for BFS methods


    @Test
    public void BFS1() {
        ArrayList<String> actual = Main.getWordLadderBFS("wrist", "hurry");
        outContent.reset();
        Main.printLadder(actual);
        Boolean answer = verifyLadder(actual, "wrist", "hurry");
        assertTrue(answer);
    }

    @Test
    public void BFS2(){
        ArrayList<String> actual = Main.getWordLadderBFS("write", "slide");
        outContent.reset();
        Main.printLadder(actual);
        Boolean answer = verifyLadder(actual, "write", "slide");
        assertTrue(answer);
    }

    @Test
    public void BFS3(){
        ArrayList<String> actual = Main.getWordLadderBFS("light", "laugh");
        outContent.reset();
        Main.printLadder(actual);
        Boolean answer = verifyLadder(actual, "light", "laugh");
        assertTrue(answer);
    }

    @Test
    public void BFS4(){
        ArrayList<String> actual = Main.getWordLadderBFS("sad", "get");
        outContent.reset();
        Main.printLadder(actual);
        String str = outContent.toString().replace("\n", "").replace(".", "").trim();
        assertEquals("no word ladder can be found between sad and get", str);
    }

    @Test
    public void BFS5(){
        ArrayList<String> actual = Main.getWordLadderBFS("heard", "eagle");
        outContent.reset();
        Main.printLadder(actual);
        String str = outContent.toString().replace("\n", "").replace(".", "").trim();
        assertEquals("no word ladder can be found between heard and eagle", str);
    }

    @Test
    public void testDFS() {
        ArrayList<String> res = Main.getWordLadderDFS("agony", "cells");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertNotNull(res);
        assertNotEquals(0, res.size());
        assertNotEquals(2, res.size());
        assertTrue(verifyLadder(res, "agony", "cells"));
    }
    @Test
    public void testDFS2() {
        ArrayList<String> res = Main.getWordLadderDFS("store", "money");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertNotNull(res);
        assertNotEquals(0, res.size());
        assertNotEquals(2, res.size());
        assertTrue(verifyLadder(res, "store", "money"));
    }
    @Test
    public void testDFS3() {
        ArrayList<String> res = Main.getWordLadderDFS("hello", "zebra");
        assertNull(res);
    }
    @Test
    public void testPrintLadderDFS(){
        ArrayList<String> res = Main.getWordLadderDFS("awake", "flare");
        outContent.reset();
        Main.printLadder(res);
        String str = outContent.toString().replace(".", "").trim();
        assertEquals("a 17-rung word ladder exists between awake and flare\n"+"awake\n" +
                "aware\n" +
                "sware\n" +
                "scare\n" +
                "share\n" +
                "chare\n" +
                "chafe\n" +
                "chape\n" +
                "chase\n" +
                "cease\n" +
                "fease\n" +
                "feast\n" +
                "beast\n" +
                "blast\n" +
                "blase\n" +
                "blare\n" +
                "flare", str);
    }
    @Test
    public void testPrintLadderDFS2() {
        ArrayList<String> res = Main.getWordLadderDFS("zebra", "money");
        outContent.reset();
        Main.printLadder(res);
        String str = outContent.toString().replace("\n", "").replace(".", "").trim();
        assertEquals("no word ladder can be found between zebra and money", str);
    }

    // Write your JUnit tests here
}