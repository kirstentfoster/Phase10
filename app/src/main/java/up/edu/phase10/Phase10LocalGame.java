/**
 * @author Kirsten Foster, Alexis Molina, Emily Hoppe, Grace Penunuri
 * this class handles the local game that is being played, makes sure the
 * current player can move, that the move it make is executed, checks if the
 * gamme is over and that the updated state is sent to the game
 *
 */

package up.edu.phase10;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import up.edu.phase10.Framework.GamePlayer;
import up.edu.phase10.Framework.LocalGame;
import up.edu.phase10.Framework.GameAction;
import up.edu.phase10.Framework.GameState;

public class Phase10LocalGame extends LocalGame {


    private Phase10GameState pgs;

    /**
     * This constructor creates a new game state
     */
    public Phase10LocalGame() {
        this.pgs = new Phase10GameState();
    }

    /**
     * can the player with the given id take an action right now?
     * @param playerIdx
     * 		the player's player-number (ID)
     * @return true if the can move  or false if the can not move.
     */
    @Override
    protected boolean canMove(int playerIdx) {
        if (playerIdx == this.pgs.getTurnId()) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * This method is called when a new action arrives from a player
     *
     * @param action
     * 			The move that the player has sent to the game
     * @return true if the action was taken or false if the action was invalid/illegal.
     */
    @Override
    protected boolean makeMove(GameAction action) {
        if (action instanceof PhaseAction) {
            this.pgs.playPhase(this.pgs.getTurnId(), ((PhaseAction) action).getPhaseContent());
            return true;
        } else if (action instanceof DiscardAction) {
            this.pgs.discard(this.pgs.getTurnId(), ((DiscardAction) action).getCard());
            this.roundOver();
            return true;
        } else if (action instanceof HitAction) {
            this.pgs.hitPlayer(this.pgs.getTurnId(), ((HitAction) action).getCard(), ((HitAction) action).getHitPlayer());
            return true;
        } else if (action instanceof DrawFaceDownAction) {
            this.pgs.drawFaceDown(this.pgs.getTurnId());
            return true;
        } else if (action instanceof DrawFaceUpAction) {
            this.pgs.drawFaceUp(this.pgs.getTurnId());
            return true;
        }
        return false;
    }//makeMove

    /**
     * send the updated state to a given player
     * @param p updated info
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        Phase10GameState cp = new Phase10GameState(this.pgs);
        p.sendInfo(cp);
    }//sendUpdatedSate

    /**
     * Check if the game is over
     *
     * @return
     * 		a message that tells who has won the game, or null if the
     * 		game is not over
     */
    @Override
    protected String checkIfGameOver() {
        int s0 = this.pgs.getPlayer1Phase();
        int s1 = this.pgs.getPlayer2Phase();
        boolean oneDone = false;
        boolean twoDone = false;
        if(s0 == 10 && this.pgs.getPlayer1HasPhased()){
            oneDone = true;
        }
        if(s1 == 10 && this.pgs.getPlayer2HasPhased()){
            twoDone = true;
        }
        if(oneDone && !twoDone) { //Player one finished phase 10 and player two did not
            return this.playerNames[0]+ " wins!";
        }
        else if (twoDone && !oneDone) { //Player tewo finished phase 10 and player one did not
            return this.playerNames[1]+ " wins!";
        }
        else if(oneDone && twoDone){ //Both finished phase 10, lowest score wins
            //Calculate scores
            if(pgs.getPlayer1Hand() != null && pgs.getPlayer1Hand().size() != 0) {
                for (Card c : pgs.getPlayer1Hand()) {
                    pgs.setPlayer1Score(pgs.getPlayer1Score() + c.getScore());
                }
            }
            if(pgs.getPlayer2Hand() != null && pgs.getPlayer2Hand().size() != 0) {
                for (Card c : pgs.getPlayer2Hand()) {
                    pgs.setPlayer1Score(pgs.getPlayer2Score() + c.getScore());
                }
            }
            if(pgs.getPlayer1Score() > pgs.getPlayer2Score()){
                return this.playerNames[1]+ " wins!";
            }
            else if(pgs.getPlayer2Score() > pgs.getPlayer1Score()){
                return this.playerNames[0]+ " wins!";
            }
            else{
                return "It's a tie!";
            }
        }
        else return null;
    }
    /**
     * ROUND HANDLING - if a player has gone out and the other player has done their last turn
     * then the round is over, and the board has to be reset and the scores updated
     *
     * @return true if round over, false if not
     */
    private boolean roundOver(){
        //Player has gone out
        if(pgs.getHasGoneOut() < 0 || pgs.getTurnId() != pgs.getHasGoneOut() ){
            return false;
        }
        //Get the hands and calculate scores
        if(pgs.getPlayer1Hand() != null && pgs.getPlayer1Hand().size() != 0) {
            for (Card c : pgs.getPlayer1Hand()) {
                pgs.setPlayer1Score(pgs.getPlayer1Score() + c.getScore());
            }
        }
        if(pgs.getPlayer2Hand() != null && pgs.getPlayer2Hand().size() != 0) {
            for (Card c : pgs.getPlayer2Hand()) {
                pgs.setPlayer1Score(pgs.getPlayer2Score() + c.getScore());
            }
        }
        //Check if game over
        if(checkIfGameOver() == null){
            //Increase phases
            if(pgs.getPlayer1HasPhased()) pgs.setPlayer1Phase(pgs.getPlayer1Phase()+1);
            if(pgs.getPlayer2HasPhased()) pgs.setPlayer2Phase(pgs.getPlayer2Phase()+1);
            //Clear phase contents
            if(pgs.getPlayer1PhaseContent().size() != 0) pgs.setPlayer1PhaseContent(new ArrayList<Card>());
            if(pgs.getPlayer2PhaseContent().size() != 0) pgs.setPlayer2PhaseContent(new ArrayList<Card>());
            //Reset variables
            //TODO allow AI to go first
            if(pgs.getGoesFirst() == 0) pgs.setTurnId(1);
            else if(pgs.getGoesFirst() == 1) pgs.setTurnId(0);
            pgs.setHasGoneOut(-1);
            pgs.setPlayerHasDrawn(false);
            pgs.setPlayer1HasPhased(false);
            pgs.setPlayer2HasPhased(false);
            pgs.setTurnStage(1);
            //Re-deal Cards
            ArrayList<Card> tempDeck = new ArrayList<Card>();
            for (int i = 1; i <= 12; i++) { //add colored cards to drawPile
                for (int j = 1; j <= 4; j++) {
                    tempDeck.add(new Card(i, j));
                    tempDeck.add(new Card(i, j));
                }
            }
            //TODO for Beta
//            for (int i = 0; i < 8; i++) { //add wild cards (represented by 0,0) //NOT IMPLEMENTED IN ALPHA
//            tempDeck.add(new Card(0, 0));
//        }
//            for (int i = 0; i < 4; i++) {//add skip cards(represented by -1,-1)
//                tempDeck.add(new Card(-1, -1));
//            }
            Collections.shuffle(tempDeck);
            Stack<Card> tempDiscard = new Stack<Card>();
            tempDiscard.push(tempDeck.get(0));
            tempDiscard.push(tempDeck.get(1));
            tempDeck.remove(0);
            tempDeck.remove(1);
            ArrayList<Card> tempP1Hand = new ArrayList<Card>();
            ArrayList<Card> tempP2Hand = new ArrayList<Card>();
            for (int i = 0; i < 10; i++) {
                tempP1Hand.add(tempDeck.get(0));
                tempDeck.remove(0);
                tempP2Hand.add(tempDeck.get(0));
                tempDeck.remove(0);
            }
            pgs.setDrawPile(tempDeck);
            pgs.setDiscardPile(tempDiscard);
            pgs.setPlayer1Hand(tempP1Hand);
            pgs.setPlayer2Hand(tempP2Hand);
            return false;
        }

        return true;
    }

}// class LocalGame