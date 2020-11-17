/**
 * @author Kirsten Foster, Alexis Molina, Emily Hoppe, Grace Penunuri
 * Holds information about phases including phase rules, the cards used for the phase
 * Checks if phase is successful
 */

package up.edu.phase10;

import java.util.ArrayList;
import java.util.List;

public class Phase { //Wild card handling will be added in beta release

    //Phase Rules
    private String Phase1 = null; //"2 sets of 3";
    private String Phase2 = null; //"1 set of 3 and 1 run of 4";
    private String Phase3 = null; //"1 set of 4 and 1 run of 4";
    private String Phase4 = null; //"1 run of 7";
    private String Phase5 = null; //"1 run of 8";
    private String Phase6 = null; //"1 run of 9";
    private String Phase7 = null; //"2 sets of 4";
    private String Phase8 = null; //"7 cards of one color";
    private String Phase9 = null; //"1 set of 5 and 1 set of 2";
    private String Phase10 = null; //"1 set of 5 and 1 set of 3";

    //Phase reqs are placed in these variables when phasing happens

    //Player 1
    Card[] play1Run;
    Card[] play1Set1;
    Card[] play1Set2;
    Card[] play1Color;

    //Player 2
    Card[] play2Run;
    Card[] play2Set1;
    Card[] play2Set2;
    Card[] play2Color;

    /**
     * constructor class, with instance variables
     **/

    public Phase(){
        Phase1 = "2 sets of 3";
        Phase2 = "1 set of 3 and 1 run of 4";
        Phase3 = "1 set of 4 and 1 run of 4";
        Phase4 = "1 run of 7";
        Phase5 = "1 run of 8";
        Phase6 = "1 run of 9";
        Phase7 = "2 sets of 4";
        Phase8 = "7 cards of one color";
        Phase9 = "1 set of 5 and 1 set of 2";
        Phase10 = "1 set of 5 and 1 set of 3";
        //Player 1
        play1Run = null;
        play1Set1 = null;
        play1Set2 = null;
        play1Color = null;

        //Player 2
        play2Run = null;
        play2Set1 = null;
        play2Set2 = null;
        play2Color = null;
    }

    public Phase(Phase p){
        Phase1 = "2 sets of 3";
        Phase2 = "1 set of 3 and 1 run of 4";
        Phase3 = "1 set of 4 and 1 run of 4";
        Phase4 = "1 run of 7";
        Phase5 = "1 run of 8";
        Phase6 = "1 run of 9";
        Phase7 = "2 sets of 4";
        Phase8 = "7 cards of one color";
        Phase9 = "1 set of 5 and 1 set of 2";
        Phase10 = "1 set of 5 and 1 set of 3";
        if(p.play1Run != null) {
            this.play1Run = new Card[p.play1Run.length];
            for (int i = 0; i < this.play1Run.length; i++) {
                this.play1Run[i] = new Card(p.play1Run[i].getNumber(), p.play1Run[i].getColor());
            }
        }
        else{
            this.play1Run = null;
        }
        if(p.play1Set1 != null) {
            this.play1Set1 = new Card[p.play1Set1.length];
            for (int i = 0; i < this.play1Set1.length; i++) {
                this.play1Set1[i] = new Card(p.play1Set1[i].getNumber(), p.play1Set1[i].getColor());
            }
        }
        else{
            this.play1Set1 = null;
        }
        if(p.play1Set2 != null) {
            this.play1Set2 = new Card[p.play1Set2.length];
            for (int i = 0; i < this.play1Set2.length; i++) {
                this.play1Set2[i] = new Card(p.play1Set2[i].getNumber(), p.play1Set2[i].getColor());
            }
        }
        else{
            this.play1Set2 = null;
        }
        if(p.play1Color != null) {
            this.play1Color = new Card[p.play1Color.length];
            for (int i = 0; i < this.play1Color.length; i++) {
                this.play1Color[i] = new Card(p.play1Color[i].getNumber(), p.play1Color[i].getColor());
            }
        }
        else{
            this.play1Color = null;
        }
        if(p.play2Run != null) {
            this.play2Run = new Card[p.play2Run.length];
            for (int i = 0; i < this.play2Run.length; i++) {
                this.play2Run[i] = new Card(p.play2Run[i].getNumber(), p.play2Run[i].getColor());
            }
        }
        else{
            this.play2Run = null;
        }
        if(p.play2Set1 != null) {
            this.play2Set1 = new Card[p.play2Set1.length];
            for (int i = 0; i < this.play2Set1.length; i++) {
                this.play2Set1[i] = new Card(p.play2Set1[i].getNumber(), p.play2Set1[i].getColor());
            }
        }
        else{
            this.play2Set1 = null;
        }
        if(p.play2Set2 != null) {
            this.play2Set2 = new Card[p.play2Set2.length];
            for (int i = 0; i < this.play2Set2.length; i++) {
                this.play2Set2[i] = new Card(p.play2Set2[i].getNumber(), p.play2Set2[i].getColor());
            }
        }
        else{
            this.play2Set2 = null;
        }
        if(p.play1Color != null) {
            this.play1Color = new Card[p.play1Color.length];
            for (int i = 0; i < this.play1Color.length; i++) {
                this.play1Color[i] = new Card(p.play1Color[i].getNumber(), p.play1Color[i].getColor());
            }
        }
        else{
            this.play1Color = null;
        }
    }


    /** checks if the play can play a phase, first by seeing what phase
     * the player is on, then by referencing two different methods that checks
     * each card to make sure the play can hit
     * @param playerPhase the phase the player is currently on
     * @param phaseContent the cards the player is trying to phase with
     * @param playerNum the player's ID number
     *
     */
    public boolean checkPhase(int playerPhase, ArrayList<Card> phaseContent, int playerNum) {
        Card[] sorted = sortCards(phaseContent);
        switch (playerPhase) {
            case 1:
                if(sorted.length != 6){
                    return false;
                }
                return checkIfPhaseOne(sorted, playerNum);
            case 2:
                if(sorted.length != 7){
                    return false;
                }
                return checkIfPhaseTwo(sorted, playerNum);
            case 3:
                if(sorted.length != 8){
                    return false;
                }
                return checkIfPhaseThree(sorted, playerNum);
            case 4:
                if(sorted.length != 7){
                    return false;
                }
                return checkIfPhaseFour(sorted, playerNum);
            case 5:
                if(sorted.length != 8){
                    return false;
                }
                return checkIfPhaseFive(sorted, playerNum);
            case 6:
                if(sorted.length != 9){
                    return false;
                }
                return checkIfPhaseSix(sorted, playerNum);
            case 7:
                if(sorted.length != 8){
                    return false;
                }
                return checkIfPhaseSeven(sorted, playerNum);
            case 8: //Special Boy - 7 cards of 1 color
                sorted = sortCardsByColor(phaseContent);
                if(isColorGroup(sorted, 7,playerNum, false) != null) return true;
                if(sorted.length != 7){
                    return false;
                }
                if(isColorGroup(sorted, 7, playerNum, false) != null) return true;
                else return false;
            case 9:
                if(sorted.length != 7){
                    return false;
                }
                return checkIfPhaseNine(sorted, playerNum);
            case 10:
                if(sorted.length != 8){
                    return false;
                }
                return checkIfPhaseTen(sorted, playerNum);
            default:
                return false;
        }
    }

    /* Each boolean method checks for a specific phase as specified
        in the name, except for 8, which is a simple case that only
        checks for 7 cards of 1 color, and sets whatever parameter is not used to null
        --i.e. if it calls for sets but no runs, then runs for the player is set to null
     */


    /**
     * checks if the cards are appropriate for phase 1
     * 2 sets of 3
     *
     * @param phaseContent cards for phase attempt
     * @param playerNum number of player that is phasing
     * @return true if phase valid
     */
    public boolean checkIfPhaseOne(Card[] phaseContent, int playerNum){
        if(isSet(phaseContent,3, playerNum,1, false) != null) {
            Card[] temp = isSet(phaseContent,3, playerNum,1, false); //First set

            if(isSet(temp,3,playerNum,2, false) != null){ //Second set

                //set other phase qualifiers as null
                if(playerNum == 0) {
                    this.play1Run = null;
                    this.play1Color = null;
                }
                else if(playerNum == 1) {
                    this.play2Run = null;
                    this.play2Color = null;
                }
                if(isSet(temp,3,playerNum,2, false) == temp) return true; //make sure no cards are left
            }
        }
        return false;
    }

    /**
     * checks if the cards are appropriate for phase 2
     * 1 set of 3 and 1 run of 4
     *
     * @param phaseContent cards for phase attempt
     * @param playerNum number of player that is phasing
     * @return true if phase valid
     */
    public boolean checkIfPhaseTwo(Card[] phaseContent, int playerNum){
        if(isRun(phaseContent,4, playerNum, false) != null) {
            Card[] temp = isRun(phaseContent,4, playerNum, false); //Check for run

            if(isSet(temp,3,playerNum,1, false) != null){ //Check for set

                //set other phase qualifiers as null
                if(playerNum == 0) {
                    this.play1Set2 = null;
                    this.play1Color = null;
                }
                else if(playerNum == 1){
                    this.play2Set2 = null;
                    this.play2Color = null;
                }
                if(isSet(temp,3,playerNum,1, false) == temp) return true; //make sure no cards are left
            }
        }
        return false;
    }

    /**
     * checks if the cards are appropriate for phase 3
     * 1 set of 4 and 1 run of 4
     *
     * @param phaseContent cards for phase attempt
     * @param playerNum number of player that is phasing
     * @return true if phase valid
     */
    public boolean checkIfPhaseThree(Card[] phaseContent, int playerNum){
        if(isRun(phaseContent,4, playerNum, false) != null) {
            Card[] temp = isRun(phaseContent,4, playerNum, false); //Check for run

            if(isSet(temp,4,playerNum,1, false) != null){ //Check for set

                //set other phase qualifiers as null
                if(playerNum == 0){
                    this.play1Set2 = null;
                    this.play1Color = null;
                }
                else if(playerNum == 1){
                    this.play2Set2 = null;
                    this.play2Color = null;
                }
                if(isSet(temp,4,playerNum,1, false) == temp) return true; //make sure no cards are left
            }
        }
        return false;
    }

    /**
     * checks if the cards are appropriate for phase 4
     * 1 run of 7
     *
     * @param phaseContent cards for phase attempt
     * @param playerNum number of player that is phasing
     * @return true if phase valid
     */
    public boolean checkIfPhaseFour(Card[] phaseContent, int playerNum){
        if(isRun(phaseContent,7,playerNum, false) == null) return false; //Check for run
        else{

            //set other phase qualifiers as null
            if(playerNum == 0) {
                this.play1Set1 = null;
                this.play1Set2 = null;
                this.play1Color = null;
            }
            else if(playerNum == 1){
                this.play2Set1 = null;
                this.play2Set2 = null;
                this.play2Color = null;
            }
            if(isRun(phaseContent,7,playerNum, false) == phaseContent) return true; //make sure no cards are left
        }
        return false;
    }

    /**
     * checks if the cards are appropriate for phase 5
     * 1 run of 8
     *
     * @param phaseContent cards for phase attempt
     * @param playerNum number of player that is phasing
     * @return true if phase valid
     */
    public boolean checkIfPhaseFive(Card[] phaseContent, int playerNum){
        if(isRun(phaseContent,8,playerNum, false) == null) return false; //Check for run
        else{
            //set other phase qualifiers as null
            if(playerNum == 0) {
                this.play1Set1 = null;
                this.play1Set2 = null;
                this.play1Color = null;
            }
            else if(playerNum == 1){
                this.play2Set1 = null;
                this.play2Set2 = null;
                this.play2Color = null;
            }
            if(isRun(phaseContent,8,playerNum, false) == phaseContent) return true; //make sure no cards are left
        }
        return false;
    }

    /**
     * checks if the cards are appropriate for phase 6
     * 1 run of 9
     *
     * @param phaseContent cards for phase attempt
     * @param playerNum number of player that is phasing
     * @return true if phase valid
     */
    public boolean checkIfPhaseSix(Card[] phaseContent, int playerNum){
        if(isRun(phaseContent,9,playerNum, false) == null) return false; //Check for run
        else{
            //set other phase qualifiers as null
            if(playerNum == 0) {
                this.play1Set1 = null;
                this.play1Set2 = null;
                this.play1Color = null;
            }
            else if(playerNum == 1){
                this.play2Set1 = null;
                this.play2Set2 = null;
                this.play2Color = null;
            }
            if(isRun(phaseContent,9,playerNum, false) == phaseContent) return true; //make sure no cards are left
        }
        return false;
    }

    /**
     * checks if the cards are appropriate for phase 7
     * 2 sets of 4
     *
     * @param phaseContent cards for phase attempt
     * @param playerNum number of player that is phasing
     * @return true if phase valid
     */
    public boolean checkIfPhaseSeven(Card[] phaseContent, int playerNum){
        if(isSet(phaseContent,4, playerNum,1, false) != null) {
            Card[] temp = isSet(phaseContent,4, playerNum,1, false); //Check for set 1

            if(isSet(temp,4,playerNum,2, false) != null){ //Check for set 2

                //set other phase qualifiers as null
                if(playerNum == 0){
                    this.play1Run = null;
                    this.play1Color = null;
                }
                else if(playerNum == 1){
                    this.play2Run = null;
                    this.play2Color = null;
                }
                if(isSet(temp,4,playerNum,2, false) == temp) return true; //make sure no cards are left
            }
        }
        return false;
    }

    /**
     * checks if the cards are appropriate for phase 9
     * 1 set of 5 and 1 set of 2
     *
     * @param phaseContent cards for phase attempt
     * @param playerNum number of player that is phasing
     * @return true if phase valid
     */
    public boolean checkIfPhaseNine(Card[] phaseContent, int playerNum){

        if(isSet(phaseContent,5, playerNum,1, false) != null) {
            Card[] temp = isSet(phaseContent,5, playerNum,1, false); //Check for set 1

            if(isSet(temp,2,playerNum,2, false) != null){ //Check for set 2

                //set other phase qualifiers as null
                if(playerNum == 0) {
                    this.play1Run = null;
                    this.play1Color = null;
                }
                else if(playerNum == 1) {
                    this.play2Run = null;
                    this.play2Color = null;
                }
                if(isSet(temp,2,playerNum,2, false) == temp) return true;//make sure no cards are left
            }
        }
        return false;
    }

    /**
     * checks if the cards are appropriate for phase 10
     * 1 set of 5 and 1 set of 3
     *
     * @param phaseContent cards for phase attempt
     * @param playerNum number of player that is phasing
     * @return true if phase valid
     */
    public boolean checkIfPhaseTen(Card[] phaseContent, int playerNum){
        if(isSet(phaseContent,5, playerNum,1,false) != null) {
            Card[] temp = isSet(phaseContent,5, playerNum,1, false); //Check for set 1

            if(isSet(temp,3,playerNum,2, false) != null){ //Check for set 2

                //set other phase qualifiers as null
                if(playerNum == 0){
                    this.play1Run = null;
                    this.play1Color = null;
                }
                else if(playerNum == 1){
                    this.play2Run = null;
                    this.play2Color = null;
                }
                if(isSet(temp,3,playerNum,2, false) == temp) return true;//make sure no cards are left
            }
        }
        return false;
    }
    // end of checkIfPhases

    /**
     * isSet checks to find the same number within the phaseContent of a player,
     *        then sorts those that are the same number into a temporary array
     *        which is then assigned to a set for a specific player whether that set
     *        be the first or second set, depending on the checkIfPhase()
     *        parameters. The remaining not used are stored in a seperate array.
     * @param checkForSet the cards being checked for set match
     * @param size the size of the set being searched for
     * @param playerNum the number of the player that is phasing
     * @param setNum 1 or 2 based on if this is the first or second set
     * @param test if true it is used by computer player and instane variables will not be set
     * @return null if unsuccessful, the extra cards if successful and cards are leftover, or the set if no extra cards
     */
    private Card[] isSet(Card[] checkForSet, int size, int playerNum, int setNum, boolean test){
        Card[] temp;
        Card[] notInSet;
        int notInSetLoc;
        int tempLoc;
        for(int i = 0; i < checkForSet.length; i++) {
            //Reset temp
            temp = new Card[size];
            temp[0] = checkForSet[i];
            tempLoc = 0;
            notInSet = new Card[checkForSet.length-size];
            notInSetLoc = 0;
            for (int j = i + 1; j < checkForSet.length; j++) {
                if (checkForSet[j].getNumber() == temp[tempLoc].getNumber() && tempLoc < size-1) {
                    temp[tempLoc + 1] = checkForSet[j];
                    tempLoc++;
                } else {
                    if(notInSet.length > 0) {
                        if(notInSetLoc < notInSet.length) {
                            notInSet[notInSetLoc] = checkForSet[j];
                            notInSetLoc++;
                        }
                        else{
                            break;
                        }
                    }
                }
            }
            if(tempLoc >= temp.length -1){
                if(playerNum == 0) {
                    for(int b = 0; b < i; b++){
                        while(notInSetLoc < notInSet.length) {
                            notInSet[notInSetLoc] = checkForSet[b];
                            notInSetLoc++;
                        }
                    }

                    if(test){
                        Card[] test1 = new Card[11];
                        test1[0] = new Card(40,40);
                        return test1;}

                    if(setNum == 1){
                        this.play1Set1 = temp;
                        if(notInSetLoc == 0){
                            return checkForSet;
                        }
                        return notInSet;
                    }
                    else if(setNum == 2){
                        this.play1Set2 = temp;
                        if(notInSetLoc == 0){
                            return checkForSet;
                        }
                        return notInSet;
                    }

                }
                else if(playerNum == 1){
                    for(int b = 0; b < i; b++){
                        while(notInSetLoc < notInSet.length) {
                            notInSet[notInSetLoc] = checkForSet[b];
                            notInSetLoc++;
                        }
                    }

                    if(test){
                        Card[] test1 = new Card[11];
                        test1[0] = new Card(40,40);
                        return test1;}

                    if(setNum == 1){
                        this.play2Set1 = temp;
                        if(notInSetLoc == 0){
                            return checkForSet;
                        }
                        return notInSet;
                    }
                    else if(setNum == 2){
                        this.play2Set2 = temp;
                        if(notInSetLoc == 0){
                            return checkForSet;
                        }
                        return notInSet;
//                        if(notInSet.length > 0) {
//                            return notInSet;
//                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * isRun sorts through to play chronologically and stores them as an array for
     * specific player, and those that cannot be sorted chronilogically are stored
     * in an array for those not in a run
     *
     * @param checkForRun the cards being checked for run match
     * @param size the size of the color set being searched for
     * @param playerNum the number of the player that is phasing
     * @param test if true it is used by computer player and instance variables will not be set
     * @return null if unsuccessful, the extra cards if successful and cards are leftover, or the set if no extra cards
     */
    private Card[] isRun(Card[] checkForRun, int size, int playerNum, boolean test){
        Card[] temp;
        Card[] notInRun;
        int notInRunLoc;
        int tempLoc;
        for(int i = 0; i<checkForRun.length; i++) {
            //Reset temp
            temp = new Card[size];
            temp[0] = checkForRun[i];
            tempLoc = 0;
            notInRun = new Card[checkForRun.length-size];
            notInRunLoc = 0;
            for (int j = i + 1; j < checkForRun.length; j++) {
                if (checkForRun[j].getNumber() == temp[tempLoc].getNumber() + 1) {
                    temp[tempLoc + 1] = checkForRun[j];
                    tempLoc++;
                } else {
                    if(notInRun.length > 0) {
                        if(notInRunLoc < notInRun.length) {
                            notInRun[notInRunLoc] = checkForRun[j];
                            notInRunLoc++;
                        }
                        else{
                            break;
                        }
                    }
                }

            }
            if(tempLoc >= temp.length -1){

                if(test){
                    Card[] test1 = new Card[11];
                    test1[0] = new Card(40,40);
                    return test1;}

                if(playerNum == 0) {

                    for(int b = 0; b < i; b++){
                        while(notInRunLoc < notInRun.length) {
                            notInRun[notInRunLoc] = checkForRun[b];
                            notInRunLoc++;
                        }
                    }
                    this.play1Run = temp;
                    if(notInRunLoc == 0){
                        return checkForRun;
                    }
                    return notInRun;
                }
                else if(playerNum == 1){
                    for(int b = 0; b < i; b++){
                        while(notInRunLoc < notInRun.length) {
                            notInRun[notInRunLoc] = checkForRun[b];
                            notInRunLoc++;
                        }
                    }
                    this.play2Run = temp;
                    if(notInRunLoc == 0){
                        return checkForRun;
                    }
                    if(notInRun.length > 0) {
                        return notInRun;
                    }
                }
            }
        }
        return null;
    }


    /**
     * isColorGroup check runs through the cards and checks to see how many of the
     *        cards are the same card, checkForColor parameter stores the color in an array,
     *        the size indicates how many need to be the same color, and the playerNum
     *        allows us to designate to which Card set for which player needs to assign the remaining
     *        unused cards and the used cards to which color set
     * @param checkForColor the cards being checked for color match
     * @param size the size of the color set being searched for
     * @param playerNum the number of the player that is phasing
     * @param test if true it is used by computer player and instance variables will not be set
     * @return null if unsuccessful, the extra cards if successful and cards are leftover, or the set if no extra cards
     */
    private Card[] isColorGroup(Card[] checkForColor, int size, int playerNum, boolean test){
        Card[] temp;
        Card[] notInColor;
        int notInColorLoc;
        int tempLoc;
        for(int i = 0; i<checkForColor.length; i++) {
            //Reset temp
            temp = new Card[size];
            temp[0] = checkForColor[i];
            tempLoc = 0;
            notInColor = new Card[checkForColor.length-size];
            notInColorLoc = 0;
            for (int j = i + 1; j < checkForColor.length; j++) {

                if (checkForColor[j].getColor() == temp[tempLoc].getColor()) {
                    temp[tempLoc + 1] = checkForColor[j];
                    tempLoc++;
                } else {
                    if(notInColor.length > 0) {
                        if(notInColorLoc < notInColor.length) {
                            notInColor[notInColorLoc] = checkForColor[j];
                            notInColorLoc++;
                        }
                        else{
                            return null;
                        }
                    }
                }

            }
            if(tempLoc >= temp.length -1){

                if(test){
                    Card[] test1 = new Card[11];
                    test1[0] = new Card(40,40);
                    return test1;}

                if(playerNum == 0) {
                    for(int b = 0; b < i; b++){
                        while(notInColorLoc < notInColor.length) {
                            notInColor[notInColorLoc] = checkForColor[b];
                            notInColorLoc++;
                        }
                    }
                    this.play1Color = temp;
                    if(notInColorLoc == 0){
                        return checkForColor;
                    }
                    return notInColor;
                }
                else if(playerNum == 1){
                    for(int b = 0; b < i; b++){
                        while(notInColorLoc < notInColor.length) {
                            notInColor[notInColorLoc] = checkForColor[b];
                            notInColorLoc++;
                        }
                    }
                    this.play2Color = temp;
                    if(notInColorLoc == 0){
                        return checkForColor;
                    }
                    if(notInColor.length>0) {
                        return notInColor;
                    }
                }
            }

        }
        return null;
    }

    /**
     * sortCards sorts through the cards of phaseContent so that
     * for the isRun, isSet, isColorGroup methods the cards can be
     * sorted from smallest to largest via card array as opposed to an ArrayList
     *
     * @param attempt the cards attempting to be phased
     * @return the sorted card array
     */
    private Card[] sortCards(ArrayList<Card> attempt){
        Card[] arr = new Card[attempt.size()];
        int x = 0;
        while(x < attempt.size()){
            arr[x] = attempt.get(x);
            x++;
        }
        for (int i = 0; i < arr.length - 1; i++){
            int index = i;
            for (int j = i + 1; j < arr.length; j++){
                if (arr[j].getNumber() < arr[index].getNumber()) {
                    index = j; //searching for lowest index
                }
            }
            Card smallestNumberCard = arr[index];
            arr[index] = arr[i];
            arr[i] = smallestNumberCard;
        }
        return arr;
    }

    /**
     * sortCards sorts the cards by color instead of number
     *
     * @param attempt the cards attempting to be phased
     * @return the sorted card array
     */
    private Card[] sortCardsByColor(ArrayList<Card> attempt){
        Card[] arr = new Card[attempt.size()];
        int x = 0;
        while(x < attempt.size()){
            arr[x] = attempt.get(x);
            x++;
        }
        for (int i = 0; i < arr.length - 1; i++){
            int index = i;
            for (int j = i + 1; j < arr.length; j++){
                if (arr[j].getColor() < arr[index].getColor()) {
                    index = j; //searching for lowest index
                }
            }
            Card smallestNumberCard = arr[index];
            arr[index] = arr[i];
            arr[i] = smallestNumberCard;
        }
        return arr;
    }



    /**
     * checkHitValid checks if the selected card is valid
     *
     * @param selectedCard the card that is being hit
     * @param playerNum the player that is being hit on
     * @param test if true it is used by computer player and instane variables will not be set
     * @return true if hit is successful
     */
    public boolean checkHitValid(Card selectedCard, int playerNum, boolean test) {
        if (playerNum == 0) {
            //Runs
            if(this.play1Run != null) {
                Card[] tempPlay1Run = new Card[play1Run.length + 1];
                for (int i = 0; i < play1Run.length; i++) {
                    tempPlay1Run[i] = play1Run[i];
                }
                tempPlay1Run[play1Run.length] = selectedCard;
                if (!(isRun(tempPlay1Run, tempPlay1Run.length, playerNum, test) == null)) return true;
            }
            //Sets
            if(this.play1Set1 != null) {
                Card[] tempPlay1Set1 = new Card[play1Set1.length + 1];
                for (int i = 0; i < play1Set1.length; i++) {
                    tempPlay1Set1[i] = play1Set1[i];
                }
                tempPlay1Set1[play1Set1.length] = selectedCard;
                if(!(isSet(tempPlay1Set1, tempPlay1Set1.length, playerNum,1, test)==null)){
                    return true;
                }
            }
            if(this.play1Set2 != null) {
                Card[] tempPlay1Set2 = new Card[play1Set2.length + 1];
                for (int i = 0; i < play1Set2.length; i++) {
                    tempPlay1Set2[i] = play1Set2[i];
                }
                tempPlay1Set2[play1Set2.length] = selectedCard;
                if(!(isSet(tempPlay1Set2, tempPlay1Set2.length, playerNum, 2, test)==null)) return true;
            }


            //Colors
            if(this.play1Color != null) {
                Card[] tempPlay1Color = new Card[play1Color.length + 1];
                for (int i = 0; i < play1Color.length; i++) {
                    tempPlay1Color[i] = play1Color[i];
                }
                tempPlay1Color[play1Color.length] = selectedCard;

                if (!(isColorGroup(tempPlay1Color, tempPlay1Color.length, playerNum, test) == null))
                    return true;
            }
        } else if (playerNum == 1) {

            //Runs
            if(this.play2Run != null) {
                Card[] tempPlay2Run = new Card[play2Run.length + 1];
                for (int i = 0; i < play2Run.length; i++) {
                    tempPlay2Run[i] = play2Run[i];
                }
                tempPlay2Run[play2Run.length] = selectedCard;
                if (!(isRun(tempPlay2Run, tempPlay2Run.length, playerNum, test) == null)) return true;
            }

            //Sets
            if(this.play2Set1 != null) {
                Card[] tempPlay2Set1 = new Card[play2Set1.length + 1];
                for (int i = 0; i < play2Set1.length; i++) {
                    tempPlay2Set1[i] = play2Set1[i];
                }
                tempPlay2Set1[play2Set1.length] = selectedCard;
                if(!(isSet(tempPlay2Set1, tempPlay2Set1.length, playerNum,1, test)==null)) return true;
            }
            if(this.play2Set2 != null) {
                Card[] tempPlay2Set2 = new Card[play2Set2.length + 1];
                for (int i = 0; i < play2Set2.length; i++) {
                    tempPlay2Set2[i] = play2Set2[i];
                }
                tempPlay2Set2[play2Set2.length] = selectedCard;
                if(!(isSet(tempPlay2Set2, tempPlay2Set2.length, playerNum, 2, test)==null)) return true;
            }

            //Colors
            if(this.play2Color != null) {
                Card[] tempPlay2Color = new Card[play2Color.length + 1];
                for (int i = 0; i < play2Color.length; i++) {
                    tempPlay2Color[i] = play2Color[i];
                }
                tempPlay2Color[play2Color.length] = selectedCard;

                if (!(isColorGroup(tempPlay2Color, tempPlay2Color.length, playerNum, test) == null))
                    return true;
            }
        }
        return false;
    }//checkHitValid end
} // end of Phase class
