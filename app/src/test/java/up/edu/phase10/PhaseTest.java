package up.edu.phase10;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class PhaseTest {

    @Test
    public void checkIfPhaseOne() {
        Phase phase = new Phase(); //creates a new phase
        ArrayList<Card> hand = new ArrayList<Card>(); //new array list for a complete phase
        ArrayList<Card> notPhase = new ArrayList<Card>(); //new array list for an incomplete phase

        //dummy hand to work
        hand.add(new Card(3,1));
        hand.add(new Card(3,2));
        hand.add(new Card(3,1));
        hand.add(new Card(4,1));
        hand.add(new Card(4,2));
        hand.add(new Card(4,4));
        //dummy hand to fail
        notPhase.add(new Card(3,1));
        notPhase.add(new Card(3,2));
        notPhase.add(new Card(3,1));
        notPhase.add(new Card(4,3));
        notPhase.add(new Card(2,1));
        notPhase.add(new Card(4,2));
        //sorts both hands
        Card[] sorted = phase.sortCards(hand);
        Card[] bad = phase.sortCards(notPhase);
        //asserts true and false
        assertTrue(phase.checkIfPhaseOne(sorted,0));
        assertFalse(phase.checkIfPhaseOne(bad,0));;

    }

    @Test
    public void checkIfPhaseTwo() {
        Random rand = new Random();
        Phase phase = new Phase(); //creates a new phase
        ArrayList<Card> hand = new ArrayList<Card>(); //new array list for a complete phase
        ArrayList<Card> notPhase = new ArrayList<Card>(); //new array list for an incomplete phase
        //dummy hand to work
        hand.add(new Card(3,2));
        hand.add(new Card(3,1));
        hand.add(new Card(3,1));
        hand.add(new Card(1,2));
        hand.add(new Card(2,1));
        hand.add(new Card(3,1));
        hand.add(new Card(4,1));
        //dummy hand to fail
        notPhase.add(new Card(3,2));
        notPhase.add(new Card(5,1));
        notPhase.add(new Card(3,1));
        notPhase.add(new Card(3,2));
        notPhase.add(new Card(5,1));
        notPhase.add(new Card(3,1));
        notPhase.add(new Card(3,1));
        //sorts both hands
        Card[] sorted = phase.sortCards(hand);
        Card[] notSorted = phase.sortCards(notPhase);
        //asserts true and false
        assertTrue(phase.checkIfPhaseTwo(sorted,0));
        assertFalse(phase.checkIfPhaseTwo(notSorted,0));
    }

    @Test
    public void checkIfPhaseThree() {
        Random rand = new Random();
        Phase phase = new Phase(); //creates a new phase
        ArrayList<Card> hand = new ArrayList<Card>(); //new array list for a complete phase
        Card[] notPhase = new Card[4]; //random card array to fail
        //dummy hand to work
        hand.add(new Card(4,1));
        hand.add(new Card(4,2));
        hand.add(new Card(4,3));
        hand.add(new Card(4,1));

        //creates the run for the correct phase
        for (int i = 0; i < 4; i++){
            hand.add(new Card(1+i,1));
        }
        //randomizes a run so it fails
        for(int i = 0; i < 4; i++){
            notPhase[i] = new Card(rand.nextInt(11)+1,1);
        }
        //sorts cards
        Card[] sorted = phase.sortCards(hand);
        //asserts true and false
        assertTrue(phase.checkIfPhaseThree(sorted,0));
        assertFalse(phase.checkIfPhaseThree(notPhase,0));
    }

    @Test
    public void checkIfPhaseFour() {
        Random rand = new Random();
        Phase phase = new Phase();
        Card[] hand = new Card[7];//will work
        Card[] notRun = new Card[7];//will fail
        //creates a correct run of 7 in hand
        for (int i = 0; i < 7; i++){
            hand[i] = new Card(1+i,1);
        }
        //creates a random hand of 7 for notRun
        for(int i = 0; i < 7; i++){
            notRun[i] = new Card(rand.nextInt(11)+1,1);
        }
        //asserts true and false
        assertTrue(phase.checkIfPhaseFour(hand,1));
        assertFalse(phase.checkIfPhaseFour(notRun,1));
    }
    @Test
    public void checkIfPhaseFive() {
        Random rand = new Random();
        Phase phase = new Phase();
        Card[] hand = new Card[8];//will work
        Card[] notRun = new Card[8];//will fail
        //creates a correct run of 8 in hand
        for (int i = 0; i < 8; i++){
            hand[i] = new Card(1+i,1);
        }
        //creates a random hand of 7 for notRun
        for(int i = 0; i < 8; i++){
            notRun[i] = new Card(rand.nextInt(11)+1,1);
        }
        //asserts true and false
        assertTrue(phase.checkIfPhaseFive(hand,0));
        assertFalse(phase.checkIfPhaseFive(notRun,0));
    }

    @Test
    public void checkIfPhaseSix() {
        Random rand = new Random();
        Phase phase = new Phase();
        Card[] hand = new Card[9];//will work
        Card[] notRun = new Card[9];//will fail
        //creates a correct run of 9 in hand
        for (int i = 0; i < 9; i++){
            hand[i] = new Card(1+i,1);
        }
        //creates a random hand of 8 for notRun
        for(int i = 0; i < 9; i++){
            notRun[i] = new Card(rand.nextInt(11)+1,1);
        }
        //asserts true and false
        assertTrue(phase.checkIfPhaseSix(hand,0));
        assertFalse(phase.checkIfPhaseSix(notRun,0));

    }

    @Test
    public void checkIfPhaseSeven() {
        //checks for a 2 sets of 4
        Phase phase = new Phase();
        ArrayList<Card> hand = new ArrayList<Card>(); //correct phase
        ArrayList<Card> notPhase = new ArrayList<Card>(); //incorrect phase
        //dummy hand to work
        hand.add(new Card(3,1));
        hand.add(new Card(3,2));
        hand.add(new Card(3,1));
        hand.add(new Card(3,3));
        hand.add(new Card(4,1));
        hand.add(new Card(4,2));
        hand.add(new Card(4,4));
        hand.add(new Card(4,3));
        //dummy hand to fail
        notPhase.add(new Card(3,1));
        notPhase.add(new Card(2,2));
        notPhase.add(new Card(2,1));
        notPhase.add(new Card(1,3));
        notPhase.add(new Card(4,1));
        notPhase.add(new Card(4,2));
        notPhase.add(new Card(4,4));
        notPhase.add(new Card(4,3));
        //sorts cards
        Card[] sorted = phase.sortCards(hand);
        Card[] bad = phase.sortCards(notPhase);
        //asserts true and false
        assertTrue(phase.checkIfPhaseSeven(sorted,0));
        assertFalse(phase.checkIfPhaseSeven(bad,0));;

    }

    @Test
    public void checkIfPhaseNine() {
        //test for 1 set of 5 and 1 set of 2
        Phase phase = new Phase();
        ArrayList<Card> hand = new ArrayList<Card>();
        ArrayList<Card> notPhase = new ArrayList<Card>();
        //dummy hand to work
        hand.add(new Card(3,1));
        hand.add(new Card(3,2));
        hand.add(new Card(3,1));
        hand.add(new Card(3,3));
        hand.add(new Card(3,1));
        hand.add(new Card(4,2));
        hand.add(new Card(4,4));
        //dummy hand to fail
        notPhase.add(new Card(3,1));
        notPhase.add(new Card(2,2));
        notPhase.add(new Card(2,1));
        notPhase.add(new Card(1,3));
        notPhase.add(new Card(4,1));
        notPhase.add(new Card(4,2));
        notPhase.add(new Card(4,4));
        //sorts both hands
        Card[] sorted = phase.sortCards(hand);
        Card[] bad = phase.sortCards(notPhase);
        //asserts true and false
        assertTrue(phase.checkIfPhaseNine(sorted,0));
        assertFalse(phase.checkIfPhaseNine(bad,0));
    }

    @Test
    public void checkIfPhaseTen() {
        //test for 1 set of 5 and 1 set of 3
        Phase phase = new Phase();
        ArrayList<Card> hand = new ArrayList<Card>();
        ArrayList<Card> notPhase = new ArrayList<Card>();
        //dummy hand to work
        hand.add(new Card(3,1));
        hand.add(new Card(3,2));
        hand.add(new Card(3,1));
        hand.add(new Card(3,3));
        hand.add(new Card(3,1));
        hand.add(new Card(4,2));
        hand.add(new Card(4,4));
        hand.add(new Card(4,4));
        //dummy hand to fail
        notPhase.add(new Card(3,1));
        notPhase.add(new Card(2,2));
        notPhase.add(new Card(2,1));
        notPhase.add(new Card(4,3));
        notPhase.add(new Card(4,1));
        notPhase.add(new Card(4,2));
        notPhase.add(new Card(4,4));
        notPhase.add(new Card(4,4));
        //sorts both hands
        Card[] sorted = phase.sortCards(hand);
        Card[] bad = phase.sortCards(notPhase);
        //asserts true and false
        assertTrue(phase.checkIfPhaseTen(sorted,0));
        assertFalse(phase.checkIfPhaseTen(bad,0));
    }



    @Test
    public void isColorGroup() {
        //tests if it is a group of all the same color (for phase 8)
        ArrayList<Card> hand = new ArrayList<Card>();
        Card[] expectedHand;
        Phase phase = new Phase();

        //dummy hand to make the color phase
        hand.add(new Card(5,1));
        hand.add(new Card(3,1));
        hand.add(new Card(1,1));
        hand.add(new Card(9,1));
        //sorts hand
        Card[] sorted = phase.sortCards(hand);
        //fills card array with expected values
        expectedHand = phase.isColorGroup(sorted, 4,1,false);
        //asserts the expected values
        assertEquals(expectedHand[0].getColor(),1);
        assertEquals(expectedHand[1].getColor(),1);
        assertEquals(expectedHand[2].getColor(),1);
        assertEquals(expectedHand[3].getColor(),1);

    }
    @Test
    public void sortCards() {
        ArrayList<Card> hand = new ArrayList<Card>();
        Card[] expectedHand;
        Phase phase = new Phase();
        //dummy hand to sort
        hand.add(new Card(5,1));
        hand.add(new Card(3,3));
        hand.add(new Card(1,4));
        hand.add(new Card(9,2));
        hand.add(new Card(2,1));
        hand.add(new Card(2,1));
        //creates the card array
        expectedHand = phase.sortCards(hand);
        //asserts the expected values
        assertEquals(expectedHand[0].getNumber(),1);
        assertEquals(expectedHand[1].getNumber(),2);
        assertEquals(expectedHand[2].getNumber(),2);
        assertEquals(expectedHand[3].getNumber(),3);
        assertEquals(expectedHand[4].getNumber(),5);
        assertEquals(expectedHand[5].getNumber(),9);
    }
}