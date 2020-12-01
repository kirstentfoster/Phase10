package up.edu.phase10;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class Phase10GameStateTest {

    @Test
    public void drawFaceDown() {
        Phase10GameState state = new Phase10GameState();
        state.setTurnId(1);
        int origDrawSize = state.getDrawPile().size();
        int origHandSize = state.getPlayer1Hand().size();
        state.drawFaceDown(1);
        int endDrawSize = state.getDrawPile().size();
        int endHandSize = state.getPlayer1Hand().size();
        assertEquals(origDrawSize, endDrawSize + 1);
        assertEquals(origHandSize, endHandSize - 1);
    }

    @Test
    public void drawFaceUp() {
        Phase10GameState state = new Phase10GameState();
        state.setTurnId(1);
        int origDrawSize = state.getDiscardPile().size();
        int origHandSize = state.getPlayer1Hand().size();
        Card drawn = state.getDiscardPile().peek();
        state.drawFaceUp(1);
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
        state.setTurnId(1);
        int origDrawSize = state.getDiscardPile().size();
        int origHandSize = state.getPlayer1Hand().size();
        Card disc = state.getPlayer1Hand().get(0);
        state.discard(1, disc);
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
        assertEquals(phased, false);
        assertTrue(phaseContent.isEmpty());

        state.playPhase(1, possiblePhase); //playphase

        //update to new values and check change
        phaseContent = state.getPlayer1PhaseContent();
        phased = state.getPlayer1HasPhased();
        assertEquals(phased, true);
        assertEquals(phaseContent, possiblePhase);

    }

    @Test
    public void hitPlayer() {
        Phase10GameState state = new Phase10GameState();
        state.setTurnId(1);
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
        state.setPlayer2Hand(givenHand);
        state.setPlayer1Phase(1); //make sure players at phase1
        state.setPlayer2Phase(1);

        ArrayList<Card> possiblePhase = new ArrayList<Card>(); //cards that are in p1's hand that work for phase 1
        possiblePhase.add(new Card(1, 1));
        possiblePhase.add(new Card(1, 2));
        possiblePhase.add(new Card(1, 3));
        possiblePhase.add(new Card(2, 1));
        possiblePhase.add(new Card(2, 2));
        possiblePhase.add(new Card(2, 3));


        state.playPhase(1, possiblePhase); //playphase for p1
        state.setTurnId(2);
        state.playPhase(2, possiblePhase); //playphase for p2

        //orig conditions:
        int origPhaseSize = state.getPlayer2PhaseContent().size();

        state.setTurnId(1);
        //Now hit p1 on p2
        Card hitCard = new Card(1, 4); //label card for hit
        state.hitPlayer(1, hitCard, 2); //hit

        //post conditions:
        ArrayList<Card> p2PhasePost = state.getPlayer2PhaseContent();

        assertEquals(origPhaseSize, p2PhasePost.size() - 1); //check a card was added
        assertEquals(p2PhasePost.get(p2PhasePost.size() - 1), hitCard); //check it was correct card

    }
}