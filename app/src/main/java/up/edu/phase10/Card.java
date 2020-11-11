/**
 * @author Kirsten Foster, Alexis Molina, Emily Hoppe, Grace Penunuri
 * Holds information about each card (particularly number and color)
 * Also includes getters/setters for card info
 * Includes a "to string" method to explain what the card is
 */

package up.edu.phase10;

import android.graphics.Canvas;
import android.graphics.Paint;

/*
EXTERNAL CITATION https://beginnersbook.com/2013/12/java-arraylist-of-object-sort-example-comparable-and-comparator/
 */
public class Card implements Comparable{
    private int number; //1-12 number
    private int color; // 1-4 to represent the 4 colors
    //wild cards are 0,0
    //skip cards are -1,-1
    private int score;
    //Score is determined based on number value
    //This is outlined in constructor
    //In Phase 10, lower scores are better

    /**
     * sets number, color, and score of card
     * @param n holds number of card
     * @param c holds color of card
     */
    public Card(int n, int c){
        this.number = n;
        this.color = c;

        //Cards with values 1-9 are worth 5 points
        if(this.number < 10 && this.number > 0) { this.score = 5;}
        //Cards with value 10-12 are worth 10 points
        else if(this.number >= 10){this.score = 10;}
        //Skip cards are worth 15 points
        else if(isSkip()){this.score = 15;}
        //Wild cards are worth 25 points
        else if(isWild()){this.score = 25;}
    }


    public void setNumber(int n){
        this.number = n;
    }
    public int getNumber(){
        return this.number;
    }

    public void setColor(int c){
        this.color = c;
    }
    public int getColor(){
        return this.color;
    }

    public int getScore(){return this.score;}

    public boolean isWild(){
        return (this.number == 0 && this.color == 0);
    }
    public boolean isSkip(){
        return (this.number == -1 && this.color == -1);
    }


    /**
     * checks if card is wild or skip, otherwise checks color and number
     * @return information about card
     */
    public String toString(){
        String color = null;
        if(this.isSkip()){
            return "skip card";
        }
        else if(this.isWild()){
            return "wild card";
        }
        else{
            if(this.getColor()==1){
                color = "yellow";
            }
            else if(this.getColor()==2){
                color = "green";
            }
            else if(this.getColor()==3){
                color = "blue";
            }
            else if(this.getColor()==4){
                color = "red";
            }
            return color + " " + this.getNumber();
        }

    }


    @Override
    public int compareTo(Object o) {
        int compareNum = (((Card) o).getNumber());
        return this.number - compareNum;
    }
}
