package up.edu.phase10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import up.edu.phase10.Framework.GameComputerPlayer;
import up.edu.phase10.Framework.GameInfo;

public class Phase10IgnorantComputerPlayer extends GameComputerPlayer {

    public Phase10IgnorantComputerPlayer(String name) {
        super(name);
    }

    private boolean drawChoice; //Cant remember why these two variables
    private int discardChoice; //exist
    //Might not use these
    private boolean hitReady;
    private Card toHit;
    private ArrayList<Card> willHit = null;
    private ArrayList<Card> couldHit = null;
    private ArrayList<Card> toPhase = null;

    private ArrayList<ArrayList<Card>> weakGroups1 = null;
    private ArrayList<ArrayList<Card>> viableGroups1 = null;
    private ArrayList<Card> completeGroup1 = null;
    private ArrayList<ArrayList<Card>> weakGroups2 = null;
    private ArrayList<ArrayList<Card>> viableGroups2 = null;
    private ArrayList<Card> completeGroup2 = null;
    private ArrayList<Card> nonGroupCards = null;

//EXTERNAL CITAITON https://howtodoinjava.com/java/collections/arraylist/arraylist-clone-deep-copy/

    protected void receiveInfo(GameInfo info) {                                       ///INCOMPLETE
        if (!(info instanceof Phase10GameState)) return;
        Phase10GameState copy = (Phase10GameState) info;
        if (copy.getTurnId() != this.playerNum) return;
        boolean hasPhased = false;
        int phase = 0;
        ArrayList<Card> fullHand = null;
        if (this.playerNum == 0) {

            //Deep copy into a usable temporary hand
            ArrayList<Card> hand = new ArrayList<Card>();
            Iterator<Card> it = copy.getPlayer1Hand().iterator();
            while (it.hasNext()) {
                hand.add(new Card(it.next().getNumber(), it.next().getColor()));
            }

            //separated to eliminate redundant playerNum tests
            hasPhased = copy.getPlayer1HasPhased();
            phase = copy.getPlayer1Phase();
            fullHand = copy.getPlayer1Hand();
            //sort groups
            Collections.sort(hand);
            if (!hasPhased) { //Groups only need to be sorted if player has not phased
                sortGroups(hand, copy.getPlayer1Phase(), fullHand, copy);
            } else { //Otherwise, we need to look at potential hit cards
                ///////////////
            }

        } else if (this.playerNum == 1) {

            //Deep copy into a usable temporary hand
            ArrayList<Card> hand = new ArrayList<Card>();
            Iterator<Card> it = copy.getPlayer2Hand().iterator();
            while (it.hasNext()) {
                hand.add(new Card(it.next().getNumber(), it.next().getColor()));
            }

            //separated to eliminate redundant playerNum tests
            hasPhased = copy.getPlayer2HasPhased();
            phase = copy.getPlayer2Phase();
            fullHand = copy.getPlayer2Hand();

            //sort groups
            Collections.sort(hand);
            if (!hasPhased) {
                sortGroups(hand, copy.getPlayer2Phase(), fullHand, copy);
            } else {
                ///////////////
            }

        }
        //Draw
        doDraw(copy, hasPhased, phase, fullHand);
        //Check phase
        if (!hasPhased) {
            int phasing = checkPhaseReady(copy, phase);
            if (phasing > 0){
                doPhase(phasing);
                hasPhased = true;
            }
        }
        //If has phased, check hit
        if (hasPhased) {
            while (checkHitReady(copy)) {
                //send hit actions with HitCARD
            }
        }

        //Discard
        doDiscard(copy, hasPhased, this.playerNum + 1);

    }

    public boolean sortGroups(ArrayList<Card> hand, int phase, ArrayList<Card> fullHand, Phase10GameState gameState) {                      //INCOMPLETE
        boolean complete1 = false;
        boolean complete2 = false;
        //Runs are always checked first
        //Bigger sets are checked before smaller sets
        switch (phase) {
            case 1:
                complete1 = testCompleteSet(hand, 3, 1);
                complete2 = testCompleteSet(hand, 3, 2);
                if (!complete1) makeSetGroups(hand, 3, 1);
                if (!complete2) makeSetGroups(hand, 3, 2);
                findLargestViable(2,fullHand);
                checkGroupOrg(3,3);
                return true;
            case 2:
                complete1 = testCompleteRun(hand, 4, 1);
                complete2 = testCompleteSet(hand, 3, 2);
                if (!complete1) makeRunGroups(hand, 4, 1);
                if (!complete2) makeSetGroups(hand, 3, 2);
                findLargestViable(2,fullHand);
                checkGroupOrg(4,3);
                return true;
            case 3:
                complete1 = testCompleteRun(hand, 4, 1);
                complete2 = testCompleteSet(hand, 4, 2);
                if (!complete1) makeRunGroups(hand, 4, 1);
                if (!complete2) makeSetGroups(hand, 4, 2);
                findLargestViable(2,fullHand);
                checkGroupOrg(4,4);
                return true;
            case 4:
                complete1 = testCompleteRun(hand, 7, 1);
                if (!complete1) makeRunGroups(hand, 7, 1);
                //no second group
                findLargestViable(1,fullHand);
                checkGroupOrg(7,0);
                return true;
            case 5:
                complete1 = testCompleteRun(hand, 8, 1);
                if (!complete1) makeRunGroups(hand, 8, 1);
                //no second group
                findLargestViable(1,fullHand);
                checkGroupOrg(8,0);
                return true;
            case 6:
                complete1 = testCompleteRun(hand, 9, 1);
                if (!complete1) makeRunGroups(hand, 9, 1);
                //no second group
                findLargestViable(1,fullHand);
                checkGroupOrg(9,0);
                return true;
            case 7:
                complete1 = testCompleteSet(hand, 4, 1);
                complete2 = testCompleteSet(hand, 4, 2);
                if (!complete1) makeSetGroups(hand, 4, 1);
                if (!complete2) makeSetGroups(hand, 4, 2);
                findLargestViable(2,fullHand);
                checkGroupOrg(4,4);
                return true;
            case 8:
                complete1 = testCompleteColor(hand, 7, 1);
                if (!complete1) makeColorGroups(hand, 7, 1);
                //no second group
                findLargestViable(1,fullHand);
                checkGroupOrg(7,0);
                return true;
            case 9:
                complete1 = testCompleteSet(hand, 5, 1);
                complete2 = testCompleteSet(hand, 2, 2);
                if (!complete1) makeSetGroups(hand, 5, 1);
                if (!complete2) makeSetGroups(hand, 2, 2);
                findLargestViable(2,fullHand);
                checkGroupOrg(5,2);
                return true;
            case 10:
                complete1 = testCompleteSet(hand, 5, 1);
                complete2 = testCompleteSet(hand, 3, 2);
                if (!complete1) makeSetGroups(hand, 3, 1);
                if (!complete2) makeSetGroups(hand, 3, 2);
                findLargestViable(2,fullHand);
                checkGroupOrg(5,3);
                return true;
            default:
                return false;
        }
        //Organize hit cards if someone has phased
        //Change above return to variable, then return that instead
    }

    public boolean makeRunGroups(ArrayList<Card> hand, int size, int groupNum) {
        ArrayList<ArrayList<Card>> allLowGroups = new ArrayList<ArrayList<Card>>();
        ArrayList<Card> temp;
        ArrayList<Card> notInGroup;
        int notInGroupLoc;
        int tempLoc;
        for (int i = 0; i < hand.size(); i++) {
            //Reset temp
            temp = new ArrayList<Card>();
            temp.set(0, hand.get(i));
            tempLoc = 0;
            notInGroup = new ArrayList<Card>();
            notInGroupLoc = 0;
            for (int j = i + 1; j < hand.size(); j++) {
                //Compare card is within run Size of initial card
                if (hand.get(j).getNumber() <= temp.get(0).getNumber() + size && hand.get(j).getNumber() != temp.get(tempLoc).getNumber()) {
                    temp.set(tempLoc + 1, hand.get(j));
                    tempLoc++;
                } else { //doesn't work as part of a run
                    notInGroup.set(notInGroupLoc, hand.get(j));
                    notInGroupLoc++;
                }
            }
            //add as another group to collective of viable and weak groups
            if (temp.size() > 1) allLowGroups.add(temp);
        }

        //separate into weak and viable
        ArrayList<ArrayList<Card>> viables = new ArrayList<ArrayList<Card>>();
        ArrayList<ArrayList<Card>> weaks = new ArrayList<ArrayList<Card>>();
        for (ArrayList<Card> group : allLowGroups) {
            if (size == 4 && group.size() == 2) weaks.add(group);
            if (size > 4 && group.size() <= 3) weaks.add(group);
            else viables.add(group);
        }

        //Groups are set to class variables
        if (groupNum == 1) {
            if (weaks.size() != 0) {
                this.weakGroups1 = weaks;
                for (ArrayList<Card> group : weaks) {
                    for (Card c : group) {
                        for (Card d : hand) {
                            if (c.equals(d)) hand.remove(d);
                        }
                    }
                }
                if (viables.size() != 0) {
                    this.viableGroups1 = viables;
                    for (ArrayList<Card> group : viables) {
                        for (Card c : group) {
                            for (Card d : hand) {
                                if (c.equals(d)) hand.remove(d);
                            }
                        }
                    }
                }
                nonGroupCards = hand;
                return true;
            }
        } else if (groupNum == 2) {
            if (weaks.size() != 0) {
                this.weakGroups2 = weaks;
                for (ArrayList<Card> group : weaks) {
                    for (Card c : group) {
                        for (Card d : hand) {
                            if (c.equals(d)) hand.remove(d);
                        }
                    }
                }
            }
            if (viables.size() != 0) {
                this.viableGroups2 = viables;
                for (ArrayList<Card> group : viables) {
                    for (Card c : group) {
                        for (Card d : hand) {
                            if (c.equals(d)) hand.remove(d);
                        }
                    }
                }
            }
            nonGroupCards = hand;
            return true;
        }
        return false;
    }
    public boolean makeSetGroups(ArrayList<Card> hand, int size, int groupNum){ //needs same code                                       ///EMPTY
        return false;
    }

    public boolean makeColorGroups(ArrayList<Card> hand, int size, int groupNum){ //needs same code                                       ///EMPTY
        return false;
    }

    public void findLargestViable(int groupNum, ArrayList<Card> hand){
        //Find best viable
        int biggest = 0;
        int loc = 0;
        if (viableGroups1.size() > 0) {
            biggest = viableGroups1.get(0).size();
            if (viableGroups1.size() > 1) {
                for (int i = 1; i < viableGroups1.size(); i++) {
                    if (viableGroups1.get(i).size() > biggest) loc = i;
                }
            }

            //put best viable group at beginning
            ArrayList<Card> tempHold = viableGroups1.get(0);
            viableGroups1.set(0, viableGroups1.get(loc));
            viableGroups1.set(loc, tempHold);

            //Remove the cards of the biggest viable group from other groups
            //To eliminate overlap
            for (Card c : viableGroups1.get(0)) {
                int uniqueness = 0;
                for (Card d : hand) {
                    if (c.equals(d)) uniqueness++;
                }
                //Check cards against cards from weak groups
                if (weakGroups1.size() > 0) {
                    for (ArrayList<Card> group : weakGroups1) {
                        int copies = 0;
                        for (Card d : group) {
                            if (c.equals(d)) copies++;
                        }
                        while (copies > uniqueness - 1) {
                            group.remove(c);
                        }
                    }
                }
                //Check cards against cards from viable groups (excluding biggest viable)
                if (viableGroups1.size() > 1) {
                    for (ArrayList<Card> group : viableGroups1) {
                        int copies = 0;
                        for (Card d : group) {
                            if (c.equals(d) && !(group.equals(viableGroups1.get(0)))) copies++;
                        }
                        while (copies > uniqueness - 1) {
                            group.remove(c);
                        }
                    }
                }
            }
        }
        if(groupNum == 2){
            //Find best viable
            biggest = 0;
            loc = 0;
            if (viableGroups2.size() > 0) {
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

                //Remove the cards of the biggest viable group from other groups
                //To eliminate overlap
                for (Card c : viableGroups2.get(0)) {
                    int uniqueness = 0;
                    for (Card d : hand) {
                        if (c.equals(d)) uniqueness++;
                    }
                    //Check cards against cards from weak groups
                    if (weakGroups2.size() > 0) {
                        for (ArrayList<Card> group : weakGroups2) {
                            int copies = 0;
                            for (Card d : group) {
                                if (c.equals(d)) copies++;
                            }
                            while (copies > uniqueness - 1) {
                                group.remove(c);
                            }
                        }
                    }
                    //Check cards against cards from viable groups (excluding biggest viable)
                    if (viableGroups2.size() > 1) {
                        for (ArrayList<Card> group : viableGroups2) {
                            int copies = 0;
                            for (Card d : group) {
                                if (c.equals(d) && !(group.equals(viableGroups2.get(0)))) copies++;
                            }
                            while (copies > uniqueness - 1) {
                                group.remove(c);
                            }
                        }
                    }
                }
            }
        }
    }

    public void checkGroupOrg (int size1, int size2) {
        if (weakGroups1 != null) {
            //remove/move groups that no longer fit qualifications
            for (ArrayList<Card> group : weakGroups1) { //remove from weak groups
                if (size1 < 4) {
                    if (group.size() < 2){
                        for(Card d : group)nonGroupCards.add(d);
                        weakGroups1.remove(group);
                    }
                } else if (group.size() >= 2) {
                    viableGroups1.add(group);
                    weakGroups1.remove(group);
                }
                if (size1 == 4) {
                    if (group.size() < 2){
                        for(Card d : group)nonGroupCards.add(d);
                        weakGroups1.remove(group);
                    }
                    else if (group.size() > 2) {
                        viableGroups1.add(group);
                        weakGroups1.remove(group);
                    }
                }
                if (size1 > 4) {
                    if (group.size() < 2){
                        for(Card d : group)nonGroupCards.add(d);
                        weakGroups1.remove(group);
                    }
                } else if (group.size() > 3) {
                    viableGroups1.add(group);
                    weakGroups1.remove(group);
                }
            }
        }

        while (viableGroups1 != null) {
            for (ArrayList<Card> group : viableGroups1) { //remove or move from viable groups
                if (size1 < 4) if (group.size() < 2){
                    for(Card d : group)nonGroupCards.add(d);
                    viableGroups1.remove(group);
                }
                if (size1 == 4) {
                    if (group.size() < 3) {
                        if (group.size() == 2) { //If group could still be weak, it will be moved there
                            weakGroups1.add(group);
                            viableGroups1.remove(group);
                        } else if (group.size() < 2){
                            for(Card d : group)nonGroupCards.add(d);
                            viableGroups1.remove(group);
                        }
                    }
                }
                if (size1 > 4) {
                    if (group.size() < 4) { //If group could still be weak, it will be moved there
                        if (group.size() == 3 || group.size() == 2) {
                            weakGroups1.add(group);
                            viableGroups1.remove(group);
                        } else if (group.size() < 2){
                            for(Card d : group)nonGroupCards.add(d);
                            viableGroups1.remove(group);
                        }
                    }
                }
                if (group.size() >= size1) {
                    for (Card c : group) {
                        completeGroup1.add(new Card(c.getNumber(), c.getColor()));
                    }
                    viableGroups1 = null;
                    weakGroups1 = null;
                }
            }
        }
        if (weakGroups2 != null) {
            //remove/move groups that no longer fit qualifications
            for (ArrayList<Card> group : weakGroups2) { //remove from weak groups
                if (size1 < 4) {
                    if (group.size() < 2){
                        for(Card d : group)nonGroupCards.add(d);
                        weakGroups2.remove(group);
                    }
                } else if (group.size() >= 2) {
                    viableGroups2.add(group);
                    weakGroups1.remove(group);
                }
                if (size1 == 4) {
                    if (group.size() < 2){
                        for(Card d : group)nonGroupCards.add(d);
                        weakGroups2.remove(group);
                    }
                    else if (group.size() > 2) {
                        viableGroups2.add(group);
                        weakGroups2.remove(group);
                    }
                }
                if (size1 > 4) {
                    if (group.size() < 2){
                        for(Card d : group)nonGroupCards.add(d);
                        weakGroups2.remove(group);
                    }
                } else if (group.size() > 3) {
                    viableGroups2.add(group);
                    weakGroups2.remove(group);
                }
            }
        }

        while (viableGroups2 != null) {
            for (ArrayList<Card> group : viableGroups2) { //remove or move from viable groups
                if (size2 < 4) if (group.size() < 2){
                    for(Card d : group)nonGroupCards.add(d);
                    viableGroups2.remove(group);
                }
                if (size2 == 4) {
                    if (group.size() < 3) {
                        if (group.size() == 2) { //If group could still be weak, it will be moved there
                            weakGroups2.add(group);
                            viableGroups2.remove(group);
                        } else if (group.size() < 2){
                            for(Card d : group)nonGroupCards.add(d);
                            viableGroups2.remove(group);
                        }
                    }
                }
                if (size2 > 4) {
                    if (group.size() < 4) { //If group could still be weak, it will be moved there
                        if (group.size() == 3 || group.size() == 2) {
                            weakGroups2.add(group);
                            viableGroups2.remove(group);
                        } else if (group.size() < 2){
                            for(Card d : group)nonGroupCards.add(d);
                            viableGroups2.remove(group);
                        }
                    }
                }
                if (group.size() >= size2) {
                    for (Card c : group) {
                        completeGroup2.add(new Card(c.getNumber(), c.getColor()));
                    }
                    viableGroups2 = null;
                    weakGroups2 = null;

                }
            }
        }
    }

    public boolean testCompleteRun(ArrayList<Card> hand, int size, int groupNum) {
        ArrayList<Card> temp;
        ArrayList<Card> notInGroup;
        int notInGroupLoc;
        int tempLoc;
        for (int i = 0; i < hand.size(); i++) {
            //Reset temp
            temp = new ArrayList<Card>();
            temp.set(0, hand.get(i));
            tempLoc = 0;
            notInGroup = new ArrayList<Card>();
            notInGroupLoc = 0;
            for (int j = i + 1; j < hand.size(); j++) {
                //works as part of a run
                if (hand.get(j).getNumber() == temp.get(tempLoc).getNumber() + 1) {
                    temp.set(tempLoc + 1, hand.get(j));
                    tempLoc++;
                    //doesn't work as part of a run
                } else {
                    notInGroup.set(notInGroupLoc, hand.get(j));
                    notInGroupLoc++;
                }
            }
            if (tempLoc >= size - 1) {
                //Place in groups
                if (groupNum == 1) {
                    this.completeGroup1 = temp;
                    this.weakGroups1 = null;
                    this.viableGroups1 = null;
                } else if (groupNum == 2) {
                    this.completeGroup2 = temp;
                    this.weakGroups2 = null;
                    this.viableGroups2 = null;
                }
                this.nonGroupCards = notInGroup;
                return true; //Complete group does exist
            }
        }
        return false; //Complete group doesn't exist
    }
    public boolean testCompleteSet(ArrayList<Card> hand, int size, int groupNum) {
        ArrayList<Card> temp;
        ArrayList<Card> notInGroup;
        int notInGroupLoc;
        int tempLoc;
        for (int i = 0; i < hand.size(); i++) {
            //Reset temp
            temp = new ArrayList<Card>();
            temp.set(0, hand.get(i));
            tempLoc = 0;
            notInGroup = new ArrayList<Card>();
            notInGroupLoc = 0;
            for (int j = i + 1; j < hand.size(); j++) {
                //works as part of a run
                if (hand.get(j).getNumber() == temp.get(tempLoc).getNumber()) {
                    temp.set(tempLoc + 1, hand.get(j));
                    tempLoc++;
                    //doesn't work as part of a run
                } else {
                    notInGroup.set(notInGroupLoc, hand.get(j));
                    notInGroupLoc++;
                }
            }
            if (tempLoc >= size - 1) {
                //Place in groups
                if (groupNum == 1) {
                    this.completeGroup1 = temp;
                    this.weakGroups1 = null;
                    this.viableGroups1 = null;
                } else if (groupNum == 2) {
                    this.completeGroup2 = temp;
                    this.weakGroups2 = null;
                    this.viableGroups2 = null;
                }
                this.nonGroupCards = notInGroup;
                return true; //Complete group does exist
            }
        }
        return false; //Complete group doesn't exist
    }
    public boolean testCompleteColor(ArrayList<Card> hand, int size, int groupNum) {
        ArrayList<Card> temp;
        ArrayList<Card> notInGroup;
        int notInGroupLoc;
        int tempLoc;
        for (int i = 0; i < hand.size(); i++) {
            //Reset temp
            temp = new ArrayList<Card>();
            temp.set(0, hand.get(i));
            tempLoc = 0;
            notInGroup = new ArrayList<Card>();
            notInGroupLoc = 0;
            for (int j = i + 1; j < hand.size(); j++) {
                //works as part of a run
                if (hand.get(j).getColor() == temp.get(tempLoc).getColor()) {
                    temp.set(tempLoc + 1, hand.get(j));
                    tempLoc++;
                    //doesn't work as part of a run
                } else {
                    notInGroup.set(notInGroupLoc, hand.get(j));
                    notInGroupLoc++;
                }
            }
            if (tempLoc >= size - 1) {
                //Place in groups
                if (groupNum == 1) {
                    this.completeGroup1 = temp;
                    this.weakGroups1 = null;
                    this.viableGroups1 = null;
                } else if (groupNum == 2) {
                    this.completeGroup2 = temp;
                    this.weakGroups2 = null;
                    this.viableGroups2 = null;
                }
                this.nonGroupCards = notInGroup;
                return true; //Complete group does exist
            }
        }
        return false; //Complete group doesn't exist
    }

    public boolean checkGrowsGroup(Card card, int phase, ArrayList<Card> fullHand){                                       ///INCOMPLETE
        boolean growsSomething = false;
        switch(phase){
            case 1:
                growsSomething = checkGrowsSet(card,1);                          //Repeat this organization for all switch cases
                if(!growsSomething) growsSomething = checkGrowsSet(card,2);
                if(growsSomething) {
                    findLargestViable(2, fullHand);
                    checkGroupOrg(3, 3);
                }
                break;
            case 2:
                checkGrowsSet(card,1);
                checkGrowsSet(card, 2);
                break;
            case 3:
                checkGrowsRun(card, 1,4);
                checkGrowsSet(card,2);
                break;
            case 4:
                checkGrowsRun(card, 1, 7);
                //no second group
                break;
            case 5:
                checkGrowsRun(card, 1, 8);
                //no second group
                break;
            case 6:
                checkGrowsRun(card, 1, 9);
                //no second group
                break;
            case 7:
                checkGrowsSet(card, 1);
                checkGrowsSet(card, 2);
                break;
            case 8:
                checkGrowsColor(card, 1);
                //no second group
                break;
            case 9:
                checkGrowsSet(card,1);
                checkGrowsSet(card, 2);
                break;
            case 10:
                checkGrowsSet(card,1);
                checkGrowsSet(card, 2);
                break;
            default:
                break;
        }
        return growsSomething;
    }
    public boolean checkGrowsRun(Card card, int groupNum, int size){
        //check groups1 aren't null
        if(groupNum == 1) {
            if (this.completeGroup1 != null) { //Will only reach here to test hit
                //Does card add to either end of complete group?
                if (card.getNumber() == this.completeGroup1.get(0).getNumber() - 1) {
                    this.completeGroup1.add(0, card);
                    return true;
                }
                if(card.getNumber() == this.completeGroup1.get(0).getNumber() + 1){
                    this.completeGroup1.add(card);
                    return true;
                }
            }
            if (viableGroups1 != null) {
                for(ArrayList<Card> group : viableGroups1) {
                    int base = group.get(0).getNumber();
                    for (int j = 1; j < size; j++) { //Compare card is within run Size of initial card
                        if(group.get(j).getNumber() != base + j && card.getNumber() == base + j ){
                            group.add(j, card);
                            return true;
                        }
                    }
                }
            }
            if (weakGroups1 != null) {
                for(ArrayList<Card> group : weakGroups1) {
                    int base = group.get(0).getNumber();
                    for (int j = 1; j < size; j++) { //Compare card is within run Size of initial card
                        if(group.get(j).getNumber() != base + j && card.getNumber() == base + j ){
                            group.add(j, card);
                            return true;
                        }
                    }
                }
            }
        }
        //check groups2 arent null
        else if(groupNum == 2) {
            if (this.completeGroup2 != null) { //Will only reach here to test hit
                //Does card add to either end of complete group?
                if (card.getNumber() == this.completeGroup1.get(0).getNumber() - 1) {
                    this.completeGroup1.add(0, card);
                    return true;
                }
                if(card.getNumber() == this.completeGroup1.get(0).getNumber() + 1){
                    this.completeGroup1.add(card);
                    return true;
                }
            }
            if (viableGroups2 != null) {
                for(ArrayList<Card> group : viableGroups2) {
                    int base = group.get(0).getNumber();
                    for (int j = 1; j < size; j++) { //Compare card is within run Size of initial card
                        if(group.get(j).getNumber() != base + j && card.getNumber() == base + j ){
                            group.add(j, card);
                            return true;
                        }
                    }
                }
            }
            if (weakGroups2 != null) {
                for(ArrayList<Card> group : weakGroups2) {
                    int base = group.get(0).getNumber();
                    for (int j = 1; j < size; j++) { //Compare card is within run Size of initial card
                        if(group.get(j).getNumber() != base + j && card.getNumber() == base + j ){
                            group.add(j, card);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public boolean checkGrowsSet(Card card, int groupNum){
        //check groups1 arent null
        if(groupNum == 1) {
            if (completeGroup1 != null) { //Will only reach here to test hit
                if(card.getNumber() == completeGroup1.get(0).getNumber()){
                    completeGroup1.add(card);
                    return true;
                }
            }
            if (viableGroups1 != null) {
                for (ArrayList<Card> group : viableGroups1) {
                    if(card.getNumber() == group.get(0).getNumber()){
                        group.add(card);
                        return true;
                    }
                }
            }
            if (weakGroups1 != null) {
                for (ArrayList<Card> group : weakGroups1) {
                    if(card.getNumber() == group.get(0).getNumber()){
                        group.add(card);
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
                    return true;
                }
            }
            if (viableGroups2 != null) {
                for (ArrayList<Card> group : viableGroups2) {
                    if (card.getNumber() == group.get(0).getNumber()) {
                        group.add(card);
                        return true;
                    }
                }
            }
            if (weakGroups2 != null) {
                for (ArrayList<Card> group : weakGroups2) {
                    if (card.getNumber() == group.get(0).getNumber()) {
                        group.add(card);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean checkGrowsColor(Card card, int groupNum){
        //check groups1 arent null
        if(groupNum == 1) {
            if (completeGroup1 != null) { //Will only reach here to test hit
                if(card.getColor() == completeGroup1.get(0).getColor()){
                    completeGroup1.add(card);
                    return true;
                }
            }
            if (viableGroups1 != null) {
                for (ArrayList<Card> group : viableGroups1) {
                    if(card.getColor() == group.get(0).getColor()){
                        group.add(card);
                        return true;
                    }
                }
            }
            if (weakGroups1 != null) {
                for (ArrayList<Card> group : weakGroups1) {
                    if(card.getColor() == group.get(0).getColor()){
                        group.add(card);
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
                    return true;
                }
            }
            if (viableGroups2 != null) {
                for (ArrayList<Card> group : viableGroups2) {
                    if (card.getColor() == group.get(0).getColor()) {
                        group.add(card);
                        return true;
                    }
                }
            }
            if (weakGroups2 != null) {
                for (ArrayList<Card> group : weakGroups2) {
                    if (card.getColor() == group.get(0).getColor()) {
                        group.add(card);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean doDraw(Phase10GameState gameState, boolean hasPhased, int phase, ArrayList<Card> fullHand){
        boolean drawUp;
        Card topCard = gameState.getDiscardPile().peek();
        if(topCard.isWild()) drawUp = true;
        else if(checkGrowsGroup(topCard, phase, fullHand)){
            drawUp = true;
        }
        else if(hasPhased && checkIsHit(topCard, gameState)) drawUp = true;
        else drawUp = false;
        if(drawUp){
            DrawFaceUpAction act = new DrawFaceUpAction(this);
            game.sendAction(act);  //Send drawUp!!
        }
        else {
            DrawFaceDownAction act = new DrawFaceDownAction(this);
            game.sendAction(act); //Send drawDown!!
        }
        return true;
    }

    public boolean doDiscard(Phase10GameState gameState, boolean hasPhased, int playerId){                                       ///INCOMPLETE
        for(int i = 0; i < this.nonGroupCards.size(); i++){
            if(this.nonGroupCards.get(i).isSkip()){ //Skips discarded with highest priority (NON GROUP AH)
                DiscardAction act = new DiscardAction(this, i);
                game.sendAction(act); //Send Discard!!
                return true;
            }
        }


        //repeat for player ID 2
        //check for non-group cards (that arent hits, or wilds)
        //check for weak groups (that aren't
        //check for viable groups
        //discard hit cards
        return false;
    }

    public int checkPhaseReady(Phase10GameState gameState, int phase) {
        if(phase == 1 || phase == 2 || phase == 3 ||  phase == 7 ||  phase == 9 ||  phase == 10) {
            if (completeGroup1 != null && completeGroup2 != null) return 2; //Return 2 groups
        }
        else if(phase == 4 || phase == 5 || phase == 6 || phase == 8) {
            if (completeGroup1 != null) return 1; //Return 1 group
        }
        return 0; //Not ready to phase
    }
    public boolean doPhase(int phasing) {
        if(phasing == 1){ //1 phase group
            PhaseAction act = new PhaseAction(this, completeGroup1);
            game.sendAction(act); //Send Phase!!
            return true;
        }
        else if(phasing == 2){ //2 phase groups
            //Consolidate cards
            ArrayList<Card> temp = new ArrayList<Card>();
            for(Card c : completeGroup1) temp.add(c);
            for(Card c: completeGroup2) temp.add(c);

            PhaseAction act = new PhaseAction(this, temp);
            game.sendAction(act); //Send Phase!!
            return true;
        }
        return false;
    }

    public boolean checkHitReady(Phase10GameState gameState){                                       ///INCOMPLETE
        //look at hitting on self
        //Use checkGrowGroup???
        //if other player has phases, look at hitting on other player
        return false;
    }
    public boolean doHit(Card card, Phase10GameState gameState){                                       ///EMPTY
        return false;
    }
    public boolean makePotentialHits(ArrayList<Card> hand){                                       ///EMPTY
        return false;
    }
}
