/**
 * @author Kirsten Foster, Alexis Molina, Emily Hoppe, Grace Penunuri
 * Where the AI methods are
 * Creates an smart AI for the use to play against
 * Should be able to do all actions necessary to complete games
 */
package up.edu.phase10;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import up.edu.phase10.Framework.GameComputerPlayer;
import up.edu.phase10.Framework.GameInfo;

//Think about one card is best groups

public class DumbComputerPlayer extends GameComputerPlayer /*extends blah blah blah*/ {

    private ArrayList<Card> hitList = null;
    private ArrayList<Integer> whereToHitList = null;
    private ArrayList<ArrayList<Card>> viableGroups1 = null;
    private ArrayList<Card> completeGroup1 = null;


    private ArrayList<ArrayList<Card>> viableGroups2 = null;
    private ArrayList<Card> completeGroup2 = null;
    private ArrayList<Card> nonGroupCards = null;


    public DumbComputerPlayer(String name) {
        super(name);
    }

    /**
     * receives information from game
     *
     * @param info the received info (gameState)
     */
    protected void receiveInfo(GameInfo info) {
        Random r = new Random();
        if (!(info instanceof Phase10GameState)) return; //Somethings wrong, exit
        clearVars();
        Phase10GameState copy = (Phase10GameState) info; //Shallow copy

        if (copy.getTurnId() != this.playerNum) return;
        // gameFramework uses 0/1 player ID
        // Phase 10 code handles based on 1/2 player ID

        Log.d("Dumb AI", "Enter receiveInfo()");
        boolean hasPhased = false;
        int phase = 0;
        ArrayList<Card> fullHand = null;
        ArrayList<Card> hand = null;

        if (this.playerNum == 0) {

            //Deep copy into a usable temporary hand
            hand = new ArrayList<Card>();
            Iterator<Card> it = copy.getPlayer1Hand().iterator();
            while (it.hasNext()) {
                Card b = it.next();
                Card c = new Card(b.getNumber(), b.getColor());
                hand.add(new Card(c.getNumber(), c.getColor()));
            }

            //Separated to eliminate redundant playerNum tests/
            hasPhased = copy.getPlayer1HasPhased();
            phase = copy.getPlayer1Phase();
            fullHand = copy.getPlayer1Hand();

            //Sort groups
            if(!hasPhased) {
                Collections.sort(hand);
                if (phase == 8) hand = sortColor(hand);
                sortGroups(hand, copy.getPlayer1Phase(), fullHand, copy);
            }
            else{
                this.nonGroupCards = hand;
                makeHits(copy, copy.getPlayer1HasPhased(), copy.getPlayer2HasPhased());
            }
        } else if (this.playerNum == 1) {

            //Deep copy into a usable temporary hand
            hand = new ArrayList<Card>();
            Iterator<Card> it = copy.getPlayer2Hand().iterator();
            while (it.hasNext()) {

                Card b = it.next();
                Card c = new Card(b.getNumber(), b.getColor());
                hand.add(new Card(c.getNumber(), c.getColor()));
            }

            //Separated to eliminate redundant playerNum tests
            hasPhased = copy.getPlayer2HasPhased();
            phase = copy.getPlayer2Phase();
            fullHand = copy.getPlayer2Hand();

            //Sort groups
            if(!hasPhased) {
                Collections.sort(hand);
                if (phase == 8) hand = sortColor(hand);
                sortGroups(hand, copy.getPlayer2Phase(), fullHand, copy);
            }
            else{
                this.nonGroupCards = hand;
                makeHits(copy, copy.getPlayer1HasPhased(), copy.getPlayer2HasPhased());
            }

        }

        /* DRAW */
        if(copy.getTurnStage() == 1) {
            doDraw(copy, hasPhased, phase, fullHand);
            copy.setTurnStage(2);
            Log.d("Dumb AI", "Exit receiveInfo()");
            return;
        }

        /* PHASE */
        if(copy.getTurnStage() == 2) {
            if (!hasPhased) {
                if (checkPhaseReady(phase)) {
                    boolean phased = doPhase(phase); //Phase action in here
                    hasPhased = phased;
                    if(phased) copy.setTurnStage(3);
                    else copy.setTurnStage(4);
                    Log.d("Dumb AI", "Exit receiveInfo()");
                    return;
                }
            }
            copy.setTurnStage(3);

        }


        /* HIT */
        if(copy.getTurnStage() == 3) {
            if (hasPhased && checkHitsExist()) {
                int v = r.nextInt(1);
                if(v==0) {
                    doHits(phase, fullHand); //Hit action in here
                }
                else{
                    doDiscard(copy, hasPhased);
                }
                Log.d("Dumb AI", "Exit receiveInfo()");
                if(!checkHitsExist())copy.setTurnStage(4);
                return;
            }
            else copy.setTurnStage(4);

        }

        /* DISCARD */
        if(copy.getTurnStage() == 4) {
            doDiscard(copy, hasPhased);
            if (hand != null || hand.size() != 0) {
                Iterator<Card> it = hand.iterator();
                while (it.hasNext()) {
                    Card c = it.next();
                    if (c.equals(copy.getDiscardPile().peek())) {
                        hand.remove(c);
                        break;
                    }
                }
            }
        }
        Log.d("Dumb AI", "Exit receiveInfo()");
        // count++;
        return;
    }

    /**
     * clears the variables of this class
     */
    private void clearVars(){
        Log.d("Dumb AI", "Enter clearVars()");
        this.hitList = null;
        this.whereToHitList = null;

        this.viableGroups1 = null;
        this.completeGroup1 = null;

        this.viableGroups2 = null;
        this.completeGroup2 = null;
        this.nonGroupCards = null;
        Log.d("Dumb AI", "Exit clearVars()");
    }

    /**
     * SortGroups organizes the cards in the AI's hand into priority groups based on how close they are to being a
     * complete phase requirement
     *
     * @param hand the AI's hand that provides sortable cards (deep)
     * @param phase the phase the AI is currently on
     * @param fullHand (shallow) copy AI hand
     * @param gameState the gameState (shallow)
     * @return true if sort successful
     */
    private boolean sortGroups(ArrayList<Card> hand, int phase, ArrayList<Card> fullHand, Phase10GameState gameState) {
        Log.d("Dumb AI", "Enter sortGroups()");
        this.completeGroup1 = null;
        this.completeGroup2 = null;

        this.viableGroups2 = null;
        this.viableGroups1 = null;
        boolean complete1 = false;
        boolean complete2 = false;
        boolean sorted = false;

        ArrayList<Card> skips = new ArrayList<Card>();
        for(int i = 0; i < hand.size(); i++){
            if(hand.get(i).isSkip()){
                skips.add(hand.get(i));
                hand.remove(i);
            }
        }

        //Runs are always checked first (runs are hard to make)
        //Bigger sets are checked before smaller sets

        switch (phase) {
            case 1:
                //test if the groups are complete first
                complete1 = testCompleteSet(hand, 3, 1);
                complete2 = testCompleteSet(hand, 3, 2);
                //If not complete, it will make weak/viable groups
                if (!complete1) {
                    makeSetGroups(hand, 3, 1);
                }
                if (!complete2) {
                    makeSetGroups(hand, 3, 2);
                }
                if(complete1 && complete2){
                    for(Card c : completeGroup1){
                        hand.add(c);
                    }
                }
                //If something wasnt complete, find largest viable group
                if(!complete1 || !complete2) {
                    findLargestViable(2, fullHand, true);
                    checkGroupOrg(3, 3); //Reorganize to accommodate largest viable
                }
                sorted = true; //Sort successful
                break;
            case 2:
                complete1 = testCompleteRun(gameState, hand, 4, 1);
                complete2 = testCompleteSet(hand, 3, 2);
                if (!complete1) makeRunGroups(hand, 4, 1);
                if (!complete2) makeSetGroups(hand, 3, 2);
                if(!complete1 || !complete2) {
                    findLargestViable(2, fullHand, false);
                    checkGroupOrg(4, 3);
                }
                sorted = true;
                break;
            case 3:
                complete1 = testCompleteRun(gameState,hand, 4, 1);
                complete2 = testCompleteSet(hand, 4, 2);
                if (!complete1) makeRunGroups(hand, 4, 1);
                if (!complete2) makeSetGroups(hand, 4, 2);
                if(!complete1 || !complete2) {
                    findLargestViable(2, fullHand, false);
                    checkGroupOrg(4, 4);
                }
                sorted = true;
                break;
            case 4:
                complete1 = testCompleteRun(gameState, hand, 7, 1);
                if (!complete1) makeRunGroups(hand, 7, 1);
                //no second group
                if(!complete1) {
                    findLargestViable(1, fullHand, false);
                    checkGroupOrg(7, 0);
                }
                sorted = true;
                break;
            case 5:
                complete1 = testCompleteRun(gameState, hand, 8, 1);
                if (!complete1) makeRunGroups(hand, 8, 1);
                //no second group
                if(!complete1 ){
                    findLargestViable(1, fullHand,false);
                    checkGroupOrg(8, 0);
                }
                sorted = true;
                break;
            case 6:
                complete1 = testCompleteRun(gameState,hand, 9, 1);
                if (!complete1) makeRunGroups(hand, 9, 1);
                //no second group
                if(!complete1) {
                    findLargestViable(1, fullHand, false);
                    checkGroupOrg(9, 0);
                }
                sorted = true;
                break;
            case 7:
                complete1 = testCompleteSet(hand, 4, 1);
                complete2 = testCompleteSet(hand, 4, 2);
                if (!complete1) makeSetGroups(hand, 4, 1);
                if (!complete2) makeSetGroups(hand, 4, 2);
                if(!complete1 || !complete2) {
                    findLargestViable(2, fullHand, true);
                    checkGroupOrg(4, 4);
                }
                sorted = true;
                break;
            case 8:
                complete1 = testCompleteColor(hand, 7, 1);
                if (!complete1) makeColorGroups(hand, 7, 1);
                //no second group
                if(!complete1) {
                    findLargestViable(1, fullHand, false);
                    checkGroupOrg(7, 0);
                }
                sorted = true;
                break;
            case 9:
                complete1 = testCompleteSet(hand, 5, 1);
                complete2 = testCompleteSet(hand, 2, 2);
                if (!complete1) makeSetGroups(hand, 5, 1);
                if (!complete2) makeSetGroups(hand, 2, 2);
                if(!complete1 || !complete2) {
                    findLargestViable(2, fullHand, true);
                    checkGroupOrg(5, 2);
                }
                sorted = true;
                break;
            case 10:
                complete1 = testCompleteSet(hand, 5, 1);
                complete2 = testCompleteSet(hand, 3, 2);
                if (!complete1) makeSetGroups(hand, 3, 1);
                if (!complete2) makeSetGroups(hand, 3, 2);
                if(!complete1 || !complete2) {
                    findLargestViable(2, fullHand, true);
                    checkGroupOrg(5, 3);
                }
                sorted = true;
                break;
            default:
                break;
        }

        if(skips.size() > 0){
            Iterator<Card> it3 = skips.iterator();
            while(it3.hasNext()){
                Card c = it3.next();
                hand.add(c);
                skips.remove(c);
            }
        }

        nonGroupCards = hand;
        Iterator<Card> it2 = fullHand.iterator();
        hand = new ArrayList<Card>();
        while (it2.hasNext()) {
            Card b = it2.next();
            Card c = new Card(b.getNumber(), b.getColor());
            hand.add(new Card(c.getNumber(), c.getColor()));
        }


        //If somebody has phased, non-group cards will be organized into hits
        if(this.nonGroupCards !=null && this.nonGroupCards.size() != 0 && (gameState.getPlayer1HasPhased() || gameState.getPlayer2HasPhased()) ) {
            makeHits(gameState, gameState.getPlayer1HasPhased(), gameState.getPlayer2HasPhased());
            sorted = true;
        }
        Log.d("Dumb AI", "Exit sortGroups()");
        return sorted;
    }

    /**
     * sorts cards into weak and viable run groups
     *
     * @param hand (Deep) AI's hand
     * @param size of phase requirement
     * @param groupNum 1 or 2 depending on group order
     * @return true if groups get made
     */
    private boolean makeRunGroups(ArrayList<Card> hand, int size, int groupNum) {
        Log.d("Dumb AI", "Enter makeRunGroups()");
        ArrayList<ArrayList<Card>> allLowGroups = new ArrayList<ArrayList<Card>>();
        ArrayList<Card> temp;
        int tempLoc;

        for (int i = 0; i < hand.size(); i++) { //Look through hand
            //Reset temp
            temp = new ArrayList<Card>();
            temp.add(hand.get(i));
            tempLoc = 0;

            for (int j = i + 1; j < hand.size(); j++) {//Compare card is within run Size of initial card

                if (hand.get(j).getNumber() <= (temp.get(0).getNumber() + size) && hand.get(j).getNumber() > temp.get(0).getNumber() && hand.get(j).getNumber() != temp.get(tempLoc).getNumber()) {
                    temp.add(hand.get(j));
                    tempLoc++;
                }
            }

            //add as another group to collective of viable and weak groups
            if (temp.size() > 1) allLowGroups.add(temp);
        }

        /* Separate into weak and viable groups based on size conditions */
        ArrayList<ArrayList<Card>> viables = new ArrayList<ArrayList<Card>>();
        ArrayList<ArrayList<Card>> weaks = new ArrayList<ArrayList<Card>>();

        Iterator<ArrayList<Card>> it1 = allLowGroups.iterator();
        while (it1.hasNext()) {
            ArrayList<Card> group = it1.next();
            if(group.size() < 2) allLowGroups.remove(group); //Too small
            if (size == 4 && group.size() == 2) weaks.add(group);
            else if (size > 4 && group.size() <= 3) weaks.add(group);
            else viables.add(group);
        }

        //Groups are set to instance variables
        if (groupNum == 1) {

                if (viables.size() != 0) {
                    this.viableGroups1 = viables;
                    for (ArrayList<Card> group : viables) {
                        for (Card c : group) {
                            Iterator<Card> it = hand.iterator();
                            while (it.hasNext()) {
                                Card d = it.next();
                                if (c.equals(d)){
                                    hand.remove(d); //Remove from (deep) hand
                                    break;
                                }
                            }
                        }
                    }
                }
                nonGroupCards = hand;  //Non group cards are anything remaining in hand
            }

         else if (groupNum == 2) { //Same for group 2

            if (viables.size() != 0) {
                this.viableGroups2 = viables;
                for (ArrayList<Card> group : viables) {
                    for (Card c : group) {
                        Iterator<Card> it = hand.iterator();
                        while (it.hasNext()) {
                            Card d = it.next();
                            if (c.equals(d)){
                                hand.remove(d); //Remove from (deep) hand
                                break;
                            }
                        }
                    }
                }
            }
            this.nonGroupCards = hand;
        }

        //Put in wilds
        boolean used = false;
        if(this.nonGroupCards == null || this.nonGroupCards.size() == 0) return true;
        Iterator<Card> it3 = this.nonGroupCards.iterator();
        while (it3.hasNext()) {
            Card c = it3.next();
            if(c.isWild()) {

                if (this.viableGroups1 != null) {
                    for (ArrayList<Card> group : viableGroups1) group.add(c);
                    used = true;
                }
                if (this.viableGroups2 != null) {
                    for (ArrayList<Card> group : viableGroups2) group.add(c);
                    used = true;
                }
            }
            if(used == true) nonGroupCards.remove(c);
        }
        if(completeGroup1 != null && completeGroup1.size() == 0) completeGroup1 = null;
        if(completeGroup2 != null && completeGroup2.size() == 0) completeGroup2 = null;
        Log.d("Dumb AI", "Exit makeRunGroups()");
        return true;
    }

    /**
     * sorts cards into weak and viable set groups
     *
     * @param hand (Deep) AI's hand
     * @param size of phase requirement
     * @param groupNum 1 or 2 depending on group order
     * @return true if groups get made
     */

    private boolean makeSetGroups(ArrayList<Card> hand, int size, int groupNum){
        Log.d("Dumb AI", "Enter makeSetGroups()");
        ArrayList<ArrayList<Card>> allLowGroups = new ArrayList<ArrayList<Card>>();
        ArrayList<Card> temp;
        int tempLoc;

        for (int i = 0; i < hand.size(); i++) { //Look at each card
            //Reset temp
            temp = new ArrayList<Card>();
            temp.add(hand.get(i));
            tempLoc = 0;
            for (int j = i + 1; j < hand.size(); j++) {//Compare card number
                if (hand.get(j).getNumber() == temp.get(0).getNumber()) {
                    temp.add(hand.get(j));
                    tempLoc++;
                }
            }
            //add as another group to collective of viable and weak groups
            if (temp.size() > 1) {
                allLowGroups.add(temp);
            }
        }

        //separate into weak and viable
        ArrayList<ArrayList<Card>> viables = new ArrayList<ArrayList<Card>>();

        Iterator<ArrayList<Card>> it1 = allLowGroups.iterator();
        while (it1.hasNext()) {
            ArrayList<Card> group = it1.next();
            if(group.size() < 2) {
                allLowGroups.remove(group);
            }

            else{
                viables.add(group);
            }
        }

        //Groups are set to class variables
        if (groupNum == 1) {

            if (viables.size() != 0) {
                this.viableGroups1 = viables;
                for (ArrayList<Card> group : viables) {
                    for (Card c : group) {
                        Iterator<Card> it = hand.iterator();
                        while (it.hasNext()) {
                            Card d = it.next();
                            if (c.equals(d)){
                                hand.remove(d); //Remove from (deep) hand
                                break;
                            }
                        }
                    }
                }
            }
            this.nonGroupCards = hand;
        }
        else if (groupNum == 2) {

            if (viables.size() != 0) {
                this.viableGroups2 = viables;
                for (ArrayList<Card> group : viables) {
                    for (Card c : group) {
                        Iterator<Card> it = hand.iterator();
                        while (it.hasNext()) {
                            Card d = it.next();
                            if (c.equals(d)){
                                hand.remove(d); //Remove from (deep) hand
                                break;
                            }
                        }
                    }
                }
            }
            this.nonGroupCards = hand;
        }
        if(completeGroup1 != null && completeGroup1.size() == 0) completeGroup1 = null;
        if(completeGroup2 != null && completeGroup2.size() == 0) completeGroup2 = null;
        Log.d("Dumb AI", "Exit makeSetGroups()");
        return true;
    }

    /**
     * sorts cards into weak and viable color groups
     *
     * @param hand (Deep) AI's hand
     * @param size of phase requirement
     * @param groupNum 1 or 2 depending on group order
     * @return true if groups get made
     */
    private boolean makeColorGroups(ArrayList<Card> hand, int size, int groupNum){ //same as makeSetGroups but compares color
        Log.d("Dumb AI", "Enter makeColorGroups()");
        ArrayList<ArrayList<Card>> allLowGroups = new ArrayList<ArrayList<Card>>();
        ArrayList<Card> temp;
        int tempLoc;
        for (int i = 0; i < hand.size(); i++) {
            //Reset temp
            temp = new ArrayList<Card>();
            temp.add(hand.get(i));
            tempLoc = 0;
            for (int j = i + 1; j < hand.size(); j++) {
                //Compare card color
                if (hand.get(j).getColor() == temp.get(0).getColor()) {
                    temp.add(hand.get(j));
                    tempLoc++;
                }
            }
            //add as another group to collective of viable and weak groups
            if (temp.size() > 1) allLowGroups.add(temp);
        }

        //separate into weak and viable
        ArrayList<ArrayList<Card>> viables = new ArrayList<ArrayList<Card>>();

        Iterator<ArrayList<Card>> it1 = allLowGroups.iterator();
        while (it1.hasNext()) {
            ArrayList<Card> group = it1.next();
            if(group.size() < 2) allLowGroups.remove(group);

            else viables.add(group);
        }

        //Groups are set to class variables
        if (groupNum == 1) {
                if (viables.size() != 0) {
                    this.viableGroups1 = viables;
                    for (ArrayList<Card> group : viables) {
                        for (Card c : group) {
                            Iterator<Card> it = hand.iterator();
                            while (it.hasNext()) {
                                Card d = it.next();
                                if (c.equals(d)){
                                    hand.remove(d); //Remove from (deep) hand
                                    break;
                                }
                            }
                        }
                    }
                }
                nonGroupCards = hand;
        }
        else if (groupNum == 2) {

            if (viables.size() != 0) {
                this.viableGroups2 = viables;
                for (ArrayList<Card> group : viables) {
                    for (Card c : group) {
                        Iterator<Card> it = hand.iterator();
                        while (it.hasNext()) {
                            Card d = it.next();
                            if (c.equals(d)){
                                hand.remove(d); //Remove from (deep) hand
                                break;
                            }
                        }
                    }
                }
            }
            this.nonGroupCards = hand;
        }
        //Put in wilds
        boolean used = false;
        if(this.nonGroupCards == null || this.nonGroupCards.size() == 0) return true;
        Iterator<Card> it3 = this.nonGroupCards.iterator();
        while (it3.hasNext()) {
            Card c = it3.next();
            if(c.isWild()){
                if (this.viableGroups1 != null) {
                    for (ArrayList<Card> group : viableGroups1) group.add(c);
                    used = true;
                }
                if (this.viableGroups2 != null) {
                    for (ArrayList<Card> group : viableGroups2) group.add(c);
                    used = true;
                }
            }
            if(used = true) nonGroupCards.remove(c);
        }
        if(completeGroup1 != null && completeGroup1.size() == 0) completeGroup1 = null;
        if(completeGroup2 != null && completeGroup2.size() == 0) completeGroup2 = null;
        Log.d("Dumb AI", "Exit makeColorGroups()");
        return true;
    }

    /**
     * identifies the largest viable group and clears out all overlapping
     *
     * cards from other groups
     * @param groupNum 1 or 2 depending on phase reqs
     * @param fullHand (deep) copy of hand
     * @param same true if Phase requirements are the same
     */
    private void findLargestViable(int groupNum, ArrayList<Card> fullHand, boolean same){
        Log.d("Dumb AI", "Enter findLargestViable()");
        int biggest = 0;
        int loc = 0;
        if(viableGroups1!=null) {
            //Find biggest viable group
            if (viableGroups1.size() > 0) {
                biggest = viableGroups1.get(0).size();
                if (viableGroups1.size() > 1) {
                    for (int i = 1; i < viableGroups1.size(); i++) {
                        if (viableGroups1.get(i).size() > biggest) {
                            loc = i;
                            biggest = viableGroups1.get(i).size();
                        }
                    }
                }

                //put best viable group at beginning
                ArrayList<Card> tempHold = viableGroups1.get(0);
                viableGroups1.set(0, viableGroups1.get(loc));
                viableGroups1.set(loc, tempHold);
                if(same && viableGroups2!=null && viableGroups2.size() > loc){
                    viableGroups2.remove(loc);
                }

                //Remove the cards of the biggest viable group from other groups
                //To eliminate overlap
                if(viableGroups1!=null) {
                    Iterator<Card> it2 = this.viableGroups1.get(0).iterator();
                    while (it2.hasNext()) {
                        Card b = it2.next();
                        Card c = new Card(b.getNumber(), b.getColor());
                        int uniqueness = 0;
                        for (Card d : fullHand) {
                            if (c.equals(d)) uniqueness++;
                        }

                        //Check cards against cards from viable groups (excluding biggest viable)
                        if (viableGroups1 != null && viableGroups1.size() > 1) {
                            Iterator<ArrayList<Card>> it1 = this.viableGroups1.iterator();
                            while (it1.hasNext()) {
                                ArrayList<Card> grp = it1.next();
                                ArrayList<Card> group = new ArrayList<Card>();
                                for(Card cd : grp) {
                                    group.add(new Card(cd.getNumber(), cd.getColor()));
                                }
                                int copies = 0;
                                Iterator<Card> it = group.iterator();
                                while (it.hasNext()) {
                                    Card d = it.next();
                                    if (c.equals(d) && !(group.equals(viableGroups1.get(0))))
                                        copies++;
                                }
                                while (copies > uniqueness - 1) {
                                    group.remove(c);
                                    copies--;
                                }
                            }
                        }
                    }
                }
            }
        }
        if(groupNum == 2){
            if(viableGroups2==null){
                Log.d("Dumb AI", "Exit findLargestViable()");
                return;
            }
            //Find best viable
            biggest = 0;
            loc = 0;
            if (viableGroups2 != null && viableGroups2.size() > 0) {
                biggest = viableGroups2.get(0).size();
                if (viableGroups2.size() > 1) {
                    for (int i = 1; i < viableGroups2.size(); i++) {
                        if (viableGroups2.get(i).size() > biggest) loc = i;
                    }
                }

                //put best viable group at beginning
                ArrayList<Card> tempHold = viableGroups2.get(0);
                viableGroups2.set(0, viableGroups2.get(loc));
                viableGroups2.set(loc, tempHold);

                if(viableGroups2!=null) {
                    //Remove the cards of the biggest viable group from other groups
                    //To eliminate overlap
                    Iterator<Card> it2 = this.viableGroups2.get(0).iterator();
                    while (it2.hasNext()) {

                        Card b = it2.next();
                        Card c = new Card(b.getNumber(), b.getColor());
                        int uniqueness = 0;
                        for (Card d : fullHand) {
                            if (c.equals(d)) uniqueness++;
                        }

                        //Check cards against cards from viable groups (excluding biggest viable)
                        if (viableGroups2 != null && viableGroups2.size() > 1) {
                            Iterator<ArrayList<Card>> it1 = this.viableGroups2.iterator();
                            while (it1.hasNext()) {
                                ArrayList<Card> grp = it1.next();
                                ArrayList<Card> group = new ArrayList<Card>();
                                for(Card cd : grp) {
                                    group.add(new Card(cd.getNumber(), cd.getColor()));
                                }
                                int copies = 0;
                                Iterator<Card> it = group.iterator();
                                while (it.hasNext()) {
                                    Card d = it.next();
                                    if (c.equals(d) && !(group.equals(viableGroups1.get(0))))
                                        copies++;
                                }
                                while (copies > uniqueness - 1) {
                                    group.remove(c);
                                    copies--;
                                }
                            }
                        }
                    }
                }
            }
        }
        Log.d("Dumb AI", "Exit findLargestViable()");
    }

    /**
     * reorganizes weak/viable/complete/non groups based on size changes
     * @param size1 group1 size from phase reqs
     * @param size2 group2 size from phase reqs
     */
    private void checkGroupOrg(int size1, int size2) {
        Log.d("Dumb AI", "Enter checkGroupOrg()");

        if (viableGroups1 != null) {//remove or move from viable groups
            Iterator<ArrayList<Card>> it = this.viableGroups1.iterator();
            while (it.hasNext()) {//remove from weak groups
                ArrayList<Card> grp = it.next();
                ArrayList<Card> group = new ArrayList<Card>();
                for (Card c : grp) {
                    group.add(new Card(c.getNumber(), c.getColor()));
                }
                if (size1 < 4) if (group.size() < 2) {
                    for (Card d : group) {
                        if (this.nonGroupCards == null) this.nonGroupCards = new ArrayList<Card>();
                        nonGroupCards.add(d);
                    }
                    viableGroups1.remove(group);
                }
                if (size1 == 4) {
                    if (group.size() < 3) {
                        if (group.size() < 2) {
                            for (Card d : group) {
                                if (this.nonGroupCards == null)
                                    this.nonGroupCards = new ArrayList<Card>();
                                nonGroupCards.add(d);
                            }
                            viableGroups1.remove(group);
                        }

                    }
                    if (size1 > 4) {
                        if (group.size() < 4) { //If group could still be weak, it will be moved there
                            if (group.size() == 3 || group.size() == 2) {
                                if (group.size() < 2) {
                                    for (Card d : group) {
                                        if (this.nonGroupCards == null)
                                            this.nonGroupCards = new ArrayList<Card>();
                                        nonGroupCards.add(d);
                                    }
                                    viableGroups1.remove(group);
                                }
                            }
                        }
                        if (this.viableGroups1.size() == 0) {
                            this.viableGroups1 = null;
                            break;
                        }
                        if (group.size() >= size1) {
                            for (Card c : group) {
                                if (this.completeGroup1 == null) {
                                    this.completeGroup1 = new ArrayList<Card>();
                                    completeGroup1.add(new Card(c.getNumber(), c.getColor()));
                                }
                            }
                            viableGroups1 = null;

                            break;
                        }
                    }
                }
            }
        }

        if (viableGroups2 != null) {//remove or move from viable groups
            Iterator<ArrayList<Card>> it = this.viableGroups2.iterator();
            while (it.hasNext()) {//remove from weak groups
                ArrayList<Card> grp = it.next();
                ArrayList<Card> group = new ArrayList<Card>();
                for(Card c : grp) {
                    group.add(new Card(c.getNumber(), c.getColor()));
                }
                if (size2 < 4) if (group.size() < 2){
                    for(Card d : group){
                        if(this.nonGroupCards == null) this.nonGroupCards = new ArrayList<Card>();
                        nonGroupCards.add(d);
                    }
                    viableGroups2.remove(group);
                }
                if (size2 == 4) {
                    if (group.size() < 3) {
                         if (group.size() < 2){
                            for(Card d : group){
                                if(this.nonGroupCards == null) this.nonGroupCards = new ArrayList<Card>();
                                nonGroupCards.add(d);
                            }
                            viableGroups2.remove(group);
                        }
                    }
                }
                if (size2 > 4) {
                    if (group.size() < 4) { //If group could still be weak, it will be moved there
                         if (group.size() < 2){
                            for(Card d : group){
                                if(this.nonGroupCards == null) this.nonGroupCards = new ArrayList<Card>();
                                nonGroupCards.add(d);
                            }
                            viableGroups2.remove(group);
                        }
                    }
                }
                if(this.viableGroups2.size() == 0) {
                    this.viableGroups2 = null;
                    break;
                }
                if (group.size() >= size2) {
                    for (Card c : group) {
                        if(this.completeGroup2 == null) {
                            this.completeGroup2 = new ArrayList<Card>();
                            completeGroup2.add(new Card(c.getNumber(), c.getColor()));
                        }
                    }
                    viableGroups2 = null;

                    break;
                }
            }
        }
        if (this.nonGroupCards.size() == 0){
            this.nonGroupCards = null;
        }
        Log.d("Dumb AI", "Exit checkGroupOrg()");
    }

    /**
     * tests if a complete run exists
     *
     * @param gs the gameState (shallow)
     * @param hand (deep) AI's hand
     * @param size size based on phase reqs
     * @param groupNum which group (1 or 2)
     * @return true if a complete run exists
     */
    private boolean testCompleteRun(Phase10GameState gs, ArrayList<Card> hand, int size, int groupNum) {
        Log.d("Dumb AI", "Enter testCompleteRun()");
        ArrayList<Card> temp;
        ArrayList<Card> notInGroup;
        int notInGroupLoc;
        int tempLoc;
        int notInGroupSize = hand.size() - size;

        for (int i = 0; i < hand.size(); i++) {
            //Reset temp
            temp = new ArrayList<Card>();
            temp.add(hand.get(i));
            tempLoc = 0;
            notInGroup = new ArrayList<Card>();
            notInGroupLoc = 0;

            for (int j = i + 1; j < hand.size(); j++) {
                //works as part of a run
                if (hand.get(j).getNumber() == temp.get(tempLoc).getNumber() + 1 && tempLoc<size-1) {
                    temp.add(hand.get(j));
                    tempLoc++;
                    //doesn't work as part of a run
                } else {
                    if (notInGroupSize > 0) {
                        if (notInGroupLoc < notInGroupSize) {
                            notInGroup.add(hand.get(j));
                            notInGroupLoc++;
                        } else {
                            Log.d("Dumb AI", "Exit testCompleteRun()");
                            return false; //Not in group cards exceed possible per phase reqs
                        }
                    }
                }
            }
            if (tempLoc >= size - 1) { //Is a complete group
                //Place in groups
                if(gs.getPhase().isRun(gs.getPhase().sortCards(temp), size, this.playerNum,true, 0) == null){
                    Log.d("Dumb AI", "Exit testCompleteRun()");
                    return false;
                }
                if (groupNum == 1) {
                    if(this.completeGroup1 == null) this.completeGroup1 = new ArrayList<Card>();
                    this.completeGroup1 = temp;

                    this.viableGroups1 = null;
                } else if (groupNum == 2) {
                    if(this.completeGroup2 == null) this.completeGroup2 = new ArrayList<Card>();
                    this.completeGroup2 = temp;

                    this.viableGroups2 = null;
                }
                if(this.nonGroupCards == null) this.nonGroupCards = new ArrayList<Card>();
                this.nonGroupCards = notInGroup;
                Log.d("Dumb AI", "Exit testCompleteRun()");
                return true; //Complete group does exist
            }
        }
        Log.d("Dumb AI", "Exit testCompleteRun()");
        return false; //Complete group doesn't exist
    }

    /**
     * tests if a complete set exists
     *
     * @param hand (deep) AI's hand
     * @param size size based on phase reqs
     * @param groupNum which group (1 or 2)
     * @return true if a complete set exists
     * Known issues: Needs to handle wild cards
     */
    private boolean testCompleteSet(ArrayList<Card> hand, int size, int groupNum) { //same as run but compares same number
        Log.d("Dumb AI", "Enter testCompleteSet()");
        ArrayList<Card> temp;
        ArrayList<Card> notInGroup;
        int notInGroupLoc;
        int tempLoc;
        int notInGroupSize = hand.size() - size;
        for (int i = 0; i < hand.size(); i++) {
            //Reset temp
            temp = new ArrayList<Card>();
            temp.add(hand.get(i));
            tempLoc = 0;
            notInGroup = new ArrayList<Card>();
            notInGroupLoc = 0;
            for (int j = i + 1; j < hand.size(); j++) {
                //works as part of group
                if (!hand.get(j).isSkip() && hand.get(j).getNumber() == temp.get(tempLoc).getNumber() && tempLoc<size-1) {
                    temp.add(hand.get(j));
                    tempLoc++;
                    //doesn't work as part of group
                } else {
                    if (notInGroupSize > 0) {
                        if (notInGroupLoc < notInGroupSize) {
                            notInGroup.add(hand.get(j));
                            notInGroupLoc++;
                        } else {
                            break;
                        }
                    }
                }
            }
            if (tempLoc >= size - 1) {
                //Place in groups
                if (groupNum == 1) {
                    if(this.completeGroup1 == null) this.completeGroup1 = new ArrayList<Card>();


                    for(Card c : temp){
                        completeGroup1.add(new Card(c.getNumber(), c.getColor()));
                    }
//                    this.completeGroup1 = temp;

                    this.viableGroups1 = null;
//                    Iterator<Card> temp2 = hand.iterator();
//                    while(temp2.hasNext()){
                    int j = -1;
                    for(Card c : completeGroup1){
                        for(int x = 0; x<hand.size(); x++){
                            if(hand.get(x).getColor() == c.getColor() && hand.get(x).getNumber() == c.getNumber()){
                                j = x;
                                break;
                            }
                        }
                        if(hand.size() > j && j >= 0) {
                            hand.remove(j);
                        }
                    }

                    Log.d("Dumb AI", "Exit testCompleteSet()");
                    return true;
                } else if (groupNum == 2) {
                    if(this.completeGroup1 == null) this.completeGroup1 = new ArrayList<Card>();
                    this.completeGroup2 = temp;

                    this.viableGroups2 = null;
                }
                if(this.nonGroupCards == null) this.nonGroupCards = new ArrayList<Card>();
                this.nonGroupCards = notInGroup;
                Log.d("Dumb AI", "Exit testCompleteSet()");
                return true; //Complete group does exist
            }
        }
        Log.d("Dumb AI", "Exit testCompleteSet()");
        return false; //Complete group doesn't exist
    }

    /**
     * tests if a complete color group exists
     *
     * @param hand (deep) AI's hand
     * @param size size based on phase reqs
     * @param groupNum which group (1 or 2)
     * @return true if a complete color group exists
     */
    private boolean testCompleteColor(ArrayList<Card> hand, int size, int groupNum) { //same as color but compares same color
        Log.d("Dumb AI", "Enter testCompleteColor()");
        ArrayList<Card> temp;
        ArrayList<Card> notInGroup;
        int notInGroupLoc;
        int tempLoc;
        for (int i = 0; i < hand.size(); i++) {
            //Reset temp
            temp = new ArrayList<Card>();
            temp.add(hand.get(i));
            tempLoc = 0;
            notInGroup = new ArrayList<Card>();
            notInGroupLoc = 0;
            int notInGroupSize = hand.size() - size;
            for (int j = i + 1; j < hand.size(); j++) {
                //works as part of a run
                if (!hand.get(j).isSkip() && hand.get(j).getColor() == temp.get(tempLoc).getColor() && tempLoc<size-1) {
                    temp.add(hand.get(j));
                    tempLoc++;
                    //doesn't work as part of a run
                } else {
                    if(notInGroupSize > 0) {
                        if(notInGroupLoc < notInGroupSize) {
                            notInGroup.add(hand.get(j));
                            notInGroupLoc++;
                        }
                        else{
                            Log.d("Dumb AI", "Exit testCompleteColor()");
                            return false;
                        }
                    }
                }
            }
            if (tempLoc >= size - 1) {
                //Place in groups
                if (groupNum == 1) {
                    if(this.completeGroup1 == null) this.completeGroup2 = new ArrayList<Card>();
                    this.completeGroup1 = temp;

                    this.viableGroups1 = null;
                } else if (groupNum == 2) {
                    if(this.completeGroup2 == null) this.completeGroup2 = new ArrayList<Card>();
                    this.completeGroup2 = temp;

                    this.viableGroups2 = null;
                }
                if(this.nonGroupCards == null) this.nonGroupCards = new ArrayList<Card>();
                this.nonGroupCards = notInGroup;
                Log.d("Dumb AI", "Exit testCompleteColor()");
                return true; //Complete group does exist
            }
        }
        Log.d("Dumb AI", "Exit testCompleteColor()");
        return false; //Complete group doesn't exist

    }

    /**
     * Checks to see if a card will grow a group based on phase reqs
     *
     * @param card the cards that hopefully grows the group
     * @param phase the AI's current phase
     * @param fullHand (shallow) AI's hand
     * @return true if cards successfully grows the group
     */
    private boolean checkGrowsGroup(Card card, int phase, ArrayList<Card> fullHand){
        Log.d("Dumb AI", "Enter checkGrowsGroup()");
        boolean growsSomething = false;
        switch(phase){
            case 1:
                growsSomething = checkGrowsSet(card,1); //Check first req
                if(growsSomething)  findLargestViable(1, fullHand, true);
                if(!growsSomething){
                    boolean temp;
                    temp = checkGrowsSet(card,2); //Check second req
                    if(temp) findLargestViable(2, fullHand, true);
                    growsSomething = temp;
                }
                if(growsSomething) checkGroupOrg(3, 3); //Recheck group organizaiton
                break;
            case 2:
                growsSomething = checkGrowsRun(card,1, 4);
                if(growsSomething) findLargestViable(1, fullHand, false);
                if(!growsSomething){
                    boolean temp;
                    temp = checkGrowsSet(card,2);
                    if(temp) findLargestViable(2, fullHand, false);
                    growsSomething = temp;
                }
                if(growsSomething) checkGroupOrg(4, 3);
                break;
            case 3:
                growsSomething = checkGrowsRun(card,1, 4);
                if(growsSomething) findLargestViable(1, fullHand, false);
                if(!growsSomething){
                    boolean temp;
                    temp = checkGrowsSet(card,2);
                    if(temp)findLargestViable(2, fullHand, false);
                    growsSomething = temp;
                }
                if(growsSomething)checkGroupOrg(4, 4);
                break;
            case 4:
                growsSomething = checkGrowsRun(card,1, 7);
                //no second group
                if(growsSomething) {
                    findLargestViable(1, fullHand, false);
                    checkGroupOrg(7, 0);
                }
                break;
            case 5:
                growsSomething = checkGrowsRun(card, 1, 8);
                //no second group
                if(growsSomething) {
                    findLargestViable(1, fullHand, false);
                    checkGroupOrg(8, 0);
                }
                break;
            case 6:
                growsSomething = checkGrowsRun(card, 1, 9);
                //no second group
                if(growsSomething) {
                    findLargestViable(1, fullHand, false);
                    checkGroupOrg(9, 0);
                }
                break;
            case 7:
                growsSomething = checkGrowsSet(card,1);
                if(growsSomething)  findLargestViable(1, fullHand, true);
                if(!growsSomething){
                    boolean temp;
                    temp = checkGrowsSet(card,2);
                    if(temp) findLargestViable(2, fullHand, true);
                    growsSomething = temp;
                }
                if(growsSomething) checkGroupOrg(4, 4);
                break;
            case 8:
                growsSomething = checkGrowsColor(card, 1);
                //no second group
                if(growsSomething) {
                    findLargestViable(1, fullHand, false);
                    checkGroupOrg(7, 0);
                }
                break;
            case 9:
                growsSomething = checkGrowsSet(card,1);
                if(growsSomething)  findLargestViable(1, fullHand, true);
                if(!growsSomething){
                    boolean temp;
                    temp = checkGrowsSet(card,2);
                    if(temp)  findLargestViable(2, fullHand, true);
                    growsSomething = temp;
                }
                if(growsSomething)checkGroupOrg(5, 2);
                break;
            case 10:
                growsSomething = checkGrowsSet(card,1);
                if(growsSomething) findLargestViable(1, fullHand, true);
                if(!growsSomething) {
                    boolean temp;
                    temp = checkGrowsSet(card,2);
                    if(temp) findLargestViable(2, fullHand, true);
                    growsSomething = temp;
                }
                if(growsSomething) checkGroupOrg(5, 3);
                break;
            default:
                break;
        }
        Log.d("Dumb AI", "Exit checkGrowsGroup()");
        return growsSomething;
    }

    /**
     * checks if a card grows a run group based on phase reqs
     *
     * @param card the card that hopefully grows the group
     * @param groupNum the group's identity
     * @param size the group's size
     * @return true if it successfully grows the group
     */
    private boolean checkGrowsRun(Card card, int groupNum, int size){ //I dont think this handles the card being a smaller number than the run
        Log.d("Dumb AI", "Enter checkGrowsRun()");
        if(groupNum == 1) { //check groups1 aren't null
            if (this.completeGroup1 != null && this.completeGroup1.size() != 0) {
                //Does card add to either end of complete group?
                if (card.getNumber() == this.completeGroup1.get(0).getNumber() - 1) {
                    this.completeGroup1.add(0, card);
                    Log.d("Dumb AI", "Exit checkGrowsRun()");
                    return true;
                }
                if(card.getNumber() == this.completeGroup1.get(0).getNumber() + 1){
                    this.completeGroup1.add(card);
                    Log.d("Dumb AI", "Exit checkGrowsRun()");
                    return true;
                }
            }
            if (viableGroups1 != null) {
                for(ArrayList<Card> group : viableGroups1) {
                    int base = group.get(0).getNumber();
                    for (int j = 1; j < size; j++) { //Compare card is within run Size of initial card
                        if(size >= group.size())break;
                        if(group.get(j).getNumber() != base + j && card.getNumber() == base + j ){
                            group.add(j, card);
                            Log.d("Dumb AI", "Exit checkGrowsRun()");
                            return true;
                        }
                    }
                }
            }

        }
        //check groups2 arent null
        else if(groupNum == 2) {
            if (this.completeGroup2 != null && this.completeGroup2.size() != 0) { //Will only reach here to test hit
                //Does card add to either end of complete group?
                if (card.getNumber() == this.completeGroup2.get(0).getNumber() - 1) {
                    this.completeGroup2.add(0, card);
                    Log.d("Dumb AI", "Exit checkGrowsRun()");
                    return true;
                }
                if(card.getNumber() == this.completeGroup2.get(0).getNumber() + 1){
                    this.completeGroup2.add(card);
                    Log.d("Dumb AI", "Exit checkGrowsRun()");
                    return true;
                }
            }
            if (viableGroups2 != null) {
                for(ArrayList<Card> group : viableGroups2) {
                    int base = group.get(0).getNumber();
                    for (int j = 1; j < size; j++) { //Compare card is within run Size of initial card
                        if(size >= group.size())break;
                        if(group.get(j).getNumber() != base + j && card.getNumber() == base + j ){
                            group.add(j, card);
                            Log.d("Dumb AI", "Exit checkGrowsRun()");
                            return true;
                        }
                    }
                }
            }

        }
        Log.d("Dumb AI", "Exit checkGrowsRun()");
        return false;
    }

    /**
     * checks if a card grows a set group based on phase reqs
     *
     * @param card the card that hopefully grows the group
     * @param groupNum the group's identity
     * @return true if it successfully grows the group
     */
    private boolean checkGrowsSet(Card card, int groupNum){
        Log.d("Dumb AI", "Enter checkGrowsSet()");
        //check groups1 arent null
        if(groupNum == 1) {
            if (completeGroup1 != null) { //Will only reach here to test hit
                if(card.getNumber() == completeGroup1.get(0).getNumber()){
                    completeGroup1.add(card);
                    Log.d("Dumb AI", "Exit checkGrowsSet()");
                    return true;
                }
            }
            if (viableGroups1 != null) {
                for (ArrayList<Card> group : viableGroups1) {
                    if(card.getNumber() == group.get(0).getNumber()){
                        group.add(card);
                        Log.d("Dumb AI", "Exit checkGrowsSet()");
                        return true;
                    }
                }
            }

        }
        //check groups2 arent null
        else if(groupNum == 2) {
            if (completeGroup2 != null) { //Will only reach here to test hit
                if (card.getNumber() == completeGroup2.get(0).getNumber()) {
                    completeGroup2.add(card);
                    Log.d("Dumb AI", "Exit checkGrowsSet()");
                    return true;
                }
            }
            if (viableGroups2 != null) {
                for (ArrayList<Card> group : viableGroups2) {
                    if (card.getNumber() == group.get(0).getNumber()) {
                        group.add(card);
                        Log.d("Dumb AI", "Exit checkGrowsSet()");
                        return true;
                    }
                }
            }

        }
        Log.d("Dumb AI", "Exit checkGrowsSet()");
        return false;
    }

    /**
     * checks if a card grows a color group based on phase reqs
     *
     * @param card the card that hopefully grows the group
     * @param groupNum the group's identity
     * @return true if it successfully grows the group
     */
    private boolean checkGrowsColor(Card card, int groupNum){
        //check groups1 arent null
        Log.d("Dumb AI", "Enter checkGrowsColor()");
        if(groupNum == 1) {
            if (completeGroup1 != null) { //Will only reach here to test hit
                if(card.getColor() == completeGroup1.get(0).getColor()){
                    completeGroup1.add(card);
                    Log.d("Dumb AI", "Exit checkGrowsColor()");
                    return true;
                }
            }
            if (viableGroups1 != null) {
                for (ArrayList<Card> group : viableGroups1) {
                    if(card.getColor() == group.get(0).getColor()){
                        group.add(card);
                        Log.d("Dumb AI", "Exit checkGrowsColor()");
                        return true;
                    }
                }
            }

        }
        //check groups2 arent null
        else if(groupNum == 2) {
            if (completeGroup2 != null) { //Will only reach here to test hit
                if (card.getColor() == completeGroup2.get(0).getColor()) {
                    completeGroup2.add(card);
                    Log.d("Dumb AI", "Exit checkGrowsColor()");
                    return true;
                }
            }
            if (viableGroups2 != null) {
                for (ArrayList<Card> group : viableGroups2) {
                    if (card.getColor() == group.get(0).getColor()) {
                        group.add(card);
                        Log.d("Dumb AI", "Exit checkGrowsColor()");
                        return true;
                    }
                }
            }

        }
        Log.d("Dumb AI", "Exit checkGrowsColor()");
        return false;
    }

    /**
     * special boy
     * sorts the hand by color, made specifically for phase 8
     *
     * @param hand the hand being sorted
     * @return the hand, now sorted
     */
    private ArrayList<Card> sortColor(ArrayList<Card> hand){
        Log.d("Dumb AI", "Enter sortColor()");
        ArrayList<Card> arrL= new ArrayList<Card>();
        int x = 0;
        while(x < hand.size()){
            arrL.add(new Card(hand.get(x).getNumber(), hand.get(x).getColor()));
            x++;
        }
        for (int i = 0; i < arrL.size() - 1; i++){
            int index = i;
            for (int j = i + 1; j < arrL.size(); j++){
                if (arrL.get(i).getColor() < hand.get(i).getColor()) { //lowest "number" color to highest
                    index = j; //searching for lowest index
                }
            }
            Card smallestColorCard = arrL.get(index);
            arrL.set(index, arrL.get(i));
            arrL.set(i, smallestColorCard);
        }
        Log.d("Dumb AI", "Exit sortColor()");
        return arrL;
    }

    /**
     * identifies if the AI should draw from the discard or draw piles
     * executes the draw action and sends it to the game
     *
     * @param gameState  (shallow) the phase 10 gameState
     * @param hasPhased true if the AI has phased
     * @param phase AI's current phase
     * @param fullHand (shallow) AI's hand
     * @return true if the draw action is successfully executed
     */
    private boolean doDraw(Phase10GameState gameState, boolean hasPhased, int phase, ArrayList<Card> fullHand){
        Log.d("Dumb AI", "Enter doDraw()");
        boolean drawUp = false; //true means draw from draw pile, false means draw from discard pile
        if(gameState.getDiscardPile() != null || gameState.getDiscardPile().size() > 0 ) {
            Card topCard = gameState.getDiscardPile().peek(); //Top of discard (visible)
            if (topCard.isWild()) drawUp = false; //Always take a wild
            else if (checkGrowsGroup(topCard, phase, fullHand)) { //Always take the card that grows a group
                drawUp = false;
            } else if (hasPhased && checkIsHit(gameState, topCard))
                drawUp = false; //If AI has already phased, always take cards that hit
            else
                drawUp = false; //Any other condition, draw from the drawPile (not visible, effectively random)
            if (drawUp && phase >= 4 && phase <= 6 && !hasPhased) {
                Iterator<Card> it = fullHand.iterator();
                while (it.hasNext()) {
                    Card c = it.next();
                    if (c.getNumber() == gameState.getDiscardPile().peek().getNumber()) {
                        drawUp = false;
                        break;
                    }
                }
            }
        }
        if(drawUp){ //Draw from discard
            DrawFaceUpAction act = new DrawFaceUpAction(this);
            game.sendAction(act);  //Send drawUp!!
            return true;
        }
        else { //Draw from drawpile

            DrawFaceDownAction act = new DrawFaceDownAction(this);
            game.sendAction(act); //Send drawDown!!

            Log.d("Dumb AI", "Exit doDraw()");
            return true;
        }
    }

    /**
     * identifies which card should be discarded from the AI's hand
     * and executes the discard to send it to the game
     *
     * @param gameState (shallow) copy of phase 10 gamestate
     * @param hasPhased true if the player has phased
     * @return true if action successful
     */
    private boolean doDiscard(Phase10GameState gameState, boolean hasPhased) {

        Log.d("Dumb AI", "Enter doDiscard()");
        Random r = new Random();
        if(this.nonGroupCards != null && this.nonGroupCards.size() > 0 && completeGroup1 != null && completeGroup2 == null){
            int v = r.nextInt(this.nonGroupCards.size());
            DiscardAction act = new DiscardAction(this, this.nonGroupCards.get(v));
            game.sendAction(act); //Send Discard!!
            return true;
        }
        if(this.nonGroupCards != null && this.nonGroupCards.size() > 0 && completeGroup2 != null){
            int v = r.nextInt(this.nonGroupCards.size());
            DiscardAction act = new DiscardAction(this, this.nonGroupCards.get(v));
            game.sendAction(act); //Send Discard!!
            return true;
        }
        if (checkHitsExist()) {  //Lowest priority discard, hit cards (by order of score)
            int highestScoreLoc = 0;
            int highScore = this.hitList.get(0).getScore();
            for (int i = 1; i < this.hitList.size(); i++) {
                if (highScore > this.hitList.get(i).getScore()) {
                    highestScoreLoc = i;
                    highScore = this.hitList.get(i).getScore();
                }
            }
            DiscardAction act = new DiscardAction(this, this.hitList.get(highestScoreLoc));
            game.sendAction(act); //Send Discard!!
            //this.hitList.remove(highestScoreLoc);
            return true;
        }
        if (playerNum == 0) {
            DiscardAction act = new DiscardAction(this, gameState.getPlayer1Hand().get(0));
            game.sendAction(act);
            return true;
        }
        else {
            DiscardAction act = new DiscardAction(this, gameState.getPlayer2Hand().get(0));
            game.sendAction(act);
            return true;
        }

    }

    /**
     * checks if the AI is ready to play phase
     *
     * @param phase current AI phase
     * @return false if not ready to phase, or true if ready
     */
    private boolean checkPhaseReady(int phase) {
        Log.d("Dumb AI", "Enter checkPhaseReady()");
        if(phase == 1 || phase == 2 || phase == 3 ||  phase == 7 ||  phase == 9 ||  phase == 10) {
            if (completeGroup1 != null && completeGroup2 != null && completeGroup1.size() != 0 && completeGroup2.size() != 0){
                Log.d("Dumb AI", "Exit checkPhaseReady()");
                return true; //Return 2 groups
            }
        }
        else if(phase == 4 || phase == 5 || phase == 6 || phase == 8) {
            if (completeGroup1 != null && completeGroup1.size() != 0 ){
                Log.d("Dumb AI", "Exit checkPhaseReady()");
                return true; //Return 1 group
            }
        }
        Log.d("Dumb AI", "Exit checkPhaseReady()");
        return false; //Not ready to phase
    }

    /**
     * send the phase action with appropriate cards
     *
     * @param phase AI's current phase
     * @return true if action sent successfully
     */
    private boolean doPhase(int phase) {
        Log.d("Dumb AI", "Enter doPhase()");
        ArrayList<Card> temp = new ArrayList<Card>();
        switch(phase){
            case 1:
                if(this.completeGroup1.size() >= 3){
                    for(int i = 0; i < 3; i++){
                        temp.add(this.completeGroup1.get(i));
                    }
                    for(int i = 3; i < this.completeGroup1.size(); i++){
                        if(this.hitList == null) this.hitList = new ArrayList<Card>();
                        this.hitList.add(this.completeGroup1.get(i));
                    }
                }
                else{
                    Log.d("Dumb AI", "Exit doPhase()");
                    return false;
                }
                if(this.completeGroup2.size() >= 3){
                    for(int i = 0; i < 3; i++){
                        temp.add(this.completeGroup2.get(i));
                    }
                    for(int i = 3; i < this.completeGroup2.size(); i++){
                        if(this.hitList == null) this.hitList = new ArrayList<Card>();
                        this.hitList.add(this.completeGroup2.get(i));
                    }
                }
                else{
                    Log.d("Dumb AI", "Exit doPhase()");
                    return false;
                }
                break;
            case 2:
                if(this.completeGroup1.size() >= 4){
                    for(int i = 0; i < 4; i++){
                        temp.add(this.completeGroup1.get(i));
                    }
                    for(int i = 4; i < this.completeGroup1.size(); i++){
                        if(this.hitList == null) this.hitList = new ArrayList<Card>();
                        this.hitList.add(this.completeGroup1.get(i));
                    }
                }
                else{
                    Log.d("Dumb AI", "Exit doPhase()");
                    return false;
                }
                if(this.completeGroup2.size() >= 3){
                    for(int i = 0; i < 3; i++){
                        temp.add(this.completeGroup2.get(i));
                    }
                    for(int i = 3; i < this.completeGroup2.size(); i++){
                        if(this.hitList == null) this.hitList = new ArrayList<Card>();
                        this.hitList.add(this.completeGroup2.get(i));
                    }
                }
                else{
                    Log.d("Dumb AI", "Exit doPhase()");
                    return false;
                }
                break;
            case 3:
            case 7:
                if(this.completeGroup1.size() >= 4){
                    for(int i = 0; i < 4; i++){
                        temp.add(this.completeGroup1.get(i));
                    }
                    for(int i = 4; i < this.completeGroup1.size(); i++){
                        if(this.hitList == null) this.hitList = new ArrayList<Card>();
                        this.hitList.add(this.completeGroup1.get(i));
                    }
                }
                else{
                    Log.d("Dumb AI", "Exit doPhase()");
                    return false;
                }
                if(this.completeGroup2.size() >= 4){
                    for(int i = 0; i < 4; i++){
                        temp.add(this.completeGroup2.get(i));
                    }
                    for(int i = 4; i < this.completeGroup2.size(); i++){
                        if(this.hitList == null) this.hitList = new ArrayList<Card>();
                        this.hitList.add(this.completeGroup2.get(i));
                    }
                }
                else{
                    Log.d("Dumb AI", "Exit doPhase()");
                    return false;
                }
                break;
            case 4:
            case 8:
                if(this.completeGroup1.size() >= 7){
                    for(int i = 0; i < 7; i++){
                        temp.add(this.completeGroup1.get(i));
                    }
                    for(int i = 7; i < this.completeGroup1.size(); i++){
                        if(this.hitList == null) this.hitList = new ArrayList<Card>();
                        this.hitList.add(this.completeGroup1.get(i));
                    }
                }else{
                    Log.d("Dumb AI", "Exit doPhase()");
                    return false;
                }
                //No second group
                break;
            case 5:
                if(this.completeGroup1.size() >= 8){
                    for(int i = 0; i < 8; i++){
                        temp.add(this.completeGroup1.get(i));
                    }
                    for(int i = 8; i < this.completeGroup1.size(); i++){
                        if(this.hitList == null) this.hitList = new ArrayList<Card>();
                        this.hitList.add(this.completeGroup1.get(i));
                    }
                }
                else{
                    Log.d("Dumb AI", "Exit doPhase()");
                    return false;
                }
                //no second group
                break;
            case 6:
                if(this.completeGroup1.size() >= 9){
                    for(int i = 0; i < 9; i++){
                        temp.add(this.completeGroup1.get(i));
                    }
                    for(int i = 9; i < this.completeGroup1.size(); i++){
                        if(this.hitList == null) this.hitList = new ArrayList<Card>();
                        this.hitList.add(this.completeGroup1.get(i));
                    }
                }
                else {
                    Log.d("Dumb AI", "Exit doPhase()");
                    return false;
                }
                //no second group
                break;
            case 9:
                if(this.completeGroup1.size() >= 5){
                    for(int i = 0; i < 5; i++){
                        temp.add(this.completeGroup1.get(i));
                    }
                    for(int i = 5; i < this.completeGroup1.size(); i++){
                        if(this.hitList == null) this.hitList = new ArrayList<Card>();
                        this.hitList.add(this.completeGroup1.get(i));
                    }
                }
                else{
                    Log.d("Dumb AI", "Exit doPhase()");
                    return false;
                }
                if(this.completeGroup2.size() >= 2){
                    for(int i = 0; i < 2; i++){
                        temp.add(this.completeGroup2.get(i));
                    }
                    for(int i = 2; i < this.completeGroup2.size(); i++){
                        if(this.hitList == null) this.hitList = new ArrayList<Card>();
                        this.hitList.add(this.completeGroup2.get(i));
                    }
                }
                else{
                    Log.d("Dumb AI", "Exit doPhase()");
                    return false;
                }
                break;
            case 10:
                if(this.completeGroup1.size() >= 5){
                    for(int i = 0; i < 5; i++){
                        temp.add(this.completeGroup1.get(i));
                    }
                    for(int i = 5; i < this.completeGroup1.size(); i++){
                        if(this.hitList == null) this.hitList = new ArrayList<Card>();
                        this.hitList.add(this.completeGroup1.get(i));
                    }
                }
                else{
                    Log.d("Dumb AI", "Exit doPhase()");
                    return false;
                }
                if(this.completeGroup2.size() >= 3){
                    for(int i = 0; i < 3; i++){
                        temp.add(this.completeGroup2.get(i));
                    }
                    for(int i = 3; i < this.completeGroup2.size(); i++){
                        if(this.hitList == null) this.hitList = new ArrayList<Card>();
                        this.hitList.add(this.completeGroup2.get(i));
                    }
                }
                else{

                    Log.d("Dumb AI", "Exit doPhase()");
                    return false;
                }
                break;
            default:
                Log.d("Dumb AI", "Exit doPhase()");
                return false;
        }

        PhaseAction act = new PhaseAction(this, temp);
        game.sendAction(act); //Send Phase!!

        Log.d("Dumb AI", "Exit doPhase()");
        return true;
    }

    /**
     * checks that there are cards for the AI to hit with
     *
     * @return true if hits exist
     */
    private boolean checkHitsExist(){
        Log.d("Dumb AI", "Enter checkHitsExist()");
        if(this.hitList != null && this.hitList.size() != 0){
            Log.d("Dumb AI", "Exit checkHitsExist()");
            return true;
        }
        else{
            Log.d("Dumb AI", "Exit checkHitsExist()");
            return false;
        }
    }

    /**
     * executes the hit action in proper coordination with which player to hit on
     * and with what card
     *
     * @param phase the AI's current phase
     * @param fullHand (shallow) copy of AI's hand
     * @return true once executed
     */
    private boolean doHits(int phase, ArrayList<Card> fullHand){
        Log.d("Dumb AI", "Enter doHits()");
        Random r = new Random();
        if(hitList == null || whereToHitList == null){
            Log.d("Dumb AI", "Exit doHits()");
            return false;
        }
        if(hitList.size() != whereToHitList.size()){
            Log.d("Dumb AI", "Exit doHits()");
            return false;
        }
        for(int i = 0; i < this.hitList.size(); i++){
            HitAction act = new HitAction(this, this.hitList.get(i), whereToHitList.get(i));
            game.sendAction(act); //Send hit!!
            if(this.playerNum == whereToHitList.get(i)){
                    checkGrowsGroup(this.hitList.get(i), phase, fullHand); //Add to complete group if self
            }
            this.hitList.remove(i);
            this.whereToHitList.remove(i);
        }
        Log.d("Dumb AI", "Exit doHits()");
        return true;
    }

    /**
     * check if a card is allowed to hit on either player phase
     *
     * @param gs (shallow) copy of phase 10 gameState
     * @param c card that is being tested to hit
     * @return true if hit is possible
     */
    private boolean checkIsHit(Phase10GameState gs, Card c){
        Log.d("Dumb AI", "Enter checkIsit()");
        if(gs.phase.checkHitValid(c, 0, true)){
            Log.d("Dumb AI", "Exit checkIsit()");
            return true;
        }
        else if(gs.phase.checkHitValid(c, 1, true)){
            Log.d("Dumb AI", "Exit checkIsit()");
            return true;
        }
        else{
            Log.d("Dumb AI", "Exit checkIsit()");
            return false;
        }
    }

    /**
     * check if any nongroup cards work as hits on either player and organize into
     * separate list
     *
     * @param gs (shallow) the phase 10 gameState
     * @param phased1 true if player 1 has phased
     * @param phased2 true if player 2 has phased
     * @return true when finished
     */
    private boolean makeHits(Phase10GameState gs, boolean phased1, boolean phased2) {//Only happens once someone has phased
        Log.d("Dumb AI", "Enter makeHits()");
        if(phased1) {
            if(this.nonGroupCards != null) {
                this.hitList = new ArrayList<Card>();
                Iterator<Card> it = this.nonGroupCards.iterator();
                while (it.hasNext()) {
                    Card b = it.next();
                    if(b == null) break;
                    Card c = new Card(b.getNumber(), b.getColor());
                    if (gs.phase.checkHitValid(c, 0, true)) {
                        if (this.hitList == null) this.hitList = new ArrayList<Card>();
                        if (this.whereToHitList == null)
                            this.whereToHitList = new ArrayList<Integer>();
                        this.hitList.add(c);
                        this.nonGroupCards.remove(c);
                        this.whereToHitList.add((Integer) 0);
                    }
                }
            }
        }
        if(phased2) {
            if(this.nonGroupCards != null) {
                Iterator<Card> it = this.nonGroupCards.iterator();
                while (it.hasNext()) {
                    Card b = it.next();
                    if(b == null) break;
                    Card c = new Card(b.getNumber(), b.getColor());
                    if (gs.phase.checkHitValid(c, 1, true)) {
                        if (this.hitList == null) this.hitList = new ArrayList<Card>();
                        if (this.whereToHitList == null)
                            this.whereToHitList = new ArrayList<Integer>();
                        this.hitList.add(c);
                        this.nonGroupCards.remove(c);
                        this.whereToHitList.add((Integer) 1);
                    }
                }
            }
        }
        Log.d("Dumb AI", "Exit makeHits()");
        return true;
    }
}