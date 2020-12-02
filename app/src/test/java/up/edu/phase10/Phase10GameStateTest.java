package up.edu.phase10;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class Phase10GameStateTest {

    @Test
    public void drawFaceDown() {
        Phase10GameState state = new Phase10GameState();
        state.setTurnId(0);
        int origDrawSize = state.getDrawPile().size();
        int origHandSize = state.getPlayer1Hand().size();
        state.drawFaceDown(0);
        int endDrawSize = state.getDrawPile().size();
        int endHandSize = state.getPlayer1Hand().size();
        assertEquals(origDrawSize, endDrawSize + 1);
    }

    @Test
    public void drawFaceUp() {
        Phase10GameState state = new Phase10GameState();
        state.setTurnId(0);
        int origDrawSize = state.getDiscardPile().size();
        int origHandSize = state.getPlayer1Hand().size();
        Card drawn = state.getDiscardPile().peek();
        state.drawFaceUp(0);
        int endDrawSize = state.getDiscardPile().size();
        int endHandSize = state.getPlayer1Hand().size();
        Card got = state.getPlayer1Hand().get(state.getPlayer1Hand().size() - 1);
        assertEquals(drawn, got);
        assertEquals(origDrawSize, endDrawSize + 1);
        assertEquals(origHandSize, endHandSize - 1);
    }

    @Test
    public void discard() {
        Phase10GameState state = new Phase10GameState();
        state.setTurnId(0);
        state.setPlayerHasDrawn(true);
        int origDrawSize = state.getDiscardPile().size();
        int origHandSize = state.getPlayer1Hand().size();
        Card disc = state.getPlayer1Hand().get(0);
        state.discard(0, disc);
        int endDrawSize = state.getDiscardPile().size();
        int endHandSize = state.getPlayer1Hand().size();
        Card discarded = state.getDiscardPile().peek();
        assertEquals(origDrawSize, endDrawSize - 1);
        assertEquals(origHandSize, endHandSize + 1);
        assertEquals(disc, discarded);
    }

    @Test
    public void getPlayer1HasPhased() {
        Phase10GameState state = new Phase10GameState();
        ArrayList<Card> phaseContent = state.getPlayer1PhaseContent();
        boolean phased = state.getPlayer1HasPhased();
        assertEquals(phased, false);
        assertTrue(phaseContent.isEmpty());


    }

    @Test
    public void playPhase() {
        Phase10GameState state = new Phase10GameState();
        ArrayList<Card> givenHand = new ArrayList<Card>(); //dummy hand to make phase successful
        givenHand.add(new Card(1, 1));
        givenHand.add(new Card(1, 2));
        givenHand.add(new Card(1, 3));
        givenHand.add(new Card(1, 4));
        givenHand.add(new Card(2, 4));
        givenHand.add(new Card(2, 1));
        givenHand.add(new Card(2, 2));
        givenHand.add(new Card(2, 3));
        state.setPlayer1Hand(givenHand); //give player hand
        state.setPlayer1Phase(1); //make sure player at phase1
        state.setPlayerHasDrawn(true); //Make appropriate play progression

        ArrayList<Card> possiblePhase = new ArrayList<Card>(); //cards that are in p1's hand that work for phase 1
        possiblePhase.add(new Card(1, 1));
        possiblePhase.add(new Card(1, 2));
        possiblePhase.add(new Card(1, 3));
        possiblePhase.add(new Card(2, 1));
        possiblePhase.add(new Card(2, 2));
        possiblePhase.add(new Card(2, 3));

        //check phase starts empty and not phased
        ArrayList<Card> phaseContent = state.getPlayer1PhaseContent();
        boolean phased = state.getPlayer1HasPhased();
        assertFalse(phased);
        assertTrue(phaseContent.isEmpty());

        state.playPhase(0, possiblePhase); //play phase

        //update to new values and check change
        phaseContent = state.getPlayer1PhaseContent();
        phased = state.getPlayer1HasPhased();
        assertTrue(phased);
        assertEquals(phaseContent, possiblePhase);

    }

    @Test
    public void hitPlayer(){
        Phase10GameState state = new Phase10GameState();
        state.setTurnId(0);
        ArrayList<Card> givenHand = new ArrayList<Card>(); //dummy hand to make phase successful
        givenHand.add(new Card(1, 1));
        givenHand.add(new Card(1, 2));
        givenHand.add(new Card(1, 3));
        givenHand.add(new Card(1, 4));
        givenHand.add(new Card(2, 4));
        givenHand.add(new Card(2, 1));
        givenHand.add(new Card(2, 2));
        givenHand.add(new Card(2, 3));
        state.setPlayer1Hand(givenHand); //give players hand
        givenHand.add(new Card(1, 1));
        givenHand.add(new Card(1, 2));
        givenHand.add(new Card(1, 3));
        givenHand.add(new Card(1, 4));
        givenHand.add(new Card(2, 4));
        givenHand.add(new Card(2, 1));
        givenHand.add(new Card(2, 2));
        givenHand.add(new Card(2, 3));
        state.setPlayer2Hand(givenHand);
        state.setPlayerHasDrawn(true);
        state.setPlayer1Phase(1); //make sure players at phase1
        state.setPlayer2Phase(1);

        ArrayList<Card> possiblePhase = new ArrayList<Card>(); //cards that are in p1's hand that work for phase 1
        possiblePhase.add(new Card(1, 1));
        possiblePhase.add(new Card(1, 2));
        possiblePhase.add(new Card(1, 3));
        possiblePhase.add(new Card(2, 1));
        possiblePhase.add(new Card(2, 2));
        possiblePhase.add(new Card(2, 3));


        state.playPhase(0, possiblePhase); //play phase for p1
        state.setTurnId(1);
        state.playPhase(1, possiblePhase); //play phase for p2

        //orig conditions:
        int origPhaseSize = state.getPlayer2PhaseContent().size();

        state.setTurnId(0);
        //Now hit p1 on p2
        Card hitCard = new Card(1, 4); //label card for hit
        state.hitPlayer(0, hitCard, 1); //hit

        //post conditions:
        ArrayList<Card> p2PhasePost = state.getPlayer2PhaseContent();

        assertEquals(origPhaseSize, p2PhasePost.size() - 1); //check a card was added
        assertEquals(p2PhasePost.get(p2PhasePost.size() - 1), hitCard); //check it was correct card

    }
}