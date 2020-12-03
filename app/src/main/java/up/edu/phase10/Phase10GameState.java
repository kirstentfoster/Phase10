/**
 * @author Kirsten Foster, Alexis Molina, Emily Hoppe, Grace Penunuri
 * Game state holding information about each player and getters and setters for each variable
 * Draws the drawables in the correct position and assigns them values
 * Has the phasing, hitting, drawing and discrding methods for the actions
 *
 */
package up.edu.phase10;

import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;

import up.edu.phase10.Framework.GameState;
public class Phase10GameState extends GameState {

    //Resources
    private int turnId; //1 or 2 based on id of player turn
    private ArrayList<Card> player1Hand = new ArrayList<Card>(); //Player hands start at 10 cards
    private ArrayList<Card> player2Hand = new ArrayList<Card>();
    private ArrayList<Card> player1PhaseContent = new ArrayList<Card>();
    private ArrayList<Card> player2PhaseContent = new ArrayList<Card>();
    private ArrayList<Card> drawPile = new ArrayList<Card>(); //108 cards before dealing
    private Stack<Card> discardPile = new Stack<Card>();
    private boolean player1HasPhased;
    private boolean player2HasPhased;
    private boolean playerHasDrawn; //Only one draw allowed per round
    private int goesFirst; //Player Id of who goes first, alternates each round
    private int player1Score; //Lower scores are better
    private int player2Score;
    private int player1Phase; //Standard is 10 phases, optional: set different phases for game
    private int player2Phase;
    private int hasGoneOut; //set to -1 until a player goes out, then set to player Id
    private int turnStage; //1-4 corresponding to draw, phase, hit, and discard actions
    public Phase phase = new Phase();

    public ImageButton discardDraw = null;

    //Setters
    public void setTurnId(int turnId) {
        this.turnId = turnId;
    }

    public void setPlayerHasDrawn(boolean playerHasDrawn) {
        this.playerHasDrawn = playerHasDrawn;
    }

    public void setGoesFirst(int goesFirst) {
        this.goesFirst = goesFirst;
    }

    public void setHasGoneOut(int hasGoneOut) {
        this.hasGoneOut = hasGoneOut;
    }

    public void setDiscardPile(Stack<Card> discardPile) {
        this.discardPile = discardPile;
    }

    public void setDrawPile(ArrayList<Card> drawPile) {
        this.drawPile = drawPile;
    }

    public void setPlayer1HasPhased(boolean player1HasPhased) {
        this.player1HasPhased = player1HasPhased;
    }

    public void setPlayer2HasPhased(boolean player2HasPhased) {
        this.player2HasPhased = player2HasPhased;
    }

    public void setPlayer1PhaseContent(ArrayList<Card> player1PhaseContent) {
        this.player1PhaseContent = player1PhaseContent;
    }

    public void setPlayer2PhaseContent(ArrayList<Card> player2PhaseContent) {
        this.player2PhaseContent = player2PhaseContent;
    }

    public void setPlayer1Phase(int player1Phase) {
        this.player1Phase = player1Phase;
    }

    public void setPlayer2Phase(int player2Phase) {
        this.player2Phase = player2Phase;
    }

    public void setPlayer1Score(int player1Score) {
        this.player1Score = player1Score;
    }

    public void setPlayer2Score(int player2Score) {
        this.player2Score = player2Score;
    }

    public void setPlayer1Hand(ArrayList<Card> player1Hand) {
        this.player1Hand = player1Hand;
    }

    public void setPlayer2Hand(ArrayList<Card> player2Hand) {
        this.player2Hand = player2Hand;
    }

    public void setTurnStage(int turnStage){this.turnStage = turnStage;}

    //Getters
    public boolean getPlayerHasDrawn() {
        return this.playerHasDrawn;
    }

    public int getHasGoneOut() {
        return this.hasGoneOut;
    }

    public int getTurnId() {
        return this.turnId;
    }

    public Stack<Card> getDiscardPile() {
        return this.discardPile;
    }

    public int getGoesFirst() {
        return this.goesFirst;
    }

    public ArrayList<Card> getDrawPile() {
        return this.drawPile;
    }

    public int getPlayer1Score() {
        return this.player1Score;
    }

    public int getPlayer2Score() {
        return this.player2Score;
    }

    public int getPlayer1Phase() {
        return this.player1Phase;
    }

    public int getPlayer2Phase() {
        return this.player2Phase;
    }

    public ArrayList<Card> getPlayer1Hand() {
        return this.player1Hand;
    }

    public ArrayList<Card> getPlayer2Hand() {
        return this.player2Hand;
    }

    public ArrayList<Card> getPlayer1PhaseContent() {
        return this.player1PhaseContent;
    }

    public ArrayList<Card> getPlayer2PhaseContent() {
        return this.player2PhaseContent;
    }

    public boolean getPlayer1HasPhased() {
        return this.player1HasPhased;
    }

    public boolean getPlayer2HasPhased() {
        return this.player2HasPhased;
    }

    public Phase getPhase(){ return this.phase; }

    public int getTurnStage(){return this.turnStage;}

    /**
     * Constructor - Initializes variables with 0/null values
     * also initializes deck/hands with shuffled cards
     */
    public Phase10GameState() {
        turnId = (int) Math.random();
        hasGoneOut = -1;
        goesFirst = turnId;
        playerHasDrawn = false;
        player1HasPhased = false;
        player2HasPhased = false;
        player1Score = 0;
        player2Score = 0;
        player1Phase = 1;
        player2Phase = 1;
        turnStage = 1;
        phase = new Phase();
        for (int i = 1; i <= 12; i++) { //add colored cards to drawPile
            for (int j = 1; j <= 4; j++) {
                drawPile.add(new Card(i, j));
                drawPile.add(new Card(i, j));
            }
        }


        for (int i = 0; i < 8; i++) { //add wild cards (represented by 0,0)
            drawPile.add(new Card(100, 100));
        }
        for (int i = 0; i < 4; i++) {//add skip cards(represented by -1,-1)
            drawPile.add(new Card(-1, -1));
        }


        /**
         External Citation
         Date: 18 Oct 2020
         Problem: Wondering if there was a way to shuffle an array list
         Resource:
         https://www.roytuts.com/how-to-shuffle-an-arraylist-using-java
         /#:~:text=%20How%20to%20shuffle%20an%20ArrayList%20using%20Java,
         test%20the%20program.%20Remember%20on%20each...%20More%20
         Solution: I used the suggested method
         */

        Collections.shuffle(drawPile);//shuffle draw pile

        //deal cards to players and discard pile from draw pile
        discardPile.add(drawPile.get(0));
        drawPile.remove(0);
        for (int i = 0; i < 10; i++) {
            player1Hand.add(drawPile.get(0));
            drawPile.remove(0);
            player2Hand.add(drawPile.get(0));
            drawPile.remove(0);
        }
        Collections.sort(this.player1Hand); //sort cards to improve ease of play
        Collections.sort(this.player2Hand);

    }

    /**
     * Deep copy of all resources
     * @param PhaseGS used to send info to the phase game state class
     */
    public Phase10GameState(Phase10GameState PhaseGS) {
        //Copy primitive variables
        this.setTurnId(PhaseGS.getTurnId());
        this.setHasGoneOut(PhaseGS.getHasGoneOut());
        this.setGoesFirst(PhaseGS.getGoesFirst());
        this.setPlayerHasDrawn(PhaseGS.getPlayerHasDrawn());
        this.setPlayer1HasPhased(PhaseGS.getPlayer1HasPhased());
        this.setPlayer2HasPhased(PhaseGS.getPlayer2HasPhased());
        this.setPlayer1Score(PhaseGS.getPlayer1Score());
        this.setPlayer2Score(PhaseGS.getPlayer2Score());
        this.setPlayer1Phase(PhaseGS.getPlayer1Phase());
        this.setPlayer2Phase(PhaseGS.getPlayer2Phase());
        this.setTurnStage(PhaseGS.getTurnStage());

        //set all array lists and stacks using for each loops
        Stack<Card> temp = new Stack<Card>();
        for(Card c : PhaseGS.getDiscardPile()){
            temp.push(new Card(c.getNumber(),c.getColor()));
        }
        this.setDiscardPile(temp);

        ArrayList<Card> temp1 = new ArrayList<Card>();
        for(Card c : PhaseGS.getDrawPile()){
            temp1.add(new Card(c.getNumber(),c.getColor()));
        }
        this.setDrawPile(temp1);

        ArrayList<Card> temp3 = new ArrayList<Card>();
        for(Card c : PhaseGS.getPlayer1Hand()){
            temp3.add(new Card(c.getNumber(),c.getColor()));
        }
        this.setPlayer1Hand(temp3);

        ArrayList<Card> temp4 = new ArrayList<Card>();
        for(Card c : PhaseGS.getPlayer2Hand()){
            temp4.add(new Card(c.getNumber(),c.getColor()));
        }
        this.setPlayer2Hand(temp4);

        ArrayList<Card> temp5 = new ArrayList<Card>();
        for(Card c : PhaseGS.getPlayer1PhaseContent()){
            temp5.add(new Card(c.getNumber(),c.getColor()));
        }
        this.setPlayer1PhaseContent(temp5);

        ArrayList<Card> temp6 = new ArrayList<Card>();
        for(Card c : PhaseGS.getPlayer2PhaseContent()){
            temp6.add(new Card(c.getNumber(),c.getColor()));
        }
        this.setPlayer2PhaseContent(temp6);
        this.phase = new Phase(PhaseGS.phase);
    }

    /**
     * attempts to draw a face down card from the draw pile and add it to the player's hand
     * player does not know what the card is until they draw it
     *
     * @param playerId id of the player attempting to draw, this param could be replaced by a player object later on,
     *                 in which case a .getId() call would be added to the method
     * @return true if the action was successful, else will return false
     */
    public boolean drawFaceDown(int playerId) {

        if (playerId != this.turnId || this.drawPile.size() <= 0 || this.hasGoneOut == playerId || this.playerHasDrawn) {
            return false;
        }

        Card drawn = this.drawPile.remove(0); //Remove top card from draw pile

        if(drawPile.isEmpty()){//shuffle discard into draw pile if they drew the final draw card
            Iterator<Card> it = discardPile.iterator();
            int i = 0;
            while(it.hasNext()){
                Card c = it.next();
                drawPile.add(c);
                i++;
            }
            for(; i > 0; i--){
                discardPile.pop();
            }
            Collections.shuffle(drawPile);
            discardPile.add(drawPile.get(0));
            drawPile.remove(0);
        }

        this.playerHasDrawn = true;

        //determine which player hand it goes to
        if (playerId == 0) {
            this.player1Hand.add(drawn); //add to hand
            turnStage++;
            return true;
        } else if (playerId == 1) {
            this.player2Hand.add(drawn); //add to hand
            turnStage++;
            return true;
        } else{
            turnStage++;
            return false;
        }
    }//drawFaceDown

    /**
     * attempts to draw a face up card from the discard pile and add it to the player's hand
     * the card drawn will be the last card discarded onto the pile, so player knows which card they are drawing
     *
     * @param playerId id of the player attempting to draw, this param could be replaced by a player object later on,
     *                 in which case a .getId() call would be added to the method
     * @return true if the action was successful, else will return false
     */
    public boolean drawFaceUp(int playerId) {

        if (playerId != this.turnId || this.discardPile.size() <= 0 || this.hasGoneOut == playerId || this.playerHasDrawn)
            return false;

        Card drawn = this.discardPile.pop(); //remove top card from discard

        this.playerHasDrawn = true;

        //determine which player hand it goes to
        if (playerId == 0) {
            this.player1Hand.add(drawn); //add to hand
            turnStage++;
            return true;
        } else if (playerId == 1) {
            this.player2Hand.add(drawn); //add to hand
            turnStage++;
            return true;
        } else{
            turnStage++;
            return false;
        }
    }//drawFaceUp

    /**
     * attempts to discard a card from player hand to the discard pile
     *
     * @param playerId id of the player attempting to discard, this param could be replaced by a player object later on,
     *                 in which case a .getId() call would be added to the method
     * @param card the card being discarded
     * @return true if the action was successful, else will return false
     */
    public boolean discard(int playerId, Card card) {
        if (playerId != this.turnId || this.hasGoneOut == playerId || !this.playerHasDrawn) return false; //Exit if discard is illegal
        int cardLoc = 0;
        boolean notFound = true;
        boolean exit = false;
        while (notFound && !exit) { //Find card location in the hand
            if (playerId == 0) { //Player 1
                for (int i = 0; i < this.player1Hand.size(); i++) {
                    if (card.getNumber()==this.player1Hand.get(i).getNumber() && card.getColor() == this.player1Hand.get(i).getColor()) {
                        cardLoc = i;
                        notFound = false;
                    }
                }
                exit = true;
            } else if (playerId == 1) { //Player 2
                for (int i = 0; i < this.player2Hand.size(); i++) {
                    if (card.getNumber()==this.player2Hand.get(i).getNumber() && card.getColor() == this.player2Hand.get(i).getColor()) {
                        cardLoc = i;
                        notFound = false;
                    }
                }
                exit = true;
            } else{
                return false;
            }
        }
        if(notFound){ //Error quit
            turnId++;
            if(turnId == 2) turnId = 0;
        }

        //determine which player is discarding
        if (playerId == 0) {
            if (this.player1Hand.size() < cardLoc){
                return false;
            }

            discardPile.push(this.player1Hand.remove(cardLoc)); //discard to discard pile

            if (this.player1Hand.size() == 0 && this.hasGoneOut < 0)
                this.hasGoneOut = playerId; //If a player's hand is empty, the other player gets one turn before round ends
            if (!discardPile.peek().isSkip()) this.turnId = 1; //Skips in 2 player mode allow current player to take 2 back-to-back turns
            turnStage = 1;
            this.playerHasDrawn = false;
            Collections.sort(this.player1Hand); //sort cards to improve ease of play
            Collections.sort(this.player2Hand);
            return true;
        } else if (playerId == 1) {
            if (this.player2Hand.size() < cardLoc){
                return false;
            }

            discardPile.push(this.player2Hand.remove(cardLoc)); //discard to discard pile

            if (this.player2Hand.size() == 0 && this.hasGoneOut < 0) this.hasGoneOut = playerId;
            if (!discardPile.peek().isSkip()) this.turnId = 0;

            turnStage = 1;
            this.playerHasDrawn = false;
            Collections.sort(this.player1Hand); //sort cards to improve ease of play
            Collections.sort(this.player2Hand);
            return true;
        } else {
            return false;
        }

    }//discard

    /**
     * converts all information to a string
     * @return prints out values of all variables
     */
    public String toString() {

        //NOTE: Cards are randomized each round so player hands will be different, but size will be the same
        return "Turn ID: " + turnId + "\nHas Gone Out: " + hasGoneOut + "\nGoes First: " + goesFirst + "\nPlayer Has Drawn: " + playerHasDrawn +
                "\nP1 has Phased: " + player1HasPhased + "\nP2 has Phased: " + player2HasPhased + "\nP1 Score: " + player1Score + "\nP2 Score: " + player2Score +
                "\nP1 Phase: " + player1Phase + "\nP2 Phase: " + player2Phase + "\nP1 Hand Size: " + player1Hand.size() + "\nP2 Hand: " + player2Hand.size() +
                "\nP1 Phase Content: " + player1PhaseContent.size() + "\nP2 Phase Content: " + player2PhaseContent.size() + "\nDiscard Pile: " + discardPile.size() +
                "\nDraw Pile Size: " + drawPile.size();
    }

    /**
     * attempts to play a phase and
     * @param playerNum the player who is trying to play a phase
     * @param phaseContent the cards that are trying to be phased
     * @return if phasing was successful
     */
    public boolean playPhase(int playerNum, ArrayList<Card> phaseContent) {
        if(!playerHasDrawn) { //not player turn
            return false;
        }
        //checks if valid, player num == playerId, needs to have not phased
        if (playerNum == 0) {
            if (phase.checkPhase(player1Phase, phaseContent, playerNum) && !player1HasPhased) { //see if phase is legal
                for (Card c : phaseContent) { // match phase content to locations in player hand
                    int j = 0;
                    for(int i=0; i<player1Hand.size(); i++){
                        if(player1Hand.get(i).getColor()==c.getColor() && player1Hand.get(i).getNumber()==c.getNumber() || (player1Hand.get(i).isWild() && c.isWild())){
                            j = i;
                            break;
                        }
                    }
                    player1Hand.remove(j);
                    player1PhaseContent.add(c);
                }
                turnStage++;
                player1HasPhased = true;
                return true;
            }
        } else if (playerNum == 1) {
            if (phase.checkPhase(player2Phase, phaseContent, playerNum) && !player2HasPhased) {//see if phase is legal
                for (Card c : phaseContent) {// match phase content to locations in player hand
                    int j = 0;
                    for(int i=0; i<player2Hand.size(); i++){
                        if(player2Hand.get(i).getColor()==c.getColor() && player2Hand.get(i).getNumber()==c.getNumber() || (player2Hand.get(i).isWild() && c.isWild())){
                            j = i;
                            break;
                        }
                    }
                    player2Hand.remove(j);
                    player2PhaseContent.add(c);
                }
                turnStage++;
                player2HasPhased = true;
                return true;
            }
        }
        turnStage++;
        return false;
    }//playPhase

    /**
     * checks if hit is valid and plays hit if it is
     * @param playerNum holds which player is currently playing
     * @param selectedCard holds what card they're using to hit
     * @param hitOnPlayer holds which player they're trying to hit
     * @return if hit successful
     */
    public boolean hitPlayer(int playerNum, Card selectedCard, int hitOnPlayer) {
        if(!playerHasDrawn){ //Illegal move, exit
            return false;
        }
        if (playerNum == this.turnId) {
            if (hitOnPlayer != playerNum) { //if this is false in a 2 player game, player is hitting on opposite player phase
                if (playerNum == 0 && player1HasPhased) {
                    if (player2HasPhased) {
                        if (phase.checkHitValid(selectedCard, hitOnPlayer, false)) {
                            player2PhaseContent.add(selectedCard);
                            int j = 0;
                            for (int i = 0; i < player1Hand.size(); i++) { //Find card location
                                if (player1Hand.get(i).getColor() == selectedCard.getColor() &&
                                        player1Hand.get(i).getNumber() == selectedCard.getNumber()
                                        || (player1Hand.get(i).isWild() && selectedCard.isWild())) {
                                    j = i;
                                    break;
                                }
                            }
                            player1Hand.remove(j);
                            if (player1Hand.size() == 0) {
                                if (hasGoneOut < 0) this.hasGoneOut = 0;
                                this.turnId = 1;
                                this.playerHasDrawn = false;
                                this.turnStage = 1;
                            }
                            return true;
                        } else {
                            return false;
                        }
                    }
                } else if (playerNum == 1 && player2HasPhased) {
                    if (player1HasPhased) {
                        if (phase.checkHitValid(selectedCard, hitOnPlayer, false)) {
                            player1PhaseContent.add(selectedCard);
                            int j = 0;
                            for (int i = 0; i < player2Hand.size(); i++) {//Find card location
                                if (player2Hand.get(i).getColor() == selectedCard.getColor() &&
                                        player2Hand.get(i).getNumber() == selectedCard.getNumber()
                                        || (player2Hand.get(i).isWild() && selectedCard.isWild())) {
                                    j = i;
                                    break;
                                }
                            }
                            player2Hand.remove(j);
                            if (player2Hand.size() == 0) {
                                if (hasGoneOut < 0) this.hasGoneOut = 1;
                                this.turnId = 0;
                                this.playerHasDrawn = false;
                                this.turnStage = 1;
                            }
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            } else if (hitOnPlayer == playerNum) { // if hitOnPlayer is the same as playerNum then the player hits on their own phaseContext
                if (playerNum == 0) {
                    if (player1HasPhased) {
                        if (phase.checkHitValid(selectedCard, hitOnPlayer, false)) {
                            player1PhaseContent.add(selectedCard);
                            int j = 0;
                            for (int i = 0; i < player1Hand.size(); i++) {//Find card location
                                if (player1Hand.get(i).getColor() == selectedCard.getColor()
                                        && player1Hand.get(i).getNumber() == selectedCard.getNumber()
                                        || (player1Hand.get(i).isWild() && selectedCard.isWild())) {
                                    j = i;
                                    break;
                                }
                            }
                            player1Hand.remove(j);
                            if (player1Hand.size() == 0) {
                                if (hasGoneOut < 0) this.hasGoneOut = 0;
                                this.turnId = 1;
                                this.playerHasDrawn = false;
                                this.turnStage = 1;
                            }
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
                if (playerNum == 1) {
                    if (player2HasPhased) {
                        if (phase.checkHitValid(selectedCard, hitOnPlayer, false)) {
                            player2PhaseContent.add(selectedCard);
                            int j = 0;
                            for (int i = 0; i < player2Hand.size(); i++) {//Find card location
                                if (player2Hand.get(i).getColor() == selectedCard.getColor()
                                        && player2Hand.get(i).getNumber() == selectedCard.getNumber()
                                        || (player2Hand.get(i).isWild() && selectedCard.isWild())) {
                                    j = i;
                                    break;
                                }
                            }
                            player2Hand.remove(j);
                            if (player2Hand.size() == 0) {
                                if (hasGoneOut < 0) this.hasGoneOut = 1;
                                this.turnId = 0;
                                this.playerHasDrawn = false;
                                this.turnStage = 1;
                            }
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }//hitPlayer

    /**
     *sends the id of the card to make it drawn on the top of the discard pile
     * @param MA instance of GameFrameworkMainActivity to find discardPile used in the game and peek into it
     */
    public void drawDiscard(MainActivity MA){
        discardDraw = MA.findViewById(R.id.DiscardPile);
        if(discardPile.size()==0){
            /**
             External Citation
             Date: 11/20/20
             Problem: I did not want to make my own empty discard slot drawable

             Resource: https://keepcalms.com/p/there-s-nothing-left-here/
             Solution: I used this one
             */
            discardDraw.setImageResource(R.drawable.empty);
        }
        else {
            int id = testSlot(this.discardPile.peek());
            discardDraw.setImageResource(id);
        }
    }//drawDiscard

    /**
     * takes card drawn and tests to see what the new top of the discard pile should be set to
     * @param card the card that is dealt
     * @return id of the card drawable
     */
    public int testSlot(Card card){
        if(card.isSkip()){
            return R.drawable.skip;
        }
        else if(card.isWild()){
            return R.drawable.wild;
        }
        else if(card.getColor() == 1){
            if(card.getNumber()==1){
                return R.drawable.red1;
            }
            else if(card.getNumber()==2){
                return R.drawable.red2;
            }
            else if(card.getNumber()==3){
                return R.drawable.red3;
            }
            else if(card.getNumber()==4){
                return R.drawable.red4;
            }
            else if(card.getNumber()==5){
                return R.drawable.red5;
            }
            else if(card.getNumber()==6){
                return R.drawable.red6;
            }
            else if(card.getNumber()==7){
                return R.drawable.red7;
            }
            else if(card.getNumber()==8){
                return R.drawable.red8;
            }
            else if(card.getNumber()==9){
                return R.drawable.red9;
            }else if(card.getNumber()==10){
                return R.drawable.red10;
            }
            else if(card.getNumber()==11){
                return R.drawable.red11;
            }
            else if(card.getNumber()==12){
                return R.drawable.red12;
            }
        }
        else if(card.getColor() == 2){
            if(card.getNumber()==1){
                return R.drawable.blue1;
            }
            else if(card.getNumber()==2){
                return R.drawable.blue2;
            }
            else if(card.getNumber()==3){
                return R.drawable.blue3;
            }
            else if(card.getNumber()==4){
                return R.drawable.blue4;
            }
            else if(card.getNumber()==5){
                return R.drawable.blue5;
            }
            else if(card.getNumber()==6){
                return R.drawable.blue6;
            }
            else if(card.getNumber()==7){
                return R.drawable.blue7;
            }
            else if(card.getNumber()==8){
                return R.drawable.blue8;
            }
            else if(card.getNumber()==9){
                return R.drawable.blue9;
            }else if(card.getNumber()==10){
                return R.drawable.blue10;
            }
            else if(card.getNumber()==11){
                return R.drawable.blue11;
            }
            else if(card.getNumber()==12){
                return R.drawable.blue12;
            }
        }
        else if(card.getColor() == 3){
            if(card.getNumber()==1){
                return R.drawable.green1;
            }
            else if(card.getNumber()==2){
                return R.drawable.green2;
            }
            else if(card.getNumber()==3){
                return R.drawable.green3;
            }
            else if(card.getNumber()==4){
                return R.drawable.green4;
            }
            else if(card.getNumber()==5){
                return R.drawable.green5;
            }
            else if(card.getNumber()==6){
                return R.drawable.green6;
            }
            else if(card.getNumber()==7){
                return R.drawable.green7;
            }
            else if(card.getNumber()==8){
                return R.drawable.green8;
            }
            else if(card.getNumber()==9){
                return R.drawable.green9;
            }else if(card.getNumber()==10){
                return R.drawable.green10;
            }
            else if(card.getNumber()==11){
                return R.drawable.green11;
            }
            else if(card.getNumber()==12){
                return R.drawable.green12;
            }
        }
        else if(card.getColor() == 4){
            if(card.getNumber()==1){
                return R.drawable.yellow1;
            }
            else if(card.getNumber()==2){
                return R.drawable.yellow2;
            }
            else if(card.getNumber()==3){
                return R.drawable.yellow3;
            }
            else if(card.getNumber()==4){
                return R.drawable.yellow4;
            }
            else if(card.getNumber()==5){
                return R.drawable.yellow5;
            }
            else if(card.getNumber()==6){
                return R.drawable.yellow6;
            }
            else if(card.getNumber()==7){
                return R.drawable.yellow7;
            }
            else if(card.getNumber()==8){
                return R.drawable.yellow8;
            }
            else if(card.getNumber()==9){
                return R.drawable.yellow9;
            }else if(card.getNumber()==10){
                return R.drawable.yellow10;
            }
            else if(card.getNumber()==11){
                return R.drawable.yellow11;
            }
            else if(card.getNumber()==12){
                return R.drawable.yellow12;
            }
        }
        return -1;
    } //testSlot
}
