
package up.edu.phase10;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Kirsten Foster, Alexis Molina, Emily Hoppe, Grace Penunuri
 * Holds information about phases including phase rules, the cards used for the phase
 * Checks if phase is successful
 */
public class Phase {

    //Phase Rules
    private String Phase1; //"2 sets of 3";
    private String Phase2; //"1 set of 3 and 1 run of 4";
    private String Phase3; //"1 set of 4 and 1 run of 4";
    private String Phase4; //"1 run of 7";
    private String Phase5; //"1 run of 8";
    private String Phase6; //"1 run of 9";
    private String Phase7; //"2 sets of 4";
    private String Phase8; //"7 cards of one color";
    private String Phase9; //"1 set of 5 and 1 set of 2";
    private String Phase10; //"1 set of 5 and 1 set of 3";

    //Phase reqs are placed in these variables when phasing happens

    //Player 1
    public Card[] play1Run;
    public Card[] play1Set1;
    public Card[] play1Set2;
    public Card[] play1Color;

    //Player 2
    public Card[] play2Run;
    public Card[] play2Set1;
    public Card[] play2Set2;
    public Card[] play2Color;

    //getters for phase strings
    public String getPhase1(){
        return Phase1;
    }
    public String getPhase2(){
        return Phase2;
    }
    public String getPhase3(){
        return Phase3;
    }
    public String getPhase4(){
        return Phase4;
    }
    public String getPhase5(){
        return Phase5;
    }
    public String getPhase6(){
        return Phase6;
    }
    public String getPhase7(){
        return Phase7;
    }
    public String getPhase8(){
        return Phase8;
    }
    public String getPhase9(){
        return Phase9;
    }
    public String getPhase10(){
        return Phase10;
    }
    /**
     * constructor class, with instance variables
     **/

    public Phase(){
        Phase1 = "2 sets of 3";
        Phase2 = "1 set of 3 and\n1 run of 4";
        Phase3 = "1 set of 4 and\n1 run of 4";
        Phase4 = "1 run of 7";
        Phase5 = "1 run of 8";
        Phase6 = "1 run of 9";
        Phase7 = "2 sets of 4";
        Phase8 = "7 cards of one color";
        Phase9 = "1 set of 5 and\n1 set of 2";
        Phase10 = "1 set of 5 and\n1 set of 3";
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

    /**
     * copy constructor
     * @param p phase objec that is being copied
     */
    public Phase(Phase p){
        //Set strings
        Phase1 = "2 sets of 3";
        Phase2 = "1 set of 3 and\n1 run of 4";
        Phase3 = "1 set of 4 and\n1 run of 4";
        Phase4 = "1 run of 7";
        Phase5 = "1 run of 8";
        Phase6 = "1 run of 9";
        Phase7 = "2 sets of 4";
        Phase8 = "7 cards of one color";
        Phase9 = "1 set of 5 and\n1 set of 2";
        Phase10 = "1 set of 5 and\n1 set of 3";
        //copy over phase groups
        //Player 1
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
        //Player 2
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
        Card[] sorted = sortCards(phaseContent); //Sort cards by number
        //Identify phase and check if cards qualify
        switch (playerPhase) {
            case 1:
                if(sorted.length != 6){ //Checks length validity
                    return false;
                }
                return checkIfPhaseOne(sorted, playerNum); //Checks card validity
            case 2:
                if(sorted.length != 7){//Checks length validity
                    return false;
                }
                return checkIfPhaseTwo(sorted, playerNum);//Checks card validity
            case 3:
                if(sorted.length != 8){//Checks length validity
                    return false;
                }
                return checkIfPhaseThree(sorted, playerNum);//Checks card validity
            case 4:
                if(sorted.length != 7){//Checks length validity
                    return false;
                }
                return checkIfPhaseFour(sorted, playerNum);//Checks card validity
            case 5:
                if(sorted.length != 8){//Checks length validity
                    return false;
                }
                return checkIfPhaseFive(sorted, playerNum);//Checks card validity
            case 6:
                if(sorted.length != 9){//Checks length validity
                    return false;
                }
                return checkIfPhaseSix(sorted, playerNum);//Checks card validity
            case 7:
                if(sorted.length != 8){//Checks length validity
                    return false;
                }
                return checkIfPhaseSeven(sorted, playerNum);//Checks card validity
            case 8: //Special Boy - 7 cards of 1 color
                sorted = sortCardsByColor(phaseContent);
                if(sorted.length != 7){//Checks length validity
                    return false;
                }
                if(isColorGroup(sorted, 7, playerNum, false) != null) return true;//Checks card validity
                else return false;
            case 9:
                if(sorted.length != 7){//Checks length validity
                    return false;
                }
                return checkIfPhaseNine(sorted, playerNum);//Checks card validity
            case 10:
                if(sorted.length != 8){//Checks length validity
                    return false;
                }
                return checkIfPhaseTen(sorted, playerNum);//Checks card validity
            default: //Default - exit
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
        if(isRun(phaseContent,4, playerNum, false,3) != null) {
            Card[] temp = isRun(phaseContent,4, playerNum, false, 3); //Check for run

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
        if(isRun(phaseContent,4, playerNum, false, 4) != null) {
            Card[] temp = isRun(phaseContent,4, playerNum, false, 4); //Check for run

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
        if(isRun(phaseContent,7,playerNum, false, 0) == null) return false; //Check for run
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
            if(isRun(phaseContent,7,playerNum, false, 0) == phaseContent) return true; //make sure no cards are left
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
        if(isRun(phaseContent,8,playerNum, false, 0) == null) return false; //Check for run
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
            if(isRun(phaseContent,8,playerNum, false, 0) == phaseContent) return true; //make sure no cards are left
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
        if(isRun(phaseContent,9,playerNum, false, 0) == null) return false; //Check for run
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
            if(isRun(phaseContent,9,playerNum, false, 0) == phaseContent) return true; //make sure no cards are left
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
        Card[] checkForSetStore = checkForSet;
        if(checkForSet[0] != null && checkForSet[0].isWild()){ //Sometimes will be passed an unsorted group
            ArrayList<Card> toSort = new ArrayList<Card>();
            for(int i = 0; i < checkForSet.length; i++){
                if(checkForSet[i] != null) toSort.add(checkForSet[i]);
            }
            checkForSet = sortCards(toSort); //sort
        }
        if(checkForSet == null) return null;
        Card[] temp;
        Card[] notInSet;
        int notInSetLoc;
        int tempLoc;
        for(int i = 0; i < checkForSet.length; i++) {  //Increment through cards in hand
            //Reset temp
            temp = new Card[size];
            temp[0] = checkForSet[i];
            int currentNum = 0;
            if(temp[0] != null) currentNum = temp[0].getNumber();
            tempLoc = 0;
            int z = checkForSet.length - size;
            if(z < 0) z = 0;
            notInSet = new Card[z];
            notInSetLoc = 0;
            for (int j = i + 1; j < checkForSet.length; j++) {//Check against following cards for set
                if(checkForSet[j] == null || temp[tempLoc] == null || checkForSet[j] == null) break;
                if ((currentNum == checkForSet[j].getNumber() || checkForSet[j].isWild()) && tempLoc < size-1 && !checkForSet[j].isSkip()) { //same num or wild
                    if(tempLoc + 1 >= temp.length) break;
                    temp[tempLoc + 1] = checkForSet[j];
                    tempLoc++;
                   if(checkForSet[j].isWild()){
                        checkForSet[j].setNumber(currentNum); //Wild card number changed to agree with set
                   }
                } else {
                    if(notInSet.length > 0) { //else, add to extra card array
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
            if(tempLoc >= (temp.length - 1)){
                if(playerNum == 0) {
                    for(int b = 0; b < i; b++){ //Add any missed cards back into extra array
                        if(notInSetLoc < notInSet.length) {
                            notInSet[notInSetLoc] = checkForSet[b];
                            notInSetLoc++;
                        }
                    }
                    for(int b = checkForSet.length - 1; b > tempLoc + i; b--){ //Add any missed cards back into extra array
                        if(notInSetLoc < notInSet.length) {
                            notInSet[notInSetLoc] = checkForSet[b];
                            notInSetLoc++;
                        }
                    }
                    if(test){ //if test and test successful, return an arbitrary array
                        Card[] test1 = new Card[11];
                        test1[0] = new Card(40,40);
                        return test1;}
                    //Set instance variables
                    if(setNum == 1){
                        this.play1Set1 = temp;
                        if(notInSetLoc == 0){ //No extra cards -> return set
                            return checkForSetStore;
                        }
                        return notInSet; //Extra cards -> return them
                    }
                    else if(setNum == 2){
                        this.play1Set2 = temp;
                        if(notInSetLoc == 0){//No extra cards -> return set
                            return checkForSetStore;
                        }
                        return notInSet;//Extra cards -> return them
                    }

                }
                else if(playerNum == 1){
                    for(int b = 0; b < i; b++){//Add any missed cards back into extra array
                        if(notInSetLoc < notInSet.length) {
                            notInSet[notInSetLoc] = checkForSet[b];
                            notInSetLoc++;
                        }
                    }
                    for(int b = checkForSet.length - 1; b > tempLoc + i; b--){ //Add any missed cards back into extra array
                        if(notInSetLoc < notInSet.length) {
                            notInSet[notInSetLoc] = checkForSet[b];
                            notInSetLoc++;
                        }
                    }
                    if(test){//if test and test successful, return an arbitrary array
                        Card[] test1 = new Card[11];
                        test1[0] = new Card(40,40);
                        return test1;}
                    //Set instance variables
                    if(setNum == 1){
                        this.play2Set1 = temp;
                        if(notInSetLoc == 0){//No extra cards -> return set
                            return checkForSetStore;
                        }
                        return notInSet; //Extra cards -> return them
                    }
                    else if(setNum == 2){
                        this.play2Set2 = temp;
                        if(notInSetLoc == 0){//No extra cards -> return set
                            return checkForSetStore;
                        }
                        return notInSet; //Extra cards -> return them
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
     * @param setSize size of set if phase req also has a set, if not setSize = 0
     * @return null if unsuccessful, the extra cards if successful and cards are leftover, or the set if no extra cards
     */
    public Card[] isRun(Card[] checkForRun, int size, int playerNum, boolean test, int setSize){
        if(checkForRun == null){
            return null;
        }
        int wildsStore = 0;
        for(int i = 0; i < checkForRun.length; i++){ //Count wilds
            if(checkForRun[i].isWild()) wildsStore++;
        }
        Card[] temp;
        Card[] notInRun;
        int notInRunLoc;
        int tempLoc;
        for(int i = 0; i<checkForRun.length; i++) {//Increment through cards in hand
            //Reset temporary values
            temp = new Card[size];
            temp[0] = checkForRun[i];
            int currentNum = 0;
            if(temp[0] != null) currentNum = temp[0].getNumber();
            tempLoc = 0;
            int z = checkForRun.length - size;
            if(z < 0) z = 0;
            notInRun = new Card[z];
            notInRunLoc = 0;
            int wilds = wildsStore;
            for (int j = i + 1; j < checkForRun.length; j++) { //Check against following cards for consecutive
                if(notInRunLoc > 1 && notInRun[notInRunLoc - 1].getNumber() == notInRun[notInRunLoc - 2].getNumber()
                        && notInRunLoc < setSize && notInRun[notInRunLoc - 1].getNumber() == checkForRun[j].getNumber() ) { //ignore card match if its part of a set
                    if(notInRunLoc < notInRun.length) {
                        notInRun[notInRunLoc] = checkForRun[j];
                        notInRunLoc++;
                    }
                }
                else if(checkForRun[j].getNumber() == currentNum + 1 ) { //card is one number higher than previous
                    if(tempLoc+1 >= temp.length) break;
                    temp[tempLoc + 1] = checkForRun[j];
                    tempLoc++;
                    currentNum++;
                }
                else if(wilds > 0){ //available wild card
                    if(checkForRun[checkForRun.length - wilds].isWild() && tempLoc < temp.length -1){
                        temp[tempLoc + 1] = checkForRun[checkForRun.length - wilds];
                        tempLoc++;
                        //Wild card number changed to agree with run
                        if(currentNum + 1 <= 12) checkForRun[checkForRun.length - wilds].setNumber(currentNum + 1); //Case wild is placed after a 12
                        else if(currentNum == 100) checkForRun[checkForRun.length - wilds].setNumber(100); //Case for run of wilds
                        else checkForRun[checkForRun.length - wilds].setNumber(temp[0].getNumber() -1);
                        if(!(tempLoc > 0 && currentNum == 100)) currentNum++;
                        wilds--;
                        j--; //Card was no actually compared, compare again
                        if(tempLoc >= temp.length -1)break;
                    }
                }
                else {
                    if(notInRun.length > 0) { //else, add to extra card array
                        if (notInRunLoc < notInRun.length) {
                            if (checkForRun[j].isWild()) { // only add wilds to extra card array if they havent been used in run
                                if (wilds > 0) {
                                    notInRun[notInRunLoc] = checkForRun[j];
                                    notInRunLoc++;
                                    wilds--;
                                }
                            } else {
                                notInRun[notInRunLoc] = checkForRun[j];
                                notInRunLoc++;
                            }
                        } else {
                            break;
                        }
                    }
                    else{
                        break;
                    }

                }

            }
            if(tempLoc >= temp.length -1){

                if(test){ //if test and test successful, return an arbitrary array
                    Card[] test1 = new Card[11];
                    test1[0] = new Card(40,40);
                  //  Log.d("Phase","Exit isRun()");
                    return test1;
                }

                if(playerNum == 0) {
                    for(int b = 0; b < i; b++){ //Add any missed cards back into extra array
                        if(notInRunLoc < notInRun.length) {
                            notInRun[notInRunLoc] = checkForRun[b];
                            notInRunLoc++;
                        }
                    }
                    for(int b = checkForRun.length - 1; b+1 >= tempLoc + i; b--){ //Add any missed cards back into extra array
                        if(notInRunLoc < notInRun.length) {
                            if(checkForRun[b].isWild()){
                                if(wilds > 0){
                                   notInRun[notInRunLoc] = checkForRun[b];
                                   notInRunLoc++;
                                   wilds--;
                               }
                            }
                            else {
                                notInRun[notInRunLoc] = checkForRun[b];
                                notInRunLoc++;
                            }
                        }
                    }
                    if(setSize > 0 ){ //if phase includes a set, make sure the extra cards are a set, otherwise move on
                        if(isSet(notInRun, setSize, playerNum, 1, true) == null){
                            continue;
                        }
                    }
                    this.play1Run = temp; //set instance variable
                    if(notInRunLoc == 0){//No extra cards -> return run
                       // Log.d("Phase","Exit isRun()");
                        return checkForRun;
                    }
                    if(notInRun.length > 0) { //extra cards -> return those for continued testing
                       // Log.d("Phase", "Exit isRun()");
                        return notInRun;
                    }
                }
                else if(playerNum == 1){
                    for(int b = 0; b < i; b++){ //Add any missed cards back into extra array
                        if(notInRunLoc < notInRun.length) {
                            notInRun[notInRunLoc] = checkForRun[b];
                            notInRunLoc++;
                        }
                    }
                    for(int b = checkForRun.length - 1; b +1 >= tempLoc + i; b--){ //Add any missed cards back into extra array
                        if(notInRunLoc < notInRun.length) {
                            if(checkForRun[b].isWild()){
                                if(wilds > 0){
                                    notInRun[notInRunLoc] = checkForRun[b];
                                    notInRunLoc++;
                                    wilds--;
                                }
                            }
                            else {
                                notInRun[notInRunLoc] = checkForRun[b];
                                notInRunLoc++;
                            }
                        }
                    }
                    if(setSize > 0){//if phase includes a set, make sure the extra cards are a set, otherwise move on
                        if(isSet(notInRun, setSize, playerNum, 1, true) == null){
                            continue;
                        }
                    }
                    this.play2Run = temp;//set instance variable
                    if(notInRunLoc == 0){//No extra cards -> return run
                        return checkForRun;
                    }
                    if(notInRun.length > 0) { //extra cards -> return those for continued testing
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
    public Card[] isColorGroup(Card[] checkForColor, int size, int playerNum, boolean test){
        if(checkForColor == null) return null; //illegal play, exit
        Card[] temp;
        Card[] notInColor;
        int notInColorLoc;
        int tempLoc;
        for(int i = 0; i<checkForColor.length; i++) { //Increment through cards in hand
            //Reset temp
            temp = new Card[size];
            temp[0] = checkForColor[i];
            tempLoc = 0;
            int currentCol = 0;
            if(temp[0] != null) currentCol = temp[0].getColor();
            int z = checkForColor.length - size;
            if(z < 0) z = 0;
            notInColor = new Card[z];
            notInColorLoc = 0;
            for (int j = i + 1; j < checkForColor.length; j++) {//Check against following cards for same color
                if(checkForColor[j] == null || temp[tempLoc] == null) break;
                if (checkForColor[j].getColor() == currentCol || checkForColor[j].isWild() && !checkForColor[j].isSkip()) { //Same color or wild
                    if(tempLoc+1 >= temp.length) break;
                    temp[tempLoc + 1] = checkForColor[j];
                    tempLoc++;
                    if(checkForColor[j].isWild()) checkForColor[j].setColor(currentCol); //Wild card number changed to agree with color
                } else {
                    if(notInColor.length > 0) { //Not part of color group
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
                if(test){//test successful - return arbitrary array
                    Card[] test1 = new Card[11];
                    test1[0] = new Card(40,40);
                    return test1;}

                if(playerNum == 0) {
                    for(int b = 0; b < i; b++){//Add any missed cards back into extra array
                        if(notInColorLoc < notInColor.length) {
                            notInColor[notInColorLoc] = checkForColor[b];
                            notInColorLoc++;
                        }
                    }
                    for(int b = checkForColor.length - 1; b > tempLoc + i; b--){ //Add any missed cards back into extra array
                        if(notInColorLoc < notInColor.length) {
                            notInColor[notInColorLoc] = checkForColor[b];
                            notInColorLoc++;
                        }
                    }
                    this.play1Color = temp;;//set instance variable
                    if(notInColorLoc == 0){//No extra cards -> return run
                        return checkForColor;
                    }
                    if(notInColor.length>0) { //extra cards -> return those for continued testing
                        return notInColor;
                    }
                }
                else if(playerNum == 1){
                    for(int b = 0; b < i; b++){//Add any missed cards back into extra array
                        if(notInColorLoc < notInColor.length) {
                            notInColor[notInColorLoc] = checkForColor[b];
                            notInColorLoc++;
                        }
                    }
                    for(int b = checkForColor.length - 1; b > tempLoc + i; b--){//Add any missed cards back into extra array
                        if(notInColorLoc < notInColor.length) {
                            notInColor[notInColorLoc] = checkForColor[b];
                            notInColorLoc++;
                        }
                    }
                    this.play2Color = temp;;//set instance variable
                    if(notInColorLoc == 0){//No extra cards -> return run
                        return checkForColor;
                    }
                    if(notInColor.length>0) { //extra cards -> return those for continued testing
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
    public Card[] sortCards(ArrayList<Card> attempt){
        Card[] arr = new Card[attempt.size()];
        int x = 0;
        while(x < attempt.size()){ //convert arraylist to array
            arr[x] = attempt.get(x);
            x++;
        }
        //Sort by number
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
     * sorts the cards by number order in an array
     * helper method for handling wilds
     *
     * @param attempt the cards attempting to be phased
     * @return the sorted card array
     */
    private Card[] sortRaw(Card[] attempt){
        Card[] arr = new Card[attempt.length];
        int x = 0;
        while(x < attempt.length){ //Copy array
            arr[x] = attempt[x];
            x++;
        }
        //Sort by number
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
     * for specific use in phase 8
     *
     * @param attempt the cards attempting to be phased
     * @return the sorted card array
     */
    private Card[] sortCardsByColor(ArrayList<Card> attempt){
        Card[] arr = new Card[attempt.size()];
        int x = 0;
        //Convert into an array
        while(x < attempt.size()){
            arr[x] = attempt.get(x);
            x++;
        }
        //Sort by color
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
        if(selectedCard == null || selectedCard.isSkip()) return false;
        if (playerNum == 0) {
            //Runs
            if(this.play1Run != null) {
                if(selectedCard.isWild()){ //wilds are always accepted
                    Card[] tempPlay1Run = new Card[play1Run.length + 1];
                    for (int i = 0; i < play1Run.length; i++) {
                        tempPlay1Run[i] = play1Run[i];
                    }
                    //Make sure wild number is set appropriately
                    if(this.play1Run[play1Run.length-1].getNumber() < 12) selectedCard.setNumber(this.play1Run[play1Run.length-1].getNumber()+1);
                    else if(this.play1Run[play1Run.length-1].getNumber() == 100)selectedCard.setNumber(100);
                    else selectedCard.setNumber(this.play1Run[0].getNumber()-1);
                    //Set instance variable
                    tempPlay1Run[play1Run.length] = selectedCard;
                    play1Run = this.sortRaw(tempPlay1Run);
                    return true;
                }
                else { //Identify if the number is smaller/bigger than the edges of the run
                    int x = -1;
                    if (selectedCard != null && selectedCard.getNumber() < play1Run[0].getNumber()) x = 1;
                    else if(selectedCard != null && selectedCard.getNumber() > play1Run[play1Run.length-1].getNumber()) x = 0;
                    if(x != -1) {//Place appropriately
                        Card[] tempPlay1Run = new Card[play1Run.length + 1];
                        for (int i = 0; i < play1Run.length; i++) {
                            tempPlay1Run[i + x] = play1Run[i];
                        }
                        if (x == 0) tempPlay1Run[play1Run.length] = selectedCard;
                        else if (x == 1) tempPlay1Run[0] = selectedCard;
                        //Test if card + old run is still a run
                        if (!(isRun(tempPlay1Run, tempPlay1Run.length, playerNum, test, 0) == null))
                            return true;
                    }
                }
            }
            //Sets
            if(this.play1Set1 != null) {
                if(selectedCard.isWild()){//wilds are always accepted
                    Card[] tempPlay1Set1 = new Card[play1Set1.length + 1];
                    for (int i = 0; i < play1Set1.length; i++) {
                        tempPlay1Set1[i] = play1Set1[i];
                    }
                    //Make sure wild number is set appropriately
                    selectedCard.setNumber(this.play1Set1[play1Set1.length-1].getNumber());
                    //Set instance variable
                    tempPlay1Set1[play1Set1.length] = selectedCard;
                    play1Set1 = this.sortRaw(tempPlay1Set1);
                    return true;
                }
                else { //Else check if group is valid
                    Card[] tempPlay1Set1 = new Card[play1Set1.length + 1];
                    for (int i = 0; i < play1Set1.length; i++) {
                        tempPlay1Set1[i] = play1Set1[i];
                    }
                    tempPlay1Set1[play1Set1.length] = selectedCard;
                    if (!(isSet(tempPlay1Set1, tempPlay1Set1.length, playerNum, 1, test) == null)) {
                        return true;
                    }
                }
            }
            if(this.play1Set2 != null) {
                if(selectedCard.isWild()){//wilds are always accepted
                    Card[] tempPlay1Set2 = new Card[play1Set2.length + 1];
                    for (int i = 0; i < play1Set2.length; i++) {
                        tempPlay1Set2[i] = play1Set2[i];
                    }
                    //Make sure wild number is set appropriately
                    selectedCard.setNumber(this.play1Set2[play1Set2.length-1].getNumber());
                    //Set instance variable
                    tempPlay1Set2[play1Set2.length] = selectedCard;
                    play1Set2 = this.sortRaw(tempPlay1Set2);
                    return true;
                }
                else { //Else check if group is valid
                    Card[] tempPlay1Set2 = new Card[play1Set2.length + 1];
                    for (int i = 0; i < play1Set2.length; i++) {
                        tempPlay1Set2[i] = play1Set2[i];
                    }
                    tempPlay1Set2[play1Set2.length] = selectedCard;
                    if (!(isSet(tempPlay1Set2, tempPlay1Set2.length, playerNum, 2, test) == null))
                        return true;
                }
            }


            //Colors
            if(this.play1Color != null) {
                if(this.play1Color[0].getColor() == selectedCard.getColor() || selectedCard.isWild()){  //Same color or wild accepted
                    selectedCard.setColor(this.play1Color[0].getColor());
                    Card[] tempPlay1Color = new Card[play1Color.length + 1];
                    for (int i = 0; i < play1Color.length; i++) {
                        tempPlay1Color[i] = play1Color[i];
                    }
                    //Make sure wild number is set appropriately
                    if(selectedCard.isWild()) selectedCard.setColor(this.play1Color[play1Color.length-1].getColor());
                    tempPlay1Color[play1Color.length] = selectedCard;
                    play1Color = this.sortRaw(tempPlay1Color);
                    return true;
                }


            }
        } else if (playerNum == 1) {

            //Runs
            if(this.play2Run != null) {
                if(selectedCard.isWild()){//wilds are always accepted
                    Card[] tempPlay2Run = new Card[play2Run.length + 1];
                    for (int i = 0; i < play2Run.length; i++) {
                        tempPlay2Run[i] = play2Run[i];
                    }
                    //Make sure wild number is set appropriately
                    if(this.play2Run[play2Run.length-1].getNumber() < 12) selectedCard.setNumber(this.play2Run[play2Run.length-1].getNumber()+1);
                    else if(this.play2Run[play2Run.length-1].getNumber() == 100)selectedCard.setNumber(100);
                    else selectedCard.setNumber(this.play2Run[0].getNumber()-1);
                    //Set instance variable
                    tempPlay2Run[play2Run.length] = selectedCard;
                    play2Run = this.sortRaw(tempPlay2Run);
                    return true;
                }
                else {//Else check if group is valid
                    int x = -1;
                    if (selectedCard != null && selectedCard.getNumber() < play2Run[0].getNumber()) x = 1;
                    else if(selectedCard != null && selectedCard.getNumber() > play2Run[play2Run.length-1].getNumber()) x = 0;
                    if(x != -1) {
                        Card[] tempPlay2Run = new Card[play2Run.length + 1];
                        for (int i = 0; i < play2Run.length; i++) {
                            tempPlay2Run[i + x] = play2Run[i];
                        }
                        if (x == 0) tempPlay2Run[play2Run.length] = selectedCard;
                        else if (x == 1) tempPlay2Run[0] = selectedCard;

                        if (!(isRun(tempPlay2Run, tempPlay2Run.length, playerNum, test, 0) == null))
                            return true;
                    }
                }
            }

            //Sets
            if(this.play2Set1 != null) {
                if(selectedCard.isWild()){//wilds are always accepted
                    Card[] tempPlay2Set1 = new Card[play2Set1.length + 1];
                    for (int i = 0; i < play2Set1.length; i++) {
                        tempPlay2Set1[i] = play2Set1[i];
                    }
                    //Make sure wild number is set appropriately
                    selectedCard.setNumber(this.play2Set1[play2Set1.length-1].getNumber());
                    //Set instance variable
                    tempPlay2Set1[play2Set1.length] = selectedCard;
                    play2Set1 = this.sortRaw(tempPlay2Set1);
                    return true;
                }
                else {//Else check if group is valid
                    Card[] tempPlay2Set1 = new Card[play2Set1.length + 1];
                    for (int i = 0; i < play2Set1.length; i++) {
                        tempPlay2Set1[i] = play2Set1[i];
                    }
                    tempPlay2Set1[play2Set1.length] = selectedCard;
                    if (!(isSet(tempPlay2Set1, tempPlay2Set1.length, playerNum, 1, test) == null))
                        return true;
                }
            }
            if(this.play2Set2 != null) {
                if(selectedCard.isWild()){//wilds are always accepted
                    Card[] tempPlay2Set2 = new Card[play2Set2.length + 1];
                    for (int i = 0; i < play2Set2.length; i++) {
                        tempPlay2Set2[i] = play2Set2[i];
                    }
                    //Make sure wild number is set appropriately
                    selectedCard.setNumber(this.play2Set2[play2Set2.length-1].getNumber());
                    //Set instance variable
                    tempPlay2Set2[play2Set2.length] = selectedCard;
                    play2Set2 = this.sortRaw(tempPlay2Set2);
                    return true;
                }
                else {
                    Card[] tempPlay2Set2 = new Card[play2Set2.length + 1];
                    for (int i = 0; i < play2Set2.length; i++) {
                        tempPlay2Set2[i] = play2Set2[i];
                    }
                    tempPlay2Set2[play2Set2.length] = selectedCard;
                    if (!(isSet(tempPlay2Set2, tempPlay2Set2.length, playerNum, 2, test) == null))
                        return true;
                }
            }

            //Colors
            if(this.play2Color != null) {
                if(this.play2Color[0].getColor() == selectedCard.getColor() || selectedCard.isWild()){ //Same color or wild accepted
                    selectedCard.setColor(this.play2Color[0].getColor());
                    Card[] tempPlay2Color = new Card[play2Color.length + 1];
                    for (int i = 0; i < play2Color.length; i++) {
                        tempPlay2Color[i] = play2Color[i];
                    }
                    //Make sure wild number is set appropriately
                    if(selectedCard.isWild()) selectedCard.setColor(this.play2Color[play2Color.length-1].getColor());
                    tempPlay2Color[play2Color.length] = selectedCard;
                    play2Color = this.sortRaw(tempPlay2Color);
                    return true;
                }
            }
        }
        return false;
    }//checkHitValid end
} // end of Phase class
