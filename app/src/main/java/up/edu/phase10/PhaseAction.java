/**
 * @author Kirsten Foster, Alexis Molina, Emily Hoppe, Grace Penunuri
 * Holds information about each card (particularly number and color)
 * Also includes getters/setters for card info
 * Includes a "to string" method to explain what the card is
 */

package up.edu.phase10;

/*
EXTERNAL CITATION https://beginnersbook.com/2013/12/java-arraylist-of-object-sort-example-comparable-and-comparator/
 */
import java.util.ArrayList;

import up.edu.phase10.Framework.GamePlayer;
import up.edu.phase10.Framework.GameAction;

public class PhaseAction extends GameAction {
    private ArrayList<Card> phaseContent;
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public PhaseAction(GamePlayer player, ArrayList<Card> phaseContent) {
        super(player);
        this.phaseContent=phaseContent;
    }
    public ArrayList<Card>  getPhaseContent() {
        return phaseContent;
    }
}