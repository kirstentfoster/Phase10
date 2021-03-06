

package up.edu.phase10;

/**
 External Citation
 Date: 11/5/20
 Problem: I wanted to adapt the collections sort to sort my cards
 Resource: https://beginnersbook.com/2013/12/java-arraylist-of-object-sort-example-comparable-and-comparator/
 Solution: I used the example from this link and adapted it to my code.
 */

/**
 * @author Kirsten Foster, Alexis Molina, Emily Hoppe, Grace Penunuri
 * Holds information about each card (particularly number and color)
 * Also includes getters/setters for card info
 * Includes a "to string" method to explain what the card is
 */
public class Card implements Comparable{
    private int number; //1-12 number
    private int color; // 1-4 to represent the 4 colors
    //wild cards are 100,100
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
        if(this.number < 10 && this.number > 0 && !this.isWild()) { this.score = 5;}
        //Cards with value 10-12 are worth 10 points
        else if(this.number >= 10 && !this.isWild()){this.score = 10;}
        //Skip cards are worth 15 points
        else if(isSkip()){this.score = 15;}
        //Wild cards are worth 25 points
        else if(isWild()){this.score = 25;}
    }

    //getters and setters
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

    public boolean isWild(){ //Wild card number or color will change to fit group it joins, one value will always be 100
        return (this.number == 100 || this.color == 100);
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
                color = "red";
            }
            else if(this.getColor()==2){
                color = "blue";
            }
            else if(this.getColor()==3){
                color = "green";
            }
            else if(this.getColor()==4){
                color = "yellow";
            }
            return color + " " + this.getNumber();
        }

    }

    /**
     * the purpose of this method is to compare the card numbers for colors and order them
     * @param o - this object is meant to create an instance of whatever card is looking at
     * @return to return the number of the object so that it can be ordered
     */
    @Override
    public int compareTo(Object o) {
        int compareNum = (((Card) o).getNumber());
        return this.number - compareNum;
    }
}
